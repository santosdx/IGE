package com.dane.ige.modelo.local.administracion;

import com.dane.ige.modelo.entidad.ArchivoXls;
import java.util.List;
import javax.ejb.Local;

/**
 * Interfaz que describe todos los metodos que podran ser implementados para la
 * entidad ArchivoXls como servicio.
 *
 * @author srojasm
 */
@Local
public interface ArchivoXlsFacadeLocal {

    void create(ArchivoXls pais);

    void edit(ArchivoXls pais);

    void remove(ArchivoXls pais);

    ArchivoXls find(Object id);

    List<ArchivoXls> findAll();

    List<ArchivoXls> findRange(int[] range);

    int count();

    ArchivoXls findByCodigoArchivo(String codigoArchivo, String evento);

}
