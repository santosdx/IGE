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
@Table(name = "ige_departamento")
@NamedQueries({
    @NamedQuery(name = "Departamento.findAllInOrderByNameAsc", query = "SELECT u FROM Departamento u order by u.nombre"),
    @NamedQuery(name = "Departamento.findById", query = "SELECT u FROM Departamento u WHERE u.id = :id")
})
public class Departamento implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String FINE_BYE_FIND_ALL_IN_ORDER_BY_NAME_ASC = "Departamento.findAllInOrderByNameAsc";

    @Id
    @Column(name = "id_departamento", unique = true, nullable = false)
    private Integer id;
    @Column(name = "DEPARTAMENTO_NOMBRE")
    private String nombre;

    public Departamento() {
    }

    public Departamento(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;

    }

    public Departamento(Integer id) {
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Departamento)) {
            return false;
        }
        Departamento other = (Departamento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nerv.sai.modelo.entidad.Departamento[ id=" + id + " ]";
    }

}
