package com.dane.ige.excel;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Clase que administra los estilos para las celdas en la generación del los
 * archivos Xls
 *
 * @author SRojasM
 */
public class EstiloCeldasXls {

    public EstiloCeldasXls() {
    }

    /**
     * Método que permite obtener el estilo de una celda aplicando el borde
     * completo. Incluye que la celda este bloqueada para exritura, de acuerdo
     * al parametro.
     *
     * @param libro
     * @param editable
     * @return CellStyle
     */
    public static CellStyle estiloBordeCompletoCedaEditable(Workbook libro, boolean editable) {
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

    /**
     * Método que permite obtener el estilo de una celda aplicando el borde
     * completo. Incluye que la celda este bloqueada para exritura, de acuerdo
     * al parametro y con formato de fecha dd/MM/yyyy
     *
     * @param libro
     * @param editable
     * @return CellStyle
     */
    public static CellStyle estiloBordeCompletoCedaEditableFecha(Workbook libro, boolean editable) {
        CellStyle css = libro.createCellStyle();
        css.setLocked((editable != true));
        short df = libro.createDataFormat().getFormat("dd/MM/yyyy");
        css.setDataFormat(df);
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

    /**
     * Método que permite obtener el estilo de una celda aplicando el borde
     * completo. Incluye que la celda este bloqueada para exritura, de acuerdo
     * al parametro y con formato de Texto
     *
     * @param libro
     * @param editable
     * @return CellStyle
     */
    public static CellStyle estiloBordeCompletoCedaEditableTexto(Workbook libro, boolean editable) {
        CellStyle css = libro.createCellStyle();
        css.setLocked((editable != true));
        short df = libro.createDataFormat().getFormat("@");
        css.setDataFormat(df);
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

    /**
     * Método que permite obtener el estilo de una celda aplicando el borde
     * completo. Incluye que la celda este bloqueada para exritura, de acuerdo
     * al parametro y con formato de Numero
     *
     * @param libro
     * @param editable
     * @return CellStyle
     */
    public static CellStyle estiloBordeCompletoCedaEditableNumero(Workbook libro, boolean editable) {
        CellStyle css = libro.createCellStyle();
        css.setLocked((editable != true));
        short df = libro.createDataFormat().getFormat("0");
        //css.setDataFormat(df);
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
}
