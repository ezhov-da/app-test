package ru.ezhov.test.pixel;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Throwable ex) {
                //
            }
            JFrame frame = new JFrame("Pixel");

            RightPanel rightPanel = new RightPanel();

            LeftPanel leftPanel = new LeftPanel(rightPanel);

            JSplitPane splitPane = new JSplitPane();
            splitPane.setLeftComponent(leftPanel);
            splitPane.setRightComponent(rightPanel);

            splitPane.setDividerLocation(0.5);

            frame.add(splitPane, BorderLayout.CENTER);

            frame.setSize(1000, 600);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }


    private static class LeftPanel extends JPanel {
        private File selectedFile = null;
        private Pixeler pixeler;

        public LeftPanel(Pixeler pixeler) {
            this.pixeler = pixeler;

            setLayout(new BorderLayout());

            ImagePanel imagePanel = new ImagePanel();

            JButton open = new JButton("Open");

            open.addActionListener(e -> {
                JFileChooser fileChooser = new JFileChooser();
                int i = fileChooser.showOpenDialog(this);
                if (i == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    selectedFile = file;
                    try {
                        imagePanel.setBufferedImage(ImageIO.read(file));

                        LeftPanel.this.repaint();
                        LeftPanel.this.revalidate();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });

            add(open, BorderLayout.NORTH);


            add(imagePanel, BorderLayout.CENTER);


            JSpinner spinner = new JSpinner(new SpinnerNumberModel(20, 1, 500, 5));

            JPanel panel = new JPanel(new BorderLayout());
            panel.add(spinner, BorderLayout.CENTER);

            JButton doButton = new JButton("Create");
            panel.add(doButton, BorderLayout.EAST);

            doButton.addActionListener(e -> {
                if (selectedFile != null) {
                    pixeler.toPixel(selectedFile, (Integer) spinner.getModel().getValue());
                }
            });

            add(panel, BorderLayout.SOUTH);
        }
    }

    private interface Pixeler {
        void toPixel(File file, int pixelSize);
    }

    private static class RightPanel extends JPanel implements Pixeler {
        private ImageLinePanel imageLinePanel = new ImageLinePanel();

        public RightPanel() {
            setLayout(new BorderLayout());

            add(imageLinePanel, BorderLayout.CENTER);

            JPanel panel = new JPanel();

            JCheckBox checkBoxImage = new JCheckBox("Paint image");

            checkBoxImage.addActionListener(e -> {
                imageLinePanel.setPaintImage(checkBoxImage.isSelected());
            });

            JCheckBox checkBox = new JCheckBox("Paint numbers");
            checkBox.addActionListener(e -> {
                imageLinePanel.setPaintNumbers(checkBox.isSelected());
            });

            JButton saveImage = new JButton("Save image");
            saveImage.addActionListener(e -> {
                try {
                    final Optional<File> file = imageLinePanel.saveImage();
                    if (file.isPresent()) {
                        Desktop.getDesktop().open(file.get());
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });

            panel.add(checkBoxImage);
            panel.add(checkBox);
            panel.add(saveImage);

            add(panel, BorderLayout.SOUTH);
        }

        @Override
        public void toPixel(File file, int pixelSize) {
            PixelService pixelService = new PixelService();
            try {
                final BufferedImage bufferedImage = pixelService.toPixel(file, pixelSize);
                pixelService.writeAsJpg(bufferedImage, "pixel.jpg");

                imageLinePanel.setBufferedImage(bufferedImage, pixelSize);

                this.revalidate();
                this.repaint();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static class ImagePanel extends JPanel {

        private BufferedImage bufferedImage = null;

        public ImagePanel() {
            setLayout(null);
        }

        public void setBufferedImage(BufferedImage bufferedImage) {
            this.bufferedImage = bufferedImage;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D graphics2D = (Graphics2D) g;

            if (bufferedImage != null) {
                graphics2D.drawImage(bufferedImage, 0, 0, null);
            }
        }
    }

    private static class ImageLinePanel extends JPanel {
        private int maxWidth = 0;
        private int maxHeight = 0;

        private boolean paintLines = false;
        private boolean paintImage = true;

        private BufferedImage bufferedImage = null;
        private int pixelSize = 0;

        public ImageLinePanel() {
            setLayout(null);
            setBackground(Color.white);
        }

        public void setBufferedImage(BufferedImage bufferedImage, int pixelSize) {
            this.bufferedImage = bufferedImage;
            this.pixelSize = pixelSize;
            lines();
        }

        public void setPaintNumbers(boolean paintLines) {
            this.paintLines = paintLines;
            lines();
        }

        public void setPaintImage(boolean paintImage) {
            this.paintImage = paintImage;
            repaint();
        }

        private void lines() {
            if (this.paintLines) {
                paintLabels();
            } else {
                removeLabels();
            }

        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D graphics2D = (Graphics2D) g;

            if (paintImage) {
                paintImage(graphics2D);
            }
            paintLines(graphics2D);
        }

        private void paintImage(Graphics2D graphics2D) {
            if (bufferedImage != null) {
                graphics2D.drawImage(bufferedImage, 0, 0, null);
            }
        }

        private void paintLines(Graphics2D graphics2D) {
            if (bufferedImage != null) {
                int counter = pixelSize;

                while (bufferedImage.getWidth() > counter) {
                    graphics2D.setColor(Color.BLACK);
                    graphics2D.drawLine(counter, 0, counter, bufferedImage.getHeight());

                    maxWidth = counter;
                    counter += pixelSize;
                }

                counter = pixelSize;
                while (bufferedImage.getHeight() > counter) {
                    graphics2D.setColor(Color.BLACK);
                    graphics2D.drawLine(0, counter, bufferedImage.getWidth(), counter);

                    maxHeight = counter;
                    counter += pixelSize;
                }
            }
        }

        private void paintLabels() {
            if (bufferedImage != null) {
                NumberService numberService = new NumberService();

                int counterY = 0;

                while (bufferedImage.getHeight() > counterY) {
                    int counterX = 0;
                    while (bufferedImage.getWidth() > counterX) {

                        int color = bufferedImage.getRGB(counterX + 1, counterY + 1);

                        String name;
                        if (Color.WHITE.equals(new Color(color))) {
                            name = numberService.odd() + "";
                        } else {
                            name = numberService.even() + "";
                        }

                        JLabel label = new JLabel(name);
                        label.setBackground(Color.white);
                        label.setHorizontalAlignment(SwingConstants.CENTER);
                        add(label);
                        label.setBounds(counterX, counterY, pixelSize, pixelSize);


                        counterX += pixelSize;
                    }
                    counterY += pixelSize;
                }

                repaint();
                revalidate();
            }
        }

        private void removeLabels() {
            removeAll();

            repaint();
            revalidate();
        }

        public Optional<File> saveImage() throws IOException {
            if (bufferedImage != null) {
                int w = maxWidth;
                int h = maxHeight;
                BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                Graphics2D g = bi.createGraphics();
                this.paint(g);
                g.dispose();
                File f = new File("result.jpg");
                ImageIO.write(bi, "jpg", f);
                return Optional.of(f);
            }
            return Optional.empty();
        }
    }
}