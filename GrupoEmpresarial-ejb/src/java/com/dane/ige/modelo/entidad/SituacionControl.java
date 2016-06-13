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
 * Clase Entity que representa la tabla ige_situacion_control
 *
 * @author srojasm
 */
@Entity
@Table(name = "ige_situacion_control")
@NamedQueries({
    @NamedQuery(name = "SituacionControl.findAllInOrderByNameAsc", query = "SELECT u FROM SituacionControl u order by u.situacion"),
    @NamedQuery(name = "SituacionControl.findAllByeUunidadInOrderByNameAsc", query = "SELECT u FROM SituacionControl u WHERE UPPER(u.unidad) = UPPER(:unidad) order by u.situacion"),
    @NamedQuery(name = "SituacionControl.findById", query = "SELECT u FROM SituacionControl u WHERE u.id = :id")})

public class SituacionControl implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String FINE_BYE_ALL_IN_ORDER_BY_NAME_ASC = "SituacionControl.findAllInOrderByNameAsc";
    public static final String FINE_BYE_ALL_BYE_UNIDAD_IN_ORDER_BY_NAME_ASC = "SituacionControl.findAllByeUunidadInOrderByNameAsc";

    @Id
    @Column(name = "ID_SITUA", unique = true, nullable = false)
    private Integer id;
    @Column(name = "SITUACION_CONTROL")
    private String situacion;
    @Column(name = "UNIDAD")
    private String unidad;
 
    public SituacionControl() {
    }

    public SituacionControl(Integer id, String situacion) {
        this.id = id;
        this.situacion = situacion;

    }

    public SituacionControl(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSituacion() {
        return situacion;
    }

    public void setSituacion(String situacion) {
        this.situacion = WordUtils.capitalize(situacion.trim());
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
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
        if (!(object instanceof SituacionControl)) {
            return false;
        }
        SituacionControl other = (SituacionControl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nerv.sai.modelo.entidad.SituacionControl[ id=" + id + " ]";
    }

}
