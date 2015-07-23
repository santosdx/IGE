package com.dane.ige.modelo.servicios.administracion;

import com.dane.ige.modelo.entidad.BodegaNovedad;
import com.dane.ige.modelo.local.administracion.BodegaNovedadFacadeLocal;
import com.dane.ige.modelo.fachada.AbstractFacade;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author srojasm
 */
@Stateless(name = "EJBServicioBodegaNovedad")
public class BodegaNovedadFacade extends AbstractFacade<BodegaNovedad> implements BodegaNovedadFacadeLocal {

    @PersistenceContext(unitName = "GrupoEmpresarial-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BodegaNovedadFacade() {
        super(BodegaNovedad.class);
    }

    /**
     * Método que permite obtener el registro de la novedad de un grupo
     * empresarial por el ID
     *
     * @param id
     * @return
     */
    @Override
    public BodegaNovedad obtenerNovedadGrupoEmpresaById(Long id) {
        BodegaNovedad resultado = null;
        try {
            Query query = em.createNamedQuery(BodegaNovedad.FINE_BYE_ID);
            query.setParameter("id", id);

            List<BodegaNovedad> listaResultado = Collections.EMPTY_LIST;
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

    @Override
    public Map<String, String> obtenerMapNovedadGrupoEmpresaById(Long id) {
        Map<String, String> resultado = null;
        try {
            Query query = em.createNamedQuery(BodegaNovedad.FINE_BYE_ID);
            query.setParameter("id", id);

            List<BodegaNovedad> listaResultado = Collections.EMPTY_LIST;
            listaResultado = query.getResultList();

            if (listaResultado.isEmpty()) {
                return null;
            } else {
                resultado = new HashMap<String, String>();
                resultado = listaResultado.get(0).toMap();
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return resultado;
    }
}
