package com.dane.ige.reporte;

import com.dane.ige.modelo.connection.ConexionBd;
import com.dane.ige.negocio.FormularioUnidadLegal;
import com.dane.ige.utilidad.Mensaje;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
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
public class ReporteUnidadLegal {

    final static Logger LOGGER = Logger.getLogger(ReporteUnidadLegal.class);

    private StreamedContent contenido;

    @ManagedProperty("#{MbFormUnidadLegal}")
    private FormularioUnidadLegal servicioFrmUnidadLegal;

    public ReporteUnidadLegal() {
    }

    public void generarReporteUnidadesLegalesPdf(String urlArchivo, String nombreArchivo) {

        LOGGER.info(urlArchivo);
        LOGGER.info(nombreArchivo);
        ConexionBd connection = null;
        JasperReport reporteJasper = null;
        JasperPrint print = null;

        try {
            connection = new ConexionBd(ConexionBd.getJndi_Sid_Desarrollo());
            String reportName = "REPORTE";
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
            LOGGER.info("setContenido");
        } catch (JRException e) {
            LOGGER.warn(e);
            throw new RuntimeException("It's not possible to generate the pdf report.", e);
        } catch (FileNotFoundException e) {
            LOGGER.warn(e);
            throw new RuntimeException("It's not possible to generate the pdf report.", e);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(ReporteUnidadLegal.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                try {
                    connection.closeConexionBd();
                } catch (Exception e) {
                }
            }
        }

        LOGGER.info("Fin");
    }

    public void generarReporteUnidadLegalPdf(String urlArchivo, String nombreArchivo, Long idOrganizacion) {

        LOGGER.info(urlArchivo);
        LOGGER.info(nombreArchivo);
        ConexionBd connection = null;
        JasperReport reporteJasper = null;
        JasperPrint print = null;

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
            LOGGER.info("setContenido");
        } catch (JRException e) {
            LOGGER.warn(e);
            throw new RuntimeException("It's not possible to generate the pdf report.", e);
        } catch (FileNotFoundException e) {
            LOGGER.warn(e);
            throw new RuntimeException("It's not possible to generate the pdf report.", e);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(ReporteUnidadLegal.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                try {
                    connection.closeConexionBd();
                } catch (Exception e) {
                }
            }
        }
        LOGGER.info("Fin");
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

}
