package edu.software.order;

public record BaseOrder(String description, Float cost) implements Order {}
