package com.dane.ige.modelo.local.administracion;

import com.dane.ige.modelo.entidad.TipoOrganizacion;
import java.util.List;
import javax.ejb.Local;

/**
 * Interfaz que describe todos los metodos que podran ser implementados para la
 * entidad TipoOrganizacion como servicio.
 *
 * @author srojasm
 */
@Local
public interface TipoOrganizacionFacadeLocal {

    void create(TipoOrganizacion tipo);

    void edit(TipoOrganizacion tipo);

    void remove(TipoOrganizacion tipo);

    TipoOrganizacion find(Object id);

    List<TipoOrganizacion> findAll();

    List<TipoOrganizacion> findRange(int[] range);

    int count();

    List<TipoOrganizacion> findAllInOrderByNameAsc();

}
