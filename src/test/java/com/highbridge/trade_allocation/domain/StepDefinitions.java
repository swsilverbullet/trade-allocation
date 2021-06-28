package com.highbridge.trade_allocation.domain;

import com.highbridge.trade_allocation.domain.generic.Money;
import com.highbridge.trade_allocation.domain.generic.Percent;
import io.cucumber.java.Before;
import io.cucumber.java8.En;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class StepDefinitions implements En {
    Portfolio portfolio;
    AccountRepository accountRepository;
    StockExchange stockExchange;
    Trade newTrade;

    public StepDefinitions() {
        Given("^an investor (.*) has an account with (.*) capital$", (String investor, String capital) -> {
            accountRepository.addAccount(new Account(investor, toMoney(capital)));
        });
        Given("^an investor (.*) sets a (.*) stock with target percent (.*)$", (String investor, String stock, String targetPercent) -> {
            accountRepository.findByInvestor(investor).addStockTargetPercent(stock, toPercent(targetPercent));
        });

        When("^current price of (.*) stock is (.*)$", (String stock, String currentPrice) -> {
            stockExchange.add(stock, toMoney(currentPrice));
        });
        When("^an investor (.*) has (.*) quantity of (.*) stock with current price (.*) in the account$", (String investor, Long ownedQuantity, String stock, String currentPrice) -> {
            Account account = accountRepository.findByInvestor(investor);
            account.addHolding(new Holding(stock, toMoney(currentPrice), ownedQuantity));
        });
        When("^an investor (.*) sets (.*) target percent of (.*) stock$", (String investor, String targetPercent, String stock) -> {
            Account account = accountRepository.findByInvestor(investor);
            account.addStockTargetPercent(stock, toPercent(targetPercent));
        });
        When("^a portfolio manager buy (.*) quantity of (.*) stock$", (Long quantity, String stock) -> {
            accountRepository.all().forEach(a -> portfolio.add(a));
            newTrade = Trade.buy(stock, quantity, stockExchange.price(stock));
            assertThat(portfolio.entitledToBuyUpTo(newTrade.stock(), newTrade.price()) >= quantity, is(true));
        });
        When("^a portfolio manager sell (.*) quantity of (.*) stock$", (Long quantity, String stock) -> {
            accountRepository.all().forEach(a -> portfolio.add(a));
            newTrade = Trade.sell(stock, quantity, stockExchange.price(stock));
            assertThat(portfolio.entitledToSellUpTo(stock) >= quantity, is(true));
        });
        When("^a portfolio manager allocates (.*) quantity of (.*) stock to (.*)'s account$", (Long quantity, String stock, String investor) -> {
            Account account = accountRepository.findByInvestor(investor);
            account.addHolding(new Holding(stock, stockExchange.price(stock), quantity));
        });
        When("^a portfolio manager deallocates (.*) quantity of (.*) stock from (.*)'s account$", (Long quantity, String stock, String investor) -> {
            Account account = accountRepository.findByInvestor(investor);
            account.addHolding(new Holding(stock, stockExchange.price(stock), -quantity));
        });
        When("^a portfolio manager reallocates the (?:new|sold) (.*) stock$", (String stock) -> {
            assertThat(newTrade.stock(), is(stock));
            portfolio.reallocateHoldings(newTrade);
        });
        Then("^an investor (.*) has an account with (.*) for (.*) stock$", (String investor, String marketValue, String stock) -> {
            Account account = accountRepository.findByInvestor(investor);
            assertThat(account.targetMarketValue(stock), is(toMoney(marketValue)));
        });
        Then("^an investor (.*) can maintain a (.*) stock up to (.*)", (String investor, String stock, Long maxQuantity) -> {
            Money price = stockExchange.price(stock);
            assertThat(accountRepository.findByInvestor(investor).targetMarketQuantity(stock, price), is(maxQuantity));
        });
        Then("^an investor (.*) can own (.*) additional quantity of (.*) stock$", (String investor, Long additionalQuantity, String stock) -> {
        });
        Then("^a portfolio manager is entitled to buy up to (.*) quantity of (.*) stock$", (Long maxQuantityToBuy, String stock) -> {
            accountRepository.all().forEach(a -> portfolio.add(a));
            assertThat(portfolio.entitledToBuyUpTo(stock, stockExchange.price(stock)), is(maxQuantityToBuy));
        });
        Then("^a portfolio manager is entitled to sell up to (.*) quantity of (.*) stock$", (Long maxQuantityToSell, String stock) -> {
            accountRepository.all().forEach(a -> portfolio.add(a));
            assertThat(portfolio.entitledToSellUpTo(stock), is(maxQuantityToSell));
        });
        Then("^an investor (.*) has (.*) quantity of (.*) stock$", (String investor, Long quantity, String stock) -> {
            Account account = accountRepository.findByInvestor(investor);
            assertThat(account.currentQuantity(stock), is(quantity));
        });
        Then("^a portfolio manager suggests total (.*) quantity of (.*) stock in (.*)'s account$", (Double totalQuantity, String stock, String investor) -> {
            assertThat(newTrade.stock(), is(stock));
            Account account = accountRepository.findByInvestor(investor);
            Double finalPosition = portfolio.suggestedFinalPosition(account, newTrade);
            assertThat(finalPosition, is(totalQuantity));
        });
        Then("^a portfolio manager suggests additional (.*) quantity of (.*) stock in (.*)'s account$", (String additionalQuantity, String stock, String investor) -> {
            assertThat(newTrade.stock(), is(stock));
            Account account = accountRepository.findByInvestor(investor);
            Double additionalPosition = portfolio.suggestedTradeAllocation(account, newTrade);
            assertThat(additionalPosition, is(toMoney(additionalQuantity).getAmount().doubleValue()));
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
        this.stockExchange = new StockExchange();
        this.newTrade = null;

        initExchangeRepository();
    }

    private void initExchangeRepository() {
        this.stockExchange.add("GOOGLE", Money.dollars(20));
        this.stockExchange.add("APPLE", Money.dollars(10));
    }
}