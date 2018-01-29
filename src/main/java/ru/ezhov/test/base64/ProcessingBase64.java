package ru.ezhov.test.base64;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ProcessingBase64 {

    private static String fileSource = "E:\\_git_local_data_\\trunk\\groovy\\from-db-to-db-base64.zip";
    private static String fileTarger = "E:\\_git_local_data_\\trunk\\groovy\\from-db-to-db-base64.text";


    public static void main(String[] args) {
        ProcessingBase64 processingBase64 = new ProcessingBase64();
        try {
            processingBase64.fromBase64(
                    new File("C:\\Users\\rrnezh\\programmer\\base64.txt"),
                    new File("C:\\Users\\rrnezh\\programmer\\from-db-to-db.zip")
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void toBase64(File source, File target) throws Exception {
        BASE64Encoder base64Encoder = new BASE64Encoder();
        BufferedInputStream fileInputStream =
                new BufferedInputStream(new FileInputStream(source));

        List<Byte> bytes = new ArrayList<Byte>();
        byte[] nowByte = new byte[fileInputStream.available()];

        int read = 0;

        while ((read = fileInputStream.read(nowByte)) != -1) {
            for (int i = 0; i < nowByte.length; i++) {
                bytes.add(nowByte[i]);
            }
        }

        System.out.println(bytes.size());


        byte[] finalByte = new byte[bytes.size()];

        for (int i = 0; i < bytes.size(); i++) {
            finalByte[i] = bytes.get(i);
        }

        fileInputStream.close();

        String data = base64Encoder.encode(finalByte);

        FileWriter fileWriter = new FileWriter(target);
        fileWriter.write(data);
        fileWriter.close();

        System.out.println(data);
    }

    public void fromBase64(File source, File target) throws Exception {
        String text = new String(Files.readAllBytes(source.toPath()));
        BASE64Decoder base64Decoder = new BASE64Decoder();
        byte[] bytes = base64Decoder.decodeBuffer(text);
        FileOutputStream fileOutputStream =
                new FileOutputStream(target);

        fileOutputStream.write(bytes);

        fileOutputStream.close();
    }


}