package ru.ezhov.test.firefox;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FaviconRestore {
    private static final String FAVICON_DB_FIREFOX_PATH =
            "C:/Users/ezhov/AppData/Roaming/Mozilla/Firefox/Profiles/6ohmnlhc.default/favicons.sqlite";

    public static void main(String[] args) {
        try (Connection connection = createSqlLiteConnection()) {
            try (PreparedStatement ps =
                         connection.prepareStatement("SELECT * FROM moz_icons")) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String id = rs.getString("id");
                        String url = rs.getString("icon_url");

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Connection createSqlLiteConnection() throws Exception {
        return DriverManager.getConnection("jdbc:sqlite:" + FAVICON_DB_FIREFOX_PATH);
    }
}
