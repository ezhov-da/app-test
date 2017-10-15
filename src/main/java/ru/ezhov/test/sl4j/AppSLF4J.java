package ru.ezhov.test.sl4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppSLF4J {
    private static final Logger LOG = LoggerFactory.getLogger(AppSLF4J.class);

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            LOG.debug("" + i);
        }

    }
}
