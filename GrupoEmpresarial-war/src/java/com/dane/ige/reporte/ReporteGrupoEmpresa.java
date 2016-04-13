package com.dane.ige.reporte;

import com.dane.ige.modelo.connection.ConexionBd;
import com.dane.ige.seguridad.Login;
import com.dane.ige.utilidad.ArchivoProperties;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 * Clase que administra los metodos, funciones y procesos necesarios para la
 * generación del reporte de los grupos empresariales.
 *
 * @author SRojasM
 */
@ManagedBean(name = "MbReporteGrupoEmpresa")
@SessionScoped
public class ReporteGrupoEmpresa implements Serializable {

    final static Logger LOGGER = Logger.getLogger(ReporteGrupoEmpresa.class);

    private StreamedContent contenido;
    private StringBuffer listaErrores;

    @ManagedProperty("#{MbLogin}")
    private Login servicioLogin;

    public ReporteGrupoEmpresa() {
    }

    /**
     * Método que permite generar el reporte con JasperReport sobre la
     * información del grupo empresarial y exportarlo a un arhivo PDF.
     */
    public void generarReportePdf() {
        String urlArchivo = ArchivoProperties.obtenerPropertieFilePathProperties("reporte.grupoEmpresarial.path");
        String nombreArchivo = ArchivoProperties.obtenerPropertieFilePathProperties("reporte.grupoEmpresarial.archivo");
        //LOGGER.info(urlArchivo);
        //LOGGER.info(nombreArchivo);

        ConexionBd connection = null;
        JasperReport reporteJasper = null;
        JasperPrint print = null;
        setListaErrores(new StringBuffer());

        try {
            connection = new ConexionBd(ConexionBd.getJndi_Sid_Desarrollo());

            Map parameters = new HashMap();
            parameters.put("id_grupo", getServicioLogin().getUsuarioLogueado().getIdIdentificacion());

            reporteJasper = JasperCompileManager.compileReport(urlArchivo + nombreArchivo);
            print = JasperFillManager.fillReport(reporteJasper, parameters, connection.getConexion());
            byte[] bites = JasperExportManager.exportReportToPdf(print);

            setContenido(new DefaultStreamedContent(new ByteArrayInputStream(bites), "application/pdf", "Reporte Grupo Empresarial.pdf"));
        } catch (JRException e) {
            LOGGER.warn(e);
            getListaErrores().append("[77] ReporteGrupoEmpresa.java -> ").append(e);
        } finally {
            if (connection != null) {
                try {
                    connection.closeConexionBd();
                } catch (Exception e) {
                    LOGGER.warn(e);
                    getListaErrores().append("[85] ReporteGrupoEmpresa.java -> ").append(e);
                }
            }
        }
        //LOGGER.info("Fin");
    }

    /**
     * El metodo se da de baja por que la implementación se realizo con la
     * creación de archivos en el cliente, lo que genera inseguridad para los
     * datos.
     *
     * @deprecated
     */
    public void generarReportePdf2() {
        //resourcePath['reporte.grupoEmpresarial.path'], resourcePath['reporte.grupoEmpresarial.archivo']
        String tempPathFile = ArchivoProperties.obtenerPropertieFilePathProperties("sistema.tempFile.path");
        String urlArchivo = ArchivoProperties.obtenerPropertieFilePathProperties("reporte.grupoEmpresarial.path");
        String nombreArchivo = ArchivoProperties.obtenerPropertieFilePathProperties("reporte.grupoEmpresarial.archivo");

        //LOGGER.info(urlArchivo);
        //LOGGER.info(nombreArchivo);
        ConexionBd connection = null;
        JasperReport reporteJasper = null;
        JasperPrint print = null;
        setListaErrores(new StringBuffer());

        try {
            connection = new ConexionBd(ConexionBd.getJndi_Sid_Desarrollo());
            //File temp = File.createTempFile(getNombreArchivoTemporal(), ".pdf");
            //File temp = File.createTempFile("ReporteGrupoEmpresarial_", ".pdf", new File(tempPathFile));
            String reportName = getServicioLogin().getUsuarioLogueado().getId() + "-Reporte Grupo Empresarial.pdf";
            File temp = new File(tempPathFile + reportName);

            Map parameters = new HashMap();
            parameters.put("id_grupo", getServicioLogin().getUsuarioLogueado().getIdIdentificacion());

            reporteJasper = JasperCompileManager.compileReport(urlArchivo + nombreArchivo);
            print = JasperFillManager.fillReport(reporteJasper, parameters, connection.getConexion());
            JRExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, new FileOutputStream(temp));
            exporter.exportReport();

            setContenido(new DefaultStreamedContent(new FileInputStream(temp), "application/pdf"));
            temp.deleteOnExit();
            //LOGGER.info("setContenido");
        } catch (JRException e) {
            LOGGER.warn(e);
            getListaErrores().append("[132] ReporteGrupoEmpresa.java -> ").append(e);
            //throw new RuntimeException("It's not possible to generate the pdf report.", e);
        } catch (FileNotFoundException e) {
            LOGGER.warn(e);
            getListaErrores().append("[136] ReporteGrupoEmpresa.java -> ").append(e);
            //throw new RuntimeException("It's not possible to generate the pdf report.", e);
        } catch (IOException ex) {
            //java.util.logging.Logger.getLogger(ReporteGrupoEmpresa.class.getName()).log(Level.SEVERE, null, ex);
            LOGGER.warn(ex);
            getListaErrores().append("[141] ReporteGrupoEmpresa.java -> ").append(ex);
        } finally {
            if (connection != null) {
                try {
                    connection.closeConexionBd();
                } catch (Exception e) {
                    LOGGER.warn(e);
                    getListaErrores().append("[148] ReporteGrupoEmpresa.java -> ").append(e);
                }
            }
        }

        //LOGGER.info("Fin");
    }

    //Métodos Set y Get de la clase
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

    public StringBuffer getListaErrores() {
        return listaErrores;
    }

    public void setListaErrores(StringBuffer listaErrores) {
        this.listaErrores = listaErrores;
    }

}
