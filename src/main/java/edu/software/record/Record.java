package edu.software.record;

import edu.software.order.Order;

import java.sql.Timestamp;

public record Record(Integer id, User user, Barber barber, Order order, Timestamp date) {

    public String getBarber() {
        return barber.name();
    }

    public String getDescription() {
        return order.description();
    }

    public Float getCost() {
        return order.cost();
    }

    public String getDate() {
        return date.toString();
    }

    @Override
    public String toString() {
        return barber.name() + ' ' +
                order.description() + ' ' +
                order.cost() + ' ' +
                date;
    }
}
