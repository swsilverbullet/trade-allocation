package com.highbridge.trade_allocation.domain.generic;

public class Percent {

    private final Double value;

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
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Percent)) {
            return false;
        }

        Percent other = (Percent) object;
        return this.value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

    @Override
    public String toString() {
        return value + " %";
    }
}
