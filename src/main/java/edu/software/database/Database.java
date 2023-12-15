package edu.software.database;

import edu.software.order.Order;
import edu.software.record.Barber;
import edu.software.record.Record;
import edu.software.record.User;
import edu.software.updater.Update;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        String query = String.format("INSERT INTO users VALUES (%d, '%s', '%s', '%s')",
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

        String query = String.format("INSERT INTO records VALUES (%d, '%s', '%s', '%s')",
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

    public List<Barber> getBarberList() {
        List<Barber> barberList = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM barbers");
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                barberList.add(new Barber(
                        set.getInt(1),
                        set.getString(2),
                        set.getString(3)
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return barberList;
    }

    public int getLastUserId() {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT user_id FROM users ORDER BY user_id DESC LIMIT 1"
            );
            ResultSet set = statement.executeQuery();

            if (set.next()) {
                return set.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public int getLastRecordId() {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT record_id FROM records ORDER BY record_id DESC LIMIT 1"
            );
            ResultSet set = statement.executeQuery();

            if (set.next()) {
                return set.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return 0;
    }

    public int checkUser(String username, String password) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    String.format("SELECT user_password FROM users WHERE user_name = '%s'", username)
            );

            ResultSet set = statement.executeQuery();

            if (set.next()) {
                String str = set.getString(1);

                if (str.equals(password)) {
                    return 0;
                } else {
                    return 1;
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return -1;
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
