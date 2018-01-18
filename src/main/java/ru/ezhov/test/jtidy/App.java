package ru.ezhov.test.jtidy;

import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;

import java.io.IOException;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;

//http://jtidy.sourceforge.net/howto.html
//подключить либу вручную через F4
public class App {
    public static void main(String[] args) {
        try {
            HttpURLConnection httpURLConnection =
                    (HttpURLConnection) new URL("https://www.goodfon.ru/").openConnection();

            Tidy tidy = new Tidy();
            Document document = tidy.parseDOM(httpURLConnection.getInputStream(), System.out);
            tidy.pprint(document, System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
