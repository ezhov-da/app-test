package ru.ezhov.test.websockets;

import java.util.logging.Logger;

import static spark.Spark.*;

public class AppSpark {
    private static final Logger LOG = Logger.getLogger(AppSpark.class.getName());

    public static void main(String[] args) {
        port(4567);
        webSocket("/test", EchoWebSocket.class);
        get("/", (request, response) -> "test");
    }
}
