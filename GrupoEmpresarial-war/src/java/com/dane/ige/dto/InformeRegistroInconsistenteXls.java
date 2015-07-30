package com.dane.ige.dto;

import java.util.List;

/**
 * Clase que define un objeto para describir las inconsistencias que presenta un
 * registro de la lectura de un archivo XLS
 *
 * @author SRojasM
 */
public class InformeRegistroInconsistenteXls {

    private Integer id;
    private String origen;
    private List<String> listaAtributosRequeridos;

    public InformeRegistroInconsistenteXls() {
    }

    public InformeRegistroInconsistenteXls(Integer id, String origen, List<String> listaAtributosRequeridos) {
        this.id = id;
        this.origen = origen;
        this.listaAtributosRequeridos = listaAtributosRequeridos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public List<String> getListaAtributosRequeridos() {
        return listaAtributosRequeridos;
    }

    public void setListaAtributosRequeridos(List<String> listaAtributosRequeridos) {
        this.listaAtributosRequeridos = listaAtributosRequeridos;
    }

}
