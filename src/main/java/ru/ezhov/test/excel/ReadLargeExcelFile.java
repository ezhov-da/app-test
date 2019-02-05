package ru.ezhov.test.excel;

public class ReadLargeExcelFile {
    public static void main(String[] args) {
        try {
            ExampleEventUserModel.main(new String[]{"large_excel_file/large-file.xlsx"});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
