package ru.ezhov.test.test;

import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println(fibonachy(scan.nextInt()));
    }

    private static int fibonachy(int n) {
        if (n <= 2) {
            return 1;
        } else {
            int a = 1;
            int b = 1;
            int val = 0;
            for (int i = 2; i < n; i++) {
                val = a + b;
                a = b;
                b = val;
            }
            return val;
        }
    }
}