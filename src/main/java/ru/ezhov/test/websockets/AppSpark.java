package ru.ezhov.test.websockets;

import java.util.Scanner;
import java.util.logging.Logger;

import static spark.Spark.*;

public class AppSpark {
    private static final Logger LOG = Logger.getLogger(AppSpark.class.getName());

    public static void main(String[] args) {
        port(4567);
        staticFileLocation("html");

//        webSocket("/test", EchoWebSocket.class);
//        get("/", (request, response) -> {
//            request.headers().forEach(System.out::println);
//            request.attributes().forEach(System.out::println);
//            request.queryParams().forEach(System.out::println);
//            System.out.println(request);
//            return "test";
//        });

        post("file-up", (request, response) -> {
            try (Scanner scanner = new Scanner(request.raw().getInputStream())) {
                while (scanner.hasNextLine()) {
                    System.out.println(scanner.nextLine());
                }
            }
            return "qwqwqwqw";
        });
    }
}
