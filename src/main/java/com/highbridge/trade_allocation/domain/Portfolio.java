package com.highbridge.trade_allocation.domain;

import com.highbridge.trade_allocation.domain.sub.Money;
import org.javatuples.Pair;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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

    public void allocateNew(String stock) {
        List<Pair<String, Integer>> additionalPositions = orderedAdditionalPositions(new Stock(stock));

        Integer remainingShare = newTrade.get(stock);
        for (int i = 0; i < additionalPositions.size(); i++) {
            Account a = accounts.get(additionalPositions.get(i).getValue0());
            if (i < additionalPositions.size() - 1) {
                Integer additionalShare = additionalPositions.get(i).getValue1();
                a.add(new Holding(stock, Money.dollars(20), additionalShare));
                remainingShare -= additionalShare;
            }
            else {
                a.add(new Holding(stock, Money.dollars(20), remainingShare));
            }
        }
    }

    private List<Pair<String, Integer>> orderedAdditionalPositions(Stock stock) {
        List<Account> ordered = new ArrayList<>(accounts.values());
        Collections.sort(ordered, new AllocationAscendingComparator(this, stock));

        return ordered.stream()
                .map(a -> new Pair<>(a.investor(), round(this.suggestedAdditionalPosition(a, stock))))
                .collect(Collectors.toList());
    }

    Integer round(Double d) {
        return Long.valueOf(Math.round(d)).intValue(); //NOTE: practically speaking, int conversion is safe here
    }

    class AllocationAscendingComparator implements Comparator<Account> {
        private final Portfolio portfolio;
        private final Stock stock;

        public AllocationAscendingComparator(Portfolio portfolio, Stock stock) {
            this.portfolio = portfolio;
            this.stock = stock;
        }

        @Override
        public int compare(Account a1, Account a2) {
            return this.portfolio.suggestedAdditionalPosition(a1, stock).compareTo(this.portfolio.suggestedAdditionalPosition(a2, stock));
        }
    }
}