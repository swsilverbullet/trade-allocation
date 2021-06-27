package com.highbridge.trade_allocation.domain;

import com.highbridge.trade_allocation.domain.generic.Money;

import java.util.HashMap;
import java.util.Map;

public class StockExchangeRepository {
    private final Map<String, Money> prices;

    public StockExchangeRepository() {
        this.prices = new HashMap<>();
    }

    public void add(String stock, Money price) {
        this.prices.put(stock, price);
    }

    public Money price(String stockSymbol) {
        return prices.get(stockSymbol);
    }
}
