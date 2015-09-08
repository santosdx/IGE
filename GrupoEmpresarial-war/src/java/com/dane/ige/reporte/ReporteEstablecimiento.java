package com.dane.ige.reporte;

import com.dane.ige.modelo.connection.ConexionBd;
import com.dane.ige.negocio.FormularioEstablecimiento;
import com.dane.ige.utilidad.ArchivoProperties;
import com.dane.ige.utilidad.Mensaje;
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
@ManagedBean(name = "MbReporteEstablecimiento")
@SessionScoped
public class ReporteEstablecimiento implements Serializable {

    final static Logger LOGGER = Logger.getLogger(ReporteEstablecimiento.class);

    private StreamedContent contenido;
    private StringBuffer listaErrores;

    @ManagedProperty("#{MbFormEstablecimiento}")
    private FormularioEstablecimiento servicioFrmEstablecimiento;

    public ReporteEstablecimiento() {
    }

    /**
     * Método que permite generar el reporte con JasperReport sobre la información
     * del listado de estalecimientos de una unidad legal y exportarlo a un arhivo PDF.
     */
    public void generarReporteEstablecimientosPdf() {
        String urlArchivo = ArchivoProperties.obtenerPropertieFilePathProperties("reporte.establecimientos.path");
        String nombreArchivo = ArchivoProperties.obtenerPropertieFilePathProperties("reporte.establecimientos.archivo");

        if (getServicioFrmEstablecimiento().getListaIdentificacion() != null && getServicioFrmEstablecimiento().getListaIdentificacion().size() > 0) {

            //LOGGER.info(urlArchivo);
            //LOGGER.info(nombreArchivo);
            ConexionBd connection = null;
            JasperReport reporteJasper = null;
            JasperPrint print = null;
            setListaErrores(new StringBuffer());

            try {
                connection = new ConexionBd(ConexionBd.getJndi_Sid_Desarrollo());

                Map parameters = new HashMap();
                parameters.put("id_ul_relacionada", getServicioFrmEstablecimiento().getIdIdentificacionSeleccionada());

                reporteJasper = JasperCompileManager.compileReport(urlArchivo + nombreArchivo);
                print = JasperFillManager.fillReport(reporteJasper, parameters, connection.getConexion());
                byte[] bites = JasperExportManager.exportReportToPdf(print);

                setContenido(new DefaultStreamedContent(new ByteArrayInputStream(bites), "application/pdf", "Reporte Lista Establecimientos.pdf"));
            } catch (JRException e) {
                LOGGER.warn(e);
                getListaErrores().append("[81] ReporteEstablecimiento.java -> ").append(e);
            } finally {
                if (connection != null) {
                    try {
                        connection.closeConexionBd();
                    } catch (Exception e) {
                        LOGGER.warn(e);
                        getListaErrores().append("[88] ReporteEstablecimiento.java -> ").append(e);
                    }
                }
            }
        } else {
            Mensaje.agregarMensajeGrowlWarn("Atención", "Debe seleccionar una unidad legal para generar el reporte y/o deben existir registros de establecimientos para la unidad legal seleccionada.");
        }
    }

