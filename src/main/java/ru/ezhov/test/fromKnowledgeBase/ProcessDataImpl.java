//package ru.ezhov.test.fromKnowledgeBase;
//
//import org.eclipse.egit.github.core.Gist;
//import org.eclipse.egit.github.core.GistFile;
//import org.eclipse.egit.github.core.client.GitHubClient;
//import org.eclipse.egit.github.core.service.GistService;
//import ru.ezhov.propertiesReader.Properties;
//import ru.ezhov.propertiesReader.PropertiesFactory;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class ProcessDataImpl implements ProcessData {
//    private Properties<String, String> properties = PropertiesFactory.getPropertiesFromUserDirectory(".properties");
//    private GitHubClient gitHubClient = new GitHubClient();
//    private GistService gistService;
//    private List<Gist> gistList = new ArrayList<>();
//
//    public ProcessDataImpl() {
//        gitHubClient.setOAuth2Token(properties.getProperty("git.token")); //your token
//        gistService = new GistService(gitHubClient);
//        try {
//            gistList = gistService.getGists("ezhov-da");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void process(String name, String link, String text) {
//        if (gistList.isEmpty()) {
//            throw new NullPointerException("fuck");
//        }
//        System.out.println("process: " + name);
//        Gist gist = new Gist().setDescription(name.replace('-', ' '));
//        GistFile file = new GistFile().setContent(link + "\n\n" + text);
//        gist.setFiles(Collections.singletonMap(name, file));
//        gist.setPublic(true);
//        try {
//            List<Gist> gistListResult = gistList
//                    .stream().filter(
//                            g -> g.getFiles().containsKey(name)).collect(Collectors.toList()
//                    );
//
//            if (gistListResult.isEmpty()) {
//                gistService.createGist(gist);
//                System.out.println("\t -> added");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
