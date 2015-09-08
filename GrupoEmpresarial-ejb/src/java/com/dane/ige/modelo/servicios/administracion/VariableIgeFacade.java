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

    public Integer createAndGetKey(VariableIge variableIge) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
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
