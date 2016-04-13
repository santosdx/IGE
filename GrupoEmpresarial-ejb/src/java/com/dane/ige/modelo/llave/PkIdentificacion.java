package com.dane.ige.modelo.llave;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Clase que representa la llave principal PrimariKey de la tabla
 * ige_identificacion
 *
 * @author SRojasM
 */
@Embeddable
public class PkIdentificacion implements Serializable {

    //@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SeqGen")
    //@SequenceGenerator(name = "SeqGen", sequenceName = "SEQ_DEE_COCA", allocationSize = 1)
    @Column(name = "id_organizacion")
    private Long id;
    //@Id
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_actualiza_iden")
    private Date fecha;

    public PkIdentificacion() {
    }

    public PkIdentificacion(Long id, Date fecha) {
        this.id = id;
        this.fecha = fecha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
