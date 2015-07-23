package com.dane.ige.excel;

import com.dane.ige.modelo.entidad.VariableIge;
import com.dane.ige.modelo.local.administracion.BodegaIdentificacionFacadeLocal;
import com.dane.ige.modelo.local.administracion.BodegaNovedadFacadeLocal;
import com.dane.ige.modelo.local.administracion.BodegaRelacionFacadeLocal;
import com.dane.ige.modelo.local.administracion.BodegaTamanoFacadeLocal;
import com.dane.ige.modelo.local.administracion.VariableIgeFacadeLocal;
import com.dane.ige.seguridad.Login;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author SRojasM
 */
@ManagedBean(name = "MbEscribirExcelGrupoEmpresa")
@ViewScoped
public class EscribirExcelGrupoEmpresa {

    final static Logger LOGGER = Logger.getLogger(EscribirExcelGrupoEmpresa.class);

    @EJB
    private VariableIgeFacadeLocal eJBServicioVariableIge;
    private StreamedContent file;

    @EJB
    private BodegaIdentificacionFacadeLocal eJBServicioBodegaIdentificacion;
    @EJB
    private BodegaNovedadFacadeLocal eJBServicioBodegaNovedad;
    @EJB
    private BodegaTamanoFacadeLocal eJBServicioBodegaTamano;
    @EJB
    private BodegaRelacionFacadeLocal eJBServicioBodegaRelacion;

    @ManagedProperty("#{MbLogin}")
    private Login servicioLogin;

    private Map<String, String> identificacionSeleccionada;
    private Map<String, String> novedadSeleccionada;
    private Map<String, String> relacionSeleccionada;
    private Map<String, String> tamanoSeleccionado;

    public EscribirExcelGrupoEmpresa() {
    }

