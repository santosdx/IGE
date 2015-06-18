package com.dane.ige.modelo.local.administracion;

import com.dane.ige.modelo.entidad.Usuario;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author srojasm
 */
@Local
public interface UsuarioFacadeLocal {

    void create(Usuario usuario);
    
    public Integer createAndGetKey(Usuario usuario);

    void edit(Usuario usuario);
        
    void remove(Usuario usuario);

    Usuario find(Object id);

    List<Usuario> findAll();

    List<Usuario> findRange(int[] range);

    int count();
    
    public Usuario buscarUsuarioByNickname(String nickname);
    public Usuario buscarUsuarioByNicknamePassword(String nickname, String password);
}
