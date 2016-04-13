package com.dane.ige.modelo.local.administracion;

import com.dane.ige.modelo.entidad.Departamento;
import java.util.List;
import javax.ejb.Local;

/**
 * Interfaz que describe todos los metodos que podran ser implementados para la
 * entidad Departamento como servicio.
 *
 * @author srojasm
 */
@Local
public interface DepartamentoFacadeLocal {

    void create(Departamento departamento);

    void edit(Departamento departamento);

    void remove(Departamento departamento);

    Departamento find(Object id);

    List<Departamento> findAll();

    List<Departamento> findRange(int[] range);

    int count();

    List<Departamento> findAllInOrderByNameAsc();

}
