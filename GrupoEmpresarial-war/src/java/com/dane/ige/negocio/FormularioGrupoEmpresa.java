package com.dane.ige.negocio;

import com.dane.ige.modelo.entidad.BodegaIdentificacion;
import com.dane.ige.modelo.entidad.BodegaNovedad;
import com.dane.ige.modelo.entidad.BodegaRelacion;
import com.dane.ige.modelo.entidad.BodegaTamano;
import com.dane.ige.modelo.local.administracion.BodegaIdentificacionFacadeLocal;
import com.dane.ige.modelo.local.administracion.BodegaNovedadFacadeLocal;
import com.dane.ige.modelo.local.administracion.BodegaRelacionFacadeLocal;
import com.dane.ige.modelo.local.administracion.BodegaTamanoFacadeLocal;
import com.dane.ige.seguridad.Login;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;
import org.primefaces.event.FlowEvent;

/**
 * Clase que permite administrar los procesos de actualización del formulario
 * Grupo Empresarial.
 *
 * @author SRojasM
 */
@ManagedBean(name = "MbFormGrupoEmpresa")
@ViewScoped
public class FormularioGrupoEmpresa implements Serializable {

    final static Logger LOGGER = Logger.getLogger(FormularioGrupoEmpresa.class);

    @EJB
    private BodegaIdentificacionFacadeLocal eJBServicioBodegaIdentificacion;
    @EJB
    private BodegaNovedadFacadeLocal eJBServicioBodegaNovedad;
    @EJB
    private BodegaTamanoFacadeLocal eJBServicioBodegaTamano;
    @EJB
    private BodegaRelacionFacadeLocal eJBServicioBodegaRelacion;

    @ManagedProperty("#{MbLogin}")
    private Login servicioLogin;

    private BodegaIdentificacion identificacionSeleccionada;
    private BodegaNovedad novedadSeleccionada;
    private BodegaRelacion relacionSeleccionada;
    private BodegaTamano tamanoSeleccionado;

    public FormularioGrupoEmpresa() {
    }

    @PostConstruct
    public void init() {
        setIdentificacionSeleccionada(geteJBServicioBodegaIdentificacion().obtenerIdentificacionGrupoEmpresaById(getServicioLogin().getUsuarioLogueado().getIdIdentificacion()));
        setNovedadSeleccionada(geteJBServicioBodegaNovedad().obtenerNovedadGrupoEmpresaById(getServicioLogin().getUsuarioLogueado().getIdIdentificacion()));
        setRelacionSeleccionada(geteJBServicioBodegaRelacion().obtenerRelacionGrupoEmpresaById(getServicioLogin().getUsuarioLogueado().getIdIdentificacion()));
        setTamanoSeleccionado(geteJBServicioBodegaTamano().obtenerTamanoGrupoEmpresaById(getServicioLogin().getUsuarioLogueado().getIdIdentificacion()));
    }

    private boolean skip;

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        } else {
            return event.getNewStep();
        }
    }

    //Métodos Set y Get de la clase.
    public BodegaIdentificacionFacadeLocal geteJBServicioBodegaIdentificacion() {
        return eJBServicioBodegaIdentificacion;
    }

    public void seteJBServicioBodegaIdentificacion(BodegaIdentificacionFacadeLocal eJBServicioBodegaIdentificacion) {
        this.eJBServicioBodegaIdentificacion = eJBServicioBodegaIdentificacion;
    }

    public BodegaNovedadFacadeLocal geteJBServicioBodegaNovedad() {
        return eJBServicioBodegaNovedad;
    }

    public void seteJBServicioBodegaNovedad(BodegaNovedadFacadeLocal eJBServicioBodegaNovedad) {
        this.eJBServicioBodegaNovedad = eJBServicioBodegaNovedad;
    }

    public BodegaTamanoFacadeLocal geteJBServicioBodegaTamano() {
        return eJBServicioBodegaTamano;
    }

    public void seteJBServicioBodegaTamano(BodegaTamanoFacadeLocal eJBServicioBodegaTamano) {
        this.eJBServicioBodegaTamano = eJBServicioBodegaTamano;
    }

    public BodegaRelacionFacadeLocal geteJBServicioBodegaRelacion() {
        return eJBServicioBodegaRelacion;
    }

    public void seteJBServicioBodegaRelacion(BodegaRelacionFacadeLocal eJBServicioBodegaRelacion) {
        this.eJBServicioBodegaRelacion = eJBServicioBodegaRelacion;
    }

    public Login getServicioLogin() {
        return servicioLogin;
    }

    public void setServicioLogin(Login servicioLogin) {
        this.servicioLogin = servicioLogin;
    }

    public BodegaIdentificacion getIdentificacionSeleccionada() {
        return identificacionSeleccionada;
    }

    public void setIdentificacionSeleccionada(BodegaIdentificacion identificacionSeleccionada) {
        this.identificacionSeleccionada = identificacionSeleccionada;
    }

    public BodegaNovedad getNovedadSeleccionada() {
        return novedadSeleccionada;
    }

    public void setNovedadSeleccionada(BodegaNovedad novedadSeleccionada) {
        this.novedadSeleccionada = novedadSeleccionada;
    }

    public BodegaRelacion getRelacionSeleccionada() {
        return relacionSeleccionada;
    }

    public void setRelacionSeleccionada(BodegaRelacion relacionSeleccionada) {
        this.relacionSeleccionada = relacionSeleccionada;
    }

    public BodegaTamano getTamanoSeleccionado() {
        return tamanoSeleccionado;
    }

    public void setTamanoSeleccionado(BodegaTamano tamanoSeleccionado) {
        this.tamanoSeleccionado = tamanoSeleccionado;
    }

}
