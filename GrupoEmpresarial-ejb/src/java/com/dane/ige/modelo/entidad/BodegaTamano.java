package com.dane.ige.modelo.entidad;

import com.dane.ige.modelo.llave.PkTamano;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
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
    @NamedQuery(name = "BodegaTamano.findById",
            query = "SELECT m FROM BodegaTamano m WHERE m.id.id = :id AND ROWNUM<=1 ORDER BY m.id.fecha DESC")})
public class BodegaTamano implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String GET_ALL = "BodegaTamano.findAll";
    public static final String FINE_BYE_ID = "BodegaTamano.findById";

    @EmbeddedId
    private PkTamano id;

    @Column(name = "info_corte")
    private Long infoCorte;

    @Column(name = "personal_ocupado")
    private Long personalOcupado;
    @Column(name = "fuente_personal_ocupado")
    private String fuentePersonalOcupado;

    @Column(name = "personal_empleado")
    private Long personalEmpleado;
    @Column(name = "fuente_personal_empleado")
    private String fuentePersonalEmpleado;

    @Column(name = "personal_temporal")
    private Long personalTemporal;
    @Column(name = "fuente_personal_temporal")
    private String fuentePersonaltemporal;

    @Column(name = "personal_cotizante")
    private Long personalCotizante;
    @Column(name = "fuente_personal_cotizante")
    private String fuentePersonalCotizante;

    @Column(name = "hombres_ocupados")
    private Long hombresOcupados;
    @Column(name = "fuente_hombres_ocupados")
    private String fuenteHombresOcupados;

    @Column(name = "mujeres_ocupadas")
    private Long mujeresOcupadas;
    @Column(name = "fuente_mujeres_ocupadas")
    private String fuenteMujeresOcupadas;

    @Column(name = "ingresos_operacionales")
    private Long ingresosOperacionales;
    @Column(name = "fuente_ingresos_operacional")
    private String fuenteIngresosOperacional;

    @Column(name = "utilidad_neta")
    private Long utilidadNeta;
    @Column(name = "fuente_utilidad_neta")
    private String fuenteUtilidadNeta;

    @Column(name = "utilidad_bruta")
    private Long utilidadBruta;
    @Column(name = "fuente_utilidad_bruta")
    private String fuenteUtilidadBrura;

    @Column(name = "activo")
    private Long activo;
    @Column(name = "fuente_activo")
    private String fuenteActivo;

    @Column(name = "pasivo")
    private Long pasivo;
    @Column(name = "fuente_pasivo")
    private String fuentePasivo;

    @Column(name = "patrimonio")
    private Long patrimonio;
    @Column(name = "fuente_patrimonio")
    private String fuentePatrimonio;

    @Column(name = "gastos_operacionales")
    private Long gastosOperacionales;
    @Column(name = "fuente_gastos_operacionales")
    private String fuenteGastosOperacionales;

    @Column(name = "gastos_operacionales_admon")
    private Long gastosOperacionalesAdmon;
    @Column(name = "fuente_gastos_Admon")
    private String fuenteGastosAdmon;

    @Column(name = "gastos_operacionales_venta")
    private Long gastosOperacionalesVenta;
    @Column(name = "fuente_gastos_venta")
    private String fuenteGastosVenta;

    @Column(name = "utilidad_operativa")
    private Long utilidadOperativa;
    @Column(name = "fuente_utilidad_operativa")
    private String fuenteUtilidadOperativa;

    @Column(name = "ebitda")
    private Long ebitda;
    @Column(name = "fuente_ebitda")
    private String fuenteEbitda;

    @Column(name = "costo_ventas_est")
    private Long costoVentasEst;

    @Column(name = "observaciones")
    private String observaciones;

    public BodegaTamano() {
    }

    public PkTamano getId() {
        return id;
    }

    public void setId(PkTamano id) {
        this.id = id;
    }

    public Long getInfoCorte() {
        return infoCorte;
    }

    public void setInfoCorte(Long infoCorte) {
        this.infoCorte = infoCorte;
    }

    public Long getPersonalOcupado() {
        return personalOcupado;
    }

    public void setPersonalOcupado(Long personalOcupado) {
        this.personalOcupado = personalOcupado;
    }

    public String getFuentePersonalOcupado() {
        return fuentePersonalOcupado;
    }

    public void setFuentePersonalOcupado(String fuentePersonalOcupado) {
        this.fuentePersonalOcupado = fuentePersonalOcupado;
    }

    public Long getPersonalEmpleado() {
        return personalEmpleado;
    }

    public void setPersonalEmpleado(Long personalEmpleado) {
        this.personalEmpleado = personalEmpleado;
    }

    public String getFuentePersonalEmpleado() {
        return fuentePersonalEmpleado;
    }

    public void setFuentePersonalEmpleado(String fuentePersonalEmpleado) {
        this.fuentePersonalEmpleado = fuentePersonalEmpleado;
    }

    public Long getPersonalTemporal() {
        return personalTemporal;
    }

    public void setPersonalTemporal(Long personalTemporal) {
        this.personalTemporal = personalTemporal;
    }

    public String getFuentePersonaltemporal() {
        return fuentePersonaltemporal;
    }

    public void setFuentePersonaltemporal(String fuentePersonaltemporal) {
        this.fuentePersonaltemporal = fuentePersonaltemporal;
    }

    public Long getPersonalCotizante() {
        return personalCotizante;
    }

    public void setPersonalCotizante(Long personalCotizante) {
        this.personalCotizante = personalCotizante;
    }

    public String getFuentePersonalCotizante() {
        return fuentePersonalCotizante;
    }

    public void setFuentePersonalCotizante(String fuentePersonalCotizante) {
        this.fuentePersonalCotizante = fuentePersonalCotizante;
    }

    public Long getHombresOcupados() {
        return hombresOcupados;
    }

    public void setHombresOcupados(Long hombresOcupados) {
        this.hombresOcupados = hombresOcupados;
    }

    public String getFuenteHombresOcupados() {
        return fuenteHombresOcupados;
    }

    public void setFuenteHombresOcupados(String fuenteHombresOcupados) {
        this.fuenteHombresOcupados = fuenteHombresOcupados;
    }

    public Long getMujeresOcupadas() {
        return mujeresOcupadas;
    }

    public void setMujeresOcupadas(Long mujeresOcupadas) {
        this.mujeresOcupadas = mujeresOcupadas;
    }

    public String getFuenteMujeresOcupadas() {
        return fuenteMujeresOcupadas;
    }

    public void setFuenteMujeresOcupadas(String fuenteMujeresOcupadas) {
        this.fuenteMujeresOcupadas = fuenteMujeresOcupadas;
    }

    public Long getIngresosOperacionales() {
        return ingresosOperacionales;
    }

    public void setIngresosOperacionales(Long ingresosOperacionales) {
        this.ingresosOperacionales = ingresosOperacionales;
    }

    public String getFuenteIngresosOperacional() {
        return fuenteIngresosOperacional;
    }

    public void setFuenteIngresosOperacional(String fuenteIngresosOperacional) {
        this.fuenteIngresosOperacional = fuenteIngresosOperacional;
    }

    public Long getUtilidadNeta() {
        return utilidadNeta;
    }

    public void setUtilidadNeta(Long utilidadNeta) {
        this.utilidadNeta = utilidadNeta;
    }

    public String getFuenteUtilidadNeta() {
        return fuenteUtilidadNeta;
    }

    public void setFuenteUtilidadNeta(String fuenteUtilidadNeta) {
        this.fuenteUtilidadNeta = fuenteUtilidadNeta;
    }

    public Long getUtilidadBruta() {
        return utilidadBruta;
    }

    public void setUtilidadBruta(Long utilidadBruta) {
        this.utilidadBruta = utilidadBruta;
    }

    public String getFuenteUtilidadBrura() {
        return fuenteUtilidadBrura;
    }

    public void setFuenteUtilidadBrura(String fuenteUtilidadBrura) {
        this.fuenteUtilidadBrura = fuenteUtilidadBrura;
    }

    public Long getActivo() {
        return activo;
    }

    public void setActivo(Long activo) {
        this.activo = activo;
    }

    public String getFuenteActivo() {
        return fuenteActivo;
    }

    public void setFuenteActivo(String fuenteActivo) {
        this.fuenteActivo = fuenteActivo;
    }

    public Long getPasivo() {
        return pasivo;
    }

    public void setPasivo(Long pasivo) {
        this.pasivo = pasivo;
    }

    public String getFuentePasivo() {
        return fuentePasivo;
    }

    public void setFuentePasivo(String fuentePasivo) {
        this.fuentePasivo = fuentePasivo;
    }

    public Long getPatrimonio() {
        return patrimonio;
    }

    public void setPatrimonio(Long patrimonio) {
        this.patrimonio = patrimonio;
    }

    public String getFuentePatrimonio() {
        return fuentePatrimonio;
    }

    public void setFuentePatrimonio(String fuentePatrimonio) {
        this.fuentePatrimonio = fuentePatrimonio;
    }

    public Long getGastosOperacionales() {
        return gastosOperacionales;
    }

    public void setGastosOperacionales(Long gastosOperacionales) {
        this.gastosOperacionales = gastosOperacionales;
    }

    public String getFuenteGastosOperacionales() {
        return fuenteGastosOperacionales;
    }

    public void setFuenteGastosOperacionales(String fuenteGastosOperacionales) {
        this.fuenteGastosOperacionales = fuenteGastosOperacionales;
    }

    public Long getGastosOperacionalesAdmon() {
        return gastosOperacionalesAdmon;
    }

    public void setGastosOperacionalesAdmon(Long gastosOperacionalesAdmon) {
        this.gastosOperacionalesAdmon = gastosOperacionalesAdmon;
    }

    public String getFuenteGastosAdmon() {
        return fuenteGastosAdmon;
    }

    public void setFuenteGastosAdmon(String fuenteGastosAdmon) {
        this.fuenteGastosAdmon = fuenteGastosAdmon;
    }

    public Long getGastosOperacionalesVenta() {
        return gastosOperacionalesVenta;
    }

    public void setGastosOperacionalesVenta(Long gastosOperacionalesVenta) {
        this.gastosOperacionalesVenta = gastosOperacionalesVenta;
    }

    public String getFuenteGastosVenta() {
        return fuenteGastosVenta;
    }

    public void setFuenteGastosVenta(String fuenteGastosVenta) {
        this.fuenteGastosVenta = fuenteGastosVenta;
    }

    public Long getUtilidadOperativa() {
        return utilidadOperativa;
    }

    public void setUtilidadOperativa(Long utilidadOperativa) {
        this.utilidadOperativa = utilidadOperativa;
    }

    public String getFuenteUtilidadOperativa() {
        return fuenteUtilidadOperativa;
    }

    public void setFuenteUtilidadOperativa(String fuenteUtilidadOperativa) {
        this.fuenteUtilidadOperativa = fuenteUtilidadOperativa;
    }

    public Long getEbitda() {
        return ebitda;
    }

    public void setEbitda(Long ebitda) {
        this.ebitda = ebitda;
    }

    public String getFuenteEbitda() {
        return fuenteEbitda;
    }

    public void setFuenteEbitda(String fuenteEbitda) {
        this.fuenteEbitda = fuenteEbitda;
    }

    public Long getCostoVentasEst() {
        return costoVentasEst;
    }

    public void setCostoVentasEst(Long costoVentasEst) {
        this.costoVentasEst = costoVentasEst;
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
