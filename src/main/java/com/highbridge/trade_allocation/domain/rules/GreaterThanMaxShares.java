package com.highbridge.trade_allocation.domain.rules;

import com.highbridge.trade_allocation.domain.Account;
import com.highbridge.trade_allocation.domain.Trade;

public class GreaterThanMaxShares implements FinalPositionErrorCondition {
    @Override
    public Boolean isSatisfiedBy(Double suggestedFinalPosition, Account account, Trade trade) {
        return suggestedFinalPosition > account.targetMarketQuantity(trade.stock(), trade.price());
    }
}