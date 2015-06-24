package com.dane.ige.modelo.servicios.administracion;

import com.dane.ige.modelo.local.administracion.ModuloFacadeLocal;
import com.dane.ige.modelo.entidad.Modulo;
import com.dane.ige.modelo.fachada.AbstractFacade;
import java.util.ArrayList;
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
@Stateless(name = "EJBServicioModulo")
public class ModuloFacade extends AbstractFacade<Modulo> implements ModuloFacadeLocal {

    @PersistenceContext(unitName = "GrupoEmpresarial-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ModuloFacade() {
        super(Modulo.class);
    }

    /**
     * Método que permite crear un registro en la entidad modulo y retorna el id
     * o llave con el cual se ingreso a la base de datos ese registro. En caso
     * de error, u otra inconsistencia, retornara -1.
     *
     * @param modulo
     * @return
     */
    @Override
    public Integer createAndGetKey(Modulo modulo) {
        Integer resultado = -1;
        try {
            em.persist(modulo);
            em.flush();
            resultado = modulo.getId();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return resultado;
    }

    @Override
    public Modulo buscarModuloByModulo(String modulo) {
        Modulo resultado = null;
        try {
            Query query = em.createNamedQuery(Modulo.FINE_BYE_MODULO);
            query.setParameter("modulo", modulo);

            List<Modulo> listaResultado = Collections.EMPTY_LIST;
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
    public List<Modulo> getModulesPerfilByIdPerfil(int idPerfil) {
        List<Modulo> resultado = new ArrayList<Modulo>();
        try {
            //Query query = em.createNamedQuery(Modulo.FINE_MODLE_BYE_IDPERFIL);

            String sql = "SELECT DISTINCT m.* "
                    + "FROM ige_modulo m "
                    + "JOIN ige_modulo_permiso mp ON (m.id_ige_modulo = mp.id_ige_modulo) "
                    + "JOIN ige_perfil_permiso pp ON (pp.id_ige_permiso = mp.id_ige_permiso) "
                    + "JOIN ige_permiso pe        ON (pe.id_ige_permiso = pp.id_ige_permiso) "
                    + "WHERE pp.id_ige_perfil = ? ORDER BY m.orden ASC";

            Query query = em.createNativeQuery(sql, Modulo.class);
            query.setParameter(1, idPerfil);

            List<Modulo> listaResultado = Collections.EMPTY_LIST;
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
