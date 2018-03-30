package ru.ezhov.cmd;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Created by ezhov_da on 30.03.2018.
 */
public class App {
    private static final Logger LOG = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {

                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Throwable t) {
                }

                JFrame frame = new JFrame("Command executor");

                JPanel panelBasic = new JPanel(new BorderLayout());

                JPanel panelTop = new JPanel(new BorderLayout());
                JPanel panelTopHeader = new JPanel(new BorderLayout());
                JPanel panelTopBody = new JPanel(new BorderLayout());
                JTextField textField = new JTextField();
                JButton button = new JButton("EXEC COMMAND");

                panelTopHeader.add(textField, BorderLayout.CENTER);
                panelTopHeader.add(button, BorderLayout.EAST);

                ListModel listModel = new ListModel();

                JList list = new JList();
                list.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        textField.setText(String.valueOf(list.getSelectedValue()));
                    }
                });

                list.setModel(listModel);
                panelTopBody.add(new JScrollPane(list));

                panelTop.add(panelTopHeader, BorderLayout.NORTH);
                panelTop.add(panelTopBody, BorderLayout.CENTER);

                JPanel panelCenter = new JPanel(new BorderLayout());
                JTextPane textPane = new JTextPane();
                textPane.setForeground(Color.WHITE);
                textPane.setVisible(true);
                textPane.setBackground(Color.BLACK);

                button.addActionListener(e -> {
                    Thread thread = new Thread(() -> {
                        Runtime runtime = Runtime.getRuntime();
                        try {
                            String command = textField.getText();
                            Process process = runtime.exec(command);
                            textPane.setText("");

                            BufferedReader is = new BufferedReader(new InputStreamReader(process.getInputStream()));
                            String[] line = {""};

                            while ((line[0] = is.readLine()) != null) {
                                SwingUtilities.invokeLater(() -> {
                                    if (line[0] != null) {
                                        String text = textPane.getText();
                                        textPane.setText(line[0] + "\n" + text);
                                        textPane.setCaretPosition(0);
                                    }
                                });
                            }

                            SwingUtilities.invokeLater(() -> {
                                textPane.setText("~done~\n" + textPane.getText());
                            });
                            listModel.add(command);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    });

                    thread.setDaemon(true);
                    thread.start();
                });

                panelCenter.add(new JScrollPane(textPane), BorderLayout.CENTER);

                panelBasic.add(panelTop, BorderLayout.NORTH);
                panelBasic.add(panelCenter, BorderLayout.CENTER);

                frame.add(panelBasic);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(900, 400);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        });
    }

    static class ListModel extends AbstractListModel<String> {
        private ArrayList<String> commands;
        private String nameFile = "commands.txt";

        public ListModel() throws FileNotFoundException {
            commands = new ArrayList<>();
            File file = new File(nameFile);
            if (file.exists()) {
                try (Scanner scanner = new Scanner(new FileInputStream(new File(nameFile)));) {
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        if (!"".equals(line)) {
                            commands.add(line);
                        }
                    }
                }
            }
        }

        @Override
        public int getSize() {
            return commands.size();
        }

        @Override
        public String getElementAt(int index) {
            return commands.get(index);
        }

        public void add(String command) {
            if (!commands.contains(command)) {
                commands.add(command);
                Collections.sort(commands);
                saveToFile();
                fireContentsChanged(this, 0, commands.size());
            }
        }

        private void saveToFile() {
            try (FileWriter fileWriter = new FileWriter(new File(nameFile))) {
                for (String command : commands) {
                    fileWriter.write(command + "\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
