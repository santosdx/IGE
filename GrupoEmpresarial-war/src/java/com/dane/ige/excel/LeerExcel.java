package com.dane.ige.excel;

import com.dane.ige.dto.InformeRegistroInconsistenteXls;
import com.dane.ige.modelo.entidad.BodegaIdentificacion;
import com.dane.ige.modelo.entidad.BodegaNovedad;
import com.dane.ige.modelo.entidad.BodegaRelacion;
import com.dane.ige.modelo.entidad.BodegaTamano;
import com.dane.ige.modelo.entidad.VariableIge;
import com.dane.ige.modelo.local.administracion.BodegaIdentificacionFacadeLocal;
import com.dane.ige.modelo.local.administracion.BodegaNovedadFacadeLocal;
import com.dane.ige.modelo.local.administracion.BodegaRelacionFacadeLocal;
import com.dane.ige.modelo.local.administracion.BodegaTamanoFacadeLocal;
import com.dane.ige.modelo.local.administracion.VariableIgeFacadeLocal;
import com.dane.ige.utilidad.Fecha;
import com.dane.ige.utilidad.Mensaje;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author SRojasM
 */
@ManagedBean(name = "MbLeerExcel")
@ViewScoped
public class LeerExcel implements Serializable {

    final static Logger LOGGER = Logger.getLogger(LeerExcel.class);
    @EJB
    private VariableIgeFacadeLocal eJBServicioVariableIge;
    @EJB
    private BodegaIdentificacionFacadeLocal eJBServicioBodegaIdentificacion;
    @EJB
    private BodegaNovedadFacadeLocal eJBServicioBodegaNovedad;
    @EJB
    private BodegaTamanoFacadeLocal eJBServicioBodegaTamano;
    @EJB
    private BodegaRelacionFacadeLocal eJBServicioBodegaRelacion;

    private UploadedFile file;
    private int filaDatosInicial = 2;
    private int colulmnaDatosInicial = 1;
    private boolean sePuedeInsertarDatos = false;
    private List<InformeRegistroInconsistenteXls> listaInconsistencias;
    private InformeRegistroInconsistenteXls inconsistenciaSeleccionada;

    private List<BodegaIdentificacion> jsonIdentificacion = null;
    private List<BodegaRelacion> jsonRelacion = null;
    private List<BodegaNovedad> jsonHistoria = null;
    private List<BodegaTamano> jsonTamano = null;

    private String formularioActivo;

    public LeerExcel() {
    }

    /**
     * Método que permite capturar el evento FileUploadEvent del componente de
     * archivos, y permite obtener el archivo y a su ves subirlo en un
     * directorio de ser necesario.
     *
     * @param event
     * @throws java.io.IOException
     */
    public void subirArchivoGrupoEmpresarial(FileUploadEvent event) throws IOException {
        setFile(event.getFile());
        setFormularioActivo(event.getComponent().getAttributes().get("formulario")+"");
        Workbook libro = new HSSFWorkbook((FileInputStream) event.getFile().getInputstream());

        setSePuedeInsertarDatos(true);
        setListaInconsistencias(new ArrayList<InformeRegistroInconsistenteXls>());

        if (libro.getSheet("Identificación") != null) {
            String resultadoIdentificacion = procesarDatosXls(leerArchivoXlsGrupoEmpresarial((FileInputStream) event.getFile().getInputstream(), "Identificación"), 2, 1, "IDENTIFICACION", "Identificación").toString();
            //Gson gsonIdentificacion = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
            Gson gsonIdentificacion = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
            Type typeIdentificacion = new TypeToken<List<BodegaIdentificacion>>() {
            }.getType();
            jsonIdentificacion = gsonIdentificacion.fromJson(resultadoIdentificacion, typeIdentificacion);
            LOGGER.info("Iden count:" + jsonIdentificacion.size());
        }

        if (libro.getSheet("Relación") != null) {
            String resultadoRelacion = procesarDatosXls(leerArchivoXlsGrupoEmpresarial((FileInputStream) event.getFile().getInputstream(), "Relación"), 2, 1, "RELACION", "Relación").toString();
            //Gson gsonRelacion = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
            Gson gsonRelacion = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
            Type typeRelacion = new TypeToken<List<BodegaRelacion>>() {
            }.getType();
            jsonRelacion = gsonRelacion.fromJson(resultadoRelacion, typeRelacion);
            LOGGER.info("Rela count:" + jsonRelacion.size());
        }

        if (libro.getSheet("Historia") != null) {
            String resultadoHistoria = procesarDatosXls(leerArchivoXlsGrupoEmpresarial((FileInputStream) event.getFile().getInputstream(), "Historia"), 2, 1, "NOVEDAD", "Historia").toString();
            //Gson gsonHistoria = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
            Gson gsonHistoria = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
            Type typeHistoria = new TypeToken<List<BodegaNovedad>>() {
            }.getType();
            jsonHistoria = gsonHistoria.fromJson(resultadoHistoria, typeHistoria);
            LOGGER.info("Histo count:" + jsonHistoria.size());
        }

        if (libro.getSheet("Tamaño") != null) {
            String resultadoTamano = procesarDatosXls(leerArchivoXlsGrupoEmpresarial((FileInputStream) event.getFile().getInputstream(), "Tamaño"), 2, 1, "TAMAÑO", "Tamaño").toString();
            //Gson gsonTamano = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
            Gson gsonTamano = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
            Type typeTamano = new TypeToken<List<BodegaTamano>>() {
            }.getType();
            jsonTamano = gsonTamano.fromJson(resultadoTamano, typeTamano);
            LOGGER.info("Tama count:" + jsonTamano.size());
        }

        //Inicia Insercciones
        if (isSePuedeInsertarDatos()) {
            Mensaje.agregarMensajeGrowlInfo("Exitosa", event.getFile().getFileName() + " está cargado.");
        } else {
            Mensaje.agregarMensajeGrowlWarn("Inconsistencia", event.getFile().getFileName() + " presenta datos incompletos.");
        }
    }

