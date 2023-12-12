package edu.software.Order;

public class HairDecorator extends BaseDecorator {
    public HairDecorator(Order order) {
        super(order);
    }

    @Override
    public String description() {
        return order.description();
    }

    @Override
    public Float cost() {
        return order.cost();
    }
}
