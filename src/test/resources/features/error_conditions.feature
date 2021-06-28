Feature: Error conditions
  Background:
    Given an investor John has an account with $50,000 capital
    And an investor John sets 4% target percent of GOOGLE stock
    And an investor John has 50 quantity of GOOGLE stock with current price $20 in the account
    And an investor John sets 1.5% target percent of APPLE stock
    And an investor John has 25 quantity of APPLE stock with current price $10 in the account

    Given an investor Sarah has an account with $150,000 capital
    And an investor Sarah sets 1% target percent of GOOGLE stock
    And an investor Sarah has 10 quantity of GOOGLE stock with current price $20 in the account
    And an investor Sarah sets 2% target percent of APPLE stock
    And an investor Sarah has 50 quantity of APPLE stock with current price $10 in the account

  Scenario: 4. ERROR Condition: SUGGESTED_FINAL_POSITION < 0
    When a portfolio manager sell 80 quantity of APPLE stock
    And a portfolio manager suggests total -1.0 quantity of APPLE stock in John's account
    And a portfolio manager reallocates the sold APPLE stock
    Then an investor John has 0 quantity of APPLE stock
    And an investor Sarah has 0 quantity of APPLE stock
    Then an investor John has 50 quantity of GOOGLE stock
    And an investor Sarah has 10 quantity of GOOGLE stock
