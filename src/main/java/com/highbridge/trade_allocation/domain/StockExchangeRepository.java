package com.highbridge.trade_allocation.domain;

import com.highbridge.trade_allocation.domain.sub.Money;

import java.util.HashMap;
import java.util.Map;

public class StockExchangeRepository {
    private final Map<String, Stock> stocks;

    public StockExchangeRepository() {
        this.stocks = new HashMap<>();
    }

    public Stock findBySymbol(String symbol) {
        return stocks.get(symbol);
    }

    public void add(Stock stock) {
        this.stocks.put(stock.symbol(), stock);
    }

    public Money price(String stockSymbol) {
        return stocks.get(stockSymbol).price();
    }
}
