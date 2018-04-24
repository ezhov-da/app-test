package ru.ezhov.test.help;

import java.util.Arrays;

public class ArrayApp {
    public static void main(String[] args) {
        //Подготовка тестовых данных
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i <= 100; i++) {
            stringBuilder.append(i);
            if (i % 10 == 0) {
                stringBuilder.append(".");
            } else {
                stringBuilder.append(":");
            }
        }

        //Здесь должно происходить получение всех данных из файла
        String result = stringBuilder.toString();
        System.out.println(result);
        String[] array = result.split("\\.");
        System.out.println(Arrays.toString(array));
        int length = array.length;
        String[][] resultArray = new String[10][10];
        for (int i = 0; i < length; i++) {
            String[] valArray = array[i].split(":");
            int lengthC = valArray.length;
            for (int c = 0; c < lengthC; c++) {
                resultArray[i][c] = valArray[c];
            }
        }
        System.out.println(Arrays.toString(resultArray));
    }
}
