package com.dane.ige.reporte;

import com.dane.ige.modelo.connection.ConexionBd;
import com.dane.ige.negocio.FormularioUnidadLegal;
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
 *
 * @author SRojasM
 */
@ManagedBean(name = "MbReporteUnidadLegal")
@SessionScoped
public class ReporteUnidadLegal implements Serializable {

    final static Logger LOGGER = Logger.getLogger(ReporteUnidadLegal.class);

    private StreamedContent contenido;
    private StringBuffer listaErrores;

    @ManagedProperty("#{MbFormUnidadLegal}")
    private FormularioUnidadLegal servicioFrmUnidadLegal;

    public ReporteUnidadLegal() {
    }

    /**
     * Método que permite generar el reporte con JasperReport sobre la información
     * del listado de unidades legales y exportarlo a un arhivo PDF.
     */
    public void generarReporteUnidadesLegalesPdf() {
        String urlArchivo = ArchivoProperties.obtenerPropertieFilePathProperties("reporte.unidadLegales.path");
        String nombreArchivo = ArchivoProperties.obtenerPropertieFilePathProperties("reporte.unidadLegales.archivo");
        //LOGGER.info(urlArchivo);
        //LOGGER.info(nombreArchivo);

        ConexionBd connection = null;
        JasperReport reporteJasper = null;
        JasperPrint print = null;
        setListaErrores(new StringBuffer());

        try {
            connection = new ConexionBd(ConexionBd.getJndi_Sid_Desarrollo());

            Map parameters = new HashMap();
            parameters.put("id_grupo_relacionado", getServicioFrmUnidadLegal().getServicioLogin().getUsuarioLogueado().getIdIdentificacion());

            reporteJasper = JasperCompileManager.compileReport(urlArchivo + nombreArchivo);
            print = JasperFillManager.fillReport(reporteJasper, parameters, connection.getConexion());
            byte[] bites = JasperExportManager.exportReportToPdf(print);

            setContenido(new DefaultStreamedContent(new ByteArrayInputStream(bites), "application/pdf", "Reporte Lista Unidades Legales.pdf"));
        } catch (JRException e) {
            LOGGER.warn(e);
            getListaErrores().append("[78] ReporteUnidadLegal.java -> ").append(e);
        } finally {
            if (connection != null) {
                try {
                    connection.closeConexionBd();
                } catch (Exception e) {
                    LOGGER.warn(e);
                    getListaErrores().append("[85] ReporteUnidadLegal.java -> ").append(e);
                }
            }
        }
        //LOGGER.info("Fin");
    }

