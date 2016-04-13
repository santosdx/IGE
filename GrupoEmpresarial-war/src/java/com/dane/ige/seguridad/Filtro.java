package com.dane.ige.seguridad;

import com.dane.ige.modelo.entidad.Permiso;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.FacesException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * Servlet Filter, clase que funciona como filtro para las peticiones que
 * realiza la aplicación; cualquier petición acceso o solicitud que no esten
 * registradas para funcionar sin que exista un acceso previo en el aplicativo
 * seran canceladas
 *
 * @author srojasm
 */
public class Filtro implements Filter {

    final static Logger LOGGER = Logger.getLogger(Filtro.class);

    /**
     * Default constructor.
     */
    public Filtro() {

    }

    /**
     * @see Filter#destroy()
     */
    @Override
    public void destroy() {

    }

    /**
     * @param fConfig
     * @throws javax.servlet.ServletException
     * @see Filter#init(FilterConfig)
     */
    @Override
    public void init(FilterConfig fConfig) throws ServletException {

    }

    /**
     * @param request
     * @param response
     * @param chain
     * @throws java.io.IOException
     * @throws javax.servlet.ServletException
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        Login loginBean = (Login) req.getSession().getAttribute("MbLogin");

        //Proceso la URL que está requiriendo el cliente
        String urlStr = "";

        try {
            urlStr = req.getRequestURL().toString();//.toLowerCase();
            boolean noProteger = noProteger(urlStr);
            //LOGGER.info(" - Acceso desprotegido? [" + noProteger + "] -> " +urlStr);

            //Si no requiere protección continúo normalmente.
            if (noProteger(urlStr)) {
                //LOGGER.info("Si no requiere protección continúo normalmente.");
                chain.doFilter(request, response);
                return;
            }
        } catch (FacesException ex) {
            LOGGER.info("Error Servidor -> " + ex);
            res.sendRedirect(req.getContextPath() + "/pagina-error/500.xhtml");
            return;
        }

        //El usuario no está logueado
        if (loginBean != null) {
            if (loginBean.isLoggedIn() == false) {
                LOGGER.info("El usuario no está logueado (Is Logged In = false)");
                res.sendRedirect(req.getContextPath() + "/login.xhtml");
                //res.sendRedirect(req.getContextPath() + "/index.xhtml");
                return;
            }
        } else {
            LOGGER.info("El usuario no está logueado (Bean Login = null)");
            res.sendRedirect(req.getContextPath() + "/login.xhtml");
            //res.sendRedirect(req.getContextPath() + "/index.xhtml");
            return;
        }
        //El recurso requiere protección, pero el usuario ya está logueado.

        //LOGGER.info("Si requiere protección evaluamos los accesos.");
        boolean tieneAcceso = false;
        List<Permiso> permisos = new ArrayList<Permiso>();
        permisos = loginBean.getUsuarioLogueado().getPerfil().getPermisos();
        for (Permiso permiso : permisos) {
            if (permiso.getUrl() != null) {
                if (urlStr.contains(permiso.getUrl())) {
                    tieneAcceso = true;
                    break;
                }
            }
        }
        if (tieneAcceso == true) {
            chain.doFilter(request, response);
            return;
        } else {
            LOGGER.info("Accesos denegado en: " + urlStr);
            res.sendRedirect(req.getContextPath() + "/pagina-error/acceso-denegado.xhtml");
            return;
        }
    }

    /**
     * Método que permite identificar que recurso no esta protegido y permite el
     * acceso.
     *
     * @param urlStr
     * @return [true-false]
     */
    private boolean noProteger(String urlStr) {

        /*
         * Este es un buen lugar para colocar y programar todos los patrones que
         * creamos convenientes para determinar cuales de los recursos no
         * requieren protección. Sin duda que habrá que crear un mecanizmo tal
         * que se obtengan de un archivo de configuración o algo que no requiera
         * compilación.
         */
        if (urlStr.indexOf("/javax.faces.resource/") != -1) {
            return true;
        }
        if (urlStr.indexOf("org.primefaces.extensions.showcase.util.CustomExporterFactory") != -1) {
            return true;
        }
        if (urlStr.indexOf("/resources/") != -1) {
            return true;
        }
        if (urlStr.indexOf("/GrupoEmpresarial/index.xhtml") != -1) {
            return true;
        }
        if (urlStr.indexOf("/GrupoEmpresarial/login.xhtml") != -1) {
            return true;
        }
        if (urlStr.indexOf("/GrupoEmpresarial/recuperar-credenciales.xhtml") != -1) {
            return true;
        }
        if (urlStr.indexOf("/GrupoEmpresarial/interfaz/usuario/itz-guia-usuario.xhtml") != -1) {
            return true;
        }
        if (urlStr.indexOf("/GrupoEmpresarial/interfaz/usuario/ventana/vta-acceso-sistema.xhtml") != -1) {
            return true;
        }
        if (urlStr.indexOf("/GrupoEmpresarial/interfaz/usuario/ventana/cpt-acceso-sistema.xhtml") != -1) {
            return true;
        }
        if (urlStr.indexOf("/GrupoEmpresarial/interfaz/documentacion/java-war/") != -1) {
            return true;
        }
        if (urlStr.indexOf("/GrupoEmpresarial/interfaz/documentacion/java-ejb/") != -1) {
            return true;
        }
        if (urlStr.indexOf("/GrupoEmpresarial/pagina-error/") != -1) {
            return true;
        }
        return false;
    }
}
