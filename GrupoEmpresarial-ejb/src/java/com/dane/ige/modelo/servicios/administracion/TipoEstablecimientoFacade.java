package com.dane.ige.modelo.servicios.administracion;

import com.dane.ige.modelo.fachada.AbstractFacade;
import com.dane.ige.modelo.local.administracion.TipoEstablecimientoFacadeLocal;
import com.dane.ige.modelo.entidad.TipoEstablecimiento;
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
 * con la clase TipoEstablecimiento como parametro y que implementa la interfaz
 * TipoEstablecimientoFacadeLocal, para brindar los servicios sobre el acceso a
 * los datos a la tabla ige_tipo_establecimiento.
 *
 * @author srojasm
 */
@Stateless(name = "EJBServicioEstablecimiento")
public class TipoEstablecimientoFacade extends AbstractFacade<TipoEstablecimiento> implements TipoEstablecimientoFacadeLocal {

    final static Logger LOGGER = Logger.getLogger(TipoEstablecimientoFacade.class);

    @PersistenceContext(unitName = "GrupoEmpresarial-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoEstablecimientoFacade() {
        super(TipoEstablecimiento.class);
    }

    /**
     * Método que permite realizar la consulta a la base de datos y retornar el
     * listado de Tipo de Establecimiento ordenado por el nombre.
     *
     * @return List[TipoEstablecimiento]
     */
    public List<TipoEstablecimiento> findAllInOrderByNameAsc() {
        List<TipoEstablecimiento> resultado = new ArrayList<TipoEstablecimiento>();
        try {
            Query query = em.createNamedQuery(TipoEstablecimiento.FINE_BYE_FIND_ALL_IN_ORDER_BY_NAME_ASC);
            List<TipoEstablecimiento> listaResultado = Collections.EMPTY_LIST;
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
