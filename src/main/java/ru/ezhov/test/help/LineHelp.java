package ru.ezhov.test.help;

        import javax.swing.*;
        import java.awt.*;

        public class LineHelp {
            public static void main(String[] args) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        JFrame frame = new JFrame();
                        JPanel panel = new JPanel(null) {
                            @Override
                            public void paint(Graphics g) {
                                super.paint(g);
                                g.drawLine(2, 2, 100, 100);
                            }
                        };
                        frame.add(panel);
                        frame.setSize(400, 400);
                        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                        frame.setLocationRelativeTo(null);
                        frame.setVisible(true);
                    }
                });
            }
        }
