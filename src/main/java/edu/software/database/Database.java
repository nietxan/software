package edu.software.database;

import edu.software.order.BaseOrder;
import edu.software.order.Order;
import edu.software.record.Barber;
import edu.software.record.Record;
import edu.software.record.User;
import edu.software.record.Receiver;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// Singleton
public class Database implements Receiver {
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
        Timestamp date = record.date();

        String query = String.format("INSERT INTO records VALUES (%d, %d, %d, '%s', %f, '%s')",
                record.id(),
                user.id(),
                barber.id(),
                order.description(),
                order.cost(),
                date
        );

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteRecord(Record record) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    String.format("DELETE FROM records WHERE record_id = %d", record.id())
            );

            statement.executeUpdate();
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

    public List<Record> getRecordList(ResultSet set) {
        List<Record> recordList = new ArrayList<>();

        try {
            while (set.next()) {
                recordList.add(
                        new Record(
                                set.getInt(1),
                                getUser(set.getInt(2)),
                                getBarber(set.getInt(3)),
                                new BaseOrder(set.getString(4), set.getFloat(5)),
                                set.getTimestamp(6)
                        )
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return recordList;
    }

    public List<Record> getRecordList(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    String.format("SELECT * FROM records WHERE user_id = '%s'", user.id())
            );
            return getRecordList(statement.executeQuery());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>();
    }

    public List<Record> getRecordList(Barber barber) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    String.format("SELECT * FROM records WHERE barber_id = '%s'", barber.id())
            );
            return getRecordList(statement.executeQuery());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>();
    }

    public Barber getBarber(int barber_id) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    String.format("SELECT * FROM barbers WHERE barber_id = %d", barber_id)
            );
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return new Barber(
                        set.getInt(1),
                        set.getString(2),
                        set.getString(3)
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public User getUser(int user_id) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    String.format("SELECT * FROM users WHERE user_id = %d", user_id)
            );
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return new User(
                        set.getInt(1),
                        set.getString(2),
                        set.getString(3),
                        set.getString(4)
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;

    }

    public int getLastId(ResultSet set) {
        try {
            if (set.next()) {
                return set.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public int getLastUserId() {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT user_id FROM users ORDER BY user_id DESC LIMIT 1"
            );
            return getLastId(statement.executeQuery());
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
            return getLastId(statement.executeQuery());
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

    public int checkBarber(String username, String password) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    String.format("SELECT barber_password FROM barbers WHERE barber_name = '%s'", username)
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

    public User getUser(String username) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    String.format("SELECT * FROM users WHERE user_name = '%s'", username)
            );
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return new User(
                        set.getInt(1), set.getString(2),
                        set.getString(3), set.getString(4)
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Barber getBarber(String barber_name) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    String.format("SELECT * FROM barbers WHERE barber_name = '%s'", barber_name)
            );
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return new Barber(
                        set.getInt(1), set.getString(2),
                        set.getString(3)
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Integer> getRecordsTimeOfDay(LocalDate date) {
        List<Integer> timeList = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(
                    String.format(
                            "SELECT extract(hour from date) AS h " +
                            "FROM records WHERE DATE(date) = '%s' ORDER BY h",
                            date.format(DateTimeFormatter.ISO_DATE)
                    )
            );

            ResultSet set = statement.executeQuery();
            while (set.next()) {
                timeList.add(set.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return timeList;
    }


    @Override
    public void update(Record record) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    String.format(
                            "UPDATE records SET barber_id = %d, order_description = '%s', order_cost = %f, date = '%s' WHERE record_id = %d",
                            record.barber().id(),
                            record.order().description(),
                            record.order().cost(),
                            record.date(),
                            record.id()
                    )
            );
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
