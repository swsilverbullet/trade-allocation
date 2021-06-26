package com.highbridge.trade_allocation;

import com.highbridge.trade_allocation.domain.sub.Money;

public class Account {
    private final String investor;
    private final Money capital;
    private String stockSymbol;
    private double targetPercent;

    public Account(String investor, Money capital) {
        this.investor = investor;
        this.capital = capital;
    }

    public void setTargetPercent(String stockSymbol, double targetPercent) {
        this.stockSymbol = stockSymbol;
        this.targetPercent = targetPercent;
    }

    public Integer maxShare(Stock stock) {
        return marketValue(stock).getAmount().divide(stock.price().getAmount()).intValue();
    }

    public Money marketValue(Stock stock) {
        if (stock.symbol().equals(stockSymbol)) {
            return capital.times(targetPercent).times(0.01);
        }
        return Money.dollars(0);
    }
}
