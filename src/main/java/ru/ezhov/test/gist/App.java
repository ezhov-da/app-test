package ru.ezhov.test.gist;

import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.GistFile;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.PageIterator;
import org.eclipse.egit.github.core.service.GistService;
import ru.ezhov.propertiesReader.Properties;
import ru.ezhov.propertiesReader.PropertiesFactory;

import java.io.InputStream;
import java.util.*;


public class App {
    public static void main(String[] args) {
        try {
            Properties<String, String> properties = PropertiesFactory.getPropertiesFromUserDirectory(".properties");
            GitHubClient gitHubClient = new GitHubClient();
            gitHubClient.setOAuth2Token(properties.getProperty("git.token")); //your token
//            Gist gist = new Gist().setDescription("Prints a string to standard out");
//            GistFile file = new GistFile().setContent("System.out.println(\"Hello World\");");
//            gist.setFiles(Collections.singletonMap("Hello.java", file));
//            gist = new GistService(gitHubClient).createGist(gist);

            PageIterator<Gist> gistPageIterator = new GistService(gitHubClient).pageGists("ezhov-da");

            List<Knowledge> knowledgeList = new ArrayList<>();

            while (gistPageIterator.hasNext()) {
                Collection<Gist> gistCollection = gistPageIterator.next();

                for (Gist gist1 : gistCollection) {
                    System.out.println("id: " + gist1.getId());
                    System.out.println("description: " + gist1.getDescription());
                    System.out.println("html url: " + gist1.getHtmlUrl());
                    System.out.println("url: " + gist1.getUrl());
                    System.out.println("is public: " + gist1.isPublic());
//                    readFiles(gist1);

                    Knowledge knowledge = new Knowledge(gist1.getDescription(), gist1.getHtmlUrl());
                    knowledge.setKnowledgeFiles(readFilesToList(gist1));
                    knowledgeList.add(knowledge);
                }
            }

            System.out.println(knowledgeList);

            List<KnowledgeTransform> knowledgeTransforms = new ArrayList<>();
            knowledgeList.forEach(k -> {
                List<KnowledgeTransform> transforms = new KnowledgeConverter().convert(k);
                knowledgeTransforms.addAll(transforms);
            });

            System.out.println(knowledgeTransforms);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void readFiles(Gist gist) throws Exception {
        Map<String, GistFile> stringFileMap = gist.getFiles();
        stringFileMap.forEach((s, f) -> {
            System.out.println("s: " + s);
            System.out.println("url: " + f.getRawUrl());
            System.out.println("name: " + f.getFilename());

//            HttpURLConnection urlConnection = null;
//            try {
//
//                urlConnection =
//                        (HttpURLConnection) new URL(f.getRawUrl()).openConnection();
//
//                String result = getStringFromInputStream(urlConnection.getInputStream());
//                System.out.println(result);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            } finally {
//                if (urlConnection != null) {
//                    urlConnection.disconnect();
//                }
//            }
        });
    }

    private static List<KnowledgeFile> readFilesToList(Gist gist) throws Exception {

        List<KnowledgeFile> knowledgeFiles = new ArrayList<>();

        Map<String, GistFile> stringFileMap = gist.getFiles();
        stringFileMap.forEach((s, f) -> {
            System.out.println("s: " + s);
            System.out.println("url: " + f.getRawUrl());
            System.out.println("name: " + f.getFilename());
            knowledgeFiles.add(new KnowledgeFile(f.getFilename(), f.getRawUrl()));

        });

        return knowledgeFiles;
    }

    // convert InputStream to String

    private static String getStringFromInputStream(InputStream is) {
        StringBuilder stringBuilder = new StringBuilder();
        Scanner scanner = new Scanner(is, "UTF-8");
        while (scanner.hasNextLine()) {
            stringBuilder.append(scanner.nextLine());
            stringBuilder.append("\n");
        }

        return stringBuilder.toString().trim();
    }
}

class Knowledge {
    private String description;
    private String url;
    private List<KnowledgeFile> knowledgeFiles = new ArrayList<>();

    public Knowledge(String description, String url) {
        this.description = description;
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<KnowledgeFile> getKnowledgeFiles() {
        return knowledgeFiles;
    }

    public void setKnowledgeFiles(List<KnowledgeFile> knowledgeFiles) {
        if (knowledgeFiles == null)
            throw new NullPointerException("List must be not null");
        this.knowledgeFiles = knowledgeFiles;
    }

    @Override
    public String toString() {
        return "Knowledge{" +
                "description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", knowledgeFiles=" + knowledgeFiles +
                '}';
    }
}

class KnowledgeFile {
    private String name;
    private String rawUrl;

    public KnowledgeFile(String name, String rawUrl) {
        this.name = name;
        this.rawUrl = rawUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRawUrl() {
        return rawUrl;
    }

    public void setRawUrl(String rawUrl) {
        this.rawUrl = rawUrl;
    }

    @Override
    public String toString() {
        return "KnowledgeFile{" +
                "name='" + name + '\'' +
                ", rawUrl='" + rawUrl + '\'' +
                '}';
    }
}

class KnowledgeConverter {
    public List<KnowledgeTransform> convert(Knowledge knowledge) {
        List<KnowledgeTransform> knowledgeTransforms = new ArrayList<>();

        List<KnowledgeFile> knowledgeFiles = knowledge.getKnowledgeFiles();
        if (!knowledgeFiles.isEmpty()) {
            knowledgeFiles.forEach(kf -> knowledgeTransforms.add(
                    new KnowledgeTransform(
                            kf.getName(),
                            kf.getRawUrl(),
                            knowledge.getDescription(),
                            knowledge.getUrl()
                    )
            ));

        }
        return knowledgeTransforms;
    }
}

class KnowledgeTransform {
    private String name;
    private String rawUrl;
    private String description;
    private String url;

    public KnowledgeTransform(String name, String rawUrl, String description, String url) {
        this.name = name;
        this.rawUrl = rawUrl;
        this.description = description;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRawUrl() {
        return rawUrl;
    }

    public void setRawUrl(String rawUrl) {
        this.rawUrl = rawUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "KnowledgeTransform{" +
                "name='" + name + '\'' +
                ", rawUrl='" + rawUrl + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}