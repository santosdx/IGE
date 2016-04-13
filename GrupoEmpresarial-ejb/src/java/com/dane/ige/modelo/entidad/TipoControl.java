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
 * Clase Entity que representa la tabla ige_tipo_control
 *
 * @author srojasm
 */
@Entity
@Table(name = "ige_tipo_control")
@NamedQueries({
    @NamedQuery(name = "TipoControl.findAllInOrderByNameAsc", query = "SELECT u FROM TipoControl u order by u.tipo"),
    @NamedQuery(name = "TipoControl.findById", query = "SELECT u FROM TipoControl u WHERE u.id = :id")})

public class TipoControl implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String FINE_BYE_FIND_ALL_IN_ORDER_BY_NAME_ASC = "TipoControl.findAllInOrderByNameAsc";

    @Id
    @Column(name = "ID_TIPO_CONTROL", unique = true, nullable = false)
    private Integer id;
    @Column(name = "TIPO_CONTROL")
    private String tipo;

    public TipoControl() {
    }

    public TipoControl(Integer id, String tipo) {
        this.id = id;
        this.tipo = tipo;

    }

    public TipoControl(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = WordUtils.capitalize(tipo.trim());
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
        if (!(object instanceof TipoControl)) {
            return false;
        }
        TipoControl other = (TipoControl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nerv.sai.modelo.entidad.TipoControl[ id=" + id + " ]";
    }

}
