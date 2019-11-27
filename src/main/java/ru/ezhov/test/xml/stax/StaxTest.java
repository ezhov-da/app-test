package ru.ezhov.test.xml.stax;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.InputStream;

public class StaxTest {
    public static void main(String[] args) {

        try (InputStream inputStream = StaxTest.class.getResourceAsStream("/xml/stax.xml");) {
            XMLStreamReader r = XMLInputFactory.newInstance().createXMLStreamReader(inputStream);
            int event = r.getEventType();

            while (true) {
                switch (event) {
                    case XMLStreamConstants.START_DOCUMENT:
                        // add cases for each event of interest
                        // ...
                }

                if (!r.hasNext())
                    break;

                event = r.next();
            }
            r.close();
        } catch (IOException | XMLStreamException e) {
            e.printStackTrace();
        }
    }
}
