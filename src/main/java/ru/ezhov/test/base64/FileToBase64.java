package ru.ezhov.test.base64;

import java.io.BufferedInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

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

        Base64.Encoder encoder = Base64.getEncoder();

        try (BufferedInputStream is = new BufferedInputStream(Files.newInputStream(Paths.get(fileAbsolutePath)))) {
            byte[] data = new byte[is.available()];
            is.read(data);
            System.out.println(encoder.encodeToString(data));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
