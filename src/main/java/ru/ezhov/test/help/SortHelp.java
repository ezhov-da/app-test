package ru.ezhov.test.help;

import java.util.Arrays;
import java.util.Comparator;

public class SortHelp {
    public static void main(String[] args) {
        String str = "bZDFghA";
        String[] sAr = str.split("");
        Arrays.sort(sAr, Comparator.comparing(String::toLowerCase));
        System.out.println(Arrays.toString(sAr));
    }
}
