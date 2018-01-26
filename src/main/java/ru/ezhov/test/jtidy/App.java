package ru.ezhov.test.jtidy;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;

import javax.xml.xpath.*;
import java.io.IOException;
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

            tidy.setXHTML(true);


            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();
            try {
                XPathExpression xPathExpression = xPath.compile("//text()");
                NodeList nodeList = (NodeList) xPathExpression.evaluate(document, XPathConstants.NODESET);

                for (int i = 0; i < nodeList.getLength(); i++){
                    System.out.println(nodeList.item(i).getNodeName());
                }

            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
