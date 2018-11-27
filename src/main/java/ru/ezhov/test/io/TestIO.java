package ru.ezhov.test.io;

import java.io.*;

public class TestIO {
    public static void main(String[] args) {
        try {
            String from = "D:/programmer/1.mp4";
            String to = "D:/programmer/2.mp4";
            copyWithFile(from, to);
            new File(to).delete();
            copyWithBuffer(from, to);
            new File(to).delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void copyWithBuffer(String pathFrom, String pathTo) throws Exception {
        long start = System.currentTimeMillis();
        try (BufferedInputStream fis = new BufferedInputStream(new FileInputStream(pathFrom), 4096)) {
            try (BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(pathTo))) {
                byte[] buff = new byte[4096];
                int read;
                while ((read = fis.read(buff)) != -1) {
                    fos.write(buff, 0, read);
                }
            }
        }
        System.out.println("buffer: " + (System.currentTimeMillis() - start));

    }

    private static void copyWithFile(String pathFrom, String pathTo) throws Exception {
        long start = System.currentTimeMillis();
        try (FileInputStream fis = new FileInputStream(pathFrom)) {
            try (FileOutputStream fos = new FileOutputStream(pathTo)) {
                byte[] buff = new byte[4096];
                int read;
                while ((read = fis.read(buff)) != -1) {
                    fos.write(buff, 0, read);
                }
            }
        }
        System.out.println("file: " + (System.currentTimeMillis() - start));
    }
}
