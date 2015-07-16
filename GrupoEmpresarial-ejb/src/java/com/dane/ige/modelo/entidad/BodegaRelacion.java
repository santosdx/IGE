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
@Table(name = "ige_relacion")
@NamedQueries({
    @NamedQuery(name = "BodegaRelacion.findAll", query = "SELECT m FROM BodegaRelacion m"),
    @NamedQuery(name = "BodegaRelacion.findById", query = "SELECT m FROM BodegaRelacion m WHERE m.id = :id")})
public class BodegaRelacion implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String GET_ALL = "BodegaRelacion.findAll";
    public static final String FINE_BYE_ID = "BodegaRelacion.findById";

    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MODULO")
    //@SequenceGenerator(name = "SEQ_MODULO", sequenceName = "seq_id_ige_modulo", allocationSize = 1)
    @Column(name = "r_id_organizacion", unique = true, nullable = false, scale = 0)
    private Long id;
    @Column(name = "tipo_congromelado")
    private String tipoCongromelado;
    @Column(name = "situacion_control")
    private String situacionControl;
    @Column(name = "tipo_control")
    private String tipoControl;
    @Column(name = "tipo_empresa_controlante")
    private String tipoEmpresaControlante;
    @Column(name = "pais_controlante")
    private String paisControlante;
    @Column(name = "sucursales_extranjero_ge")
    private String sucursalesExtranjeroGe;
    @Column(name = "consorcio_unidades_temp_ge")
    private String consorcioUnidadesTempGe;
    @Column(name = "tipo_relacion_ul")
    private String tipoRelacionUl;
    @Column(name = "nit_controlante")
    private String nitControlante;
    @Column(name = "nombre_controlante")
    private String nombreControlante;
    @Column(name = "porcentaje_controlante_ul")
    private Long porcentajeControlanteul;
    @Column(name = "perfilador")
    private String perfilador;

    public BodegaRelacion() {
    }

    public BodegaRelacion(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoCongromelado() {
        return tipoCongromelado;
    }

    public void setTipoCongromelado(String tipoCongromelado) {
        this.tipoCongromelado = tipoCongromelado;
    }

    public String getSituacionControl() {
        return situacionControl;
    }

    public void setSituacionControl(String situacionControl) {
        this.situacionControl = situacionControl;
    }

    public String getTipoControl() {
        return tipoControl;
    }

    public void setTipoControl(String tipoControl) {
        this.tipoControl = tipoControl;
    }

    public String getTipoEmpresaControlante() {
        return tipoEmpresaControlante;
    }

    public void setTipoEmpresaControlante(String tipoEmpresaControlante) {
        this.tipoEmpresaControlante = tipoEmpresaControlante;
    }

    public String getPaisControlante() {
        return paisControlante;
    }

    public void setPaisControlante(String paisControlante) {
        this.paisControlante = paisControlante;
    }

    public String getSucursalesExtranjeroGe() {
        if(sucursalesExtranjeroGe != null){
            return sucursalesExtranjeroGe.equals("SI")?"true":"false";
        }else{
            return "false";
        }
    }

    public void setSucursalesExtranjeroGe(String sucursalesExtranjeroGe) {
        if(sucursalesExtranjeroGe != null){
            this.sucursalesExtranjeroGe = sucursalesExtranjeroGe.equals("true")?"SI":"NO";
        }else{
            this.sucursalesExtranjeroGe = "NO";
        }
    }

    public String getConsorcioUnidadesTempGe() {
        if(consorcioUnidadesTempGe != null){
            return consorcioUnidadesTempGe.equals("SI")?"true":"false";
        }else{
            return "false";
        }
    }

    public void setConsorcioUnidadesTempGe(String consorcioUnidadesTempGe) {
        if(consorcioUnidadesTempGe != null){
            this.consorcioUnidadesTempGe = consorcioUnidadesTempGe.equals("true")?"SI":"NO";
        }else{
            this.consorcioUnidadesTempGe = "NO";
        }
    }

    public String getTipoRelacionUl() {
        return tipoRelacionUl;
    }

    public void setTipoRelacionUl(String tipoRelacionUl) {
        this.tipoRelacionUl = tipoRelacionUl;
    }

    public String getNitControlante() {
        return nitControlante;
    }

    public void setNitControlante(String nitControlante) {
        this.nitControlante = nitControlante;
    }

    public String getNombreControlante() {
        return nombreControlante;
    }

    public void setNombreControlante(String nombreControlante) {
        this.nombreControlante = nombreControlante;
    }

    public Long getPorcentajeControlanteul() {
        return porcentajeControlanteul;
    }

    public void setPorcentajeControlanteul(Long porcentajeControlanteul) {
        this.porcentajeControlanteul = porcentajeControlanteul;
    }

    public String getPerfilador() {
        return perfilador;
    }

    public void setPerfilador(String perfilador) {
        this.perfilador = perfilador;
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
        if (!(object instanceof BodegaRelacion)) {
            return false;
        }
        BodegaRelacion other = (BodegaRelacion) object;
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