    public void insertarRegistrosBodega() {

        Workbook libro;
        try {
            libro = new HSSFWorkbook((FileInputStream) getFile().getInputstream());
            if (libro.getSheet("Identificación") != null) {
                //Creamos cada uno de los registros de identificación en la Tabla.
                for (BodegaIdentificacion bodegaIdentificacion : jsonIdentificacion) {
                    geteJBServicioBodegaIdentificacion().create(bodegaIdentificacion);
                }
            }

            if (libro.getSheet("Relación") != null) {
                //Creamos cada uno de los registros de relación en la Tabla.
                for (BodegaRelacion bodegaRelacion : jsonRelacion) {
                    geteJBServicioBodegaRelacion().create(bodegaRelacion);
                }
            }

            if (libro.getSheet("Historia") != null) {
                //Creamos cada uno de los reigistros de historia en la Tabla.
                for (BodegaNovedad bodegaNovedad : jsonHistoria) {
                    geteJBServicioBodegaNovedad().create(bodegaNovedad);
                }
            }

            if (libro.getSheet("Tamaño") != null) {
                //Creamos cada uno de los registros de tamaño en la Tabla.
                for (BodegaTamano bodegaTamano : jsonTamano) {
                    geteJBServicioBodegaTamano().create(bodegaTamano);
                }
            }

            Mensaje.agregarMensajeGrowlInfo("Exitosa", "Información cargada con exito.");
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(LeerExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método que permite leer el archivo xls de la unidad, Grupo Empresa y
     * genera un List de resultado de datos.
     *
     * @param archivo
     * @param nombreHoja
     * @return List
     */
    public List leerArchivoXlsGrupoEmpresarial(FileInputStream archivo, String nombreHoja) {

        List sheetData = new ArrayList();
        FileInputStream fileInpSt = null;
        try {

            fileInpSt = archivo;
            Workbook libro = new HSSFWorkbook(fileInpSt);
            Sheet hoja = libro.getSheet(nombreHoja);

            Iterator rows = hoja.rowIterator();
            rows.hasNext();
            while (rows.hasNext()) {
                Row row = (Row) rows.next();
                Iterator cells = row.cellIterator();
                List data = new ArrayList();
                while (cells.hasNext()) {
                    Cell cell = (Cell) cells.next();
                    data.add(cell);
                }
                sheetData.add(data);
            }
        } catch (IOException e) {
            java.util.logging.Logger.getLogger(LeerExcel.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (fileInpSt != null) {
                try {
                    fileInpSt.close();
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(LeerExcel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return sheetData;
    }

    /**
     * Método que permite leer una hoja de un archivo xls y genera una List de
     * resultado de datos.
     *
     * @param sheet
     * @return List
     */
    public List obtenerListaDatosHojaXls(Sheet sheet) {
        List sheetData = new ArrayList();
        Iterator rows = sheet.rowIterator();
        while (rows.hasNext()) {
            Row row = (Row) rows.next();
            Iterator cells = row.cellIterator();
            List data = new ArrayList();
            while (cells.hasNext()) {
                Cell cell = (Cell) cells.next();
                data.add(cell);
            }
            sheetData.add(data);
        }
        return sheetData;
    }

    /**
     * Método que permite mostrar por consola los resultados fila por fila de
     * los datos del archivo xls. Recibe como parametro el listado de resultados
     * y el indice de fila y columna por el cual iniciar la visualización.
     *
     * @param fila
     * @param columna
     * @param sheetData
     */
    public void visualizarDatosXls(List sheetData, int fila, int columna) {
        StringBuffer resultado;
        int filaTemp = 0;
        int columnaTemp = 0;

        for (Object sheetRow : sheetData) {
            if (filaTemp >= fila) {
                resultado = new StringBuffer();
                List list = (List) sheetRow;
                for (int j = 0; j < list.size(); j++) {
                    if (columnaTemp >= columna) {
                        Cell cell = (Cell) list.get(j);
                        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            resultado.append(cell.getNumericCellValue());
                        } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                            resultado.append(cell.getRichStringCellValue());
                        } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
                            resultado.append(cell.getBooleanCellValue());
                        }
                        if (j < list.size() - 1) {
                            resultado.append("|");
                        }
                    }
                    columnaTemp++;
                }
                LOGGER.info(resultado);
            }
            filaTemp++;
        }
    }

    private StringBuffer procesarDatosXls(List sheetData, int fila, int columna, String grupoVariable, String nombreHoja) {

        StringBuffer resultado;
        StringBuffer resultadoFila;

        List<VariableIge> columnas = geteJBServicioVariableIge().buscarVariableByGrupo(grupoVariable);
        List encabezadoXsl = (List) sheetData.get(1);

        int filaTemp = 0;
        int columnaTemp = 0;

        resultado = new StringBuffer();
        resultado.append("[");

        for (Object sheetRow : sheetData) {
            if (filaTemp >= fila) {

                List<String> listaVariablesSinDatos = new ArrayList<String>();
                List list = (List) sheetRow;
                resultadoFila = new StringBuffer();
                resultadoFila.append("{");

                Cell cellIndice = (Cell) list.get(0);
                String textoLlave = "";
                Cell cellId = (Cell) list.get(1);
                Cell cellFecha = (Cell) list.get(2);
                String date = Fecha.formatFechaDateToString(new Date());
                textoLlave = "\"id\":{\"id\":\"" + cellId.getRichStringCellValue() + "\",\"fecha\":\"" + date + "\"},";
                resultadoFila.append(textoLlave);

                for (int j = columna + 2; j < encabezadoXsl.size(); j++) {
                    Cell cellEncabezado = (Cell) encabezadoXsl.get(j);
                    for (VariableIge variable : columnas) {
                        String valorCelda = null;

                        if (cellEncabezado.getStringCellValue().equals(variable.getEtiqueta())) {
                            //LOGGER.info(variable.getColumna());
                            Cell cellRegistro = (Cell) list.get(j);

                            if (variable.getTipo().equals("NUMBER")) {
                                if (cellRegistro.getCellType() == 0) {
                                    if ("".equals(cellRegistro.getNumericCellValue() + "")) {
                                        resultadoFila.append("\"" + variable.getNombreAtributoClase() + "\":" + null + "");
                                    } else {
                                        valorCelda = cellRegistro.getNumericCellValue() + "";
                                        resultadoFila.append("\"" + variable.getNombreAtributoClase() + "\":\"" + cellRegistro.getNumericCellValue() + "\"");
                                    }
                                } else {
                                    if ("".equals(cellRegistro.getStringCellValue() + "")) {
                                        resultadoFila.append("\"" + variable.getNombreAtributoClase() + "\":" + null + "");
                                    } else {
                                        valorCelda = cellRegistro.getStringCellValue() + "";
                                        resultadoFila.append("\"" + variable.getNombreAtributoClase() + "\":\"" + cellRegistro.getStringCellValue() + "\"");
                                    }
                                }
                            } else if (variable.getTipo().equals("DATE")) {
                                String fecha = null;
                                if (cellRegistro.getCellType() == 0) {
                                    fecha = Fecha.formatFechaDateToString(cellRegistro.getDateCellValue());
                                }
                                if (fecha != null) {
                                    valorCelda = fecha;
                                    resultadoFila.append("\"" + variable.getNombreAtributoClase() + "\":\"" + fecha + "\"");
                                } else {
                                    resultadoFila.append("\"" + variable.getNombreAtributoClase() + "\":" + null + "");
                                }

                            } else {
                                if (cellRegistro.getCellType() == 0) {
                                    valorCelda = cellRegistro.getNumericCellValue() + "";
                                    resultadoFila.append("\"" + variable.getNombreAtributoClase() + "\":\"" + cellRegistro.getNumericCellValue() + "\"");

                                } else {
                                    valorCelda = cellRegistro.getStringCellValue();
                                    resultadoFila.append("\"" + variable.getNombreAtributoClase() + "\":\"" + cellRegistro.getStringCellValue() + "\"");
                                }
                            }
                            if (esObligatoriaVariableFormulario(variable.getObligatoria())) {
                                if (valorCelda == null || valorCelda.equals("")) {
                                    listaVariablesSinDatos.add(variable.getEtiqueta());
                                }
                            }
                            resultadoFila.append(",");
                        }
                    }
                }

                if (listaVariablesSinDatos.size() > 0) {
                    DecimalFormat nf = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    nf.applyPattern("####");
                    getListaInconsistencias().add(new InformeRegistroInconsistenteXls(Integer.parseInt(nf.format(cellIndice.getNumericCellValue()) + ""), nombreHoja, listaVariablesSinDatos));
                    setSePuedeInsertarDatos(false);
                }

                String temp = resultadoFila.toString();
                temp = temp.substring(0, temp.length() - 1);
                resultadoFila = new StringBuffer(temp);

                if (sheetData.size() == (filaTemp + 1)) {
                    resultadoFila.append("}");
                } else {
                    resultadoFila.append("},");
                }
                resultado.append(resultadoFila);
            }
            filaTemp++;
        }
        LOGGER.info(resultado.append("]"));
        return resultado;
    }

    /**
     * Método que permite identificar si una variable es obligatoria en un
     * formulario determinado.
     *
     * @param obligatoriedad
     * @return
     */
    private boolean esObligatoriaVariableFormulario(String obligatoriedad) {
        boolean resultado = false;
        if (obligatoriedad != null) {
            String[] lista = obligatoriedad.split(",");
            for (String string : lista) {
                if (string.equals(getFormularioActivo())) {
                    resultado = true;
                    break;
                }
            }
        }
        return resultado;
    }

    //Métodos Set y Get de la clase
    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public VariableIgeFacadeLocal geteJBServicioVariableIge() {
        return eJBServicioVariableIge;
    }

    public void seteJBServicioVariableIge(VariableIgeFacadeLocal eJBServicioVariableIge) {
        this.eJBServicioVariableIge = eJBServicioVariableIge;
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

    public boolean isSePuedeInsertarDatos() {
        return sePuedeInsertarDatos;
    }

    public void setSePuedeInsertarDatos(boolean sePuedeInsertarDatos) {
        this.sePuedeInsertarDatos = sePuedeInsertarDatos;
    }

    public List<InformeRegistroInconsistenteXls> getListaInconsistencias() {
        return listaInconsistencias;
    }

    public void setListaInconsistencias(List<InformeRegistroInconsistenteXls> listaInconsistencias) {
        this.listaInconsistencias = listaInconsistencias;
    }

    public InformeRegistroInconsistenteXls getInconsistenciaSeleccionada() {
        return inconsistenciaSeleccionada;
    }

    public void setInconsistenciaSeleccionada(InformeRegistroInconsistenteXls inconsistenciaSeleccionada) {
        this.inconsistenciaSeleccionada = inconsistenciaSeleccionada;
    }

    public String getFormularioActivo() {
        return formularioActivo;
    }

    public void setFormularioActivo(String formularioActivo) {
        this.formularioActivo = formularioActivo;
    }

}
