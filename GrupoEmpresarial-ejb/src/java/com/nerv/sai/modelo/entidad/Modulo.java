package com.nerv.sai.modelo.entidad;

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
@Table(name = "ige_modulo")
@NamedQueries({
    @NamedQuery(name = "Modulo.findAll", query = "SELECT m FROM Modulo m"),
    @NamedQuery(name = "Modulo.findById", query = "SELECT m FROM Modulo m WHERE m.id = :id"),
    @NamedQuery(name = "Modulo.findByModulo", query = "SELECT m FROM Modulo m WHERE m.modulo = :modulo"),    
    @NamedQuery(name = "Modulo.findByDescripcion", query = "SELECT m FROM Modulo m WHERE m.descripcion = :descripcion")})
public class Modulo implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public static final String FINE_BYE_MODULO = "Modulo.findByModulo";
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ")
    @SequenceGenerator(name = "SEQ", sequenceName = "seq_id_ige_modulo")
    @Column(name = "id_ige_modulo", unique = true, nullable = false, scale = 0)
    private Integer id;
    @Column(name = "modulo")
    private String modulo;
    @Column(name = "descripcion")
    private String descripcion;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "ige_modulo_permiso",
            joinColumns = @JoinColumn(name = "id_ige_modulo"),
            inverseJoinColumns = @JoinColumn(name = "id_ige_permiso")
    )
    private List<Permiso> permisos;      

    public Modulo() {
    }

    public Modulo(Integer id) {
        this.id = id;
    }

    public Modulo(String modulo, String descripcion) {        
        this.modulo = modulo;
        this.descripcion = descripcion;
    }

    public Modulo(Integer id, String modulo, String descripcion) {
        this.id = id;
        this.modulo = modulo;
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = WordUtils.capitalize(modulo.trim());
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Permiso> getPermisos() {
        List<Permiso> lista = new ArrayList<Permiso>(permisos);
        Collections.copy(lista, permisos);       
        return lista;
    }

    public void setPermisos(List<Permiso> permisos) {
        this.permisos = permisos;
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
        if (!(object instanceof Modulo)) {
            return false;
        }
        Modulo other = (Modulo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nerv.sai.modelo.entidad.Modulo[ id=" + id + " ]";
    }
    
}
