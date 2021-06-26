package com.highbridge.trade_allocation.features;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java8.En;

import java.lang.annotation.Annotation;

import static org.junit.Assert.*;

public class StepDefinitions implements En {
    @Before
    public void before() {
    }

    public StepDefinitions() {
        Given("^an investor (.*) has an account with (.*) capital$", (String investor, String capital) -> {
            System.out.println(investor);
            System.out.println(capital);
        });
        Given("^an investor (.*) sets a (.*) Stock with target percent (.*)$", (String investor, String stockSymbol, String targetPercent) -> {
            System.out.println(investor);
            System.out.println(stockSymbol);
            System.out.println(targetPercent);
        });
        When("^current price of (.*) Stock is (.*)$", (String stockSymbol, String currentPrice) -> {
            System.out.println(stockSymbol);
            System.out.println(currentPrice);
        });
        Then("^an investor (.*) can maintain a (.*) Stock up to (.*)$", (String investor, String stockSymbol, String marketValue) -> {
            System.out.println(investor);
            System.out.println(stockSymbol);
            System.out.println(marketValue);
        });
    }


}