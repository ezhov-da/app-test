package ru.ezhov.test.help;

import javax.swing.*;
import java.awt.*;

public class SwingTest {
    public static void main(String[] args) {
        JFrame view = new JFrame("Dark Eldar");
        JPanel top = new JPanel();
        top.setBackground(new Color(85, 26, 139));
        top.setLayout(null);
        JButton but = new JButton("Real-space raid");
        but.setBounds(750, 800, 150, 50);
        top.add(but);
        view.setSize(1920, 1080);
        view.add(top);
        view.setResizable(false);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setVisible(true);
    }
}