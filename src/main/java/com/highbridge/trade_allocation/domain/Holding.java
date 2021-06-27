package com.highbridge.trade_allocation.domain;

public class Holding {
    private final String stock;
    private final Integer quantity;

    public Holding(String stock, Integer quantity) {
        this.stock = stock;
        this.quantity = quantity;
    }

    public String stock() {
        return stock;
    }

    public Integer quantity() {
        return quantity;
    }

    public Holding merge(Holding other) {
        if (this.stock.equals(other.stock)) {
            return new Holding(this.stock, this.quantity + other.quantity);
        }
        throw new IllegalStateException("different stock - cannot merge the holding");
    }
}