package ru.ezhov.test.gist;

import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.GistFile;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.GistService;

import java.io.FileReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

public class GistWithWord {
    public static void main(String[] args) {
        String source = "Test GIST: [code:]xml[:code]<![CDATA[....]]>[/code]";
        System.out.println(removeCodeTag(source));
        gist();
    }

    private static void gist() {
        Properties properties = new Properties();
        try {
            properties.load(
                    new FileReader("D:\\programmer\\git-private\\config\\common\\common.properties")
            );
            GitHubClient gitHubClient = new GitHubClient();
            gitHubClient.setOAuth2Token(properties.getProperty("git.token.repositories")); //your token
            GistService gistService = new GistService(gitHubClient);
            List<Gist> gistList = gistService.getGists("ezhov-da");
            gistList.forEach(g -> {
                Map<String, GistFile> fileMap = g.getFiles();
                for (Map.Entry<String, GistFile> fileEntry : fileMap.entrySet()) {
                    try {
                        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(fileEntry.getValue().getRawUrl()).openConnection();
                        StringBuilder stringBuilder = new StringBuilder();
                        try (Scanner scanner = new Scanner(httpURLConnection.getInputStream(), "UTF-8")) {
                            while (scanner.hasNextLine()) {
                                stringBuilder.append(scanner.nextLine()).append("\n");
                            }
                        }
                        String text = stringBuilder.toString();
                        if (text.toLowerCase().contains("[code:]")) {
                            fileEntry.getValue().setContent(removeCodeTag(text));
                            //TODO: ХЗ, как обновить...програмно
                            System.out.printf("%s\t%s\n", fileEntry.getKey(), g.getHtmlUrl());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static String removeCodeTag(String source) {
        return
                source
                        .replaceAll("\\[code:\\]\\w+\\[:code\\]", "")
                        .replaceAll("\\[/code\\]", "");
    }
}
