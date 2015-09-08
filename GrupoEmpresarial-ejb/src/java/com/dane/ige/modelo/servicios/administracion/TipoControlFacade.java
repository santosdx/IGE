package com.dane.ige.modelo.servicios.administracion;

import com.dane.ige.modelo.fachada.AbstractFacade;
import com.dane.ige.modelo.local.administracion.TipoControlFacadeLocal;
import com.dane.ige.modelo.entidad.TipoControl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author srojasm
 */
@Stateless(name = "EJBServicioControl")
public class TipoControlFacade extends AbstractFacade<TipoControl> implements TipoControlFacadeLocal {

    final static Logger LOGGER = Logger.getLogger(TipoControlFacade.class);

    @PersistenceContext(unitName = "GrupoEmpresarial-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoControlFacade() {
        super(TipoControl.class);
    }
    
     public List<TipoControl> findAllInOrderByNameAsc(){
        List<TipoControl> resultado = new ArrayList<TipoControl>();
        try {
            Query query = em.createNamedQuery(TipoControl.FINE_BYE_FIND_ALL_IN_ORDER_BY_NAME_ASC);
            List<TipoControl> listaResultado = Collections.EMPTY_LIST;
            listaResultado = query.getResultList();
            if (listaResultado.isEmpty()) {
                return null;
            } else {
                resultado = listaResultado;
            }
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
        }
        return resultado;
    }
}
