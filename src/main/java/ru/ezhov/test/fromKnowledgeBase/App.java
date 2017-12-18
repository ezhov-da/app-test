package ru.ezhov.test.fromKnowledgeBase;

import ru.ezhov.propertiesReader.Properties;
import ru.ezhov.propertiesReader.PropertiesFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class App {
    public static void main(String[] args) {
        App app = new App();
        try {
            Properties<String, String> stringProperties =
                    PropertiesFactory.getPropertiesFromUserDirectory("config-properties-unload-kb.properties");
            app.unloadFromBase(stringProperties, new ProcessDataImpl());
//            app.renameFileFromTwoDash(stringProperties);
//            app.replaceLtGt(stringProperties);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void renameFileFromTwoDash(Properties<String, String> stringProperties) {
        String path = stringProperties.getProperty("path.to.save.files");

        File file = new File(path);
        File[] files = file.listFiles();

        for (File f : files) {
            if (f.getName().contains("--")) {
                f.renameTo(
                        new File(
                                path,
                                f
                                        .getName()
                                        .replaceAll("--", "-")));
            }
        }

    }

    private void replaceLtGt(Properties<String, String> stringProperties) {
        String path = stringProperties.getProperty("path.to.save.files");

        File file = new File(path);
        File[] files = file.listFiles();

        for (File f : files) {

            try {
                String s =
                        new String(Files.readAllBytes(f.toPath()), "UTF-8");
//                String replaceS = s.replaceAll("&gt;", ">").replaceAll("&lt;", "<");
                String replaceS = s.replaceAll("\\[code:\\].*\\[:code\\]", "").replaceAll("\\[\\/code\\]", "");
                Files.write(f.toPath(), replaceS.getBytes(), StandardOpenOption.WRITE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void unloadFromBase(Properties<String, String> stringProperties, ProcessData processData) {
        try {
            String classDriver = stringProperties.getProperty("class.driver");
            String url = stringProperties.getProperty("url");
            String login = stringProperties.getProperty("login");
            String pass = stringProperties.getProperty("password");
            String query = stringProperties.getProperty("query");
            String pathToSaveFiles = stringProperties.getProperty("path.to.save.files");

            System.out.println(query);

            Class.forName(classDriver);

            try (Connection connection = DriverManager.getConnection(url, login, pass);) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        while (resultSet.next()) {

                            String name = resultSet.getString("name");
                            String link = resultSet.getString("link");
                            String text = resultSet.getString("text");

//                            System.out.println(name);
//                            System.out.println(link);
//                            System.out.println(text);

                            processData.process(name, link, text);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveToFile(String pathToSave, String name, String link, String text) {
        String nameReplace = name.replaceAll("- ", "-");
        nameReplace = nameReplace.replaceAll(" ", "-");
        nameReplace = nameReplace.toLowerCase();

        File file = new File(pathToSave, nameReplace + ".kb");

        StringBuilder stringBuilder = new StringBuilder(500 + text.length());
        if (link != null && !"".equals(link)) {
            stringBuilder.append("#kb:l ");
            stringBuilder.append(link);
            stringBuilder.append("\n\n");
        }
        stringBuilder.append(text);

        try (FileWriter fileWriter = new FileWriter(file);) {
            fileWriter.write(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
