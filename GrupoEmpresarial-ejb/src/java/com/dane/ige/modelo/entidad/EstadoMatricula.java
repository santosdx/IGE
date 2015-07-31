package com.dane.ige.modelo.entidad;

import java.io.Serializable;
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
import org.apache.commons.lang3.text.WordUtils;

/**
 *
 * @author srojasm
 */
@Entity
@Table(name = "ige_estado_matricula")
@NamedQueries({
    @NamedQuery(name = "EstadoMatricula.findAllInOrderByNameAsc", query = "SELECT u FROM EstadoMatricula u order by u.estado"),
    @NamedQuery(name = "EstadoMatricula.findById", query = "SELECT u FROM EstadoMatricula u WHERE u.id = :id")})
    
 
public class EstadoMatricula implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String FINE_BYE_FIND_ALL_IN_ORDER_BY_NAME_ASC = "EstadoMatricula.findAllInOrderByNameAsc";

    @Id
    @Column(name = "ID_ESTADO_MATRICULA", unique = true, nullable = false)
    private Integer id;
    @Column(name = "ESTADO_MATRICULA")
    private String estado;
    
    public EstadoMatricula() {
    }

    public EstadoMatricula(Integer id, String estado) {
        this.id = id;
        this.estado = estado;

    }

    public EstadoMatricula(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = WordUtils.capitalize(estado.trim());
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
        if (!(object instanceof EstadoMatricula)) {
            return false;
        }
        EstadoMatricula other = (EstadoMatricula) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nerv.sai.modelo.entidad.EstadoMatricula[ id=" + id + " ]";
    }

}
