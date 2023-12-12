package edu.software.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Singleton
// TODO: Connection established but, auth failed
public class Database {
    private static Database database;
    private Connection connection;

    private Database() {
        try {
            String jdbcURL = "jdbc:postgresql://localhost:5432/soft_db";
            String username = "soft_admin";
            String password = "admin";

            connection = DriverManager.getConnection(jdbcURL, username, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public synchronized static Database getDatabase() {
        if (database == null) {
            database = new Database();
        }
        return database;
    }

    public Connection getConnection() {
        return connection;
    }
}
