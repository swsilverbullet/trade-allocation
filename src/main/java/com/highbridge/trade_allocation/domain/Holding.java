package com.highbridge.trade_allocation.domain;

import com.highbridge.trade_allocation.domain.sub.Money;

public class Holding {
    private final String stockSymbol;
    private final Money price;
    private final Integer quantity;

    public Holding(String stockSymbol, Money price, Integer quantity) {
        this.stockSymbol = stockSymbol;
        this.price = price;
        this.quantity = quantity;
    }
}
