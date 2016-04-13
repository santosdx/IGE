package com.dane.ige.modelo.servicios.administracion;

import com.dane.ige.modelo.fachada.AbstractFacade;
import com.dane.ige.modelo.local.administracion.SituacionControlFacadeLocal;
import com.dane.ige.modelo.entidad.SituacionControl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 * Clase que se describe como servicio y que extiende de la clase AbstractFacade
 * con la clase SituacionControl como parametro y que implementa la interfaz
 * SituacionControlFacadeLocal, para brindar los servicios sobre el acceso a los
 * datos a la tabla ige_situacion_control.
 *
 * @author srojasm
 */
@Stateless(name = "EJBServicioSituacion")
public class SituacionControlFacade extends AbstractFacade<SituacionControl> implements SituacionControlFacadeLocal {

    final static Logger LOGGER = Logger.getLogger(SituacionControlFacade.class);

    @PersistenceContext(unitName = "GrupoEmpresarial-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SituacionControlFacade() {
        super(SituacionControl.class);
    }

    /**
     * Método que permite realizar la consulta a la base de datos y retornar el
     * listado de las situaciones de control ordenado por el nombre.
     *
     * @return List[SituacionControl]
     */
    public List<SituacionControl> findAllInOrderByNameAsc() {
        List<SituacionControl> resultado = new ArrayList<SituacionControl>();
        try {
            Query query = em.createNamedQuery(SituacionControl.FINE_BYE_FIND_ALL_IN_ORDER_BY_NAME_ASC);
            List<SituacionControl> listaResultado = Collections.EMPTY_LIST;
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
