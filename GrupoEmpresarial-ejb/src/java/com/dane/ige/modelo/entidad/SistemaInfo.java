package com.dane.ige.modelo.entidad;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Clase Entity que representa la tabla ige_sistema_info
 *
 * @author srojasm
 */
@Entity
@Table(name = "ige_sistema_info")
@NamedQueries({
    @NamedQuery(name = "SistemaInfo.findAll", query = "SELECT s FROM SistemaInfo s"),
    @NamedQuery(name = "SistemaInfo.findById", query = "SELECT s FROM SistemaInfo s WHERE s.id = :id"),
    @NamedQuery(name = "SistemaInfo.findBySistema", query = "SELECT s FROM SistemaInfo s WHERE s.sistema = :sistema"),
    @NamedQuery(name = "SistemaInfo.findByVersion", query = "SELECT s FROM SistemaInfo s WHERE s.version = :version"),
    @NamedQuery(name = "SistemaInfo.findByFechaVigencia", query = "SELECT s FROM SistemaInfo s WHERE s.fechaVigencia = :fechaVigencia"),
    @NamedQuery(name = "SistemaInfo.findByFechaVigenciaNull", query = "SELECT s FROM SistemaInfo s WHERE s.fechaVigencia is null")})
public class SistemaInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String FIND_BY_FECHA_VIGENCIA_NULL = "SistemaInfo.findByFechaVigenciaNull";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SISTEMA_INFO")
    @SequenceGenerator(name = "SEQ_SISTEMA_INFO", sequenceName = "seq_id_ige_sistema_info", allocationSize = 1)
    @Column(name = "id_ige_sistema_info", unique = true, nullable = false)
    private Integer id;
    @Column(name = "sistema")
    private String sistema;
    @Column(name = "version")
    private String version;
    @Column(name = "fecha_vigencia")
    @Temporal(TemporalType.DATE)
    private Date fechaVigencia;
    @Column(name = "texto_bienvenida")
    private String textoBienvenida;
    @Column(name = "texto_portada")
    private String textoPortada;
    @Column(name = "titulo_proyecto")
    private String tituloProyecto;

    public SistemaInfo() {
    }

    public SistemaInfo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSistema() {
        return sistema;
    }

    public void setSistema(String sistema) {
        this.sistema = sistema;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getFechaVigencia() {
        return fechaVigencia;
    }

    public void setFechaVigencia(Date fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
    }

    public String getTextoBienvenida() {
        return textoBienvenida;
    }

    public String getTextoPortada() {
        return textoPortada;
    }

    public void setTextoPortada(String textoPortada) {
        this.textoPortada = textoPortada;
    }

    public void setTextoBienvenida(String textoBienvenida) {
        this.textoBienvenida = textoBienvenida;
    }

    public String getTituloProyecto() {
        return tituloProyecto;
    }

    public void setTituloProyecto(String tituloProyecto) {
        this.tituloProyecto = tituloProyecto;
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
        if (!(object instanceof SistemaInfo)) {
            return false;
        }
        SistemaInfo other = (SistemaInfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nerv.sai.negocio.entidad.SistemaInfo[ id=" + id + " ]";
    }

}
