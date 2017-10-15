package ru.ezhov.test.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ClassNameUtilTest {
    @Test
    public void getClassName() throws Exception {
        String currentClassName = ClassNameUtil.getCurrentClassName();
        System.out.println(currentClassName);
        assertEquals("ru.ezhov.test.util.ClassNameUtilTest", currentClassName);
    }
}