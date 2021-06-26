package com.highbridge.trade_allocation.domain;

import com.highbridge.trade_allocation.domain.sub.Money;
import com.highbridge.trade_allocation.domain.sub.Percent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Account {
    private final String investor;
    private final Money capital;

    private final List<Holding> holdings;
    private final Map<String, Percent> targetPercents;
    private final Map<String, Money> stockPrices;

    public Account(String investor, Money capital) {
        this.investor = investor;
        this.capital = capital;
        this.holdings = new ArrayList<>();
        this.targetPercents = new HashMap<>();
        this.stockPrices = new HashMap<>();
    }

    public void add(Holding holding) {
        this.holdings.add(holding);
    }

    public void add(Stock stock) {
        this.stockPrices.put(stock.symbol(), stock.price());
    }

    public void add(String stockSymbol, Percent targetPercent) {
        this.targetPercents.put(stockSymbol, targetPercent);
    }

    public Integer maxShare(Stock stock) {
        return marketValue(stock).getAmount().divide(stock.price().getAmount()).intValue();
    }

    public Money marketValue(Stock stock) {
        return targetPercents.get(stock.symbol()).of(capital);
    }

    public String investor() {
        return this.investor;
    }
}
