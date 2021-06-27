package com.highbridge.trade_allocation.features;

import com.highbridge.trade_allocation.domain.*;
import com.highbridge.trade_allocation.domain.generic.Money;
import com.highbridge.trade_allocation.domain.generic.Percent;
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
        Given("^an investor (.*) sets a (.*) stock with target percent (.*)$", (String investor, String stock, String targetPercent) -> {
            accountRepository.findByInvestor(investor).add(stock, toPercent(targetPercent));
        });

        When("^current price of (.*) stock is (.*)$", (String stock, String currentPrice) -> {
            exchangeRepository.add(stock, toMoney(currentPrice));
        });
        When("^an investor (.*) has (.*) shares of (.*) stock with current price (.*) in the account$", (String investor, Integer ownedQuantity, String stock, String currentPrice) -> {
            Account account = accountRepository.findByInvestor(investor);
            account.add(new Holding(stock, ownedQuantity));
        });
        When("^an investor (.*) sets (.*) target percent of (.*) stock$", (String investor, String targetPercent, String stock) -> {
            Account account = accountRepository.findByInvestor(investor);
            account.add(stock, toPercent(targetPercent));
        });
        When("^a portfolio manager buy (.*) share of (.*) stock$", (Integer quantity, String stock) -> {
            // TODO BL - let's improve this logic. now it simply checks the number of max share portfolio manager can buy
            accountRepository.all().forEach(a -> portfolio.add(a));
            portfolio.addNewTrade(stock, quantity);
            assertThat(portfolio.entitledToBuyUpTo(stock, exchangeRepository.price(stock)) >= quantity, is(true));
        });
        When("^a portfolio manager allocates (.*) shares of (.*) stock to (.*)'s account$", (Integer quantity, String stock, String investor) -> {
            Account account = accountRepository.findByInvestor(investor);
            account.add(new Holding(stock, quantity));
        });
        When("^a portfolio manager allocates the new (.*) stock$", (String stock) -> {
            portfolio.allocateNew(stock);
        });

        Then("^an investor (.*) has an account with (.*) for (.*) stock$", (String investor, String marketValue, String stock) -> {
            Account account = accountRepository.findByInvestor(investor);
            assertThat(account.marketValue(stock), is(toMoney(marketValue)));
        });
        Then("^an investor (.*) can maintain a (.*) stock up to (.*) share$", (String investor, String stock, Integer maxShare) -> {
            Money price = exchangeRepository.price(stock);
            assertThat(accountRepository.findByInvestor(investor).maxShare(stock, price), is(maxShare));
        });
        Then("^an investor (.*) can own (.*) more shares of (.*) stock$", (String investor, Integer extraShare, String stock) -> {
        });
        Then("^a portfolio manager is entitled to buy up to (.*) share of (.*) stock$", (Integer maxShareToBuy, String stock) -> {
            accountRepository.all().forEach(a -> portfolio.add(a));
            assertThat(portfolio.entitledToBuyUpTo(stock, exchangeRepository.price(stock)), is(maxShareToBuy));
        });
        Then("^a portfolio manager is entitled to sell up to (.*) share of (.*) stock$", (Integer maxShareToSell, String stock) -> {
            accountRepository.all().forEach(a -> portfolio.add(a));
            assertThat(portfolio.entitledToSellUpTo(stock), is(maxShareToSell));
        });
        Then("^an investor (.*) has (.*) shares of (.*) stock$", (String investor, Integer quantity, String stock) -> {
            Account account = accountRepository.findByInvestor(investor);
            assertThat(account.currentShare(stock), is(quantity));
        });
        Then("^a portfolio manager suggests total (.*) shares of (.*) stock in (.*)'s account$", (Double totalShare, String stock, String investor) -> {
            Account account = accountRepository.findByInvestor(investor);
            Double finalPosition = portfolio.suggestedFinalPosition(account, stock);
            assertThat(finalPosition, is(totalShare));
        });
        Then("^a portfolio manager suggests additional (.*) shares of (.*) stock in (.*)'s account$", (String additionalShare, String stock, String investor) -> {
            Account account = accountRepository.findByInvestor(investor);
            Double additionalPosition = portfolio.suggestedAdditionalPosition(account, stock);
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
        exchangeRepository.add("GOOGLE", Money.dollars(20));
        exchangeRepository.add("APPLE", Money.dollars(10));
    }
}