package com.dane.ige.modelo.local.administracion;

import com.dane.ige.modelo.entidad.TipoEmpresa;
import java.util.List;
import javax.ejb.Local;

/**
 * Interfaz que describe todos los metodos que podran ser implementados para la
 * entidad TipoEmpresa como servicio.
 *
 * @author srojasm
 */
@Local
public interface TipoEmpresaFacadeLocal {

    void create(TipoEmpresa tipo);

    void edit(TipoEmpresa tipo);

    void remove(TipoEmpresa tipo);

    TipoEmpresa find(Object id);

    List<TipoEmpresa> findAll();

    List<TipoEmpresa> findRange(int[] range);

    int count();

    List<TipoEmpresa> findAllInOrderByNameAsc();

}
