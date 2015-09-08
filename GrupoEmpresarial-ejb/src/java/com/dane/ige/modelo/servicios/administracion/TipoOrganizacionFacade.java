package com.dane.ige.modelo.servicios.administracion;

import com.dane.ige.modelo.fachada.AbstractFacade;
import com.dane.ige.modelo.local.administracion.TipoOrganizacionFacadeLocal;
import com.dane.ige.modelo.entidad.TipoOrganizacion;
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
@Stateless(name = "EJBServicioOrganizacion")
public class TipoOrganizacionFacade extends AbstractFacade<TipoOrganizacion> implements TipoOrganizacionFacadeLocal {

    final static Logger LOGGER = Logger.getLogger(TipoOrganizacionFacade.class);

    @PersistenceContext(unitName = "GrupoEmpresarial-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoOrganizacionFacade() {
        super(TipoOrganizacion.class);
    }
    
     public List<TipoOrganizacion> findAllInOrderByNameAsc(){
        List<TipoOrganizacion> resultado = new ArrayList<TipoOrganizacion>();
        try {
            Query query = em.createNamedQuery(TipoOrganizacion.FINE_BYE_FIND_ALL_IN_ORDER_BY_NAME_ASC);
            List<TipoOrganizacion> listaResultado = Collections.EMPTY_LIST;
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
