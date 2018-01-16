package ru.ezhov.sudoku;

public class Sudoku {
    private static final int COUNT = 5;

    public static void main(String[] args) {
        int[][] ints = generate();
        print(ints);
    }

    private static int[][] generate() {
        int max = COUNT * COUNT;
        // https://habrahabr.ru/post/192102/
        int[][] array = new int[max][max];
        for (int i = 0; i < max; i++) {
            for (int j = 0; j < max; j++) {
                int val = ((i * COUNT + i / COUNT + j) % (COUNT * COUNT) + 1);
                array[i][j] = val;
            }
        }
        return array;
    }

    private static void print(int[][] array) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            for (int y = 0; y < array[i].length; y++) {
                int val = array[i][y];
                stringBuilder.append(String.format("%-3s", val));
                if ((y + 1) % COUNT == 0 && y + 1 != array[i].length) {
                    stringBuilder.append(String.format("%-3s", "|"));
                } else if (y + 1 != array[i].length) {
                    stringBuilder.append(String.format("%-3s", "-"));
                }
            }
            System.out.print(stringBuilder.toString());
            System.out.println("");
            if ((i + 1) % COUNT == 0) {
                String line = stringBuilder.toString();
                for (int y = 0; y < line.length(); y++) {
                    System.out.print("-");
                }
                System.out.println("");
            }

            stringBuilder = new StringBuilder();
        }
    }
}
