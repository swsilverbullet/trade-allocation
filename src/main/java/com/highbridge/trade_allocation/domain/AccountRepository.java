package com.highbridge.trade_allocation.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountRepository {

    private Map<String, Account> accounts;

    public AccountRepository() {
        this.accounts = new HashMap<>();
    }

    Account findByInvestor(String investor) {
        return accounts.get(investor);
    }

    List<Account> all() {
        return new ArrayList<>(accounts.values());
    }

    void addAccount(Account account) {
        this.accounts.put(account.investor(), account);
    }
}