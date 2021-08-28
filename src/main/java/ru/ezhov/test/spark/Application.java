package ru.ezhov.test.spark;

import java.io.InputStream;
import java.io.OutputStream;

import static spark.Spark.get;
import static spark.Spark.port;

//port 4567
public class Application {
    public static void main(String[] args) {
        port(8080);

        get("/guid/:guid", (request, response) -> {
            Thread.sleep(2000);

            request.headers().forEach(System.out::println);


            return "Hello World! " + request.params(":guid");
        });

        get("/file", ((request, response) -> {
            response.header("Content-Type", "image/jpeg");
            response.header("Content-disposition", "attachment; filename=field.jpg;");

            try (
                    InputStream inputStream = Application.class.getResourceAsStream("/field.jpg");
                    OutputStream outputStream = response.raw().getOutputStream()
            ) {
                byte[] bytes = new byte[inputStream.available()];
                inputStream.read(bytes);
                outputStream.write(bytes);
                outputStream.flush();
                return response;
            }
        }));

        get("/*", ((request, response) -> {

            System.out.println("==================================================");
            System.out.println(request.uri());
            request.headers().forEach(h -> {
                System.out.println(h + ": " + request.headers(h));
            });
            System.out.println("--------------------------------------------------");

            String rolloutBucket = request.cookie("rollout_bucket");
            if (rolloutBucket == null || "".equals(rolloutBucket)) {
                int i = 5;
            }

            return response;
        }));
    }
}
