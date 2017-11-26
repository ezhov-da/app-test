package ru.ezhov.test.jansi;

import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;

import org.fusesource.jansi.AnsiConsole;

/**
 * http://fusesource.github.io/jansi/
 */
public class JansiTest {
    public static void main(String[] args) {
        AnsiConsole.systemInstall();
//        or
//        AnsiConsole.out.println("Hello World");
//        if no use System.out

        System.out.println(ansi().eraseScreen().fg(RED).a("Hello").fg(GREEN).a(" World").reset());
    }
}
