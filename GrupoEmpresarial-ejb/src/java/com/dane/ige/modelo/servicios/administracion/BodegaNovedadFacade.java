package com.dane.ige.modelo.servicios.administracion;

import com.dane.ige.modelo.entidad.BodegaNovedad;
import com.dane.ige.modelo.local.administracion.BodegaNovedadFacadeLocal;
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
 * Clase que se describe como servicio y que extiende de la clase AbstractFacade
 * con la clase BodegaNovedad como parametro y que implementa la interfaz
 * BodegaNovedadFacadeLocal, para brindar los servicios sobre el acceso a los
 * datos a la tabla ige_novedad.
 *
 * @author srojasm
 */
@Stateless(name = "EJBServicioBodegaNovedad")
public class BodegaNovedadFacade extends AbstractFacade<BodegaNovedad> implements BodegaNovedadFacadeLocal {

    final static Logger LOGGER = Logger.getLogger(BodegaNovedadFacade.class);

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
     * @return BodegaNovedad
     */
    @Override
    public BodegaNovedad obtenerNovedadGrupoEmpresaById(Long id) {
        BodegaNovedad resultado = null;
        try {
            //Query query = em.createNamedQuery(BodegaNovedad.FINE_BYE_ID);
            //query.setParameter("id", id);
            String sql = "SELECT * FROM ige_novedad "
                    + "WHERE n_id_organizacion = " + id + " "
                    + "AND n_fecha_actualiza = (SELECT MAX(n_fecha_actualiza) FROM ige_novedad WHERE n_id_organizacion = " + id + " )";
            Query query = em.createNativeQuery(sql, BodegaNovedad.class);

            List<BodegaNovedad> listaResultado = Collections.EMPTY_LIST;
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

    /**
     * Método que permite realizar la consulta a la base de datos enviando como
     * parametro el ID de la unidad en la tabla Novedad, y retornando el listado
     * de atributos y valor en un Map.
     *
     * @param id
     * @return Map<String, String>
     */
    @Override
    public Map<String, String> obtenerMapNovedadGrupoEmpresaById(Long id) {
        Map<String, String> resultado = null;
        try {
            //Query query = em.createNamedQuery(BodegaNovedad.FINE_BYE_ID);
            //query.setParameter("id", id);
            String sql = "SELECT * FROM ige_novedad "
                    + "WHERE n_id_organizacion = " + id + " "
                    + "AND n_fecha_actualiza = (SELECT MAX(n_fecha_actualiza) FROM ige_novedad WHERE n_id_organizacion = " + id + " )";
            Query query = em.createNativeQuery(sql, BodegaNovedad.class);

            List<BodegaNovedad> listaResultado = Collections.EMPTY_LIST;
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
