package utils;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtils {

    public static String read(String path, int row, int col) throws IOException {
        FileInputStream fs = new FileInputStream(path);
        XSSFWorkbook workbook = new XSSFWorkbook(fs);
        XSSFSheet sheet = workbook.getSheetAt(0);
        return String.valueOf(sheet.getRow(row).getCell(col));
    }

    public static void write(String data1, String data2, String path) throws IOException {

        //Write a new data
        FileInputStream fs = new FileInputStream(path);
        Workbook wb = new XSSFWorkbook(fs);
        Sheet sheet = wb.getSheetAt(0);

        //Create a new row in the excel file
        Row newRow = sheet.createRow(sheet.getLastRowNum() + 1);
        newRow.createCell(0).setCellValue(data1);
        newRow.createCell(1).setCellValue(data2);

        //write the data using output stream
        FileOutputStream fos = new FileOutputStream(path);
        wb.write(fos);
        fos.close();
    }

    public static int getLastRowNumber(String path) throws IOException {
        FileInputStream fs = new FileInputStream(path);
        Workbook wb = new XSSFWorkbook(fs);
        Sheet sheet = wb.getSheetAt(0);
        return sheet.getLastRowNum() + 1;
    }

}