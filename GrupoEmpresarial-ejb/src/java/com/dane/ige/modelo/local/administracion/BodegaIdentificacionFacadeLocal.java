package com.dane.ige.modelo.local.administracion;

import com.dane.ige.modelo.entidad.BodegaIdentificacion;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

/**
 * Interfaz que describe todos los metodos que podran ser implementados para la
 * entidad BodegaIdentificacion como servicio.
 *
 * @author srojasm
 */
@Local
public interface BodegaIdentificacionFacadeLocal {

    void create(BodegaIdentificacion identificacion);

    //public Long createAndGetKey(BodegaIdentificacion identificacion);
    void edit(BodegaIdentificacion identificacion);

    void remove(BodegaIdentificacion identificacion);

    BodegaIdentificacion find(Object id);

    List<BodegaIdentificacion> findAll();

    List<BodegaIdentificacion> findRange(int[] range);

    int count();

    BodegaIdentificacion obtenerIdentificacionByIdTipoOrganizacion(Long id, String tipoOrganizacion);

    Map<String, String> obtenerMapIdentificacionByIdTipoOrganizacion(Long id, String tipoOrganizacion);

    List<BodegaIdentificacion> obtenerListaIdentificacionUnidadLegalByIdGrupoRelacionadoTipoOrganizacion(Long idGrupoRelacionado);

    List<BodegaIdentificacion> obtenerListaIdentificacionEstablecimientoByIdGrupoRelacionadoTipoOrganizacion(Long idUnidadLegallacionada);
}
