package com.dane.ige.modelo.servicios.administracion;

import com.dane.ige.modelo.fachada.AbstractFacade;
import com.dane.ige.modelo.local.administracion.TipoRelacionFacadeLocal;
import com.dane.ige.modelo.entidad.TipoRelacion;
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
 * con la clase TipoRelacion como parametro y que implementa la interfaz
 * TipoRelacionFacadeLocal, para brindar los servicios sobre el acceso a los
 * datos a la tabla ige_tipo_relacion.
 *
 * @author srojasm
 */
@Stateless(name = "EJBServicioRelacion")
public class TipoRelacionFacade extends AbstractFacade<TipoRelacion> implements TipoRelacionFacadeLocal {

    final static Logger LOGGER = Logger.getLogger(TipoRelacionFacade.class);

    @PersistenceContext(unitName = "GrupoEmpresarial-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoRelacionFacade() {
        super(TipoRelacion.class);
    }

    /**
     * Método que permite realizar la consulta a la base de datos y retornar el
     * listado de Tipo de Relación ordenado por el nombre.
     *
     * @return List[TipoRelacion]
     */
    public List<TipoRelacion> findAllInOrderByNameAsc() {
        List<TipoRelacion> resultado = new ArrayList<TipoRelacion>();
        try {
            Query query = em.createNamedQuery(TipoRelacion.FINE_BYE_FIND_ALL_IN_ORDER_BY_NAME_ASC);
            List<TipoRelacion> listaResultado = Collections.EMPTY_LIST;
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
