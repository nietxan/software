package edu.software.order;

public class HairDecorator extends BaseDecorator {
    public HairDecorator(Order order) {
        super(order);
    }

    @Override
    public String description() {
        return order.description() + " + hair";
    }

    @Override
    public Float cost() {
        return order.cost();
    }
}