    /**
     * Método que permite generar el reporte con JasperReport sobre la información
     * de una unidad legal y exportarlo a un arhivo PDF.
     */
    public void generarReporteUnidadLegalPdf(Long idOrganizacion) {
        String urlArchivo = ArchivoProperties.obtenerPropertieFilePathProperties("reporte.unidadLegal.path");
        String nombreArchivo = ArchivoProperties.obtenerPropertieFilePathProperties("reporte.unidadLegal.archivo");
        //LOGGER.info(urlArchivo);
        //LOGGER.info(nombreArchivo);

        ConexionBd connection = null;
        JasperReport reporteJasper = null;
        JasperPrint print = null;
        setListaErrores(new StringBuffer());

        try {
            connection = new ConexionBd(ConexionBd.getJndi_Sid_Desarrollo());

            Map parameters = new HashMap();

            parameters.put("id_grupo", idOrganizacion);
            reporteJasper = JasperCompileManager.compileReport(urlArchivo + nombreArchivo);
            print = JasperFillManager.fillReport(reporteJasper, parameters, connection.getConexion());
            byte[] bites = JasperExportManager.exportReportToPdf(print);

            setContenido(new DefaultStreamedContent(new ByteArrayInputStream(bites), "application/pdf", "Reporte Unidad Legal.pdf"));
        } catch (JRException e) {
            LOGGER.warn(e);
            getListaErrores().append("[120] ReporteUnidadLegal.java -> ").append(e);
        } finally {
            if (connection != null) {
                try {
                    connection.closeConexionBd();
                } catch (Exception e) {
                    LOGGER.warn(e);
                    getListaErrores().append("[127] ReporteUnidadLegal.java -> ").append(e);
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
    public void generarReporteUnidadesLegalesPdf2() {
        String tempPathFile = ArchivoProperties.obtenerPropertieFilePathProperties("sistema.tempFile.path");
        String urlArchivo = ArchivoProperties.obtenerPropertieFilePathProperties("reporte.unidadLegales.path");
        String nombreArchivo = ArchivoProperties.obtenerPropertieFilePathProperties("reporte.unidadLegales.archivo");
        //LOGGER.info(urlArchivo);
        //LOGGER.info(nombreArchivo);

        ConexionBd connection = null;
        JasperReport reporteJasper = null;
        JasperPrint print = null;
        setListaErrores(new StringBuffer());

        try {
            connection = new ConexionBd(ConexionBd.getJndi_Sid_Desarrollo());
            String reportName = "Reporte Unidad Legal_";
            File temp = File.createTempFile(reportName, ".pdf");

            Map parameters = new HashMap();
            parameters.put("id_grupo_relacionado", getServicioFrmUnidadLegal().getServicioLogin().getUsuarioLogueado().getIdIdentificacion());

            reporteJasper = JasperCompileManager.compileReport(urlArchivo + nombreArchivo);
            print = JasperFillManager.fillReport(reporteJasper, parameters, connection.getConexion());
            JRExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, new FileOutputStream(temp)); // your output goes here
            exporter.exportReport();

            setContenido(new DefaultStreamedContent(new FileInputStream(temp), "application/pdf", "Reporte Unidad Legal.pdf"));
            temp.delete();
            temp.deleteOnExit();
            //LOGGER.info("setContenido");
        } catch (JRException e) {
            LOGGER.warn(e);
            getListaErrores().append("[77] ReporteUnidadLegal.java -> ").append(e);
            //throw new RuntimeException("It's not possible to generate the pdf report.", e);
        } catch (FileNotFoundException e) {
            LOGGER.warn(e);
            getListaErrores().append("[81] ReporteUnidadLegal.java -> ").append(e);
            //throw new RuntimeException("It's not possible to generate the pdf report.", e);
        } catch (IOException ex) {
            LOGGER.warn(ex);
            getListaErrores().append("[85] ReporteUnidadLegal.java -> ").append(ex);
            //java.util.logging.Logger.getLogger(ReporteUnidadLegal.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                try {
                    connection.closeConexionBd();
                } catch (Exception e) {
                    LOGGER.warn(e);
                    getListaErrores().append("[93] ReporteUnidadLegal.java -> ").append(e);
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
    public void generarReporteUnidadLegalPdf2(Long idOrganizacion) {
        String tempPathFile = ArchivoProperties.obtenerPropertieFilePathProperties("sistema.tempFile.path");
        String urlArchivo = ArchivoProperties.obtenerPropertieFilePathProperties("reporte.unidadLegales.path");
        String nombreArchivo = ArchivoProperties.obtenerPropertieFilePathProperties("reporte.unidadLegales.archivo");
        //LOGGER.info(urlArchivo);
        //LOGGER.info(nombreArchivo);

        ConexionBd connection = null;
        JasperReport reporteJasper = null;
        JasperPrint print = null;
        setListaErrores(new StringBuffer());

        try {
            connection = new ConexionBd(ConexionBd.getJndi_Sid_Desarrollo());
            String reportName = "REPORTE";
            File temp = File.createTempFile(reportName, ".pdf");

            Map parameters = new HashMap();

            parameters.put("id_grupo", idOrganizacion);
            reporteJasper = JasperCompileManager.compileReport(urlArchivo + nombreArchivo);
            print = JasperFillManager.fillReport(reporteJasper, parameters, connection.getConexion());
            JRExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, new FileOutputStream(temp)); // your output goes here
            exporter.exportReport();

            setContenido(new DefaultStreamedContent(new FileInputStream(temp), "application/pdf", "Reporte Unidad Legal.pdf"));
            temp.delete();
            temp.deleteOnExit();
            //LOGGER.info("setContenido");
        } catch (JRException e) {
            LOGGER.warn(e);
            getListaErrores().append("[131] ReporteUnidadLegal.java -> ").append(e);
            //throw new RuntimeException("It's not possible to generate the pdf report.", e);
        } catch (FileNotFoundException e) {
            LOGGER.warn(e);
            getListaErrores().append("[135] ReporteUnidadLegal.java -> ").append(e);
            //throw new RuntimeException("It's not possible to generate the pdf report.", e);
        } catch (IOException ex) {
            LOGGER.warn(ex);
            getListaErrores().append("[139] ReporteUnidadLegal.java -> ").append(ex);
            //java.util.logging.Logger.getLogger(ReporteUnidadLegal.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                try {
                    connection.closeConexionBd();
                } catch (Exception e) {
                    LOGGER.warn(e);
                    getListaErrores().append("[147] ReporteUnidadLegal.java -> ").append(e);
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

    public FormularioUnidadLegal getServicioFrmUnidadLegal() {
        return servicioFrmUnidadLegal;
    }

    public void setServicioFrmUnidadLegal(FormularioUnidadLegal servicioFrmUnidadLegal) {
        this.servicioFrmUnidadLegal = servicioFrmUnidadLegal;
    }

    public StringBuffer getListaErrores() {
        return listaErrores;
    }

    public void setListaErrores(StringBuffer listaErrores) {
        this.listaErrores = listaErrores;
    }
}
