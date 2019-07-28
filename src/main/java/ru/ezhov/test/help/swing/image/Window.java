package ru.ezhov.test.help.swing.image;

import javax.swing.*;

public class Window extends JFrame {
    public Window() {
        setSize(1000,500);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JImage img = new JImage();
        img.setLayout(null);
        img.setSize(100, 100);
        img.setLocation(100,100);
        img.setImage("pdf-image.png");
        img.setVisible(true);

        add(img);
        setVisible(true);
    }
}
