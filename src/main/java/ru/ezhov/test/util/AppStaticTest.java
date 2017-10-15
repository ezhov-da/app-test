package ru.ezhov.test.util;

public class AppStaticTest {

    static {
        System.out.println(StaticClassNameUtil.getCurrentClassName());
    }

    public static void main(String[] args) {

    }
}
