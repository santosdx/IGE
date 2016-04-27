package com.dane.ige.modelo.local.administracion;

import com.dane.ige.modelo.entidad.BodegaTamano;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

/**
 * Interfaz que describe todos los metodos que podran ser implementados para la
 * entidad BodegaTamano como servicio.
 *
 * @author srojasm
 */
@Local
public interface BodegaTamanoFacadeLocal {

    void create(BodegaTamano relacion);

    //public Long createAndGetKey(BodegaTamano tamano);
    void edit(BodegaTamano relacion);

    void remove(BodegaTamano relacion);

    BodegaTamano find(Object id);

    List<BodegaTamano> findAll();

    List<BodegaTamano> findRange(int[] range);

    int count();

    BodegaTamano obtenerTamanoGrupoEmpresaById(Long id);

    Map<String, String> obtenerMapTamanoGrupoEmpresaById(Long id);

    BodegaTamano obtenerRegistroByLlaveCompuesta(String llave);
}
