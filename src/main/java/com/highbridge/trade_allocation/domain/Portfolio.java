package com.highbridge.trade_allocation.domain;

import com.highbridge.trade_allocation.domain.generic.Money;
import org.javatuples.Pair;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class Portfolio {
    private final Map<String, Account> accounts;

    public Portfolio() {
        this.accounts = new HashMap<>();
    }

    void add(Account account) {
        this.accounts.put(account.investor(), account);
    }

    Long entitledToBuyUpTo(String stock, Money price) {
        return accounts.values().stream().mapToLong(a -> a.quantityCanBeAdded(stock, price)).sum();
    }

    Long entitledToSellUpTo(String stock) {
        return accounts.values().stream().mapToLong(a -> a.currentQuantity(stock)).sum();
    }

    Double suggestedFinalPosition(Account account, Trade newTrade) {
        Money accountTargetMarketValue = account.targetMarketValue(newTrade.stock());
        Money portfolioTotalTargetMarketValue = totalTargetMarketValue(newTrade.stock());
        return accountTargetMarketValue.times(allInPosition(newTrade)).divide(portfolioTotalTargetMarketValue).getAmount().doubleValue();
    }

    Long allInPosition(Trade newTrade) {
        return totalQuantity(newTrade.stock()) + newTrade.singedQuantity();
    }

    Double suggestedTradeAllocation(Account account, Trade newTrade) {
        Double suggestedFinalPosition = suggestedFinalPosition(account, newTrade);
        return BigDecimal.valueOf(suggestedFinalPosition).add(BigDecimal.valueOf(account.currentQuantity(newTrade.stock())).setScale(2).negate()).doubleValue();
    }

    private Money totalTargetMarketValue(String stock) {
        return Money.dollars(accounts.values().stream().mapToDouble(a -> a.targetMarketValue(stock).getAmount().doubleValue()).sum());
    }

    private Long totalQuantity(String stock) {
        return accounts.values().stream().mapToLong(a -> a.currentQuantity(stock)).sum();
    }

    void reallocateHoldings(Trade newTrade) {
        List<Pair<String, Long>> additionalPositions = orderedAdditionalPositions(newTrade);

        Long remainingShare = newTrade.singedQuantity();
        for (int i = 0; i < additionalPositions.size(); i++) {
            Account a = accounts.get(additionalPositions.get(i).getValue0());
            if (i < additionalPositions.size() - 1) {
                Long additionalShare = additionalPositions.get(i).getValue1();
                a.addHolding(new Holding(newTrade.stock(), newTrade.price(), additionalShare));
                remainingShare -= additionalShare;
            }
            else {
                a.addHolding(new Holding(newTrade.stock(), newTrade.price(), remainingShare));
            }
        }
    }

    private List<Pair<String, Long>> orderedAdditionalPositions(Trade trade) {
        List<Account> ordered = new ArrayList<>(accounts.values());
        Collections.sort(ordered, new AllocationAscendingComparator(this, trade));

        return ordered.stream()
                .map(a -> new Pair<>(a.investor(), Math.round(this.suggestedTradeAllocation(a, trade))))
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