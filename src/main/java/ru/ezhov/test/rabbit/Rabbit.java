package ru.ezhov.test.rabbit;

import java.util.concurrent.atomic.AtomicInteger;

public class Rabbit implements Runnable {
    private Thread thread;

    public Rabbit() {
        this.thread = new Thread(this);
        this.thread.setDaemon(false);
        this.thread.start();
    }

    public static volatile AtomicInteger counter = new AtomicInteger();

    @Override
    public void run() {
        if (counter.intValue() + 2 <= 10) {
            new Rabbit();
            counter.incrementAndGet();

            new Rabbit();
            counter.incrementAndGet();

            System.out.println(counter);
        }
    }

    public void join() throws InterruptedException {
        this.thread.join();
    }

    public static void main(String[] args) {
        try {
            new Rabbit().join();

            System.out.println(Rabbit.counter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
