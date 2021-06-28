Feature: Error conditions
  Background:
    Given an investor John has an account with $50,000 capital
    And an investor John sets 4% target percent of GOOGLE stock
    And an investor John has 41 quantity of GOOGLE stock with current price $20 in the account
    And an investor John sets 1.5% target percent of APPLE stock
    And an investor John has 25 quantity of APPLE stock with current price $10 in the account

    Given an investor Sarah has an account with $150,000 capital
    And an investor Sarah sets 1% target percent of GOOGLE stock
    And an investor Sarah has 59 quantity of GOOGLE stock with current price $20 in the account
    And an investor Sarah sets 2% target percent of APPLE stock
    And an investor Sarah has 50 quantity of APPLE stock with current price $10 in the account

  Scenario: 4. ERROR Condition: SUGGESTED_FINAL_POSITION (-1) < 0
    When a portfolio manager sell 80 quantity of APPLE stock
    And a portfolio manager suggests total -1.0 quantity of APPLE stock in John's account
    And a portfolio manager reallocates the sold APPLE stock
    Then an investor John has 0 quantity of APPLE stock
    And an investor Sarah has 0 quantity of APPLE stock
    Then an investor John has 41 quantity of GOOGLE stock
    And an investor Sarah has 59 quantity of GOOGLE stock

  Scenario: 5. ERROR Condition: SUGGESTED_FINAL_POSITION (76) > MAX_SHARES (75)
    When a portfolio manager buy 305 quantity of APPLE stock
    And an investor John can maintain a APPLE stock up to 75
    And a portfolio manager suggests total 76 quantity of APPLE stock in John's account
    And a portfolio manager reallocates the new APPLE stock
    Then an investor John has 0 quantity of APPLE stock
    And an investor Sarah has 0 quantity of APPLE stock
    Then an investor John has 41 quantity of GOOGLE stock
    And an investor Sarah has 59 quantity of GOOGLE stock

  Scenario: 6. ERROR Condition: SUGGESTED_FINAL_POSITION (24) < Current Held Quantity (25) when trade is a BUY
    When a portfolio manager buy 45 quantity of APPLE stock
    And a portfolio manager suggests total 24 quantity of APPLE stock in John's account
    And a portfolio manager reallocates the new APPLE stock
    Then an investor John has 0 quantity of APPLE stock
    And an investor Sarah has 0 quantity of APPLE stock
    Then an investor John has 41 quantity of GOOGLE stock
    And an investor Sarah has 59 quantity of GOOGLE stock

  Scenario: 7. ERROR Condition: SUGGESTED_FINAL_POSITION (10) > Current Held Quantity (25-20=5) when trade is a SELL
    Given an investor John has -20 quantity of APPLE stock with current price $10 in the account
    When a portfolio manager sell 5 quantity of APPLE stock
    And a portfolio manager suggests total 10 quantity of APPLE stock in John's account
    And a portfolio manager reallocates the new APPLE stock
    Then an investor John has 0 quantity of APPLE stock
#    And an investor Sarah has 0 quantity of APPLE stock
    Then an investor John has 41 quantity of GOOGLE stock
    And an investor Sarah has 59 quantity of GOOGLE stock
