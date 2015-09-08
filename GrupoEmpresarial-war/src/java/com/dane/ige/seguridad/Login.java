package com.dane.ige.seguridad;

import com.dane.ige.modelo.entidad.Modulo;
import com.dane.ige.modelo.entidad.Permiso;
import com.dane.ige.modelo.entidad.Usuario;
import com.dane.ige.modelo.local.administracion.ModuloFacadeLocal;
import com.dane.ige.modelo.local.administracion.ModuloPermisoFacadeLocal;
import com.dane.ige.modelo.local.administracion.UsuarioFacadeLocal;
import com.dane.ige.utilidad.Ventana;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

/**
 * Clase de acceso.
 *
 * @author srojasm
 */
//@ManagedBean(name = "MbLogin")
@SessionScoped
public class Login implements Serializable {

    private static final long serialVersionUID = 1L;
    final static Logger LOGGER = Logger.getLogger(Login.class);

    @EJB
    private UsuarioFacadeLocal eJBServicioUsuario;

    @EJB
    private ModuloFacadeLocal eJBServicioModulo;

    @EJB
    private ModuloPermisoFacadeLocal eJBServicioModuloPermiso;

    private final String pathSistema = "#{request.requestURL.substring(0, request.requestURL.length() - request.requestURI.length())}#{request.contextPath}";
    private Usuario usuarioLogueado;
    private String username;
    private String password;
    private boolean loggedIn;
    private List<Modulo> listaModulos;
    private MenuModel modeloMenu;

    public Login() {

    }

    @PostConstruct
    public void init() {
        construirMenuInicial();
    }

    /**
     * Metodo que permite iniciar una sesion para el usuario en la aplicacion.
     *
     * @param event
     */
    //public void login(ActionEvent event) {
    public void login() {

        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        String ctxPath = ((ServletContext) ctx.getContext()).getContextPath();
        //LOGGER.info("ctxPath:"+ctxPath);

        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message = null;

        setUsuarioLogueado(geteJBServicioUsuario().buscarUsuarioByNicknamePassword(username, password));

        if (getUsuarioLogueado() != null) {
            if (getUsuarioLogueado().getPerfil() == null) {
                message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Atención", "El usuario no tiene asignado un perfil");
            } else if (getUsuarioLogueado().getIdIdentificacion() == null) {
                message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Atención", "El usuario no tiene asignado un grupo empresarial");
            } else {
                setLoggedIn(true);
                message = construirMenuLogin();
            }
        } else {
            setLoggedIn(false);
            context.addCallbackParam("loggedIn", isLoggedIn());
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error de acceso", "Credenciales invalidas");
        }

        context.addCallbackParam("loggedIn", isLoggedIn());
        FacesContext.getCurrentInstance().addMessage(null, message);

        if (isLoggedIn()) {
            //LOGGER.info("Login-OK");
            LOGGER.info("El usuario ("+getUsuarioLogueado().getNickname()+") "+getUsuarioLogueado().getNombres() +" "+ getUsuarioLogueado().getApellidos() +", ingreso al sistema");
            context.addCallbackParam("view", ctxPath + "/index.xhtml");
        }
    }


    /**
     * Método que permite contruir dinamicamente el menú principal del
     * aplicativo, con la asignación de modulos y permisos del usuario de
     * acuerdo a su perfil.
     *
     * @return
     */
    private FacesMessage construirMenuLogin() {
        FacesMessage message = null;
        setListaModulos(geteJBServicioModulo().getModulesPerfilByIdPerfil(getUsuarioLogueado().getPerfil().getId()));

        if (getListaModulos() != null && getListaModulos().size() > 0) {
            setModeloMenu(new DefaultMenuModel());

            DefaultMenuItem itemHome = new DefaultMenuItem("Inicio");
            itemHome.setUrl("/index.xhtml");
            itemHome.setIcon("ui-icon-home");
            getModeloMenu().addElement(itemHome);

            DefaultMenuItem itemGuia = new DefaultMenuItem("Guía de Usuario");
            itemGuia.setUrl("/interfaz/usuario/itz-guial-usuario.xhtml");
            itemGuia.setIcon("fa fa-book");
            getModeloMenu().addElement(itemGuia);

            for (Modulo modulo : getListaModulos()) {
                //First submenu
                DefaultSubMenu submenu = new DefaultSubMenu(modulo.getModulo());

                for (Permiso permiso : getUsuarioLogueado().getPerfil().getPermisos()) {
                    if (permiso.getVisible().equals("true")) {
                        if (geteJBServicioModuloPermiso().buscarAsignacionModuloPermiso(modulo.getId(), permiso.getId()) != null) {
                            DefaultMenuItem item = new DefaultMenuItem(permiso.getPermiso());
                            if (permiso.getUrl() != null) {
                                item.setUrl(permiso.getUrl());
                            }
                            if (permiso.getComando() != null && !StringUtils.isEmpty(permiso.getComando())) {
                                item.setCommand(permiso.getComando());
                                item.setUrl(null);
                            }
                            if (permiso.getActualizar() != null) {
                                item.setUpdate(permiso.getActualizar());
                            }

                            item.setAjax((permiso.getAjax() != 0));
                            //item.setIcon("ui-icon-home");
                            submenu.addElement(item);
                            //item.setDisabled(true);
                        }
                    }
                }
                getModeloMenu().addElement(submenu);
            }
            /*
             DefaultSubMenu submenuPerfil = new DefaultSubMenu("Perfil2");
             DefaultMenuItem itemSalir = new DefaultMenuItem("Salir2");
             //itemSalir.setUrl(pathSistema+"/index.xhtml");
             itemSalir.setCommand("#{MbLogin.logout}");
             itemSalir.setIcon("fa-sign-out");
             submenuPerfil.addElement(itemSalir);
             getModeloMenu().addElement(submenuPerfil); 
             */

            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido", usuarioLogueado.getNombres());
        } else {
            setLoggedIn(false);
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Ateción", "El perfil no tiene asignado módulos");
        }
        return message;
    }

