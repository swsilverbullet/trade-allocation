package com.highbridge.trade_allocation.domain;

import com.highbridge.trade_allocation.domain.generic.Money;
import com.highbridge.trade_allocation.domain.generic.Percent;

import java.util.HashMap;
import java.util.Map;

public class Account {
    private final String investor;
    private final Money capital;

    private final Map<String, Holding> holdings;
    private final Map<String, Percent> targetPercents;

    public Account(String investor, Money capital) {
        this.investor = investor;
        this.capital = capital;
        this.holdings = new HashMap<>();
        this.targetPercents = new HashMap<>();
    }

    void add(Holding holding) {
        if (this.holdings.containsKey(holding.stock())) {
            this.holdings.put(holding.stock(), this.holdings.get(holding.stock()).merge(holding));
        }
        else {
            this.holdings.put(holding.stock(), holding);
        }
    }

    void add(String stock, Percent targetPercent) {
        this.targetPercents.put(stock, targetPercent);
    }

    Integer maxShare(String stock, Money price) {
        return stockHoldingCap(stock).getAmount().divide(price.getAmount()).intValue();
    }

    Integer currentShare(String stock) {
        // TODO BL - assuming that we have only one stock in place in holdings
        return holdings.values().stream().filter(h -> h.stock().equals(stock)).mapToInt(h -> h.quantity()).sum();
    }

    Integer availableShare(String stock, Money price) {
        return maxShare(stock, price) - currentShare(stock);
    }

    // maximumHoldingValue
    Money stockHoldingCap(String stock) {
        return targetPercents.get(stock).of(capital);
    }

    String investor() {
        return this.investor;
    }
}
