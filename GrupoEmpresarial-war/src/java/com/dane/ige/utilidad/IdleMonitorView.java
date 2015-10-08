package com.dane.ige.utilidad;

import com.dane.ige.seguridad.Login;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author SRojasM
 */
@ManagedBean
public class IdleMonitorView {
     
    public void onIdle() {
        Mensaje.agregarMensajeGrowlInfo("Sin actividad", "Hay alguien ahí!");
        Login login = new Login();
        login.logout();
    }
 
    public void onActive() {
        Mensaje.agregarMensajeGrowlInfo("Bienvenido de regreso", "Continua trabajando.");
    }
}