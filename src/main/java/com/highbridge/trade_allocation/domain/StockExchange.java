package com.highbridge.trade_allocation.domain;

import com.highbridge.trade_allocation.domain.generic.Money;

import java.util.HashMap;
import java.util.Map;

public class StockExchange {
    private final Map<String, Money> prices;

    public StockExchange() {
        this.prices = new HashMap<>();
    }

    public void add(String stock, Money price) {
        this.prices.put(stock, price);
    }

    public Money price(String stock) {
        return prices.get(stock);
    }
}
