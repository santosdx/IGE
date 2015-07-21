package com.dane.ige.modelo.local.administracion;

import com.dane.ige.modelo.entidad.BodegaIdentificacion;
import java.util.List;
import javax.ejb.Local;

/**
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

    public List<BodegaIdentificacion> obtenerListaIdentificacionUnidadLegalByIdGrupoRelacionadoTipoOrganizacion(Long idGrupoRelacionado) ;

    public List<BodegaIdentificacion> obtenerListaIdentificacionEstablecimientoByIdGrupoRelacionadoTipoOrganizacion(Long idUnidadLegallacionada) ;
}
