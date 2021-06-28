package com.highbridge.trade_allocation.domain.rules;

import com.highbridge.trade_allocation.domain.Account;
import com.highbridge.trade_allocation.domain.Trade;

public interface FinalPositionErrorCondition {
    Boolean isSatisfiedBy(Double suggestedFinalPosition, Account account, Trade trade);
}