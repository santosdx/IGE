package com.dane.ige.modelo.local.administracion;

import com.dane.ige.modelo.entidad.UsuarioPerfil;
import java.util.List;
import javax.ejb.Local;

/**
 * Interfaz que describe todos los metodos que podran ser implementados para la
 * entidad UsuarioPerfil como servicio.
 *
 * @author srojasm
 */
@Local
public interface UsuarioPerfilFacadeLocal {

    void create(UsuarioPerfil usuarioPerfil);

    void edit(UsuarioPerfil usuarioPerfil);

    void remove(UsuarioPerfil usuarioPerfil);

    UsuarioPerfil find(Object id);

    List<UsuarioPerfil> findAll();

    List<UsuarioPerfil> findRange(int[] range);

    int count();

    public UsuarioPerfil buscarAsignacionUsuarioPerfil(int idUsuario, int idPerfil);

}
