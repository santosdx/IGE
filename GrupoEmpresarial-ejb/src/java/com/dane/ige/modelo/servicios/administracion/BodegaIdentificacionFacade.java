package com.dane.ige.modelo.servicios.administracion;

import com.dane.ige.modelo.entidad.BodegaIdentificacion;
import com.dane.ige.modelo.local.administracion.BodegaIdentificacionFacadeLocal;
import com.dane.ige.modelo.fachada.AbstractFacade;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author srojasm
 */
@Stateless(name = "EJBServicioBodegaIdentificacion")
public class BodegaIdentificacionFacade extends AbstractFacade<BodegaIdentificacion> implements BodegaIdentificacionFacadeLocal {

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
     * Método que permite obtener el registro de identificación de cualquier tipo de unidad
     * pasando como parametro el ID del grupo empresarial.
     * @param id
     * @return 
     */
    @Override
    public BodegaIdentificacion obtenerIdentificacionByIdTipoOrganizacion(Long id, String tipoOrganizacion) {
        BodegaIdentificacion resultado = null;
        try {
            Query query = em.createNamedQuery(BodegaIdentificacion.FINE_BYE_ID);
            query.setParameter("id", id);
            query.setParameter("tipoOrganizacion", tipoOrganizacion);

            List<BodegaIdentificacion> listaResultado = Collections.EMPTY_LIST;
            listaResultado = query.getResultList();
            if (listaResultado.isEmpty()) {
                return null;
            } else {
                resultado = listaResultado.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return resultado;
    }

    /**
     * Método que permite obtener el listado de registros de identificación de cualquier tipo unidad
     * pasando como parametro el ID del grupo empresarial.
     * @param id
     * @return 
     */
    @Override
    public List<BodegaIdentificacion> obtenerListaIdentificacionByIdGrupoRelacionadoTipoOrganizacion(Long idGrupoRelacionado, String tipoOrganizacion) {
        List<BodegaIdentificacion> resultado = null;
        try {
            Query query = em.createNamedQuery(BodegaIdentificacion.FINE_BYE_ID_GRUPO_RELACIONADO);
            query.setParameter("idGrupoRelacionado", idGrupoRelacionado);
            query.setParameter("tipoOrganizacion", tipoOrganizacion);

            List<BodegaIdentificacion> listaResultado = Collections.EMPTY_LIST;
            listaResultado = query.getResultList();
            if (listaResultado.isEmpty()) {
                return null;
            } else {
                resultado = listaResultado;
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return resultado;
    }
}
