package ru.ezhov.test.jsoup;

import com.sun.xml.internal.ws.api.streaming.XMLStreamWriterFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;


public class Test {
    public static void main(String[] args) {
        String url = "https://jsehelper.blogspot.com/2016/01/blog-post_9.html";

        List<Note> notes = Arrays.asList(
                new Note("questions/ООП", "http://jsehelper.blogspot.com/2016/01/blog-post_9.html"),
                new Note("questions/JAVA CORE", "http://jsehelper.blogspot.com/2016/01/java-core-1.html"),
                new Note("questions/JAVACOLLECTION FRAMEWORKS", "http://jsehelper.blogspot.com/2016/01/java-collections-framework-1.html"),
                new Note("questions/JAVA 8", "http://jsehelper.blogspot.com/2016/05/java-8-1.html"),
                new Note("questions/ПОТОКИ ВВОДА-ВЫВОДА В JAVA", "http://jsehelper.blogspot.com/2016/02/java-1.html"),
                new Note("questions/MULTITHREADING", "http://jsehelper.blogspot.com/2016/01/multithreading-1.html")
        );

        notes.forEach(Test::parseAndSave);
    }

    private static void parseAndSave(Note note) {
        try {
            System.out.printf(">> start: %s\n", note.getName());
            File file = new File(note.getName() + ".xml");
            Document document = Jsoup.connect(note.getUrl()).get();
            Element element = document.body();
            Elements elements = element.getElementsByTag("span");

            XMLStreamWriter xmlStreamWriter = XMLStreamWriterFactory.create(new FileOutputStream(file));
            try {
                xmlStreamWriter.writeStartDocument();
                xmlStreamWriter.writeStartElement("questions");
                for (Element link : elements) {
                    String linkText = link.text();
//                    System.out.println(linkText);
                    if (!"".equals(linkText)) {
                        xmlStreamWriter.writeStartElement("question");
                        xmlStreamWriter.writeCharacters(linkText);
                        xmlStreamWriter.writeEndElement();
                    }
                }
                xmlStreamWriter.writeEndElement();
                xmlStreamWriter.writeEndDocument();
            } finally {
                if (xmlStreamWriter != null) {
                    xmlStreamWriter.close();
                }
            }
            System.out.printf(">>> end: %s\n", note.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


class Note {
    private String name;
    private String url;

    public Note(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
