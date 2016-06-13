package com.dane.ige.excel;

import com.dane.ige.dto.InformeRegistroInconsistenteXls;
import com.dane.ige.componente.lista.ListasDesplegablesFormularios;
import com.dane.ige.modelo.entidad.ArchivoXls;
import com.dane.ige.modelo.entidad.BodegaIdentificacion;
import com.dane.ige.modelo.entidad.BodegaNovedad;
import com.dane.ige.modelo.entidad.BodegaRelacion;
import com.dane.ige.modelo.entidad.BodegaTamano;
import com.dane.ige.modelo.entidad.Departamento;
import com.dane.ige.modelo.entidad.EstadoMatricula;
import com.dane.ige.modelo.entidad.Municipio;
import com.dane.ige.modelo.entidad.Pais;
import com.dane.ige.modelo.entidad.SituacionControl;
import com.dane.ige.modelo.entidad.TipoControl;
import com.dane.ige.modelo.entidad.TipoEmpresa;
import com.dane.ige.modelo.entidad.TipoEstablecimiento;
import com.dane.ige.modelo.entidad.TipoOrganizacion;
import com.dane.ige.modelo.entidad.VariableIge;
import com.dane.ige.modelo.local.administracion.ArchivoXlsFacadeLocal;
import com.dane.ige.modelo.local.administracion.BodegaIdentificacionFacadeLocal;
import com.dane.ige.modelo.local.administracion.BodegaNovedadFacadeLocal;
import com.dane.ige.modelo.local.administracion.BodegaRelacionFacadeLocal;
import com.dane.ige.modelo.local.administracion.BodegaTamanoFacadeLocal;
import com.dane.ige.modelo.local.administracion.VariableIgeFacadeLocal;
import com.dane.ige.negocio.FormularioEstablecimiento;
import com.dane.ige.negocio.FormularioUnidadLegal;
import com.dane.ige.seguridad.Login;
import com.dane.ige.utilidad.Fecha;
import com.dane.ige.utilidad.Mensaje;
import com.dane.ige.utilidad.Numero;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.io.ByteArrayOutputStream;
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
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.primefaces.context.RequestContext;

