package com.dane.ige.modelo.servicios.administracion;

import com.dane.ige.modelo.fachada.AbstractFacade;
import com.dane.ige.modelo.local.administracion.ArchivoXlsFacadeLocal;
import com.dane.ige.modelo.entidad.ArchivoXls;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 * Clase que se describe como servicio y que extiende de la clase AbstractFacade
 * con la clase ArchivoXls como parametro y que implementa la interfaz
 * ArchivoXlsFacadeLocal, para brindar los servicios sobre el acceso a los datos
 * a la tabla ige_archivo_xls.
 *
 * @author srojasm
 */
@Stateless(name = "EJBServicioArchivoXls")
public class ArchivoXlsFacade extends AbstractFacade<ArchivoXls> implements ArchivoXlsFacadeLocal {

    final static Logger LOGGER = Logger.getLogger(ArchivoXlsFacade.class);

    @PersistenceContext(unitName = "GrupoEmpresarial-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ArchivoXlsFacade() {
        super(ArchivoXls.class);
    }

    /**
     * Método que permite realizar la consulta a la base de datos y retornar el
     * registro del Archivo Xls enviando como parametro el código del archivo y
     * el evento, (carga o descarga).
     *
     * @param codigoArchivo
     * @param evento
     * @return ArchivoXls
     */
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
            LOGGER.warn(e.getMessage());
        }
        return resultado;
    }
}
