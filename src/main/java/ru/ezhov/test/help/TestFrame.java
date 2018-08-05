package ru.ezhov.test.help;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class TestFrame extends JFrame {
    private JPanel test1Panel = new JPanel();
    private JScrollPane scrollPane = new JScrollPane(test1Panel);

    public TestFrame(String title) {
        super(title);
        test1Panel.setLayout(new BoxLayout(test1Panel, BoxLayout.Y_AXIS));
        test1Panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().add(scrollPane);
        runTest1();
        setVisible(true);
    }

    public static void main(String[] args) {
        new TestFrame("sdsdv");
    }

    private void runTest1() {
        List<String> testQuestions = Arrays.asList(generateText());
        for (String question : testQuestions) {
            //            String finalQuestion = "<html><body style=\"background-color:white;\">" + question.replace("\n", "<br>") + "</body>";
            String finalQuestion = "<html>" + question.replace("\n", "<br>");
            test1Panel.add(new RadioPanel(finalQuestion));
        }
    }

    private class RadioPanel extends JPanel {
        private JLabel textLabel;
        private JRadioButton radioButtonYes;
        private JRadioButton radioButtonNo;

        private RadioPanel(String text) {
            setLayout(new BorderLayout());
            textLabel = new JLabel(text);
            JPanel panelRadio = new JPanel();
            BoxLayout boxLayout = new BoxLayout(panelRadio, BoxLayout.Y_AXIS);
            panelRadio.setLayout(boxLayout);
            panelRadio.setAlignmentX(JPanel.LEFT_ALIGNMENT);
            radioButtonYes = new JRadioButton("Да", false);
            radioButtonNo = new JRadioButton("Нет", false);
            panelRadio.add(radioButtonYes);
            panelRadio.add(radioButtonNo);
            ButtonGroup butGroup = new ButtonGroup();
            butGroup.add(radioButtonYes);
            butGroup.add(radioButtonNo);
            add(textLabel, BorderLayout.CENTER);
            add(panelRadio, BorderLayout.SOUTH);
            textLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            textLabel.setBorder(
                    BorderFactory.createCompoundBorder(
                            BorderFactory.createEmptyBorder(5, 5, 5, 5),
                            BorderFactory.createLineBorder(Color.GRAY)
                    )
            );
            setBorder(
                    BorderFactory.createCompoundBorder(
                            BorderFactory.createEmptyBorder(5, 5, 5, 5),
                            BorderFactory.createLineBorder(Color.GRAY)
                    )
            );
        }
    }

    public String[] generateText() {
        int randomBlocks = new Random().nextInt(10) + 10;
        String[] array = new String[randomBlocks];
        for (int i = 0; i < randomBlocks; i++) {
            int randomRows = new Random().nextInt(10) + 10;
            StringBuilder stringBuilder = new StringBuilder();
            for (int r = 0; r < randomRows; r++) {
                int randomChar = new Random().nextInt(30) + 10;
                for (int ch = 0; ch < randomChar; ch++) {
                    stringBuilder.append(UUID.randomUUID());
                }
                stringBuilder.append("\n");
            }
            array[i] = stringBuilder.toString();
        }
        return array;
    }
}