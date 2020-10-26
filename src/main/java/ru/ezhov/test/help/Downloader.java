package ru.ezhov.test.help;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


public class Downloader extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        TextField textFieldLink = new TextField();
        textFieldLink.setPromptText("Ссылка");
        textFieldLink.setText("https://github.com/google/guava/archive/master.zip");
        BorderPane borderPaneSelect = new BorderPane();
        TextField textFieldFile = new TextField();
        textFieldFile.setText(System.getProperty("user.home") + "/master.zip");
        textFieldFile.setPromptText("Файл для сохранения");
        Button buttonSelectFile = new Button("Выберите файл для сохранения");
        buttonSelectFile.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showSaveDialog(primaryStage);
            if (file != null) {
                textFieldFile.setText(file.getAbsolutePath());
            }
        });
        borderPaneSelect.setCenter(textFieldFile);
        borderPaneSelect.setRight(buttonSelectFile);
        VBox vBox = new VBox(textFieldLink, borderPaneSelect);

        Button buttonLoad = new Button("Загрузить");
        buttonLoad.setOnAction(event -> {
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(textFieldLink.getText()).openConnection();
                try (BufferedInputStream bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());) {
                    try (FileOutputStream fileOutputStream = new FileOutputStream(textFieldFile.getText())) {
                        int data;
                        while ((data = bufferedInputStream.read()) != -1) {
                            fileOutputStream.write(data);
                        }
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Файл загружен");
                        alert.show();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        BorderPane borderPaneLoad = new BorderPane();
        borderPaneLoad.setBottom(buttonLoad);

        BorderPane borderPaneCommon = new BorderPane();
        borderPaneCommon.setCenter(vBox);
        borderPaneCommon.setBottom(borderPaneLoad);
        Scene scene = new Scene(borderPaneCommon);
        primaryStage.setTitle("Загрузка файла");
        primaryStage.setScene(scene);
        primaryStage.setWidth(800);
        primaryStage.show();
    }
}
