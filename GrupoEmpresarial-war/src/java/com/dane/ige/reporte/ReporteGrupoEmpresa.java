package com.dane.ige.reporte;

import com.dane.ige.modelo.connection.ConexionBd;
import com.dane.ige.seguridad.Login;
import com.dane.ige.utilidad.Fecha;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
@ManagedBean(name = "MbReporteGrupoEmpresa")
@SessionScoped
public class ReporteGrupoEmpresa implements Serializable {

    final static Logger LOGGER = Logger.getLogger(ReporteGrupoEmpresa.class);

    private StreamedContent contenido;
    private String urlArchivoTemporal;
    private String nombreArchivoTemporal;

    @ManagedProperty("#{MbLogin}")
    private Login servicioLogin;

    public ReporteGrupoEmpresa() {
    }

    public void generarReportePdf(String urlArchivo, String nombreArchivo) {

        LOGGER.info(urlArchivo);
        LOGGER.info(nombreArchivo);
        ConexionBd connection = null;
        JasperReport reporteJasper = null;
        JasperPrint print = null;

        try {
            connection = new ConexionBd(ConexionBd.getJndi_Sid_Desarrollo());
            setNombreArchivoTemporal("Reporte Grupo Empresa_");
            File temp = File.createTempFile(getNombreArchivoTemporal(), ".pdf");
            setUrlArchivoTemporal(temp.getAbsolutePath());

            Map parameters = new HashMap();
            parameters.put("id_grupo", getServicioLogin().getUsuarioLogueado().getIdIdentificacion());

            reporteJasper = JasperCompileManager.compileReport(urlArchivo + nombreArchivo);
            print = JasperFillManager.fillReport(reporteJasper, parameters, connection.getConexion());
            JRExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, new FileOutputStream(temp)); // your output goes here
            exporter.exportReport();

            setContenido(new DefaultStreamedContent(new FileInputStream(temp), "application/pdf", getNombreArchivoTemporal() + ".pdf"));
            temp.delete();
            temp.deleteOnExit();
            LOGGER.info("setContenido");
        } catch (JRException e) {
            LOGGER.warn(e);
            //throw new RuntimeException("It's not possible to generate the pdf report.", e);
        } catch (FileNotFoundException e) {
            LOGGER.warn(e);
            //throw new RuntimeException("It's not possible to generate the pdf report.", e);
        } catch (IOException ex) {
            //java.util.logging.Logger.getLogger(ReporteGrupoEmpresa.class.getName()).log(Level.SEVERE, null, ex);
            LOGGER.warn(ex);
        } finally {
            if (connection != null) {
                try {
                    connection.closeConexionBd();
                } catch (Exception e) {
                    LOGGER.warn(e);
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

    public String getUrlArchivoTemporal() {
        return urlArchivoTemporal;
    }

    public void setUrlArchivoTemporal(String urlArchivoTemporal) {
        this.urlArchivoTemporal = urlArchivoTemporal;
    }

    public String getNombreArchivoTemporal() {
        return nombreArchivoTemporal;
    }

    public void setNombreArchivoTemporal(String nombreArchivoTemporal) {
        this.nombreArchivoTemporal = nombreArchivoTemporal;
    }

    public Login getServicioLogin() {
        return servicioLogin;
    }

    public void setServicioLogin(Login servicioLogin) {
        this.servicioLogin = servicioLogin;
    }

}
