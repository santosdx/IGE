package com.dane.ige.administracion;

import com.dane.ige.modelo.entidad.BodegaIdentificacion;
import com.dane.ige.modelo.entidad.Usuario;
import com.dane.ige.modelo.entidad.UsuarioPerfil;
import com.dane.ige.modelo.local.administracion.BodegaIdentificacionFacadeLocal;
import com.dane.ige.modelo.local.administracion.UsuarioFacadeLocal;
import com.dane.ige.modelo.local.administracion.UsuarioPerfilFacadeLocal;
import com.dane.ige.seguridad.ClaseDESBase64;
import com.dane.ige.utilidad.ArchivoProperties;
import com.dane.ige.utilidad.Mensaje;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;

/**
 * Clase que maneja la entidad Usuario y la Vista de administración de usuarios.
 *
 * @author srojasm
 */
@ManagedBean(name = "MbAdministrarUsuario")
@ViewScoped
public class AdministrarUsuario implements Serializable {

    final static Logger LOGGER = Logger.getLogger(AdministrarUsuario.class);

    @EJB
    private UsuarioFacadeLocal eJBServicioUsuario;

    @EJB
    private UsuarioPerfilFacadeLocal eJBServicioUsuarioPerfil;

    @EJB
    private BodegaIdentificacionFacadeLocal eJBServicioBodegaIdentificacion;

    @ManagedProperty("#{MbAdministrarPerfil}")
    private AdministrarPerfil servicioPerfil;

    private boolean esNuevoUsuario;

    private List<Usuario> listaUsuarios;
    private Usuario usuarioSeleccionado;
    private String contrasenaActualizarUsuario;
    private List<BodegaIdentificacion> listaIdentificacionGrupos;

    public AdministrarUsuario() {
    }

    @PostConstruct
    public void init() {
        inicializarVariables();
    }

    /**
     * Método que permite inicializar las variables necesarias para el
     * funcionamiento de los metos de crear y actualiar.
     */
    private void inicializarVariables() {
        setListaUsuarios(geteJBServicioUsuario().listarTodosLosUsuariosEnOrdenNombre());
        setUsuarioSeleccionado(new Usuario(null, null, null, null));
        getServicioPerfil().setPerfilSeleccionado(null);
        setEsNuevoUsuario(true);
        setContrasenaActualizarUsuario(null);
        setListaIdentificacionGrupos(eJBServicioBodegaIdentificacion.obtenerListaIdentificacionTodosLosGrupos());
    }

    /**
     * Método que permite crear un nuevo usuario.
     */
    public void nuevoUsuario() {
        if (getUsuarioSeleccionado().getNickname() != null) {
            if (geteJBServicioUsuario().buscarUsuarioByNickname(getUsuarioSeleccionado().getNickname()) == null) {

                String key = ArchivoProperties.obtenerPropertieFilePathProperties("login.password.keyEncrypt");
                ClaseDESBase64 obj = new ClaseDESBase64(key);
                String contrasenDesencriptada = obj.encriptar(getUsuarioSeleccionado().getPassword());
                getUsuarioSeleccionado().setPassword(contrasenDesencriptada);

                Integer idUsuario = geteJBServicioUsuario().createAndGetKey(getUsuarioSeleccionado());
                Integer idPerfil = getServicioPerfil().getPerfilSeleccionado().getId();

                geteJBServicioUsuarioPerfil().create(new UsuarioPerfil(idUsuario, idPerfil));

                inicializarVariables();

                //LOGGER.info("Id usuario:"+idUsuario);
                //LOGGER.info("Id perfil:"+idPerfil);
                //LOGGER.info("nuevo usuario");
                Mensaje.agregarMensajeGrowlInfo("Exito!", "Nuevo usuario agregado.");
            } else {
                //LOGGER.info("el usuario ya existe");
                Mensaje.agregarMensajeGrowlWarn("Atención!", "El usuario ya existe.");
            }
        } else {
            //LOGGER.info("ingresar datos usuario");
            Mensaje.agregarMensajeGrowlWarn("Atención!", "Debe ingresar los datos.");
        }
    }

