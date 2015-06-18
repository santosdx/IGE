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
@Table(name = "ige_perfil")
@NamedQueries({
    @NamedQuery(name = "Perfil.findAll", query = "SELECT p FROM Perfil p"),
    @NamedQuery(name = "Perfil.findById", query = "SELECT p FROM Perfil p WHERE p.id = :id"),
    @NamedQuery(name = "Perfil.findByPerfil", query = "SELECT p FROM Perfil p WHERE p.perfil = :perfil"),
    @NamedQuery(name = "Perfil.findByDescripcion", query = "SELECT p FROM Perfil p WHERE p.descripcion = :descripcion")})
public class Perfil implements Serializable {
        
    private static final long serialVersionUID = 1L;
    
    public static final String FINE_BYE_PERFIL = "Perfil.findByPerfil";
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ")
    @SequenceGenerator(name = "SEQ", sequenceName = "seq_id_ige_perfil")
    @Column(name = "id_ige_perfil", unique = true, nullable = false)
    private Integer id;
    @Column(name = "perfil")
    private String perfil;
    @Column(name = "descripcion")
    private String descripcion;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "ige_perfil_permiso",
            joinColumns = @JoinColumn(name = "id_ige_perfil"),
            inverseJoinColumns = @JoinColumn(name = "id_ige_permiso")
    )
    private List<Permiso> permisos;    

    public Perfil() {
    }

    public Perfil(Integer id) {
        this.id = id;
    }
        
    public Perfil(String perfil, String descripcion) {
        this.perfil=perfil;
        this.descripcion=descripcion;
    }

    public Perfil(Integer id, String perfil, String descripcion) {
        this.id = id;
        this.perfil = perfil;
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = WordUtils.capitalize(perfil.trim());
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
        if (!(object instanceof Perfil)) {
            return false;
        }
        Perfil other = (Perfil) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nerv.sai.modelo.entidad.Perfil[ id=" + id + " ]";
    }
    
}
