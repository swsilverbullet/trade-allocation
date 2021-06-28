Feature: Sell & de-allocate the stocks from accounts
  Background:
    Given an investor John has an account with $50,000 capital
    And an investor John sets 1.5% target percent of APPLE stock
    And an investor John has 25 quantity of APPLE stock with current price $10 in the account

    Given an investor Sarah has an account with $150,000 capital
    And an investor Sarah sets 2% target percent of APPLE stock
    And an investor Sarah has 50 quantity of APPLE stock with current price $10 in the account

  Scenario: Maximum quantity for portfolio manager can sell
    Then a portfolio manager is entitled to sell up to 75 quantity of APPLE stock

  Scenario: Deallocating the stocks from investor's account
    When a portfolio manager sell 20 quantity of APPLE stock
    And a portfolio manager deallocates 5 quantity of APPLE stock from John's account
    And a portfolio manager deallocates 15 quantity of APPLE stock from Sarah's account
    Then an investor John has 20 quantity of APPLE stock
    And an investor Sarah has 35 quantity of APPLE stock

  Scenario: Suggesting final position for given stock
    When a portfolio manager sell 20 quantity of APPLE stock
    Then a portfolio manager suggests total 11.0 quantity of APPLE stock in John's account
    And a portfolio manager suggests total 44.0 quantity of APPLE stock in Sarah's account
    And a portfolio manager suggests additional -14.0 quantity of APPLE stock in John's account
    And a portfolio manager suggests additional -6.0 quantity of APPLE stock in Sarah's account

  Scenario: Basic deallocation logic for sold stock
    When a portfolio manager sell 20 quantity of APPLE stock
    And a portfolio manager reallocates the sold APPLE stock
    Then an investor John has 11 quantity of APPLE stock
    And an investor Sarah has 44 quantity of APPLE stock
