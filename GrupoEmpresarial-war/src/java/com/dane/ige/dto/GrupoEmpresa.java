package com.dane.ige.dto;

import com.dane.ige.modelo.entidad.BodegaIdentificacion;
import com.dane.ige.modelo.entidad.BodegaNovedad;
import com.dane.ige.modelo.entidad.BodegaRelacion;
import com.dane.ige.modelo.entidad.BodegaTamano;

/**
 * Clase que describe el objeto que representa el Grupo Empresa con la
 * información de los 4 grupos de datos, identificación, relación, novedad y
 * tamaño.
 *
 * @author SRojasM
 */
public class GrupoEmpresa {

    private BodegaIdentificacion identificacion;
    private BodegaRelacion relacion;
    private BodegaNovedad novedad;
    private BodegaTamano tamano;

    public GrupoEmpresa(BodegaIdentificacion identificacion, BodegaRelacion relacion, BodegaNovedad novedad, BodegaTamano tamano) {
        this.identificacion = identificacion;
        this.relacion = relacion;
        this.novedad = novedad;
        this.tamano = tamano;
    }

    public BodegaIdentificacion getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(BodegaIdentificacion identificacion) {
        this.identificacion = identificacion;
    }

    public BodegaRelacion getRelacion() {
        return relacion;
    }

    public void setRelacion(BodegaRelacion relacion) {
        this.relacion = relacion;
    }

    public BodegaNovedad getNovedad() {
        return novedad;
    }

    public void setNovedad(BodegaNovedad novedad) {
        this.novedad = novedad;
    }

    public BodegaTamano getTamano() {
        return tamano;
    }

    public void setTamano(BodegaTamano tamano) {
        this.tamano = tamano;
    }

}
