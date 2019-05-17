package ru.ezhov.test.log4j;

import org.apache.log4j.Logger;

public class CommonService {
    private static final Logger LOG = Logger.getLogger(CommonService.class);

    void test() {
        LOG.info("CommonService");
    }
}
