package ru.ezhov.test.help.swing;

        import javax.swing.*;
        import java.awt.*;

        public class BarCodeGenerator {

            public static void main(String[] args) {
                SwingUtilities.invokeLater(() -> {
                    JFrame frame = new JFrame("_________");
                    BarCodePanel barCodePanel = new BarCodePanel(new Line[]{
                            new Line(3),
                            new Line(2),
                            new Line(4),
                            new Line(3),
                            new Line(2),
                            new Line(6),
                            new Line(4),
                            new Line(5),
                            new Line(7),
                            new Line(5),
                            new Line(3),
                            new Line(4),
                            new Line(9),
                            new Line(12),
                            new Line(11),
                            new Line(3),
                            new Line(6),
                            new Line(3),
                            new Line(2),
                            new Line(8)
                    });
                    frame.add(barCodePanel);
                    frame.setSize(1000, 600);
                    frame.setLocationRelativeTo(null);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setVisible(true);
                });
            }
        }

        class BarCodePanel extends JPanel {
            private Line[] lines;

            public BarCodePanel(Line[] lines) {
                this.lines = lines;
            }

            @Override
            public void paint(Graphics g) {
                super.paint(g);
                Graphics2D g2D = (Graphics2D) g;
                int x = 0;
                for (int i = 0; i < lines.length; i++) {
                    if (i % 2 == 0) {
                        g2D.setColor(Color.BLACK);
                    } else {
                        g2D.setColor(Color.WHITE);
                    }
                    Line line = lines[i];
                    g2D.fillRect(x, 0, line.getWidth(), 60);
                    x += line.getWidth();
                }

            }
        }

        class Line {
            private int width;

            public Line(int width) {
                this.width = width;
            }

            public int getWidth() {
                return width;
            }
        }