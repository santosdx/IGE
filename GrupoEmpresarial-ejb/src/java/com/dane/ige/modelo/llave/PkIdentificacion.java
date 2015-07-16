package com.dane.ige.modelo.llave;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author SRojasM
 */
@Embeddable
public class PkIdentificacion implements Serializable {

    //@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SeqGen")
    //@SequenceGenerator(name = "SeqGen", sequenceName = "SEQ_DEE_COCA", allocationSize = 1)
    @Column(name = "ID_ORGANIZACION")
    private Integer id;
    //@Id
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA_ACTUALIZA_IDEN")
    private Date fecha;

    public PkIdentificacion() {
    }

    public PkIdentificacion(Integer id, Date fecha) {
        this.id = id;
        this.fecha = fecha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
