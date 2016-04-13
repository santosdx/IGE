package com.dane.ige.modelo.servicios.administracion;

import com.dane.ige.modelo.local.administracion.VariableIgeFacadeLocal;
import com.dane.ige.modelo.entidad.VariableIge;
import com.dane.ige.modelo.fachada.AbstractFacade;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 * Clase que se describe como servicio y que extiende de la clase AbstractFacade
 * con la clase VariableIge como parametro y que implementa la interfaz
 * VariableIgeFacadeLocal, para brindar los servicios sobre el acceso a los
 * datos a la tabla ige_variable.
 *
 * @author srojasm
 */
@Stateless(name = "EJBServicioVariableIge")
public class VariableIgeFacade extends AbstractFacade<VariableIge> implements VariableIgeFacadeLocal {

    final static Logger LOGGER = Logger.getLogger(VariableIgeFacade.class);

    @PersistenceContext(unitName = "GrupoEmpresarial-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VariableIgeFacade() {
        super(VariableIge.class);
    }

    /**
     * Método que permite crear una variable y retornar el ID asignado.
     *
     * @param variableIge
     * @return Integer
     */
    public Integer createAndGetKey(VariableIge variableIge) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Método que permite consultar en la tabla variables el listado de
     * registros de un grupo determinado.
     *
     * @param grupo
     * @return List[VariableIge]
     */
    @Override
    public List<VariableIge> buscarVariableByGrupo(String grupo) {
        List<VariableIge> resultado = null;
        try {
            Query query = em.createNamedQuery(VariableIge.FINE_BYE_GRUPO);
            query.setParameter("grupo", grupo);
            List<VariableIge> listaResultado = Collections.EMPTY_LIST;
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
