package com.dane.ige.modelo.servicios.administracion;

import com.dane.ige.modelo.fachada.AbstractFacade;
import com.dane.ige.modelo.local.administracion.EstadoMatriculaFacadeLocal;
import com.dane.ige.modelo.entidad.EstadoMatricula;
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
 * con la clase EstadoMatricula como parametro y que implementa la interfaz
 * EstadoMatriculaFacadeLocal, para brindar los servicios sobre el acceso a los
 * datos a la tabla ige_estado_matricula.
 *
 * @author srojasm
 */
@Stateless(name = "EJBServicioMatricula")
public class EstadoMatriculaFacade extends AbstractFacade<EstadoMatricula> implements EstadoMatriculaFacadeLocal {

    final static Logger LOGGER = Logger.getLogger(EstadoMatriculaFacade.class);

    @PersistenceContext(unitName = "GrupoEmpresarial-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EstadoMatriculaFacade() {
        super(EstadoMatricula.class);
    }

    /**
     * Método que permite realizar la consulta a la base de datos y retornar el
     * listado de Estados de Matricula ordenado por el nombre.
     *
     * @return List[EstadoMatricula]
     */
    public List<EstadoMatricula> findAllInOrderByNameAsc() {
        List<EstadoMatricula> resultado = new ArrayList<EstadoMatricula>();
        try {
            Query query = em.createNamedQuery(EstadoMatricula.FINE_BYE_FIND_ALL_IN_ORDER_BY_NAME_ASC);
            List<EstadoMatricula> listaResultado = Collections.EMPTY_LIST;
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
