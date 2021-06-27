package com.highbridge.trade_allocation.features;

import com.highbridge.trade_allocation.domain.*;
import com.highbridge.trade_allocation.domain.sub.Money;
import com.highbridge.trade_allocation.domain.sub.Percent;
import io.cucumber.java.Before;
import io.cucumber.java8.En;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class StepDefinitions implements En {
    Portfolio portfolio;
    AccountRepository accountRepository;
    StockExchangeRepository exchangeRepository;

    public StepDefinitions() {
        Given("^an investor (.*) has an account with (.*) capital$", (String investor, String capital) -> {
            accountRepository.addAccount(new Account(investor, toMoney(capital)));
        });
        Given("^an investor (.*) sets a (.*) stock with target percent (.*)$", (String investor, String stockSymbol, String targetPercent) -> {
            accountRepository.findByInvestor(investor).add(stockSymbol, toPercent(targetPercent));
        });

        When("^current price of (.*) stock is (.*)$", (String stockSymbol, String currentPrice) -> {
            exchangeRepository.add(new Stock(stockSymbol), toMoney(currentPrice));
        });
        When("^an investor (.*) has (.*) shares of (.*) stock with current price (.*) in the account$", (String investor, Integer ownedQuantity, String stockSymbol, String currentPrice) -> {
            Account account = accountRepository.findByInvestor(investor);
            account.add(new Holding(stockSymbol, toMoney(currentPrice), ownedQuantity));
        });
        When("^an investor (.*) sets (.*) target percent of (.*) stock$", (String investor, String targetPercent, String stockSymbol) -> {
            Account account = accountRepository.findByInvestor(investor);
            account.add(stockSymbol, toPercent(targetPercent));
        });
        When("^a portfolio manager buy (.*) share of (.*) stock$", (Integer quantity, String stockSymbol) -> {
            // TODO BL - let's improve this logic. now it simply checks the number of max share portfolio manager can buy
            accountRepository.all().forEach(a -> portfolio.add(a));
            portfolio.addNewTrade(stockSymbol, quantity);
            assertThat(portfolio.entitledToBuyUpTo(stockSymbol, exchangeRepository.price(stockSymbol)) >= quantity, is(true));
        });
        When("^a portfolio manager allocates (.*) shares of (.*) stock to (.*)'s account$", (Integer quantity, String stockSymbol, String investor) -> {
            Account account = accountRepository.findByInvestor(investor);
            account.add(new Holding(stockSymbol, exchangeRepository.price(stockSymbol), quantity));
        });
        When("^a portfolio manager allocates the new (.*) stock$", (String stockSymbol) -> {
            portfolio.allocateNew(stockSymbol);
        });

        Then("^an investor (.*) has an account with (.*) for (.*) stock$", (String investor, String marketValue, String stockSymbol) -> {
            Account account = accountRepository.findByInvestor(investor);
            assertThat(account.marketValue(new Stock(stockSymbol)), is(toMoney(marketValue)));
        });
        Then("^an investor (.*) can maintain a (.*) stock up to (.*) share$", (String investor, String stockSymbol, Integer maxShare) -> {
            Money price = exchangeRepository.price(stockSymbol);
            assertThat(accountRepository.findByInvestor(investor).maxShare(new Stock(stockSymbol), price), is(maxShare));
        });
        Then("^an investor (.*) can own (.*) more shares of (.*) stock$", (String investor, Integer extraShare, String stockSymbol) -> {
        });
        Then("^a portfolio manager is entitled to buy up to (.*) share of (.*) stock$", (Integer maxShareToBuy, String stockSymbol) -> {
            accountRepository.all().forEach(a -> portfolio.add(a));
            assertThat(portfolio.entitledToBuyUpTo(stockSymbol, exchangeRepository.price(stockSymbol)), is(maxShareToBuy));
        });
        Then("^an investor (.*) has (.*) shares of (.*) stock$", (String investor, Integer quantity, String stockSymbol) -> {
            Account account = accountRepository.findByInvestor(investor);
            assertThat(account.currentShare(new Stock(stockSymbol)), is(quantity));
        });
        Then("^a portfolio manager suggests total (.*) shares of (.*) stock in (.*)'s account$", (Double totalShare, String stockSymbol, String investor) -> {
            Account account = accountRepository.findByInvestor(investor);
            Double finalPosition = portfolio.suggestedFinalPosition(account, new Stock(stockSymbol));
            assertThat(finalPosition, is(totalShare));
        });
        Then("^a portfolio manager suggests additional (.*) shares of (.*) stock in (.*)'s account$", (String additionalShare, String stockSymbol, String investor) -> {
            Account account = accountRepository.findByInvestor(investor);
            Double additionalPosition = portfolio.suggestedAdditionalPosition(account, new Stock(stockSymbol));
            assertThat(additionalPosition, is(toMoney(additionalShare).getAmount().doubleValue()));
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
        this.portfolio = new Portfolio();
        this.accountRepository = new AccountRepository();
        this.exchangeRepository = new StockExchangeRepository();

        initExchangeRepository();
    }

    private void initExchangeRepository() {
        exchangeRepository.add(new Stock("GOOGLE"), Money.dollars(20));
        exchangeRepository.add(new Stock("APPLE"), Money.dollars(10));
    }
}