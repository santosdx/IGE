package com.dane.ige.utilidad;

import com.dane.ige.excel.LeerExcel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import org.apache.commons.lang3.StringUtils;

/**
 * Clase que permite administrar las operaciónes del atributo fecha.
 *
 * @author SRojasM
 */
public class Fecha {

    public Fecha() {
    }

    /**
     * Método que recibe una fecha en String y se convierte a Date con el
     * formato: dd/MM/yyyy
     *
     * @param fecha
     * @return
     */
    public static Date fomatoFechaStringToDate(String fecha) {
        Date resultado = null;
        SimpleDateFormat sdfDateIn = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        SimpleDateFormat sdfDateOut = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        if (!StringUtils.isBlank(fecha)) {
            try {
                date = sdfDateIn.parse(fecha);
                resultado = sdfDateOut.parse(formatFechaDateToString(date));
            } catch (ParseException ex) {
                java.util.logging.Logger.getLogger(LeerExcel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resultado;
    }

    /**
     * *
     * Método que recibe una fecha en Date y se convierte en String con el
     * formato: dd/MM/yyyy
     *
     * @param fecha
     * @return
     */
    public static String formatFechaDateToString(Date fecha) {
        String resultado = null;
        if (fecha != null) {
            SimpleDateFormat sdfFecha = new SimpleDateFormat("dd/MM/yyyy");
            resultado = sdfFecha.format(fecha);
        }
        return resultado;
    }

}
