package com.highbridge.trade_allocation.domain;

import com.highbridge.trade_allocation.domain.generic.Money;

import static com.highbridge.trade_allocation.domain.Trade.BuyOrSell.*;

public class Trade {
    private final String stock;
    private final Long quantity;
    private final Money price;
    private final BuyOrSell buyOrSell;

    private Trade(String stock, Long quantity, Money price, BuyOrSell buyOrSell) {
        this.stock = stock;
        this.quantity = quantity;
        this.price = price;
        this.buyOrSell = buyOrSell;
    }

    static Trade buy(String stock, Long quantity, Money price) {
        return new Trade(stock, quantity, price, Buy);
    }

    static Trade sell(String stock, Long quantity, Money price) {
        return new Trade(stock, quantity, price, Sell);
    }

    public String stock() {
        return stock;
    }

    public Long singedQuantity() {
        return this.buyOrSell == Buy ? quantity : -quantity;
    }

    public Money price() {
        return price;
    }

    public Boolean isBuy() {
        return buyOrSell == Buy;
    }

    public Boolean isSell() {
        return !isBuy();
    }

    enum BuyOrSell {
        Buy, Sell
    }
}
