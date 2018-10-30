package ru.ezhov.test.websockets;

import java.util.logging.Logger;

import static spark.Spark.get;
import static spark.Spark.port;

public class AppSpark {
    private static final Logger LOG = Logger.getLogger(AppSpark.class.getName());

    public static void main(String[] args) {
        port(4567);
//        webSocket("/test", EchoWebSocket.class);
        get("/", (request, response) -> {
            request.headers().forEach(System.out::println);
            request.attributes().forEach(System.out::println);
            request.queryParams().forEach(System.out::println);
            System.out.println(request);
            return "test";
        });
    }
}
