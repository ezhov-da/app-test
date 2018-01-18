package ru.ezhov.test.xmlUnpack;

import java.io.InputStream;

public class App {
    public static void main(String[] args) {
//        Directory directory = new Directory("test", null);
//        directory.addFile(new File("testFile", directory.getName()));
//        directory.addDirectory(new Directory("test dir", directory.getName()));
//
//        StringWriter stringWriter = new StringWriter();
//        JAXB.marshal(directory, stringWriter);
//        String s = stringWriter.toString();
//        try {
//            stringWriter.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(s);

        try (InputStream inputStream =
                     App.class.getResourceAsStream("/server/unpack.xml")) {

            UnpackJarResources unpackJarResources =
                    new UnpackJarResources(inputStream, ".");

            unpackJarResources.createAndCopy();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
