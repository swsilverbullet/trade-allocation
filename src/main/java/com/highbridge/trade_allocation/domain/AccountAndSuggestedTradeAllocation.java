package com.highbridge.trade_allocation.domain;

import org.javatuples.Pair;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AccountAndSuggestedTradeAllocation {
    private final Portfolio portfolio;

    public AccountAndSuggestedTradeAllocation(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    List<Pair<Account, Long>> ordered(Trade trade) {
        List<Account> ordered = portfolio.accounts();
        Collections.sort(ordered, new AllocationAscendingComparator(portfolio, trade));

        return ordered.stream()
                .map(a -> new Pair<>(a, Math.round(portfolio.suggestedTradeAllocation(a, trade))))
                .collect(Collectors.toList());
    }
}


class AllocationAscendingComparator implements Comparator<Account> {
    private final Portfolio portfolio;
    private final Trade trade;

    public AllocationAscendingComparator(Portfolio portfolio, Trade trade) {
        this.portfolio = portfolio;
        this.trade = trade;
    }

    @Override
    public int compare(Account a1, Account a2) {
        return this.portfolio.suggestedTradeAllocation(a1, trade).compareTo(this.portfolio.suggestedTradeAllocation(a2, trade));
    }
}