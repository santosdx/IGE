package com.dane.ige.modelo.entidad;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Clase Entity que representa la tabla ige_archivo_xls
 *
 * @author srojasm
 */
@Entity
@Table(name = "ige_archivo_xls")
@NamedQueries({
    @NamedQuery(name = "ArchivoXls.findAll", query = "SELECT a FROM ArchivoXls a"),
    @NamedQuery(name = "ArchivoXls.findById", query = "SELECT a FROM ArchivoXls a WHERE a.id = :id"),
    @NamedQuery(name = "ArchivoXls.findByCodigoArchivoEvento", query = "SELECT a FROM ArchivoXls a WHERE a.codigoArchivo = :codigoArchivo AND a.evento =:evento")
})
public class ArchivoXls implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String FINE_ALL = "ArchivoXls.findAll";
    public static final String FINE_BYE_ID = "ArchivoXls.findById";
    public static final String FINE_BYE_CODIGO_ARCHIVO_AND_EVENTO = "ArchivoXls.findByCodigoArchivoEvento";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ARCHIVO")
    @SequenceGenerator(name = "SEQ_ARCHIVO", sequenceName = "SEQ_ID_IGE_ARCHIVO_XLS", allocationSize = 1)
    @Column(name = "id_archivo_xls", unique = true, nullable = false)
    private Integer id;
    @Column(name = "id_grupo")
    private Long idGrupo;
    @Column(name = "unidad")
    private String unidad;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_evento")
    private Date fechaEvento;
    @Column(name = "evento")
    private String evento;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "codigo_archivo")
    private String codigoArchivo;
    @Lob
    @Column(name = "archivo")
    private byte[] archivo;

    public ArchivoXls() {
    }

    public ArchivoXls(Long idGrupo, String unidad, Date fechaEvento, String evento, Integer idUsuario, String codigoArchivo, byte[] archivo) {
        this.idGrupo = idGrupo;
        this.unidad = unidad;
        this.fechaEvento = fechaEvento;
        this.evento = evento;
        this.idUsuario = idUsuario;
        this.codigoArchivo = codigoArchivo;
        this.archivo = archivo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Long idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public Date getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(Date fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCodigoArchivo() {
        return codigoArchivo;
    }

    public void setCodigoArchivo(String codigoArchivo) {
        this.codigoArchivo = codigoArchivo;
    }

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ArchivoXls)) {
            return false;
        }
        ArchivoXls other = (ArchivoXls) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nerv.sai.modelo.entidad.ArchivoXls[ id=" + id + " ]";
    }

}
