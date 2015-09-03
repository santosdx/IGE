package com.dane.ige.modelo.servicios.administracion;

import com.dane.ige.modelo.fachada.AbstractFacade;
import com.dane.ige.modelo.local.administracion.TipoConglomeradoFacadeLocal;
import com.dane.ige.modelo.entidad.TipoConglomerado;
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
@Stateless(name = "EJBServicioConglomerado")
public class TipoConglomeradoFacade extends AbstractFacade<TipoConglomerado> implements TipoConglomeradoFacadeLocal {

    @PersistenceContext(unitName = "GrupoEmpresarial-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoConglomeradoFacade() {
        super(TipoConglomerado.class);
    }
    
     public List<TipoConglomerado> findAllInOrderByNameAsc(){
        List<TipoConglomerado> resultado = new ArrayList<TipoConglomerado>();
        try {
            Query query = em.createNamedQuery(TipoConglomerado.FINE_BYE_FIND_ALL_IN_ORDER_BY_NAME_ASC);
            List<TipoConglomerado> listaResultado = Collections.EMPTY_LIST;
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
