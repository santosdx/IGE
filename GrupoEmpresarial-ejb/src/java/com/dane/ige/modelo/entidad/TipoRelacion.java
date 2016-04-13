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
 * Clase Entity que representa la tabla ige_tipo_relacion
 *
 * @author srojasm
 */
@Entity
@Table(name = "ige_tipo_relacion")
@NamedQueries({
    @NamedQuery(name = "TipoRelacion.findAllInOrderByNameAsc", query = "SELECT u FROM TipoRelacion u order by u.tipo"),
    @NamedQuery(name = "TipoRelacion.findById", query = "SELECT u FROM TipoRelacion u WHERE u.id = :id")})

public class TipoRelacion implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String FINE_BYE_FIND_ALL_IN_ORDER_BY_NAME_ASC = "TipoRelacion.findAllInOrderByNameAsc";

    @Id
    @Column(name = "ID_TIPO_RELACION", unique = true, nullable = false)
    private Integer id;
    @Column(name = "TIPO_RELACION")
    private String tipo;

    public TipoRelacion() {
    }

    public TipoRelacion(Integer id, String tipo) {
        this.id = id;
        this.tipo = tipo;

    }

    public TipoRelacion(Integer id) {
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
        if (!(object instanceof TipoRelacion)) {
            return false;
        }
        TipoRelacion other = (TipoRelacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nerv.sai.modelo.entidad.TipoRelacion[ id=" + id + " ]";
    }

}
