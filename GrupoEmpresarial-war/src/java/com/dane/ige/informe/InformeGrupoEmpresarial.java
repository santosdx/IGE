package com.dane.ige.informe;

import com.dane.ige.modelo.connection.ConexionBd;
import com.dane.ige.modelo.entidad.BodegaIdentificacion;
import com.dane.ige.modelo.entidad.ObjetoIndicador;
import com.dane.ige.modelo.local.administracion.BodegaIdentificacionFacadeLocal;
import com.dane.ige.modelo.servicios.administracion.BodegaIndicadorGrupo;
import com.dane.ige.reporte.ReporteGrupoEmpresa;
import com.dane.ige.seguridad.Login;
import com.dane.ige.utilidad.ArchivoProperties;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.imageio.ImageIO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.chart.PieChartModel;

/**
 * Clase que contiene las funciones, procedimientos y métodos para generar el
 * informe ejecutivo de los grupos empresariales, mediente el uso de la
 * herramienta Jasper Report
 *
 * @author SRojasM
 */
@ManagedBean(name = "MbInformeGrupoEmpresarial")
@SessionScoped
public class InformeGrupoEmpresarial {

    final static Logger LOGGER = Logger.getLogger(ReporteGrupoEmpresa.class);

    @EJB
    private BodegaIdentificacionFacadeLocal eJBServicioBodegaIdentificacion;
    @EJB
    private BodegaIndicadorGrupo eJBservicioBodegaIndicadorGrupo;

    @ManagedProperty("#{MbLogin}")
    private Login servicioLogin;

    private StreamedContent contenido;
    private Long idIdentificacionSeleccionada;
    private BodegaIdentificacion identificacionSeleccionada;
    private List<BodegaIdentificacion> listaIdentificacion;
    private String resenaConglomerado;

    private PieChartModel pieModeUnidadLegalDepartamento;
    private StreamedContent chart;

    /**
     * Creates a new instance of InformeGrupoEmpresarial
     */
    public InformeGrupoEmpresarial() {
    }

    @PostConstruct
    public void init() {
        setPieModeUnidadLegalDepartamento(new PieChartModel());
        getPieModeUnidadLegalDepartamento().set("Item", 100);
        setListaIdentificacion(eJBServicioBodegaIdentificacion.obtenerListaIdentificacionTodosLosGrupos());
    }

    public void mySelectionMethodListener(AjaxBehaviorEvent event) {
        // cargarIndicadores();
        for (BodegaIdentificacion bodegaIdentificacion : getListaIdentificacion()) {
            if (bodegaIdentificacion.getId().getId().compareTo(getIdIdentificacionSeleccionada()) == 0) {
                setIdentificacionSeleccionada(bodegaIdentificacion);
                //setChart(converterCharToImage("Unidad Legal por Departamento",createDatasetDepartamento()));

                List<ObjetoIndicador> datosIndicadorUnidadLegalDepartamento;
                setPieModeUnidadLegalDepartamento(new PieChartModel());
                datosIndicadorUnidadLegalDepartamento = eJBservicioBodegaIndicadorGrupo.indicadorUnidadLegalPorDepartamentoDelGrpupo(getIdIdentificacionSeleccionada());
                for (ObjetoIndicador objetoIndicador : datosIndicadorUnidadLegalDepartamento) {
                    getPieModeUnidadLegalDepartamento().set(objetoIndicador.getItem(), objetoIndicador.getValor());
                }

                getPieModeUnidadLegalDepartamento().setTitle("Unidad Legal por Departamento");
                getPieModeUnidadLegalDepartamento().setLegendPosition("ne");
                getPieModeUnidadLegalDepartamento().setShowDataLabels(true);
                getPieModeUnidadLegalDepartamento().setMouseoverHighlight(true);

            }
        }
    }

    public void generarInformeEjecutivoGrupoEmpresarialPdf() {
        String urlArchivo = ArchivoProperties.obtenerPropertieFilePathProperties("informe.grupoEmpresarial.path");
        String nombreArchivo = ArchivoProperties.obtenerPropertieFilePathProperties("informe.grupoEmpresarial.archivo");
        //LOGGER.info(urlArchivo);
        //LOGGER.info(nombreArchivo);

        ConexionBd connection = null;
        JasperReport reporteJasper = null;
        JasperPrint print = null;

        try {
            connection = new ConexionBd(ConexionBd.getJndi_Sid_Desarrollo());

            Map parameters = new HashMap();
            parameters.put("id_grupo", getIdIdentificacionSeleccionada());
            parameters.put("RESENA_CONGLOMERADO", getResenaConglomerado());
            parameters.put("chartUnidadLegalDepartamento", renderedImage);

            reporteJasper = JasperCompileManager.compileReport(urlArchivo + nombreArchivo);
            print = JasperFillManager.fillReport(reporteJasper, parameters, connection.getConexion());
            byte[] bites = JasperExportManager.exportReportToPdf(print);

            setContenido(new DefaultStreamedContent(new ByteArrayInputStream(bites), "application/pdf", "Informe Ejecutivo Grupo Empresarial.pdf"));
        } catch (JRException e) {
            LOGGER.warn(e);
        } finally {
            if (connection != null) {
                try {
                    connection.closeConexionBd();
                } catch (Exception e) {
                    LOGGER.warn(e);
                }
            }
        }
        //LOGGER.info("Fin");
    }

