package com.highbridge.trade_allocation;

public class Account {
    private final String investor;
    private final double capital;
    private String stockSymbol;
    private double targetPercent;

    public Account(String investor, double capital) {
        this.investor = investor;
        this.capital = capital;
    }

    public void setTargetPercent(String stockSymbol, double targetPercent) {
        this.stockSymbol = stockSymbol;
        this.targetPercent = targetPercent;
    }

    public Integer maxShare(Stock stock) {
        if (stock.symbol().equals(stockSymbol)) {
            return Double.valueOf(capital * targetPercent * 0.01 / stock.price()).intValue();
        }
        return 0;
    }

    public Double marketValue(Stock stock) {
        if (stock.symbol().equals(stockSymbol)) {
            return Double.valueOf(capital * targetPercent * 0.01);
        }
        return 0d;
    }
}
