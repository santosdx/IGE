package com.dane.ige.modelo.entidad;

import com.dane.ige.modelo.llave.PkIdentificacion;
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
@Table(name = "ige_identificacion")
@NamedQueries({
    @NamedQuery(name = "BodegaIdentificacion.findAll", query = "SELECT i FROM BodegaIdentificacion i"),
    @NamedQuery(name = "BodegaIdentificacion.findById", 
            query = "SELECT i FROM BodegaIdentificacion i WHERE i.id.id = :id AND i.tipoOrganizacion=:tipoOrganizacion AND ROWNUM<=1 ORDER BY i.id.fecha DESC"),
    @NamedQuery(name = "BodegaIdentificacion.findByIdGrupoRelacionadoTipoOrganizacion", 
            query = "SELECT i FROM BodegaIdentificacion i WHERE i.idGrupoRelacionado = :idGrupoRelacionado AND i.tipoOrganizacion=:tipoOrganizacion ORDER BY i.nombreRegistrado")})
public class BodegaIdentificacion implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String GET_ALL = "BodegaIdentificacion.findAll";
    public static final String FINE_BYE_ID = "BodegaIdentificacion.findById";
    public static final String FINE_BYE_ID_GRUPO_RELACIONADO = "BodegaIdentificacion.findByIdGrupoRelacionadoTipoOrganizacion";

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
    @Column(name = "pagina_web")
    private String paginaWeb;
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

    public String getPaginaWeb() {
        return paginaWeb;
    }

    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
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
        return unidadAuxiliarUl;
    }

    public void setUnidadAuxiliarUl(String unidadAuxiliarUl) {
        this.unidadAuxiliarUl = unidadAuxiliarUl;
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
        return orientadaMercadoUl;
    }

    public void setOrientadaMercadoUl(String orientadaMercadoUl) {
        this.orientadaMercadoUl = orientadaMercadoUl;
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

    @Override
    public String toString() {
        return "com.dabe.ige.modelo.entidad.BodegaIdentificacion[ id=" + id + " ]";
    }

}
