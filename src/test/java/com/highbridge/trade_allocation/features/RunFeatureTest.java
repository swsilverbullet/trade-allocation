package com.highbridge.trade_allocation.features;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"classpath:features/"}, glue = {"com.highbridge.trade_allocation.features"}, plugin = {"pretty"} )
public class RunFeatureTest {
}