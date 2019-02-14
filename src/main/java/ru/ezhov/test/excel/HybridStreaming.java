package ru.ezhov.test.excel;

import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTSheet;
import org.xml.sax.SAXException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * This demonstrates how a hybrid approach to workbook read can be taken, using
 * a mix of traditional XSSF and streaming one particular worksheet (perhaps one
 * which is too big for the ordinary DOM parse).
 */
public class HybridStreaming {

    private static final String SHEET_TO_STREAM = "Лист1";

    public static void main(String[] args) throws IOException, SAXException {
        try (InputStream sourceBytes = new FileInputStream("large_excel_file/large-file-with-empty.xlsx")) {
            XSSFWorkbook workbook = new XSSFWorkbook(sourceBytes) {
                /**
                 * Avoid DOM parse of large sheet
                 */
                @Override
                public void parseSheet(java.util.Map<String, XSSFSheet> shIdMap, CTSheet ctSheet) {
                    System.out.println(ctSheet.getName());
                    if (!SHEET_TO_STREAM.equals(ctSheet.getName())) {
                        super.parseSheet(shIdMap, ctSheet);
                    }
                }
            };

            // Having avoided a DOM-based parse of the sheet, we can stream it instead.
            ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(workbook.getPackage());
            new XSSFSheetXMLHandler(workbook.getStylesSource(), strings, createSheetContentsHandler(), false);
            workbook.close();
        }
    }

    private static SheetContentsHandler createSheetContentsHandler() {
        return new SheetContentsHandler() {

            @Override
            public void startRow(int rowNum) {
                System.out.print("-> ");
            }

            @Override
            public void endRow(int rowNum) {
                System.out.println();
            }

            @Override
            public void cell(String cellReference, String formattedValue, XSSFComment comment) {
                System.out.print(cellReference + ",");
            }
        };
    }
}

