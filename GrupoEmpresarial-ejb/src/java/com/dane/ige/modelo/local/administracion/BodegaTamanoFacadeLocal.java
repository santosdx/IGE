package com.dane.ige.modelo.local.administracion;

import com.dane.ige.modelo.entidad.BodegaTamano;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author srojasm
 */
@Local
public interface BodegaTamanoFacadeLocal {

    void create(BodegaTamano relacion);

    //public Integer createAndGetKey(BodegaTamano tamano);
    void edit(BodegaTamano relacion);

    void remove(BodegaTamano relacion);

    BodegaTamano find(Object id);

    List<BodegaTamano> findAll();

    List<BodegaTamano> findRange(int[] range);

    int count();

    BodegaTamano obtenerTamanoGrupoEmpresaById(Integer id);

}
