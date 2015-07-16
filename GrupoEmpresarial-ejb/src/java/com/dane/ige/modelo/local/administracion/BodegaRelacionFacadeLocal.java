package com.dane.ige.modelo.local.administracion;

import com.dane.ige.modelo.entidad.BodegaRelacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author srojasm
 */
@Local
public interface BodegaRelacionFacadeLocal {

    void create(BodegaRelacion relacion);

    //public Integer createAndGetKey(BodegaRelacion relacion);

    void edit(BodegaRelacion relacion);

    void remove(BodegaRelacion relacion);

    BodegaRelacion find(Object id);

    List<BodegaRelacion> findAll();

    List<BodegaRelacion> findRange(int[] range);

    int count();

    BodegaRelacion obtenerRelacionGrupoEmpresaById(Integer id);
}
