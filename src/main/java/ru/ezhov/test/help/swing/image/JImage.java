package ru.ezhov.test.help.swing.image;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class JImage extends JComponent {
    Image img;

    public JImage() {
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(getImage(), 0, 0, getSize().width, getSize().height, null);
    }

    void setImage(String filePath) {
        try {
            this.img = ImageIO.read(new File(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Image getImage() {
        return this.img;
    }
}
