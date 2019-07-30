package ru.ezhov.test.jul;

import java.util.logging.Level;
import java.util.logging.Logger;

public class JulTest {
    private static final Logger LOG = Logger.getLogger(JulTest.class.getName());

    public static void main(String[] args) {
        LOG.log(Level.CONFIG, "action=\"Вот, новый поворот\"");
    }
}
