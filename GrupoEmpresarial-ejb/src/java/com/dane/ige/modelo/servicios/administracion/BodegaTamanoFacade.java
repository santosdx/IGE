package com.dane.ige.modelo.servicios.administracion;

import com.dane.ige.modelo.entidad.BodegaTamano;
import com.dane.ige.modelo.local.administracion.BodegaTamanoFacadeLocal;
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
@Stateless(name = "EJBServicioBodegaTamano")
public class BodegaTamanoFacade extends AbstractFacade<BodegaTamano> implements BodegaTamanoFacadeLocal {

    final static Logger LOGGER = Logger.getLogger(BodegaTamanoFacade.class);

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
            //Query query = em.createNamedQuery(BodegaTamano.FINE_BYE_ID);
            //query.setParameter("id", id);
            String sql = "SELECT * FROM ige_tamano "
                    + "WHERE t_id_organizacion = " + id + " "
                    + "AND t_fecha_actualiza = (SELECT MAX(t_fecha_actualiza) FROM ige_tamano WHERE t_id_organizacion = " + id + " )";
            Query query = em.createNativeQuery(sql, BodegaTamano.class);

            List<BodegaTamano> listaResultado = Collections.EMPTY_LIST;
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
    public Map<String, String> obtenerMapTamanoGrupoEmpresaById(Long id) {
        Map<String, String> resultado = null;
        try {
            //Query query = em.createNamedQuery(BodegaTamano.FINE_BYE_ID);
            //query.setParameter("id", id);
            String sql = "SELECT * FROM ige_tamano "
                    + "WHERE t_id_organizacion = " + id + " "
                    + "AND t_fecha_actualiza = (SELECT MAX(t_fecha_actualiza) FROM ige_tamano WHERE t_id_organizacion = " + id + " )";
            Query query = em.createNativeQuery(sql, BodegaTamano.class);

            List<BodegaTamano> listaResultado = Collections.EMPTY_LIST;
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
