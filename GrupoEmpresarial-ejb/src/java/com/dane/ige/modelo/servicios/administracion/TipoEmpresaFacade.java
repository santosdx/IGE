package com.dane.ige.modelo.servicios.administracion;

import com.dane.ige.modelo.fachada.AbstractFacade;
import com.dane.ige.modelo.local.administracion.TipoEmpresaFacadeLocal;
import com.dane.ige.modelo.entidad.TipoEmpresa;
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
@Stateless(name = "EJBServicioEmpresa")
public class TipoEmpresaFacade extends AbstractFacade<TipoEmpresa> implements TipoEmpresaFacadeLocal {

    final static Logger LOGGER = Logger.getLogger(TipoEmpresaFacade.class);

    @PersistenceContext(unitName = "GrupoEmpresarial-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoEmpresaFacade() {
        super(TipoEmpresa.class);
    }
    
     public List<TipoEmpresa> findAllInOrderByNameAsc(){
        List<TipoEmpresa> resultado = new ArrayList<TipoEmpresa>();
        try {
            Query query = em.createNamedQuery(TipoEmpresa.FINE_BYE_FIND_ALL_IN_ORDER_BY_NAME_ASC);
            List<TipoEmpresa> listaResultado = Collections.EMPTY_LIST;
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
