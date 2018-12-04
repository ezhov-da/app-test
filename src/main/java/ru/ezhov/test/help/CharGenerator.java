package ru.ezhov.test.help;

import java.util.concurrent.ThreadLocalRandom;

public class CharGenerator {
    public static void main(String[] args) {
        System.out.println(new String(new byte[]{(byte) ThreadLocalRandom.current().nextInt(97, 122 + 1)}));
        System.out.println((char) ThreadLocalRandom.current().nextInt(97, 122 + 1));
    }
}
