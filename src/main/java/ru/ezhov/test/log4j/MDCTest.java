package ru.ezhov.test.log4j;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.apache.log4j.NDC;

//%X
public class MDCTest {
    private static final Logger LOG = Logger.getLogger(NDCTest.class);

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new MDCTestThread(i).start();
        }
    }
}


class MDCTestThread extends Thread {
    private static final Logger LOG = Logger.getLogger(NDCTestThread.class);
    private int number;

    public MDCTestThread(int number) {
        this.number = number;
    }

    @Override
    public void run() {
        MDC.put("Thread", number);
        MDC.put("Test", "OK");
        for (int i = 0; i < 3; i++) {
            LOG.info(i);
            new CommonService().test();
        }
        MDC.clear();
    }
}
