package com.dane.ige.modelo.local.administracion;

import com.dane.ige.modelo.entidad.BodegaRelacion;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

/**
 * Interfaz que describe todos los metodos que podran ser implementados para la
 * entidad BodegaRelacion como servicio.
 *
 * @author srojasm
 */
@Local
public interface BodegaRelacionFacadeLocal {

    void create(BodegaRelacion relacion);

    //public Long createAndGetKey(BodegaRelacion relacion);
    void edit(BodegaRelacion relacion);

    void remove(BodegaRelacion relacion);

    BodegaRelacion find(Object id);

    List<BodegaRelacion> findAll();

    List<BodegaRelacion> findRange(int[] range);

    int count();

    BodegaRelacion obtenerRelacionGrupoEmpresaById(Long id);

    Map<String, String> obtenerMapRelacionGrupoEmpresaById(Long id);

    BodegaRelacion obtenerRegistroByLlaveCompuesta(String llave);
}
