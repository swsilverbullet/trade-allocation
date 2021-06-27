package com.highbridge.trade_allocation.domain;

import com.highbridge.trade_allocation.domain.generic.Money;
import com.highbridge.trade_allocation.domain.generic.Percent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public void add(Holding holding) {
        if (this.holdings.containsKey(holding.stock())) {
            this.holdings.put(holding.stock(), this.holdings.get(holding.stock()).merge(holding));
        }
        else {
            this.holdings.put(holding.stock(), holding);
        }
    }

    public void add(String stock, Percent targetPercent) {
        this.targetPercents.put(stock, targetPercent);
    }

    public Integer maxShare(String stock, Money price) {
        return marketValue(stock).getAmount().divide(price.getAmount()).intValue();
    }

    public Integer currentShare(String stock) {
        // TODO BL - assuming that we have only one stock in place in holdings
        return holdings.values().stream().filter(h -> h.stock().equals(stock)).mapToInt(h -> h.quantity()).sum();
    }

    public Integer availableShare(String stock, Money price) {
        return maxShare(stock, price) - currentShare(stock);
    }

    public Money marketValue(String stock) {
        return targetPercents.get(stock).of(capital);
    }

    public String investor() {
        return this.investor;
    }
}
