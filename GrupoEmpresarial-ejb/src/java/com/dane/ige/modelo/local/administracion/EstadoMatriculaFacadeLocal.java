package com.dane.ige.modelo.local.administracion;

import com.dane.ige.modelo.entidad.EstadoMatricula;
import java.util.List;
import javax.ejb.Local;

/**
 * Interfaz que describe todos los metodos que podran ser implementados para la
 * entidad EstadoMatricula como servicio.
 *
 * @author srojasm
 */
@Local
public interface EstadoMatriculaFacadeLocal {

    void create(EstadoMatricula estado);

    void edit(EstadoMatricula estado);

    void remove(EstadoMatricula estado);

    EstadoMatricula find(Object id);

    List<EstadoMatricula> findAll();

    List<EstadoMatricula> findRange(int[] range);

    int count();

    List<EstadoMatricula> findAllInOrderByNameAsc();

}
