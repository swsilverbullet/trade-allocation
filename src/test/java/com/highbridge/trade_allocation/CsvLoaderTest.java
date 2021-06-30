package com.highbridge.trade_allocation;

import com.highbridge.trade_allocation.domain.Trade.BuyOrSell;
import com.highbridge.trade_allocation.domain.generic.Money;
import com.highbridge.trade_allocation.domain.generic.Percent;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Triplet;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.highbridge.trade_allocation.domain.Trade.BuyOrSell.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CsvLoaderTest {

    @Test
    public void testLoadTrades() {
        List<Quartet<String, BuyOrSell, Long, Money>> expected = Arrays.asList(
                Quartet.with("GOOGLE", BUY, 100L, Money.dollars(20)),
                Quartet.with("APPLE", SELL, 20L, Money.dollars(10))
        );
        assertThat(new CsvFileLoader().loadTrades("trades.csv"), is(expected));
    }

    @Test
    public void testLoadCapitals() {
        List<Pair<String, Money>> expected = Arrays.asList(
                Pair.with("John", Money.dollars(50000)),
                Pair.with("Sarah", Money.dollars(150000))
        );
        assertThat(new CsvFileLoader().loadCapitals("capitals.csv"), is(expected));
    }

    @Test
    public void testLoadHoldings() {
        List<Quartet<String, String, Long, Money>> expected = Arrays.asList(
                Quartet.with("John", "GOOGLE", 50L, Money.dollars(20)),
                Quartet.with("Sarah", "GOOGLE", 10L, Money.dollars(20)),
                Quartet.with("John", "APPLE", 25L, Money.dollars(10)),
                Quartet.with("Sarah", "APPLE", 50L, Money.dollars(10))
        );
        assertThat(new CsvFileLoader().loadHoldings("holdings.csv"), is(expected));
    }

    @Test
    public void testLoadTargets() {
        List<Triplet<String, String, Percent>> expected = Arrays.asList(
                Triplet.with("GOOGLE", "John", Percent.of(4)),
                Triplet.with("GOOGLE", "Sarah", Percent.of(1)),
                Triplet.with("APPLE", "John", Percent.of(1.5)),
                Triplet.with("APPLE", "Sarah", Percent.of(2))
        );
        assertThat(new CsvFileLoader().loadTargets("targets.csv"), is(expected));
    }
}