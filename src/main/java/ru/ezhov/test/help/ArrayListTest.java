package ru.ezhov.test.help;

import java.util.ArrayList;
import java.util.Arrays;

public class ArrayListTest {
    public static void main(String[] args) {
        ArrayList<String> strings = new ArrayList<String>() {{
            add("a");
            add("b");
        }};

        ArrayList<String> listFromObject = (ArrayList<String>) get(strings);
        System.out.println(Arrays.toString(listFromObject.toArray()));
    }

    private static Object get(ArrayList<String> list) {
        return list;
    }
}