    private PieDataset createDatasetDepartamento() {
        List<ObjetoIndicador> datosIndicadorUnidadLegalDepartamento;
        DefaultPieDataset dataset = new DefaultPieDataset();
        datosIndicadorUnidadLegalDepartamento = eJBservicioBodegaIndicadorGrupo.indicadorUnidadLegalPorDepartamentoDelGrpupo(getIdIdentificacionSeleccionada());
        for (ObjetoIndicador objetoIndicador : datosIndicadorUnidadLegalDepartamento) {
            dataset.setValue(objetoIndicador.getItem(), objetoIndicador.getValor());
        }
        return dataset;
    }

    private StreamedContent converterCharToImage(String titulo, PieDataset dataset) {
        //Chart
        StreamedContent chart = null;
        try {
            JFreeChart jfreechart = ChartFactory.createPieChart(titulo, dataset, true, true, false);
            File chartFile = new File("dynamichart");
            ChartUtilities.saveChartAsPNG(chartFile, jfreechart, 375, 300);
            chart = new DefaultStreamedContent(new FileInputStream(chartFile), "image/png");
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(InformeGrupoEmpresarial.class.getName()).log(Level.SEVERE, null, ex);
        }
        return chart;
    }

    private String base64Str;

    public String getBase64Str() {
        return base64Str;
    }

    public void setBase64Str(String base64Str) {
        this.base64Str = base64Str;
    }
    
    private RenderedImage renderedImage;
            
    public void submittedBase64Str(ActionEvent event){
    // You probably want to have a more comprehensive check here. 
    // In this example I only use a simple check
    if(base64Str.split(",").length > 1){
        String encoded = base64Str.split(",")[1];
        //byte[] decoded = org.apache.commons.codec.binary.Base64.decodeBase64(encoded);
        byte[] decoded = Base64.decodeBase64(encoded);
        // Write to a .png file
        try {
            renderedImage = ImageIO.read(new ByteArrayInputStream(decoded));
            ImageIO.write(renderedImage, "png", new File("C:\\out.png")); // use a proper path & file name here.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
    
    
    
    public StreamedContent getChart() {
        return chart;
    }

    public void setChart(StreamedContent chart) {
        this.chart = chart;
    }

    public String getResenaConglomerado() {
        return resenaConglomerado;
    }

    public void setResenaConglomerado(String resenaConglomerado) {
        this.resenaConglomerado = resenaConglomerado;
    }

    public Long getIdIdentificacionSeleccionada() {
        return idIdentificacionSeleccionada;
    }

    public void setIdIdentificacionSeleccionada(Long idIdentificacionSeleccionada) {
        this.idIdentificacionSeleccionada = idIdentificacionSeleccionada;
    }

    public BodegaIdentificacion getIdentificacionSeleccionada() {
        return identificacionSeleccionada;
    }

    public void setIdentificacionSeleccionada(BodegaIdentificacion identificacionSeleccionada) {
        this.identificacionSeleccionada = identificacionSeleccionada;
    }

    public List<BodegaIdentificacion> getListaIdentificacion() {
        return listaIdentificacion;
    }

    public void setListaIdentificacion(List<BodegaIdentificacion> listaIdentificacion) {
        this.listaIdentificacion = listaIdentificacion;
    }

    public StreamedContent getContenido() {
        return contenido;
    }

    public void setContenido(StreamedContent contenido) {
        this.contenido = contenido;
    }

    public Login getServicioLogin() {
        return servicioLogin;
    }

    public void setServicioLogin(Login servicioLogin) {
        this.servicioLogin = servicioLogin;
    }

    public PieChartModel getPieModeUnidadLegalDepartamento() {
        return pieModeUnidadLegalDepartamento;
    }

    public void setPieModeUnidadLegalDepartamento(PieChartModel pieModeUnidadLegalDepartamento) {
        this.pieModeUnidadLegalDepartamento = pieModeUnidadLegalDepartamento;
    }
}
