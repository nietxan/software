package edu.software.Record;

import edu.software.Order.Order;

import java.sql.Date;

public record Record(User user, Barber barber, Order order, Date date) {}
