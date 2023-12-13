package edu.software.order;

public class BeardDecorator extends BaseDecorator {
    public BeardDecorator(Order order) {
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
