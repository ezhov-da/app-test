package ru.ezhov.test.help;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private String str() {
        String str = "00!!!0* dsgsh235 !!!=-lktrfgho";
        Pattern r = Pattern.compile("[-[0-9]_!@#$%&*()+=\\s+]");
        Matcher b = r.matcher(str);

        Pattern pat = Pattern.compile("[A-Z+][a-z+]");
        Matcher mat = pat.matcher(str);
        return str;

    }

    public static void main(String[] args) {
        String str = "00!!!0*dAsgsh235 !!!=-lktKHrfgho";
        Pattern pat = Pattern.compile("[^(A-Za-z)]+");
        Matcher mat = pat.matcher(str);
        int max = 0;
        while (mat.find()) {
            int current = mat.group().length();
            max = max < current ? current : max;
        }
        System.out.println(max);
    }

}