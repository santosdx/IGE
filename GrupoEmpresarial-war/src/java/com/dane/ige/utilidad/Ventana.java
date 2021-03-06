package com.dane.ige.utilidad;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import org.primefaces.context.RequestContext;

/**
 * Clase que administra los métodos y propiedades de una ventana modal.
 *
 * @author srojasm
 */
@ManagedBean(name = "dfView")
public class Ventana implements Serializable {

    /**
     * Método que permite visualizar un componente en una ventana modal.
     *
     * @param componente
     */
    public void visualizarVentana(String componente) {
        RequestContext.getCurrentInstance().openDialog(componente);
    }

    /**
     * Método que permite visualizar un componente en una ventana modal, pasando
     * los parametros de modal, dragable, resizable y la altitura.
     *
     * @param componente
     * @param modal
     * @param dragable
     * @param resizable
     * @param alto
     * @param ancho
     */
    public void visualizarVentanaParametrizada(String componente, boolean modal, boolean dragable, boolean resizable, Long alto, Long ancho) {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", modal);
        options.put("draggable", dragable);
        options.put("resizable", resizable);
        options.put("contentHeight", alto);
        options.put("contentWidth", ancho);
        RequestContext.getCurrentInstance().openDialog(componente, options, null);
    }

    /**
     * Función que permite cerrar la ventana modal parametrizada
     *
     * @param componente
     */
    public void cerrarVentanaParametrizada(String componente) {
        RequestContext.getCurrentInstance().closeDialog(componente);
    }
}
