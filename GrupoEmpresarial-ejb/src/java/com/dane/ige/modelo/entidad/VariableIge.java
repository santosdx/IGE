package com.dane.ige.modelo.entidad;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author srojasm
 */
@Entity
@Table(name = "ige_variable")
@NamedQueries({
    @NamedQuery(name = "VariableIge.findAll", query = "SELECT u FROM VariableIge u order by u.columna"),
    @NamedQuery(name = "VariableIge.findById", query = "SELECT u FROM VariableIge u WHERE u.id = :id"),
    @NamedQuery(name = "VariableIge.findByVariable", query = "SELECT u FROM VariableIge u WHERE u.columna = :columna"),
    @NamedQuery(name = "VariableIge.findByGrupo", query = "SELECT u FROM VariableIge u WHERE u.grupo = :grupo"),})
public class VariableIge implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String FINE_ALL = "VariableIge.findAll";
    public static final String FINE_BYE_ID = "VariableIge.findById";
    public static final String FINE_BYE_VARIABLE = "VariableIge.findByVariable";
    public static final String FINE_BYE_GRUPO = "VariableIge.findByGrupo";

    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USUARIO")
    //@SequenceGenerator(name = "SEQ_USUARIO", sequenceName = "seq_id_ige_usuario", allocationSize = 1)
    @Column(name = "id_ige_VARIABLE", unique = true, nullable = false)
    private Integer id;
    @Column(name = "columna")
    private String columna;
    @Column(name = "grupo")
    private String grupo;
    @Column(name = "tabla")
    private String tabla;
    @Column(name = "etiqueta")
    private String etiqueta;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "longitud")
    private Integer longitud;
    @Column(name = "editable")
    private String editable;
    @Column(name = "nombre_atributo_clase")
    private String nombreAtributoClase;
    @Column(name = "obligatoria")
    private String obligatoria;

    public VariableIge() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getColumna() {
        return columna;
    }

    public void setColumna(String columna) {
        this.columna = columna;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getLongitud() {
        return longitud;
    }

    public void setLongitud(Integer longitud) {
        this.longitud = longitud;
    }

    public String getEditable() {
        return editable;
    }

    public void setEditable(String editable) {
        this.editable = editable;
    }

    public String getNombreAtributoClase() {
        return nombreAtributoClase;
    }

    public void setNombreAtributoClase(String nombreAtributoClase) {
        this.nombreAtributoClase = nombreAtributoClase;
    }

    public String getObligatoria() {
        return obligatoria;
    }

    public void setObligatoria(String obligatoria) {
        this.obligatoria = obligatoria;
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
        if (!(object instanceof VariableIge)) {
            return false;
        }
        VariableIge other = (VariableIge) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dane.ige.modelo.entidad.VariableIge[ id=" + id + " ]";
    }

}
