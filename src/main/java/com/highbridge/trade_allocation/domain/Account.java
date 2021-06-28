package com.highbridge.trade_allocation.domain;

import com.highbridge.trade_allocation.domain.generic.Money;
import com.highbridge.trade_allocation.domain.generic.Percent;

import java.util.HashMap;
import java.util.Map;

public class Account {
    private final String investor;
    private final Money capital;

    private final Map<String, Holding> holdings;
    private final Map<String, Percent> stockTargetPercent;

    public Account(String investor, Money capital) {
        this.investor = investor;
        this.capital = capital;
        this.holdings = new HashMap<>();
        this.stockTargetPercent = new HashMap<>();
    }

    void addHolding(Holding holding) {
        if (this.holdings.containsKey(holding.stock())) {
            this.holdings.put(holding.stock(), this.holdings.get(holding.stock()).merge(holding));
        }
        else {
            this.holdings.put(holding.stock(), holding);
        }
    }

    void addStockTargetPercent(String stock, Percent targetPercent) {
        this.stockTargetPercent.put(stock, targetPercent);
    }

    Money targetMarketValue(String stock) {
        return stockTargetPercent.get(stock).of(capital);
    }

    Integer targetMarketQuantity(String stock, Money price) {
        return targetMarketValue(stock).divide(price).getAmount().intValue();
    }

    Integer currentQuantity(String stock) {
        return holdings.get(stock).quantity();
    }

    Integer quantityCanBeAdded(String stock, Money price) {
        return targetMarketQuantity(stock, price) - currentQuantity(stock);
    }

    String investor() {
        return this.investor;
    }
}
