package ru.ezhov.test.log4j;

import org.apache.log4j.Logger;
import org.apache.log4j.NDC;

//%x
public class NDCTest {
    private static final Logger LOG = Logger.getLogger(NDCTest.class);

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            new NDCTestThread(i).start();
        }
    }
}


class NDCTestThread extends Thread {
    private static final Logger LOG = Logger.getLogger(NDCTestThread.class);
    private int number;

    public NDCTestThread(int number) {
        this.number = number;
    }

    @Override
    public void run() {
        NDC.push("Thread " + number);
        for (int i = 0; i < 3; i++) {
            LOG.info(i);
            new CommonService().test();
        }
        NDC.pop();
        NDC.remove();
    }
}

