package ru.ezhov.test.websockets;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;
import java.util.Random;
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
            request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
            Part part = request.raw().getPart("file");
            System.out.println("Name: " + part.getName());
            System.out.println("SubmittedFileName: " + part.getSubmittedFileName());
//            try (InputStream is = request.raw().getPart("file").getInputStream()) {
            // Use the input stream to create a file
//            }
            return "File uploaded";
        });


        get("/id", (request, response) -> {
            response.type("application/json");
            return "{\"id\":" + new Random().nextInt() + "}";
        });
        get("/:id/get", (request, response) -> "get id: " + request.params("id"));

        post("ip", (request, response) -> {
            try (Scanner scanner = new Scanner(request.raw().getInputStream())) {
                while (scanner.hasNextLine()) {
                    System.out.println(scanner.nextLine());
                }
            }
            return "OK";
        });
    }
}
