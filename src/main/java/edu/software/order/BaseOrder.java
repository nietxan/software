package edu.software.order;

public record BaseOrder(String description, Float cost) implements Order {
    @Override
    public String toString() {
        return description;
    }
}
