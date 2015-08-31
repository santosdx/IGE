package com.dane.ige.modelo.entidad;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.apache.commons.lang3.text.WordUtils;

/**
 *
 * @author srojasm
 */
@Entity
@Table(name = "ige_municipio")
@NamedQueries({
    @NamedQuery(name = "Municipio.findAllInOrderByNameAsc", query = "SELECT u FROM Municipio u order by u.nombre"),
    @NamedQuery(name = "Municipio.findById", query = "SELECT u FROM Municipio u WHERE u.id = :id"),
    @NamedQuery(name = "Municipio.findAllByDepartamentoOrderAsc", query = "SELECT u FROM Municipio u WHERE u.departamento = :departamento ORDER BY u.nombre")
})
public class Municipio implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String FINE_BYE_FIND_ALL_IN_ORDER_BY_NAME_ASC = "Municipio.findAllInOrderByNameAsc";
    public static final String FINE_BYE_FIND_ALL_BY_DEPARTAMENTO_ORDER_ASC = "Municipio.findAllByDepartamentoOrderAsc";

    @Id
    @Column(name = "ID_MUNICIPIO", unique = true, nullable = false)
    private Integer id;
    @Column(name = "MUNICIPIO_NOMBRE")
    private String nombre;
    @Column(name = "DEPARTAMENTO_NOMBRE")
    private String departamento;

    public Municipio() {
    }

    public Municipio(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Municipio(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = WordUtils.capitalize(nombre.trim());
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
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
        if (!(object instanceof Municipio)) {
            return false;
        }
        Municipio other = (Municipio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nerv.sai.modelo.entidad.Municipio[ id=" + id + " ]";
    }

}
