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
    private List<Inconsistencia> listaInconsistencias;

    public InformeRegistroInconsistenteXls() {
    }

    public InformeRegistroInconsistenteXls(Integer id, String origen, List<Inconsistencia> listaInconsistencias) {
        this.id = id;
        this.origen = origen;
        this.listaInconsistencias = listaInconsistencias;
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

    public List<Inconsistencia> getListaInconsistencias() {
        return listaInconsistencias;
    }

    public void setListaInconsistencias(List<Inconsistencia> listaInconsistencias) {
        this.listaInconsistencias = listaInconsistencias;
    }

    public static class Inconsistencia {

        private int id;
        private String variable;
        private String inconsistencia;

        public Inconsistencia() {
        }

        public Inconsistencia(int id, String variable, String inconsistencia) {
            this.id = id;
            this.variable = variable;
            this.inconsistencia = inconsistencia;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getVariable() {
            return variable;
        }

        public void setVariable(String variable) {
            this.variable = variable;
        }

        public String getInconsistencia() {
            return inconsistencia;
        }

        public void setInconsistencia(String inconsistencia) {
            this.inconsistencia = inconsistencia;
        }

    }

}
