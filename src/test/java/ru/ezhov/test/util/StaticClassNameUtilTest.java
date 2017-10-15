package ru.ezhov.test.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StaticClassNameUtilTest {
    @Test
    public void getClassName() throws Exception {
        String currentClassName = StaticClassNameUtil.getCurrentClassName();
        System.out.println(currentClassName);
        assertEquals("ru.ezhov.test.util.StaticClassNameUtilTest", currentClassName);
    }
}