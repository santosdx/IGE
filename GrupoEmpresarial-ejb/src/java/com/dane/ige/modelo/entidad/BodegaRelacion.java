package com.dane.ige.modelo.entidad;

import com.dane.ige.modelo.llave.PkRelacion;
import com.dane.ige.utilidad.Texto;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Clase Entity que representa la tabla ige_relacion
 *
 * @author srojasm
 */
@Entity
@Table(name = "ige_relacion")
@NamedQueries({
    @NamedQuery(name = "BodegaRelacion.findAll", query = "SELECT m FROM BodegaRelacion m"), //@NamedQuery(name = "BodegaRelacion.findById", query = "SELECT m FROM BodegaRelacion m WHERE m.id.id = :id AND ROWNUM<=1 ORDER BY m.id.fecha DESC")
})
public class BodegaRelacion implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String GET_ALL = "BodegaRelacion.findAll";
    //public static final String FINE_BYE_ID = "BodegaRelacion.findById";

    @EmbeddedId
    private PkRelacion id;
    @Column(name = "tipo_conglomerado")
    private String tipoConglomerado;
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
    private String porcentajeControlanteul;

    public BodegaRelacion() {
    }

    public PkRelacion getId() {
        return id;
    }

    public void setId(PkRelacion id) {
        this.id = id;
    }

    public String getTipoConglomerado() {
        return tipoConglomerado;
    }

    public void setTipoConglomerado(String tipoConglomerado) {
        this.tipoConglomerado = tipoConglomerado;
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
        if (sucursalesExtranjeroGe != null) {
            return sucursalesExtranjeroGe.equals("SI") ? "true" : "false";
        } else {
            return "false";
        }
    }

    public void setSucursalesExtranjeroGe(String sucursalesExtranjeroGe) {
        if (sucursalesExtranjeroGe != null) {
            this.sucursalesExtranjeroGe = sucursalesExtranjeroGe.equals("true") ? "SI" : "NO";
        } else {
            this.sucursalesExtranjeroGe = "NO";
        }
    }

    public String getConsorcioUnidadesTempGe() {
        if (consorcioUnidadesTempGe != null) {
            return consorcioUnidadesTempGe.equals("SI") ? "true" : "false";
        } else {
            return "false";
        }
    }

    public void setConsorcioUnidadesTempGe(String consorcioUnidadesTempGe) {
        if (consorcioUnidadesTempGe != null) {
            this.consorcioUnidadesTempGe = consorcioUnidadesTempGe.equals("true") ? "SI" : "NO";
        } else {
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

    public String getPorcentajeControlanteul() {
        return porcentajeControlanteul;
    }

    public void setPorcentajeControlanteul(String porcentajeControlanteul) {
        this.porcentajeControlanteul = porcentajeControlanteul;
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
        if (!(object instanceof BodegaRelacion)) {
            return false;
        }
        BodegaRelacion other = (BodegaRelacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public Map<String, String> toMap() {
        Map<String, String> resultado = new HashMap<String, String>();
        resultado.put("R_ID_ORGANIZACION", id.getId() + "");
        resultado.put("R_FECHA_ACTUALIZA", id.getFecha() + "");
        resultado.put("TIPO_CONGLOMERADO", Texto.blankText(tipoConglomerado + ""));
        resultado.put("SITUACION_CONTROL", Texto.blankText(situacionControl + ""));
        resultado.put("TIPO_CONTROL", Texto.blankText(tipoControl + ""));
        resultado.put("TIPO_EMPRESA_CONTROLANTE", Texto.blankText(tipoEmpresaControlante + ""));
        resultado.put("PAIS_CONTROLANTE", Texto.blankText(paisControlante + ""));
        resultado.put("SUCURSALES_EXTRANJERO_GE", Texto.blankText(sucursalesExtranjeroGe + ""));
        resultado.put("CONSORCIO_UNIDADES_TEMP_GE", Texto.blankText(consorcioUnidadesTempGe + ""));
        resultado.put("TIPO_RELACION_UL", Texto.blankText(tipoRelacionUl + ""));
        resultado.put("NIT_CONTROLANTE", Texto.blankText(nitControlante + ""));
        resultado.put("NOMBRE_CONTROLANTE", Texto.blankText(nombreControlante + ""));
        resultado.put("PORCENTAJE_CONTROLANTE_UL", Texto.blankText(porcentajeControlanteul + ""));
        return resultado;
    }

    @Override
    public String toString() {
        return "com.dabe.ige.modelo.entidad.BodegaRelacion[ id=" + id.getId() + " ]";
    }

}
