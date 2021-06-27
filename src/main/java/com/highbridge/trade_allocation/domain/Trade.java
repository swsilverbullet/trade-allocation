package com.highbridge.trade_allocation.domain;

import com.highbridge.trade_allocation.domain.generic.Money;

public class Trade {
    private final String stock;
    private final Integer quantity;
    private final Money price;
    private final BuyOrSell buyOrSell;

    private Trade(String stock, Integer quantity, Money price, BuyOrSell buyOrSell) {
        this.stock = stock;
        this.quantity = quantity;
        this.price = price;
        this.buyOrSell = buyOrSell;
    }

    static Trade buy(String stock, Integer quantity, Money price) {
        return new Trade(stock, quantity, price, BuyOrSell.Buy);
    }

    static Trade sell(String stock, Integer quantity, Money price) {
        return new Trade(stock, quantity, price, BuyOrSell.Sell);
    }

    Integer singedQuantity() {
        return this.buyOrSell == BuyOrSell.Buy ? quantity : -quantity;
    }

    enum BuyOrSell {
        Buy, Sell
    }
}