    /**
     * Método que permite actualizar los datos del usuario.
     */
    public void actualziarUsuario() {
        if (getUsuarioSeleccionado().getId() != null) {

            //Validamos si el usuario sera inactivado para registrar la fecha
            if (!getUsuarioSeleccionado().getEstado().equals("ACTIVO")) {
                getUsuarioSeleccionado().setFechaFinActividad(new Date());
            } else {
                getUsuarioSeleccionado().setFechaFinActividad(null);
            }

            if (!getContrasenaActualizarUsuario().equals(getUsuarioSeleccionado().getPassword())) {
                String key = ArchivoProperties.obtenerPropertieFilePathProperties("login.password.keyEncrypt");
                ClaseDESBase64 obj = new ClaseDESBase64(key);
                String contrasenEncriptada = obj.encriptar(getContrasenaActualizarUsuario());
                getUsuarioSeleccionado().setPassword(contrasenEncriptada);
            }

            geteJBServicioUsuario().edit(getUsuarioSeleccionado());

            if (getServicioPerfil().getPerfilSeleccionado() != null) {

                Integer idUsuario = getUsuarioSeleccionado().getId();
                Integer idPerfil = getServicioPerfil().getPerfilSeleccionado().getId();

                if (getUsuarioSeleccionado().getPerfil() == null) {
                    geteJBServicioUsuarioPerfil().create(new UsuarioPerfil(idUsuario, idPerfil));
                } else {
                    if (getUsuarioSeleccionado().getPerfil().getId().compareTo(getServicioPerfil().getPerfilSeleccionado().getId()) != 0) {
                        UsuarioPerfil usuarioPerfil = geteJBServicioUsuarioPerfil().buscarAsignacionUsuarioPerfil(idUsuario, getUsuarioSeleccionado().getPerfil().getId());
                        usuarioPerfil.setIdPerfil(getServicioPerfil().getPerfilSeleccionado().getId());
                        geteJBServicioUsuarioPerfil().edit(usuarioPerfil);
                    } else {
                        Mensaje.agregarMensajeGrowlWarn("Avertencia!", "El perfil seleccionado ya esta asignado.");
                    }
                }
            } else {
                Mensaje.agregarMensajeGrowlWarn("Avertencia!", "No selecciono un perfil para el usuario.");
            }

            inicializarVariables();
            //LOGGER.info("actualizo usuario");
            Mensaje.agregarMensajeGrowlInfo("Exito!", "Usuario actualizado.");
        } else {
            //LOGGER.info("seleccionar usuario");
            Mensaje.agregarMensajeGrowlWarn("Atención!", "Debe seleccionar un usuario.");
        }
    }

    /**
     * Método que permite actualizar los datos del usuario, pasando como
     * parametro el objeto usuario.
     *
     * @param usuario
     */
    public void actualziarUsuarioPerfil(Usuario usuario) {

        String key = ArchivoProperties.obtenerPropertieFilePathProperties("login.password.keyEncrypt");
        ClaseDESBase64 obj = new ClaseDESBase64(key);
        String contrasenEncriptada = null;

        for (Usuario usuarioLista : getListaUsuarios()) {
            if (usuarioLista.getId().compareTo(usuario.getId()) == 0) {
                if (!usuarioLista.getPassword().equals(usuario.getPassword())) {
                    contrasenEncriptada = obj.encriptar(usuario.getPassword());
                    usuario.setPassword(contrasenEncriptada);
                }
            }
        }

        geteJBServicioUsuario().edit(usuario);
        //LOGGER.info("actualizo usuario");
        Mensaje.agregarMensajeGrowlInfo("Exito!", "Usuario actualizado.");
    }

    /**
     * Método que permite seleccionar en la lista de perfiles, el perfil que
     * tiene el usuario
     */
    public void seleccionarUsuario() {
        if (getUsuarioSeleccionado().getPerfil() != null) {
            getServicioPerfil().setPerfilSeleccionado(getServicioPerfil().getPerfilById(getUsuarioSeleccionado().getPerfil().getId()));
        } else {
            getServicioPerfil().setPerfilSeleccionado(null);
        }
        setContrasenaActualizarUsuario(getUsuarioSeleccionado().getPassword());
        //getUsuarioSeleccionado().getId();
        //LOGGER.info("Usuario seleccionado: "+getUsuarioSeleccionado().getId());
    }

    // Métodos Set y Get para los atributos de la clase
    public UsuarioFacadeLocal geteJBServicioUsuario() {
        return eJBServicioUsuario;
    }

    public void seteJBServicioUsuario(UsuarioFacadeLocal eJBServicioUsuario) {
        this.eJBServicioUsuario = eJBServicioUsuario;
    }

    public UsuarioPerfilFacadeLocal geteJBServicioUsuarioPerfil() {
        return eJBServicioUsuarioPerfil;
    }

    public void seteJBServicioUsuarioPerfil(UsuarioPerfilFacadeLocal eJBServicioUsuarioPerfil) {
        this.eJBServicioUsuarioPerfil = eJBServicioUsuarioPerfil;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public Usuario getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    public void setUsuarioSeleccionado(Usuario usuarioSeleccionado) {
        setEsNuevoUsuario(false);
        this.usuarioSeleccionado = usuarioSeleccionado;
    }

    public boolean getEsNuevoUsuario() {
        return esNuevoUsuario;
    }

    public void setEsNuevoUsuario(boolean esNuevoUsuario) {
        this.esNuevoUsuario = esNuevoUsuario;
    }

    public AdministrarPerfil getServicioPerfil() {
        return servicioPerfil;
    }

    public void setServicioPerfil(AdministrarPerfil servicioPerfil) {
        this.servicioPerfil = servicioPerfil;
    }

    public String getContrasenaActualizarUsuario() {
        return contrasenaActualizarUsuario;
    }

    public void setContrasenaActualizarUsuario(String contrasenaActualizarUsuario) {
        this.contrasenaActualizarUsuario = contrasenaActualizarUsuario;
    }

    public List<BodegaIdentificacion> getListaIdentificacionGrupos() {
        return listaIdentificacionGrupos;
    }

    public void setListaIdentificacionGrupos(List<BodegaIdentificacion> listaIdentificacionGrupos) {
        this.listaIdentificacionGrupos = listaIdentificacionGrupos;
    }

}
