package ru.ezhov.test.help;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class FotoLable extends JFrame {
    Image image;

    public FotoLable() throws HeadlessException {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLayout(new GridBagLayout());
        JLabel imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(300, 400));
        imageLabel.setBounds(0, 0, 300, 400);

        this.add(imageLabel, new GridBagConstraints(0, 0, 1, 17, 1, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 0, 0));
        JButton jButtonFileImage = new JButton("Выбрать фото");
        this.add(jButtonFileImage, new GridBagConstraints(GridBagConstraints.RELATIVE, 0, 1, 1, 1, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 0, 0));
        jButtonFileImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileopen = new JFileChooser();
                int ret = fileopen.showDialog(null, "Открыть файл");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileopen.getSelectedFile();
                    try {
                        image = ImageIO.read(file);
                        imageLabel.setIcon(new ImageIcon(image));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                }
            }
        });
    }

    public static void main(String[] args) {
        FotoLable photoLabel = new FotoLable();
        photoLabel.setVisible(true);
    }
}