package com.dane.ige.modelo.local.administracion;

import com.dane.ige.modelo.entidad.Municipio;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author srojasm
 */
@Local
public interface MunicipioFacadeLocal {

    void create(Municipio municipio);
    
    void edit(Municipio municipio);
        
    void remove(Municipio municipio);

    Municipio find(Object id);

    List<Municipio> findAll();

    List<Municipio> findRange(int[] range);

    int count();
    
    List<Municipio> findAllInOrderByNameAsc();
    
    List<Municipio> findAllByDepartamentoOrderAsc(String departamento);
}
