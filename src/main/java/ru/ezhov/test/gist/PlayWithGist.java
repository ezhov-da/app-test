package ru.ezhov.test.gist;

import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.GistService;
import ru.ezhov.propertiesReader.Properties;
import ru.ezhov.propertiesReader.PropertiesFactory;

public class PlayWithGist {
    public static void main(String[] args) {
        Properties<String, String> properties = PropertiesFactory.getPropertiesFromUserDirectory("common.properties");
        GitHubClient gitHubClient = new GitHubClient();
        gitHubClient.setOAuth2Token(properties.getProperty("git.token")); //your token
        GistService gistService = new GistService(gitHubClient);
    }
}
