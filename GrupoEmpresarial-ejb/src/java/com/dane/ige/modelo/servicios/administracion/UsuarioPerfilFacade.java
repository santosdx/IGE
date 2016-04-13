package com.dane.ige.modelo.servicios.administracion;

import com.dane.ige.modelo.local.administracion.UsuarioPerfilFacadeLocal;
import com.dane.ige.modelo.entidad.UsuarioPerfil;
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
 * con la clase UsuarioPerfil como parametro y que implementa la interfaz
 * UsuarioPerfilFacadeLocal, para brindar los servicios sobre el acceso a los
 * datos a la tabla ige_usuario_perfil.
 *
 * @author srojasm
 */
@Stateless
public class UsuarioPerfilFacade extends AbstractFacade<UsuarioPerfil> implements UsuarioPerfilFacadeLocal {

    final static Logger LOGGER = Logger.getLogger(UsuarioPerfilFacade.class);

    @PersistenceContext(unitName = "GrupoEmpresarial-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioPerfilFacade() {
        super(UsuarioPerfil.class);
    }

    /**
     * Método que permite buscar una asiganación de perfil a usuario, retornando
     * el objeto de la relación, o en caso de no encontrar nada un null
     *
     * @param idUsuario
     * @param idPerfil
     * @return UsuarioPerfil
     */
    @Override
    public UsuarioPerfil buscarAsignacionUsuarioPerfil(int idUsuario, int idPerfil) {
        UsuarioPerfil resultado = null;
        try {
            Query query = em.createNamedQuery(UsuarioPerfil.FINE_BYE_IDUSUARIO_IDPERFIL);
            query.setParameter("idUsuario", idUsuario);
            query.setParameter("idPerfil", idPerfil);

            List<UsuarioPerfil> listaResultado = Collections.EMPTY_LIST;
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
}
