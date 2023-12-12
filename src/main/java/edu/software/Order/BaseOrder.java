package edu.software.Order;

public record BaseOrder(String description, Float cost) implements Order {}
