package com.highbridge.trade_allocation.domain;

import com.highbridge.trade_allocation.domain.generic.Money;
import com.highbridge.trade_allocation.domain.generic.Percent;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

    public void addHolding(Holding holding) {
        if (this.holdings.containsKey(holding.stock())) {
            this.holdings.put(holding.stock(), this.holdings.get(holding.stock()).merge(holding));
        }
        else {
            this.holdings.put(holding.stock(), holding);
        }
    }

    void setToZeroQuantity(String stock, Money price) {
        if (this.holdings.containsKey(stock)) {
            this.holdings.put(stock, this.holdings.get(stock).withQuantity(0L));
        }
        else {
            this.holdings.put(stock, new Holding(stock, price, 0L));
        }
    }

    public void addStockTargetPercent(String stock, Percent targetPercent) {
        this.stockTargetPercent.put(stock, targetPercent);
    }

    public Money targetMarketValue(String stock) {
        if (stockTargetPercent.get(stock) != null) {
            return stockTargetPercent.get(stock).of(capital);
        }
        return Money.dollars(0);
    }

    public Long targetMarketQuantity(String stock, Money price) {
        return targetMarketValue(stock).divide(price).getAmount().longValue();
    }

    public Long currentQuantity(String stock) {
        if (holdings.get(stock) != null) {
            return holdings.get(stock).quantity();
        }
        return 0L;
    }

    Long quantityCanBeAdded(String stock, Money price) {
        return targetMarketQuantity(stock, price) - currentQuantity(stock);
    }

    public Set<String> holdStocks() {
        return holdings.keySet();
    }

    public String investor() {
        return this.investor;
    }
}
