package com.dane.ige.archivo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

/**
 *
 * @author SRojasM
 */
@ManagedBean(name = "MbAdministrarArchivo")
@SessionScoped
public class AdministrarArchivo {

    final static Logger LOGGER = Logger.getLogger(AdministrarArchivo.class);
    private UploadedFile file;

    public AdministrarArchivo() {
    }

    /**
     * Método que permite capturar el evento FileUploadEvent del componente de
     * archivos, y permite obtener el archivo y a su ves subirlo en un
     * directorio de ser necesario.
     *
     * @param event
     */
    public void subirArchivo(FileUploadEvent event) throws IOException{
        setFile(event.getFile());
        FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
        leerArchivoXls((FileInputStream) event.getFile().getInputstream());
    }

    private void leerArchivoXls(FileInputStream archivo) {

        List sheetData = new ArrayList();
        FileInputStream fis = null;
        try {

            fis = archivo;
            HSSFWorkbook workbook = new HSSFWorkbook(fis);
            HSSFSheet sheet = workbook.getSheetAt(0);

            Iterator rows = sheet.rowIterator();
            while (rows.hasNext()) {
                HSSFRow row = (HSSFRow) rows.next();

                Iterator cells = row.cellIterator();
                List data = new ArrayList();
                while (cells.hasNext()) {
                    HSSFCell cell = (HSSFCell) cells.next();
                    //  System.out.println("Añadiendo Celda: " + cell.toString());
                    data.add(cell);
                }
                sheetData.add(data);
            }
        } catch (IOException e) {
            java.util.logging.Logger.getLogger(AdministrarArchivo.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(AdministrarArchivo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        showExelData(sheetData);
    }

    private static void showExelData(List sheetData) {
    //
        // Iterates the data and print it out to the console.
        //
        for (int i = 0; i < sheetData.size(); i++) {
            List list = (List) sheetData.get(i);
            for (int j = 0; j < list.size(); j++) {
                Cell cell = (Cell) list.get(j);
                if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    System.out.print(cell.getNumericCellValue());
                } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                    System.out.print(cell.getRichStringCellValue());
                } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
                    System.out.print(cell.getBooleanCellValue());
                }
                if (j < list.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("");
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
