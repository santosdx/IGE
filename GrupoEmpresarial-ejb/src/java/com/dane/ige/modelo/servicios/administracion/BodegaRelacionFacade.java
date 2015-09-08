package com.dane.ige.modelo.servicios.administracion;

import com.dane.ige.modelo.entidad.BodegaRelacion;
import com.dane.ige.modelo.local.administracion.BodegaRelacionFacadeLocal;
import com.dane.ige.modelo.fachada.AbstractFacade;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author srojasm
 */
@Stateless(name = "EJBServicioBodegaRelacion")
public class BodegaRelacionFacade extends AbstractFacade<BodegaRelacion> implements BodegaRelacionFacadeLocal {

    final static Logger LOGGER = Logger.getLogger(BodegaRelacionFacade.class);

    @PersistenceContext(unitName = "GrupoEmpresarial-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BodegaRelacionFacade() {
        super(BodegaRelacion.class);
    }

    /**
     * Método que permite obtener el registro de la relacion de un grupo
     * empresarial por el ID
     *
     * @param id
     * @return
     */
    @Override
    public BodegaRelacion obtenerRelacionGrupoEmpresaById(Long id) {
        BodegaRelacion resultado = null;
        try {
            //Query query = em.createNamedQuery(BodegaRelacion.FINE_BYE_ID);
            //query.setParameter("id", id);
            String sql = "SELECT * FROM ige_relacion "
                    + "WHERE r_id_organizacion = " + id + " "
                    + "AND r_fecha_actualiza = (SELECT MAX(r_fecha_actualiza) FROM ige_relacion WHERE r_id_organizacion = " + id + " )";
            Query query = em.createNativeQuery(sql, BodegaRelacion.class);

            List<BodegaRelacion> listaResultado = Collections.EMPTY_LIST;
            listaResultado = query.getResultList();
            if (listaResultado.isEmpty()) {
                return null;
            } else {
                resultado = listaResultado.get(0);
            }
        } catch (Exception e) {
             LOGGER.warn(e.getMessage());
        }
        return resultado;
    }

    @Override
    public Map<String, String> obtenerMapRelacionGrupoEmpresaById(Long id) {
        Map<String, String> resultado = null;
        try {
            //Query query = em.createNamedQuery(BodegaRelacion.FINE_BYE_ID);
            //query.setParameter("id", id);
            String sql = "SELECT * FROM ige_relacion "
                    + "WHERE r_id_organizacion = " + id + " "
                    + "AND r_fecha_actualiza = (SELECT MAX(r_fecha_actualiza) FROM ige_relacion WHERE r_id_organizacion = " + id + " )";
            Query query = em.createNativeQuery(sql, BodegaRelacion.class);

            List<BodegaRelacion> listaResultado = Collections.EMPTY_LIST;
            listaResultado = query.getResultList();
            if (listaResultado.isEmpty()) {
                return null;
            } else {
                resultado = new HashMap<String, String>();
                resultado = listaResultado.get(0).toMap();
            }
        } catch (Exception e) {
             LOGGER.warn(e.getMessage());
        }
        return resultado;
    }
}
