package com.highbridge.trade_allocation.domain;

import com.highbridge.trade_allocation.domain.sub.Money;

import java.util.ArrayList;
import java.util.List;

public class Portfolio {
    private List<Account> accounts;

    public Portfolio() {
        this.accounts = new ArrayList<>();
    }

    public void add(Account account) {
        this.accounts.add(account);
    }

    public Integer entitledToBuyUpTo(String stockSymbol, Money price) {
        return accounts.stream().mapToInt(a -> a.availableShare(new Stock(stockSymbol), price)).sum();
    }
}
