package com.dane.ige.modelo.servicios.administracion;

import com.dane.ige.modelo.entidad.BodegaIdentificacion;
import com.dane.ige.modelo.local.administracion.BodegaIdentificacionFacadeLocal;
import com.dane.ige.modelo.fachada.AbstractFacade;
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
@Stateless(name = "EJBServicioBodegaIdentificacion")
public class BodegaIdentificacionFacade extends AbstractFacade<BodegaIdentificacion> implements BodegaIdentificacionFacadeLocal {

    @PersistenceContext(unitName = "GrupoEmpresarial-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BodegaIdentificacionFacade() {
        super(BodegaIdentificacion.class);
    }

    /**
     * Método que permite obtener el registro del grupo empresarial por el ID
     * @param id
     * @return 
     */
    @Override
    public BodegaIdentificacion obtenerIdentificacionGrupoEmpresaById(Integer id) {
        BodegaIdentificacion resultado = null;
        try {
            Query query = em.createNamedQuery(BodegaIdentificacion.FINE_BYE_ID);
            query.setParameter("id", id);

            List<BodegaIdentificacion> listaResultado = Collections.EMPTY_LIST;
            listaResultado = query.getResultList();
            if (listaResultado.isEmpty()) {
                return null;
            } else {
                resultado = listaResultado.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return resultado;
    }

}
