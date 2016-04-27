package com.dane.ige.indicador;

import com.dane.ige.modelo.entidad.BodegaIdentificacion;
import com.dane.ige.modelo.entidad.ObjetoIndicador;
import com.dane.ige.modelo.local.administracion.BodegaIdentificacionFacadeLocal;
import com.dane.ige.modelo.servicios.administracion.BodegaIndicadorGrupo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author SRojasM
 */
@ManagedBean(name = "MbIndicadorGrupo")
@SessionScoped
public class IndicadorGrupo implements Serializable {

    @EJB
    private BodegaIdentificacionFacadeLocal eJBServicioBodegaIdentificacion;
    @EJB
    private BodegaIndicadorGrupo eJBservicioBodegaIndicadorGrupo;

    private PieChartModel pieModeUnidadLegalDepartamento;
    private PieChartModel pieModeUnidadLegalMunicipio;
    private PieChartModel pieModeUnidadLegalPersonalOcupado;

    private Long idIdentificacionSeleccionada;
    private List<BodegaIdentificacion> listaIdentificacion;

    /**
     * Creates a new instance of IndicadorGrupo
     */
    public IndicadorGrupo() {
    }

    @PostConstruct
    public void init() {
        setPieModeUnidadLegalDepartamento(new PieChartModel());
        getPieModeUnidadLegalDepartamento().set("Item", 100);
        setPieModeUnidadLegalMunicipio(new PieChartModel());
        getPieModeUnidadLegalMunicipio().set("Item", 100);
        setPieModeUnidadLegalPersonalOcupado(new PieChartModel());
        getPieModeUnidadLegalPersonalOcupado().set("Item", 100);
        setListaIdentificacion(eJBServicioBodegaIdentificacion.obtenerListaIdentificacionTodosLosGrupos());
    }

    public void mySelectionMethodListener(AjaxBehaviorEvent event) {
        cargarIndicadores();
    }

    public void cargarIndicadores() {
        List<ObjetoIndicador> datosIndicadorUnidadLegalDepartamento;
        List<ObjetoIndicador> datosIndicadorUnidadLegalMunicipio;
        List<ObjetoIndicador> datosIndicadorUnidadLegalPersonalOcupado;

        setPieModeUnidadLegalDepartamento(new PieChartModel());
        datosIndicadorUnidadLegalDepartamento = eJBservicioBodegaIndicadorGrupo.indicadorUnidadLegalPorDepartamentoDelGrpupo(getIdIdentificacionSeleccionada());
        for (ObjetoIndicador objetoIndicador : datosIndicadorUnidadLegalDepartamento) {
            getPieModeUnidadLegalDepartamento().set(objetoIndicador.getItem(), objetoIndicador.getValor());
        }
        getPieModeUnidadLegalDepartamento().setTitle("Unidad Legal por Departamento");
        getPieModeUnidadLegalDepartamento().setLegendPosition("ne");
        getPieModeUnidadLegalDepartamento().setShowDataLabels(true);
        getPieModeUnidadLegalDepartamento().setMouseoverHighlight(true);

        setPieModeUnidadLegalMunicipio(new PieChartModel());
        datosIndicadorUnidadLegalMunicipio = eJBservicioBodegaIndicadorGrupo.indicadorUnidadLegalPorMunicipioDelGrpupo(getIdIdentificacionSeleccionada());
        for (ObjetoIndicador objetoIndicador : datosIndicadorUnidadLegalMunicipio) {
            getPieModeUnidadLegalMunicipio().set(objetoIndicador.getItem(), objetoIndicador.getValor());
        }
        getPieModeUnidadLegalMunicipio().setTitle("Unidad Legal por Municipio");
        getPieModeUnidadLegalMunicipio().setLegendPosition("ne");
        getPieModeUnidadLegalMunicipio().setShowDataLabels(true);
        getPieModeUnidadLegalMunicipio().setMouseoverHighlight(true);
        
        setPieModeUnidadLegalPersonalOcupado(new PieChartModel());
        datosIndicadorUnidadLegalPersonalOcupado = eJBservicioBodegaIndicadorGrupo.indicadorUnidadLegalPorPersonalOcupadoDelGrpupo(getIdIdentificacionSeleccionada());
        for (ObjetoIndicador objetoIndicador : datosIndicadorUnidadLegalPersonalOcupado) {
            getPieModeUnidadLegalPersonalOcupado().set(objetoIndicador.getItem(), objetoIndicador.getValor());
        }
        getPieModeUnidadLegalPersonalOcupado().setTitle("Unidad Legal por Personal Ocupado");
        getPieModeUnidadLegalPersonalOcupado().setLegendPosition("ne");
        getPieModeUnidadLegalPersonalOcupado().setShowDataLabels(true);
        getPieModeUnidadLegalPersonalOcupado().setMouseoverHighlight(true);
    }

    public PieChartModel getPieModeUnidadLegalDepartamento() {
        return pieModeUnidadLegalDepartamento;
    }

    public void setPieModeUnidadLegalDepartamento(PieChartModel pieModeUnidadLegalDepartamento) {
        this.pieModeUnidadLegalDepartamento = pieModeUnidadLegalDepartamento;
    }

    public PieChartModel getPieModeUnidadLegalMunicipio() {
        return pieModeUnidadLegalMunicipio;
    }

    public void setPieModeUnidadLegalMunicipio(PieChartModel pieModeUnidadLegalMunicipio) {
        this.pieModeUnidadLegalMunicipio = pieModeUnidadLegalMunicipio;
    }

    public PieChartModel getPieModeUnidadLegalPersonalOcupado() {
        return pieModeUnidadLegalPersonalOcupado;
    }

    public void setPieModeUnidadLegalPersonalOcupado(PieChartModel pieModeUnidadLegalPersonalOcupado) {
        this.pieModeUnidadLegalPersonalOcupado = pieModeUnidadLegalPersonalOcupado;
    }

    public List<BodegaIdentificacion> getListaIdentificacion() {
        return listaIdentificacion;
    }

    public void setListaIdentificacion(List<BodegaIdentificacion> listaIdentificacion) {
        this.listaIdentificacion = listaIdentificacion;
    }

    public Long getIdIdentificacionSeleccionada() {
        return idIdentificacionSeleccionada;
    }

    public void setIdIdentificacionSeleccionada(Long idIdentificacionSeleccionada) {
        this.idIdentificacionSeleccionada = idIdentificacionSeleccionada;
    }


}
