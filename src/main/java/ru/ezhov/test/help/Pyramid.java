package ru.ezhov.test.help;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Pyramid {
    public static void main(String[] args) {
        process(generateSourceList());
    }

    private static List<List<String>> generateSourceList() {
        List<List<String>> sources = new ArrayList<>();
        int countSources = new Random().nextInt(10) + 1;
        for (int i = 0; i < countSources; i++) {
            List<String> source = new ArrayList<>();
            int countSource = new Random().nextInt(50) + 1;
            System.out.println("Сгенерирован источник с " + countSource + " элементами");
            for (int cs = 0; cs < countSource; cs++) {
                source.add("1");
            }
            sources.add(source);
        }
        System.out.println("Сгенерировано: " + countSources + " источников");
        return sources;
    }

    private static void process(List<List<String>> sources) {
        for (List<String> list : sources) {
            System.out.println("==============================");
            System.out.println("Вывод пирамиды из " + list.size() + " элементов");
            System.out.println("==============================");
            printPyramidData(
                    createPyramidDataFromList(list)
            );
            System.out.println();
        }
    }

    private static PyramidData createPyramidDataFromList(List<String> data) {
        int beginSubstr = 0;
        List<List<String>> listRows = new ArrayList<>();
        int maxLen = 0;
        for (int i = 0; i < data.size(); i++) {
            int step = i + 1;
            if (beginSubstr + step < data.size()) {
                List<String> row = data.subList(beginSubstr, beginSubstr + step);
                maxLen = row.size() > maxLen ? row.size() : maxLen;
                listRows.add(row);
                beginSubstr = beginSubstr + step;
            } else {
                System.out.println("Лишние элементы: " +
                        Arrays.toString(
                                data.subList(beginSubstr, data.size())
                                        .toArray()
                        )
                );
                break;
            }
        }
        System.out.println("Получено: " + listRows.size() + " строк");
        return new PyramidData(listRows, maxLen);
    }

    private static void printPyramidData(PyramidData pyramidData) {
        int maxLenWithSpace = pyramidData.maxLen() + (pyramidData.maxLen() - 1);
        for (List<String> list : pyramidData.data()) {
            int listSize = list.size();
            int startPost = (maxLenWithSpace - (listSize + (listSize - 1))) / 2;
            for (int l = 0; l < maxLenWithSpace; l++) {
                if (l == startPost) {
                    for (int s = 0; s < listSize; s++) {
                        String val = list.get(s);
                        if (s == (listSize - 1)) {
                            System.out.print(val);
                        } else {
                            System.out.print(val + " ");
                        }
                    }
                } else {
                    if (l == (maxLenWithSpace - 1)) {
                        System.out.println(" ");
                    } else {
                        System.out.print(" ");
                    }
                }
            }
        }
    }
}

class PyramidData {
    private List<List<String>> data;
    private int maxLen;

    PyramidData(List<List<String>> data, int maxLen) {
        this.data = data;
        this.maxLen = maxLen;
    }

    List<List<String>> data() {
        return data;
    }

    int maxLen() {
        return maxLen;
    }
}
