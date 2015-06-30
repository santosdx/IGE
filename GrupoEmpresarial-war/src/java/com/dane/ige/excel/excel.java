package com.dane.ige.excel;

import java.io.File;
import java.io.IOException;

import jxl.*;
import jxl.read.biff.BiffException;

public class excel {

    public void leer() throws IOException, BiffException {
        Workbook workbook = Workbook.getWorkbook(new File("C:/Users/klbejaranos/Desktop/Trabajo/GE/Formularios en Excel 2010/Formulario Grupo Empresarial.xlsm")); //Pasamos el excel que vamos a leer
        Sheet sheet = workbook.getSheet(0); //Seleccionamos la hoja que vamos a leer
        String nombre;
        for (int fila = 1; fila < sheet.getRows(); fila++) { //recorremos las filas
            for (int columna = 0; columna < sheet.getColumns(); columna++) { //recorremos las columnas
                nombre = sheet.getCell(columna, fila).getContents(); //setear la celda leida a nombre
                System.out.print(nombre + ""); // imprimir nombre
            }
            System.out.println("\n");
            System.out.println("————————————-");
        }
    }
}