    /**
     * Método que permite construir el menú inicial del aplicativo.
     */
    private void construirMenuInicial() {

        FacesMessage message = null;

        setListaModulos(geteJBServicioModulo().getModulesPerfil());

        if (getListaModulos() != null && getListaModulos().size() > 0) {
            setModeloMenu(new DefaultMenuModel());

            DefaultMenuItem itemHome = new DefaultMenuItem("Inicio");
            itemHome.setUrl("/index.xhtml");
            itemHome.setIcon("ui-icon-home");
            getModeloMenu().addElement(itemHome);

            for (Modulo modulo : getListaModulos()) {
                //First submenu
                //DefaultSubMenu submenu = new DefaultSubMenu(modulo.getModulo());

                DefaultMenuItem itemSalir = new DefaultMenuItem(modulo.getModulo());
                itemSalir.setUrl("/login.xhtml");
                //itemSalir.setCommand("#{MbLogin.ventanaLogin}");
                //itemSalir.setCommand("#{dfView.visualizarVentanaParametrizada('/interfaz/usuario/ventana/vta-acceso-sistema',true,true,false,130,320)}");
                //itemSalir.setIcon("fa-sign-out");
                //submenu.addElement(itemSalir);

                getModeloMenu().addElement(itemSalir);
            }

            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido", "");
        } else {
            setLoggedIn(false);
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Ateción", "El perfil no tiene asignado módulos");
        }
    }

    /**
     * Metodo que permite cerrar la sesion del usuario.
     */
    public void logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.invalidate();
        setLoggedIn(false);
        FacesContext contex = FacesContext.getCurrentInstance();
        try {
            //contex.getExternalContext().redirect("index.xhtml");
            contex.getExternalContext().redirect("login.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo que permite abrir una ventana con el componente login, para el
     * inicio de session.
     *
     * @param componente
     */
    public void abrirVentanaLogin() {
        Ventana acceso = new Ventana();
        acceso.visualizarVentanaParametrizada("/interfaz/usuario/ventana/vta-acceso-sistema", true, true, false, 140L, 320L);
    }

    /**
     * Método que permite cerrar la ventana del componente login.
     */
    public void cerrarVentanaLogin() {
        Ventana acceso = new Ventana();
        acceso.cerrarCentanaParametrizada("/interfaz/usuario/ventana/vta-acceso-sistema");
    }

    //Lista métodos Set y Get de la clase
    public UsuarioFacadeLocal geteJBServicioUsuario() {
        return eJBServicioUsuario;
    }

    public void seteJBServicioUsuario(UsuarioFacadeLocal eJBServicioUsuario) {
        this.eJBServicioUsuario = eJBServicioUsuario;
    }

    public ModuloFacadeLocal geteJBServicioModulo() {
        return eJBServicioModulo;
    }

    public void seteJBServicioModulo(ModuloFacadeLocal eJBServicioModulo) {
        this.eJBServicioModulo = eJBServicioModulo;
    }

    public ModuloPermisoFacadeLocal geteJBServicioModuloPermiso() {
        return eJBServicioModuloPermiso;
    }

    public void seteJBServicioModuloPermiso(ModuloPermisoFacadeLocal eJBServicioModuloPermiso) {
        this.eJBServicioModuloPermiso = eJBServicioModuloPermiso;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the loggedIn
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }

    /**
     * @param loggedIn the loggedIn to set
     */
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public Usuario getUsuarioLogueado() {
        return usuarioLogueado;
    }

    public void setUsuarioLogueado(Usuario usuarioLogueado) {
        this.usuarioLogueado = usuarioLogueado;
    }

    public List<Modulo> getListaModulos() {
        return listaModulos;
    }

    public void setListaModulos(List<Modulo> listaModulos) {
        this.listaModulos = listaModulos;
    }

    public MenuModel getModeloMenu() {
        return modeloMenu;
    }

    public void setModeloMenu(MenuModel modeloMenu) {
        this.modeloMenu = modeloMenu;
    }

}
