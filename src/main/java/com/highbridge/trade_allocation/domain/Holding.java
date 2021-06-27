package com.highbridge.trade_allocation.domain;

public class Holding {
    private final String stockSymbol;
    private final Integer quantity;

    public Holding(String stockSymbol, Integer quantity) {
        this.stockSymbol = stockSymbol;
        this.quantity = quantity;
    }

    public String stockSymbol() {
        return stockSymbol;
    }

    public Integer quantity() {
        return quantity;
    }
}
