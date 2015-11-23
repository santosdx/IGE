package com.dane.ige.modelo.servicios.administracion;

import com.dane.ige.modelo.fachada.AbstractFacade;
import com.dane.ige.modelo.local.administracion.DepartamentoFacadeLocal;
import com.dane.ige.modelo.entidad.Departamento;
import java.util.ArrayList;
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
@Stateless(name = "EJBServicioDepartamento")
public class DepartamentoFacade extends AbstractFacade<Departamento> implements DepartamentoFacadeLocal {

    final static Logger LOGGER = Logger.getLogger(DepartamentoFacade.class);

    @PersistenceContext(unitName = "GrupoEmpresarial-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DepartamentoFacade() {
        super(Departamento.class);
    }
    
     public List<Departamento> findAllInOrderByNameAsc(){
        List<Departamento> resultado = new ArrayList<Departamento>();
        try {
            String sql = "SELECT id_departamento, REPLACE(departamento_nombre,'_',' ') as departamento_nombre  FROM ige_departamento ORDER BY departamento_nombre ASC";
            //Query query = em.createNamedQuery(Departamento.FINE_BYE_FIND_ALL_IN_ORDER_BY_NAME_ASC);
            Query query = em.createNativeQuery(sql,Departamento.class);
            List<Departamento> listaResultado = Collections.EMPTY_LIST;
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
