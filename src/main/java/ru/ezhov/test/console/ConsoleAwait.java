package ru.ezhov.test.console;

import java.time.LocalDateTime;

public class ConsoleAwait {
    public static void main(String[] args) {
        while(true){
            System.out.println(LocalDateTime.now());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
