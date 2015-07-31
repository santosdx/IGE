package com.dane.ige.modelo.servicios.administracion;

import com.dane.ige.modelo.fachada.AbstractFacade;
import com.dane.ige.modelo.local.administracion.MunicipioFacadeLocal;
import com.dane.ige.modelo.entidad.Municipio;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author srojasm
 */
@Stateless(name = "EJBServicioMunicipio")
public class MunicipioFacade extends AbstractFacade<Municipio> implements MunicipioFacadeLocal {

    @PersistenceContext(unitName = "GrupoEmpresarial-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MunicipioFacade() {
        super(Municipio.class);
    }
    
     public List<Municipio> findAllInOrderByNameAsc(){
        List<Municipio> resultado = new ArrayList<Municipio>();
        try {
            Query query = em.createNamedQuery(Municipio.FINE_BYE_FIND_ALL_IN_ORDER_BY_NAME_ASC);
            List<Municipio> listaResultado = Collections.EMPTY_LIST;
            listaResultado = query.getResultList();
            if (listaResultado.isEmpty()) {
                return null;
            } else {
                resultado = listaResultado;
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return resultado;
    }
     
     /**
      * Método que permite listar los municipios de un departamento, pasando como
      * parametro el nombre del departamento al que pertenece.
      * @param departamento
      * @return 
      */
     public List<Municipio> findAllByDepartamentoOrderAsc(String departamento){
        List<Municipio> resultado = new ArrayList<Municipio>();
        try {
            Query query = em.createNamedQuery(Municipio.FINE_BYE_FIND_ALL_BY_DEPARTAMENTO_ORDER_ASC);
            query.setParameter("departamento", departamento);

            List<Municipio> listaResultado = Collections.EMPTY_LIST;
            listaResultado = query.getResultList();
            if (listaResultado.isEmpty()) {
                return null;
            } else {
                resultado = listaResultado;
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return resultado;
    }
}
