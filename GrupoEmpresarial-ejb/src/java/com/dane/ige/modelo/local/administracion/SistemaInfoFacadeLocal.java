package com.dane.ige.modelo.local.administracion;

import com.dane.ige.modelo.entidad.SistemaInfo;
import java.sql.SQLException;
import java.util.List;
import javax.ejb.Local;
import javax.resource.ResourceException;
import org.hibernate.exception.GenericJDBCException;

/**
 *
 * @author srojasm
 */
@Local
public interface SistemaInfoFacadeLocal {

    void create(SistemaInfo sistemaInfo);

    void edit(SistemaInfo sistemaInfo);

    void remove(SistemaInfo sistemaInfo);

    SistemaInfo find(Object id);

    List<SistemaInfo> findAll();

    List<SistemaInfo> findRange(int[] range);

    int count();

    SistemaInfo obtenerUltimaVersion();

    boolean testConexion() throws ResourceException;

}
