package com.highbridge.trade_allocation.domain;

import com.highbridge.trade_allocation.domain.sub.Money;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Portfolio {
    private final Map<String, Account> accounts;
    private final Map<String, Integer> newTrade;

    public Portfolio() {
        this.accounts = new HashMap<>();
        this.newTrade = new HashMap<>();
    }

    public void add(Account account) {
        this.accounts.put(account.investor(), account);
    }

    public void addNewTrade(String stock, Integer quantity) {
        this.newTrade.put(stock, quantity);
    }

    public Integer entitledToBuyUpTo(String stockSymbol, Money price) {
        return accounts.values().stream().mapToInt(a -> a.availableShare(new Stock(stockSymbol), price)).sum();
    }

    public Double suggestedFinalPosition(Account account, Stock stock) {
        Money accountMarketValue = account.marketValue(stock);
        Integer allShare = allShareInPosition(stock, newTrade.get(stock.symbol()));
        return accountMarketValue.times(allShare).divide(totalTargetMarketValue(stock)).getAmount().doubleValue();
    }

    public Double suggestedAdditionalPosition(Account account, Stock stock) {
        return BigDecimal.valueOf(suggestedFinalPosition(account, stock)).add(BigDecimal.valueOf(account.currentShare(stock)).setScale(2).negate()).doubleValue();
    }

    private Money totalTargetMarketValue(Stock stock) {
        return Money.dollars(accounts.values().stream().mapToDouble(a -> a.marketValue(stock).getAmount().doubleValue()).sum());
    }

    private Integer allShareInPosition(Stock stock, Integer newShare) {
        return accounts.values().stream().mapToInt(a -> a.currentShare(stock)).sum() + newShare;
    }
}
