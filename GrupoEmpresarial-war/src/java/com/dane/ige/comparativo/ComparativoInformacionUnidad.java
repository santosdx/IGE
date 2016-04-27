package com.dane.ige.comparativo;

import com.dane.ige.dto.GrupoEmpresa;
import com.dane.ige.modelo.entidad.BodegaIdentificacion;
import com.dane.ige.modelo.entidad.BodegaNovedad;
import com.dane.ige.modelo.entidad.BodegaRelacion;
import com.dane.ige.modelo.entidad.BodegaTamano;
import com.dane.ige.modelo.local.administracion.BodegaIdentificacionFacadeLocal;
import com.dane.ige.modelo.local.administracion.BodegaNovedadFacadeLocal;
import com.dane.ige.modelo.local.administracion.BodegaRelacionFacadeLocal;
import com.dane.ige.modelo.local.administracion.BodegaTamanoFacadeLocal;
import com.dane.ige.utilidad.Fecha;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;

/**
 * Clase que contiene las funciones y atributos para el funcionamient del
 * proceso de comparación de los periodos de actualización de la unidad de Grupo
 * Empresarial, Unidad Legal y Establecimiento.
 *
 * @author SRojasM
 */
@ManagedBean(name = "MbComparativoInformacionUnidad")
@SessionScoped
public class ComparativoInformacionUnidad implements Serializable {

    @EJB
    private BodegaIdentificacionFacadeLocal eJBServicioBodegaIdentificacion;
    @EJB
    private BodegaRelacionFacadeLocal eJBServicioBodegaRelacion;
    @EJB
    private BodegaNovedadFacadeLocal eJBServicioBodegaNovedad;
    @EJB
    private BodegaTamanoFacadeLocal eJBServicioBodegaTamano;

    private List<BodegaIdentificacion> listaIdentificacionGrupo;
    private Long idIdentificacionGrupoSeleccionada;
    private List<BodegaIdentificacion> listaIdentificacionUnidadLegal;
    private Long idIdentificacionUnidadLegalSeleccionada;
    private List<BodegaIdentificacion> listaIdentificacionEstablecimiento;
    private Long idIdentificacionEstablecimientoSeleccionado;

    private List<BodegaIdentificacion> listaPeriodosActualizacion;
    private List<BodegaIdentificacion> listaPeriodosActualizacionSeleccionados;
    private List<GrupoEmpresa> listaGrupoEmpresaComparar;

    /**
     * Creates a new instance of ComparativoInformacionUnidad
     */
    public ComparativoInformacionUnidad() {
    }

    @PostConstruct
    public void init() {
        setListaIdentificacionGrupo(eJBServicioBodegaIdentificacion.obtenerListaIdentificacionTodosLosGrupos());
    }

    /**
     * Método listener que se ejecuta cuando en el componente de lista que
     * almacena los grupo empresariales, se selecciona un item.
     *
     * @param event
     */
    public void mySelectionMethodListenerGrupo(AjaxBehaviorEvent event) {
        setListaIdentificacionUnidadLegal(null);
        setListaIdentificacionEstablecimiento(null);
        if (getIdIdentificacionGrupoSeleccionada() != null) {
            setListaIdentificacionUnidadLegal(eJBServicioBodegaIdentificacion.obtenerListaIdentificacionUnidadLegalByIdGrupoRelacionadoTipoOrganizacion(getIdIdentificacionGrupoSeleccionada()));
        }
    }

    /**
     * Método listener que se ejecuta cuando en el componente de lista que
     * almacena las unidades legales, se selecciona un item.
     *
     * @param event
     */
    public void mySelectionMethodListenerUnidadLegal(AjaxBehaviorEvent event) {
        setListaIdentificacionEstablecimiento(null);
        if (getIdIdentificacionUnidadLegalSeleccionada() != null) {
            setListaIdentificacionEstablecimiento(eJBServicioBodegaIdentificacion.obtenerListaIdentificacionEstablecimientoByIdGrupoRelacionadoTipoOrganizacion(getIdIdentificacionUnidadLegalSeleccionada()));
        }
    }

    /**
     * Método listener que se ejecuta cuando en el componente de lista que
     * almacena los establecimientos, se selecciona un item.
     *
     * @param event
     */
    public void mySelectionMethodListenerEstablecimiento(AjaxBehaviorEvent event) {
    }

    /**
     * Método que permite consultar el listado de los periodos e actualización
     * para un grupo empresarial, pasando como parametro el ID
     */
    public void consultarPeriodosActualizacionGrupo() {
        if (getIdIdentificacionGrupoSeleccionada() != null) {
            setListaPeriodosActualizacion(eJBServicioBodegaIdentificacion.obtenerListaPeriodoActualizadoUnidadByIdUnidad(getIdIdentificacionGrupoSeleccionada()));
        }
    }

    /**
     * Método que permite consultar el listado de los periodos e actualización
     * para una unidad legal, pasando como parametro el ID
     */
    public void consultarPeriodosActualizacionUnidadLegal() {
        if (getIdIdentificacionUnidadLegalSeleccionada() != null) {
            setListaPeriodosActualizacion(eJBServicioBodegaIdentificacion.obtenerListaPeriodoActualizadoUnidadByIdUnidad(getIdIdentificacionUnidadLegalSeleccionada()));
        }
    }

    /**
     * Método que permite consultar el listado de los periodos e actualización
     * para un establecimiento, pasando como parametro el ID
     */
    public void consultarPeriodosActualizacionEstablecimiento() {
        if (getIdIdentificacionEstablecimientoSeleccionado() != null) {
            setListaPeriodosActualizacion(eJBServicioBodegaIdentificacion.obtenerListaPeriodoActualizadoUnidadByIdUnidad(getIdIdentificacionEstablecimientoSeleccionado()));
        }
    }

