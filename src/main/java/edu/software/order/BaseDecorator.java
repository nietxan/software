package edu.software.order;

public abstract class BaseDecorator implements Order {
    protected Order order;

    public BaseDecorator(Order order) {
        this.order = order;
    }
}
