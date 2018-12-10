package ru.ezhov.test.help;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

public class SqLite {


    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
        String userName = "root";
        String password = "1";
        String connectionUrl = "jdbc:sqlite:D:/programs/test.db";
        Class.forName("org.sqlite.JDBC");
        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            System.out.println("Connection establish");
        try (FileInputStream fileInputStream =
                     new FileInputStream(
                             new File("2018-10-17 22_16_32-Best Practices for Sencha Javascript Applications on Vimeo.png"))) {
            byte[] bytes = new byte[fileInputStream.available()];
            fileInputStream.read(bytes);
            System.out.println(bytes.length);
            try (PreparedStatement statement1 =
                         connection.prepareStatement("insert into carblob(name,speed,img) values(?,?,?)");) {
                statement1.setString(1, "BMW");
                statement1.setInt(2, 200);
                statement1.setBytes(3, bytes);
                statement1.execute();
            }
        }
        }
    }
}
