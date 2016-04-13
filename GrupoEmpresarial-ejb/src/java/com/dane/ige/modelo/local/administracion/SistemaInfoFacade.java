package com.dane.ige.modelo.local.administracion;


import com.dane.ige.modelo.local.administracion.SistemaInfoFacadeLocal;
import com.dane.ige.modelo.entidad.SistemaInfo;
import com.dane.ige.modelo.fachada.AbstractFacade;
import com.dane.ige.modelo.fachada.AbstractFacade;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.resource.ResourceException;

/**
 * Clase que se describe como servicio y que extiende de la clase AbstractFacade
 * con la clase SistemaInfo como parametro y que implementa la interfaz
 * SistemaInfoFacadeLocal, para brindar los servicios sobre el acceso a los
 * datos a la tabla ige_sistema_info.
 *
 * @author srojasm
 */
@Stateless(name = "EJBServicioSistemaInfo")
public class SistemaInfoFacade extends AbstractFacade<SistemaInfo> implements SistemaInfoFacadeLocal {

    final static Logger LOGGER = Logger.getLogger(SistemaInfoFacade.class);

    @PersistenceContext(unitName = "GrupoEmpresarial-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SistemaInfoFacade() {
        super(SistemaInfo.class);
    }

    /**
     * Método que permite consultar a la base de datos la información de la
     * ultima versión del aplicativo.
     *
     * @return SistemaInfo
     */
    public SistemaInfo obtenerUltimaVersion() {

        SistemaInfo objSistema = null;

        try {
            Query query = em.createNamedQuery(SistemaInfo.FIND_BY_FECHA_VIGENCIA_NULL);
            //query.setParameter("fechaVigencia", TemporalType.DATE);

            List<SistemaInfo> listaDatos = Collections.EMPTY_LIST;
            listaDatos = query.getResultList();
            if (listaDatos.isEmpty()) {
                return null;
            } else {
                objSistema = listaDatos.get(0);
            }
            LOGGER.info("Versión del Aplicativo: " + objSistema.getVersion());
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
        }
        return objSistema;
    }

    /**
     * Método que permite realizar un text de conexión a la base de datos
     *
     * @return boolean
     * @throws ResourceException
     */
    public boolean testConexion() throws ResourceException {
        boolean resultado = false;
        SistemaInfo objSistema = null;
        Query query = em.createNamedQuery(SistemaInfo.FIND_BY_FECHA_VIGENCIA_NULL);
        List<SistemaInfo> listaDatos = Collections.EMPTY_LIST;
        listaDatos = query.getResultList();
        if (listaDatos.isEmpty()) {
            resultado = false;
        } else {
            objSistema = listaDatos.get(0);
            resultado = true;
        }
        //LOGGER.info("Test de conexión");
        return resultado;
    }
}
