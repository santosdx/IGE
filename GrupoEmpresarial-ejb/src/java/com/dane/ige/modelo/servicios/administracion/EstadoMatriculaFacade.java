package com.dane.ige.modelo.servicios.administracion;

import com.dane.ige.modelo.fachada.AbstractFacade;
import com.dane.ige.modelo.local.administracion.EstadoMatriculaFacadeLocal;
import com.dane.ige.modelo.entidad.EstadoMatricula;
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
}
