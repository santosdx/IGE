package com.dane.ige.modelo.servicios.administracion;

import com.dane.ige.modelo.fachada.AbstractFacade;
import com.dane.ige.modelo.local.administracion.TipoOrganizacionFacadeLocal;
import com.dane.ige.modelo.entidad.TipoOrganizacion;
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
@Stateless(name = "EJBServicioOrganizacion")
public class TipoOrganizacionFacade extends AbstractFacade<TipoOrganizacion> implements TipoOrganizacionFacadeLocal {

    @PersistenceContext(unitName = "GrupoEmpresarial-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoOrganizacionFacade() {
        super(TipoOrganizacion.class);
    }
}
