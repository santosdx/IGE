
import com.dane.ige.modelo.entidad.SistemaInfo;
import com.dane.ige.modelo.local.administracion.SistemaInfoFacadeLocal;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.resource.ResourceException;
import org.hibernate.exception.GenericJDBCException;

/**
 * Clase que administra el bean de la ventana principal de la aplicación.
 *
 * @author srojasm
 */
@ManagedBean(name = "aplicacionPrincipal")
@SessionScoped
public class AplicacionPrincipal {

    @EJB
    private SistemaInfoFacadeLocal eJBServicioSistemaInfo;

    private SistemaInfo sistemaInfo;

    public AplicacionPrincipal() {

    }

    @PostConstruct
    public void init() {
        //Realizar prueba de conexión a la base de datos
        boolean testConexion = false;
        try {
            testConexion = geteJBServicioSistemaInfo().testConexion();
            testConexion = true;
        } catch (ResourceException e) {
            testConexion = false;
            System.out.println("Error de conexión");
        }

        if (testConexion == true) {
            setSistemaInfo(geteJBServicioSistemaInfo().obtenerUltimaVersion());
        } else {
            FacesContext contex = FacesContext.getCurrentInstance();
            try {
                contex.getExternalContext().redirect("/pagina-error/500.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(AplicacionPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Error de conexión");
        }
    }

    /**
     * Método que permite obtener el nombre del navegador sobre el cual se esta
     * ejecutando la aplicación.
     *
     * @return String
     */
    public String getBrowserName() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String userAgent = externalContext.getRequestHeaderMap().get("User-Agent");

        if (userAgent.contains("MSIE")) {
            return "Internet Explorer";
        }
        if (userAgent.contains("Firefox")) {
            return "Firefox";
        }
        if (userAgent.contains("Chrome")) {
            return "Chrome";
        }
        if (userAgent.contains("Opera")) {
            return "Opera";
        }
        if (userAgent.contains("Safari")) {
            return "Safari";
        }
        return "Unknown";
    }

    // Lista de métodos Set y Get de la clase
    public SistemaInfoFacadeLocal geteJBServicioSistemaInfo() {
        return eJBServicioSistemaInfo;
    }

    public void seteJBServicioSistemaInfo(SistemaInfoFacadeLocal eJBServicioSistemaInfo) {
        this.eJBServicioSistemaInfo = eJBServicioSistemaInfo;
    }

    public SistemaInfo getSistemaInfo() {
        return sistemaInfo;
    }

    public void setSistemaInfo(SistemaInfo sistemaInfo) {
        this.sistemaInfo = sistemaInfo;
    }

}
