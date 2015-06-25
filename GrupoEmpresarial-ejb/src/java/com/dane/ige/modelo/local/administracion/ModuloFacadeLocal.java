package com.dane.ige.modelo.local.administracion;

import com.dane.ige.modelo.entidad.Modulo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author srojasm
 */
@Local
public interface ModuloFacadeLocal {

    void create(Modulo modulo);
    
    public Integer createAndGetKey(Modulo modulo);

    void edit(Modulo modulo);

    void remove(Modulo modulo);

    Modulo find(Object id);

    List<Modulo> findAll();

    List<Modulo> findRange(int[] range);

    int count();
    
    public Modulo buscarModuloByModulo(String modulo);
    public List<Modulo> getModulesPerfilByIdPerfil(int idPerfil);
    public List<Modulo> getModulesPerfil();
}
