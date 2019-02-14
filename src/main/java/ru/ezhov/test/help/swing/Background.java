package ru.ezhov.test.help.swing;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Background extends JFrame {

    private static final int width = 1024;
    private static final int heigth = 768;

    public Background() throws IOException {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(width, heigth));
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().add(new BackgroundImage("/test.jpg"));
        setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        new Background();
    }
}

class BackgroundImage extends JPanel {

    private Image background;

    public BackgroundImage(String fileName) {
        try {
            background = ImageIO.read(getClass().getResourceAsStream(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        graphics.drawImage(background, 0, 0, this);
    }
}