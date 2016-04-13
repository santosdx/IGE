package com.dane.ige.modelo.local.administracion;

import com.dane.ige.modelo.entidad.Perfil;
import java.util.List;
import javax.ejb.Local;

/**
 * Interfaz que describe todos los metodos que podran ser implementados para la
 * entidad Perfil como servicio.
 *
 * @author srojasm
 */
@Local
public interface PerfilFacadeLocal {

    void create(Perfil perfil);
    
    public Integer createAndGetKey(Perfil perfil);

    void edit(Perfil perfil);

    void remove(Perfil perfil);

    Perfil find(Object id);

    List<Perfil> findAll();

    List<Perfil> findRange(int[] range);

    int count();
    
    public Perfil buscarPerfilByPerfil(String perfil);
}
