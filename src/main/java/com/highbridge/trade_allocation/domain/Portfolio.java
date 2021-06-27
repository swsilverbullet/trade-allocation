package com.highbridge.trade_allocation.domain;

import com.highbridge.trade_allocation.domain.generic.Money;
import org.javatuples.Pair;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class Portfolio {
    private final Map<String, Account> accounts;
    private final Map<String, Trade> trades;

    public Portfolio() {
        this.accounts = new HashMap<>();
        this.trades = new HashMap<>();
    }

    void add(Account account) {
        this.accounts.put(account.investor(), account);
    }

    void addBuyTrade(String stock, Integer quantity) {
        this.trades.put(stock, Trade.buy(stock, quantity, Money.dollars(0)));
    }

    void addSellTrade(String stock, Integer quantity) {
        this.trades.put(stock, Trade.sell(stock, quantity, Money.dollars(0)));
    }

    Integer entitledToBuyUpTo(String stock, Money price) {
        return accounts.values().stream().mapToInt(a -> a.quantityCanBeAdded(stock, price)).sum();
    }

    Integer entitledToSellUpTo(String stock) {
        return accounts.values().stream().mapToInt(a -> a.stockHoldingQuantity(stock)).sum();
    }

    Double suggestedFinalPosition(Account account, String stock) {
        Money accountStockHoldingCap = account.stockHoldingMoneyCap(stock);
        Money portfolioStockHoldingCap = stockHoldingCap(stock);
        Integer quantityToBeReAllocated = quantity(stock) + trades.get(stock).singedQuantity();
        return accountStockHoldingCap.times(quantityToBeReAllocated).divide(portfolioStockHoldingCap).getAmount().doubleValue();
    }

    Double suggestedAdditionalPosition(Account account, String stock) {
        return BigDecimal.valueOf(suggestedFinalPosition(account, stock)).add(BigDecimal.valueOf(account.stockHoldingQuantity(stock)).setScale(2).negate()).doubleValue();
    }

    private Money stockHoldingCap(String stock) {
        return Money.dollars(accounts.values().stream().mapToDouble(a -> a.stockHoldingMoneyCap(stock).getAmount().doubleValue()).sum());
    }

    private Integer quantity(String stock) {
        return accounts.values().stream().mapToInt(a -> a.stockHoldingQuantity(stock)).sum();
    }

    void reallocateHoldings(String stock) {
        List<Pair<String, Integer>> additionalPositions = orderedAdditionalPositions(stock);

        Integer remainingShare = trades.get(stock).singedQuantity();
        for (int i = 0; i < additionalPositions.size(); i++) {
            Account a = accounts.get(additionalPositions.get(i).getValue0());
            if (i < additionalPositions.size() - 1) {
                Integer additionalShare = additionalPositions.get(i).getValue1();
                a.addHolding(new Holding(stock, additionalShare));
                remainingShare -= additionalShare;
            }
            else {
                a.addHolding(new Holding(stock, remainingShare));
            }
        }
    }

    private List<Pair<String, Integer>> orderedAdditionalPositions(String stock) {
        List<Account> ordered = new ArrayList<>(accounts.values());
        Collections.sort(ordered, new AllocationAscendingComparator(this, stock));

        return ordered.stream()
                .map(a -> new Pair<>(a.investor(), round(this.suggestedAdditionalPosition(a, stock))))
                .collect(Collectors.toList());
    }

    private Integer round(Double d) {
        return Long.valueOf(Math.round(d)).intValue(); //NOTE: practically speaking, int conversion is safe here
    }

    class AllocationAscendingComparator implements Comparator<Account> {
        private final Portfolio portfolio;
        private final String stock;

        public AllocationAscendingComparator(Portfolio portfolio, String stock) {
            this.portfolio = portfolio;
            this.stock = stock;
        }

        @Override
        public int compare(Account a1, Account a2) {
            return this.portfolio.suggestedAdditionalPosition(a1, stock).compareTo(this.portfolio.suggestedAdditionalPosition(a2, stock));
        }
    }
}
