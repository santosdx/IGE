package com.dane.ige.utilidad;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.log4j.Logger;

/**
 * Clase que administra los métodos para el comportamiento de subir archivos.
 *
 * @author SRojasM
 */
public class FileUpload {

    final static Logger LOGGER = Logger.getLogger(FileUpload.class);

    public FileUpload() {
    }

    /**
     * Método que permite subir un documento en una ruta expecifica.
     * @param pathAlmacenar
     * @param nombreArchivo
     * @param documento
     * @return 
     */
    public boolean subirDocumento(String pathAlmacenar, String nombreArchivo, InputStream documento) {
        boolean resultado = false;
        try {
            OutputStream out = new FileOutputStream(new File(pathAlmacenar, nombreArchivo));
            @SuppressWarnings("UnusedAssignment")
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = documento.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            documento.close();
            out.flush();
            out.close();
            resultado = true;
        } catch (IOException e) {
            LOGGER.warn(e);
        }
        return resultado;
    }
}
