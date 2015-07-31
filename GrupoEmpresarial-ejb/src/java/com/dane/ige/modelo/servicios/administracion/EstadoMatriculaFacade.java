package com.dane.ige.modelo.servicios.administracion;

import com.dane.ige.modelo.fachada.AbstractFacade;
import com.dane.ige.modelo.local.administracion.EstadoMatriculaFacadeLocal;
import com.dane.ige.modelo.entidad.EstadoMatricula;
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
@Stateless(name = "EJBServicioMatricula")
public class EstadoMatriculaFacade extends AbstractFacade<EstadoMatricula> implements EstadoMatriculaFacadeLocal {

    @PersistenceContext(unitName = "GrupoEmpresarial-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EstadoMatriculaFacade() {
        super(EstadoMatricula.class);
    }
    
     public List<EstadoMatricula> findAllInOrderByNameAsc(){
        List<EstadoMatricula> resultado = new ArrayList<EstadoMatricula>();
        try {
            Query query = em.createNamedQuery(EstadoMatricula.FINE_BYE_FIND_ALL_IN_ORDER_BY_NAME_ASC);
            List<EstadoMatricula> listaResultado = Collections.EMPTY_LIST;
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
