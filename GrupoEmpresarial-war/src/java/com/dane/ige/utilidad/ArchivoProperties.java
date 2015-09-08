package com.dane.ige.utilidad;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * Clase que gestiona los archivos de propiedades.
 *
 * @author SRojasM
 */
public class ArchivoProperties {

    final static Logger LOGGER = Logger.getLogger(ArchivoProperties.class);

    public ArchivoProperties() {
    }

    /**
     * Método que permite cargar un archivo de propiedades que esta en la raiz y
     * obtener el valor de una propiedad a partir de su llave.
     *
     * @param propiedad
     * @return
     */
    public static String obtenerPropertieFilePathProperties(String propiedad) {
        String resultado = null;
        Properties prop = new Properties();
        InputStream input = null;
        String fileName = "path_windows.properties";
        //Definir el Sistema operativo
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            //System.out.println("El sistema operativo es Windows");
            fileName = "path_windows.properties";
        } else if (os.contains("nix") || os.contains("nux")) {
            //System.out.println("El sistema operativo es linux");
            fileName = "path_unix.properties";
        }
        try {

            input = ArchivoProperties.class.getClassLoader().getResourceAsStream(fileName);
            if (input == null) {
                LOGGER.warn("No se encuentra el archivo " + fileName);
            } else {
                //load a properties file
                prop.load(input);
                //get the property value and print in out
                resultado = prop.getProperty(propiedad);
                //System.out.println(resultado);
            }
        } catch (FileNotFoundException ex) {
            LOGGER.warn("Line[71]" + ex);
        } catch (IOException ex) {
            LOGGER.warn("Line[73]" + ex);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ex) {
                    LOGGER.warn("Line[79]" + ex);
                }
            }
        }
        return resultado;
    }
}
