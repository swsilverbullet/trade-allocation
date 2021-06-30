Feature: Buy & allocate stocks in accounts - no GOOGLE target percent
  Background:
    Given an investor John has an account with $50,000 capital

    Given an investor Sarah has an account with $150,000 capital
    And an investor Sarah sets 1% target percent of GOOGLE stock
    And an investor Sarah has 10 quantity of GOOGLE stock with current price $20 in the account

  Scenario: Allocating the stock into investor's account - John has no GOOGLE target percent
    When a portfolio manager buy 2 quantity of GOOGLE stock
    And a portfolio manager reallocates the new GOOGLE stock
    Then an investor John has 0 quantity of GOOGLE stock
    And an investor Sarah has 12 quantity of GOOGLE stock