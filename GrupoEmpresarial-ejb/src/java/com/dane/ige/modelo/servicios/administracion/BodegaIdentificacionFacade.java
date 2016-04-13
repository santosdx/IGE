package com.dane.ige.modelo.servicios.administracion;

import com.dane.ige.modelo.entidad.BodegaIdentificacion;
import com.dane.ige.modelo.local.administracion.BodegaIdentificacionFacadeLocal;
import com.dane.ige.modelo.fachada.AbstractFacade;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 * Clase que se describe como servicio y que extiende de la clase AbstractFacade
 * con la clase BodegaIdentificacion como parametro y que implementa la interfaz
 * BodegaIdentificacionFacadeLocal, para brindar los servicios sobre el acceso a
 * los datos a la tabla ige_identificacion.
 *
 * @author srojasm
 */
@Stateless(name = "EJBServicioBodegaIdentificacion")
public class BodegaIdentificacionFacade extends AbstractFacade<BodegaIdentificacion> implements BodegaIdentificacionFacadeLocal {

    final static Logger LOGGER = Logger.getLogger(BodegaIdentificacionFacade.class);

    @PersistenceContext(unitName = "GrupoEmpresarial-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BodegaIdentificacionFacade() {
        super(BodegaIdentificacion.class);
    }

    /**
     * Método que permite obtener el registro de identificación de cualquier
     * tipo de unidad pasando como parametro el ID del grupo empresarial.
     *
     * @param id
     * @param tipoOrganizacion
     * @return BodegaIdentificacion
     */
    @Override
    public BodegaIdentificacion obtenerIdentificacionByIdTipoOrganizacion(Long id, String tipoOrganizacion) {
        BodegaIdentificacion resultado = null;
        try {
            //Query query = em.createNamedQuery(BodegaIdentificacion.FINE_BYE_ID);
            //query.setParameter("id", id);
            //query.setParameter("tipoOrganizacion", tipoOrganizacion);
            String sql = "SELECT * FROM ige_identificacion "
                    + "WHERE id_organizacion = " + id + " "
                    + "AND tipo_organizacion = '" + tipoOrganizacion + "' "
                    + "AND fecha_actualiza_iden = (SELECT MAX(fecha_actualiza_iden) FROM ige_identificacion WHERE id_organizacion = " + id + " AND tipo_organizacion = '" + tipoOrganizacion + "' )";
            Query query = em.createNativeQuery(sql, BodegaIdentificacion.class);

            List<BodegaIdentificacion> listaResultado = Collections.EMPTY_LIST;
            listaResultado = query.getResultList();
            if (listaResultado.isEmpty()) {
                return null;
            } else {
                resultado = listaResultado.get(0);
            }
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
        }
        return resultado;
    }

    /**
     * Método que permite obtener un Map con los datos de identificación del
     * grupo.
     *
     * @param id
     * @param tipoOrganizacion
     * @return Map<String, String>
     */
    @Override
    public Map<String, String> obtenerMapIdentificacionByIdTipoOrganizacion(Long id, String tipoOrganizacion) {
        Map<String, String> resultado = null;
        try {
            //Query query = em.createNamedQuery(BodegaIdentificacion.FINE_BYE_ID);
            //query.setParameter("id", id);
            //query.setParameter("tipoOrganizacion", tipoOrganizacion);
            String sql = "SELECT * FROM ige_identificacion "
                    + "WHERE id_organizacion = " + id + " "
                    + "AND tipo_organizacion = '" + tipoOrganizacion + "' "
                    + "AND fecha_actualiza_iden = (SELECT MAX(fecha_actualiza_iden) FROM ige_identificacion WHERE id_organizacion = " + id + " AND tipo_organizacion = '" + tipoOrganizacion + "' )";
            Query query = em.createNativeQuery(sql, BodegaIdentificacion.class);

            List<BodegaIdentificacion> listaResultado = Collections.EMPTY_LIST;
            listaResultado = query.getResultList();

            if (listaResultado.isEmpty()) {
                return null;
            } else {
                resultado = new HashMap<String, String>();
                resultado = listaResultado.get(0).toMap();
            }
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
        }
        return resultado;
    }

