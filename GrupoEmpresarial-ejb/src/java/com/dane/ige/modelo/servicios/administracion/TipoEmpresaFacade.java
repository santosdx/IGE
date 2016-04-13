package com.dane.ige.modelo.servicios.administracion;

import com.dane.ige.modelo.fachada.AbstractFacade;
import com.dane.ige.modelo.local.administracion.TipoEmpresaFacadeLocal;
import com.dane.ige.modelo.entidad.TipoEmpresa;
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
 * con la clase TipoEmpresa como parametro y que implementa la interfaz
 * TipoEmpresaFacadeLocal, para brindar los servicios sobre el acceso a los
 * datos a la tabla ige_tipo_empresa_controlante.
 *
 * @author srojasm
 */
@Stateless(name = "EJBServicioEmpresa")
public class TipoEmpresaFacade extends AbstractFacade<TipoEmpresa> implements TipoEmpresaFacadeLocal {

    final static Logger LOGGER = Logger.getLogger(TipoEmpresaFacade.class);

    @PersistenceContext(unitName = "GrupoEmpresarial-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoEmpresaFacade() {
        super(TipoEmpresa.class);
    }

    /**
     * Método que permite realizar la consulta a la base de datos y retornar el
     * listado de Tipo de Empresa ordenado por el nombre.
     *
     * @return List[TipoEmpresa]
     */
    public List<TipoEmpresa> findAllInOrderByNameAsc() {
        List<TipoEmpresa> resultado = new ArrayList<TipoEmpresa>();
        try {
            Query query = em.createNamedQuery(TipoEmpresa.FINE_BYE_FIND_ALL_IN_ORDER_BY_NAME_ASC);
            List<TipoEmpresa> listaResultado = Collections.EMPTY_LIST;
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
