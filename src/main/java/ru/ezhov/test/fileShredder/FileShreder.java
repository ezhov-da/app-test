package ru.ezhov.test.fileShredder;

import java.io.*;
import java.security.DigestInputStream;
import java.security.MessageDigest;

/**
 * Created by ezhov_da on 06.10.2017.
 */
public class FileShreder {
    public static void main(String[] args) {
        shredder();
        glue();
    }

    private static void shredder() {
        try {
            File source = new File("E:/test_file_clear.txt");

            File f1 = File.createTempFile("md51", ".hash");
            File f2 = File.createTempFile("md52", ".hash");

            MessageDigest md = MessageDigest.getInstance("MD5");
            try (InputStream is = new FileInputStream(source);
                 DigestInputStream dis = new DigestInputStream(is, md);
                 FileOutputStream fileOutputStream1 = new FileOutputStream(f1);
                 FileOutputStream fileOutputStream2 = new FileOutputStream(f2)
            ) {

                byte[] buffer = new byte[4];
                int byteRead;

                while ((byteRead = dis.read(buffer)) != -1) {

                    for (int i = 0; i < buffer.length; i++) {
                        if (i % 2 == 0) {
                            System.out.println("%2: " + buffer[i]);
                            fileOutputStream1.write(buffer[i]);
                            fileOutputStream1.flush();
                        } else {
                            System.out.println("!%2: " + buffer[i]);
                            fileOutputStream2.write(buffer[i]);
                            fileOutputStream2.flush();
                        }
                    }
                }
            }

            byte[] digest = md.digest();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < digest.length; i++) {
                sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
            }

            String hashMD5 = sb.toString();

            System.out.println(hashMD5);

            File f1f = new File("E:/" + hashMD5 + "-1.source");
            f1f.delete();
            File f2f = new File("E:/" + hashMD5 + "-2.source");
            f2f.delete();


            f1.renameTo(f1f);
            f2.renameTo(f2f);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void glue() {
        try {
            File f1f = new File("E:/71ab68de0ea87bb0d4d4e3c8b0e963eb-1.source");
            File f2f = new File("E:/71ab68de0ea87bb0d4d4e3c8b0e963eb-2.source");

            File source = new File("E:/test_file_clear_glue.txt");

            try (
                    BufferedInputStream part1 = new BufferedInputStream(new FileInputStream(f1f));
                    BufferedInputStream part2 = new BufferedInputStream(new FileInputStream(f2f));
                    BufferedOutputStream sourceOutput = new BufferedOutputStream(new FileOutputStream(source))
            ) {
                byte[] buffer1 = new byte[part1.available()];
                byte[] buffer2 = new byte[part2.available()];
                int byteRead;

                part1.read(buffer1);
                part2.read(buffer2);

                for (int i = 0; i < buffer1.length; i++) {
                    sourceOutput.write(buffer1[i]);
                    sourceOutput.write(buffer2[i]);
                }
                sourceOutput.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
