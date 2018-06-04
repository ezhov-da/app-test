package ru.ezhov.test.help;

        import java.util.regex.Matcher;
        import java.util.regex.Pattern;

        public class IpHelp {
            public static void main(String[] args) {
                printFind(
                        "avcavf 213.123.11.255aoegger",
                        "54.34.23.43asdsad sddas sagf a",
                        "@@$H%KJ@%54.342.23.43asdsad sddas sagf a",
                        "asdsa54.34.23.143d sddas sagf a"
                );
            }

            private static void printFind(String... lines) {
                Pattern p = Pattern.compile("(\\d{0,3}\\.){3}\\d{0,3}");
                for (String s : lines) {
                    Matcher m = p.matcher(s);
                    if (m.find()) {
                        System.out.println(m.group());
                    } else {
                        System.out.println("Not found");
                    }
                }
            }
        }
