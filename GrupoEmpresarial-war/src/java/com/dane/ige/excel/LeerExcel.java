package com.dane.ige.excel;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
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
public class LeerExcel {

    final static Logger LOGGER = Logger.getLogger(LeerExcel.class);
    private UploadedFile file;

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
        FacesMessage message = new FacesMessage("Exitosa", event.getFile().getFileName() + " está cargado.");
        FacesContext.getCurrentInstance().addMessage(null, message);
        visualizarDatosXls(leerArchivoXlsGrupoEmpresarial((FileInputStream) event.getFile().getInputstream(), 1));
    }

    /**
     * Método que permite leer el archivo xls de la unidad, Grupo Empresa y
     * genera un List de resultado de datos.
     *
     * @param archivo
     * @param indexHoja
     * @return List
     */
    public List leerArchivoXlsGrupoEmpresarial(FileInputStream archivo, int indexHoja) {

        List sheetData = new ArrayList();
        FileInputStream fileInpSt = null;
        try {

            fileInpSt = archivo;
            Workbook workbook = new HSSFWorkbook(fileInpSt);
            Sheet sheet = workbook.getSheetAt(indexHoja);

            Iterator rows = sheet.rowIterator();
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
     * Método que permite leer una hoja de un archivo xls y 
     * genera una List de resultado de datos.
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
     * los datos del archivo xls.
     *
     * @param sheetData
     */
    public void visualizarDatosXls(List sheetData) {
        StringBuffer resultado;
        for (Object sheetRow : sheetData) {
            resultado = new StringBuffer();
            List list = (List) sheetRow;
            for (int j = 0; j < list.size(); j++) {
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
            LOGGER.info(resultado);
        }
    }

    //Métodos Set y Get de la clase
    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
}
