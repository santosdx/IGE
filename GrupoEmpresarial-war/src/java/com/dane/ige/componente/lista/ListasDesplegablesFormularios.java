package com.dane.ige.componente.lista;

import com.dane.ige.modelo.entidad.Pais;
import com.dane.ige.modelo.local.administracion.PaisFacadeLocal;
import com.dane.ige.negocio.FormularioGrupoEmpresa;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
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

    private List<Pais> listaPaises;

    public ListasDesplegablesFormularios() {
    }

    @PostConstruct
    public void init(){
        setListaPaises(eJBServicioPais.findAll());
    }

    //Lista Get y Set de la clase
    
    public List<Pais> getListaPaises() {
        return listaPaises;
    }

    public void setListaPaises(List<Pais> listaPaises) {
        this.listaPaises = listaPaises;
    }

}
