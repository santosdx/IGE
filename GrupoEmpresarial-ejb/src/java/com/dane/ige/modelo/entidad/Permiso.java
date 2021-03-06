package com.dane.ige.modelo.entidad;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Clase Entity que representa la tabla ige_permiso
 *
 * @author srojasm
 */
@Entity
@Table(name = "ige_permiso")
@NamedQueries({
    @NamedQuery(name = "Permiso.findAll", query = "SELECT p FROM Permiso p"),
    @NamedQuery(name = "Permiso.findById", query = "SELECT p FROM Permiso p WHERE p.id = :id"),
    @NamedQuery(name = "Permiso.findByPermiso", query = "SELECT p FROM Permiso p WHERE p.permiso = :permiso"),
    @NamedQuery(name = "Permiso.findByDescripcion", query = "SELECT p FROM Permiso p WHERE p.descripcion = :descripcion")})
public class Permiso implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String FINE_BYE_PERMISO = "Permiso.findByPermiso";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PERMISO")
    @SequenceGenerator(name = "SEQ_PERMISO", sequenceName = "seq_id_ige_permiso", allocationSize = 1)
    @Column(name = "id_ige_permiso", unique = true, nullable = false)
    private Integer id;
    @Column(name = "permiso")
    private String permiso;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "url")
    private String url;
    @Column(name = "comando")
    private String comando;
    @Column(name = "actualizar")
    private String actualizar;
    @Column(name = "ajax")
    private Integer ajax;
    @Column(name = "orden")
    private Integer orden;
    @Column(name = "visible")
    private String visible;
    @Column(name = "target")
    private String target;

    public Permiso() {
    }

    public Permiso(Integer id) {
        this.id = id;
    }

    public Permiso(String permiso, String descripcion) {
        this.permiso = permiso;
        this.descripcion = descripcion;
    }

    public Permiso(Integer id, String permiso, String descripcion) {
        this.id = id;
        this.permiso = permiso;
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPermiso() {
        return permiso;
    }

    public void setPermiso(String permiso) {
        //this.permiso = WordUtils.capitalize(permiso.trim());
        this.permiso = permiso.trim();
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getComando() {
        return comando;
    }

    public void setComando(String comando) {
        this.comando = comando;
    }

    public String getActualizar() {
        return actualizar;
    }

    public void setActualizar(String actualizar) {
        this.actualizar = actualizar;
    }

    public Integer getAjax() {
        return ajax;
    }

    public void setAjax(Integer ajax) {
        this.ajax = ajax;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
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
        if (!(object instanceof Permiso)) {
            return false;
        }
        Permiso other = (Permiso) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nerv.sai.modelo.entidad.Permiso[ id=" + id + " ]";
    }

}
