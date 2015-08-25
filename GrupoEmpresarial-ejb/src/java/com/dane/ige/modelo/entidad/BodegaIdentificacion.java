package com.dane.ige.modelo.entidad;

import com.dane.ige.modelo.llave.PkIdentificacion;
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
 *
 * @author srojasm
 */
@Entity
@Table(name = "ige_identificacion")
@NamedQueries({
    @NamedQuery(name = "BodegaIdentificacion.findAll", query = "SELECT i FROM BodegaIdentificacion i"), //@NamedQuery(name = "BodegaIdentificacion.findById", 
//        query = "SELECT i FROM BodegaIdentificacion i WHERE i.id.id = :id AND i.tipoOrganizacion=:tipoOrganizacion AND ROWNUM<=1 ORDER BY i.id.fecha DESC"),
})
public class BodegaIdentificacion implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String GET_ALL = "BodegaIdentificacion.findAll";
    //public static final String FINE_BYE_ID = "BodegaIdentificacion.findById";

    @EmbeddedId
    private PkIdentificacion id;

    @Column(name = "tipo_organizacion")
    private String tipoOrganizacion;
    @Column(name = "id_grupo_relacionado")
    private Long idGrupoRelacionado;
    @Column(name = "id_ul_relacionada")
    private Long idUlRelacionada;
    @Column(name = "tipo_unidad_legal")
    private String tipoUnidadLegal;
    @Column(name = "tipo_establecimiento")
    private String tipoEstablecimiento;
    @Column(name = "nombre_comercial")
    private String nombreComercial;
    @Column(name = "nombre_registrado")
    private String nombreRegistrado;
    @Column(name = "nit")
    private Long nit;
    @Column(name = "matricula_mercantil")
    private Long matriculaMercantil;
    @Column(name = "digito_verificacion")
    private Long digitoVerificacion;
    @Column(name = "pais")
    private String pais;
    @Column(name = "departamento")
    private String departamento;
    @Column(name = "municipio")
    private String municipio;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "telefono")
    private Long telefono;
    @Column(name = "indicativo")
    private String indicativo;
    @Column(name = "pagina_web")
    private String paginaWeb;
    @Column(name = "correo_electronico")
    private String correoElectronico;
    @Column(name = "representante_legal")
    private String representanteLegal;
    @Column(name = "actividad_principal")
    private Long actividadPrincipal;
    @Column(name = "actividad_secundaria")
    private String actividadSecundaria;
    @Column(name = "unidad_auxiliar_ul")
    private String unidadAuxiliarUl;
    @Column(name = "actividad_auxiliar_est")
    private String actividadAuxiliarEst;
    @Column(name = "otras_actividades_est")
    private String otrasActividadesEst;
    @Column(name = "estado_est")
    private String estadoEst;
    @Column(name = "tipo_organizacion_ul")
    private String tipoOrganizacionUl;
    @Column(name = "sector_institucional_ul")
    private String sectorInstitucionalUl;
    @Column(name = "orientada_mercado_ul")
    private String orientadaMercadoUl;
    @Column(name = "encuestas_ul")
    private String encuestasUl;

    @Column(name = "nombre_contacto")
    private String nombreContacto;
    @Column(name = "cargo_contacto")
    private String cargoContacto;
    @Column(name = "telefono_contacto")
    private String telefonoContacto;
    @Column(name = "mail_contacto")
    private String mailContacto;

    @Column(name = "nombre_contacto2")
    private String nombreContacto2;
    @Column(name = "cargo_contacto2")
    private String cargoContacto2;
    @Column(name = "telefono_contacto2")
    private String telefonoContacto2;
    @Column(name = "mail_contacto2")
    private String mailContacto2;

    @Column(name = "nombre_contacto3")
    private String nombreContacto3;
    @Column(name = "cargo_contacto3")
    private String cargoContacto3;
    @Column(name = "telefono_contacto3")
    private String telefonoContacto3;
    @Column(name = "mail_contacto3")
    private String mailContacto3;

    @Column(name = "persona_actualiza")
    private String personaActualiza;

    public BodegaIdentificacion() {
    }

    public PkIdentificacion getId() {
        return id;
    }

    public void setId(PkIdentificacion id) {
        this.id = id;
    }

    public String getTipoOrganizacion() {
        return tipoOrganizacion;
    }

    public void setTipoOrganizacion(String tipoOrganizacion) {
        this.tipoOrganizacion = tipoOrganizacion;
    }

    public Long getIdGrupoRelacionado() {
        return idGrupoRelacionado;
    }

    public void setIdGrupoRelacionado(Long idGrupoRelacionado) {
        this.idGrupoRelacionado = idGrupoRelacionado;
    }

    public Long getIdUlRelacionada() {
        return idUlRelacionada;
    }

    public void setIdUlRelacionada(Long idUlRelacionada) {
        this.idUlRelacionada = idUlRelacionada;
    }

    public String getTipoUnidadLegal() {
        return tipoUnidadLegal;
    }

    public void setTipoUnidadLegal(String tipoUnidadLegal) {
        this.tipoUnidadLegal = tipoUnidadLegal;
    }

    public String getTipoEstablecimiento() {
        return tipoEstablecimiento;
    }

    public void setTipoEstablecimiento(String tipoEstablecimiento) {
        this.tipoEstablecimiento = tipoEstablecimiento;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getNombreRegistrado() {
        return nombreRegistrado;
    }

    public void setNombreRegistrado(String nombreRegistrado) {
        this.nombreRegistrado = nombreRegistrado;
    }

    public Long getNit() {
        return nit;
    }

    public void setNit(Long nit) {
        this.nit = nit;
    }

    public Long getMatriculaMercantil() {
        return matriculaMercantil;
    }

    public void setMatriculaMercantil(Long matriculaMercantil) {
        this.matriculaMercantil = matriculaMercantil;
    }

    public Long getDigitoVerificacion() {
        return digitoVerificacion;
    }

    public void setDigitoVerificacion(Long digitoVerificacion) {
        this.digitoVerificacion = digitoVerificacion;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Long getTelefono() {
        return telefono;
    }

    public void setTelefono(Long telefono) {
        this.telefono = telefono;
    }

    public String getIndicativo() {
        return indicativo;
    }

    public void setIndicativo(String indicativo) {
        this.indicativo = indicativo;
    }

    public String getPaginaWeb() {
        return paginaWeb;
    }

    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getRepresentanteLegal() {
        return representanteLegal;
    }

    public void setRepresentanteLegal(String representanteLegal) {
        this.representanteLegal = representanteLegal;
    }

    public Long getActividadPrincipal() {
        return actividadPrincipal;
    }

    public void setActividadPrincipal(Long actividadPrincipal) {
        this.actividadPrincipal = actividadPrincipal;
    }

    public String getActividadSecundaria() {
        return actividadSecundaria;
    }

    public void setActividadSecundaria(String actividadSecundaria) {
        this.actividadSecundaria = actividadSecundaria;
    }

    public String getUnidadAuxiliarUl() {
        if (unidadAuxiliarUl != null) {
            return unidadAuxiliarUl.equals("SI") ? "true" : "false";
        } else {
            return "false";
        }
    }

    public void setUnidadAuxiliarUl(String unidadAuxiliarUl) {
        if (unidadAuxiliarUl != null) {
            this.unidadAuxiliarUl = unidadAuxiliarUl.equals("true") ? "SI" : "NO";
        } else {
            this.unidadAuxiliarUl = "NO";
        }
    }

    public String getActividadAuxiliarEst() {
        return actividadAuxiliarEst;
    }

    public void setActividadAuxiliarEst(String actividadAuxiliarEst) {
        this.actividadAuxiliarEst = actividadAuxiliarEst;
    }

    public String getOtrasActividadesEst() {
        return otrasActividadesEst;
    }

    public void setOtrasActividadesEst(String otrasActividadesEst) {
        this.otrasActividadesEst = otrasActividadesEst;
    }

    public String getEstadoEst() {
        return estadoEst;
    }

    public void setEstadoEst(String estadoEst) {
        this.estadoEst = estadoEst;
    }

    public String getTipoOrganizacionUl() {
        return tipoOrganizacionUl;
    }

    public void setTipoOrganizacionUl(String tipoOrganizacionUl) {
        this.tipoOrganizacionUl = tipoOrganizacionUl;
    }

    public String getSectorInstitucionalUl() {
        return sectorInstitucionalUl;
    }

    public void setSectorInstitucionalUl(String sectorInstitucionalUl) {
        this.sectorInstitucionalUl = sectorInstitucionalUl;
    }

    public String getOrientadaMercadoUl() {
        if (orientadaMercadoUl != null) {
            return orientadaMercadoUl.equals("SI") ? "true" : "false";
        } else {
            return "false";
        }
    }

    public void setOrientadaMercadoUl(String orientadaMercadoUl) {
        if (orientadaMercadoUl != null) {
            this.orientadaMercadoUl = orientadaMercadoUl.equals("true") ? "SI" : "NO";
        } else {
            this.orientadaMercadoUl = "NO";
        }
    }

    public String getEncuestasUl() {
        return encuestasUl;
    }

    public void setEncuestasUl(String encuestasUl) {
        this.encuestasUl = encuestasUl;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public String getCargoContacto() {
        return cargoContacto;
    }

    public void setCargoContacto(String cargoContacto) {
        this.cargoContacto = cargoContacto;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public String getMailContacto() {
        return mailContacto;
    }

    public void setMailContacto(String mailContacto) {
        this.mailContacto = mailContacto;
    }

    public String getNombreContacto2() {
        return nombreContacto2;
    }

    public void setNombreContacto2(String nombreContacto2) {
        this.nombreContacto2 = nombreContacto2;
    }

    public String getCargoContacto2() {
        return cargoContacto2;
    }

    public void setCargoContacto2(String cargoContacto2) {
        this.cargoContacto2 = cargoContacto2;
    }

    public String getTelefonoContacto2() {
        return telefonoContacto2;
    }

    public void setTelefonoContacto2(String telefonoContacto2) {
        this.telefonoContacto2 = telefonoContacto2;
    }

    public String getMailContacto2() {
        return mailContacto2;
    }

    public void setMailContacto2(String mailContacto2) {
        this.mailContacto2 = mailContacto2;
    }

    public String getNombreContacto3() {
        return nombreContacto3;
    }

    public void setNombreContacto3(String nombreContacto3) {
        this.nombreContacto3 = nombreContacto3;
    }

    public String getCargoContacto3() {
        return cargoContacto3;
    }

    public void setCargoContacto3(String cargoContacto3) {
        this.cargoContacto3 = cargoContacto3;
    }

    public String getTelefonoContacto3() {
        return telefonoContacto3;
    }

    public void setTelefonoContacto3(String telefonoContacto3) {
        this.telefonoContacto3 = telefonoContacto3;
    }

    public String getMailContacto3() {
        return mailContacto3;
    }

    public void setMailContacto3(String mailContacto3) {
        this.mailContacto3 = mailContacto3;
    }

    public String getPersonaActualiza() {
        return personaActualiza;
    }

    public void setPersonaActualiza(String personaActualiza) {
        this.personaActualiza = personaActualiza;
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
        if (!(object instanceof BodegaIdentificacion)) {
            return false;
        }
        BodegaIdentificacion other = (BodegaIdentificacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public Map<String, String> toMap() {
        Map<String, String> resultado = new HashMap<String, String>();
        resultado.put("ID_ORGANIZACION", id.getId() + "");
        resultado.put("FECHA_ACTUALIZA_IDEN", id.getFecha() + "");
        resultado.put("TIPO_ORGANIZACION", Texto.blankText(tipoOrganizacion + ""));
        resultado.put("ID_GRUPO_RELACIONADO", Texto.blankText(idGrupoRelacionado + ""));
        resultado.put("ID_UL_RELACIONADA", Texto.blankText(idUlRelacionada + ""));
        resultado.put("TIPO_UNIDAD_LEGAL", Texto.blankText(tipoUnidadLegal + ""));
        resultado.put("TIPO_ESTABLECIMIENTO", Texto.blankText(tipoEstablecimiento + ""));
        resultado.put("NOMBRE_COMERCIAL", Texto.blankText(nombreComercial + ""));
        resultado.put("NOMBRE_REGISTRADO", Texto.blankText(nombreRegistrado + ""));
        resultado.put("NIT", Texto.blankText(nit + ""));
        resultado.put("MATRICULA_MERCANTIL", Texto.blankText(matriculaMercantil + ""));
        resultado.put("DIGITO_VERIFICACION", Texto.blankText(digitoVerificacion + ""));
        resultado.put("PAIS", Texto.blankText(pais + ""));
        resultado.put("DEPARTAMENTO", Texto.blankText(departamento + ""));
        resultado.put("MUNICIPIO", Texto.blankText(municipio + ""));
        resultado.put("DIRECCION", Texto.blankText(direccion + ""));
        resultado.put("TELEFONO", Texto.blankText(telefono + ""));
        resultado.put("INDICATIVO", Texto.blankText(indicativo + ""));
        resultado.put("PAGINA_WEB", Texto.blankText(paginaWeb + ""));
        resultado.put("CORREO_ELECTRONICO", Texto.blankText(correoElectronico + ""));
        resultado.put("REPRESENTANTE_LEGAL", Texto.blankText(representanteLegal + ""));
        resultado.put("ACTIVIDAD_PRINCIPAL", Texto.blankText(actividadPrincipal + ""));
        resultado.put("ACTIVIDAD_SECUNDARIA", Texto.blankText(actividadSecundaria + ""));
        resultado.put("UNIDAD_AUXILIAR_UL", Texto.blankText(unidadAuxiliarUl + ""));
        resultado.put("ACTIVIDAD_AUXILIAR_EST", Texto.blankText(actividadAuxiliarEst + ""));
        resultado.put("OTRAS_ACTIVIDADES_EST", Texto.blankText(otrasActividadesEst + ""));
        resultado.put("ESTADO_EST", Texto.blankText(estadoEst + ""));
        resultado.put("TIPO_ORGANIZACION_UL", Texto.blankText(tipoOrganizacionUl + ""));
        resultado.put("SECTOR_INSTITUCIONAL_UL", Texto.blankText(sectorInstitucionalUl + ""));
        resultado.put("ORIENTADA_MERCADO_UL", Texto.blankText(orientadaMercadoUl + ""));
        resultado.put("ENCUESTAS_UL", Texto.blankText(encuestasUl + ""));
        resultado.put("NOMBRE_CONTACTO", Texto.blankText(nombreContacto + ""));
        resultado.put("CARGO_CONTACTO", Texto.blankText(cargoContacto + ""));
        resultado.put("TELEFONO_CONTACTO", Texto.blankText(telefonoContacto + ""));
        resultado.put("MAIL_CONTACTO", Texto.blankText(mailContacto + ""));
        resultado.put("NOMBRE_CONTACTO2", Texto.blankText(nombreContacto2 + ""));
        resultado.put("CARGO_CONTACTO2", Texto.blankText(cargoContacto2 + ""));
        resultado.put("TELEFONO_CONTACTO2", Texto.blankText(telefonoContacto2 + ""));
        resultado.put("MAIL_CONTACTO2", Texto.blankText(mailContacto2 + ""));
        resultado.put("NOMBRE_CONTACTO3", Texto.blankText(nombreContacto3 + ""));
        resultado.put("CARGO_CONTACTO3", Texto.blankText(cargoContacto3 + ""));
        resultado.put("TELEFONO_CONTACTO3", Texto.blankText(telefonoContacto3 + ""));
        resultado.put("MAIL_CONTACTO3", Texto.blankText(mailContacto3 + ""));
        resultado.put("PERSONA_ACTUALIZA", Texto.blankText(personaActualiza + ""));
        return resultado;
    }

    @Override
    public String toString() {
        return "com.dabe.ige.modelo.entidad.BodegaIdentificacion[ id=" + id.getId() + " ]";
        /*
         return "id_organizacion^" + id.getId()
         + "~fecha_actualiza_iden^" + id.getFecha()
         + "~tipo_organizacion^" + tipoOrganizacion
         + "~id_grupo_relacionado^" + idGrupoRelacionado
         + "~id_ul_relacionada^" + idUlRelacionada
         + "~tipo_unidad_legal^" + tipoUnidadLegal
         + "~tipo_establecimiento^" + tipoEstablecimiento
         + "~nombre_comercial^" + nombreComercial
         + "~nombre_registrado^" + nombreRegistrado
         + "~nit^" + nit
         + "~matricula_mercantil^" + matriculaMercantil
         + "~digito_verificacion^" + digitoVerificacion
         + "~pais^" + pais
         + "~departamento^" + departamento
         + "~municipio^" + municipio
         + "~direccion^" + direccion
         + "~telefono^" + telefono
         + "~pagina_web^" + paginaWeb
         + "~correo_electronico^" + correoElectronico
         + "~representante_legal^" + representanteLegal
         + "~actividad_principal^" + actividadPrincipal
         + "~actividad_secundaria^" + actividadSecundaria
         + "~unidad_auxiliar_ul^" + unidadAuxiliarUl
         + "~actividad_auxiliar_est^" + actividadAuxiliarEst
         + "~otras_actividades_est^" + otrasActividadesEst
         + "~estado_est^" + estadoEst
         + "~tipo_organizacion_ul^" + tipoOrganizacionUl
         + "~sector_institucional_ul^" + sectorInstitucionalUl
         + "~orientada_mercado_ul^" + orientadaMercadoUl
         + "~encuestas_ul^" + encuestasUl
         + "~nombre_contacto^" + nombreContacto
         + "~cargo_contacto^" + cargoContacto
         + "~telefono_contacto^" + telefonoContacto
         + "~mail_contacto^" + mailContacto
         + "~nombre_contacto2^" + nombreContacto2
         + "~cargo_contacto2^" + cargoContacto2
         + "~telefono_contacto2^" + telefonoContacto2
         + "~mail_contacto2^" + mailContacto2
         + "~nombre_contacto3^" + nombreContacto3
         + "~cargo_contacto3^" + cargoContacto3
         + "~telefono_contacto3^" + telefonoContacto3
         + "~mail_contacto3^" + mailContacto3
         + "~personaActualiza^" + personaActualiza;
         */
    }

}
