package com.dane.ige.utilidad;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.activation.MimetypesFileTypeMap;
import javax.faces.bean.ManagedBean;
import org.apache.log4j.Logger;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean(name = "MbFileDownloadView")
public class FileDownload {

    final static Logger LOGGER = Logger.getLogger(FileUpload.class);

    public FileDownload() {

    }

    /**
     * Método que permite descargar un archivo a partir de su url (path) y nombre.
     * @param url
     * @param documento
     * @return 
     */
    public StreamedContent descargarFile(String url, String documento) {

        LOGGER.info("Url:" + url + " Doc:" + documento);

        StreamedContent arquivoXLS = null;

        File arquivo = new File(url + documento);
        String contentTypeFile = new MimetypesFileTypeMap().getContentType(arquivo);

        try {
            arquivoXLS = new DefaultStreamedContent(new FileInputStream(arquivo), contentTypeFile, documento);
            return arquivoXLS;
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            Mensaje.agregarMensajeGrowlError("Error descarga.", "No se puede descargar el archivo porque no existe o presenta error en el directorio de almacenamiento.");
            return null;
        }
    }

}
