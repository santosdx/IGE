package com.dane.ige.modelo.local.administracion;

import com.dane.ige.modelo.entidad.Pais;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author srojasm
 */
@Local
public interface PaisFacadeLocal {

    void create(Pais pais);
    
    void edit(Pais pais);
        
    void remove(Pais pais);

    Pais find(Object id);

    List<Pais> findAll();

    List<Pais> findRange(int[] range);

    int count();
    
}
