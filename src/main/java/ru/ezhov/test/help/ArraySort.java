package ru.ezhov.test.help;

        import java.util.Arrays;
        import java.util.Comparator;
        import java.util.regex.Matcher;
        import java.util.regex.Pattern;

        public class ArraySort {
            private static Pattern pattern = Pattern.compile("(?<number>\\d+)");

            public static void main(String[] args) {
                String[] arr = {
                        "a (51)",
                        "b (13)",
                        "c (55)"
                };

                Arrays.sort(arr, new Comparator<String>() {

                    @Override
                    public int compare(String o1, String o2) {
                        return getValue(o1).compareTo(getValue(o2));
                    }
                });
                System.out.println(Arrays.toString(arr));

            }

            private static String getValue(String value) {
                Matcher matcher = pattern.matcher(value);
                if (matcher.find()) {
                    return matcher.group("number");
                } else {
                    return "";
                }
            }
        }
