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
 * Clase que se describe como servicio y que extiende de la clase AbstractFacade
 * con la clase BodegaTamano como parametro y que implementa la interfaz
 * BodegaTamanoFacadeLocal, para brindar los servicios sobre el acceso a los
 * datos a la tabla ige_tamano.
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
     * @return BodegaTamano
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

    /**
     * Método que permite realizar la consulta a la base de datos enviando como
     * parametro el ID de la unidad en la tabla Identificación, y retornando el
     * listado de atributos y valor en un Map.
     *
     * @param id
     * @return Map<String, String>
     */
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

    /**
     * Método que permite consultar el registro de tamaño enviando como
     * parametro la llave compuesta por el ID y la Fecha de actualizacion
     *
     * @param llave
     * @return BodegaTamano
     */
    @Override
    public BodegaTamano obtenerRegistroByLlaveCompuesta(String llave) {
        BodegaTamano resultado = null;
        try {

            String sql = "SELECT * "
                    + "FROM ige_tamano "
                    + "WHERE t_id_organizacion ||''|| to_char(t_fecha_actualiza,'dd/MM/yyyy HH24:MI:SS') =" + llave + " ";
            //'1530/08/2015 00:00:00','1530/11/2015 16:12:17'
            LOGGER.info(sql);
            Query query = em.createNativeQuery(sql, BodegaTamano.class);

            resultado = (BodegaTamano) query.getSingleResult();

        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
        }
        return resultado;
    }
}
