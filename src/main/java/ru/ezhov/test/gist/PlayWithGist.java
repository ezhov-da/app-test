package ru.ezhov.test.gist;

import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.GistFile;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.GistService;

import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
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
            List<String> nameForReplace = Arrays.asList(
                    "-xml-cdata.xml",
                    "-sql-mssql-логирование",
                    "-js-отправка-form-через-ajax",
                    "-js-вставка-текста-в-позицию-курсора",
                    "-java-шаблонизатор-jsp-sitemesh",
                    "-java-фильтр-для-сервлета",
                    "-java-tomcat-mssql-настройка-подключения",
                    "-java-spring-базовые-зависимости-maven-spring",
                    "-java-sl4j",
                    "-java-persistence-api-cache",
                    "-java-maven-сборка-проекта-с-библиотеками-в-отдельную-папку",
                    "-java-maven-сборка-проекта-с-библиотеками-в-один-jar",
                    "-java-maven-сборка-проекта-для-сервиса-приложений",
                    "-java-maven-плагины-(plugins)",
                    "-java-maven-плагин-ant",
                    "-java-maven-setting-source-target",
                    "-java-maven-properties-from-file",
                    "-java-log4j",
                    "-java-jpa-persistance.xml-h2db-inmemory",
                    "-java-jetty",
                    "-java-h2db-jdbc-driver",
                    "-java-ee7-api-maven-all",
                    "-java-ant-создание-exe-через-launch4j-netbeans",
                    "-java-ant-сборка-проекта-в-один-jar-через-netbeans",
                    "-java-ant-сборка-проекта-в-netbeans-для-сервиса-приложений",
                    "-java-ant-настройки-для-netbeans",
                    "-html-таблица-bootstrap",
                    "-html-переадресация-в-html",
                    "-excel-сделать-значения-как-текст",
                    "-common-пробрасывание-портов-на-домашнем-роутере"
            );


            GitHubClient gitHubClient = new GitHubClient();
            gitHubClient.setOAuth2Token(properties.getProperty("git.token")); //your token
            GistService gistService = new GistService(gitHubClient);
            try {
                List<Gist> gists = gistService.getGists("ezhov-da");
                for (Gist gist : gists) {
                    Map<String, GistFile> map = gist.getFiles();
                    for (Map.Entry<String, GistFile> fileEntry : map.entrySet()) {
                        String content = getContentFromUrl(new URL(fileEntry.getValue().getRawUrl()));
                        if (content.contains("&gt;")) {
                            System.out.println(fileEntry.getKey());
                        }

                        if (nameForReplace.contains(fileEntry.getKey())) {
                            String contentAfterReplace = content
                                    .replaceAll("&lt;", "<")
                                    .replaceAll("&gt;", ">");
                            GistFile file = new GistFile();
                            file.setContent(contentAfterReplace);
                            file.setFilename(fileEntry.getKey());

                            gist.setFiles(Collections.singletonMap(fileEntry.getKey(), file));
                            gistService.updateGist(gist);
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
