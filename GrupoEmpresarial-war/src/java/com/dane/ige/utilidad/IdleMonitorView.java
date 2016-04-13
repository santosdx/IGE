package com.dane.ige.utilidad;

import com.dane.ige.seguridad.Login;
import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Clase que administra los metodos y atributos para el funcionamiento del
 * componente de IdleMonitor de la vista.
 *
 * @author SRojasM
 */
@ManagedBean
@ViewScoped
public class IdleMonitorView implements Serializable {

    private int tiempoInactivo = 0;
    private int horaIniciaInactividad = 0;
    private int minutosIniciaInactividad = 0;
    private int minutosCerrarSesion = 5;

    /**
     * Funcion que se dispara por el evento "idle" del p:idleMonitor y que
     * permite identificar cuando el usuario ha estado inactivo en el aplicativo
     * por el maximo tiempo de sesión menos 5 minutos.
     *
     * @param tiempo
     */
    public void onIdle(Long tiempo) {
        tiempoInactivo = Integer.parseInt(tiempo / 60 + "");
        Calendar fechaHora = new GregorianCalendar();
        setHoraIniciaInactividad(fechaHora.getTime().getHours());
        setMinutosIniciaInactividad(fechaHora.getTime().getMinutes());
        //Mensaje.agregarMensajeGrowlInfo("Sin actividad", "Hay alguien ahí!");
    }

    /**
     * Funcion que se dispara por el evento "active" del p:idleMonitor y que
     * permite identificar cuando el usuario realizo una actividad en el
     * aplicativo, eliminando asi la inactividad
     */
    public void onActive() {
        Mensaje.agregarMensajeGrowlInfo("Bienvenido de regreso", "Continua trabajando.");
    }

    private int intervaloPoll = 1;

    /**
     * Función que permite evaluar cada 60 sg (cada minuto) si han pasado los 5
     * minutos antes de cerrar la sesión. Este evento lo dispara el componente
     * Poll de la vista y es cancelado por el evento "active" del p:idleMonitor
     * mediante la funcion onActive()
     */
    public void intervaloPollCerrarSesion() {
        //System.out.println("Tiempo restante:" + minutosCerrarSesion);
        if (5 == intervaloPoll) {
            Login login = new Login();
            login.logout();
        }
        minutosCerrarSesion--;
        intervaloPoll++;
    }

    public int getHoraIniciaInactividad() {
        return horaIniciaInactividad;
    }

    public void setHoraIniciaInactividad(int horaIniciaInactividad) {
        this.horaIniciaInactividad = horaIniciaInactividad;
    }

    public int getMinutosIniciaInactividad() {
        return minutosIniciaInactividad;
    }

    public void setMinutosIniciaInactividad(int minutosIniciaInactividad) {
        this.minutosIniciaInactividad = minutosIniciaInactividad;
    }

    public int getMinutosCerrarSesion() {
        return minutosCerrarSesion;
    }

    public int getTiempoInactivo() {
        return tiempoInactivo;
    }

}
