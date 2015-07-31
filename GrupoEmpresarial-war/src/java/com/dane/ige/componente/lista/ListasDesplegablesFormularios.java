package com.dane.ige.componente.lista;

import com.dane.ige.modelo.entidad.Departamento;
import com.dane.ige.modelo.entidad.EstadoMatricula;
import com.dane.ige.modelo.entidad.Municipio;
import com.dane.ige.modelo.entidad.Pais;
import com.dane.ige.modelo.entidad.SituacionControl;
import com.dane.ige.modelo.entidad.TipoControl;
import com.dane.ige.modelo.entidad.TipoEmpresa;
import com.dane.ige.modelo.entidad.TipoEstablecimiento;
import com.dane.ige.modelo.entidad.TipoOrganizacion;
import com.dane.ige.modelo.local.administracion.DepartamentoFacadeLocal;
import com.dane.ige.modelo.local.administracion.EstadoMatriculaFacadeLocal;
import com.dane.ige.modelo.local.administracion.MunicipioFacadeLocal;
import com.dane.ige.modelo.local.administracion.PaisFacadeLocal;
import com.dane.ige.modelo.local.administracion.SituacionControlFacadeLocal;
import com.dane.ige.modelo.local.administracion.TipoControlFacadeLocal;
import com.dane.ige.modelo.local.administracion.TipoEmpresaFacadeLocal;
import com.dane.ige.modelo.local.administracion.TipoEstablecimientoFacadeLocal;
import com.dane.ige.modelo.local.administracion.TipoOrganizacionFacadeLocal;
import com.dane.ige.negocio.FormularioGrupoEmpresa;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import org.apache.log4j.Logger;

/**
 * Clase que administra los servicios de consulta de todas las listas
 * desplegables para los formularios.
 *
 * @author SRojasM
 */
@ManagedBean(name = "MbListaDesplegable")
@ViewScoped
public class ListasDesplegablesFormularios {

    final static Logger LOGGER = Logger.getLogger(FormularioGrupoEmpresa.class);

    @EJB
    private PaisFacadeLocal eJBServicioPais;
    @EJB
    private DepartamentoFacadeLocal eJBServicioDepartamento;
    @EJB
    private EstadoMatriculaFacadeLocal eJBServicioMatricula;
    @EJB
    private MunicipioFacadeLocal eJBServicioMunicipio;
    @EJB
    private SituacionControlFacadeLocal eJBServicioSituacion;
    @EJB
    private TipoControlFacadeLocal eJBServicioControl;
    @EJB
    private TipoEmpresaFacadeLocal eJBServicioEmpresa;
    @EJB
    private TipoEstablecimientoFacadeLocal eJBServicioEstablecimiento;
    @EJB
    private TipoOrganizacionFacadeLocal eJBServicioOrganizacion;

    private List<Pais> listaPaises;
    private List<Departamento> listaDepartamentos;
    private List<EstadoMatricula> listaMatriculas;
    private List<Municipio> listaMunicipios;
    private List<SituacionControl> listaSituaciones;
    private List<TipoControl> listaControles;
    private List<TipoEmpresa> listaEmpresas;
    private List<TipoEstablecimiento> listaEstablecimeintos;
    private List<TipoOrganizacion> listaOrganizaciones;

    @ManagedProperty("#{MbFormGrupoEmpresa}")
    private FormularioGrupoEmpresa servicioFormularioGrupoEmpresa;

    public ListasDesplegablesFormularios() {
    }

    @PostConstruct
    public void init() {
        setListaPaises(eJBServicioPais.findAllInOrderByNameAsc());
        setListaDepartamentos(eJBServicioDepartamento.findAllInOrderByNameAsc());
        setListaMatriculas(eJBServicioMatricula.findAllInOrderByNameAsc());
        setListaMunicipios(eJBServicioMunicipio.findAllInOrderByNameAsc());
        setListaSituaciones(eJBServicioSituacion.findAllInOrderByNameAsc());
        setListaControles(eJBServicioControl.findAllInOrderByNameAsc());
        setListaEmpresas(eJBServicioEmpresa.findAllInOrderByNameAsc());
        setListaEstablecimeintos(eJBServicioEstablecimiento.findAllInOrderByNameAsc());
        setListaOrganizaciones(eJBServicioOrganizacion.findAllInOrderByNameAsc());
    }

    /**
     * Método listener que se dispara al seleccionar un item de la lista de 
     * departamentos, permitiendo consultar y cargar el listado de municipios
     * de acuerdo al departamento seleccionado
     * @param event 
     */
    public void seleccionDepartamento(AjaxBehaviorEvent event) {
        setListaMunicipios(eJBServicioMunicipio.findAllByDepartamentoOrderAsc(getServicioFormularioGrupoEmpresa().getIdentificacionSeleccionada().getDepartamento()));
    }

    //Lista Get y Set de la clase
    public List<Pais> getListaPaises() {
        return listaPaises;
    }

    public void setListaPaises(List<Pais> listaPaises) {
        this.listaPaises = listaPaises;
    }

    public List<Departamento> getListaDepartamentos() {
        return listaDepartamentos;
    }

    public void setListaDepartamentos(List<Departamento> listaDepartamentos) {
        this.listaDepartamentos = listaDepartamentos;
    }

    public List<EstadoMatricula> getListaMatriculas() {
        return listaMatriculas;
    }

    public void setListaMatriculas(List<EstadoMatricula> listaMatriculas) {
        this.listaMatriculas = listaMatriculas;
    }

    public List<Municipio> getListaMunicipios() {
        return listaMunicipios;
    }

    public void setListaMunicipios(List<Municipio> listaMunicipios) {
        this.listaMunicipios = listaMunicipios;
    }

    public List<SituacionControl> getListaSituaciones() {
        return listaSituaciones;
    }

    public void setListaSituaciones(List<SituacionControl> listaSituaciones) {
        this.listaSituaciones = listaSituaciones;
    }

    public List<TipoControl> getListaControles() {
        return listaControles;
    }

    public void setListaControles(List<TipoControl> listaControles) {
        this.listaControles = listaControles;
    }

    public List<TipoEmpresa> getListaEmpresas() {
        return listaEmpresas;
    }

    public void setListaEmpresas(List<TipoEmpresa> listaEmpresas) {
        this.listaEmpresas = listaEmpresas;
    }

    public List<TipoEstablecimiento> getListaEstablecimeintos() {
        return listaEstablecimeintos;
    }

    public void setListaEstablecimeintos(List<TipoEstablecimiento> listaEstablecimeintos) {
        this.listaEstablecimeintos = listaEstablecimeintos;
    }

    public List<TipoOrganizacion> getListaOrganizaciones() {
        return listaOrganizaciones;
    }

    public void setListaOrganizaciones(List<TipoOrganizacion> listaOrganizaciones) {
        this.listaOrganizaciones = listaOrganizaciones;
    }

    public FormularioGrupoEmpresa getServicioFormularioGrupoEmpresa() {
        return servicioFormularioGrupoEmpresa;
    }

    public void setServicioFormularioGrupoEmpresa(FormularioGrupoEmpresa servicioFormularioGrupoEmpresa) {
        this.servicioFormularioGrupoEmpresa = servicioFormularioGrupoEmpresa;
    }

    
}
