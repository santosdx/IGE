package com.dane.ige.convertidor;

import com.dane.ige.modelo.entidad.BodegaIdentificacion;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 * Clase que implementa Converter, para el manejo de los objetos en el
 * componente de BodegaIdentificacion.
 *
 * @author srojasm
 */
@FacesConverter(value = "identificacionConverter")
public class IdentificacionConvertidor implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                List<BodegaIdentificacion> lista = (List<BodegaIdentificacion>) uic.getAttributes().get("listaItem");
                for (BodegaIdentificacion bodegaIdentificacion : lista) {
                    String llaveCompuesta = bodegaIdentificacion.getId().getId() +""+bodegaIdentificacion.getId().getFecha();
                    if(llaveCompuesta.equals(value)){
                        return bodegaIdentificacion;
                    }
                }
                return null;
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid list."));
            }
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o != null && !o.equals("")) {
            //Se crea una llave compuesta entre el ID y la fecha de actualizacion
            return String.valueOf(((BodegaIdentificacion) o).getId().getId()+""+((BodegaIdentificacion) o).getId().getFecha());
        } else {
            return null;
        }
    }

}
