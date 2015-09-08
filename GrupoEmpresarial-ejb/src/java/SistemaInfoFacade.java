import com.dane.ige.modelo.local.administracion.SistemaInfoFacadeLocal;
import com.dane.ige.modelo.entidad.SistemaInfo;
import com.dane.ige.modelo.fachada.AbstractFacade;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
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
            LOGGER.info("Versión del Aplicativo: "+ objSistema.getVersion());
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
        } 
        return objSistema;
    }
    
}
