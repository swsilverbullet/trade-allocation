package com.highbridge.trade_allocation.domain;

import java.util.HashMap;
import java.util.Map;

public class AccountRepository {

    private Map<String, Account> accounts;

    public AccountRepository() {
        this.accounts = new HashMap<>();
    }

    public Account findByInvestor(String investor) {
        return accounts.get(investor);
    }

    public void addAccount(Account account) {
        this.accounts.put(account.investor(), account);
    }
}