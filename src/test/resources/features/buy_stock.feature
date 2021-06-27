Feature: Buy & allocate stocks in accounts
  Background:
    Given an investor John has an account with $50,000 capital
    And an investor John sets 4% target percent of GOOGLE stock
    And an investor John has 50 shares of GOOGLE stock with current price $20 in the account

    Given an investor Sarah has an account with $150,000 capital
    And an investor Sarah sets 1% target percent of GOOGLE stock
    And an investor Sarah has 10 shares of GOOGLE stock with current price $20 in the account

  Scenario: Maximum share for portfolio manager can buy
    Then a portfolio manager is entitled to buy up to 115 share of GOOGLE stock

  Scenario: Allocating the shares into investor's account
    When a portfolio manager buy 115 share of GOOGLE stock
    And a portfolio manager allocates 50 shares of GOOGLE stock to John's account
    And a portfolio manager allocates 65 shares of GOOGLE stock to Sarah's account
    Then an investor John has 100 shares of GOOGLE stock
    And an investor Sarah has 75 shares of GOOGLE stock

  Scenario: Suggesting final position for given stock
    When a portfolio manager buy 100 share of GOOGLE stock
    Then a portfolio manager suggests total 91.43 shares of GOOGLE stock in John's account
    And a portfolio manager suggests total 68.57 shares of GOOGLE stock in Sarah's account
    And a portfolio manager suggests additional 41.43 shares of GOOGLE stock in John's account
    And a portfolio manager suggests additional 58.57 shares of GOOGLE stock in Sarah's account

  Scenario: Basic allocation logic for new shares
    When a portfolio manager buy 100 share of GOOGLE stock
    And a portfolio manager allocates the new GOOGLE stock
    Then an investor John has 91 shares of GOOGLE stock
    And an investor Sarah has 69 shares of GOOGLE stock


  #  Scenario Outline: Maximum share to buy & allocate
#    Given an investor John has a capability to own extra 50 share of GOOGLE stock
#    And an investor Sarah has a capability to own extra 65 share of GOOGLE stock
#    Then a portfolio manager Mary entitled to buy up to 115 share of GOOGLE stock
#    And a portfolio manager Mary can allocate the 50 share of GOOGLE stock into John account
#    And a portfolio manager Mary can allocate the 65 share of GOOGLE stock into Sarah account

#    Given an investor John has an account with $50,000 capital
#    And an investor Sarah has an account with $150,000 capital
#    When an investor John has 50 shares of GOOGLE stock with current price $20 in his account
#    And John sets 4% as target percent for GOOGLE stock
#    And an investor John has 25 shares of APPLE stock with current price $10 in his account
#    And John sets 1.5% as target percent for APPLE stock
#    When an investor Sarah has 10 shares of GOOGLE stock with current price $20 in her account
#    And Sarah sets 1% as target percent for GOOGLE stock
#    And an investor Sarah has 15 shares of APPLE stock with current price $10 in the account
#    And Sarah sets 2% as target percent for APPLE stock
#    Then a portfolio manager Mary buy