package com.dane.ige.modelo.local.administracion;

import com.dane.ige.modelo.entidad.PerfilPermiso;
import java.util.List;
import javax.ejb.Local;

/**
 * Interfaz que describe todos los metodos que podran ser implementados para la
 * entidad PerfilPermiso como servicio.
 *
 * @author srojasm
 */
@Local
public interface PerfilPermisoFacadeLocal {

    void create(PerfilPermiso perfilPermiso);

    void edit(PerfilPermiso perfilPermiso);

    void remove(PerfilPermiso perfilPermiso);

    PerfilPermiso find(Object id);

    List<PerfilPermiso> findAll();

    List<PerfilPermiso> findRange(int[] range);

    int count();

    public PerfilPermiso buscarAsignacionPermisoPerfil(int idPermiso, int idPerfil);

    public int eliminarPermisosPerfil(int idPerfil);
}
