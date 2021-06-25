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
        Given("^nothing given (.*)$", (String given1) -> {
            System.out.println(given1);
        });
        When("^nothing when (.*)$", (String when1) -> {
            System.out.println(when1);
        });
        Then("^nothing then (.*)$", (String then1) -> {
            System.out.println(then1);
        });
    }
}