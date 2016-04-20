package com.dane.ige.modelo.servicios.administracion;

import com.dane.ige.modelo.entidad.ObjetoIndicador;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
@Stateless(name = "EJBServicioIndicadorGrupo")
public class BodegaIndicadorGrupo {

    final static Logger LOGGER = Logger.getLogger(BodegaIndicadorGrupo.class);

    @PersistenceContext(unitName = "GrupoEmpresarial-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public BodegaIndicadorGrupo() {

    }

    /**
     * Método que permite obtener el listado de resultado del indicador de la
     * distribución de unidadles legales por departamento de un grupo
     * empresarial
     *
     * @param idGrupoRelacionado
     * @return List ObjetoIndicador
     */
    public List<ObjetoIndicador> indicadorUnidadLegalPorDepartamentoDelGrpupo(Long idGrupoRelacionado) {
        List<ObjetoIndicador> resultado = null;
        try {
            String sql = "SELECT NVL(DEPARTAMENTO,'SIN IDENTIFICAR') AS item, count(*) valor FROM IGE_IDENTIFICACION "
                    + "WHERE ID_ORGANIZACION ||'-'||TO_CHAR(FECHA_ACTUALIZA_IDEN,'dd/MM/yyyy HH24:MI:SS') in ( "
                    + "SELECT ID_ORGANIZACION ||'-'||TO_CHAR(FECHA,'dd/MM/yyyy HH24:MI:SS') AS ID FROM ( "
                    + "SELECT ID_ORGANIZACION, MAX (FECHA_ACTUALIZA_IDEN) AS FECHA "
                    + "FROM IGE_IDENTIFICACION "
                    + "WHERE ID_GRUPO_RELACIONADO =" + idGrupoRelacionado + " AND TIPO_ORGANIZACION='UNIDAD LEGAL' "
                    + "GROUP BY ID_ORGANIZACION ORDER BY 1 "
                    + ") "
                    + ")GROUP BY DEPARTAMENTO ORDER BY DEPARTAMENTO";

            Query query = em.createNativeQuery(sql);

            List<Object[]> listaResultado = Collections.EMPTY_LIST;
            listaResultado = query.getResultList();
            if (listaResultado.isEmpty()) {
                return null;
            } else {
                resultado = new ArrayList<ObjetoIndicador>();
                for (Object[] object : listaResultado) {
                    ObjetoIndicador fila = new ObjetoIndicador();
                    fila.setItem(object[0] + "");
                    fila.setValor((Number) object[1]);
                    resultado.add(fila);
                }
            }
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
        }
        return resultado;
    }

    /**
     * Método que permite obtener el listado de resultado del indicador de la
     * distribución de unidadles legales por municipio de un grupo
     * empresarial
     *
     * @param idGrupoRelacionado
     * @return List ObjetoIndicador
     */
    public List<ObjetoIndicador> indicadorUnidadLegalPorMunicipioDelGrpupo(Long idGrupoRelacionado) {
        List<ObjetoIndicador> resultado = null;
        try {
            String sql = "SELECT NVL(MUNICIPIO,'SIN IDENTIFICAR') AS item, count(*) valor FROM IGE_IDENTIFICACION "
                    + "WHERE ID_ORGANIZACION ||'-'||TO_CHAR(FECHA_ACTUALIZA_IDEN,'dd/MM/yyyy HH24:MI:SS') in ( "
                    + "SELECT ID_ORGANIZACION ||'-'||TO_CHAR(FECHA,'dd/MM/yyyy HH24:MI:SS') AS ID FROM ( "
                    + "SELECT ID_ORGANIZACION, MAX (FECHA_ACTUALIZA_IDEN) AS FECHA "
                    + "FROM IGE_IDENTIFICACION "
                    + "WHERE ID_GRUPO_RELACIONADO =" + idGrupoRelacionado + " AND TIPO_ORGANIZACION='UNIDAD LEGAL' "
                    + "GROUP BY ID_ORGANIZACION ORDER BY 1 "
                    + ") "
                    + ")GROUP BY MUNICIPIO ORDER BY MUNICIPIO";

            Query query = em.createNativeQuery(sql);

            List<Object[]> listaResultado = Collections.EMPTY_LIST;
            listaResultado = query.getResultList();
            if (listaResultado.isEmpty()) {
                return null;
            } else {
                resultado = new ArrayList<ObjetoIndicador>();
                for (Object[] object : listaResultado) {
                    ObjetoIndicador fila = new ObjetoIndicador();
                    fila.setItem(object[0] + "");
                    fila.setValor((Number) object[1]);
                    resultado.add(fila);
                }
            }
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
        }
        return resultado;
    }
    
   /**
     * Método que permite obtener el listado de resultado del indicador de la
     * distribución de personal ocupado por unidad legal
     * empresarial
     *
     * @param idGrupoRelacionado
     * @return List ObjetoIndicador
     */
    public List<ObjetoIndicador> indicadorUnidadLegalPorPersonalOcupadoDelGrpupo(Long idGrupoRelacionado) {
        List<ObjetoIndicador> resultado = null;
        try {
            String sql = "SELECT i.NOMBRE_REGISTRADO AS item,  NVL(PERSONAL_OCUPADO,0) AS valor "
                        + "FROM ige_identificacion i "
                        + "JOIN ige_tamano t   ON (i.id_organizacion=t.t_id_organizacion  "
                        + ") "
                        + "WHERE i.id_organizacion  ||'-'  ||TO_CHAR(i.fecha_actualiza_iden,'dd/MM/yyyy HH24:MI:SS') IN "
                        + "  (SELECT id_organizacion    ||'-'    ||TO_CHAR(FECHA,'dd/MM/yyyy HH24:MI:SS') AS ID "
                        + "  FROM "
                        + "    (SELECT ID_ORGANIZACION,  MAX (FECHA_ACTUALIZA_IDEN) AS FECHA  FROM IGE_IDENTIFICACION "
                        + "     WHERE ID_GRUPO_RELACIONADO = " + idGrupoRelacionado + "   AND TIPO_ORGANIZACION ='UNIDAD LEGAL' "
                        + "     GROUP BY ID_ORGANIZACION ORDER BY 1) "
                        + "  ) "
                        + "AND t.t_id_organizacion ||'-'  ||TO_CHAR(t.t_fecha_actualiza,'dd/MM/yyyy HH24:MI:SS') IN ( "
                        + "SELECT ID_ORGANIZACION    ||'-'    ||TO_CHAR(FECHA,'dd/MM/yyyy HH24:MI:SS') AS ID "
                        + "  FROM "
                        + "    (SELECT ID_ORGANIZACION,  MAX (FECHA_ACTUALIZA_IDEN) AS FECHA  FROM IGE_IDENTIFICACION "
                        + "     WHERE ID_GRUPO_RELACIONADO = " + idGrupoRelacionado + "   AND TIPO_ORGANIZACION ='UNIDAD LEGAL' "
                        + "     GROUP BY ID_ORGANIZACION ORDER BY 1) "
                        + ") "
                        + "ORDER BY i.NOMBRE_REGISTRADO ";

            Query query = em.createNativeQuery(sql);

            List<Object[]> listaResultado = Collections.EMPTY_LIST;
            listaResultado = query.getResultList();
            if (listaResultado.isEmpty()) {
                return null;
            } else {
                resultado = new ArrayList<ObjetoIndicador>();
                for (Object[] object : listaResultado) {
                    ObjetoIndicador fila = new ObjetoIndicador();
                    fila.setItem(object[0] + "");
                    fila.setValor((Number) object[1]);
                    resultado.add(fila);
                }
            }
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
        }
        return resultado;
    }
}
