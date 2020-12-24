package ru.ezhov.test.spark;

import static spark.Spark.*;

//port 4567
public class Application {
    public static void main(String[] args) {
        get("/:guid", (request, response) -> {
            Thread.sleep(2000);

            request.headers().forEach(System.out::println);


            return "Hello World! " + request.params(":guid");
        });
    }
}
