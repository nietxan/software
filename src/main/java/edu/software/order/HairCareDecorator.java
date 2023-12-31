package edu.software.order;

public class HairCareDecorator extends BaseDecorator {
    public HairCareDecorator(Order order) {
        super(order);
    }

    @Override
    public String description() {
        return order.description() + " + hair care";
    }

    @Override
    public Float cost() {
        return order.cost() + 250f;
    }
}
