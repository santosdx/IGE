package com.dane.ige.modelo.servicios.administracion;

import com.dane.ige.modelo.local.administracion.PermisoFacadeLocal;
import com.dane.ige.modelo.entidad.Permiso;
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
 * con la clase Permiso como parametro y que implementa la interfaz
 * PermisoFacadeLocal, para brindar los servicios sobre el acceso a los datos a
 * la tabla ige_permiso.
 *
 * @author srojasm
 */
@Stateless(name = "EJBServicioPermiso")
public class PermisoFacade extends AbstractFacade<Permiso> implements PermisoFacadeLocal {

    final static Logger LOGGER = Logger.getLogger(PermisoFacade.class);

    @PersistenceContext(unitName = "GrupoEmpresarial-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PermisoFacade() {
        super(Permiso.class);
    }

    /**
     * Método que permite realizar la consulta a la base de datos en el listado
     * de permisos y retornar el registro de acuerdo al nombre del permiso
     * enviado como parametro
     *
     * @param permiso
     * @return Permiso
     */
    @Override
    public Permiso buscarPermisoByPermiso(String permiso) {
        Permiso resultado = null;
        try {
            Query query = em.createNamedQuery(Permiso.FINE_BYE_PERMISO);
            query.setParameter("permiso", permiso);

            List<Permiso> listaResultado = Collections.EMPTY_LIST;
            listaResultado = query.getResultList();
            if (listaResultado.isEmpty()) {
                return null;
            } else {
                resultado = listaResultado.get(0);
            }
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
        }
        return resultado;
    }

    /**
     * Método que permite realizar la consulta a la base de datos en el listado
     * de permisos y retornar el listado de permisos de acuerdo al ID del perfil
     * al cual estan asiganados y que se envia como parametro.
     *
     * @param idPerfil
     * @return List[Permiso]
     */
    @Override
    public List<Permiso> buscarPermisosUsuarioByidPerfil(Integer idPerfil) {
        List<Permiso> resultado = null;
        try {
            String sql = "SELECT DISTINCT pe.* "
                    + "FROM ige_modulo m "
                    + "JOIN ige_modulo_permiso mp ON (m.id_ige_modulo = mp.id_ige_modulo) "
                    + "JOIN ige_perfil_permiso pp ON (pp.id_ige_permiso = mp.id_ige_permiso) "
                    + "JOIN ige_permiso pe        ON (pe.id_ige_permiso =pp.id_ige_permiso) "
                    + "WHERE pp.id_ige_perfil = ?";

            Query query = em.createNativeQuery(sql, Permiso.class);
            query.setParameter(1, idPerfil);

            List<Permiso> listaResultado = Collections.EMPTY_LIST;
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
