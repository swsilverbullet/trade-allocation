Feature: Allocate stocks in accounts - no GOOGLE stock
  Background:
    Given an investor John has an account with $50,000 capital
    And an investor John sets 4% target percent of GOOGLE stock

    Given an investor Sarah has an account with $150,000 capital
    And an investor Sarah sets 1% target percent of GOOGLE stock
    And an investor Sarah has 10 quantity of GOOGLE stock with current price $20 in the account

  Scenario: Basic allocation logic for new stock - John has no GOOGLE stock
    When a portfolio manager buy 100 quantity of GOOGLE stock
    And a portfolio manager reallocates the new GOOGLE stock
    Then an investor John has 63 quantity of GOOGLE stock
    And an investor Sarah has 47 quantity of GOOGLE stock