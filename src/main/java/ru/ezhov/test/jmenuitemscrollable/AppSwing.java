package ru.ezhov.test.jmenuitemscrollable;

import javax.swing.*;

public class AppSwing {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Throwable ex) {
                    //
                }
                JFrame frame = new JFrame("_________");

                JMenu menu = new JMenu("Test");
                MenuScroller.setScrollerFor(menu);

                menu.add(new JMenuItem("Test 1"));
                menu.add(new JMenuItem("Test 2"));
                menu.add(new JMenuItem("Test 3"));
                menu.add(new JMenuItem("Test 4"));
                menu.add(new JMenuItem("Test 5"));
                menu.add(new JMenuItem("Test 6"));

                frame.add(menu);

                frame.setSize(1000, 600);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}