package edu.software.Database;

import java.sql.*;

// Singleton
public class Database {
    private static Database database;
    private final Connection connection;

    private Database() {
        try {
            Class.forName("java.sql.Driver");

            String jdbcURL = "jdbc:postgresql://localhost:5432/barbershop";
            String username = "barber";
            String password = "qwerty123";

            connection = DriverManager.getConnection(jdbcURL, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized static Database getDatabase() {
        if (database == null) {
            database = new Database();
        }
        return database;
    }

    public void execute(String query) throws SQLException {
        if (connection == null) {
            throw new SQLException("Connection not established");
        }

        PreparedStatement statement = connection.prepareStatement(query);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            System.out.println(resultSet.getInt(0) + " " + resultSet.getString(1));
        }
    }
}
