package com.dane.ige.modelo.local.administracion;

import com.dane.ige.modelo.entidad.TipoRelacion;
import java.util.List;
import javax.ejb.Local;

/**
 * Interfaz que describe todos los metodos que podran ser implementados para la
 * entidad TipoRelacion como servicio.
 *
 * @author srojasm
 */
@Local
public interface TipoRelacionFacadeLocal {

    void create(TipoRelacion tipo);

    void edit(TipoRelacion tipo);

    void remove(TipoRelacion tipo);

    TipoRelacion find(Object id);

    List<TipoRelacion> findAll();

    List<TipoRelacion> findRange(int[] range);

    int count();

    List<TipoRelacion> findAllInOrderByNameAsc();
}
