package edu.software.Database;

import edu.software.Order.Order;
import edu.software.Record.Barber;
import edu.software.Record.Record;
import edu.software.Record.User;

import java.sql.*;

// Singleton
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

    public void insertUser(User user) {
        String query = String.format("INSERT INTO users VALUES (%d, %s, %s, %s)",
                user.id(),
                user.name(),
                user.phoneNumber(),
                user.password()
        );

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.execute();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertRecord(Record record) {
        User user = record.user();
        Barber barber = record.barber();
        Order order = record.order();
        Date date = record.date();

        String query = String.format("INSERT INTO records VALUES (%s, %s, %s, %s)",
                user.id(),
                barber.id(),
                order.description(),
                date.toString()
        );

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateRecord(Record record, Barber barber) {
        String query = String.format("UPDATE records SET barber_id=%d WHERE record_id=%d",
                barber.id(),
                record.id()
        );

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateRecord(Record record, Order order) {
        String query = String.format("UPDATE records SET order_description='%s' WHERE record_id=%d",
                order.description(),
                record.id()
        );

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateRecord(Record record, Date date) {
        String query = String.format("UPDATE records SET date='%s' WHERE record_id=%d",
                date.toString(),
                record.id()
        );

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
