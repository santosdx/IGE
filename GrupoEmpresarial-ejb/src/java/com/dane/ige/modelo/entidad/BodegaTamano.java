package com.dane.ige.modelo.entidad;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
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
import org.apache.commons.lang3.text.WordUtils;

/**
 *
 * @author srojasm
 */
@Entity
@Table(name = "ige_tamano")
@NamedQueries({
    @NamedQuery(name = "BodegaTamano.findAll", query = "SELECT m FROM BodegaTamano m"),
    @NamedQuery(name = "BodegaTamano.findById", query = "SELECT m FROM BodegaTamano m WHERE m.id = :id")})
public class BodegaTamano implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String GET_ALL = "BodegaTamano.findAll";
    public static final String FINE_BYE_ID = "BodegaTamano.findById";

    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MODULO")
    //@SequenceGenerator(name = "SEQ_MODULO", sequenceName = "seq_id_ige_modulo", allocationSize = 1)
    @Column(name = "t_id_organizacion", unique = true, nullable = false, scale = 0)
    private Integer id;
    @Column(name = "info_corte")
    private Integer infoCorte;

    @Column(name = "personal_ocupado")
    private Integer personalOcupado;
    @Column(name = "fuente_personal_ocupado")
    private String fuentePersonalOcupado;

    @Column(name = "personal_empleado")
    private Integer personalEmpleado;
    @Column(name = "fuente_personal_empleado")
    private String fuentePersonalEmpleado;

    @Column(name = "personal_temporal")
    private Integer personalTemporal;
    @Column(name = "fuente_personal_temporal")
    private String fuentePersonaltemporal;

    @Column(name = "personal_cotizante")
    private Integer personalCotizante;
    @Column(name = "fuente_personal_cotizante")
    private String fuentePersonalCotizante;

    @Column(name = "hombres_ocupados")
    private Integer hombresOcupados;
    @Column(name = "fuente_hombres_ocupados")
    private String fuenteHombresOcupados;

    @Column(name = "mujeres_ocupadas")
    private Integer mujeresOcupadas;
    @Column(name = "fuente_mujeres_ocupadas")
    private String fuenteMujeresOcupadas;

    @Column(name = "ingresos_operacionales")
    private Integer ingresosOperacionales;
    @Column(name = "fuente_ingresos_operacional")
    private String fuenteIngresosOperacional;

    @Column(name = "utilidad_neta")
    private Integer utilidadNeta;
    @Column(name = "fuente_utilidad_neta")
    private String fuenteUtilidadNeta;

    @Column(name = "utilidad_bruta")
    private Integer utilidadBruta;
    @Column(name = "fuente_utilidad_bruta")
    private String fuenteUtilidadBrura;

    @Column(name = "activo")
    private Integer activo;
    @Column(name = "fuente_activo")
    private String fuenteActivo;

    @Column(name = "pasivo")
    private Integer pasivo;
    @Column(name = "fuente_pasivo")
    private String fuentePasivo;

    @Column(name = "patrimonio")
    private Integer patrimonio;
    @Column(name = "fuente_patrimonio")
    private String fuentePatrimonio;

    @Column(name = "gastos_operacionales")
    private Integer gastosOperacionales;
    @Column(name = "fuente_gastos_operacionales")
    private String fuenteGastosOperacionales;

    @Column(name = "gastos_operacionales_admon")
    private Integer gastosOperacionalesAdmon;
    @Column(name = "fuente_gastos_Admon")
    private String fuenteGastosAdmon;

    @Column(name = "gastos_operacionales_venta")
    private Integer gastosOperacionalesVenta;
    @Column(name = "fuente_gastos_venta")
    private String fuenteGastosVenta;

    @Column(name = "utilidad_operativa")
    private Integer utilidadOperativa;
    @Column(name = "fuente_utilidad_operativa")
    private String fuenteUtilidadOperativa;

    @Column(name = "ebitda")
    private Integer ebitda;
    @Column(name = "fuente_ebitda")
    private String fuenteEbitda;

    @Column(name = "observaciones")
    private String observaciones;

    public BodegaTamano() {
    }

    public BodegaTamano(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInfoCorte() {
        return infoCorte;
    }

    public void setInfoCorte(Integer infoCorte) {
        this.infoCorte = infoCorte;
    }

    public Integer getPersonalOcupado() {
        return personalOcupado;
    }

    public void setPersonalOcupado(Integer personalOcupado) {
        this.personalOcupado = personalOcupado;
    }

    public String getFuentePersonalOcupado() {
        return fuentePersonalOcupado;
    }

    public void setFuentePersonalOcupado(String fuentePersonalOcupado) {
        this.fuentePersonalOcupado = fuentePersonalOcupado;
    }

    public Integer getPersonalEmpleado() {
        return personalEmpleado;
    }

    public void setPersonalEmpleado(Integer personalEmpleado) {
        this.personalEmpleado = personalEmpleado;
    }

    public String getFuentePersonalEmpleado() {
        return fuentePersonalEmpleado;
    }

    public void setFuentePersonalEmpleado(String fuentePersonalEmpleado) {
        this.fuentePersonalEmpleado = fuentePersonalEmpleado;
    }

    public Integer getPersonalTemporal() {
        return personalTemporal;
    }

    public void setPersonalTemporal(Integer personalTemporal) {
        this.personalTemporal = personalTemporal;
    }

    public String getFuentePersonaltemporal() {
        return fuentePersonaltemporal;
    }

    public void setFuentePersonaltemporal(String fuentePersonaltemporal) {
        this.fuentePersonaltemporal = fuentePersonaltemporal;
    }

    public Integer getPersonalCotizante() {
        return personalCotizante;
    }

    public void setPersonalCotizante(Integer personalCotizante) {
        this.personalCotizante = personalCotizante;
    }

    public String getFuentePersonalCotizante() {
        return fuentePersonalCotizante;
    }

    public void setFuentePersonalCotizante(String fuentePersonalCotizante) {
        this.fuentePersonalCotizante = fuentePersonalCotizante;
    }

    public Integer getHombresOcupados() {
        return hombresOcupados;
    }

    public void setHombresOcupados(Integer hombresOcupados) {
        this.hombresOcupados = hombresOcupados;
    }

    public String getFuenteHombresOcupados() {
        return fuenteHombresOcupados;
    }

    public void setFuenteHombresOcupados(String fuenteHombresOcupados) {
        this.fuenteHombresOcupados = fuenteHombresOcupados;
    }

    public Integer getMujeresOcupadas() {
        return mujeresOcupadas;
    }

    public void setMujeresOcupadas(Integer mujeresOcupadas) {
        this.mujeresOcupadas = mujeresOcupadas;
    }

    public String getFuenteMujeresOcupadas() {
        return fuenteMujeresOcupadas;
    }

    public void setFuenteMujeresOcupadas(String fuenteMujeresOcupadas) {
        this.fuenteMujeresOcupadas = fuenteMujeresOcupadas;
    }

    public Integer getIngresosOperacionales() {
        return ingresosOperacionales;
    }

    public void setIngresosOperacionales(Integer ingresosOperacionales) {
        this.ingresosOperacionales = ingresosOperacionales;
    }

    public String getFuenteIngresosOperacional() {
        return fuenteIngresosOperacional;
    }

    public void setFuenteIngresosOperacional(String fuenteIngresosOperacional) {
        this.fuenteIngresosOperacional = fuenteIngresosOperacional;
    }

    public Integer getUtilidadNeta() {
        return utilidadNeta;
    }

    public void setUtilidadNeta(Integer utilidadNeta) {
        this.utilidadNeta = utilidadNeta;
    }

    public String getFuenteUtilidadNeta() {
        return fuenteUtilidadNeta;
    }

    public void setFuenteUtilidadNeta(String fuenteUtilidadNeta) {
        this.fuenteUtilidadNeta = fuenteUtilidadNeta;
    }

    public Integer getUtilidadBruta() {
        return utilidadBruta;
    }

    public void setUtilidadBruta(Integer utilidadBruta) {
        this.utilidadBruta = utilidadBruta;
    }

    public String getFuenteUtilidadBrura() {
        return fuenteUtilidadBrura;
    }

    public void setFuenteUtilidadBrura(String fuenteUtilidadBrura) {
        this.fuenteUtilidadBrura = fuenteUtilidadBrura;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

    public String getFuenteActivo() {
        return fuenteActivo;
    }

    public void setFuenteActivo(String fuenteActivo) {
        this.fuenteActivo = fuenteActivo;
    }

    public Integer getPasivo() {
        return pasivo;
    }

    public void setPasivo(Integer pasivo) {
        this.pasivo = pasivo;
    }

    public String getFuentePasivo() {
        return fuentePasivo;
    }

    public void setFuentePasivo(String fuentePasivo) {
        this.fuentePasivo = fuentePasivo;
    }

    public Integer getPatrimonio() {
        return patrimonio;
    }

    public void setPatrimonio(Integer patrimonio) {
        this.patrimonio = patrimonio;
    }

    public String getFuentePatrimonio() {
        return fuentePatrimonio;
    }

    public void setFuentePatrimonio(String fuentePatrimonio) {
        this.fuentePatrimonio = fuentePatrimonio;
    }

    public Integer getGastosOperacionales() {
        return gastosOperacionales;
    }

    public void setGastosOperacionales(Integer gastosOperacionales) {
        this.gastosOperacionales = gastosOperacionales;
    }

    public String getFuenteGastosOperacionales() {
        return fuenteGastosOperacionales;
    }

    public void setFuenteGastosOperacionales(String fuenteGastosOperacionales) {
        this.fuenteGastosOperacionales = fuenteGastosOperacionales;
    }

    public Integer getGastosOperacionalesAdmon() {
        return gastosOperacionalesAdmon;
    }

    public void setGastosOperacionalesAdmon(Integer gastosOperacionalesAdmon) {
        this.gastosOperacionalesAdmon = gastosOperacionalesAdmon;
    }

    public String getFuenteGastosAdmon() {
        return fuenteGastosAdmon;
    }

    public void setFuenteGastosAdmon(String fuenteGastosAdmon) {
        this.fuenteGastosAdmon = fuenteGastosAdmon;
    }

    public Integer getGastosOperacionalesVenta() {
        return gastosOperacionalesVenta;
    }

    public void setGastosOperacionalesVenta(Integer gastosOperacionalesVenta) {
        this.gastosOperacionalesVenta = gastosOperacionalesVenta;
    }

    public String getFuenteGastosVenta() {
        return fuenteGastosVenta;
    }

    public void setFuenteGastosVenta(String fuenteGastosVenta) {
        this.fuenteGastosVenta = fuenteGastosVenta;
    }

    public Integer getUtilidadOperativa() {
        return utilidadOperativa;
    }

    public void setUtilidadOperativa(Integer utilidadOperativa) {
        this.utilidadOperativa = utilidadOperativa;
    }

    public String getFuenteUtilidadOperativa() {
        return fuenteUtilidadOperativa;
    }

    public void setFuenteUtilidadOperativa(String fuenteUtilidadOperativa) {
        this.fuenteUtilidadOperativa = fuenteUtilidadOperativa;
    }

    public Integer getEbitda() {
        return ebitda;
    }

    public void setEbitda(Integer ebitda) {
        this.ebitda = ebitda;
    }

    public String getFuenteEbitda() {
        return fuenteEbitda;
    }

    public void setFuenteEbitda(String fuenteEbitda) {
        this.fuenteEbitda = fuenteEbitda;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    
    //--
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BodegaTamano)) {
            return false;
        }
        BodegaTamano other = (BodegaTamano) object;
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
