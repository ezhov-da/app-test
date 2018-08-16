package ru.ezhov.test.gist;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;

import java.io.FileReader;
import java.util.List;
import java.util.Properties;

public class GitHubRepoList {
    public static void main(String[] args) {
        Properties properties = new Properties();
        try {
            properties.load(
                    new FileReader("D:\\programmer\\git-private\\config\\common\\common.properties")
            );
            GitHubClient gitHubClient = new GitHubClient();
            gitHubClient.setOAuth2Token(properties.getProperty("git.token.repositories")); //your token
            RepositoryService gitHubService = new RepositoryService(gitHubClient);
            List<Repository> repositories = gitHubService.getRepositories("ezhov-da");
            for (Repository repository : repositories) {
                String repoName = repository.getName();
                String repoHtmlUrl = repository.getHtmlUrl();
                System.out.printf("%s;%s\n", repoName, repoHtmlUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
