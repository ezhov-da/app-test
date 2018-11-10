package ru.ezhov.test.swing.junit.play;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class StarsTest {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
        {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Throwable ex) {
                //
            }
            JFrame frame = new JFrame("_________");
            JButton button = new JButton("Сгенерировать");
            JPanel starGeneratorPanel = new JPanel();
            JSpinner spinnerCount = new JSpinner(new SpinnerNumberModel(10, 1, 100, 1));
            JPanel countStar = new JPanel();
            JLabel label = new JLabel("Кол-во звезд");
            label.setLabelFor(spinnerCount);
            countStar.add(label);
            countStar.add(label);
            countStar.add(spinnerCount);

            JSpinner spinnerRadius = new JSpinner(new SpinnerNumberModel(10, 1, 100, 1));
            JPanel radiusStar = new JPanel();
            label = new JLabel("Радиус");
            label.setLabelFor(spinnerRadius);
            countStar.add(label);
            countStar.add(spinnerRadius);

            JSpinner spinnerGroup = new JSpinner(new SpinnerNumberModel(2, 1, 100, 1));
            JPanel groupStar = new JPanel();
            label = new JLabel("Кол-во групп");
            label.setLabelFor(spinnerGroup);
            groupStar.add(label);
            groupStar.add(spinnerGroup);

            starGeneratorPanel.add(countStar);
            starGeneratorPanel.add(radiusStar);
            starGeneratorPanel.add(groupStar);

            button.addActionListener(e -> {
                StarCreator starCreator = new StarCreator();
                List<Star> stars = starCreator.generateStars(
                        Integer.parseInt(spinnerCount.getValue().toString()),
                        Integer.parseInt(spinnerRadius.getValue().toString()),
                        400,
                        400
                );
                StarPanel panel = new StarPanel(
                        stars,
                        starCreator.generateGroupsLink(Integer.parseInt(spinnerGroup.getValue().toString()), stars));


                JPanel panelP = new JPanel() {

                    @Override
                    public void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        Dimension dimension = getSize();

                        Point point = new Point(
                                dimension.width / 2 - panel.getSize().width / 2,
                                dimension.height / 2 - panel.getSize().height / 2
                        );

                        System.out.println(point);

                        g.dispose();
                    }
                };
                panelP.add(panel);

                frame.add(panelP, BorderLayout.CENTER);
                frame.revalidate();
            });
            starGeneratorPanel.add(button);
            frame.add(starGeneratorPanel, BorderLayout.NORTH);
            frame.setSize(700, 600);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setResizable(false);
            frame.setVisible(true);
        });
    }
}

class StarCreator {
    public List<Star> generateStars(int count, int radius, int maxX, int maxY) {
        List<Star> stars = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Star star;
            while (true) {
                int x = new Random().nextInt(maxX) + 1;
                int y = new Random().nextInt(maxY) + 1;
                int r = new Random().nextInt(radius) + 1;
                star = new Star(x, y, r);
                if (!stars.contains(star)) {
                    stars.add(star);
                    break;
                }
            }
        }
        return stars;
    }

    public Map<List<Star>, List<Star>> generateGroupsLink(int groupCount, List<Star> stars) {
        int sizeGroup = stars.size() / groupCount;
        List<List<Star>> starsGroup = new ArrayList<>();
        for (int i = 0, pos = 0; i < groupCount; i++) {
            int to = pos + sizeGroup;
            if (to >= stars.size()) {
                starsGroup.add(stars.subList(pos, stars.size()));
            } else {
                starsGroup.add(stars.subList(pos, to));
            }
            pos = to;
        }
        Map<List<Star>, List<Star>> starsLink = new HashMap<>();
        starsGroup.forEach(sg -> {
            int countLinks = new Random().nextInt(sg.size());
            List<Star> listFrom = new ArrayList<>();
            List<Star> listTo = new ArrayList<>();
            for (int i = 0; i < countLinks; i++) {
                int from = new Random().nextInt(sg.size());
                int to;
                while (true) {
                    to = new Random().nextInt(sg.size());
                    if (from != to) {
                        break;
                    }
                }
                listFrom.add(sg.get(from));
                listTo.add(sg.get(to));
            }
            starsLink.put(listFrom, listTo);
        });

        return starsLink;
    }

}

class StarPanel extends JPanel {
    private List<Star> stars;
    private Map<List<Star>, List<Star>> starsLink;

    public StarPanel(List<Star> stars, Map<List<Star>, List<Star>> starsLink) {
        this.stars = stars;
        this.starsLink = starsLink;
        this.setLayout(null);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setSize(new Dimension(500, 500));
        this.setMinimumSize(new Dimension(500, 500));
        this.setMaximumSize(new Dimension(500, 500));
        this.setPreferredSize(new Dimension(500, 500));
        this.setBackground(Color.black);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2D.setRenderingHints(rh);
        g2D.setColor(Color.WHITE);
        stars.forEach(s -> {
            g2D.drawOval(s.getX(), s.getY(), s.getRadius() * 2, s.getRadius() * 2);
        });
        g2D.setColor(Color.WHITE);
        starsLink.entrySet().forEach(e -> {
            List<Star> from = e.getKey();
            List<Star> to = e.getValue();
            from.forEach(s -> {
                to.forEach(t -> {
                    g2D.drawLine(
                            s.getX() + s.getRadius(),
                            s.getY() + s.getRadius(),
                            t.getX() + t.getRadius(),
                            t.getY() + t.getRadius()
                    );
                });
            });
        });
    }
}

class Star {
    private int x;
    private int y;
    private int radius;

    public Star(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRadius() {
        return radius;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Star star = (Star) o;

        if (x != star.x) return false;
        if (y != star.y) return false;
        return radius == star.radius;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + radius;
        return result;
    }
}
