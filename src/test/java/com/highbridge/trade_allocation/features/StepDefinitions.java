package com.highbridge.trade_allocation.features;

import com.highbridge.trade_allocation.Account;
import com.highbridge.trade_allocation.Stock;
import com.highbridge.trade_allocation.domain.sub.Money;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java8.En;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import java.lang.annotation.Annotation;

public class StepDefinitions implements En {
    Account anAccount;
    Stock aStock;

    public StepDefinitions() {
        Given("^an investor (.*) has an account with (.*) capital$", (String investor, String capital) -> {
            anAccount = new Account(investor, toMoney(capital));
        });
        Given("^an investor (.*) sets a (.*) Stock with target percent (.*)$", (String investor, String stockSymbol, String targetPercent) -> {
            System.out.println(investor);
            anAccount.setTargetPercent(stockSymbol, percentToDouble(targetPercent));
        });
        When("^current price of (.*) Stock is (.*)$", (String stockSymbol, String currentPrice) -> {
            aStock = new Stock(stockSymbol, toMoney(currentPrice));
        });

        Then("^an investor (.*) has an account with (.*) for (.*) Stock$", (String investor, String marketValue, String stockSymbol) -> {
            System.out.println(investor);
            System.out.println(stockSymbol);

            assertThat(anAccount.marketValue(aStock), is(toMoney(marketValue)));
        });

        Then("^an investor (.*) can maintain a (.*) Stock up to (.*) share$", (String investor, String stockSymbol, Integer maxShare) -> {
            System.out.println(investor);
            System.out.println(stockSymbol);

            assertThat(anAccount.maxShare(aStock), is(maxShare));
        });

    }

    Money toMoney(String money) {
        return Money.dollars(Double.valueOf(money.replaceAll("\\$|,", "")));
    }

    Double percentToDouble(String percent) {
        return Double.valueOf(percent.replaceAll("%", ""));
    }

    @Before
    public void before() {
    }
}