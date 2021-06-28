package com.highbridge.trade_allocation.domain;

import com.highbridge.trade_allocation.domain.generic.Money;

public class Holding {
    private final String stock;
    private final Money price;
    private final Long quantity;

    public Holding(String stock, Money price, Long quantity) {
        this.stock = stock;
        this.price = price;
        this.quantity = quantity;
    }

    String stock() {
        return stock;
    }

    Long quantity() {
        return quantity;
    }

    // TODO BL - Not used anywhere. but keep this here followed by instruction
    Money marketValue() {
        return price.times(quantity);
    }

    Holding merge(Holding other) {
        if (this.stock.equals(other.stock) && this.price.equals(other.price)) {
            return withQuantity(this.quantity + other.quantity);
        }
        throw new IllegalStateException("different stock or price - cannot merge the holding");
    }

    Holding withQuantity(Long quantity) {
        return new Holding(this.stock, this.price, quantity);
    }
}