package ru.ezhov.test.help.swing;

import javax.swing.*;
import java.awt.*;

public class Test1Frame extends JFrame {
    private JPanel test1Panel = new JPanel();
    private JScrollPane scrollPane = new JScrollPane(test1Panel);

    public Test1Frame() {
        super("Тест 1");
        test1Panel.setLayout(new BoxLayout(test1Panel, BoxLayout.Y_AXIS));
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        getContentPane().add(scrollPane);
        setPreferredSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        runTest1();
        setVisible(true);
    }

    private void runTest1() {
        test1Panel.add(new JLabel("1123123123123123"));
        for (int i = 0; i < 100; i++)
            test1Panel.add(new JButton("123123123123123123"));

    }

    public static void main(String[] args) {
        new Test1Frame();
    }
}