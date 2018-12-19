package ru.ezhov.test.help;

        import java.util.Arrays;

        public class SortNumberHelp {
            public static void main(String[] args) {
                int[] ints = new int[args.length];
                for (int i = 0; i < args.length; i++) {
                    ints[i] = Integer.parseInt(args[i]);
                }
                Arrays.sort(ints);
                System.out.println("Array before sort: " + Arrays.toString(args));
                System.out.println("Array after sort: " + Arrays.toString(ints));
            }
        }
