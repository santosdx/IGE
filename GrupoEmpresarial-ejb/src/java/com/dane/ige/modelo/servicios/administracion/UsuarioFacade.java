package com.dane.ige.modelo.servicios.administracion;

import com.dane.ige.modelo.fachada.AbstractFacade;
import com.dane.ige.modelo.local.administracion.UsuarioFacadeLocal;
import com.dane.ige.modelo.entidad.Usuario;
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
@Stateless(name = "EJBServicioUsuario")
public class UsuarioFacade extends AbstractFacade<Usuario> implements UsuarioFacadeLocal {

    final static Logger LOGGER = Logger.getLogger(UsuarioFacade.class);

    @PersistenceContext(unitName = "GrupoEmpresarial-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }

    /**
     * Método que permite crear un registro en la entidad Usuario y retorna el
     * id o llave con el cual se ingreso a la base de datos ese registro. En
     * caso de error, u otra inconsistencia, retornara -1.
     *
     * @param usuario
     * @return
     */
    @Override
    public Integer createAndGetKey(Usuario usuario) {
        Integer resultado = -1;
        try {
            em.persist(usuario);
            em.flush();
            resultado = usuario.getId();
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
        }
        return resultado;
    }

    /**
     * Método que permite buscar y obtener un registro de usuario de la entidad
     * usuarios, pasando como parametro el nickname.
     *
     * @param nickname
     * @return
     */
    @Override
    public Usuario buscarUsuarioByNickname(String nickname) {
        Usuario resultado = null;
        try {
            Query query = em.createNamedQuery(Usuario.FINE_BYE_NICKNAME);
            query.setParameter("nickname", nickname);

            List<Usuario> listaResultado = Collections.EMPTY_LIST;
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
     * Método que permite obtener un usuario de la entidad usuario, pasando como
     * parametro el nickname (alias) y contraseña.
     *
     * @param nickname
     * @param password
     * @return
     */
    @Override
    public Usuario buscarUsuarioByNicknamePassword(String nickname, String password) {
        Usuario resultado = null;
        try {
            Query query = em.createNamedQuery(Usuario.FINE_BYE_NICKNAME_PASSWORD);
            query.setParameter("nickname", nickname);
            query.setParameter("password", password);

            List<Usuario> listaResultado = Collections.EMPTY_LIST;
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

}
