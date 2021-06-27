Feature: Sell & de-allocate the stocks from accounts
  Background:
    Given an investor John has an account with $50,000 capital
    And an investor John sets 4% target percent of GOOGLE stock
    And an investor John has 50 shares of GOOGLE stock with current price $20 in the account

    Given an investor Sarah has an account with $150,000 capital
    And an investor Sarah sets 1% target percent of GOOGLE stock
    And an investor Sarah has 10 shares of GOOGLE stock with current price $20 in the account

  Scenario: Maximum share for portfolio manager can sell
    Then a portfolio manager is entitled to sell up to 60 share of GOOGLE stock
