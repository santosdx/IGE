package com.dane.ige.seguridad;

import com.dane.ige.modelo.entidad.Modulo;
import com.dane.ige.modelo.entidad.Permiso;
import com.dane.ige.modelo.entidad.Usuario;
import com.dane.ige.modelo.local.administracion.ModuloFacadeLocal;
import com.dane.ige.modelo.local.administracion.ModuloPermisoFacadeLocal;
import com.dane.ige.modelo.local.administracion.SistemaInfoFacadeLocal;
import com.dane.ige.modelo.local.administracion.UsuarioFacadeLocal;
import com.dane.ige.utilidad.ArchivoProperties;
import com.dane.ige.utilidad.Mensaje;
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
 * Clase que contiene todos los metodos, funciones y algoritmos para el proceso
 * de acceso al aplicativo.
 *
 * @author srojasm
 */
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

    @EJB
    private SistemaInfoFacadeLocal eJBServicioSistemaInfo;

    private final String pathSistema = "#{request.requestURL.substring(0, request.requestURL.length() - request.requestURI.length())}#{request.contextPath}";
    private Usuario usuarioLogueado;
    private String username;
    private String password;
    private boolean loggedIn;
    private List<Modulo> listaModulos;
    private MenuModel modeloMenu;
    private String versionPlantilla;

    public Login() {
    }

    @PostConstruct
    public void init() {
        construirMenuInicial();
    }

    /**
     * Metodo que permite iniciar una sesion para el usuario en la aplicacion.
     *
     */
    //public void login(ActionEvent event) {
    public void login() {

        if ((getUsername() != null && !getUsername().equals("")) && (getPassword() != null && !getPassword().equals(""))) {
            ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
            String ctxPath = ((ServletContext) ctx.getContext()).getContextPath();
            //LOGGER.info("ctxPath:"+ctxPath);

            RequestContext context = RequestContext.getCurrentInstance();
            FacesMessage message = null;

            String key = ArchivoProperties.obtenerPropertieFilePathProperties("login.password.keyEncrypt");
            ClaseDESBase64 obj = new ClaseDESBase64(key);
            String contrasenaEncriptada = obj.encriptar(getPassword());

            setUsuarioLogueado(geteJBServicioUsuario().buscarUsuarioByNicknamePassword(getUsername(), contrasenaEncriptada));

            if (getUsuarioLogueado() != null) {
                if (getUsuarioLogueado().getEstado().equals("ACTIVO")) {
                    if (getUsuarioLogueado().getPerfil() == null) {
                        Mensaje.agregarMensajeGrowlWarn("Atención", "El usuario no tiene asignado un perfil");
                    } else if (getUsuarioLogueado().getIdIdentificacion() == null) {
                        Mensaje.agregarMensajeGrowlWarn("Atención", "El usuario no tiene asignado un grupo empresarial");
                    } else {
                        setLoggedIn(true);
                        message = construirMenuLogin();
                        FacesContext.getCurrentInstance().addMessage(null, message);
                    }
                } else {
                    setLoggedIn(false);
                    context.addCallbackParam("loggedIn", isLoggedIn());
                    Mensaje.agregarMensajeGrowlError("Error de acceso", "El usuario esta " + getUsuarioLogueado().getEstado());
                }
            } else {
                setLoggedIn(false);
                context.addCallbackParam("loggedIn", isLoggedIn());
                Mensaje.agregarMensajeGrowlError("Error de acceso", "Error al ingresar el nombre de usuario y contraseña");
            }

            context.addCallbackParam("loggedIn", isLoggedIn());

            if (isLoggedIn()) {
                //LOGGER.info("Login-OK");
                LOGGER.info("El usuario (" + getUsuarioLogueado().getNickname() + ") " + getUsuarioLogueado().getNombres() + " " + getUsuarioLogueado().getApellidos() + ", ingreso al sistema");

                //Validamos que versión del aplicativo esta activa
                if (eJBServicioSistemaInfo.obtenerUltimaVersion().getVersion().equals("2")) {
                    if (getUsuarioLogueado().getPerfil().getId().equals(5) || // Perfil: Grupo Empresarial v.2
                            getUsuarioLogueado().getPerfil().getId().equals(6) || // Perfil: Unidad Legal v.2
                            getUsuarioLogueado().getPerfil().getId().equals(7)) { // Perfil: Establecimiento v.2
                        setVersionPlantilla("V2");
                        context.addCallbackParam("view", ctxPath + "/inicio.xhtml");
                    } else {
                        setVersionPlantilla("V1");
                        context.addCallbackParam("view", ctxPath + "/index.xhtml");
                    }
                } else {
                    setVersionPlantilla("V1");
                    context.addCallbackParam("view", ctxPath + "/index.xhtml");
                }
            }
        } else if ((getUsername() == null || getUsername().equals("")) && (getPassword() == null || getPassword().equals(""))) {
            Mensaje.agregarMensajeGrowlError("Atención", "Debe ingresa el nombre de usuario y la contraseña");
        } else if (getUsername() == null || getUsername().equals("")) {
            Mensaje.agregarMensajeGrowlError("Atención", "Debe ingresa el nombre de usuario");
        } else if (getPassword() == null || getPassword().equals("")) {
            Mensaje.agregarMensajeGrowlError("Atención", "Debe ingresa la contraseña");
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
            //itemHome.setStyle("#{view.viewId eq '/index.xhtml' ? 'background:#B6014C !important; color:#FFFFFF;' : ''}");
            getModeloMenu().addElement(itemHome);

            DefaultMenuItem itemGuia = new DefaultMenuItem("Guía de Usuario");
            itemGuia.setUrl("/interfaz/usuario/itz-guia-usuario.xhtml");
            //itemGuia.setStyle("#{view.viewId == '/interfaz/usuario/itz-guia-usuario.xhtml' ? 'background:#B6014C !important; color:#FFFFFF;' : ''}");
            itemGuia.setIcon("fa fa-book");
            //itemGuia.setStyle("#{view.viewId == '/interfaz/usuario/itz-guial-usuario.xhtml' ? 'background:#B6014C !important; color:#FFFFFF;' : ''}");
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
                                //item.setStyle("#{view.viewId == '" + permiso.getUrl() + "' ? 'background:#B6014C !important; color:#FFFFFF;' : ''}");
                            }
                            if (permiso.getComando() != null && !StringUtils.isEmpty(permiso.getComando())) {
                                item.setCommand(permiso.getComando());
                                item.setUrl(null);
                            }
                            if (permiso.getActualizar() != null) {
                                item.setUpdate(permiso.getActualizar());
                            }

                            item.setAjax((permiso.getAjax() != 0));
                            item.setTarget(permiso.getTarget());
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
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            session.invalidate();
            setLoggedIn(false);
            FacesContext contex = FacesContext.getCurrentInstance();
            //contex.getExternalContext().redirect("index.xhtml");
            contex.getExternalContext().redirect("login.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException nex) {
            LOGGER.warn("Variable session is " + nex.getMessage());
        }
    }

    /**
     * Metodo que permite abrir una ventana con el componente login, para el
     * inicio de session.
     *
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
        acceso.cerrarVentanaParametrizada("/interfaz/usuario/ventana/vta-acceso-sistema");
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

    public String getVersionPlantilla() {
        return versionPlantilla;
    }

    public void setVersionPlantilla(String versionPlantilla) {
        this.versionPlantilla = versionPlantilla;
    }

}
