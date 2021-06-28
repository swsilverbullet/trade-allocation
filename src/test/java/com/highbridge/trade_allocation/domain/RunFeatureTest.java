package com.highbridge.trade_allocation.domain;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"classpath:features/error_conditions.feature"}, glue = {"com.highbridge.trade_allocation.domain"}, plugin = {"pretty"} )
public class RunFeatureTest {
}