package com.highbridge.trade_allocation.domain;

import com.highbridge.trade_allocation.domain.sub.Money;
import com.highbridge.trade_allocation.domain.sub.Percent;

public class Account {
    private final String investor;
    private final Money capital;
    private String stockSymbol;
    private Percent targetPercent;

    public Account(String investor, Money capital) {
        this.investor = investor;
        this.capital = capital;
    }

    public void setTargetPercent(String stockSymbol, Percent targetPercent) {
        this.stockSymbol = stockSymbol;
        this.targetPercent = targetPercent;
    }

    public Integer maxShare(Stock stock) {
        return marketValue(stock).getAmount().divide(stock.price().getAmount()).intValue();
    }

    public Money marketValue(Stock stock) {
        if (stock.symbol().equals(stockSymbol)) {
            return targetPercent.of(capital);
        }
        return Money.dollars(0);
    }
}
