package ru.ezhov.test.console;

import java.io.IOException;
import java.util.Scanner;

/*
run-console-test.bat
*/
public class ConsoleTest {
    public static void main(String[] args) throws IOException {
        int available = System.in.available();
        if (available != 0) {
            System.out.println(System.in.available());
            try (Scanner scanner = new Scanner(System.in, "UTF-8")) {
                while (scanner.hasNextLine()) {
                    System.out.println(scanner.nextLine());
                }
            }
        } else {
            System.out.println("Жду поток байтов :)");
        }
        System.exit(0);
    }
}
