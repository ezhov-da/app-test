package ru.ezhov.test.base64;

import sun.misc.BASE64Encoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

/**
 * java -cp app-test.jar ru.ezhov.test.base64.FileToBase64 app-test.jar
 */
public class FileToBase64 {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("One arg - absolute file path...");
            System.err.println("exit");
            return;
        }
        String fileAbsolutePath = args[0];

        BASE64Encoder base64Encoder = new BASE64Encoder();

        try {
            base64Encoder.encode(
                    new BufferedInputStream(new FileInputStream(fileAbsolutePath)),
                    System.out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
