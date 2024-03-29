package com.highbridge.trade_allocation.domain.rules;

import com.highbridge.trade_allocation.domain.Account;
import com.highbridge.trade_allocation.domain.Portfolio;
import com.highbridge.trade_allocation.domain.Trade;

import java.util.ArrayList;
import java.util.List;

public class AllocationRule {
    private final Portfolio portfolio;
    private final List<FinalPositionErrorCondition> conditions;

    public AllocationRule(Portfolio portfolio) {
        this.portfolio = portfolio;
        conditions = new ArrayList<>();
        conditions.add(new LessThanZero());
        conditions.add(new GreaterThanMaxShares());
        conditions.add(new LessThanHeldQuantityAndBuyTrade());
        conditions.add(new GreaterThanHeldQuantityAndSellTrade());
    }

    public Boolean isErrorConditionMet(Trade trade) {
        for (Account account : portfolio.accounts()) {
            if (conditions.stream().anyMatch(c -> c.isSatisfiedBy(portfolio.suggestedFinalPosition(account, trade), account, trade))) {
                return true;
            }
        }
        return false;
    }
}
