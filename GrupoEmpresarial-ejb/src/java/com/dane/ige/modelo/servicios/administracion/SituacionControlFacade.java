package com.dane.ige.modelo.servicios.administracion;

import com.dane.ige.modelo.fachada.AbstractFacade;
import com.dane.ige.modelo.local.administracion.SituacionControlFacadeLocal;
import com.dane.ige.modelo.entidad.SituacionControl;
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
@Stateless(name = "EJBServicioSituacion")
public class SituacionControlFacade extends AbstractFacade<SituacionControl> implements SituacionControlFacadeLocal {

    @PersistenceContext(unitName = "GrupoEmpresarial-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SituacionControlFacade() {
        super(SituacionControl.class);
    }
    
     public List<SituacionControl> findAllInOrderByNameAsc(){
        List<SituacionControl> resultado = new ArrayList<SituacionControl>();
        try {
            Query query = em.createNamedQuery(SituacionControl.FINE_BYE_FIND_ALL_IN_ORDER_BY_NAME_ASC);
            List<SituacionControl> listaResultado = Collections.EMPTY_LIST;
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
