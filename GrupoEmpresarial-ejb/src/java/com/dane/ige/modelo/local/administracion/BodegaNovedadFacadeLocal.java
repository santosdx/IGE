package com.dane.ige.modelo.local.administracion;

import com.dane.ige.modelo.entidad.BodegaNovedad;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

/**
 *
 * @author srojasm
 */
@Local
public interface BodegaNovedadFacadeLocal {

    void create(BodegaNovedad novedad);

    //public Long createAndGetKey(BodegaNovedad identificacion);
    void edit(BodegaNovedad novedad);

    void remove(BodegaNovedad novedad);

    BodegaNovedad find(Object id);

    List<BodegaNovedad> findAll();

    List<BodegaNovedad> findRange(int[] range);

    int count();

    BodegaNovedad obtenerNovedadGrupoEmpresaById(Long id);

    Map<String, String> obtenerMapNovedadGrupoEmpresaById(Long id);

}
