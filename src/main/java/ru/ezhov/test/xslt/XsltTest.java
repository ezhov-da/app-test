package ru.ezhov.test.xslt;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.StringWriter;

public class XsltTest {
    public static void main(String[] args) {
        DocumentBuilder builder = null;
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(XsltTest.class.getResourceAsStream("/xslt/article.xml"));
            StreamSource stylesource = new StreamSource(XsltTest.class.getResourceAsStream("/xslt/article1a.xsl"));
            Transformer transformer = TransformerFactory.newInstance().newTransformer(stylesource);
            StringWriter stringWriter = new StringWriter();
            transformer.transform(new DOMSource(document), new StreamResult(stringWriter));
            String text = stringWriter.toString();
            stringWriter.close();
            System.out.println(stringWriter);
            File file = new File(System.getProperty("user.home") + "/test-xslt.html");
            System.out.println(file.getAbsolutePath());
            try (FileOutputStream fileOutputStream = new FileOutputStream(file, false)) {
                fileOutputStream.write(text.getBytes());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
