package com.highbridge.trade_allocation.domain;

import com.highbridge.trade_allocation.domain.generic.Money;
import com.highbridge.trade_allocation.domain.rules.AllocationRule;
import org.javatuples.Pair;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Portfolio {
    private final Map<String, Account> accounts;
    private final AllocationRule rule;

    public Portfolio() {
        this.accounts = new HashMap<>();
        this.rule = new AllocationRule(this);
    }

    public void add(Account account) {
        this.accounts.put(account.investor(), account);
    }

    Long entitledToBuyUpTo(String stock, Money price) {
        return accounts.values().stream().mapToLong(a -> a.quantityCanBeAdded(stock, price)).sum();
    }

    Long entitledToSellUpTo(String stock) {
        return accounts.values().stream().mapToLong(a -> a.currentQuantity(stock)).sum();
    }

    Long allInPosition(Trade newTrade) {
        return totalQuantity(newTrade.stock()) + newTrade.singedQuantity();
    }

    Double suggestedTradeAllocation(Account account, Trade newTrade) {
        BigDecimal suggestedFinalQuantity = BigDecimal.valueOf(suggestedFinalPosition(account, newTrade));
        BigDecimal currentQuantity = BigDecimal.valueOf(account.currentQuantity(newTrade.stock()));
        return suggestedFinalQuantity.subtract(currentQuantity).doubleValue();
    }

    public Double suggestedFinalPosition(Account account, Trade newTrade) {
        Money accountTargetMarketValue = account.targetMarketValue(newTrade.stock());
        Money portfolioTotalTargetMarketValue = totalTargetMarketValue(newTrade.stock());
        return accountTargetMarketValue.times(allInPosition(newTrade)).divide(portfolioTotalTargetMarketValue).getAmount().doubleValue();
    }

    private Money totalTargetMarketValue(String stock) {
        return Money.dollars(accounts.values().stream().mapToDouble(a -> a.targetMarketValue(stock).getAmount().doubleValue()).sum());
    }

    private Long totalQuantity(String stock) {
        return accounts.values().stream().mapToLong(a -> a.currentQuantity(stock)).sum();
    }

    public void reallocateHoldings(Trade newTrade) {
        if (this.rule.isErrorConditionMet(newTrade)) {
            this.accounts.values().forEach(account -> account.setToZeroQuantity(newTrade.stock(), newTrade.price()));
        }
        else {
            List<Pair<Account, Long>> accountAndSuggestedQuantity = new AccountAndSuggestedTradeAllocation(this).ordered(newTrade);

            Long remainingQuantity = newTrade.singedQuantity();
            for (int i = 0; i < accountAndSuggestedQuantity.size(); i++) {
                Account account = accountAndSuggestedQuantity.get(i).getValue0();
                if (i < accountAndSuggestedQuantity.size() - 1) {
                    Long suggestedQuantity = accountAndSuggestedQuantity.get(i).getValue1();
                    account.addHolding(new Holding(newTrade.stock(), newTrade.price(), suggestedQuantity));
                    remainingQuantity -= suggestedQuantity;
                }
                else {  // set the remainder for last account
                    account.addHolding(new Holding(newTrade.stock(), newTrade.price(), remainingQuantity));
                }
            }
        }
    }

    public List<Account> accounts() {
        return new ArrayList<>(this.accounts.values());
    }
}
