package com.dane.ige.modelo.servicios.administracion;

import com.dane.ige.modelo.entidad.ModuloPermiso;
import com.dane.ige.modelo.fachada.AbstractFacade;
import com.dane.ige.modelo.local.administracion.ModuloPermisoFacadeLocal;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 * Clase que se describe como servicio y que extiende de la clase AbstractFacade
 * con la clase ModuloPermiso como parametro y que implementa la interfaz
 * ModuloPermisoFacadeLocal, para brindar los servicios sobre el acceso a los
 * datos a la tabla ige_modulo_permiso.
 *
 * @author srojasm
 */
@Stateless
public class ModuloPermisoFacade extends AbstractFacade<ModuloPermiso> implements ModuloPermisoFacadeLocal {

    final static Logger LOGGER = Logger.getLogger(ModuloPermisoFacade.class);

    @PersistenceContext(unitName = "GrupoEmpresarial-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ModuloPermisoFacade() {
        super(ModuloPermiso.class);
    }

    /**
     * Método que permite realizar la consulta a la base de datos y buscar la
     * asiganación de los modulos por permiso, enviando como parametro el ID del
     * modulo y el ID del permiso.
     *
     * @param idModulo
     * @param idPermiso
     * @return ModuloPermiso
     */
    @Override
    public ModuloPermiso buscarAsignacionModuloPermiso(int idModulo, int idPermiso) {
        ModuloPermiso resultado = null;
        try {
            Query query = em.createNamedQuery(ModuloPermiso.FINE_BYE_IDMODULO_IDPERMISO);
            query.setParameter("idModulo", idModulo);
            query.setParameter("idPermiso", idPermiso);

            List<ModuloPermiso> listaResultado = Collections.EMPTY_LIST;
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
     * Método que permite eliminar la asignación del modulo a un permiso en la
     * base de datos, enviando como parametro el ID del modulo.
     *
     * @param idModulo
     * @return int
     */
    @Override
    public int eliminarModuloPermiso(int idModulo) {
        int resultado = 0;
        try {
            Query query = em.createNamedQuery(ModuloPermiso.DELETE_ALL_MODULO_PERMISOS_BY_IDMODULO);
            query.setParameter("idModulo", idModulo);
            resultado = query.executeUpdate();
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
        }
        return resultado;
    }

}
