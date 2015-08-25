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
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import org.apache.log4j.Logger;
import org.primefaces.event.FlowEvent;

/**
 * Clase que permite administrar los procesos de actualización del formulario
 * Establecimiento.
 *
 * @author SRojasM
 */
@ManagedBean(name = "MbFormEstablecimiento")
@SessionScoped
public class FormularioEstablecimiento implements Serializable {

    final static Logger LOGGER = Logger.getLogger(FormularioEstablecimiento.class);

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

    private List<BodegaIdentificacion> listaIdentificacionUnidadLegal;
    private Long idIdentificacionSeleccionada;
    private BodegaIdentificacion identificacionUnidadLegalSeleccionada;

    private List<BodegaIdentificacion> listaIdentificacion;

    public FormularioEstablecimiento() {
    }

    @PostConstruct
    public void init() {
        Long id = Long.parseLong(getServicioLogin().getUsuarioLogueado().getIdIdentificacion() + "");
        setListaIdentificacionUnidadLegal(geteJBServicioBodegaIdentificacion().obtenerListaIdentificacionUnidadLegalByIdGrupoRelacionadoTipoOrganizacion(id));
    }

    public void mySelectionMethodListener(AjaxBehaviorEvent event) {
        setListaIdentificacion(geteJBServicioBodegaIdentificacion().obtenerListaIdentificacionEstablecimientoByIdGrupoRelacionadoTipoOrganizacion(getIdIdentificacionSeleccionada()));
    }

    /**
     * Método que permite seleccionar un establecimietno y abrir la pagina del
     * formulario con los datos prediligenciados con el establecimiento
     * seleccionado.
     *
     * @param idOrganizacion
     * @return
     */
    public String abrirFormularioActualizarDatos(Long idOrganizacion) {
        setIdentificacionSeleccionada(geteJBServicioBodegaIdentificacion().obtenerIdentificacionByIdTipoOrganizacion(idOrganizacion, "ESTABLECIMIENTO"));
        setRelacionSeleccionada(geteJBServicioBodegaRelacion().obtenerRelacionGrupoEmpresaById(idOrganizacion));
        setNovedadSeleccionada(geteJBServicioBodegaNovedad().obtenerNovedadGrupoEmpresaById(idOrganizacion));
        setTamanoSeleccionado(geteJBServicioBodegaTamano().obtenerTamanoGrupoEmpresaById(idOrganizacion));
        return "/interfaz/establecimiento/itz-formulario-establecimiento.xhtml";
    }

    /**
     * Método que permite insertar un nuevo registro en la bodega de datos, para
     * las tablas de identificacion, relacion, novedad y tamaño del
     * establecimiento.
     */
    public String actualizarEstablecimiento() {
        Date fechaActualizacion = new Date();

        getIdentificacionSeleccionada().getId().setFecha(fechaActualizacion);
        getIdentificacionSeleccionada().setPersonaActualiza(getServicioLogin().getUsuarioLogueado().getNombres() + getServicioLogin().getUsuarioLogueado().getApellidos());
        geteJBServicioBodegaIdentificacion().create(identificacionSeleccionada);

        getRelacionSeleccionada().getId().setFecha(fechaActualizacion);
        geteJBServicioBodegaRelacion().create(relacionSeleccionada);

        getNovedadSeleccionada().getId().setFecha(fechaActualizacion);
        geteJBServicioBodegaNovedad().create(novedadSeleccionada);

        getTamanoSeleccionado().getId().setFecha(fechaActualizacion);
        geteJBServicioBodegaTamano().create(tamanoSeleccionado);

        Mensaje.agregarMensajeGrowlInfo("Exito!", "Información actualizada del establecimiento.");

        return "/interfaz/establecimiento/itz-informe-establecimiento.xhtml";
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

    public void testSelected(AjaxBehaviorEvent event) {

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

    public List<BodegaIdentificacion> getListaIdentificacionUnidadLegal() {
        return listaIdentificacionUnidadLegal;
    }

    public void setListaIdentificacionUnidadLegal(List<BodegaIdentificacion> listaIdentificacionUnidadLegal) {
        this.listaIdentificacionUnidadLegal = listaIdentificacionUnidadLegal;
    }

    public BodegaIdentificacion getIdentificacionUnidadLegalSeleccionada() {
        return identificacionUnidadLegalSeleccionada;
    }

    public void setIdentificacionUnidadLegalSeleccionada(BodegaIdentificacion identificacionUnidadLegalSeleccionada) {
        this.identificacionUnidadLegalSeleccionada = identificacionUnidadLegalSeleccionada;
    }

    public List<BodegaIdentificacion> getListaIdentificacion() {
        return listaIdentificacion;
    }

    public void setListaIdentificacion(List<BodegaIdentificacion> listaIdentificacion) {
        this.listaIdentificacion = listaIdentificacion;
    }

    public Long getIdIdentificacionSeleccionada() {
        return idIdentificacionSeleccionada;
    }

    public void setIdIdentificacionSeleccionada(Long idIdentificacionSeleccionada) {
        this.idIdentificacionSeleccionada = idIdentificacionSeleccionada;
    }

}
