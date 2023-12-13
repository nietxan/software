package edu.software.database;

import edu.software.order.Order;
import edu.software.record.Barber;
import edu.software.record.Record;
import edu.software.record.User;
import edu.software.updater.Update;

import java.sql.*;

// Singleton
public class Database implements Update {
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

    @Override
    public void update(Object... args) {
        Record record = (Record) args[0];

        for (Object obj : args) {
            try {
                switch (obj.getClass().getName()) {
                    case "Barber": {
                        Barber barber = (Barber) obj;

                        String query = String.format("UPDATE records SET barber_id=%d WHERE record_id=%d",
                                barber.id(),
                                record.id()
                        );

                        PreparedStatement statement = connection.prepareStatement(query);
                        statement.execute();
                        break;
                    }
                    case "Order": {
                        Order order = (Order) obj;

                        String query = String.format("UPDATE records SET order_description='%s' WHERE record_id=%d",
                                order.description(),
                                record.id()
                        );

                        PreparedStatement statement = connection.prepareStatement(query);
                        statement.execute();
                        break;
                    }
                    case "Date": {
                        Date date = (Date) obj;

                        String query = String.format("UPDATE records SET date='%s' WHERE record_id=%d",
                                date,
                                record.id()
                        );

                        PreparedStatement statement = connection.prepareStatement(query);
                        statement.execute();
                        break;
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
