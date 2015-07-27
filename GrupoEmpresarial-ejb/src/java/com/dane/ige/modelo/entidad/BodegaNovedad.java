package com.dane.ige.modelo.entidad;

import com.dane.ige.modelo.llave.PkNovedad;
import com.dane.ige.utilidad.Texto;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author srojasm
 */
@Entity
@Table(name = "ige_novedad")
@NamedQueries({
    @NamedQuery(name = "BodegaNovedad.findAll", query = "SELECT m FROM BodegaNovedad m"), 
    //@NamedQuery(name = "BodegaNovedad.findById",query = "SELECT m FROM BodegaNovedad m WHERE m.id.id = :id AND ROWNUM<=1 ORDER BY m.id.fecha DESC")
})
public class BodegaNovedad implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String GET_ALL = "BodegaNovedad.findAll";
    // public static final String FINE_BYE_ID = "BodegaNovedad.findById";

    @EmbeddedId
    private PkNovedad id;
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
    private Long ultimoAnoRenovado;
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
    private Long idGrupoNovedad;
    @Column(name = "nit_ul_involucrada")
    private Long nitUlInvolucrada;
    @Column(name = "observaciones")
    private String observaciones;

    public BodegaNovedad() {
    }

    public PkNovedad getId() {
        return id;
    }

    public void setId(PkNovedad id) {
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

    public Long getUltimoAnoRenovado() {
        return ultimoAnoRenovado;
    }

    public void setUltimoAnoRenovado(Long ultimoAnoRenovado) {
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

    public Long getIdGrupoNovedad() {
        return idGrupoNovedad;
    }

    public void setIdGrupoNovedad(Long idGrupoNovedad) {
        this.idGrupoNovedad = idGrupoNovedad;
    }

    public Long getNitUlInvolucrada() {
        return nitUlInvolucrada;
    }

    public void setNitUltimaInvolucrada(Long nitUlInvolucrada) {
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

    public Map<String, String> toMap() {
        Map<String, String> resultado = new HashMap<String, String>();
        resultado.put("N_ID_ORGANIZACION", id.getId() + "");
        resultado.put("N_FECHA_ACTUALIZA", id.getFecha() + "");
        resultado.put("ESTADO", Texto.blankText(estado + ""));
        resultado.put("FECHA_CONFIGURACION", Texto.blankText(fechaConfiguracion + ""));
        resultado.put("FECHA_MATRICULA", Texto.blankText(fechaMatricula + ""));
        resultado.put("ESTADO_MATRICULA", Texto.blankText(estadoMatricula + ""));
        resultado.put("ULTIMO_ANO_RENOVADO", Texto.blankText(ultimoAnoRenovado + ""));
        resultado.put("FECHA_CREACION", Texto.blankText(fechaCreacion + ""));
        resultado.put("FECHA_INICIO_ACTI", Texto.blankText(fechaInicioActi + ""));
        resultado.put("FECHA_CIERRE", Texto.blankText(fechaCierre + ""));
        resultado.put("FECHA_LIQUIDACION", Texto.blankText(fechaLiquidacion + ""));
        resultado.put("TIPO_NOVEDAD", Texto.blankText(tipoNovedad + ""));
        resultado.put("FECHA_NOVEDAD", Texto.blankText(fechaNovedad + ""));
        resultado.put("ID_GRUPO_NOVEDAD", Texto.blankText(idGrupoNovedad + ""));
        resultado.put("NIT_UL_INVOLUCRADA", Texto.blankText(nitUlInvolucrada + ""));
        resultado.put("OBSERVACIONES", Texto.blankText(observaciones + ""));
        return resultado;
    }

    @Override
    public String toString() {
        return "com.dabe.ige.modelo.entidad.BodegaIdentificacion[ id=" + id + " ]";
    }

}