    public void generarArchivoXls(String urlArchivo, String nombreArchivo, Long id, String unidad) {
        try {
            LOGGER.info(urlArchivo);
            LOGGER.info(nombreArchivo);

            String filename = urlArchivo + nombreArchivo;
            FileInputStream fis = new FileInputStream(filename);

            Workbook workbook = new HSSFWorkbook(fis);
            escribirLibroXls(workbook, nombreArchivo, id, unidad);

        } catch (FileNotFoundException ex) {
            LOGGER.warn(ex.getMessage());
            //java.util.logging.Logger.getLogger(EscribirExcelGrupoEmpresa.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            LOGGER.warn(ex.getMessage());
            //java.util.logging.Logger.getLogger(EscribirExcelGrupoEmpresa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método que permite escribir los datos de la plantilla xls del grupo
     * empresa y generar el archivo para ser descargado desde la vista.
     *
     * @param libro
     */
    private void escribirLibroXls(Workbook libro, String nombreArchivo, Long id, String unidad) {
        //Long id = Long.parseLong(getServicioLogin().getUsuarioLogueado().getIdIdentificacion() + "");
        //String unidad = "GRUPO";

        setIdentificacionSeleccionada(geteJBServicioBodegaIdentificacion().obtenerMapIdentificacionByIdTipoOrganizacion(id, unidad));
        Workbook libroTemp = ingresarDatosIdentificacion(libro);

        setRelacionSeleccionada(geteJBServicioBodegaRelacion().obtenerMapRelacionGrupoEmpresaById(id));
        ingresarDatosRelacion(libroTemp);

        setNovedadSeleccionada(geteJBServicioBodegaNovedad().obtenerMapNovedadGrupoEmpresaById(id));
        ingresarDatosEventos(libroTemp);

        setTamanoSeleccionado(geteJBServicioBodegaTamano().obtenerMapTamanoGrupoEmpresaById(id));
        ingresarDatosTamano(libroTemp);
        /*
         LeerExcel excel = new LeerExcel();
         excel.visualizarDatosXls(excel.obtenerListaDatosHojaXls(libroTemp.getSheetAt(1)));
         excel.visualizarDatosXls(excel.obtenerListaDatosHojaXls(libroTemp.getSheetAt(2)));
         */
        try {
            File temp = File.createTempFile(nombreArchivo, ".xls");
            FileOutputStream elFichero = new FileOutputStream(temp);
            libro.write(elFichero);

            setFile(new DefaultStreamedContent(new FileInputStream(temp), "application/vnd.ms-excel", nombreArchivo));
            elFichero.close();
            temp.deleteOnExit();
            temp.delete();
        } catch (IOException e) {
            LOGGER.warn(e.getMessage());
        }
    }

    /**
     * Método que permite ingresar los datos de identificación la plantilla xls
     * de el grupo empresa.
     *
     * @param libro
     * @return libro
     */
    private Workbook ingresarDatosIdentificacion(Workbook libro) {
        //La hoja 1 es la hoja de los datos de identificacion
        List<VariableIge> columnas = geteJBServicioVariableIge().buscarVariableByGrupo("IDENTIFICACION");
        Sheet hoja = libro.getSheetAt(1);
        hoja.protectSheet("123");

        Row encabezadoXsl = hoja.getRow(1);
        Iterator cells = encabezadoXsl.cellIterator();
        int indiceColumna = 0;
        Row fila = hoja.getRow(2);
        while (cells.hasNext()) {
            Cell cell = (Cell) cells.next();
            for (VariableIge variableIge : columnas) {
                if (cell.getStringCellValue().trim().toLowerCase().equals(variableIge.getEtiqueta().trim().toLowerCase())) {
                    Cell celda = fila.getCell(indiceColumna);
                    celda.setCellStyle(estiloBordeCompletoCedaEditable(libro, Boolean.parseBoolean(variableIge.getEditable())));
                    celda.setCellValue(getIdentificacionSeleccionada().get(variableIge.getColumna().trim()));
                    break;
                }
            }
            indiceColumna++;
        }

        return libro;
    }

    /**
     * Método que permite ingresar los datos de relación a la plantilla xls de el
     * grupo empresa.
     *
     * @param libro
     * @return libro
     */
    private Workbook ingresarDatosRelacion(Workbook libro) {
        //La hoja 2 es la hoja de los datos de relación
        List<VariableIge> columnas = geteJBServicioVariableIge().buscarVariableByGrupo("RELACION");
        Sheet hoja = libro.getSheetAt(2);
        hoja.protectSheet("123");

        Row encabezadoXsl = hoja.getRow(1);
        Iterator cells = encabezadoXsl.cellIterator();
        int indiceColumna = 0;
        Row fila = hoja.getRow(2);
        while (cells.hasNext()) {
            Cell cell = (Cell) cells.next();
            cell.setCellType(1);
            for (VariableIge variableIge : columnas) {
                if (cell.getStringCellValue().trim().toLowerCase().equals(variableIge.getEtiqueta().trim().toLowerCase())) {
                    Cell celda = fila.getCell(indiceColumna);
                    celda.setCellStyle(estiloBordeCompletoCedaEditable(libro, Boolean.parseBoolean(variableIge.getEditable())));
                    celda.setCellValue(getRelacionSeleccionada().get(variableIge.getColumna().trim()));
                    break;
                }
            }
            indiceColumna++;
        }

        return libro;
    }

    /**
     * Método que permite ingresar los datos de novedad a la plantilla xls de el
     * grupo empresa.
     *
     * @param libro
     * @return libro
     */
    private Workbook ingresarDatosEventos(Workbook libro) {
        //La hoja 3 es la hoja de los datos de eventos
        List<VariableIge> columnas = geteJBServicioVariableIge().buscarVariableByGrupo("NOVEDAD");
        Sheet hoja = libro.getSheetAt(3);
        hoja.protectSheet("123");

        Row encabezadoXsl = hoja.getRow(1);
        Iterator cells = encabezadoXsl.cellIterator();
        int indiceColumna = 0;
        Row fila = hoja.getRow(2);
        while (cells.hasNext()) {
            Cell cell = (Cell) cells.next();
            for (VariableIge variableIge : columnas) {
                if (cell.getStringCellValue().trim().toLowerCase().equals(variableIge.getEtiqueta().trim().toLowerCase())) {
                    Cell celda = fila.getCell(indiceColumna);
                    celda.setCellStyle(estiloBordeCompletoCedaEditable(libro, Boolean.parseBoolean(variableIge.getEditable())));
                    celda.setCellValue(getNovedadSeleccionada().get(variableIge.getColumna().trim()));
                    break;
                }
            }
            indiceColumna++;
        }

        return libro;
    }

    /**
     * Método que permite ingresar los datos de tamaño a la plantilla xls de el
     * grupo empresa.
     *
     * @param libro
     * @return libro
     */
    private Workbook ingresarDatosTamano(Workbook libro) {
        //La hoja 3 es la hoja de los datos de tamaño
        List<VariableIge> columnas = geteJBServicioVariableIge().buscarVariableByGrupo("TAMAÑO");
        Sheet hoja = libro.getSheetAt(4);
        hoja.protectSheet("123");

        Row encabezadoXsl = hoja.getRow(1);
        Iterator cells = encabezadoXsl.cellIterator();
        int indiceColumna = 0;
        Row fila = hoja.getRow(2);
        while (cells.hasNext()) {
            Cell cell = (Cell) cells.next();
            for (VariableIge variableIge : columnas) {
                if (cell.getStringCellValue().trim().toLowerCase().equals(variableIge.getEtiqueta().trim().toLowerCase())) {
                    Cell celda = fila.getCell(indiceColumna);
                    celda.setCellStyle(estiloBordeCompletoCedaEditable(libro, Boolean.parseBoolean(variableIge.getEditable())));
                    celda.setCellValue(getTamanoSeleccionado().get(variableIge.getColumna().trim()));
                    break;
                }
            }
            indiceColumna++;
        }

        return libro;
    }

    /**
     * Método que permite obtener el estilo de una celda aplicando el borde
     * completo. Incluye que la celda este bloqueada para exritura, de acuerdo
     * al parametro.
     *
     * @param libro
     * @param editable
     * @return
     */
    private CellStyle estiloBordeCompletoCedaEditable(Workbook libro, boolean editable) {
        CellStyle css = libro.createCellStyle();
        css.setLocked((editable != true));
        css.setBorderTop(CellStyle.BORDER_THIN);
        css.setTopBorderColor(IndexedColors.BLACK.getIndex());
        css.setBorderLeft(CellStyle.BORDER_THIN);
        css.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        css.setBorderRight(CellStyle.BORDER_THIN);
        css.setRightBorderColor(IndexedColors.BLACK.getIndex());
        css.setBorderBottom(CellStyle.BORDER_THIN);
        css.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        return css;
    }

    //Métodos Set y Get de la clase
    public VariableIgeFacadeLocal geteJBServicioVariableIge() {
        return eJBServicioVariableIge;
    }

    public void seteJBServicioVariableIge(VariableIgeFacadeLocal eJBServicioVariableIge) {
        this.eJBServicioVariableIge = eJBServicioVariableIge;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public BodegaIdentificacionFacadeLocal geteJBServicioBodegaIdentificacion() {
        return eJBServicioBodegaIdentificacion;
    }

    public void seteJBServicioBodegaIdentificacion(BodegaIdentificacionFacadeLocal eJBServicioBodegaIdentificacion) {
        this.eJBServicioBodegaIdentificacion = eJBServicioBodegaIdentificacion;
    }

    public BodegaNovedadFacadeLocal geteJBServicioBodegaNovedad() {
        return eJBServicioBodegaNovedad;
    }

    public void seteJBServicioBodegaNovedad(BodegaNovedadFacadeLocal eJBServicioBodegaNovedad) {
        this.eJBServicioBodegaNovedad = eJBServicioBodegaNovedad;
    }

    public BodegaTamanoFacadeLocal geteJBServicioBodegaTamano() {
        return eJBServicioBodegaTamano;
    }

    public void seteJBServicioBodegaTamano(BodegaTamanoFacadeLocal eJBServicioBodegaTamano) {
        this.eJBServicioBodegaTamano = eJBServicioBodegaTamano;
    }

    public BodegaRelacionFacadeLocal geteJBServicioBodegaRelacion() {
        return eJBServicioBodegaRelacion;
    }

    public void seteJBServicioBodegaRelacion(BodegaRelacionFacadeLocal eJBServicioBodegaRelacion) {
        this.eJBServicioBodegaRelacion = eJBServicioBodegaRelacion;
    }

    public Login getServicioLogin() {
        return servicioLogin;
    }

    public void setServicioLogin(Login servicioLogin) {
        this.servicioLogin = servicioLogin;
    }

    public Map<String, String> getIdentificacionSeleccionada() {
        return identificacionSeleccionada;
    }

    public void setIdentificacionSeleccionada(Map<String, String> identificacionSeleccionada) {
        this.identificacionSeleccionada = identificacionSeleccionada;
    }

    public Map<String, String> getNovedadSeleccionada() {
        return novedadSeleccionada;
    }

    public void setNovedadSeleccionada(Map<String, String> novedadSeleccionada) {
        this.novedadSeleccionada = novedadSeleccionada;
    }

    public Map<String, String> getRelacionSeleccionada() {
        return relacionSeleccionada;
    }

    public void setRelacionSeleccionada(Map<String, String> relacionSeleccionada) {
        this.relacionSeleccionada = relacionSeleccionada;
    }

    public Map<String, String> getTamanoSeleccionado() {
        return tamanoSeleccionado;
    }

    public void setTamanoSeleccionado(Map<String, String> tamanoSeleccionado) {
        this.tamanoSeleccionado = tamanoSeleccionado;
    }

}
