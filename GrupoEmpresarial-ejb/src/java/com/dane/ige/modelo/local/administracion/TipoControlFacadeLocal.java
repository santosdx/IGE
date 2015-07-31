package com.dane.ige.modelo.local.administracion;

import com.dane.ige.modelo.entidad.Pais;
import com.dane.ige.modelo.entidad.TipoControl;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author srojasm
 */
@Local
public interface TipoControlFacadeLocal {

    void create(TipoControl tipo);
    
    void edit(TipoControl tipo);
        
    void remove(TipoControl tipo);

    TipoControl find(Object id);

    List<TipoControl> findAll();

    List<TipoControl> findRange(int[] range);

    int count();

    List<TipoControl> findAllInOrderByNameAsc();
}
