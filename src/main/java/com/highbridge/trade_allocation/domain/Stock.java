package com.highbridge.trade_allocation.domain;

public class Stock {
    private final String symbol;

    public Stock(String stockSymbol) {
        this.symbol = stockSymbol;
    }

    public String symbol() {
        return symbol;
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Stock)) {
            return false;
        }

        Stock other = (Stock) object;
        return symbol().equals(other.symbol());
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 37 * result + symbol().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return symbol();
    }
}