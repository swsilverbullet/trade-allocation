package com.highbridge.trade_allocation.features;

import com.highbridge.trade_allocation.domain.*;
import com.highbridge.trade_allocation.domain.sub.Money;
import com.highbridge.trade_allocation.domain.sub.Percent;
import io.cucumber.java.Before;
import io.cucumber.java8.En;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class StepDefinitions implements En {
    AccountRepository accountRepository = new AccountRepository();
    StockExchangeRepository exchangeRepository = new StockExchangeRepository();

    public StepDefinitions() {
        Given("^an investor (.*) has an account with (.*) capital$", (String investor, String capital) -> {
            accountRepository.addAccount(new Account(investor, toMoney(capital)));
        });
        Given("^an investor (.*) sets a (.*) Stock with target percent (.*)$", (String investor, String stockSymbol, String targetPercent) -> {
            accountRepository.findByInvestor(investor).add(stockSymbol, toPercent(targetPercent));
        });
        When("^current price of (.*) Stock is (.*)$", (String stockSymbol, String currentPrice) -> {
            exchangeRepository.add(new Stock(stockSymbol, toMoney(currentPrice)));
        });
        When("^an investor (.*) has (.*) shares of (.*) Stock with current price (.*) in the account$", (String investor, Integer ownedQuantity, String stockSymbol, String currentPrice) -> {
            Account account = accountRepository.findByInvestor(investor);
            account.add(new Holding(stockSymbol, toMoney(currentPrice), ownedQuantity));
        });

        When("^an investor (.*) sets (.*) target percent of (.*) Stock$", (String investor, String targetPercent, String stockSymbol) -> {
            Account account = accountRepository.findByInvestor(investor);
            account.add(stockSymbol, toPercent(targetPercent));
        });

        Then("^an investor (.*) has an account with (.*) for (.*) Stock$", (String investor, String marketValue, String stockSymbol) -> {
            System.out.println(stockSymbol);

            Account account = accountRepository.findByInvestor(investor);
            Stock aStock = exchangeRepository.findBySymbol(stockSymbol);
            assertThat(account.marketValue(aStock), is(toMoney(marketValue)));
        });

        Then("^an investor (.*) can maintain a (.*) Stock up to (.*) share$", (String investor, String stockSymbol, Integer maxShare) -> {
            Stock stock = exchangeRepository.findBySymbol(stockSymbol);
            assertThat(accountRepository.findByInvestor(investor).maxShare(stock), is(maxShare));
        });
        Then("^an investor (.*) can own (.*) more shares of (.*) Stock$", (String investor, Integer extraShare, String stockSymbol) -> {
        });
    }

    Money toMoney(String money) {
        return Money.dollars(Double.valueOf(money.replaceAll("\\$|,", "")));
    }

    Percent toPercent(String percent) {
        return Percent.of(Double.valueOf(percent.replaceAll("%", "")));
    }

    @Before
    public void before() {
        initExchangeRepository();
    }

    private void initExchangeRepository() {
        exchangeRepository.add(new Stock("GOOGLE", Money.dollars(20)));
        exchangeRepository.add(new Stock("APPLE", Money.dollars(10)));
    }
}