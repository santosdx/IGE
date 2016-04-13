package com.dane.ige.modelo.servicios.administracion;

import com.dane.ige.modelo.fachada.AbstractFacade;
import com.dane.ige.modelo.local.administracion.TipoConglomeradoFacadeLocal;
import com.dane.ige.modelo.entidad.TipoConglomerado;
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
 * con la clase TipoConglomerado como parametro y que implementa la interfaz
 * TipoConglomeradoFacadeLocal, para brindar los servicios sobre el acceso a los
 * datos a la tabla ige_tipo_conglomerado.
 *
 * @author srojasm
 */
@Stateless(name = "EJBServicioConglomerado")
public class TipoConglomeradoFacade extends AbstractFacade<TipoConglomerado> implements TipoConglomeradoFacadeLocal {

    final static Logger LOGGER = Logger.getLogger(TipoConglomeradoFacade.class);

    @PersistenceContext(unitName = "GrupoEmpresarial-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoConglomeradoFacade() {
        super(TipoConglomerado.class);
    }

    /**
     * Método que permite realizar la consulta a la base de datos y retornar el
     * listado de Tipo de Conglomerado ordenado por el nombre.
     *
     * @return List[TipoConglomerado]
     */
    public List<TipoConglomerado> findAllInOrderByNameAsc() {
        List<TipoConglomerado> resultado = new ArrayList<TipoConglomerado>();
        try {
            Query query = em.createNamedQuery(TipoConglomerado.FINE_BYE_FIND_ALL_IN_ORDER_BY_NAME_ASC);
            List<TipoConglomerado> listaResultado = Collections.EMPTY_LIST;
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
