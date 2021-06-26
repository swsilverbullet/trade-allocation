package com.highbridge.trade_allocation.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AccountRepository {

    private Map<String, Account> accounts;

    public AccountRepository() {
        this.accounts = new HashMap<>();
    }

    public Optional<Account> findByInvestor(String investor) {
        return Optional.ofNullable(accounts.get(investor));
    }

    public void addAccount(Account account) {
        this.accounts.put(account.investor(), account);
    }
}