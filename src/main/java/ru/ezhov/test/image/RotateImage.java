package ru.ezhov.test.image;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

//Original: 2 771 байт / 303 X 188
//ACDSee:   2 873 байт / 188 X 303
//Windows:  2 904 байт / 188 X 303
//Java:     3 285 байт / 188 X 303

public class RotateImage {

    static JPanel currentRotatePanel = null;
    static BufferedImage current = null;
    static DefaultListModel<String> defaultListModel = new DefaultListModel();

    public static void main(String[] args) throws IOException {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Throwable ex) {
                //
            }
            JList<String> jList = new JList(defaultListModel);
            jList.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        String value = jList.getSelectedValue();
                        if (value != null) {
                            try {
                                Desktop.getDesktop().open(new File(value));
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }
            });

            JFrame frame = new JFrame("_________");
            JPanel panelRotate = new JPanel();
            JButton buttonRotate = new JButton("Повернуть");
            buttonRotate.addActionListener(e -> {
                SwingUtilities.invokeLater(() -> {
                    if (current == null) {
                        try {
                            current = getBufferedImageSource("tt-test.png");
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    current = rotate(current);
                    try {
                        frame.remove(currentRotatePanel);
                        currentRotatePanel = rotatePanel(current);
                        frame.add(currentRotatePanel, BorderLayout.CENTER);
                        frame.revalidate();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                });
            });


            panelRotate.add(buttonRotate);
            frame.add(panelRotate, BorderLayout.NORTH);
            frame.add(new JScrollPane(jList), BorderLayout.WEST);
            try {
                current = getBufferedImageSource("tt-test.png");
                currentRotatePanel = rotatePanel(current);
                frame.add(currentRotatePanel, BorderLayout.CENTER);
            } catch (IOException e) {
                e.printStackTrace();
            }

            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });


    }

    private static BufferedImage getBufferedImageSource(String path) throws IOException {
        return ImageIO.read(new File(path));
    }

    private static JPanel rotatePanel(BufferedImage bufferedImage) throws IOException {
        JPanel panelInfo = new JPanel();
        JLabel labelWidth = new JLabel("Ширина: " + bufferedImage.getWidth());
        JLabel labelHeight = new JLabel("Высота: " + bufferedImage.getHeight());
        File file = File.createTempFile("img", ".png");
        System.out.println("file: " + file.getAbsolutePath());
        SwingUtilities.invokeLater(() -> defaultListModel.addElement(file.getAbsolutePath()));
        ImageIO.write(bufferedImage, "png", file);
        JLabel labelSize = new JLabel("Размер: " + file.length());
        panelInfo.add(labelWidth);
        panelInfo.add(labelHeight);
        panelInfo.add(labelSize);

        JPanel panelDraw = new JPanel(null) {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                Graphics2D g2d = (Graphics2D) g;
                int w = getWidth();
                int h = getHeight();
                g2d.drawImage(
                        bufferedImage, w / 2 - bufferedImage.getWidth() / 2,
                        h / 2 - bufferedImage.getHeight() / 2,
                        null
                );
            }
        };

        JPanel panelRotate = new JPanel(new BorderLayout());
        panelRotate.add(panelInfo, BorderLayout.NORTH);
        panelRotate.add(panelDraw, BorderLayout.CENTER);
        return panelRotate;
    }

    private static BufferedImage rotate(BufferedImage imageOriginal) {
        AffineTransform tx = new AffineTransform();
        // last, width = height and height = width :)
        tx.translate(imageOriginal.getHeight() / 2, imageOriginal.getWidth() / 2);
        tx.rotate(Math.PI / 2);
        // first - center image at the origin so rotate works OK
        tx.translate(-imageOriginal.getWidth() / 2, -imageOriginal.getHeight() / 2);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        // new destination image where height = width and width = height.
        BufferedImage newImage = new BufferedImage(
                imageOriginal.getHeight(),
                imageOriginal.getWidth(),
                imageOriginal.getType()
        );
        op.filter(imageOriginal, newImage);
        return newImage;
    }
}
