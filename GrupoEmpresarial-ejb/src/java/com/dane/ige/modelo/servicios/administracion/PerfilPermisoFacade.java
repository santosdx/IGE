package com.dane.ige.modelo.servicios.administracion;

import com.dane.ige.modelo.local.administracion.PerfilPermisoFacadeLocal;
import com.dane.ige.modelo.entidad.PerfilPermiso;
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
 * con la clase PerfilPermiso como parametro y que implementa la interfaz
 * PerfilPermisoFacadeLocal, para brindar los servicios sobre el acceso a los
 * datos a la tabla ige_perfil_permiso.
 *
 * @author srojasm
 */
@Stateless
public class PerfilPermisoFacade extends AbstractFacade<PerfilPermiso> implements PerfilPermisoFacadeLocal {

    final static Logger LOGGER = Logger.getLogger(PerfilPermisoFacade.class);

    @PersistenceContext(unitName = "GrupoEmpresarial-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PerfilPermisoFacade() {
        super(PerfilPermiso.class);
    }

    /**
     * Método que permite buscar una asiganación de permiso a perfil, retornando
     * el objeto de la relación, o en caso de no encontrar nada un null
     *
     * @param idPerfil
     * @param idPermiso
     * @return PerfilPermiso
     */
    @Override
    public PerfilPermiso buscarAsignacionPermisoPerfil(int idPermiso, int idPerfil) {
        PerfilPermiso resultado = null;
        try {
            Query query = em.createNamedQuery(PerfilPermiso.FINE_BYE_IDPERMISO_IDPERFIL);
            query.setParameter("idPermiso", idPermiso);
            query.setParameter("idPerfil", idPerfil);

            List<PerfilPermiso> listaResultado = Collections.EMPTY_LIST;
            listaResultado = query.getResultList();
            if (listaResultado.isEmpty()) {
                resultado = null;
            } else {
                resultado = listaResultado.get(0);
            }
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
        }
        return resultado;
    }

    /**
     * Método que permite eliminar todas las asignaciones de permisos a un
     * perfil pasando como parametro el id de l perfil.
     *
     * @param idPerfil
     * @return int
     */
    @Override
    public int eliminarPermisosPerfil(int idPerfil) {
        int resultado = 0;
        try {
            Query query = em.createNamedQuery(PerfilPermiso.DELETE_ALL_PERMISOS_PERFIL_BY_IDPERFIL);
            query.setParameter("idPerfil", idPerfil);
            resultado = query.executeUpdate();
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
        }
        return resultado;
    }
}
