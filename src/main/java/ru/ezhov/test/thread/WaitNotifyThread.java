package ru.ezhov.tests.swingwithout.thread;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ezhov_da
 */
public class WaitNotifyThread implements Runnable {

    private static final Object monitor = new Object();
    private static boolean ready = false;

    private Test test;

    public enum Test {

        PREPARE, SEND
    };

    public WaitNotifyThread(Test test) {
        this.test = test;
    }

    public void prepareData() {
        synchronized (monitor) {
            System.out.println("Data prepared");
            ready = true;
            monitor.notifyAll();
        }
    }

    public void sendData() {
        synchronized (monitor) {
            System.out.println("Waiting for data...");
            while (!ready) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // continue execution and sending data
            System.out.println("Sending data...");
        }
    }

    @Override
    public void run() {
        switch (test) {
            case PREPARE:
                prepareData();
                break;
            case SEND:
                sendData();
                break;
        }
    }

    public static void main(String[] args) {
        WaitNotifyThread waitNotifyThreadSend = new WaitNotifyThread(Test.SEND);
        Thread thread = new Thread(waitNotifyThreadSend);
        thread.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(WaitNotifyThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        WaitNotifyThread waitNotifyThreadPrepare = new WaitNotifyThread(Test.PREPARE);
        thread = new Thread(waitNotifyThreadPrepare);
        thread.start();
    }
}
