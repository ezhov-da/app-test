package ru.ezhov.test.bits;

public class BitsTest {
    public static void main(String[] args) {
        System.out.println("bit count 42: " + Integer.bitCount(42));
        System.out.println("bit count 32: " + Integer.bitCount(32));
        System.out.println("highest One Bit 42: " + Integer.highestOneBit(42));
        System.out.println("highest One Bit 32: " + Integer.highestOneBit(32));
        System.out.println("to Binary String 42: " + Integer.toBinaryString(42));
        System.out.println("to Binary String -42: " + Integer.toBinaryString(-42));
        System.out.println("to Binary String -314: " + Integer.toBinaryString(-314));
        System.out.println("to Binary String 5: " + Integer.toBinaryString(5));
        System.out.println("to Binary String 4: " + Integer.toBinaryString(4));
        //System.out.println("to Binary String -314: " + Integer.parseInt("11111111111111111111111011000110", 10));

        int x = 5;
        int y = 4;
        System.out.println("& - " +
                x + ": " + Integer.toBinaryString(x) + " / " +
                y + ": " + Integer.toBinaryString(y) + " - " +
                (x & y) + "/" + Integer.toBinaryString((x & y))
        );

        System.out.println("| - " +
                x + ": " + Integer.toBinaryString(x) + " / " +
                y + ": " + Integer.toBinaryString(y) + " - " +
                (x | y) + "/" + Integer.toBinaryString((x | y))
        );

        System.out.println("^ - " +
                x + ": " + Integer.toBinaryString(x) + " / " +
                y + ": " + Integer.toBinaryString(y) + " - " +
                (x ^ y) + "/" + Integer.toBinaryString((x ^ y))
        );

        System.out.println("~ - " +
                x + ": " + Integer.toBinaryString(x) + " / " +
                y + ": " + Integer.toBinaryString(y) + " - " +
                (~x) + "/" + Integer.toBinaryString((~x))
        );
        System.out.println("<< - " +
                x + ": " + Integer.toBinaryString(x) + " / " +
                y + ": " + Integer.toBinaryString(y) + " - " +
                (x << 2) + "/" + Integer.toBinaryString((x << 2))
        );

        System.out.println(">> - " +
                x + ": " + Integer.toBinaryString(x) + " / " +
                y + ": " + Integer.toBinaryString(y) + " - " +
                (x >> 2) + "/" + Integer.toBinaryString((x >> 2))
        );

        System.out.println(">>> - " +
                x + ": " + Integer.toBinaryString(x) + " / " +
                y + ": " + Integer.toBinaryString(y) + " - " +
                (x >>> 2) + "/" + Integer.toBinaryString((x >>> 2))
        );

    }
}
