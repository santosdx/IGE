package com.dane.ige.modelo.local.administracion;

import com.dane.ige.modelo.entidad.TipoConglomerado;
import java.util.List;
import javax.ejb.Local;

/**
 * Interfaz que describe todos los metodos que podran ser implementados para la
 * entidad TipoConglomerado como servicio.
 *
 * @author srojasm
 */
@Local
public interface TipoConglomeradoFacadeLocal {

    void create(TipoConglomerado tipo);

    void edit(TipoConglomerado tipo);

    void remove(TipoConglomerado tipo);

    TipoConglomerado find(Object id);

    List<TipoConglomerado> findAll();

    List<TipoConglomerado> findRange(int[] range);

    int count();

    List<TipoConglomerado> findAllInOrderByNameAsc();
}
