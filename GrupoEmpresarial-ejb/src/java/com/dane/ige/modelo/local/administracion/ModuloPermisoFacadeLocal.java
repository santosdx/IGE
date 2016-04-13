package com.dane.ige.modelo.local.administracion;

import com.dane.ige.modelo.entidad.ModuloPermiso;
import java.util.List;
import javax.ejb.Local;

/**
 * Interfaz que describe todos los metodos que podran ser implementados para la
 * entidad ModuloPermiso como servicio.
 *
 * @author srojasm
 */
@Local
public interface ModuloPermisoFacadeLocal {

    void create(ModuloPermiso moduloPermiso);

    void edit(ModuloPermiso moduloPermiso);

    void remove(ModuloPermiso moduloPermiso);

    ModuloPermiso find(Object id);

    List<ModuloPermiso> findAll();

    List<ModuloPermiso> findRange(int[] range);

    int count();

    /**
     * Método que retorna la asignación de un permiso a un módulo, pasando como
     * parametro el id del permiso y el id del modulo.
     *
     * @param idModulo
     * @param idPermiso
     * @return ModuloPermiso
     */
    public ModuloPermiso buscarAsignacionModuloPermiso(int idModulo, int idPermiso);

    /**
     * Método que elimina todas la asignaciones de permisos a un módulo, pasando
     * como parametro el id del módulo.
     *
     * @param idModulo
     * @return int
     */
    public int eliminarModuloPermiso(int idModulo);
}
