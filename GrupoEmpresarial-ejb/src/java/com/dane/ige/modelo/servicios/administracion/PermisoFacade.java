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

/**
 *
 * @author srojasm
 */
@Stateless(name = "EJBServicioPermiso")
public class PermisoFacade extends AbstractFacade<Permiso> implements PermisoFacadeLocal {

    @PersistenceContext(unitName = "GrupoEmpresarial-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PermisoFacade() {
        super(Permiso.class);
    }

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
            e.printStackTrace(System.err);
        }
        return resultado;
    }

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
            e.printStackTrace(System.err);
        }
        return resultado;
    }
}
