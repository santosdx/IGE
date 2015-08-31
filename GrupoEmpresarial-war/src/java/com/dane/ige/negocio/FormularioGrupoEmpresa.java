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
import com.dane.ige.utilidad.Mensaje;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
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
        Long id = Long.parseLong(getServicioLogin().getUsuarioLogueado().getIdIdentificacion() + "");

        setIdentificacionSeleccionada(geteJBServicioBodegaIdentificacion().obtenerIdentificacionByIdTipoOrganizacion(id, "GRUPO"));
        setRelacionSeleccionada(geteJBServicioBodegaRelacion().obtenerRelacionGrupoEmpresaById(id));
        setNovedadSeleccionada(geteJBServicioBodegaNovedad().obtenerNovedadGrupoEmpresaById(id));
        setTamanoSeleccionado(geteJBServicioBodegaTamano().obtenerTamanoGrupoEmpresaById(id));
    }

    /**
     * Método que permite insertar un nuevo registro en la bodega de datos, para
     * las tablas de identificacion, relacion, novedad y tamaño del grupo
     * empresarial.
     */
    public String actualizarGrupoEmpresa() {
        Date fechaActualizacion = new Date();

        getIdentificacionSeleccionada().getId().setFecha(fechaActualizacion);
        getIdentificacionSeleccionada().setPersonaActualiza(getServicioLogin().getUsuarioLogueado().getNombres() + getServicioLogin().getUsuarioLogueado().getApellidos());
        getIdentificacionSeleccionada().setOrigenActualizacion("Formulario Web");
        geteJBServicioBodegaIdentificacion().create(identificacionSeleccionada);

        getRelacionSeleccionada().getId().setFecha(fechaActualizacion);
        geteJBServicioBodegaRelacion().create(relacionSeleccionada);

        getNovedadSeleccionada().getId().setFecha(fechaActualizacion);
        geteJBServicioBodegaNovedad().create(novedadSeleccionada);

        getTamanoSeleccionado().getId().setFecha(fechaActualizacion);
        geteJBServicioBodegaTamano().create(tamanoSeleccionado);

        Mensaje.agregarMensajeGrowlInfo("Exito!", "Información actualizada del grupo empresarial.");

        return "/interfaz/grupo-empresa/itz-informe-grupo-empresa.xhtml";
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

    public void testSelected(AjaxBehaviorEvent event){
        
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
