package ru.ezhov.test.spark;

import java.io.InputStream;
import java.io.OutputStream;

import static spark.Spark.get;

//port 4567
public class Application {
    public static void main(String[] args) {
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
    }
}
