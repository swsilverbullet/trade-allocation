package com.highbridge.trade_allocation.domain.sub;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class MoneyTest {

    @Test
    public void testDollars() throws Exception {
        Money dollars100 = new Money(new BigDecimal(100), Currency.getInstance("USD"));
        assertThat(Money.dollars(100), is(dollars100));
    }

    @Test
    public void testFractionalDigits_USD() throws Exception {
        assertThat(Money.dollars(2.5012), is(Money.dollars(2.50)));
    }

    @Test
    public void testNegate() throws Exception {
        assertThat(Money.dollars(100).negate(), is(Money.dollars(-100)));
        assertThat(Money.dollars(-100).negate(), is(Money.dollars(100)));
    }

    @Test
    public void testPlus() throws Exception {
        assertThat(Money.dollars(250).plus(Money.dollars(150)), is(Money.dollars(400)));
        assertThat(Money.dollars(-250).plus(Money.dollars(350)), is(Money.dollars(100)));
    }

    @Test
    public void testMinus() throws Exception {
        assertThat(Money.dollars(250).minus(Money.dollars(150)), is(Money.dollars(100)));
        assertThat(Money.dollars(250).minus(Money.dollars(350)), is(Money.dollars(-100)));
        assertThat(Money.dollars(-250).minus(Money.dollars(150)), is(Money.dollars(-400)));
        assertThat(Money.dollars(250).minus(Money.dollars(-150)), is(Money.dollars(400)));
    }

    @Test
    public void testTimes() throws Exception {
        assertThat(Money.dollars(30.15).times(3.26), is(Money.dollars(98.29)));
        assertThat(Money.dollars(125.05).times(2.15), is(Money.dollars(268.86)));
    }

    @Test
    public void testDivide() throws Exception {
        assertThat(Money.dollars(30.15).divide(3), is(Money.dollars(10.05)));
        assertThat(Money.dollars(2000).times(160).divide(3500), is(Money.dollars(91.43)));
    }

    @Test
    public void testEqual() throws Exception {
        assertThat(Money.dollars(100), is(Money.dollars(100)));
        assertThat(Money.dollars(100), is(Money.valueOf(new BigDecimal(100), Currency.getInstance("USD"))));
    }

    @Test
    public void testNotEqual() throws Exception {
        assertThat(Money.dollars(100), not(Money.dollars(200)));
        assertThat(Money.dollars(100), not(Money.valueOf(new BigDecimal(100), Currency.getInstance("EUR"))));
    }

    @Test
    public void testToString() throws Exception {
        assertThat(Money.dollars(100).toString(), is("$100.00"));
    }
}
