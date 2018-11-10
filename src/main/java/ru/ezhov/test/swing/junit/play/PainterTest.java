package ru.ezhov.test.swing.junit.play;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class PainterTest {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
        {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Throwable ex) {
                //
            }
            JFrame frame = new JFrame("_________");

            CoordinateImageHolderPanel coordinateImageHolderPanel =
                    new CoordinateImageHolderPanel(new CoordinateImagePanel());
            frame.add(coordinateImageHolderPanel);
            frame.setSize(1000, 600);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

class CoordinateImagePanel extends JPanel {
    private Dimension defaultDimension = new Dimension(500, 500);

    public CoordinateImagePanel() {
        this.setLayout(null);
        this.setMinimumSize(defaultDimension);
        this.setMaximumSize(defaultDimension);
        this.setPreferredSize(defaultDimension);
        this.setSize(defaultDimension);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }
}

class CoordinateImageHolderPanel extends JPanel {
    private CoordinateImagePanel coordinateImagePanel;

    public CoordinateImageHolderPanel(CoordinateImagePanel coordinateImagePanel) {
        this.coordinateImagePanel = coordinateImagePanel;
        this.setLayout(null);
        this.add(coordinateImagePanel);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;

        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHints(rh);

        coordinateImagePanel.setLocation(40, 40);
        paintSizeLines(g2);

        AffineTransform saveXform = new AffineTransform();
        saveXform.rotate(Math.toRadians(90));
        g2.transform(saveXform);
        g2.drawString("test", 30, 30);
        g2.setTransform(saveXform);
    }

    private void paintSizeLines(Graphics2D g) {
        Point location = coordinateImagePanel.getLocation();
        Dimension dimension = coordinateImagePanel.getSize();
        System.out.println(location);
        System.out.println(dimension);

        int x1 = ((int) location.getX()) - 15;
        int y1 = ((int) location.getY()) - 10;
        int x2 = ((int) location.getX()) - 15;
        int y2 = dimension.height + y1 + 10 + 10;

        //отрисовали вертикальную черту
        g.drawLine(x1, y1, x2, y2);

        //отрисовка верхней вертикальной стрелочки
        x1 = ((int) location.getX()) - 30;
        y1 = ((int) location.getY());
        x2 = ((int) location.getX());
        y2 = ((int) location.getY());
        g.drawLine(x1, y1, x2, y2);

        x1 = ((int) location.getX()) - 8;
        y1 = ((int) location.getY() - 5);
        x2 = ((int) location.getX());
        y2 = ((int) location.getY());
        g.drawLine(x1, y1, x2, y2);

        x1 = ((int) location.getX()) - 8;
        y1 = ((int) location.getY()) + 5;
        x2 = ((int) location.getX());
        y2 = ((int) location.getY());
        g.drawLine(x1, y1, x2, y2);

        x1 = ((int) location.getX()) - 8;
        y1 = ((int) location.getY()) - 5;
        x2 = ((int) location.getX()) - 8;
        y2 = ((int) location.getY()) + 5;
        g.drawLine(x1, y1, x2, y2);

        //отрисовка нижней вериткальной стрелочки
        x1 = ((int) location.getX()) - 30;
        y1 = dimension.height + ((int) location.getY());
        x2 = ((int) location.getX());
        y2 = dimension.height + ((int) location.getY());
        g.drawLine(x1, y1, x2, y2);

        x1 = ((int) location.getX()) - 8;
        y1 = dimension.height + ((int) location.getY()) - 5;
        x2 = ((int) location.getX());
        y2 = dimension.height + ((int) location.getY());
        g.drawLine(x1, y1, x2, y2);

        x1 = ((int) location.getX()) - 8;
        y1 = dimension.height + ((int) location.getY()) + 5;
        x2 = ((int) location.getX());
        y2 = dimension.height + ((int) location.getY());
        g.drawLine(x1, y1, x2, y2);

        x1 = ((int) location.getX()) - 8;
        y1 = dimension.height + ((int) location.getY()) - 5;
        x2 = ((int) location.getX()) - 8;
        y2 = dimension.height + ((int) location.getY()) + 5;
        g.drawLine(x1, y1, x2, y2);
    }
}