package com.dane.ige.convertidor;

import com.dane.ige.administracion.AdministrarPermiso;
import com.dane.ige.modelo.entidad.Permiso;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 * Clase que implementa Converter, para el manejo de los objetos en el
 * componente de Permiso.
 *
 * @author srojasm
 */
@FacesConverter(value = "permisoConverter")
public class PermisoConvertidor implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if (value != null && value.trim().length() > 0) {
            try {
                //Map<String, Object> viewMap =  FacesContext.getCurrentInstance().getViewRoot().getViewMap();
                //AdministrarPermiso service = (AdministrarPermiso) viewMap.get("MbAdministrarPermiso");                
                FacesContext context = FacesContext.getCurrentInstance();
                AdministrarPermiso service = (AdministrarPermiso) context.getApplication().evaluateExpressionGet(context, "#{MbAdministrarPermiso}", AdministrarPermiso.class);
                return service.getPermisoById(Integer.parseInt(value));
                // Implementación para Session
                //AdministrarPermiso service = (AdministrarPermiso) fc.getExternalContext().getSessionMap().get("MbAdministrarPermiso");
                //return service.getPermisoById(Integer.parseInt(value));                
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid list."));
            }
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if (o != null && !o.equals("")) {
            return String.valueOf(((Permiso) o).getId());
        } else {
            return null;
        }
    }

}
