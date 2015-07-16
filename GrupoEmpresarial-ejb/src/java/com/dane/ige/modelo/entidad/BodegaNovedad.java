package com.dane.ige.modelo.entidad;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.apache.commons.lang3.text.WordUtils;

/**
 *
 * @author srojasm
 */
@Entity
@Table(name = "ige_novedad")
@NamedQueries({
    @NamedQuery(name = "BodegaNovedad.findAll", query = "SELECT m FROM BodegaNovedad m"),
    @NamedQuery(name = "BodegaNovedad.findById", query = "SELECT m FROM BodegaNovedad m WHERE m.id = :id")})
public class BodegaNovedad implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String GET_ALL = "BodegaNovedad.findAll";
    public static final String FINE_BYE_ID = "BodegaNovedad.findById";

    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MODULO")
    //@SequenceGenerator(name = "SEQ_MODULO", sequenceName = "seq_id_ige_modulo", allocationSize = 1)
    @Column(name = "n_id_organizacion", unique = true, nullable = false, scale = 0)
    private Integer id;
    @Column(name = "estado")
    private String estado;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_configuracion")
    private Date fechaConfiguracion;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_matricula")
    private Date fechaMatricula;
    @Column(name = "estado_matricula")
    private String estadoMatricula;
    @Column(name = "ultimo_ano_renovado")
    private Integer ultimoAnoRenovado;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_creacion")
    private Date fechaCreacion;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_inicio_acti")
    private Date fechaInicioActi;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_cierre")
    private Date fechaCierre;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_liquidacion")
    private Date fechaLiquidacion;
    @Column(name = "tipo_novedad")
    private String tipoNovedad;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_novedad")
    private Date fechaNovedad;
    @Column(name = "id_grupo_novedad")
    private Integer idGrupoNovedad;
    @Column(name = "nit_ul_involucrada")
    private Integer nitUlInvolucrada;
    @Column(name = "observaciones")
    private String observaciones;

    public BodegaNovedad() {
    }

    public BodegaNovedad(Integer id) {
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
        this.estado = estado;
    }

    public Date getFechaConfiguracion() {
        return fechaConfiguracion;
    }

    public void setFechaConfiguracion(Date fechaConfiguracion) {
        this.fechaConfiguracion = fechaConfiguracion;
    }

    public Date getFechaMatricula() {
        return fechaMatricula;
    }

    public void setFechaMatricula(Date fechaMatricula) {
        this.fechaMatricula = fechaMatricula;
    }

    public String getEstadoMatricula() {
        return estadoMatricula;
    }

    public void setEstadoMatricula(String estadoMatricula) {
        this.estadoMatricula = estadoMatricula;
    }

    public Integer getUltimoAnoRenovado() {
        return ultimoAnoRenovado;
    }

    public void setUltimoAnoRenovado(Integer ultimoAnoRenovado) {
        this.ultimoAnoRenovado = ultimoAnoRenovado;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaInicioActi() {
        return fechaInicioActi;
    }

    public void setFechaInicioActi(Date fechaInicioActi) {
        this.fechaInicioActi = fechaInicioActi;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public Date getFechaLiquidacion() {
        return fechaLiquidacion;
    }

    public void setFechaLiquidacion(Date fechaLiquidacion) {
        this.fechaLiquidacion = fechaLiquidacion;
    }

    public String getTipoNovedad() {
        return tipoNovedad;
    }

    public void setTipoNovedad(String tipoNovedad) {
        this.tipoNovedad = tipoNovedad;
    }

    public Date getFechaNovedad() {
        return fechaNovedad;
    }

    public void setFechaNovedad(Date fechaNovedad) {
        this.fechaNovedad = fechaNovedad;
    }

    public Integer getIdGrupoNovedad() {
        return idGrupoNovedad;
    }

    public void setIdGrupoNovedad(Integer idGrupoNovedad) {
        this.idGrupoNovedad = idGrupoNovedad;
    }

    public Integer getNitUlInvolucrada() {
        return nitUlInvolucrada;
    }

    public void setNitUltimaInvolucrada(Integer nitUlInvolucrada) {
        this.nitUlInvolucrada = nitUlInvolucrada;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    
    
    //---
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BodegaNovedad)) {
            return false;
        }
        BodegaNovedad other = (BodegaNovedad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dabe.ige.modelo.entidad.BodegaIdentificacion[ id=" + id + " ]";
    }

}
