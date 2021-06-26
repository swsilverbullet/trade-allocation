package com.highbridge.trade_allocation;

public class Stock {
    private final String symbol;
    private final double price;

    public Stock(String stockSymbol, double currentPrice) {
        this.symbol = stockSymbol;
        this.price = currentPrice;
    }

    public String symbol() {
        return symbol;
    }

    public double price() {
        return price;
    }
}