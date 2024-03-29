package com.highbridge.trade_allocation.domain.generic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

public class Money {
    private static final Currency USD = Currency.getInstance("USD");

    private static final RoundingMode DEFAULT_ROUNDING = RoundingMode.HALF_EVEN;

    private final BigDecimal amount;
    private final Currency currency;

    public static Money dollars(double amount) {
        return valueOf(BigDecimal.valueOf(amount), USD);
    }

    static Money valueOf(BigDecimal amount, Currency currency) {
        return new Money(amount, currency);
    }

    Money(BigDecimal amount, Currency currency) {
        this(amount, currency, DEFAULT_ROUNDING);
    }

    Money(BigDecimal amount, Currency currency, RoundingMode rounding) {
        this.amount = amount.setScale(currency.getDefaultFractionDigits(), rounding);
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Money negate() {
        return valueOf(getAmount().negate(), getCurrency());
    }

    public Money plus(Money added) {
        if (!isCurrencyEquals(added.getCurrency())) {
            throw new IllegalArgumentException("Currency is not same!");
        }

        return valueOf(getAmount().add(added.getAmount()), getCurrency());
    }

    private boolean isCurrencyEquals(Currency currency) {
        return this.currency.equals(currency);
    }

    public Money minus(Money other) {
        return plus(other.negate());
    }

    public Money times(double factor) {
        return valueOf(amount.multiply(BigDecimal.valueOf(factor)), getCurrency());
    }

    public Money divide(double factor) {
        return times(1 / factor);
    }

    public Money divide(Money other) {
        return divide(other.getAmount().doubleValue());
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Money)) {
            return false;
        }

        Money other = (Money) object;
        return getAmount().equals(other.getAmount()) && getCurrency().equals(other.getCurrency());
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 37 * result + getAmount().hashCode();
        result = 37 * result + getCurrency().hashCode();

        return result;
    }

    @Override
    public String toString() {
        return getCurrency().getSymbol() + getAmount();
    }
}
