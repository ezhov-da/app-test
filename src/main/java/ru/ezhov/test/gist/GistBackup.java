package ru.ezhov.test.gist;

import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.GistFile;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.GistService;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

public class GistBackup {
    private static final Logger LOG = Logger.getLogger(GistBackup.class.getName());

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        String gistToken = System.getProperty("git.token");
        String username = "ezhov-da";
        String bkpFolder = System.getProperty("D:/programmer");
        File file = new File(
                bkpFolder,
                String.format(
                        "gist_%s_%s.bkp.xml",
                        username,
                        new SimpleDateFormat("yyyy-MM-ddHHmmss").format(new Date())
                )
        );
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            XMLStreamWriter xsw = XMLOutputFactory.newFactory().createXMLStreamWriter(fileOutputStream);
            xsw.writeStartDocument();
            System.out.println("начато создание бэкапа");
            readGists(gistToken, username, (name, content) -> {
                xsw.writeStartElement("gist");
                xsw.writeStartElement("name");
                xsw.writeCharacters(name);
                xsw.writeEndElement();
                xsw.writeStartElement("content");
                xsw.writeCData(content);
                xsw.writeEndElement();
                xsw.writeEndElement();
            });
            xsw.writeEndElement();
            xsw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("бэкап gist создан. файл '" + file.getAbsolutePath() + "'. время создания бэкапа '" + (endTime - startTime) + " ms'");
    }

    private static String getContent(URL url) throws Exception {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        StringBuilder stringBuilder = new StringBuilder();
        try (Scanner scanner = new Scanner(httpURLConnection.getInputStream(), "UTF-8")) {
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine()).append("\n");
            }
            return stringBuilder.toString();
        }
    }

    private static void readGists(String token, String username, GistReader gistReader) throws Exception {
        GitHubClient gitHubClient = new GitHubClient();
        gitHubClient.setOAuth2Token(token);
        GistService gistService = new GistService(gitHubClient);
        List<Gist> gistList = gistService.getGists(username);
        gistList.forEach(g -> {
            Map<String, GistFile> fileMap = g.getFiles();
            for (Map.Entry<String, GistFile> fileEntry : fileMap.entrySet()) {
                try {
                    String name = fileEntry.getKey();
                    String text = getContent(new URL(fileEntry.getValue().getRawUrl()));
                    gistReader.read(name, text);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

interface GistReader {
    void read(String name, String content) throws Exception;
}
