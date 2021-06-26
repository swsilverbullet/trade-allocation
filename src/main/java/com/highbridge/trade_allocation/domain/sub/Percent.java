package com.highbridge.trade_allocation.domain.sub;

public class Percent {

    private final double percent;

    public static Percent of(double percent) {
        return new Percent(percent);
    }

    Percent(double percent) {
        this.percent = percent;
    }

    public Money of(Money money) {
        return money.times(this.percent * 0.01);
    }

    @Override
    public String toString() {
        return percent + " %";
    }
}
