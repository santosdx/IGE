package com.dane.ige.modelo.local.administracion;

import com.dane.ige.modelo.entidad.TipoEstablecimiento;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author srojasm
 */
@Local
public interface TipoEstablecimientoFacadeLocal {

    void create(TipoEstablecimiento tipo);
    
    void edit(TipoEstablecimiento tipo);
        
    void remove(TipoEstablecimiento tipo);

    TipoEstablecimiento find(Object id);

    List<TipoEstablecimiento> findAll();

    List<TipoEstablecimiento> findRange(int[] range);

    int count();
    
}
