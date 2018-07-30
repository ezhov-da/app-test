package ru.ezhov.test.qr.play;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class QrSwing {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
        {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Throwable ex) {
                //
            }
            try {
                JFrame frame = new JFrame("_________");
                JPanel panelInput = new JPanel(new BorderLayout());
                JTextField textField = new JTextField();
                JSlider scrollBar = new JSlider();
                textField.setText("hello");

                panelInput.add(textField, BorderLayout.NORTH);
                panelInput.add(scrollBar, BorderLayout.CENTER);

                final JPanel[] panelBlockImage = {new JPanel()};
                scrollBar.setValue(200);
                scrollBar.setMinimum(50);
                scrollBar.setMaximum(500);
                scrollBar.setPaintLabels(true);
                scrollBar.addChangeListener(e -> test(scrollBar, frame, panelBlockImage, textField));
                textField.addCaretListener(e -> test(scrollBar, frame, panelBlockImage, textField));
                frame.add(panelInput, BorderLayout.NORTH);
                frame.setSize(1000, 600);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private static void test(JSlider scrollBar, JFrame frame, JPanel[] panelBlockImage, JTextField textField) {
        SwingUtilities.invokeLater(() -> {
            try {
                int value = scrollBar.getValue();
                PanelBlockImage panelBlockImageNew = new PanelBlockImage(getQRCodeImage(textField.getText(), value, value));
                frame.remove(panelBlockImage[0]);
                panelBlockImage[0] = panelBlockImageNew;
                frame.add(panelBlockImage[0], BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
    }


    private static BufferedImage getQRCodeImage(String text, int width, int height) throws WriterException,
            IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }
}


class PanelBlock extends JPanel {
    private int[][] array;

    public PanelBlock(int[][] array) {
        this.array = array;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int widthPanel = getWidth();
        int heightPanel = getHeight();
//		int maxSize = widthPanel > heightPanel ? heightPanel : widthPanel;
        int maxSize = 100;
        int sizeCell = maxSize / array.length;
        int maxSizeComponent = sizeCell * array.length;
        int x;
        int y;
        int xConstant = x = widthPanel / 2 - maxSizeComponent / 2;
        int yConstant = y = heightPanel / 2 - maxSizeComponent / 2;

        Graphics2D g2d = (Graphics2D) g;
        for (int r = 0; r < array.length; r++) {
            for (int c = 0; c < array[r].length; c++) {
                int val = array[r][c];
                if (val == 0) {
                    g2d.setColor(Color.BLACK);
                } else {
                    g2d.setColor(Color.WHITE);
                }
                g2d.fillRect(x, y, sizeCell, sizeCell);
                x += sizeCell;
            }
            y += sizeCell;
            x = xConstant;
        }
    }
}

class PanelBlockImage extends JPanel {
    private BufferedImage bufferedImage;

    public PanelBlockImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        int widthPanel = getWidth();
        int heightPanel = getHeight();

        int widthImage = bufferedImage.getWidth();
        int heightImage = bufferedImage.getWidth();


        g2d.drawImage(bufferedImage, null, widthPanel / 2 - widthImage / 2, heightPanel / 2 - heightImage / 2);
    }
}