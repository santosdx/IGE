package com.dane.ige.modelo.servicios.administracion;

import com.dane.ige.modelo.fachada.AbstractFacade;
import com.dane.ige.modelo.local.administracion.PaisFacadeLocal;
import com.dane.ige.modelo.entidad.Pais;
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
 * con la clase Pais como parametro y que implementa la interfaz
 * PaisFacadeLocal, para brindar los servicios sobre el acceso a los datos a la
 * tabla ige_pais.
 *
 * @author srojasm
 */
@Stateless(name = "EJBServicioPais")
public class PaisFacade extends AbstractFacade<Pais> implements PaisFacadeLocal {

    final static Logger LOGGER = Logger.getLogger(PaisFacade.class);

    @PersistenceContext(unitName = "GrupoEmpresarial-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PaisFacade() {
        super(Pais.class);
    }

    /**
     * Método que permite realizar la consulta a la base de datos y retornar el
     * listado de Paises ordenado por el nombre.
     *
     * @return List[Pais]
     */
    public List<Pais> findAllInOrderByNameAsc() {
        List<Pais> resultado = new ArrayList<Pais>();
        try {
            Query query = em.createNamedQuery(Pais.FINE_BYE_FIND_ALL_IN_ORDER_BY_NAME_ASC);
            List<Pais> listaResultado = Collections.EMPTY_LIST;
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
