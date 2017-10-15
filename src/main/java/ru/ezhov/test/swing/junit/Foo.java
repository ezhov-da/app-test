/**
 * основная идея и файлы для тестирования взяты с сайта:
 * http://www.javaworld.com/article/2073056/swing-gui-programming/automate-gui-tests-for-swing-applications.html
 */
package ru.ezhov.test.swing.junit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Foo extends JFrame {

    JTextField inputField;

    public Foo() {
        super("Foo app");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container root = getContentPane();
        root.setLayout(new BoxLayout(root, BoxLayout.X_AXIS));

        // Menu bar with 'File' and 'Color'
        JMenuBar bar = new JMenuBar();
        JMenuItem item;
        JMenu menu;

        // File -> Exit menu item
        menu = new JMenu("File");

        item = new JMenuItem("Exit");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent notUsed) {
                System.exit(0);
            }
        });
        item.setName("exit");
        menu.add(item);

        bar.add(menu); // add File menu

        // Color change menu
        menu = new JMenu("Color");

        item = new JMenuItem("Red");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent notUsed) {
                inputField.setForeground(Color.red);
            }
        });
        item.setName("red");
        menu.add(item);

        item = new JMenuItem("Blue");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent notUsed) {
                inputField.setForeground(Color.blue);
            }
        });
        item.setName("blue");
        menu.add(item);

        bar.add(menu); // add 'Color' menu
        setJMenuBar(bar);

        // Test input field, add "?" to the text when ENTER is hit.
        // inputField is a instance variable
        inputField = new JTextField(20);
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                inputField.setText(inputField.getText() + "?");
            }
        });
        inputField.setName("input");
        getContentPane().add(inputField);

        // Button to show a pop-up window with a message
        JButton button = new JButton("Doit!");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                showMessage(inputField.getText());
            }
        });
        button.setName("popup");
        getContentPane().add(button);

        pack();
        setLocationRelativeTo(null);
    }

    private void showMessage(String message) {
        JTextArea text = new JTextArea(message + " ... done.");
        text.setColumns(20);
        text.setLineWrap(true);
        text.setBackground(null);
        text.setEditable(false);

        JOptionPane.showMessageDialog(this, new JScrollPane(text));
    }

    public static void main(String[] args) {
        Foo foo = new Foo();
        foo.setVisible(true);
    }
}
