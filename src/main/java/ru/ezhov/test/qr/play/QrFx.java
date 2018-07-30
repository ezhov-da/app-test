package ru.ezhov.test.qr.play;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

public class QrFx extends Application {
    private static final Logger LOG = Logger.getLogger(QrFx.class.getName());

    @Override
    public void start(Stage primaryStage) throws Exception {
        final Pane[] paneCanvas = {new Pane()};

        TextField textField = new TextField("default");
        Slider slider = new Slider(50, 500, 200);

        BorderPane borderPaneTop = new BorderPane();
        borderPaneTop.setTop(textField);
        borderPaneTop.setCenter(slider);

        BorderPane root = new BorderPane();
        root.setTop(borderPaneTop);
        root.setCenter(paneCanvas[0]);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setWidth(1000);
        primaryStage.setHeight(600);
        textField.setOnKeyTyped(e -> setQr(paneCanvas, textField, slider, root));
        slider.valueProperty().addListener((observable, oldValue, newValue) -> setQr(paneCanvas, textField, slider, root));
        primaryStage.show();
    }

    private void setQr(Pane[] paneCanvas, TextField textField, Slider slider, BorderPane root) {
        Platform.runLater(() -> {
            int val = (int) slider.getValue();
            try {
                root.getChildren().remove(paneCanvas[0]);
                paneCanvas[0] = new QrPane(textField.getText(), val, val);
                root.setCenter(paneCanvas[0]);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
    }
}

class QrPane extends Pane {
    private String text;
    private int w;
    private int h;

    public QrPane(String text, int w, int h) throws Exception {
        this.text = text;
        this.w = w;
        this.h = h;
        Canvas canvas = new Canvas(w, h);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        InputStream inputStream = new ByteArrayInputStream(getQRCodeImage(text, w, h));
        gc.drawImage(new Image(inputStream), 0, 0, w, h);
        getChildren().add(canvas);
    }

    private byte[] getQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        return pngOutputStream.toByteArray();
    }
}