package ru.ezhov.test.gist;

import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.GistFile;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.GistService;

import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

public class PlayWithGist {
    public static void main(String[] args) {
        Properties properties = new Properties();
        try {
            properties.load(
                    new FileReader("E:\\programmer\\git-private\\config\\common\\common.properties")
            );
            GitHubClient gitHubClient = new GitHubClient();
            gitHubClient.setOAuth2Token(properties.getProperty("git.token")); //your token
            GistService gistService = new GistService(gitHubClient);
            try {
                List<Gist> gists = gistService.getGists("ezhov-da");
                for (Gist gist : gists) {
                    Map<String, GistFile> map = gist.getFiles();
                    for (Map.Entry<String, GistFile> fileEntry : map.entrySet()) {
                        String conetnt = getContentFromUrl(new URL(fileEntry.getValue().getRawUrl()));
                        if (conetnt.contains("&gt;")) {
                            System.out.println(fileEntry.getKey());
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getContentFromUrl(URL url) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        if (httpURLConnection.getResponseCode() == 200) {
            try (Scanner scanner = new Scanner(httpURLConnection.getInputStream())) {
                while (scanner.hasNextLine()) {
                    stringBuilder.append(scanner.nextLine()).append("\n");
                }
            }
        }
        httpURLConnection.disconnect();
        return stringBuilder.toString();
    }
}