    /**
     * Método listener que se ejecuta cuando en el componente de lista que
     * almacena los registros de los periodos de actualización de una unidad, se
     * selecciona un item.
     *
     */
    public void seleccionPeriodoActualizacion() {
        /*if(getListaPeriodosActualizacionSeleccionados() != null && getListaPeriodosActualizacionSeleccionados().size() <=2){
            
         }else{
         Mensaje.agregarMensajeGrowlWarn("Atención!", "La comparación se realiza solo entre dos periodos.");
         }*/
    }

    /**
     * Método que se utiliza para lanzar las funciones de consulta a la base de
     * datos de los registros de identificación, relacion, novedad y tamaño para
     * los registros de los periodos de actualización seleccionados.
     */
    public void lanzarProcesoComparacion() {
        String llaveCompuesta = "";

        setListaGrupoEmpresaComparar(new ArrayList<GrupoEmpresa>());
        for (BodegaIdentificacion bodegaIdentificacion : getListaPeriodosActualizacionSeleccionados()) {
            String fecha = Fecha.formatFechaDateToStringOtherFormat(bodegaIdentificacion.getId().getFecha(), "dd/MM/yyyy HH:mm:ss");
            llaveCompuesta = "'" + bodegaIdentificacion.getId().getId() + fecha + "'";
            System.out.println("Llave:" + llaveCompuesta);

            BodegaIdentificacion identificacion = eJBServicioBodegaIdentificacion.obtenerRegistroByLlaveCompuesta(llaveCompuesta);
            BodegaRelacion relacion = eJBServicioBodegaRelacion.obtenerRegistroByLlaveCompuesta(llaveCompuesta);
            BodegaNovedad novedad = eJBServicioBodegaNovedad.obtenerRegistroByLlaveCompuesta(llaveCompuesta);
            BodegaTamano tamano = eJBServicioBodegaTamano.obtenerRegistroByLlaveCompuesta(llaveCompuesta);

            GrupoEmpresa grupo = new GrupoEmpresa(identificacion, relacion, novedad, tamano);

            if (identificacion != null && relacion != null && novedad != null && tamano != null) {
                getListaGrupoEmpresaComparar().add(grupo);
            }
        }

    }

    // Metodos Sett y Gett
    public List<BodegaIdentificacion> getListaIdentificacionGrupo() {
        return listaIdentificacionGrupo;
    }

    public void setListaIdentificacionGrupo(List<BodegaIdentificacion> listaIdentificacionGrupo) {
        this.listaIdentificacionGrupo = listaIdentificacionGrupo;
    }

    public Long getIdIdentificacionGrupoSeleccionada() {
        return idIdentificacionGrupoSeleccionada;
    }

    public void setIdIdentificacionGrupoSeleccionada(Long idIdentificacionGrupoSeleccionada) {
        this.idIdentificacionGrupoSeleccionada = idIdentificacionGrupoSeleccionada;
    }

    public List<BodegaIdentificacion> getListaIdentificacionUnidadLegal() {
        return listaIdentificacionUnidadLegal;
    }

    public void setListaIdentificacionUnidadLegal(List<BodegaIdentificacion> listaIdentificacionUnidadLegal) {
        this.listaIdentificacionUnidadLegal = listaIdentificacionUnidadLegal;
    }

    public Long getIdIdentificacionUnidadLegalSeleccionada() {
        return idIdentificacionUnidadLegalSeleccionada;
    }

    public void setIdIdentificacionUnidadLegalSeleccionada(Long idIdentificacionUnidadLegalSeleccionada) {
        this.idIdentificacionUnidadLegalSeleccionada = idIdentificacionUnidadLegalSeleccionada;
    }

    public List<BodegaIdentificacion> getListaIdentificacionEstablecimiento() {
        return listaIdentificacionEstablecimiento;
    }

    public void setListaIdentificacionEstablecimiento(List<BodegaIdentificacion> listaIdentificacionEstablecimiento) {
        this.listaIdentificacionEstablecimiento = listaIdentificacionEstablecimiento;
    }

    public Long getIdIdentificacionEstablecimientoSeleccionado() {
        return idIdentificacionEstablecimientoSeleccionado;
    }

    public void setIdIdentificacionEstablecimientoSeleccionado(Long idIdentificacionEstablecimientoSeleccionado) {
        this.idIdentificacionEstablecimientoSeleccionado = idIdentificacionEstablecimientoSeleccionado;
    }

    public List<BodegaIdentificacion> getListaPeriodosActualizacion() {
        return listaPeriodosActualizacion;
    }

    public void setListaPeriodosActualizacion(List<BodegaIdentificacion> listaPeriodosActualizacion) {
        this.listaPeriodosActualizacion = listaPeriodosActualizacion;
    }

    public List<BodegaIdentificacion> getListaPeriodosActualizacionSeleccionados() {
        return listaPeriodosActualizacionSeleccionados;
    }

    public void setListaPeriodosActualizacionSeleccionados(List<BodegaIdentificacion> listaPeriodosActualizacionSeleccionados) {
        this.listaPeriodosActualizacionSeleccionados = listaPeriodosActualizacionSeleccionados;
    }

    public List<GrupoEmpresa> getListaGrupoEmpresaComparar() {
        return listaGrupoEmpresaComparar;
    }

    public void setListaGrupoEmpresaComparar(List<GrupoEmpresa> listaGrupoEmpresaComparar) {
        this.listaGrupoEmpresaComparar = listaGrupoEmpresaComparar;
    }

}
