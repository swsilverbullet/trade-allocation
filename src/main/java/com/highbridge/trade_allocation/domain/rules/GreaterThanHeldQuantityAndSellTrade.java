package com.highbridge.trade_allocation.domain.rules;

import com.highbridge.trade_allocation.domain.Account;
import com.highbridge.trade_allocation.domain.Trade;

public class GreaterThanHeldQuantityAndSellTrade implements FinalPositionErrorCondition {
    @Override
    public Boolean isSatisfiedBy(Double suggestedFinalPosition, Account account, Trade trade) {
        return suggestedFinalPosition > account.currentQuantity(trade.stock()) && trade.isSell();
    }
}
