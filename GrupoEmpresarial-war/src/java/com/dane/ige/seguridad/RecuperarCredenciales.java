package com.dane.ige.seguridad;

import com.dane.ige.mail.EmailUtils;
import com.dane.ige.modelo.entidad.Usuario;
import com.dane.ige.modelo.local.administracion.UsuarioFacadeLocal;
import com.dane.ige.utilidad.ArchivoProperties;
import com.dane.ige.utilidad.Mensaje;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.commons.mail.EmailException;

/**
 * Clase que brinda la funcionalidad para la recuperación de las credenciales de
 * (usuario y contraseña) a un usuario del aplicativo.
 *
 * @author SRojasM
 */
@ManagedBean(name = "MbRecuperarCredenciales")
@ViewScoped
public class RecuperarCredenciales implements Serializable {

    @EJB
    private UsuarioFacadeLocal eJBServicioUsuario;

    private String correo;
    private boolean correoValidado;

    /**
     * Método que permite validar el correo ingresado con los registros de los
     * usuarios; al encontrar Coincidencia se envia un correo con los datos de
     * acceso del usuario propietario del correo
     *
     * @throws EmailException
     */
    public void recuperarCredenciales() throws EmailException {
        Usuario usuarioRecuperacion = eJBServicioUsuario.buscarUsuarioByCorreo(getCorreo());
        if (usuarioRecuperacion != null) {
            if (usuarioRecuperacion.getEstado().equals("ACTIVO")) {

                Map<Integer, String> destinatarios = new HashMap<Integer, String>();
                destinatarios.put(1, getCorreo());
                EmailUtils correoEnviar = new EmailUtils();
                StringBuilder cuerpoCorreo = new StringBuilder();
                cuerpoCorreo.append("" + correoEnviar.obtenerSaludoMensaje());
                cuerpoCorreo.append("<br>");
                cuerpoCorreo.append("<p>Los datos de acceso para el usuario: " + usuarioRecuperacion.getNombres() + " " + usuarioRecuperacion.getApellidos() + ", son los siguientes:</p>");
                cuerpoCorreo.append("<p>Usuario: " + usuarioRecuperacion.getNickname() + " <br>");

                String key = ArchivoProperties.obtenerPropertieFilePathProperties("login.password.keyEncrypt");
                ClaseDESBase64 obj = new ClaseDESBase64(key);
                String contrasenDesencriptada = obj.desencriptar(usuarioRecuperacion.getPassword());
                cuerpoCorreo.append("Contraseña: " + contrasenDesencriptada + "</p>");
                cuerpoCorreo.append("<br>");
                cuerpoCorreo.append("<a href=\"http://localhost:7070/GrupoEmpresarial/login.xhtml\">Investigación Grupo Empresa</a>");

                correoEnviar.envioFinalHtmlEmail("Recuperación de Credenciales IGE", cuerpoCorreo.toString(), destinatarios);
                Mensaje.agregarMensajeGrowlInfo("Exito!", "Se han enviado los datos de acceso al correo: " + getCorreo() + "");
                setCorreo("");
            } else {
                Mensaje.agregarMensajeGrowlWarn("Atención!", "El usuario del correo: " + getCorreo() + " esta " + usuarioRecuperacion.getEstado() + ", debe ponerse en contacto con el administrador.");
            }
        } else {
            Mensaje.agregarMensajeGrowlWarn("Atención!", "El correo: " + getCorreo() + " no se encuentra registrado en la plataforma.");
        }
    }

    public boolean isCorreoValidado() {
        return correoValidado;
    }

    public void setCorreoValidado(boolean correoValidado) {
        this.correoValidado = correoValidado;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

}
