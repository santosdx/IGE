package com.dane.ige.utilidad;

import org.apache.commons.lang3.StringUtils;

/**
 * Clase que agrupa las implementaciones y/o funciones para los objetos de tipo
 * texto o cualquier cadena de String
 *
 * @author SRojasM
 */
public class Texto {

    /**
     * Constructor de clase
     */
    public Texto() {
    }

    /**
     * Método que recibe un parametro de tipo texto y retorna un blanco si:
     * StringUtils.isBlank(null) = true StringUtils.isBlank("") = true
     * StringUtils.isBlank(" ") = true En caso contrario retorna el dato
     *
     * @param dato
     * @return String
     */
    public static String blankText(String dato) {
        String resultado = "";
        resultado = StringUtils.isBlank(dato.trim()) == true ? "" : dato.trim();
        resultado = resultado.equals("null") == true ? "" : dato.trim();
        return resultado;
    }

}
