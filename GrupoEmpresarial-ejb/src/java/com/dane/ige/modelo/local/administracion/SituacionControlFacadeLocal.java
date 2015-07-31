package com.dane.ige.modelo.local.administracion;

import com.dane.ige.modelo.entidad.Pais;
import com.dane.ige.modelo.entidad.SituacionControl;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author srojasm
 */
@Local
public interface SituacionControlFacadeLocal {

    void create(SituacionControl situacion);
    
    void edit(SituacionControl situacion);
        
    void remove(SituacionControl situacion);

    SituacionControl find(Object id);

    List<SituacionControl> findAll();

    List<SituacionControl> findRange(int[] range);

    int count();
    
    List<SituacionControl> findAllInOrderByNameAsc();
}
