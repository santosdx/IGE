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
import com.dane.ige.negocio.FormularioEstablecimiento;
import com.dane.ige.negocio.FormularioGrupoEmpresa;
import com.dane.ige.negocio.FormularioUnidadLegal;
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
    @ManagedProperty("#{MbFormUnidadLegal}")
    private FormularioUnidadLegal servicioFormularioUnidadLegal;
    @ManagedProperty("#{MbFormEstablecimiento}")
    private FormularioEstablecimiento servicioFormularioEstablecimiento;

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
     * de acuerdo al departamento seleccionado del Formulario Grupo Empresarial
     * @param event 
     */
    public void seleccionDepartamentoGrupoEmpresarial(AjaxBehaviorEvent event) {
        setListaMunicipios(eJBServicioMunicipio.findAllByDepartamentoOrderAsc(getServicioFormularioGrupoEmpresa().getIdentificacionSeleccionada().getDepartamento()));
    }

    /**
     * Método listener que se dispara al seleccionar un item de la lista de 
     * departamentos, permitiendo consultar y cargar el listado de municipios
     * de acuerdo al departamento seleccionado del Formulario Unidad Legal
     * @param event 
     */
    public void seleccionDepartamentoUnidadLegal(AjaxBehaviorEvent event) {
        setListaMunicipios(eJBServicioMunicipio.findAllByDepartamentoOrderAsc(getServicioFormularioUnidadLegal().getIdentificacionSeleccionada().getDepartamento()));
    }

    /**
     * Método listener que se dispara al seleccionar un item de la lista de 
     * departamentos, permitiendo consultar y cargar el listado de municipios
     * de acuerdo al departamento seleccionado del Formulario Establecimiento
     * @param event 
     */
    public void seleccionDepartamentoEstablecimiento(AjaxBehaviorEvent event) {
        setListaMunicipios(eJBServicioMunicipio.findAllByDepartamentoOrderAsc(getServicioFormularioEstablecimiento().getIdentificacionSeleccionada().getDepartamento()));
    }

    /**
     * Métodod que permite buscar en un listado de paises, si contienen un pais
     * determinado.
     * @param paises
     * @param pais
     * @return 
     */
    public static boolean listaContieneElPais(List<Pais> paises, String pais){
        boolean resultado=false;
        for (Pais pais1 : paises) {
            if(pais1.getNombre().equals(pais)){
                resultado=true;
                break;
            }
        }
        return resultado;
    }

    /**
     * Métodod que permite buscar en un listado de departamentos, si contienen un 
     * departamento determinado.
     * @param departamentos
     * @param departamento
     * @return 
     */
    public static boolean listaContieneElDepartamento(List<Departamento> departamentos, String departamento){
        boolean resultado=false;
        for (Departamento departamento1 : departamentos) {
            if(departamento1.getNombre().equals(departamento)){
                resultado=true;
                break;
            }
        }
        return resultado;
    }

    /**
     * Métodod que permite buscar en un listado de municipios, si contienen un 
     * municipio determinado.
     * @param municipios
     * @param municipio
     * @return 
     */
    public static boolean listaContieneElMunicipio(List<Municipio> municipios, String municipio){
        boolean resultado=false;
        for (Municipio municipio1 : municipios) {
            if(municipio1.getNombre().equals(municipio)){
                resultado=true;
                break;
            }
        }
        return resultado;
    }

    /**
     * Métodod que permite buscar en un listado de estados de matricula, si contienen
     * un estado de matricula determinado.
     * @param estadoMatriculas
     * @param estadoMatricula
     * @return 
     */
    public static boolean listaContieneElEstadoMatricula(List<EstadoMatricula> estadoMatriculas, String estadoMatricula){
        boolean resultado=false;
        for (EstadoMatricula estadoMatricula1 : estadoMatriculas) {
            if(estadoMatricula1.getEstado().equals(estadoMatricula)){
                resultado=true;
                break;
            }
        }
        return resultado;
    }

    /**
     * Métodod que permite buscar en un listado de situaciones de control, si contienen
     * una situacion de control determinado.
     * @param situacionesControl
     * @param situacionControl
     * @return 
     */
    public static boolean listaContieneLaSituacionControl(List<SituacionControl> situacionesControl, String situacionControl){
        boolean resultado=false;
        for (SituacionControl situacionControl1 : situacionesControl) {
            if(situacionControl1.getSituacion().equals(situacionControl)){
                resultado=true;
                break;
            }
        }
        return resultado;
    }

    /**
     * Métodod que permite buscar en un listado de tipos de control, si contienen
     * un tipo de control determinado.
     * @param tipoControles
     * @param tipoControl
     * @return 
     */
    public static boolean listaContieneElTipoControl(List<TipoControl> tipoControles, String tipoControl){
        boolean resultado=false;
        for (TipoControl tipoControl1 : tipoControles) {
            if(tipoControl1.getTipo().equals(tipoControl)){
                resultado=true;
                break;
            }
        }
        return resultado;
    }

    /**
     * Métodod que permite buscar en un listado de tipos de empresa controlante, si contienen
     * un tipo de empresa controlante determinado.
     * @param tipoEmpresaControlantes
     * @param tipoEmpresaControlante
     * @return 
     */
    public static boolean listaContieneElTipoEmpresaControlante(List<TipoEmpresa> tipoEmpresaControlantes, String tipoEmpresaControlante){
        boolean resultado=false;
        for (TipoEmpresa tipoEmpresaControlante1 : tipoEmpresaControlantes) {
            if(tipoEmpresaControlante1.getTipo().equals(tipoEmpresaControlante)){
                resultado=true;
                break;
            }
        }
        return resultado;
    }

    /**
     * Métodod que permite buscar en un listado de tipos de establecimientos, si contienen
     * un tipo de establecimiento determinado.
     * @param tipoEstablecimientos
     * @param tipoEstablecimiento
     * @return 
     */
    public static boolean listaContieneElTipoEstablecimiento(List<TipoEstablecimiento> tipoEstablecimientos, String tipoEstablecimiento){
        boolean resultado=false;
        for (TipoEstablecimiento tipoEstablecimiento1 : tipoEstablecimientos) {
            if(tipoEstablecimiento1.getTipo().equals(tipoEstablecimiento)){
                resultado=true;
                break;
            }
        }
        return resultado;
    }

    /**
     * Métodod que permite buscar en un listado de tipos de organización de unidad legal,
     * si contienen un tipo de organización de unidad legal determinado.
     * @param tipoOrganizaciones
     * @param tipoOrganizacion
     * @return 
     */
    public static boolean listaContieneElTipoOrganizacionUnidadLegal(List<TipoOrganizacion> tipoOrganizaciones, String tipoOrganizacion){
        boolean resultado=false;
        for (TipoOrganizacion tipoOrganizacion1 : tipoOrganizaciones) {
            if(tipoOrganizacion1.getTipo().equals(tipoOrganizacion)){
                resultado=true;
                break;
            }
        }
        return resultado;
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

    public FormularioUnidadLegal getServicioFormularioUnidadLegal() {
        return servicioFormularioUnidadLegal;
    }

    public void setServicioFormularioUnidadLegal(FormularioUnidadLegal servicioFormularioUnidadLegal) {
        this.servicioFormularioUnidadLegal = servicioFormularioUnidadLegal;
    }

    public FormularioEstablecimiento getServicioFormularioEstablecimiento() {
        return servicioFormularioEstablecimiento;
    }

    public void setServicioFormularioEstablecimiento(FormularioEstablecimiento servicioFormularioEstablecimiento) {
        this.servicioFormularioEstablecimiento = servicioFormularioEstablecimiento;
    }

    public PaisFacadeLocal geteJBServicioPais() {
        return eJBServicioPais;
    }

    public void seteJBServicioPais(PaisFacadeLocal eJBServicioPais) {
        this.eJBServicioPais = eJBServicioPais;
    }

    public DepartamentoFacadeLocal geteJBServicioDepartamento() {
        return eJBServicioDepartamento;
    }

    public void seteJBServicioDepartamento(DepartamentoFacadeLocal eJBServicioDepartamento) {
        this.eJBServicioDepartamento = eJBServicioDepartamento;
    }

    public EstadoMatriculaFacadeLocal geteJBServicioMatricula() {
        return eJBServicioMatricula;
    }

    public void seteJBServicioMatricula(EstadoMatriculaFacadeLocal eJBServicioMatricula) {
        this.eJBServicioMatricula = eJBServicioMatricula;
    }

    public MunicipioFacadeLocal geteJBServicioMunicipio() {
        return eJBServicioMunicipio;
    }

    public void seteJBServicioMunicipio(MunicipioFacadeLocal eJBServicioMunicipio) {
        this.eJBServicioMunicipio = eJBServicioMunicipio;
    }

    public SituacionControlFacadeLocal geteJBServicioSituacion() {
        return eJBServicioSituacion;
    }

    public void seteJBServicioSituacion(SituacionControlFacadeLocal eJBServicioSituacion) {
        this.eJBServicioSituacion = eJBServicioSituacion;
    }

    public TipoControlFacadeLocal geteJBServicioControl() {
        return eJBServicioControl;
    }

    public void seteJBServicioControl(TipoControlFacadeLocal eJBServicioControl) {
        this.eJBServicioControl = eJBServicioControl;
    }

    public TipoEmpresaFacadeLocal geteJBServicioEmpresa() {
        return eJBServicioEmpresa;
    }

    public void seteJBServicioEmpresa(TipoEmpresaFacadeLocal eJBServicioEmpresa) {
        this.eJBServicioEmpresa = eJBServicioEmpresa;
    }

    public TipoEstablecimientoFacadeLocal geteJBServicioEstablecimiento() {
        return eJBServicioEstablecimiento;
    }

    public void seteJBServicioEstablecimiento(TipoEstablecimientoFacadeLocal eJBServicioEstablecimiento) {
        this.eJBServicioEstablecimiento = eJBServicioEstablecimiento;
    }

    public TipoOrganizacionFacadeLocal geteJBServicioOrganizacion() {
        return eJBServicioOrganizacion;
    }

    public void seteJBServicioOrganizacion(TipoOrganizacionFacadeLocal eJBServicioOrganizacion) {
        this.eJBServicioOrganizacion = eJBServicioOrganizacion;
    }

    
}
