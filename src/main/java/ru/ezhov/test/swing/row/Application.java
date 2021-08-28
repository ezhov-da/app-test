package ru.ezhov.test.swing.row;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Application {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            File file = new File("D:\\docs\\work\\3020\\full_report_clear3.txt");
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


            JFrame frame = new JFrame();

            JEditorPane pane = new JEditorPane();

            frame.add(new JScrollPane(pane), BorderLayout.CENTER);

            JButton button = new JButton("Next");

            BufferedReader finalReader = reader;
            button.addActionListener(e -> {

                try {
                    String line = finalReader.readLine();
                    if (line == null) {
                        line = "end";
                        finalReader.close();
                    } else {
                        line = line.substring(1, line.length() - 1);
                        line = line.replace("\\t", "\t");
                        line = line.replace("\\n", "\n");


                        String[] linesGood = line.split("\n");

                        List<String> cause = new ArrayList<>();
                        for (String l : linesGood) {
                            if (l.startsWith("Caused by:")) {
                                cause.add(l);
                            }

                        }
                        if (!cause.isEmpty()) {
                            System.out.println(
                                    cause
                                            .subList(cause.size() - 1, cause.size())
                                            .stream()
                                            .collect(
                                                    Collectors.joining(
                                                            "",
                                                            "--------------------------\n",
                                                            "\n--------------------------\n"
                                                    )
                                            )
                            );
                        }


                    }

                    String finalLine = line;
                    SwingUtilities.invokeLater(() -> {
                        pane.setText(finalLine);
                    });
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            });

            frame.add(button, BorderLayout.SOUTH);

            frame.setSize(800, 800);
            frame.setVisible(true);
        });
    }
}
