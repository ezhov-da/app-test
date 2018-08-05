package ru.ezhov.test.help;

        import org.xml.sax.InputSource;

        import javax.xml.xpath.XPath;
        import javax.xml.xpath.XPathFactory;
        import java.io.FileReader;

        public class XPathTest {
            public static void main(String[] args) {
                try (FileReader fileReader = new FileReader("D:\\programmer\\application.xml")) {
                    XPath xPath = XPathFactory.newInstance().newXPath();
                    String data =
                            xPath.evaluate(
                                    "/Configuration/Other[@adobeCode='V7{}Photoshop-19-Win-GM']/Data[@key='TrialSerialNumber']",
                                    new InputSource(fileReader)
                            );
                    System.out.println(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
