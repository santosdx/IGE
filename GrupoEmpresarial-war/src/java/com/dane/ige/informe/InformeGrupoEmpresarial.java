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

    private StreamedContent contenidoPDF;
    private Long idIdentificacionSeleccionada;
    private BodegaIdentificacion identificacionSeleccionada;
    private List<BodegaIdentificacion> listaIdentificacion;
    private String resenaConglomerado;
    private String analisis1;

    private PieChartModel pieModeUnidadLegalDepartamento;
    private StreamedContent chartUnidadLegalDepartamento;

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

        for (BodegaIdentificacion bodegaIdentificacion : getListaIdentificacion()) {
            if (bodegaIdentificacion.getId().getId().compareTo(getIdIdentificacionSeleccionada()) == 0) {
                setIdentificacionSeleccionada(bodegaIdentificacion);
                //setChartUnidadLegalDepartamento(converterCharToImage("Unidad Legal por Departamento",createDatasetDepartamento()));

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

    /**
     * Método que permite generar el Documetno PDF del informe ejecutivo.
     */
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
            parameters.put("chartUnidadLegalDepartamento", convertirBase64StrToRenderdImage(getBase64StrChartDepartamento()));
            parameters.put("ANALISIS1", getAnalisis1());

            reporteJasper = JasperCompileManager.compileReport(urlArchivo + nombreArchivo);
            print = JasperFillManager.fillReport(reporteJasper, parameters, connection.getConexion());
            byte[] bites = JasperExportManager.exportReportToPdf(print);

            setContenidoPDF(new DefaultStreamedContent(new ByteArrayInputStream(bites), "application/pdf", "Informe Ejecutivo Grupo Empresarial.pdf"));
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

    // Métodos convertir Chart to Imagen 
    private String base64StrChartDepartamento;

    public String getBase64StrChartDepartamento() {
        return base64StrChartDepartamento;
    }

    public void setBase64StrChartDepartamento(String base64StrChartDepartamento) {
        this.base64StrChartDepartamento = base64StrChartDepartamento;
    }

    /**
     * Método que permite convertir una cadena String en base 64 en un objeto
     * RenderedImage
     *
     * @param base64Str
     * @return RenderedImage
     */
    public RenderedImage convertirBase64StrToRenderdImage(String base64Str) {
        RenderedImage renderedImage = null;
        // You probably want to have a more comprehensive check here. 
        // In this example I only use a simple check
        if (base64Str.split(",").length > 1) {
            String encoded = base64Str.split(",")[1];
            //byte[] decoded = org.apache.commons.codec.binary.Base64.decodeBase64(encoded);
            byte[] decoded = Base64.decodeBase64(encoded);
            // Write to a .png file
            try {
                renderedImage = ImageIO.read(new ByteArrayInputStream(decoded));
                //ImageIO.write(renderedImage, "png", new File("C:\\out.png")); // use a proper path & file name here.
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return renderedImage;
    }

    /**
     * Método que permite crear en PieDataset de la consulta para generar el
     * indicador Chart de Unidad Legal pro Departamento
     *
     * @deprecated
     * @return PieDataset
     */
    private PieDataset createDatasetDepartamento() {
        List<ObjetoIndicador> datosIndicadorUnidadLegalDepartamento;
        DefaultPieDataset dataset = new DefaultPieDataset();
        datosIndicadorUnidadLegalDepartamento = eJBservicioBodegaIndicadorGrupo.indicadorUnidadLegalPorDepartamentoDelGrpupo(getIdIdentificacionSeleccionada());
        for (ObjetoIndicador objetoIndicador : datosIndicadorUnidadLegalDepartamento) {
            dataset.setValue(objetoIndicador.getItem(), objetoIndicador.getValor());
        }
        return dataset;
    }

    /**
     * Método que permite convertir un PieDataset en un StreamedContent para
     * visualizarlo como imagen en la interfaz
     *
     * @deprecated
     * @param titulo
     * @param dataset
     * @return StreamedContent
     */
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

    // Metodos Sett y Gett
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

    public String getResenaConglomerado() {
        return resenaConglomerado;
    }

    public void setResenaConglomerado(String resenaConglomerado) {
        this.resenaConglomerado = resenaConglomerado;
    }

    public StreamedContent getChartUnidadLegalDepartamento() {
        return chartUnidadLegalDepartamento;
    }

    public void setChartUnidadLegalDepartamento(StreamedContent chartUnidadLegalDepartamento) {
        this.chartUnidadLegalDepartamento = chartUnidadLegalDepartamento;
    }

    public String getAnalisis1() {
        return analisis1;
    }

    public void setAnalisis1(String analisis1) {
        this.analisis1 = analisis1;
    }

    public StreamedContent getContenidoPDF() {
        return contenidoPDF;
    }

    public void setContenidoPDF(StreamedContent contenidoPDF) {
        this.contenidoPDF = contenidoPDF;
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
