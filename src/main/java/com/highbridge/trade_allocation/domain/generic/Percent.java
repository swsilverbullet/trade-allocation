package com.highbridge.trade_allocation.domain.generic;

public class Percent {

    private final double value;

    public static Percent of(double value) {
        return new Percent(value);
    }

    Percent(double value) {
        this.value = value;
    }

    public Money of(Money money) {
        return money.times(this.value * 0.01);
    }

    @Override
    public String toString() {
        return value + " %";
    }
}
