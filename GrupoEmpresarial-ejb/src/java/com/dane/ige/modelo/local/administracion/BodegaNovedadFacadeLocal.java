package com.dane.ige.modelo.local.administracion;

import com.dane.ige.modelo.entidad.BodegaNovedad;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author srojasm
 */
@Local
public interface BodegaNovedadFacadeLocal {

    void create(BodegaNovedad novedad);

    //public Integer createAndGetKey(BodegaNovedad identificacion);

    void edit(BodegaNovedad novedad);

    void remove(BodegaNovedad novedad);

    BodegaNovedad find(Object id);

    List<BodegaNovedad> findAll();

    List<BodegaNovedad> findRange(int[] range);

    int count();
    
    BodegaNovedad obtenerNovedadGrupoEmpresaById(Integer id);

}
