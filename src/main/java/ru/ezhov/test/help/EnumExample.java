package ru.ezhov.test.help;

        public class EnumExample {
            public static void main(String[] args) {
                Names colName = Names.findBy("colName");
                System.out.println(colName);
                Names.findBy("colNameTest");
            }
        }

        enum Names {
            COL_NAME("colname"), ROW_NAME("rowname");
            private String name;

            Names(String name) {
                this.name = name;
            }

            public static Names findBy(String name) {
                for (Names names : Names.values()) {
                    if (name.equalsIgnoreCase(names.name)) {
                        return names;
                    }
                }
                throw new IllegalArgumentException("Перечисление по имени '" + name + "' не найдено");
            }
        }
