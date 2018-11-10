package ru.ezhov.test.stars;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Stars {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
        {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Throwable ex) {
                //
            }
            JFrame frame = new JFrame("_________");

            try {
                JTabbedPane tabbedPane = new JTabbedPane();
                tabbedPane.addTab("Получение координат", new StarsCoordinate());
                tabbedPane.addTab("Отображение", new StarPanel());

                frame.add(tabbedPane, BorderLayout.CENTER);
            } catch (IOException e) {
                e.printStackTrace();
            }
            frame.setSize(600, 700);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

class StarsCoordinate extends JPanel {
    public StarsCoordinate() throws IOException {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);

        ImagePanel imagePanel = new ImagePanel();
        JPanel panelCont = new JPanel();
        panelCont.add(imagePanel);
        panelCont.setBounds(0, 0, 500, 500);
        panelCont.setLocation(0, 0);

        Dimension dimension = new Dimension(500, 500);
        imagePanel.setMinimumSize(dimension);
        imagePanel.setMaximumSize(dimension);
        imagePanel.setPreferredSize(dimension);
        imagePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater(() -> {
                    textArea.append(String.format("X: %s Y: %s\n", e.getX(), e.getY()));
                });
            }
        });

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setTopComponent(panelCont);
        splitPane.setBottomComponent(panel);
        this.setLayout(new BorderLayout());
        this.add(splitPane, BorderLayout.CENTER);
    }
}


class ImagePanel extends JPanel {
    private BufferedImage image;

    public ImagePanel() throws IOException {
        this.setLayout(null);
//        image = ImageIO.read(new File("D:\\maxresdefault.jpg"));
        image = ImageIO.read(new URL("https://st2.depositphotos.com/1963585/10247/v/950/depositphotos_102475060-stock-illustration-stock-vector-icons-with-scorpio.jpg"));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Dimension dimension = getSize();
        System.out.println(dimension);
        Graphics2D g2D = (Graphics2D) g;

        double maxSize = dimension.getWidth() > dimension.getHeight() ? dimension.getWidth() : dimension.getHeight();

        int w = image.getWidth();
        int h = image.getHeight();

        int max = w > h ? w : h;

        double wv = (w == max) ? maxSize : w / (max / maxSize);
        double hv = (h == max) ? maxSize : h / (max / maxSize);

        Image scaled = image.getScaledInstance((int) wv, (int) hv, Image.SCALE_SMOOTH);
        g2D.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2D.drawImage(scaled, 0, 0, null);
    }
}


class StarPanel extends JPanel {
    private List<Star> stars;

    public StarPanel(List<Star> stars) {
        this.stars = stars;
    }

    public StarPanel() {
        stars = new ArrayList<>();
        //большая медведица
//        stars.add(new Star(131, 81));
//        stars.add(new Star(193, 78));
//        stars.add(new Star(225, 102));
//        stars.add(new Star(266, 131));
//        stars.add(new Star(357, 136));
//        stars.add(new Star(338, 182));
//        stars.add(new Star(269, 171));
        //скорпион
        Star s1 = new Star(203, 195);
        stars.add(s1);
        Star s2 = new Star(170, 226);
        stars.add(s2);
        s1.addChildren(s2);

        s1 = new Star(186, 261);
        stars.add(s1);
        s2.addChildren(s1);
        s1 = new Star(229, 264);
        stars.add(s1);
        s2 = new Star(258, 231);
        stars.add(s2);
        s1.addChildren(s1);
//        s1.addChildren(s2);
//        s2 = new Star(268, 196);
//        stars.add(s2);
//        s1 = new Star(297, 170);
//        stars.add(s1);
//        s2.addChildren(s1);
//        Star starBase = new Star(324, 152);
//        stars.add(starBase);
//        Star star = new Star(352, 108);
//        starBase.addChildren(star);
//        stars.add(star);
//        star = new Star(376, 146);
//        starBase.addChildren(star);
//        stars.add(star);
//        star = new Star(364, 184);
//        starBase.addChildren(star);
//        stars.add(star);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        List<Star> starsWithChildren = new ArrayList<>();

        Graphics2D g2D = (Graphics2D) g;
        stars.forEach(s -> {
            g2D.drawOval(s.getX(), s.getY(), 10, 10);
            if (!s.getChild().isEmpty()) {
                starsWithChildren.add(s);
            }
        });

        starsWithChildren.forEach(s -> {
            s.getChild().forEach(c -> {
                g2D.drawLine(
                        s.getX() + 5,
                        s.getY() + 5,
                        c.getX() + 5,
                        c.getY() + 5
                );
            });
        });
    }
}

class Star {
    private int x;
    private int y;

    private List<Star> child;

    public List<Star> getChild() {
        return child;
    }

    public Star(int x, int y) {
        this.x = x;
        this.y = y;
        this.child = new ArrayList<>();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getId() {
        return x + "" + y;
    }

    public void addChildren(Star children) {
        if (!child.contains(children)) {
            child.add(children);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Star star = (Star) o;

        if (x != star.x) return false;
        return y == star.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}

