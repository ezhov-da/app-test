package ru.ezhov.test.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class WriteWithoutSave {
    public static void main(String[] args) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            try (Workbook workbook = new XSSFWorkbook()) {
                final Sheet sheet = workbook.createSheet();
                final Row row = sheet.createRow(0);
                final Cell cell = row.createCell(0);
                cell.setCellValue("test");

                workbook.write(byteArrayOutputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(Arrays.toString(byteArrayOutputStream.toByteArray()));
    }
}
