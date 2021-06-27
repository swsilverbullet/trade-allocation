Feature: Sell & de-allocate the stocks from accounts
  Background:
    Given an investor John has an account with $50,000 capital
    And an investor John sets 1.5% target percent of APPLE stock
    And an investor John has 25 shares of APPLE stock with current price $10 in the account

    Given an investor Sarah has an account with $150,000 capital
    And an investor Sarah sets 2% target percent of APPLE stock
    And an investor Sarah has 50 shares of APPLE stock with current price $10 in the account

  Scenario: Maximum share for portfolio manager can sell
    Then a portfolio manager is entitled to sell up to 75 share of APPLE stock

  Scenario: Deallocating the shares from investor's account
    When a portfolio manager sell 20 share of APPLE stock
    And a portfolio manager deallocates 5 shares of APPLE stock from John's account
    And a portfolio manager deallocates 15 shares of APPLE stock from Sarah's account
    Then an investor John has 20 shares of GOOGLE stock
    And an investor Sarah has 35 shares of GOOGLE stock
