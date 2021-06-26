package com.highbridge.trade_allocation;

import com.highbridge.trade_allocation.domain.sub.Money;

public class Stock {
    private final String symbol;
    private final Money price;

    public Stock(String stockSymbol, Money currentPrice) {
        this.symbol = stockSymbol;
        this.price = currentPrice;
    }

    public String symbol() {
        return symbol;
    }

    public Money price() {
        return price;
    }
}