/**
 * Clase que contiene las funcionalidades de lectura de los archivos Excel (xls)
 * validando la calidad de los datos, los datos obligatorios, la estructura del
 * archivo y realizando el proceso de actualización o inserción de los mismos en
 * las tablas de la base de datos.
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
    @EJB
    private ArchivoXlsFacadeLocal eJBServicioArchivoXls;

    private UploadedFile file;
    private int filaDatosInicial = 2;
    private int colulmnaDatosInicial = 1;
    private boolean sePuedeInsertarDatos = false;
    private boolean archivoValidoPorIdentidad = false;
    private List<InformeRegistroInconsistenteXls> listaInconsistencias;
    private InformeRegistroInconsistenteXls inconsistenciaSeleccionada;

    private List<BodegaIdentificacion> jsonIdentificacion = null;
    private List<BodegaRelacion> jsonRelacion = null;
    private List<BodegaNovedad> jsonHistoria = null;
    private List<BodegaTamano> jsonTamano = null;

    private String formularioActivo;

    private List<Pais> listaPaises;
    private List<Departamento> listaDepartamentos;
    private List<EstadoMatricula> listaMatriculas;
    private List<Municipio> listaMunicipios;
    private List<SituacionControl> listaSituaciones;
    private List<TipoControl> listaControles;
    private List<TipoEmpresa> listaEmpresas;
    private List<TipoEstablecimiento> listaEstablecimeintos;
    private List<TipoOrganizacion> listaOrganizaciones;

    @ManagedProperty("#{MbListaDesplegable}")
    private ListasDesplegablesFormularios servicioListasDesplegablesFormularios;
    @ManagedProperty("#{MbLogin}")
    private Login servicioLogin;

    @ManagedProperty("#{MbFormUnidadLegal}")
    private FormularioUnidadLegal servicioFormularioUnidadLegal;

    @ManagedProperty("#{MbFormEstablecimiento}")
    private FormularioEstablecimiento servicioFormularioEstablecimiento;

    private Long idGrupo = null;
    private String unidad = null;
    private Date fechaEvento = null;
    private String evento = null;
    private Integer idUsuario = null;
    private String codigoArchivo = null;
    private String mensajeErrorValidacionArchivo = "";

    public LeerExcel() {
    }

    @PostConstruct
    public void init() {
        listaPaises = servicioListasDesplegablesFormularios.geteJBServicioPais().findAllInOrderByNameAsc();
        listaDepartamentos = servicioListasDesplegablesFormularios.geteJBServicioDepartamento().findAllInOrderByNameAsc();
        listaMunicipios = servicioListasDesplegablesFormularios.geteJBServicioMunicipio().findAllInOrderByNameAsc();
        listaMatriculas = servicioListasDesplegablesFormularios.geteJBServicioMatricula().findAllInOrderByNameAsc();
        listaSituaciones = servicioListasDesplegablesFormularios.geteJBServicioSituacion().findAllInOrderByNameAsc();
        listaControles = servicioListasDesplegablesFormularios.geteJBServicioControl().findAllInOrderByNameAsc();
        listaEmpresas = servicioListasDesplegablesFormularios.geteJBServicioEmpresa().findAllInOrderByNameAsc();
        listaEstablecimeintos = servicioListasDesplegablesFormularios.geteJBServicioEstablecimiento().findAllInOrderByNameAsc();
        listaOrganizaciones = servicioListasDesplegablesFormularios.geteJBServicioOrganizacion().findAllInOrderByNameAsc();
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
        setFormularioActivo(event.getComponent().getAttributes().get("formulario") + "");
        Workbook libro = new HSSFWorkbook((FileInputStream) event.getFile().getInputstream());

        if (validarIdentidadArchivo(libro)) {
            setSePuedeInsertarDatos(true);
            setListaInconsistencias(new ArrayList<InformeRegistroInconsistenteXls>());

            //Esta es la fecha de la llave compuesta con la cual se inserta todos los retistros para las 4 tablas
            String fechaIdLlave = Fecha.formatFechaDateToString(new Date());
            
            if (libro.getSheet("Identificación") != null) {
                String resultadoIdentificacion = procesarDatosXls(leerArchivoXls((FileInputStream) event.getFile().getInputstream(), "Identificación"), 2, 1, "IDENTIFICACION", "Identificación",fechaIdLlave).toString();
                //Gson gsonIdentificacion = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                Gson gsonIdentificacion = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create();
                Type typeIdentificacion = new TypeToken<List<BodegaIdentificacion>>() {
                }.getType();
                try {
                    jsonIdentificacion = gsonIdentificacion.fromJson(resultadoIdentificacion, typeIdentificacion);
                    //LOGGER.info("Iden count:" + jsonIdentificacion.size());
                } catch (JsonSyntaxException jSyEx) {
                    LOGGER.error(jSyEx.getMessage());
                }
            }

            if (libro.getSheet("Relación") != null) {
                String resultadoRelacion = procesarDatosXls(leerArchivoXls((FileInputStream) event.getFile().getInputstream(), "Relación"), 2, 1, "RELACION", "Relación",fechaIdLlave).toString();
                //Gson gsonRelacion = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                Gson gsonRelacion = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create();
                Type typeRelacion = new TypeToken<List<BodegaRelacion>>() {
                }.getType();
                try {
                    jsonRelacion = gsonRelacion.fromJson(resultadoRelacion, typeRelacion);
                    //LOGGER.info("Rela count:" + jsonRelacion.size());
                } catch (JsonSyntaxException jSyEx) {
                    LOGGER.error(jSyEx.getMessage());
                }
            }

            if (libro.getSheet("Historia") != null) {
                String resultadoHistoria = procesarDatosXls(leerArchivoXls((FileInputStream) event.getFile().getInputstream(), "Historia"), 2, 1, "NOVEDAD", "Historia",fechaIdLlave).toString();
                //Gson gsonHistoria = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                Gson gsonHistoria = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create();
                Type typeHistoria = new TypeToken<List<BodegaNovedad>>() {
                }.getType();
                try {
                    jsonHistoria = gsonHistoria.fromJson(resultadoHistoria, typeHistoria);
                    //LOGGER.info("Histo count:" + jsonHistoria.size());
                } catch (JsonSyntaxException jSyEx) {
                    LOGGER.error(jSyEx.getMessage());
                }
            }

            if (libro.getSheet("Tamaño") != null) {
                String resultadoTamano = procesarDatosXls(leerArchivoXls((FileInputStream) event.getFile().getInputstream(), "Tamaño"), 2, 1, "TAMAÑO", "Tamaño",fechaIdLlave).toString();
                //Gson gsonTamano = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                Gson gsonTamano = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create();
                Type typeTamano = new TypeToken<List<BodegaTamano>>() {
                }.getType();
                try {
                    jsonTamano = gsonTamano.fromJson(resultadoTamano, typeTamano);
                    //LOGGER.info("Tama count:" + jsonTamano.size());
                } catch (JsonSyntaxException jSyEx) {
                    LOGGER.error(jSyEx.getMessage());
                }
            }

            //Inicia Insercciones
            if (isSePuedeInsertarDatos()) {

            } else {
                setMensajeErrorValidacionArchivo(event.getFile().getFileName() + " presenta datos incompletos.");
                Mensaje.agregarMensajeGrowlWarn("Inconsistencia", getMensajeErrorValidacionArchivo());
            }
        }
    }

    /**
     * Método que realiza las validaciónes sobre la valides del archivo
     * descargado.
     *
     * @param libro
     * @return
     */
    private boolean validarIdentidadArchivo(Workbook libro) {

        boolean resultado = false;
        idGrupo = null;
        unidad = null;
        fechaEvento = null;
        evento = null;
        idUsuario = null;
        codigoArchivo = null;

        //visualizarDatosXls(obtenerListaDatosHojaXls(libro.getSheet("ID-ARCHIVO")), 1, 0);
        Sheet hoja = libro.getSheet("ID-ARCHIVO");
        setArchivoValidoPorIdentidad(false);

        if (hoja != null) {

            Row fila1 = hoja.getRow(1);
            Cell ID_GRUPO = fila1.getCell(1);
            idGrupo = Long.parseLong(ID_GRUPO.getStringCellValue());

            Row fila2 = hoja.getRow(2);
            Cell UNIDAD = fila2.getCell(1);
            unidad = UNIDAD.getStringCellValue();

            Row fila3 = hoja.getRow(3);
            Cell FECHA_EVENTO = fila3.getCell(1);
            fechaEvento = FECHA_EVENTO.getDateCellValue();

            Row fila4 = hoja.getRow(4);
            Cell EVENTO = fila4.getCell(1);
            evento = EVENTO.getStringCellValue();

            Row fila5 = hoja.getRow(5);
            Cell ID_USUARIO = fila5.getCell(1);
            idUsuario = Integer.parseInt(ID_USUARIO.getStringCellValue());

            Row fila6 = hoja.getRow(6);
            Cell CODIGO_ARCHIVO = fila6.getCell(1);
            codigoArchivo = CODIGO_ARCHIVO.getStringCellValue();

            LOGGER.info(idGrupo);
            LOGGER.info(unidad);
            LOGGER.info(fechaEvento);
            LOGGER.info(evento);
            LOGGER.info(idUsuario);
            LOGGER.info(codigoArchivo);

            if (getServicioLogin().getUsuarioLogueado().getIdIdentificacion().compareTo(idGrupo) == 0) {
                if (getServicioLogin().getUsuarioLogueado().getId().compareTo(idUsuario) == 0) {
                    if (unidad.equals(getFormularioActivo())) {

                        ArchivoXls archivoDescargado = geteJBServicioArchivoXls().findByCodigoArchivo(codigoArchivo, "DESCARGADO");

                        if (archivoDescargado != null) {
                            ArchivoXls archivoCargado = geteJBServicioArchivoXls().findByCodigoArchivo(codigoArchivo, "CARGADO");
                            //if (archivoCargado == null) {
                            resultado = true;
                            setArchivoValidoPorIdentidad(true);
                            /*} else {
                             setMensajeErrorValidacionArchivo("Este archivo ya fue cargado el dia:" + archivoCargado.getFechaEvento());
                             Mensaje.agregarMensajeGrowlError("Atención!", getMensajeErrorValidacionArchivo());
                             }*/
                        } else {
                            //setMensajeErrorValidacionArchivo("No se encontro el archivo descargado, por codigo:" + codigoArchivo);
                            setMensajeErrorValidacionArchivo("El archivo que intenta subir, no corresponde al archivo descargado.");
                            Mensaje.agregarMensajeGrowlError("Error!", getMensajeErrorValidacionArchivo());
                        }
                    } else {
                        if (getFormularioActivo().equals("GRUPO")) {
                            setMensajeErrorValidacionArchivo("En esta sección debe cargar un archivo de Grupo Empresarial.");
                            Mensaje.agregarMensajeGrowlError("Atención!", getMensajeErrorValidacionArchivo());
                        } else if (getFormularioActivo().equals("UNIDAD_LEGAL")) {
                            setMensajeErrorValidacionArchivo("En esta sección debe cargar un archivo de Unidades Legales.");
                            Mensaje.agregarMensajeGrowlError("Atención!", getMensajeErrorValidacionArchivo());
                        } else if (getFormularioActivo().equals("ESTABLECIMIENTO")) {
                            setMensajeErrorValidacionArchivo("En esta sección debe cargar un archivo de Establecimientos.");
                            Mensaje.agregarMensajeGrowlError("Atención!", getMensajeErrorValidacionArchivo());
                        }
                    }
                } else {
                    setMensajeErrorValidacionArchivo("Solo el usuario que genero el archivo puede cargarlo.");
                    Mensaje.agregarMensajeGrowlError("Atención!", getMensajeErrorValidacionArchivo());
                }
            } else {
                setMensajeErrorValidacionArchivo("El archivo que intenta cargar, no contiene información sobre el grupo empresarial asignado al usuario.");
                Mensaje.agregarMensajeGrowlError("Atención!", getMensajeErrorValidacionArchivo());
            }
        } else {
            setMensajeErrorValidacionArchivo("Archivo incorrecto, debe cargar el archivo con el formato descargado desde la aplicación.");
            Mensaje.agregarMensajeGrowlError("Atención!", getMensajeErrorValidacionArchivo());
        }
        return resultado;
    }

    /**
     * Método que permite insertar los datos del archivo Xls en la bodega de
     * datos.
     */
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

            //Escribir los datos de cargue el la hojda de identificacion del archivo
            //La hoja 1 es la hoja de los datos de identificacion del archivo
            //Sheet hoja = (new HSSFWorkbook((FileInputStream) getFile().getInputstream())).getSheet("ID-ARCHIVO");
            Sheet hoja = libro.getSheet("ID-ARCHIVO");

            hoja.protectSheet("123");

            Row fila1 = hoja.getRow(1);
            Cell ID_GRUPO = fila1.getCell(2);
            ID_GRUPO.setCellValue(idGrupo + "");

            Row fila2 = hoja.getRow(2);
            Cell UNIDAD = fila2.getCell(2);
            UNIDAD.setCellValue(unidad);

            Row fila3 = hoja.getRow(3);
            Cell FECHA_EVENTO = fila3.getCell(2);
            FECHA_EVENTO.setCellValue(new Date());

            Row fila4 = hoja.getRow(4);
            Cell EVENTO = fila4.getCell(2);
            EVENTO.setCellValue("CARGADO");

            Row fila5 = hoja.getRow(5);
            Cell ID_USUARIO = fila5.getCell(2);
            ID_USUARIO.setCellValue(getServicioLogin().getUsuarioLogueado().getId() + "");

            Row fila6 = hoja.getRow(6);
            Cell CODIGO_ARCHIVO = fila6.getCell(2);
            CODIGO_ARCHIVO.setCellValue(codigoArchivo);

            //Creamos el registro de base de datos con la información del archivo creado, incluye el archivo.
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try {
                libro.write(bos);
            } finally {
                bos.close();
            }
            byte[] archivo = bos.toByteArray();
            ArchivoXls archivoXls = new ArchivoXls(idGrupo, unidad, new Date(), "CARGADO", idUsuario, codigoArchivo, archivo);
            geteJBServicioArchivoXls().create(archivoXls);

            Mensaje.agregarMensajeGrowlInfo("Exito", "Información cargada correctamente.");

            if (unidad.equals("UNIDAD_LEGAL")) {
                servicioFormularioUnidadLegal.init();
                RequestContext.getCurrentInstance().update("frmInfUnidadLegal:dtUnidadLegal");
            } else if (unidad.equals("ESTABLECIMIENTO")) {
                servicioFormularioEstablecimiento.refrescarListaEstablecimientos();
                RequestContext.getCurrentInstance().update("frmInfEstablecimiento:dtEstablecimiento");
            }
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(LeerExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método que permite leer el archivo xls de la unidad, y genera un List de
     * resultado de datos.
     *
     * @param archivo
     * @param nombreHoja
     * @return List
     */
    public List leerArchivoXls(FileInputStream archivo, String nombreHoja) {

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
                //LOGGER.info(resultado);
            }
            filaTemp++;
        }
    }

    /**
     * Método que procesa las hojas de trabajo del archivo Xls, obteniendo los
     * resultados de las filas y las columnas y retornando un json.
     *
     * @param sheetData
     * @param fila
     * @param columna
     * @param grupoVariable
     * @param nombreHoja
     * @return
     */
    private StringBuffer procesarDatosXls(List sheetData, int fila, int columna, String grupoVariable, String nombreHoja, String fechaIdLlave) {

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

                List<InformeRegistroInconsistenteXls.Inconsistencia> listaInconsistenciaVariables = new ArrayList<InformeRegistroInconsistenteXls.Inconsistencia>();
                List listaDatosFila = (List) sheetRow;
                resultadoFila = new StringBuffer();
                resultadoFila.append("{");

                Cell cellIndice = (Cell) listaDatosFila.get(0);
                String textoLlave = "";
                Cell cellId = (Cell) listaDatosFila.get(1);
                Cell cellFecha = (Cell) listaDatosFila.get(2);
                textoLlave = "\"id\":{\"id\":\"" + cellId.getRichStringCellValue() + "\",\"fecha\":\"" + fechaIdLlave + "\"},\"origenActualizacion\":\"Archivo XLS\",";
                resultadoFila.append(textoLlave);

                for (int j = columna + 2; j < encabezadoXsl.size(); j++) {
                    Cell cellEncabezado = (Cell) encabezadoXsl.get(j);
                    for (VariableIge variable : columnas) {
                        String valorCelda = null;

                        if (cellEncabezado.getStringCellValue().equals(variable.getEtiqueta())) {
                            //LOGGER.info(variable.getColumna());
                            Cell cellRegistro = (Cell) listaDatosFila.get(j);

                            if (variable.getTipo().equals("NUMBER")) {
                                if (cellRegistro.getCellType() == 0) {
                                    if ("".equals(cellRegistro.getNumericCellValue() + "")) {
                                        resultadoFila.append("\"" + variable.getNombreAtributoClase() + "\":" + null + "");
                                    } else {
                                        valorCelda = cellRegistro.getNumericCellValue() + "";
                                        resultadoFila.append("\"" + variable.getNombreAtributoClase() + "\":\"" + Numero.formatoNumeroEntero(valorCelda) + "\"");
                                    }
                                } else {
                                    if ("".equals(cellRegistro.getStringCellValue() + "")) {
                                        resultadoFila.append("\"" + variable.getNombreAtributoClase() + "\":" + null + "");
                                    } else {
                                        valorCelda = cellRegistro.getStringCellValue() + "";
                                        resultadoFila.append("\"" + variable.getNombreAtributoClase() + "\":\"" + Numero.formatoNumeroEntero(valorCelda) + "\"");
                                    }
                                }
                            } else if (variable.getTipo().equals("DATE")) {
                                String fecha = null;
                                if (cellRegistro.getCellType() == 0) {
                                    fecha = Fecha.formatFechaDateToString(cellRegistro.getDateCellValue());
                                    valorCelda = cellRegistro.getDateCellValue() + "";
                                } else {
                                    fecha = Fecha.fomatoFechaStringToString(cellRegistro.getStringCellValue());
                                    valorCelda = cellRegistro.getStringCellValue();
                                }
                                if (fecha != null) {
                                    //valorCelda = fecha;
                                    resultadoFila.append("\"" + variable.getNombreAtributoClase() + "\":\"" + fecha + "\"");
                                } else {
                                    resultadoFila.append("\"" + variable.getNombreAtributoClase() + "\":" + null + "");
                                }
                            } else {
                                if (cellRegistro.getCellType() == 0) {
                                    valorCelda = cellRegistro.getNumericCellValue() + "";
                                    resultadoFila.append("\"" + variable.getNombreAtributoClase() + "\":\"" + Numero.formatoNumeroEntero(valorCelda) + "\"");
                                } else {
                                    valorCelda = cellRegistro.getStringCellValue();

                                    if (!StringUtils.isBlank(variable.getTablaReferencial())) {
                                        if (variable.getTablaReferencial().equals("IGE_DEPARTAMENTO")) {
                                        //Remplazamos el caracter _ por espacio, esto por que en la plantilla XLS la lista de departamentos
                                            //pesenta este caracter por funcionalidad
                                            valorCelda = valorCelda.replace("_", " ");
                                        }
                                    }
                                    resultadoFila.append("\"" + variable.getNombreAtributoClase() + "\":\"" + valorCelda + "\"");
                                }
                            }

                            // VALIDACIONES EN LAS VARIABLES
                            if (esObligatoriaVariableFormulario(variable.getObligatoria())) {
                                //Validar si la variable es obligatoria en el formulario.
                                if (valorCelda == null || valorCelda.equals("")) {

                                    String columnaObligatoriedad = "-";
                                    String valorObligatoriedad = "-";
                                    if (variable.getValidacionObligatoriedad() != null && variable.getValidacionObligatoriedad().length() > 0) {
                                        columnaObligatoriedad = variable.getValidacionObligatoriedad().split(":")[0];
                                        valorObligatoriedad = variable.getValidacionObligatoriedad().split(":")[1];
                                    }

                                    if (columnaObligatoriedad.equals("-")) {
                                        InformeRegistroInconsistenteXls.Inconsistencia inconsistencia = new InformeRegistroInconsistenteXls.Inconsistencia(listaInconsistenciaVariables.size(), variable.getEtiqueta(), "Sin datos, valor requerido.");
                                        listaInconsistenciaVariables.add(inconsistencia);
                                    } else {
                                        String valorBusqueda = obtenerValorColumaDeFila(columnaObligatoriedad, filaTemp);
                                        ////LOGGER.info(.out.println("(" + filaTemp + ")[" + variable.getColumna() + "]:[" + valorBusqueda + "] -> " + columnaObligatoriedad + ":" + valorObligatoriedad);
                                        if (valorBusqueda != null && !StringUtils.isBlank(valorBusqueda)) {
                                            if (valorObligatoriedad.toLowerCase().equals(valorBusqueda.toLowerCase())) {
                                                InformeRegistroInconsistenteXls.Inconsistencia inconsistencia = new InformeRegistroInconsistenteXls.Inconsistencia(listaInconsistenciaVariables.size(), variable.getEtiqueta(), "Sin datos, valor requerido.");
                                                listaInconsistenciaVariables.add(inconsistencia);
                                            }
                                        } else {
                                            InformeRegistroInconsistenteXls.Inconsistencia inconsistencia = new InformeRegistroInconsistenteXls.Inconsistencia(listaInconsistenciaVariables.size(), variable.getEtiqueta(), "Sin datos, valor requerido.");
                                            listaInconsistenciaVariables.add(inconsistencia);
                                        }
                                    }

                                } else {

                                    if (variable.getTipo().equals("VARCHAR2")) {
                                        //Validar si la variable contiene la longitud maxima requerida en caracteres.
                                        if (valorCelda.length() > variable.getLongitud()) {
                                            InformeRegistroInconsistenteXls.Inconsistencia inconsistencia = new InformeRegistroInconsistenteXls.Inconsistencia(listaInconsistenciaVariables.size(), variable.getEtiqueta(), "Longitud cadena maxima(" + variable.getLongitud() + ").");
                                            listaInconsistenciaVariables.add(inconsistencia);
                                        }
                                        if (!StringUtils.isBlank(variable.getTablaReferencial())) {
                                            if (variable.getTablaReferencial().equals("IGE_PAIS")) {
                                                if (!ListasDesplegablesFormularios.listaContieneElPais(listaPaises, valorCelda)) {
                                                    InformeRegistroInconsistenteXls.Inconsistencia inconsistencia = new InformeRegistroInconsistenteXls.Inconsistencia(listaInconsistenciaVariables.size(), variable.getEtiqueta(), "Nombre del país inconrrecto.");
                                                    listaInconsistenciaVariables.add(inconsistencia);
                                                }
                                            }
                                            if (variable.getTablaReferencial().equals("IGE_DEPARTAMENTO")) {
                                                valorCelda = valorCelda.replace("_", " ");
                                                if (!ListasDesplegablesFormularios.listaContieneElDepartamento(listaDepartamentos, valorCelda)) {
                                                    InformeRegistroInconsistenteXls.Inconsistencia inconsistencia = new InformeRegistroInconsistenteXls.Inconsistencia(listaInconsistenciaVariables.size(), variable.getEtiqueta(), "Nombre del departamento inconrrecto.");
                                                    listaInconsistenciaVariables.add(inconsistencia);
                                                }
                                            }
                                            if (variable.getTablaReferencial().equals("IGE_MUNICIPIO")) {
                                                valorCelda = valorCelda.replace("_", " ");
                                                if (!ListasDesplegablesFormularios.listaContieneElMunicipio(listaMunicipios, valorCelda)) {
                                                    InformeRegistroInconsistenteXls.Inconsistencia inconsistencia = new InformeRegistroInconsistenteXls.Inconsistencia(listaInconsistenciaVariables.size(), variable.getEtiqueta(), "Nombre del municipio inconrrecto.");
                                                    listaInconsistenciaVariables.add(inconsistencia);
                                                }
                                            }
                                            if (variable.getTablaReferencial().equals("IGE_ESTADO_MATRICULA")) {
                                                if (!ListasDesplegablesFormularios.listaContieneElEstadoMatricula(listaMatriculas, valorCelda)) {
                                                    InformeRegistroInconsistenteXls.Inconsistencia inconsistencia = new InformeRegistroInconsistenteXls.Inconsistencia(listaInconsistenciaVariables.size(), variable.getEtiqueta(), "El estado de matricula es inconrrecto.");
                                                    listaInconsistenciaVariables.add(inconsistencia);
                                                }
                                            }
                                            if (variable.getTablaReferencial().equals("IGE_SITUACION_CONTROL")) {
                                                if (!ListasDesplegablesFormularios.listaContieneLaSituacionControl(listaSituaciones, valorCelda)) {
                                                    InformeRegistroInconsistenteXls.Inconsistencia inconsistencia = new InformeRegistroInconsistenteXls.Inconsistencia(listaInconsistenciaVariables.size(), variable.getEtiqueta(), "La situación de control es inconrrecta.");
                                                    listaInconsistenciaVariables.add(inconsistencia);
                                                }
                                            }
                                            if (variable.getTablaReferencial().equals("IGE_TIPO_CONTROL")) {
                                                if (!ListasDesplegablesFormularios.listaContieneElTipoControl(listaControles, valorCelda)) {
                                                    InformeRegistroInconsistenteXls.Inconsistencia inconsistencia = new InformeRegistroInconsistenteXls.Inconsistencia(listaInconsistenciaVariables.size(), variable.getEtiqueta(), "El tipo de control es inconrrecto.");
                                                    listaInconsistenciaVariables.add(inconsistencia);
                                                }
                                            }
                                            if (variable.getTablaReferencial().equals("IGE_TIPO_EMPRESA_CONTROLANTE")) {
                                                if (!ListasDesplegablesFormularios.listaContieneElTipoEmpresaControlante(listaEmpresas, valorCelda)) {
                                                    InformeRegistroInconsistenteXls.Inconsistencia inconsistencia = new InformeRegistroInconsistenteXls.Inconsistencia(listaInconsistenciaVariables.size(), variable.getEtiqueta(), "El tipo de empresa controlante es inconrrecto.");
                                                    listaInconsistenciaVariables.add(inconsistencia);
                                                }
                                            }
                                            if (variable.getTablaReferencial().equals("IGE_TIPO_ESTABLECIMIENTO")) {
                                                if (!ListasDesplegablesFormularios.listaContieneElTipoEstablecimiento(listaEstablecimeintos, valorCelda)) {
                                                    InformeRegistroInconsistenteXls.Inconsistencia inconsistencia = new InformeRegistroInconsistenteXls.Inconsistencia(listaInconsistenciaVariables.size(), variable.getEtiqueta(), "El tipo de establecimiento es inconrrecto.");
                                                    listaInconsistenciaVariables.add(inconsistencia);
                                                }
                                            }
                                            if (variable.getTablaReferencial().equals("IGE_TIPO_ORGANIZACION_UL")) {
                                                if (!ListasDesplegablesFormularios.listaContieneElTipoOrganizacionUnidadLegal(listaOrganizaciones, valorCelda)) {
                                                    InformeRegistroInconsistenteXls.Inconsistencia inconsistencia = new InformeRegistroInconsistenteXls.Inconsistencia(listaInconsistenciaVariables.size(), variable.getEtiqueta(), "El tipo de oraganización de unidad legal es inconrrecto.");
                                                    listaInconsistenciaVariables.add(inconsistencia);
                                                }
                                            }
                                        }
                                    }
                                    //Validar si la variable contiene el dato de tipo numerico.
                                    if (variable.getTipo().equals("NUMBER")) {
                                        String numeroResultado = null;
                                        if (cellRegistro.getCellType() == 0) {
                                            numeroResultado = Numero.formatoNumeroEntero(cellRegistro.getNumericCellValue() + "") + "";
                                            //LOGGER.info("----->" + numeroResultado + "");
                                            if (numeroResultado.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
                                                //"Is a number";
                                            } else {
                                                //"Is not a number";
                                                InformeRegistroInconsistenteXls.Inconsistencia inconsistencia = new InformeRegistroInconsistenteXls.Inconsistencia(listaInconsistenciaVariables.size(), variable.getEtiqueta(), "El dato " + numeroResultado + " debe ser numerico.");
                                                listaInconsistenciaVariables.add(inconsistencia);
                                            }
                                        } else {
                                            numeroResultado = Numero.formatoNumeroEntero(cellRegistro.getStringCellValue()) + "";
                                            ////LOGGER.info(.out.println("----->" + numeroResultado + "");
                                            if (numeroResultado.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
                                                //"Is a number";
                                            } else {
                                                //"Is not a number";
                                                InformeRegistroInconsistenteXls.Inconsistencia inconsistencia = new InformeRegistroInconsistenteXls.Inconsistencia(listaInconsistenciaVariables.size(), variable.getEtiqueta(), "El dato " + numeroResultado + " debe ser numerico.");
                                                listaInconsistenciaVariables.add(inconsistencia);
                                            }
                                        }
                                        //Validar si la variable contiene la longitud maxima requerida en caracteres.
                                        if (valorCelda.length() > variable.getLongitud()) {
                                            InformeRegistroInconsistenteXls.Inconsistencia inconsistencia = new InformeRegistroInconsistenteXls.Inconsistencia(listaInconsistenciaVariables.size(), variable.getEtiqueta(), "Longitud cadena maxima(" + variable.getLongitud() + ").");
                                            listaInconsistenciaVariables.add(inconsistencia);
                                        }
                                    }
                                    //Validar si la variable contiene una fecha valida
                                    if (variable.getTipo().equals("DATE")) {
                                        String fecha = null;
                                        if (cellRegistro.getCellType() == 0) {
                                            fecha = Fecha.formatFechaDateToString(cellRegistro.getDateCellValue());
                                        } else {
                                            fecha = Fecha.fomatoFechaStringToString(cellRegistro.getStringCellValue());
                                        }
                                        if (fecha == null) {
                                            InformeRegistroInconsistenteXls.Inconsistencia inconsistencia = new InformeRegistroInconsistenteXls.Inconsistencia(listaInconsistenciaVariables.size(), variable.getEtiqueta(), "Formato de fecha incorrecto. Ej 09/10/2012");
                                            listaInconsistenciaVariables.add(inconsistencia);
                                        }
                                    }
                                }
                            } else {
                                if (valorCelda != null) {
                                    if (!StringUtils.isBlank(valorCelda.trim())) {
                                        if (variable.getTipo().equals("VARCHAR2")) {
                                            //Validar si la variable contiene la longitud maxima requerida en caracteres.
                                            if (valorCelda.length() > variable.getLongitud()) {
                                                InformeRegistroInconsistenteXls.Inconsistencia inconsistencia = new InformeRegistroInconsistenteXls.Inconsistencia(listaInconsistenciaVariables.size(), variable.getEtiqueta(), "Longitud cadena maxima(" + variable.getLongitud() + ").");
                                                listaInconsistenciaVariables.add(inconsistencia);
                                            }
                                        }
                                        //Validar si la variable contiene el dato de tipo numerico.
                                        if (variable.getTipo().equals("NUMBER")) {
                                            String numeroResultado = null;
                                            if (cellRegistro.getCellType() == 0) {
                                                numeroResultado = Numero.formatoNumeroEntero(cellRegistro.getNumericCellValue() + "") + "";
                                                ////LOGGER.info(.out.println("----->" + numeroResultado + "");
                                                if (numeroResultado.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
                                                    ////LOGGER.info(.out.println("Is a number");
                                                } else {
                                                    ////LOGGER.info(.out.println("Is not a number");
                                                    InformeRegistroInconsistenteXls.Inconsistencia inconsistencia = new InformeRegistroInconsistenteXls.Inconsistencia(listaInconsistenciaVariables.size(), variable.getEtiqueta(), "El dato " + numeroResultado + " debe ser numerico.");
                                                    listaInconsistenciaVariables.add(inconsistencia);
                                                }
                                            } else {
                                                numeroResultado = Numero.formatoNumeroEntero(cellRegistro.getStringCellValue()) + "";
                                                ////LOGGER.info(.out.println("----->" + numeroResultado + "");
                                                if (numeroResultado.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
                                                    ////LOGGER.info(.out.println("Is a number");
                                                } else {
                                                    ////LOGGER.info(.out.println("Is not a number");
                                                    InformeRegistroInconsistenteXls.Inconsistencia inconsistencia = new InformeRegistroInconsistenteXls.Inconsistencia(listaInconsistenciaVariables.size(), variable.getEtiqueta(), "El dato " + numeroResultado + " debe ser numerico.");
                                                    listaInconsistenciaVariables.add(inconsistencia);
                                                }
                                            }
                                            //Validar si la variable contiene la longitud maxima requerida en caracteres.
                                            if (valorCelda.length() > variable.getLongitud()) {
                                                InformeRegistroInconsistenteXls.Inconsistencia inconsistencia = new InformeRegistroInconsistenteXls.Inconsistencia(listaInconsistenciaVariables.size(), variable.getEtiqueta(), "Longitud cadena maxima(" + variable.getLongitud() + ").");
                                                listaInconsistenciaVariables.add(inconsistencia);
                                            }
                                        }
                                        //Validar si la variable contiene una fecha valida
                                        if (variable.getTipo().equals("DATE")) {
                                            String fecha = null;
                                            if (cellRegistro.getCellType() == 0) {
                                                fecha = Fecha.formatFechaDateToString(cellRegistro.getDateCellValue());
                                            } else {
                                                fecha = Fecha.fomatoFechaStringToString(cellRegistro.getStringCellValue());
                                            }
                                            if (fecha == null) {
                                                InformeRegistroInconsistenteXls.Inconsistencia inconsistencia = new InformeRegistroInconsistenteXls.Inconsistencia(listaInconsistenciaVariables.size(), variable.getEtiqueta(), "Formato de fecha incorrecto. Ej 09/10/2012");
                                                listaInconsistenciaVariables.add(inconsistencia);
                                            }
                                        }
                                    }
                                }
                            }

                            resultadoFila.append(",");
                        }
                    }
                }

                if (listaInconsistenciaVariables.size() > 0) {
                    DecimalFormat nf = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    nf.applyPattern("####");
                    getListaInconsistencias().add(new InformeRegistroInconsistenteXls(Integer.parseInt(nf.format(cellIndice.getNumericCellValue()) + ""), nombreHoja, listaInconsistenciaVariables));
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
        resultado.append("]");
        //LOGGER.info(resultado.toString());
        return resultado;
    }

    /**
     * Método que permite obtener el valor de una columna, de una fila del
     * archivo XLS, pasando como parametro la fila con los datos, el encabezado
     * del archivo xls, las variables y la variable o columna a buscar.
     *
     * @param variableBuscar
     * @param datosFila
     * @param encabezadoXsl
     * @param columnas
     * @return
     */
    private String obtenerValorColumaDeFila(String variableBuscar, int indiceRegistro) {
        String resultado = null;
        boolean contieneVariableBuscar = false;
        try {
            Workbook libro = new HSSFWorkbook((FileInputStream) getFile().getInputstream());
            String[] hojas = {"Identificación", "Relación", "Historia", "Tamaño"};
            String[] grupoVariables = {"IDENTIFICACION", "RELACION", "NOVEDAD", "TAMAÑO"};

            int indiceHoja = 0;

            for (String hojaNombre : hojas) {
                //LOGGER.info(.out.println("(" + indiceRegistro + ")*************[" + hojaNombre + "]");
                if (contieneVariableBuscar == false) {

                    List<VariableIge> columnas = geteJBServicioVariableIge().buscarVariableByGrupo(grupoVariables[indiceHoja]);
                    List listaTemp = leerArchivoXls((FileInputStream) getFile().getInputstream(), hojaNombre);
                    //List listaTemp = obtenerListaDatosHojaXls(libro.getSheet(hojaNombre));
                    List listaDatosFila = (List) listaTemp.get(indiceRegistro);
                    List encabezadoXsl = (List) listaTemp.get(1);

                    for (int j = 0; j < encabezadoXsl.size(); j++) {
                        Cell cellEncabezado = (Cell) encabezadoXsl.get(j);
                        for (VariableIge variable : columnas) {

                            if (cellEncabezado.getStringCellValue().equals(variable.getEtiqueta())) {

                                if (variable.getColumna().equals(variableBuscar)) {
                                    //LOGGER.info(.out.println("*->[" + variable.getColumna() + "]=[" + variableBuscar + "]");
                                    Cell cellRegistro = (Cell) listaDatosFila.get(j);

                                    if (variable.getTipo().equals("NUMBER")) {
                                        if (cellRegistro.getCellType() == 0) {
                                            resultado = cellRegistro.getNumericCellValue() + "";
                                        } else {
                                            resultado = cellRegistro.getStringCellValue() + "";
                                        }
                                    } else if (variable.getTipo().equals("DATE")) {
                                        String fecha = null;
                                        if (cellRegistro.getCellType() == 0) {
                                            fecha = Fecha.formatFechaDateToString(cellRegistro.getDateCellValue());
                                            resultado = cellRegistro.getDateCellValue() + "";
                                        } else {
                                            fecha = Fecha.fomatoFechaStringToString(cellRegistro.getStringCellValue());
                                            resultado = cellRegistro.getStringCellValue();
                                        }
                                    } else {
                                        if (cellRegistro.getCellType() == 0) {
                                            resultado = cellRegistro.getNumericCellValue() + "";
                                        } else {
                                            resultado = cellRegistro.getStringCellValue();
                                        }
                                    }
                                    contieneVariableBuscar = true;
                                    break;
                                }
                            }

                        }
                    }

                    indiceHoja++;
                }
            }
        } catch (IOException ex) {
            //java.util.logging.Logger.getLogger(LeerExcel.class.getName()).log(Level.SEVERE, null, ex);
            LOGGER.warn(ex);
        }
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

    public ArchivoXlsFacadeLocal geteJBServicioArchivoXls() {
        return eJBServicioArchivoXls;
    }

    public void seteJBServicioArchivoXls(ArchivoXlsFacadeLocal eJBServicioArchivoXls) {
        this.eJBServicioArchivoXls = eJBServicioArchivoXls;
    }

    public boolean isSePuedeInsertarDatos() {
        return sePuedeInsertarDatos;
    }

    public void setSePuedeInsertarDatos(boolean sePuedeInsertarDatos) {
        this.sePuedeInsertarDatos = sePuedeInsertarDatos;
    }

    public boolean isArchivoValidoPorIdentidad() {
        return archivoValidoPorIdentidad;
    }

    public void setArchivoValidoPorIdentidad(boolean archivoValidoPorIdentidad) {
        this.archivoValidoPorIdentidad = archivoValidoPorIdentidad;
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

    public ListasDesplegablesFormularios getServicioListasDesplegablesFormularios() {
        return servicioListasDesplegablesFormularios;
    }

    public void setServicioListasDesplegablesFormularios(ListasDesplegablesFormularios servicioListasDesplegablesFormularios) {
        this.servicioListasDesplegablesFormularios = servicioListasDesplegablesFormularios;
    }

    public Login getServicioLogin() {
        return servicioLogin;
    }

    public void setServicioLogin(Login servicioLogin) {
        this.servicioLogin = servicioLogin;
    }

    public FormularioUnidadLegal getServicioFormularioUnidadLegal() {
        return servicioFormularioUnidadLegal;
    }

    public void setServicioFormularioUnidadLegal(FormularioUnidadLegal servicioFormularioUnidadLegal) {
        this.servicioFormularioUnidadLegal = servicioFormularioUnidadLegal;
    }

    public FormularioEstablecimiento getServicioFormularioEstablecimiento() {
        return servicioFormularioEstablecimiento;
    }

    public void setServicioFormularioEstablecimiento(FormularioEstablecimiento servicioFormularioEstablecimiento) {
        this.servicioFormularioEstablecimiento = servicioFormularioEstablecimiento;
    }

    public int getFilaDatosInicial() {
        return filaDatosInicial;
    }

    public void setFilaDatosInicial(int filaDatosInicial) {
        this.filaDatosInicial = filaDatosInicial;
    }

    public Long getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Long idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public Date getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(Date fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public String getCodigoArchivo() {
        return codigoArchivo;
    }

    public void setCodigoArchivo(String codigoArchivo) {
        this.codigoArchivo = codigoArchivo;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getMensajeErrorValidacionArchivo() {
        return mensajeErrorValidacionArchivo;
    }

    public void setMensajeErrorValidacionArchivo(String mensajeErrorValidacionArchivo) {
        this.mensajeErrorValidacionArchivo = mensajeErrorValidacionArchivo;
    }

}
