package com.dane.ige.excel;

import com.dane.ige.modelo.entidad.ArchivoXls;
import com.dane.ige.modelo.entidad.BodegaIdentificacion;
import com.dane.ige.modelo.entidad.VariableIge;
import com.dane.ige.modelo.local.administracion.ArchivoXlsFacadeLocal;
import com.dane.ige.modelo.local.administracion.BodegaIdentificacionFacadeLocal;
import com.dane.ige.modelo.local.administracion.BodegaNovedadFacadeLocal;
import com.dane.ige.modelo.local.administracion.BodegaRelacionFacadeLocal;
import com.dane.ige.modelo.local.administracion.BodegaTamanoFacadeLocal;
import com.dane.ige.modelo.local.administracion.VariableIgeFacadeLocal;
import com.dane.ige.seguridad.Login;
import com.dane.ige.utilidad.ArchivoProperties;
import com.dane.ige.utilidad.Fecha;
import com.dane.ige.utilidad.FileDownload;
import com.dane.ige.utilidad.Mensaje;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
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
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author SRojasM
 */
@ManagedBean(name = "MbEscribirExcelUnidadLegal")
@ViewScoped
public class EscribirExcelUnidadLegal {

    final static Logger LOGGER = Logger.getLogger(EscribirExcelUnidadLegal.class);

    @EJB
    private VariableIgeFacadeLocal eJBServicioVariableIge;
    private StreamedContent file;
    private String passwordShee = "123";

    @EJB
    private BodegaIdentificacionFacadeLocal eJBServicioBodegaIdentificacion;
    @EJB
    private BodegaNovedadFacadeLocal eJBServicioBodegaNovedad;
    @EJB
    private BodegaTamanoFacadeLocal eJBServicioBodegaTamano;
    @EJB
    private BodegaRelacionFacadeLocal eJBServicioBodegaRelacion;
    @EJB
    private ArchivoXlsFacadeLocal eJBServicioArchivoXls;

    @ManagedProperty("#{MbLogin}")
    private Login servicioLogin;

    private List<BodegaIdentificacion> listaIdentificacion;
    private Map<String, String> identificacionSeleccionada;
    private Map<String, String> novedadSeleccionada;
    private Map<String, String> relacionSeleccionada;
    private Map<String, String> tamanoSeleccionado;

    public EscribirExcelUnidadLegal() {
    }

