package com.nerv.sai.modelo.local.administracion;

import com.nerv.sai.modelo.entidad.UsuarioPerfil;
import java.util.List;
import javax.ejb.Local;

/**
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
