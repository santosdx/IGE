package com.dane.ige.utilidad;

import org.apache.log4j.Logger;

/**
 * Clase que permite administrar las operaciónes del atributo número.
 *
 * @author SRojasM
 */
public class Numero {

    final static Logger LOGGER = Logger.getLogger(Fecha.class);

    public Numero() {
    }

    /**
     * Método que recibe un valor numerico en String y le da un formato de
     * entero, sin coma, punto o exponente.
     *
     * @param numero
     * @return String
     */
    public static String formatoNumeroEntero(String numero) {
        String resultado = null;
        try {
            resultado = Double.valueOf(numero).longValue() + "";
        } catch (NumberFormatException nfEx) {
            resultado = numero;
            LOGGER.warn(nfEx);
        }
        return resultado;
    }
}
