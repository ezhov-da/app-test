package ru.ezhov.test.gist;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;
import ru.ezhov.propertiesReader.Properties;
import ru.ezhov.propertiesReader.PropertiesFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class CloneRepoToComputer {
    public static void main(String[] args) {
        String pathToFolderRepo = "E:/programmer/git";

        Properties<String, String> properties = PropertiesFactory.getPropertiesFromUserDirectory("common.properties");
        GitHubClient gitHubClient = new GitHubClient();
        String token = properties.getProperty("git.token.repositories");
        System.out.println("token" + token);
        gitHubClient.setOAuth2Token(token);
        RepositoryService gitHubService = new RepositoryService(gitHubClient);

        try {
            List<Repository> repositoryList = gitHubService.getRepositories();
            repositoryList.forEach(repo -> {
                String cloneUrl = repo.getCloneUrl();
                String name = repo.getName();
                System.out.println(name + " : " + cloneUrl);

                File file = new File(pathToFolderRepo + "/" + name);
                if (file.exists()) {
                    System.out.println("\tExist: " + file.getAbsoluteFile());
                } else {
                    Runtime runtime = Runtime.getRuntime();
                    try {
                        String command = "cmd /c cd /D " + pathToFolderRepo + " && git clone " + cloneUrl;
                        Process process = runtime.exec(command);
                        try (Scanner scanner = new Scanner(process.getInputStream())) {
                            while (scanner.hasNextLine()) {
                                System.out.println("\t\t" + scanner.nextLine());
                            }
                        }

                        System.out.println("\tClone: " + name);
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Oooops...");
                    }
                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
