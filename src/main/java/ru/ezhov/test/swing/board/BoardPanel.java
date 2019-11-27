package ru.ezhov.test.swing.board;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BoardPanel {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
        {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Throwable ex) {
                //
            }

            JPanel panelAll = new JPanel(new BorderLayout());

            JPanel panelTop = new JPanel(new GridLayout(1, Day.values().length));
            JPanel panel = new JPanel(new GridLayout(5, 7));

            for (Day day : Day.values()) {
                JPanel p = new JPanel(new BorderLayout());
                JLabel label = new JLabel(day.description());
                label.setHorizontalAlignment(SwingConstants.CENTER);
                p.add(label, BorderLayout.CENTER);
                p.setBorder(BorderFactory.createLineBorder(Color.black));
                panelTop.add(p);
            }


            for (int i = 0; i < 35; i++) {
                JPanel p = new JPanel(new BorderLayout());
                JLabel label = new JLabel(i + "");
                label.setHorizontalAlignment(SwingConstants.CENTER);
                p.add(label, BorderLayout.CENTER);
                p.setBorder(BorderFactory.createLineBorder(Color.black));
                panel.add(p);

            }

            JToolBar toolBar = new JToolBar();
            toolBar.add(new AbstractAction() {

                {
                    putValue(Action.SHORT_DESCRIPTION, "PRINT");
                    putValue(Action.NAME, "PRINT");
                }

                @Override
                public void actionPerformed(ActionEvent e) {
                    BufferedImage bufferedImage = new BufferedImage(panelAll.getWidth(), panelAll.getHeight(), ColorSpace.TYPE_RGB);

                    Graphics2D graphics2D = bufferedImage.createGraphics();

                    panelAll.paint(graphics2D);


                    try {
                        File f = File.createTempFile("board", ".jpg");
                        ImageIO.write(bufferedImage, "jpg", f);

                        System.out.println(f);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            panelAll.add(panelTop, BorderLayout.NORTH);

            panelAll.setSize(3508, 2480);
            panelAll.setMaximumSize(new Dimension(3508, 2480));
            panelAll.setMinimumSize(new Dimension(3508, 2480));
            panelAll.setPreferredSize(new Dimension(3508, 2480));

            panelAll.add(panel, BorderLayout.CENTER);

            JFrame frame = new JFrame("_________");

            frame.setSize(600, 400);

            frame.add(toolBar, BorderLayout.NORTH);
            frame.add(new JScrollPane(panelAll), BorderLayout.CENTER);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}