    /**
     * Método que permite obtener el listado de registros de identificación de
     * unidad legal pasando como parametro el ID del grupo empresarial.
     *
     * @param idGrupoRelacionado
     * @return List BodegaIdentificacion
     */
    @Override
    public List<BodegaIdentificacion> obtenerListaIdentificacionUnidadLegalByIdGrupoRelacionadoTipoOrganizacion(Long idGrupoRelacionado) {
        List<BodegaIdentificacion> resultado = null;
        try {
            //Query query = em.createNamedQuery(BodegaIdentificacion.FINE_BYE_ID_GRUPO_RELACIONADO);
            //query.setParameter("idGrupoRelacionado", idGrupoRelacionado);
            //query.setParameter("tipoOrganizacion", tipoOrganizacion);

            String sql = "SELECT * FROM IGE_IDENTIFICACION "
                    + "WHERE ID_ORGANIZACION ||'-'||TO_CHAR(FECHA_ACTUALIZA_IDEN,'dd/MM/yyyy HH24:MI:SS') in ( "
                    + "SELECT ID_ORGANIZACION ||'-'||TO_CHAR(FECHA,'dd/MM/yyyy HH24:MI:SS') AS ID FROM ( "
                    + "SELECT ID_ORGANIZACION, MAX (FECHA_ACTUALIZA_IDEN) AS FECHA "
                    + "FROM IGE_IDENTIFICACION "
                    + "WHERE ID_GRUPO_RELACIONADO =" + idGrupoRelacionado + " AND TIPO_ORGANIZACION='UNIDAD LEGAL' "
                    + "GROUP BY ID_ORGANIZACION ORDER BY 1 "
                    + ") "
                    + ")ORDER BY NOMBRE_REGISTRADO ";

            Query query = em.createNativeQuery(sql, BodegaIdentificacion.class);

            List<BodegaIdentificacion> listaResultado = Collections.EMPTY_LIST;
            listaResultado = query.getResultList();
            if (listaResultado.isEmpty()) {
                return null;
            } else {
                resultado = listaResultado;
            }
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
        }
        return resultado;
    }

    /**
     * Método que permite obtener el listado de registros de identificación de
     * la unidad establecimiento pasando como parametro el ID del grupo
     * empresarial.
     *
     * @param idUnidadLegallacionada
     * @return List-BodegaIdentificacion
     */
    @Override
    public List<BodegaIdentificacion> obtenerListaIdentificacionEstablecimientoByIdGrupoRelacionadoTipoOrganizacion(Long idUnidadLegallacionada) {
        List<BodegaIdentificacion> resultado = null;
        try {
            //Query query = em.createNamedQuery(BodegaIdentificacion.FINE_BYE_ID_GRUPO_RELACIONADO);
            //query.setParameter("idGrupoRelacionado", idGrupoRelacionado);
            //query.setParameter("tipoOrganizacion", tipoOrganizacion);

            String sql = "SELECT * FROM IGE_IDENTIFICACION "
                    + "WHERE ID_ORGANIZACION ||'-'||TO_CHAR(FECHA_ACTUALIZA_IDEN,'dd/MM/yyyy HH24:MI:SS') in ( "
                    + "SELECT ID_ORGANIZACION ||'-'||TO_CHAR(FECHA,'dd/MM/yyyy HH24:MI:SS') AS ID FROM ( "
                    + "SELECT ID_ORGANIZACION, MAX (FECHA_ACTUALIZA_IDEN) AS FECHA "
                    + "FROM IGE_IDENTIFICACION "
                    + "WHERE ID_UL_RELACIONADA =" + idUnidadLegallacionada + " AND TIPO_ORGANIZACION='ESTABLECIMIENTO' "
                    + "GROUP BY ID_ORGANIZACION ORDER BY 1 "
                    + ") "
                    + ")ORDER BY NOMBRE_REGISTRADO ";

            Query query = em.createNativeQuery(sql, BodegaIdentificacion.class);

            List<BodegaIdentificacion> listaResultado = Collections.EMPTY_LIST;
            listaResultado = query.getResultList();
            if (listaResultado.isEmpty()) {
                return null;
            } else {
                resultado = listaResultado;
            }
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
        }
        return resultado;
    }
}
