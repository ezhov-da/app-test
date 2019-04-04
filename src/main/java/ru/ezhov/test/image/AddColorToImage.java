package ru.ezhov.test.image;

import net.sf.image4j.codec.ico.ICOEncoder;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class AddColorToImage {
    public static void main(String[] args) {
        //ICOEncoder.write(image, new File("laptop.ico"));

        SwingUtilities.invokeLater(() ->
        {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Throwable ex) {
                //
            }
            JFrame frame = new JFrame("_________");

            frame.setSize(700, 400);
            frame.add(new IconPanelGenerator());
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

    private static class IconPanelGenerator extends JPanel {
        private JPanel panelSelectedFile;
        private JPanel panelOriginal;
        private JPanel panelResult;
        private JPanel panelConfig;

        private java.util.List<Color> list = new ArrayList() {
            {
                add(Color.BLUE);
                add(Color.ORANGE);
                add(Color.RED);
                add(Color.BLACK);
                add(Color.GREEN);
                add(Color.CYAN);
                add(Color.MAGENTA);
                add(Color.PINK);
                add(Color.YELLOW);
            }
        };

        private JComboBox comboBoxColor = new JComboBox(list.toArray());

        private BufferedImage selectedOriginalImage;
        private BufferedImage newImage;

        public IconPanelGenerator() {
            comboBoxColor.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value != null) {
                        label.setBackground((Color) value);
                    }
                    return label;
                }
            });


            setLayout(new BorderLayout());

            panelSelectedFile = new JPanel(new BorderLayout());
            JButton buttonFileChooser = new JButton("Выбрать файл");
            buttonFileChooser.addActionListener(e -> {
                JFileChooser fileChooser = new JFileChooser();

                fileChooser.setSelectedFile(new File("D:\\programmer\\git\\app-test\\src\\main\\resources\\images\\global-icon.png"));
                int i = fileChooser.showOpenDialog(this);
                if (i == JFileChooser.APPROVE_OPTION) {
                    try {
                        File selectedFile = fileChooser.getSelectedFile();
                        try (FileInputStream fileInputStream = new FileInputStream(selectedFile)) {
                            selectedOriginalImage = ImageIO.read(fileInputStream);
                            SwingUtilities.invokeLater(() -> {
                                JLabel label = new JLabel(new ImageIcon(selectedOriginalImage));
                                panelOriginal.add(label, BorderLayout.CENTER);
                                panelOriginal.revalidate();
                                panelOriginal.repaint();

                                JSpinner spinnerX = new JSpinner(new SpinnerNumberModel(5, 1, selectedOriginalImage.getWidth(), 1));
                                JSpinner spinnerY = new JSpinner(new SpinnerNumberModel(5, 1, selectedOriginalImage.getHeight(), 1));
                                JSpinner spinnerWidth = new JSpinner(new SpinnerNumberModel(5, 1, selectedOriginalImage.getWidth(), 1));
                                JSpinner spinnerHeight = new JSpinner(new SpinnerNumberModel(5, 1, selectedOriginalImage.getHeight(), 1));

                                panelConfig.removeAll();

                                panelConfig.add(spinnerX);
                                panelConfig.add(spinnerY);
                                panelConfig.add(spinnerWidth);
                                panelConfig.add(spinnerHeight);
                                panelConfig.add(comboBoxColor);

                                JButton buttonShow = new JButton("Показать");
                                buttonShow.addActionListener(e12 -> {
                                    SwingUtilities.invokeLater(() -> {
                                        newImage = copyImage(selectedOriginalImage);
                                        Graphics2D graphics2D = newImage.createGraphics();
                                        graphics2D.setColor((Color) comboBoxColor.getSelectedItem());
                                        graphics2D.fillRect(
                                                Integer.valueOf(spinnerX.getValue().toString()),
                                                Integer.valueOf(spinnerY.getValue().toString()),
                                                Integer.valueOf(spinnerWidth.getValue().toString()),
                                                Integer.valueOf(spinnerHeight.getValue().toString())
                                        );

                                        panelResult.removeAll();
                                        panelResult.add(new JLabel(new ImageIcon(newImage)), BorderLayout.CENTER);
                                        panelResult.revalidate();
                                        panelResult.repaint();
                                    });
                                });
                                panelConfig.add(buttonShow);

                                JRadioButton radioButtonPng = new JRadioButton(".png");
                                radioButtonPng.setSelected(true);
                                JRadioButton radioButtonIco = new JRadioButton(".ico");
                                ButtonGroup buttonGroup = new ButtonGroup();
                                buttonGroup.add(radioButtonPng);
                                buttonGroup.add(radioButtonIco);
                                panelConfig.add(radioButtonPng);
                                panelConfig.add(radioButtonIco);
                                JButton buttonSave = new JButton("Сохранить");
                                buttonSave.addActionListener(e13 -> {
                                    JFileChooser chooser = new JFileChooser();
                                    int i1 = chooser.showSaveDialog(IconPanelGenerator.this);
                                    if (i1 == JFileChooser.APPROVE_OPTION) {
                                        File file = chooser.getSelectedFile();
                                        if (radioButtonPng.isSelected()) {
                                            try {
                                                ImageIO.write(newImage, "png", file);
                                            } catch (IOException e1) {
                                                e1.printStackTrace();
                                                JOptionPane.showMessageDialog(IconPanelGenerator.this, "Ошибка записи файла");
                                            }
                                        } else if (radioButtonIco.isSelected()) {
                                            try {
                                                ICOEncoder.write(newImage, file);
                                            } catch (IOException e1) {
                                                e1.printStackTrace();
                                                JOptionPane.showMessageDialog(IconPanelGenerator.this, "Ошибка записи файла");
                                            }
                                        }

                                    }
                                });
                                panelConfig.add(buttonSave);

                                panelConfig.add(Box.createVerticalStrut(9000));
                                panelConfig.revalidate();
                            });
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();

                    }
                }
            });
            panelSelectedFile.add(buttonFileChooser, BorderLayout.CENTER);

            panelOriginal = new JPanel(new BorderLayout());
            panelResult = new JPanel(new BorderLayout());
            panelConfig = new JPanel();
            panelConfig.setLayout(new BoxLayout(panelConfig, BoxLayout.Y_AXIS));

            add(panelSelectedFile, BorderLayout.NORTH);
            add(panelOriginal, BorderLayout.WEST);
            add(panelConfig, BorderLayout.CENTER);
            add(panelResult, BorderLayout.EAST);
        }

        private BufferedImage copyImage(BufferedImage coverImage) {
            ColorModel colorModel = coverImage.getColorModel();
            boolean isAlphaPremultiplied = coverImage.isAlphaPremultiplied();
            WritableRaster raster = coverImage.copyData(null);
            BufferedImage newImage = new BufferedImage(colorModel, raster,
                    isAlphaPremultiplied, null);
            return newImage;
        }
    }
}
