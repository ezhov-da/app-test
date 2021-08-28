package ru.ezhov.test.swing.row.log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Application {
    public static void main(String[] args) {

//        createErrors();
        createErrors1();

//        countErrors();
    }

    private static void createErrors() {
        File errorsFile = new File("D:\\docs\\work\\3020\\errors_full.txt");
        if (errorsFile.exists()) {
            errorsFile.delete();
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(errorsFile))) {
            files().forEach(f -> {
                try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
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
                            String error = String.join("", cause
                                    .subList(cause.size() - 1, cause.size()));

                            bufferedWriter.write(error + "\n");

                            System.out.println(error);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createErrors1() {
        File errorsFile = new File("D:\\docs\\work\\3020\\errors_full.txt");
        if (errorsFile.exists()) {
            errorsFile.delete();
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(errorsFile))) {
            files().forEach(f -> {
                try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        line = line.substring(1, line.length() - 1);
                        line = line.replace("\\t", "\t");
                        line = line.replace("\\n", "\n");

                        String[] linesGood = line.split("\n");

                        List<String> cause = new ArrayList<>();
                        cause.add(linesGood[0]);
                        for (String l : linesGood) {
                            if (l.startsWith("Caused by:")) {
                                cause.add(l);
                            }

                        }
                        if (!cause.isEmpty()) {
                            String error = String.join(" -> ", cause);

                            bufferedWriter.write(error + "\n");

                            System.out.println(error);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<File> files() {
        List<Integer> files = Arrays.asList(0, 1, 2, 3, 4, 5);
        return files
                .stream()
                .map(i -> new File("D:\\docs\\work\\3020\\full_report_clear" + i + ".txt"))
                .collect(Collectors.toList());
    }

    private static void countErrors() {
        File errorsFile = new File("D:\\docs\\work\\3020\\errors_full.txt");

        try {
            List<String> lines = Files.readAllLines(errorsFile.toPath());

            Map<String, Long> errorsCount = lines.stream().collect(Collectors.toMap(Function.identity(), v -> 1L, Long::sum));

            List<Map.Entry<String, Long>> list = new ArrayList(errorsCount.entrySet());
            list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

            Map<String, Long> result = new LinkedHashMap<>();
            for (Map.Entry<String, Long> entry : list) {
                result.put(entry.getKey(), entry.getValue());
            }

            result.forEach((k, v) -> {
                System.out.println(v + "-" + k);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
