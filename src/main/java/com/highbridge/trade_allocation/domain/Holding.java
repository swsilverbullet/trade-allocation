package com.highbridge.trade_allocation.domain;

import com.highbridge.trade_allocation.domain.generic.Money;

public class Holding {
    private final String stock;
    private final Money price;
    private final Integer quantity;

    public Holding(String stock, Money price, Integer quantity) {
        this.stock = stock;
        this.price = price;
        this.quantity = quantity;
    }

    String stock() {
        return stock;
    }

    Integer quantity() {
        return quantity;
    }

    Money marketValue() {
        return price.times(quantity);
    }

    Holding merge(Holding other) {
        if (this.stock.equals(other.stock) && this.price.equals(other.price)) {
            return new Holding(this.stock, this.price, this.quantity + other.quantity);
        }
        throw new IllegalStateException("different stock or price - cannot merge the holding");
    }
}