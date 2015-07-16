package com.dane.ige.modelo.servicios.administracion;

import com.dane.ige.modelo.entidad.BodegaTamano;
import com.dane.ige.modelo.local.administracion.BodegaTamanoFacadeLocal;
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
@Stateless(name = "EJBServicioBodegaTamano")
public class BodegaTamanoFacade extends AbstractFacade<BodegaTamano> implements BodegaTamanoFacadeLocal {

    @PersistenceContext(unitName = "GrupoEmpresarial-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BodegaTamanoFacade() {
        super(BodegaTamano.class);
    }

    /**
     * Método que permite obtener el registro del tamaño de un grupo empresarial
     * por el ID
     *
     * @param id
     * @return
     */
    @Override
    public BodegaTamano obtenerTamanoGrupoEmpresaById(Long id) {
        BodegaTamano resultado = null;
        try {
            Query query = em.createNamedQuery(BodegaTamano.FINE_BYE_ID);
            query.setParameter("id", id);

            List<BodegaTamano> listaResultado = Collections.EMPTY_LIST;
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
