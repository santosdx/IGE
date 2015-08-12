package com.dane.ige.modelo.servicios.administracion;

import com.dane.ige.modelo.fachada.AbstractFacade;
import com.dane.ige.modelo.local.administracion.ArchivoXlsFacadeLocal;
import com.dane.ige.modelo.entidad.ArchivoXls;
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
@Stateless(name = "EJBServicioArchivoXls")
public class ArchivoXlsFacade extends AbstractFacade<ArchivoXls> implements ArchivoXlsFacadeLocal {

    @PersistenceContext(unitName = "GrupoEmpresarial-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ArchivoXlsFacade() {
        super(ArchivoXls.class);
    }

    public ArchivoXls findByCodigoArchivo(String codigoArchivo, String evento) {
        ArchivoXls resultado = new ArchivoXls();
        try {
            Query query = em.createNamedQuery(ArchivoXls.FINE_BYE_CODIGO_ARCHIVO_AND_EVENTO);
            query.setParameter("codigoArchivo", codigoArchivo);
            query.setParameter("evento", evento);

            List<ArchivoXls> listaResultado = Collections.EMPTY_LIST;
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
}
