package com.dane.ige.modelo.servicios.administracion;

import com.dane.ige.modelo.fachada.AbstractFacade;
import com.dane.ige.modelo.local.administracion.TipoControlFacadeLocal;
import com.dane.ige.modelo.entidad.TipoControl;
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
 * con la clase TipoControl como parametro y que implementa la interfaz
 * TipoControlFacadeLocal, para brindar los servicios sobre el acceso a los
 * datos a la tabla ige_tipo_control.
 *
 * @author srojasm
 */
@Stateless(name = "EJBServicioControl")
public class TipoControlFacade extends AbstractFacade<TipoControl> implements TipoControlFacadeLocal {

    final static Logger LOGGER = Logger.getLogger(TipoControlFacade.class);

    @PersistenceContext(unitName = "GrupoEmpresarial-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoControlFacade() {
        super(TipoControl.class);
    }

    /**
     * Método que permite realizar la consulta a la base de datos y retornar el
     * listado de Tipo de Relación ordenado por el nombre.
     *
     * @return List[TipoControl]
     */
    public List<TipoControl> findAllInOrderByNameAsc() {
        List<TipoControl> resultado = new ArrayList<TipoControl>();
        try {
            Query query = em.createNamedQuery(TipoControl.FINE_BYE_FIND_ALL_IN_ORDER_BY_NAME_ASC);
            List<TipoControl> listaResultado = Collections.EMPTY_LIST;
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