    /**
     * Método que permite generar el reporte con JasperReport sobre la información
     * de un establecimiento y exportarlo a un arhivo PDF.
     */
    public void generarReporteEstablecimientoPdf(Long idOrganizacion) {
        String urlArchivo = ArchivoProperties.obtenerPropertieFilePathProperties("reporte.establecimiento.path");
        String nombreArchivo = ArchivoProperties.obtenerPropertieFilePathProperties("reporte.establecimiento.archivo");
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

            setContenido(new DefaultStreamedContent(new ByteArrayInputStream(bites), "application/pdf", "Reporte Establecimiento.pdf"));
        } catch (JRException e) {
            LOGGER.warn(e);
            getListaErrores().append("[124] ReporteEstablecimiento.java -> ").append(e);
        } finally {
            if (connection != null) {
                try {
                    connection.closeConexionBd();
                } catch (Exception e) {
                    LOGGER.warn(e);
                    getListaErrores().append("[131] ReporteEstablecimiento.java -> ").append(e);
                }
            }
        }
    }

    /**
     * El metodo se da de baja por que la implementación se realizo con la
     * creación de archivos en el cliente, lo que genera inseguridad para los
     * datos.
     *
     * @deprecated
     */
    public void generarReporteEstablecimientosPdf2() {
        String tempPathFile = ArchivoProperties.obtenerPropertieFilePathProperties("sistema.tempFile.path");
        String urlArchivo = ArchivoProperties.obtenerPropertieFilePathProperties("reporte.establecimientos.path");
        String nombreArchivo = ArchivoProperties.obtenerPropertieFilePathProperties("reporte.establecimientos.archivo");

        if (getServicioFrmEstablecimiento().getListaIdentificacion() != null && getServicioFrmEstablecimiento().getListaIdentificacion().size() > 0) {

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
                parameters.put("id_ul_relacionada", getServicioFrmEstablecimiento().getIdIdentificacionSeleccionada());

                reporteJasper = JasperCompileManager.compileReport(urlArchivo + nombreArchivo);
                print = JasperFillManager.fillReport(reporteJasper, parameters, connection.getConexion());
                JRExporter exporter = new JRPdfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, new FileOutputStream(temp)); // your output goes here
                exporter.exportReport();

                setContenido(new DefaultStreamedContent(new FileInputStream(temp), "application/pdf", "Reporte Establecimientos.pdf"));
                temp.delete();
                temp.deleteOnExit();
                //LOGGER.info("setContenido");
            } catch (JRException e) {
                LOGGER.warn(e);
                getListaErrores().append("[79] ReporteEstablecimiento.java -> ").append(e);
                //throw new RuntimeException("It's not possible to generate the pdf report.", e);
            } catch (FileNotFoundException e) {
                LOGGER.warn(e);
                getListaErrores().append("[83] ReporteEstablecimiento.java -> ").append(e);
                //throw new RuntimeException("It's not possible to generate the pdf report.", e);
            } catch (IOException ex) {
                LOGGER.warn(ex);
                getListaErrores().append("[87] ReporteEstablecimiento.java -> ").append(ex);
                //java.util.logging.Logger.getLogger(ReporteEstablecimiento.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (connection != null) {
                    try {
                        connection.closeConexionBd();
                    } catch (Exception e) {
                        LOGGER.warn(e);
                        getListaErrores().append("[95] ReporteEstablecimiento.java -> ").append(e);
                    }
                }
            }
        } else {
            Mensaje.agregarMensajeGrowlWarn("Atención", "Debe seleccionar una unidad legal para generar el reporte y/o deben existir registros de establecimientos para la unidad legal seleccionada.");
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
    public void generarReporteEstablecimientoPdf2(Long idOrganizacion) {
        String tempPathFile = ArchivoProperties.obtenerPropertieFilePathProperties("sistema.tempFile.path");
        String urlArchivo = ArchivoProperties.obtenerPropertieFilePathProperties("reporte.establecimientos.path");
        String nombreArchivo = ArchivoProperties.obtenerPropertieFilePathProperties("reporte.establecimientos.archivo");

        //LOGGER.info(urlArchivo);
        //LOGGER.info(nombreArchivo);
        ConexionBd connection = null;
        JasperReport reporteJasper = null;
        JasperPrint print = null;
        setListaErrores(new StringBuffer());

        try {
            connection = new ConexionBd(ConexionBd.getJndi_Sid_Desarrollo());
            String reportName = "Reporte Establecimiento_";
            File temp = File.createTempFile(reportName, ".pdf");

            Map parameters = new HashMap();

            parameters.put("id_grupo", idOrganizacion);
            reporteJasper = JasperCompileManager.compileReport(urlArchivo + nombreArchivo);
            print = JasperFillManager.fillReport(reporteJasper, parameters, connection.getConexion());
            JRExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, new FileOutputStream(temp)); // your output goes here
            exporter.exportReport();

            setContenido(new DefaultStreamedContent(new FileInputStream(temp), "application/pdf", "Reporte Establecimientos.pdf"));
            temp.delete();
            temp.deleteOnExit();
            //LOGGER.info("setContenido");
        } catch (JRException e) {
            LOGGER.warn(e);
            getListaErrores().append("[134] ReporteEstablecimiento.java -> ").append(e);
            //throw new RuntimeException("It's not possible to generate the pdf report.", e);
        } catch (FileNotFoundException e) {
            LOGGER.warn(e);
            getListaErrores().append("[138] ReporteEstablecimiento.java -> ").append(e);
            //throw new RuntimeException("It's not possible to generate the pdf report.", e);
        } catch (IOException ex) {
            LOGGER.warn(ex);
            getListaErrores().append("[142] ReporteEstablecimiento.java -> ").append(ex);
            //java.util.logging.Logger.getLogger(ReporteEstablecimiento.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                try {
                    connection.closeConexionBd();
                } catch (Exception e) {
                    LOGGER.warn(e);
                    getListaErrores().append("[150] ReporteEstablecimiento.java -> ").append(e);
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

    public FormularioEstablecimiento getServicioFrmEstablecimiento() {
        return servicioFrmEstablecimiento;
    }

    public void setServicioFrmEstablecimiento(FormularioEstablecimiento servicioFrmEstablecimiento) {
        this.servicioFrmEstablecimiento = servicioFrmEstablecimiento;
    }

    public StringBuffer getListaErrores() {
        return listaErrores;
    }

    public void setListaErrores(StringBuffer listaErrores) {
        this.listaErrores = listaErrores;
    }
}
