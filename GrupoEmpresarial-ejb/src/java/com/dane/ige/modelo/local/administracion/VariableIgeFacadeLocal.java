package com.dane.ige.modelo.local.administracion;

import com.dane.ige.modelo.entidad.VariableIge;
import java.util.List;
import javax.ejb.Local;

/**
 * Interfaz que describe todos los metodos que podran ser implementados para la
 * entidad VariableIge como servicio.
 *
 * @author srojasm
 */
@Local
public interface VariableIgeFacadeLocal {

    void create(VariableIge variableIge);

    public Integer createAndGetKey(VariableIge variableIge);

    void edit(VariableIge variableIge);

    void remove(VariableIge variableIge);

    VariableIge find(Object id);

    List<VariableIge> findAll();

    List<VariableIge> findRange(int[] range);

    int count();

    List<VariableIge> buscarVariableByGrupo(String grupo);
}
