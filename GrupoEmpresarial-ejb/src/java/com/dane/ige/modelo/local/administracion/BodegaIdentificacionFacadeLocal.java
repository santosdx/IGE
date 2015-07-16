package com.dane.ige.modelo.local.administracion;

import com.dane.ige.modelo.entidad.BodegaIdentificacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author srojasm
 */
@Local
public interface BodegaIdentificacionFacadeLocal {

    void create(BodegaIdentificacion identificacion);

    //public Integer createAndGetKey(BodegaIdentificacion identificacion);

    void edit(BodegaIdentificacion identificacion);

    void remove(BodegaIdentificacion identificacion);

    BodegaIdentificacion find(Object id);

    List<BodegaIdentificacion> findAll();

    List<BodegaIdentificacion> findRange(int[] range);

    int count();

    BodegaIdentificacion obtenerIdentificacionGrupoEmpresaById(Integer id);
}
