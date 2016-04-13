package com.dane.ige.modelo.entidad;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.apache.commons.lang3.text.WordUtils;

/**
 * Clase Entity que representa la tabla ige_usuario
 *
 * @author srojasm
 */
@Entity
@Table(name = "ige_usuario")
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u order by u.nombres ASC"),
    @NamedQuery(name = "Usuario.findById", query = "SELECT u FROM Usuario u WHERE u.id = :id"),
    @NamedQuery(name = "Usuario.findByNickname", query = "SELECT u FROM Usuario u WHERE u.nickname = :nickname"),
    @NamedQuery(name = "Usuario.findByNombres", query = "SELECT u FROM Usuario u WHERE u.nombres = :nombres"),
    @NamedQuery(name = "Usuario.findByApellidos", query = "SELECT u FROM Usuario u WHERE u.apellidos = :apellidos"),
    @NamedQuery(name = "Usuario.findByCorreo", query = "SELECT u FROM Usuario u WHERE UPPER(u.correo) = :correo"),
    @NamedQuery(name = "Usuario.findByNicknamePassword", query = "SELECT u FROM Usuario u WHERE u.nickname = :nickname AND u.password = :password")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String FINE_BYE_NICKNAME = "Usuario.findByNickname";
    public static final String FINE_BYE_NICKNAME_PASSWORD = "Usuario.findByNicknamePassword";
    public static final String FINE_BYE_CORREO = "Usuario.findByCorreo";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USUARIO")
    @SequenceGenerator(name = "SEQ_USUARIO", sequenceName = "seq_id_ige_usuario", allocationSize = 1)
    @Column(name = "id_ige_usuario", unique = true, nullable = false)
    private Integer id;
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "password")
    private String password;
    @Column(name = "nombres")
    private String nombres;
    @Column(name = "apellidos")
    private String apellidos;
    @Column(name = "correo")
    private String correo;
    @Column(name = "ige_iden_id_organizacion")
    private Long idIdentificacion;
    @Column(name = "estado")
    private String estado;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_fin_actividad")
    private Date fechaFinActividad;
    /*
     @OneToOne
     @JoinColumn(name = "id_ige_usuario", referencedColumnName = "id_ige_usuario", insertable = false, updatable = false)
     private Perfil perfil;
     */

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "ige_usuario_perfil",
            joinColumns = @JoinColumn(name = "id_ige_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_ige_perfil")
    )
    private Perfil perfil;

    public Usuario() {
    }

    public Usuario(String nickname, String nombres, String apellidos, String correo) {
        this.nickname = nickname;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
    }

    public Usuario(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = WordUtils.capitalize(nombres.trim());
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = WordUtils.capitalize(apellidos.trim());
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Long getIdIdentificacion() {
        return idIdentificacion;
    }

    public void setIdIdentificacion(Long idIdentificacion) {
        this.idIdentificacion = idIdentificacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaFinActividad() {
        return fechaFinActividad;
    }

    public void setFechaFinActividad(Date fechaFinActividad) {
        this.fechaFinActividad = fechaFinActividad;
    }

    /*
     public List<Perfil> getPerfiles() {
     return perfiles;
     }

     public void setPerfiles(List<Perfil> perfiles) {
     this.perfiles = perfiles;
     }
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nerv.sai.modelo.entidad.Usuario[ id=" + id + " ]";
    }

}
