package edu.software.record;

import edu.software.order.Order;

import java.sql.Timestamp;

public record Record(Integer id, User user, Barber barber, Order order, Timestamp date) {
    @Override
    public String toString() {
        return  barber.name() + ' ' +
                order.description() + ' ' +
                order.cost() + ' ' +
                date;
    }
}
