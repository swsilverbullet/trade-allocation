package com.highbridge.trade_allocation.domain.generic;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PercentTest {

    @Test
    public void testPercent() throws Exception {
        Percent onePercent = Percent.of(1);
        assertThat(onePercent.of(Money.dollars(100)), is(Money.dollars(1)));
    }
}