    public void generarArchivoXls() {
        //MbEscribirExcelUnidadLegal.generarArchivoXls(resourcePath['plantilla.unidadLegal.path'], resourcePath['plantilla.unidadLegal.archivo'])
        String urlArchivo = ArchivoProperties.obtenerPropertieFilePathProperties("plantilla.unidadLegal.path");
        String nombreArchivo = ArchivoProperties.obtenerPropertieFilePathProperties("plantilla.unidadLegal.archivo");
        try {
            //LOGGER.info(urlArchivo);
            //LOGGER.info(nombreArchivo);

            String filename = urlArchivo + nombreArchivo;
            FileInputStream fis = new FileInputStream(filename);

            Workbook workbook = new HSSFWorkbook(fis);
            escribirLibroXls(workbook, nombreArchivo);

        } catch (FileNotFoundException ex) {
            LOGGER.warn("[89] EscribirExcelUnidadLegal.java -> "+ex.getMessage());
            Mensaje.agregarMensajeGrowlError("Error!", ex.getMessage());
            //java.util.logging.Logger.getLogger(EscribirExcelGrupoEmpresa.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            LOGGER.warn("[93] EscribirExcelUnidadLegal.java -> "+ex.getMessage());
            Mensaje.agregarMensajeGrowlError("Error!", ex.getMessage());
            //java.util.logging.Logger.getLogger(EscribirExcelGrupoEmpresa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método que permite escribir los datos de la plantilla xls de las unidades
     * legales del grupo empresa seleccionado y generar el archivo para ser
     * descargado desde la vista.
     *
     * @param libro
     */
    private void escribirLibroXls(Workbook libro, String nombreArchivo) {
        Long id = getServicioLogin().getUsuarioLogueado().getIdIdentificacion();
        String tempPathFile = ArchivoProperties.obtenerPropertieFilePathProperties("sistema.tempFile.path");
        nombreArchivo = getServicioLogin().getUsuarioLogueado().getId()+"-"+nombreArchivo;

        setListaIdentificacion(geteJBServicioBodegaIdentificacion().obtenerListaIdentificacionUnidadLegalByIdGrupoRelacionadoTipoOrganizacion(id));

        Workbook libroTemp = ingresarDatosIdentificacion(libro);
        ingresarDatosRelacion(libroTemp);
        ingresarDatosEventos(libroTemp);
        ingresarDatosTamano(libroTemp);

        Long idGrupo = id;
        String unidad = "UNIDAD_LEGAL";
        Date fechaEvento = new Date();
        String evento = "DESCARGADO";
        Integer idUsuario = getServicioLogin().getUsuarioLogueado().getId();
        String codigoArchivo = FileDownload.generarCodigoAlphaNumerico();
        ingresarIdentificacionArchivoGenerado(libro, idGrupo, unidad, fechaEvento, evento, idUsuario, codigoArchivo);

        try {
            //File temp = File.createTempFile(nombreArchivo, ".xls");
            File temp = new File(tempPathFile+nombreArchivo);
            FileOutputStream elFichero = new FileOutputStream(temp);
            libro.write(elFichero);

            setFile(new DefaultStreamedContent(new FileInputStream(temp), "application/vnd.ms-excel", nombreArchivo));
            elFichero.close();
            temp.deleteOnExit();
            temp.delete();

            //Copiamos el archivo xls y lo convertimos a byte[]
            StreamedContent fileCopy = new DefaultStreamedContent(new FileInputStream(temp), "application/vnd.ms-excel", nombreArchivo);
            byte[] archivo = FileDownload.inputStreamToByte(fileCopy.getStream());

            //Creamos el registro de base de datos con la información del archivo creado, incluye el archivo.
            ArchivoXls archivoXls = new ArchivoXls(idGrupo, unidad, fechaEvento, evento, idUsuario, codigoArchivo, archivo);
            geteJBServicioArchivoXls().create(archivoXls);

        } catch (IOException e) {
            LOGGER.warn("[143] EscribirExcelUnidadLegal.java -> "+e.getMessage());
            Mensaje.agregarMensajeGrowlError("Error!", e.getMessage());
        }
    }

    /**
     * Método que permite ingresar los datos de identificación del archivo xls
     * que se genera y descarga por el usuario. de el grupo empresa.
     *
     * @param libro
     * @return libro
     */
    private Workbook ingresarIdentificacionArchivoGenerado(Workbook libro, Long idGrupo, String unidad, Date fechaEvento, String evento, Integer idUsuario, String codigoArchivo) {
        //La hoja 1 es la hoja de los datos de identificacion del archivo
        Sheet hoja = libro.getSheet("ID-ARCHIVO");
        hoja.protectSheet(passwordShee);

        Row fila1 = hoja.getRow(1);
        Cell ID_GRUPO = fila1.getCell(1);
        ID_GRUPO.setCellValue(idGrupo+"");

        Row fila2 = hoja.getRow(2);
        Cell UNIDAD = fila2.getCell(1);
        UNIDAD.setCellValue(unidad);

        Row fila3 = hoja.getRow(3);
        Cell FECHA_EVENTO = fila3.getCell(1);
        FECHA_EVENTO.setCellValue(fechaEvento);

        Row fila4 = hoja.getRow(4);
        Cell EVENTO = fila4.getCell(1);
        EVENTO.setCellValue(evento);

        Row fila5 = hoja.getRow(5);
        Cell ID_USUARIO = fila5.getCell(1);
        ID_USUARIO.setCellValue(idUsuario+"");

        Row fila6 = hoja.getRow(6);
        Cell CODIGO_ARCHIVO = fila6.getCell(1);
        CODIGO_ARCHIVO.setCellValue(codigoArchivo);

        return libro;
    }

    /**
     * Método que permite ingresar los datos de identificación la plantilla xls
     * de las unidades legales del grupo empresa.
     *
     * @param libro
     * @return libro
     */
    private Workbook ingresarDatosIdentificacion(Workbook libro) {
        //La hoja 1 es la hoja de los datos de identificacion
        List<VariableIge> columnas = geteJBServicioVariableIge().buscarVariableByGrupo("IDENTIFICACION");
        Sheet hoja = libro.getSheet("Identificación");//getSheetAt(1);
        hoja.protectSheet(passwordShee);

        int indiceRegistro = 1;
        int indiceFila = 2;

        CellStyle cellStyleLocked = EstiloCeldasXls.estiloBordeCompletoCedaEditable(libro, false);
        CellStyle cellStyleUnlocked = EstiloCeldasXls.estiloBordeCompletoCedaEditable(libro, true);
        CellStyle cellStyleLockedFecha = EstiloCeldasXls.estiloBordeCompletoCedaEditableFecha(libro, false);
        CellStyle cellStyleUnlockedFecha = EstiloCeldasXls.estiloBordeCompletoCedaEditableFecha(libro, true);
        CellStyle cellStyleLockedTexto = EstiloCeldasXls.estiloBordeCompletoCedaEditableTexto(libro, false);
        CellStyle cellStyleUnlockedTexto = EstiloCeldasXls.estiloBordeCompletoCedaEditableTexto(libro, true);

        for (BodegaIdentificacion unidad : getListaIdentificacion()) {
            setIdentificacionSeleccionada(geteJBServicioBodegaIdentificacion().obtenerMapIdentificacionByIdTipoOrganizacion(unidad.getId().getId(), "UNIDAD LEGAL"));

            Row encabezadoXsl = hoja.getRow(1);
            Iterator cells = encabezadoXsl.cellIterator();

            int indiceColumna = 0;
            Row fila = hoja.createRow(indiceFila);

            Cell celdaIndice = fila.createCell(indiceColumna);
            celdaIndice.setCellStyle(cellStyleLocked);
            celdaIndice.setCellValue(indiceRegistro);

            while (cells.hasNext()) {

                Cell cell = (Cell) cells.next();
                for (VariableIge variableIge : columnas) {
                    if (cell.getStringCellValue().trim().toLowerCase().equals(variableIge.getEtiqueta().trim().toLowerCase())) {
                        Cell celda = fila.createCell(indiceColumna);
                        if (variableIge.getTipo().equals("DATE")) {
                            if (Boolean.parseBoolean(variableIge.getEditable())) {
                                celda.setCellStyle(cellStyleUnlockedFecha);
                            } else {
                                celda.setCellStyle(cellStyleLockedFecha);
                            }
                            if (Fecha.fomatoFechaStringToDate(getIdentificacionSeleccionada().get(variableIge.getColumna().trim())) != null) {
                                celda.setCellValue(Fecha.fomatoFechaStringToDate(getIdentificacionSeleccionada().get(variableIge.getColumna().trim())));
                            }
                        } else if (variableIge.getTipo().equals("NUMBER")) {
                            if (Boolean.parseBoolean(variableIge.getEditable())) {
                                celda.setCellStyle(cellStyleUnlocked);
                            } else {
                                celda.setCellStyle(cellStyleLocked);
                            }
                            celda.setCellValue(getIdentificacionSeleccionada().get(variableIge.getColumna().trim()));
                        } else {
                            if (Boolean.parseBoolean(variableIge.getEditable())) {
                                celda.setCellStyle(cellStyleUnlockedTexto);
                            } else {
                                celda.setCellStyle(cellStyleLockedTexto);
                            }
                            celda.setCellValue(getIdentificacionSeleccionada().get(variableIge.getColumna().trim()));
                        }
                        break;
                    }
                }
                indiceColumna++;
            }
            indiceFila++;
            indiceRegistro++;
        }

        return libro;
    }

    /**
     * Método que permite ingresar los datos de relación la plantilla xls de las
     * unidades legales del grupo empresa.
     *
     * @param libro
     * @return libro
     */
    private Workbook ingresarDatosRelacion(Workbook libro) {
        //La hoja 2 es la hoja de los datos de relación
        List<VariableIge> columnas = geteJBServicioVariableIge().buscarVariableByGrupo("RELACION");
        Sheet hoja = libro.getSheet("Relación");//getSheetAt(2);
        hoja.protectSheet(passwordShee);

        CellStyle cellStyleLocked = EstiloCeldasXls.estiloBordeCompletoCedaEditable(libro, false);
        CellStyle cellStyleUnlocked = EstiloCeldasXls.estiloBordeCompletoCedaEditable(libro, true);
        CellStyle cellStyleLockedFecha = EstiloCeldasXls.estiloBordeCompletoCedaEditableFecha(libro, false);
        CellStyle cellStyleUnlockedFecha = EstiloCeldasXls.estiloBordeCompletoCedaEditableFecha(libro, true);
        CellStyle cellStyleLockedTexto = EstiloCeldasXls.estiloBordeCompletoCedaEditableTexto(libro, false);
        CellStyle cellStyleUnlockedTexto = EstiloCeldasXls.estiloBordeCompletoCedaEditableTexto(libro, true);

        int indiceRegistro = 1;
        int indiceFila = 2;
        for (BodegaIdentificacion unidad : getListaIdentificacion()) {
            setRelacionSeleccionada(geteJBServicioBodegaRelacion().obtenerMapRelacionGrupoEmpresaById(unidad.getId().getId()));
            Row encabezadoXsl = hoja.getRow(1);
            Iterator cells = encabezadoXsl.cellIterator();

            int indiceColumna = 0;
            Row fila = hoja.createRow(indiceFila);

            Cell celdaIndice = fila.createCell(indiceColumna);
            celdaIndice.setCellStyle(cellStyleLocked);
            celdaIndice.setCellValue(indiceRegistro);

            while (cells.hasNext()) {

                Cell cell = (Cell) cells.next();
                for (VariableIge variableIge : columnas) {
                    if (cell.getStringCellValue().trim().toLowerCase().equals(variableIge.getEtiqueta().trim().toLowerCase())) {
                        Cell celda = fila.createCell(indiceColumna);
                        if (variableIge.getTipo().equals("DATE")) {
                            if (Boolean.parseBoolean(variableIge.getEditable())) {
                                celda.setCellStyle(cellStyleUnlockedFecha);
                            } else {
                                celda.setCellStyle(cellStyleLockedFecha);
                            }
                            if (Fecha.fomatoFechaStringToDate(getRelacionSeleccionada().get(variableIge.getColumna().trim())) != null) {
                                celda.setCellValue(Fecha.fomatoFechaStringToDate(getRelacionSeleccionada().get(variableIge.getColumna().trim())));
                            }
                        } else if (variableIge.getTipo().equals("NUMBER")) {
                            if (Boolean.parseBoolean(variableIge.getEditable())) {
                                celda.setCellStyle(cellStyleUnlocked);
                            } else {
                                celda.setCellStyle(cellStyleLocked);
                            }
                            celda.setCellValue(getRelacionSeleccionada().get(variableIge.getColumna().trim()));
                        } else {
                            if (Boolean.parseBoolean(variableIge.getEditable())) {
                                celda.setCellStyle(cellStyleUnlockedTexto);
                            } else {
                                celda.setCellStyle(cellStyleLockedTexto);
                            }
                            celda.setCellValue(getRelacionSeleccionada().get(variableIge.getColumna().trim()));
                        }
                        break;
                    }
                }
                indiceColumna++;
            }
            indiceFila++;
            indiceRegistro++;
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
        Sheet hoja = libro.getSheet("Historia");//.getSheetAt(3);
        hoja.protectSheet(passwordShee);

        CellStyle cellStyleLocked = EstiloCeldasXls.estiloBordeCompletoCedaEditable(libro, false);
        CellStyle cellStyleUnlocked = EstiloCeldasXls.estiloBordeCompletoCedaEditable(libro, true);
        CellStyle cellStyleLockedFecha = EstiloCeldasXls.estiloBordeCompletoCedaEditableFecha(libro, false);
        CellStyle cellStyleUnlockedFecha = EstiloCeldasXls.estiloBordeCompletoCedaEditableFecha(libro, true);
        CellStyle cellStyleLockedTexto = EstiloCeldasXls.estiloBordeCompletoCedaEditableTexto(libro, false);
        CellStyle cellStyleUnlockedTexto = EstiloCeldasXls.estiloBordeCompletoCedaEditableTexto(libro, true);

        int indiceRegistro = 1;
        int indiceFila = 2;
        for (BodegaIdentificacion unidad : getListaIdentificacion()) {
            setNovedadSeleccionada(geteJBServicioBodegaNovedad().obtenerMapNovedadGrupoEmpresaById(unidad.getId().getId()));
            Row encabezadoXsl = hoja.getRow(1);
            Iterator cells = encabezadoXsl.cellIterator();

            int indiceColumna = 0;
            Row fila = hoja.createRow(indiceFila);

            Cell celdaIndice = fila.createCell(indiceColumna);
            celdaIndice.setCellStyle(cellStyleLocked);
            celdaIndice.setCellValue(indiceRegistro);

            while (cells.hasNext()) {

                Cell cell = (Cell) cells.next();
                for (VariableIge variableIge : columnas) {
                    if (cell.getStringCellValue().trim().toLowerCase().equals(variableIge.getEtiqueta().trim().toLowerCase())) {
                        Cell celda = fila.createCell(indiceColumna);
                        if (variableIge.getTipo().equals("DATE")) {
                            if (Boolean.parseBoolean(variableIge.getEditable())) {
                                celda.setCellStyle(cellStyleUnlockedFecha);
                            } else {
                                celda.setCellStyle(cellStyleLockedFecha);
                            }
                            if (Fecha.fomatoFechaStringToDate(getNovedadSeleccionada().get(variableIge.getColumna().trim())) != null) {
                                celda.setCellValue(Fecha.fomatoFechaStringToDate(getNovedadSeleccionada().get(variableIge.getColumna().trim())));
                            }
                        } else if (variableIge.getTipo().equals("NUMBER")) {
                            if (Boolean.parseBoolean(variableIge.getEditable())) {
                                celda.setCellStyle(cellStyleUnlocked);
                            } else {
                                celda.setCellStyle(cellStyleLocked);
                            }
                            celda.setCellValue(getNovedadSeleccionada().get(variableIge.getColumna().trim()));
                        } else {
                            if (Boolean.parseBoolean(variableIge.getEditable())) {
                                celda.setCellStyle(cellStyleUnlockedTexto);
                            } else {
                                celda.setCellStyle(cellStyleLockedTexto);
                            }
                            celda.setCellValue(getNovedadSeleccionada().get(variableIge.getColumna().trim()));
                        }
                        break;
                    }
                }
                indiceColumna++;
            }
            indiceFila++;
            indiceRegistro++;
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
        Sheet hoja = libro.getSheet("Tamaño");//.getSheetAt(4);
        hoja.protectSheet(passwordShee);

        CellStyle cellStyleLocked = EstiloCeldasXls.estiloBordeCompletoCedaEditable(libro, false);
        CellStyle cellStyleUnlocked = EstiloCeldasXls.estiloBordeCompletoCedaEditable(libro, true);
        CellStyle cellStyleLockedFecha = EstiloCeldasXls.estiloBordeCompletoCedaEditableFecha(libro, false);
        CellStyle cellStyleUnlockedFecha = EstiloCeldasXls.estiloBordeCompletoCedaEditableFecha(libro, true);
        CellStyle cellStyleLockedTexto = EstiloCeldasXls.estiloBordeCompletoCedaEditableTexto(libro, false);
        CellStyle cellStyleUnlockedTexto = EstiloCeldasXls.estiloBordeCompletoCedaEditableTexto(libro, true);

        int indiceRegistro = 1;
        int indiceFila = 2;
        for (BodegaIdentificacion unidad : getListaIdentificacion()) {
            setTamanoSeleccionado(geteJBServicioBodegaTamano().obtenerMapTamanoGrupoEmpresaById(unidad.getId().getId()));
            Row encabezadoXsl = hoja.getRow(1);
            Iterator cells = encabezadoXsl.cellIterator();

            int indiceColumna = 0;
            Row fila = hoja.createRow(indiceFila);

            Cell celdaIndice = fila.createCell(indiceColumna);
            celdaIndice.setCellStyle(cellStyleLocked);
            celdaIndice.setCellValue(indiceRegistro);

            while (cells.hasNext()) {

                Cell cell = (Cell) cells.next();
                for (VariableIge variableIge : columnas) {
                    if (cell.getStringCellValue().trim().toLowerCase().equals(variableIge.getEtiqueta().trim().toLowerCase())) {
                        Cell celda = fila.createCell(indiceColumna);
                        if (variableIge.getTipo().equals("DATE")) {
                            if (Boolean.parseBoolean(variableIge.getEditable())) {
                                celda.setCellStyle(cellStyleUnlockedFecha);
                            } else {
                                celda.setCellStyle(cellStyleLockedFecha);
                            }
                            celda.setCellValue(Fecha.fomatoFechaStringToDate(getTamanoSeleccionado().get(variableIge.getColumna().trim())));
                        } else if (variableIge.getTipo().equals("NUMBER")) {
                            if (Boolean.parseBoolean(variableIge.getEditable())) {
                                celda.setCellStyle(cellStyleUnlocked);
                            } else {
                                celda.setCellStyle(cellStyleLocked);
                            }
                            celda.setCellValue(getTamanoSeleccionado().get(variableIge.getColumna().trim()));
                        } else {
                            if (Boolean.parseBoolean(variableIge.getEditable())) {
                                celda.setCellStyle(cellStyleUnlockedTexto);
                            } else {
                                celda.setCellStyle(cellStyleLockedTexto);
                            }
                            celda.setCellValue(getTamanoSeleccionado().get(variableIge.getColumna().trim()));
                        }
                        break;
                    }
                }
                indiceColumna++;
            }
            indiceFila++;
            indiceRegistro++;
        }
        return libro;
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

    public List<BodegaIdentificacion> getListaIdentificacion() {
        return listaIdentificacion;
    }

    public void setListaIdentificacion(List<BodegaIdentificacion> listaIdentificacion) {
        this.listaIdentificacion = listaIdentificacion;
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

    public ArchivoXlsFacadeLocal geteJBServicioArchivoXls() {
        return eJBServicioArchivoXls;
    }

    public void seteJBServicioArchivoXls(ArchivoXlsFacadeLocal eJBServicioArchivoXls) {
        this.eJBServicioArchivoXls = eJBServicioArchivoXls;
    }

}
