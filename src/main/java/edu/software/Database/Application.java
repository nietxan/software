package edu.software.Database;

import java.sql.SQLException;

public class Application {
    public static void main(String[] args) {
        Database database = Database.getDatabase();

        try {
            database.execute("SELECT * FROM test");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}