package ru.ezhov.test.util;

public class AppStaticTest {

    static {
        System.out.println(ClassNameUtil.getCurrentClassName());
    }

    public static void main(String[] args) {

    }
}
