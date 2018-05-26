package ru.ezhov.test.help;

public class WhileTest {
    public static void main(String[] args) {
        while (true) {
            String list = "Введите значение";
            String words = "";
//                words = showInputDialog(null, list);
            String dwords = words.toLowerCase();
            if (dwords.equals("привет")) {
//                    showMessageDialog(null, "Вы вписали значение: " + words);
            }
        }
    }
}
