package com.praveen.AI.Bargaining.model;

import lombok.NoArgsConstructor;


public class ProductRequest {

    private String name;
    private String description;
    private double price;
    private int quantity;
    private double targetPrice;
    private String quantityRange;
    private String negotiationStrategy;
    private int timeoutSeconds;

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTargetPrice() {
        return targetPrice;
    }

    public void setTargetPrice(double targetPrice) {
        this.targetPrice = targetPrice;
    }

    public String getQuantityRange() {
        return quantityRange;
    }

    public void setQuantityRange(String quantityRange) {
        this.quantityRange = quantityRange;
    }

    public String getNegotiationStrategy() {
        return negotiationStrategy;
    }

    public void setNegotiationStrategy(String negotiationStrategy) {
        this.negotiationStrategy = negotiationStrategy;
    }

    public int getTimeoutSeconds() {
        return timeoutSeconds;
    }

    public void setTimeoutSeconds(int timeoutSeconds) {
        this.timeoutSeconds = timeoutSeconds;
    }
}