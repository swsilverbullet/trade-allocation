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
    Then an investor John has 20 shares of APPLE stock
    And an investor Sarah has 35 shares of APPLE stock

  # TODO BL - double check the value below
  Scenario: Suggesting final position for given stock
    When a portfolio manager sell 20 share of APPLE stock
    Then a portfolio manager suggests total 11.0 shares of APPLE stock in John's account
    And a portfolio manager suggests total 44.0 shares of APPLE stock in Sarah's account
    And a portfolio manager suggests additional -14.0 shares of APPLE stock in John's account
    And a portfolio manager suggests additional -6.0 shares of APPLE stock in Sarah's account

  Scenario: Basic deallocation logic for sold shares
    When a portfolio manager sell 20 share of APPLE stock
    And a portfolio manager deallocates the sold APPLE stock
    Then an investor John has 11 shares of APPLE stock
    And an investor Sarah has 44 shares of APPLE stock