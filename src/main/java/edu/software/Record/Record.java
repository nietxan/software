package edu.software.Record;

import edu.software.Order.Order;

import java.sql.Date;

public record Record(Integer id, User user, Barber barber, Order order, Date date) {}
