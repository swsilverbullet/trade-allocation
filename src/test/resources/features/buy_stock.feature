Feature: Buy & Allocate a new Stock

  Scenario Outline: What is "Target Max Value" & "Target Maximum Share"
    Given an investor <Investor> has an account with <Capital> capital
    And an investor <Investor> sets a <Stock Symbol> Stock with target percent <Target Percent>
    When current price of <Stock Symbol> Stock is <Current Price>
    Then an investor <Investor> has an account with <Target Market Value> for <Stock Symbol> Stock
    And an investor <Investor> can maintain a <Stock Symbol> Stock up to <Target Max Share> share
    Examples:
      | Investor | Capital  | Stock Symbol | Current Price | Target Percent | Target Market Value | Target Max Share |
      | John     | $50,000  | GOOGLE       | $20           | 4%             | $2,000              | 100              |
      | John     | $50,000  | APPLE        | $10           | 1.5%           | $750                | 75               |
      | Sarah    | $150,000 | GOOGLE       | $20           | 1%             | $1,500              | 75               |
      | Sarah    | $150,000 | APPLE        | $10           | 2%             | $3,000              | 300              |

  Scenario Outline: Calculating allowed share when the account already holds some
    Given an investor <Investor> has an account with <Capital> capital
    When an investor <Investor> has <Owned Quantity> shares of <Stock Symbol> Stock with current price <Current Price> in the account
    And an investor <Investor> sets <Target Percent> target percent of <Stock Symbol> Stock
    Then an investor <Investor> can own <Extra Quantity> more shares of <Stock Symbol> Stock
    Examples:
      | Investor | Capital  | Stock Symbol | Current Price | Target Percent | Owned Quantity | Extra Quantity |
      | John     | $50,000  | GOOGLE       | $20           | 4%             | 50             | 50             |
      | John     | $50,000  | APPLE        | $10           | 1.5%           | 25             | 50             |
      | Sarah    | $150,000 | GOOGLE       | $20           | 1%             | 10             | 65             |
      | Sarah    | $150,000 | APPLE        | $10           | 2%             | 50             | 250            |

  Scenario: Maximum share for portfolio manager can buy
    Given an investor John has an account with $50,000 capital
    And an investor John sets 4% target percent of GOOGLE Stock
    And an investor John has 50 shares of GOOGLE Stock with current price $20 in the account
    Given an investor Sarah has an account with $150,000 capital
    And an investor Sarah sets 1% target percent of GOOGLE Stock
    And an investor Sarah has 10 shares of GOOGLE Stock with current price $20 in the account
    Then a portfolio manager is entitled to buy up to 115 share of GOOGLE Stock

  Scenario: Allocating the shares into investor's account
    Given an investor John has an account with $50,000 capital
    And an investor John sets 4% target percent of GOOGLE Stock
    And an investor John has 50 shares of GOOGLE Stock with current price $20 in the account
    Given an investor Sarah has an account with $150,000 capital
    And an investor Sarah sets 1% target percent of GOOGLE Stock
    And an investor Sarah has 10 shares of GOOGLE Stock with current price $20 in the account
    When a portfolio manager buy 115 share of GOOGLE Stock
    And a portfolio manager allocates 50 shares of GOOGLE Stock to John's account
    And a portfolio manager allocates 65 shares of GOOGLE Stock to Sarah's account
    Then an investor John has 100 shares of GOOGLE Stock
    And an investor Sarah has 75 shares of GOOGLE Stock




  #  Scenario Outline: Maximum share to buy & allocate
#    Given an investor John has a capability to own extra 50 share of GOOGLE Stock
#    And an investor Sarah has a capability to own extra 65 share of GOOGLE Stock
#    Then a portfolio manager Mary entitled to buy up to 115 share of GOOGLE Stock
#    And a portfolio manager Mary can allocate the 50 share of GOOGLE Stock into John account
#    And a portfolio manager Mary can allocate the 65 share of GOOGLE Stock into Sarah account

#    Given an investor John has an account with $50,000 capital
#    And an investor Sarah has an account with $150,000 capital
#    When an investor John has 50 shares of GOOGLE Stock with current price $20 in his account
#    And John sets 4% as target percent for GOOGLE Stock
#    And an investor John has 25 shares of APPLE Stock with current price $10 in his account
#    And John sets 1.5% as target percent for APPLE Stock
#    When an investor Sarah has 10 shares of GOOGLE Stock with current price $20 in her account
#    And Sarah sets 1% as target percent for GOOGLE Stock
#    And an investor Sarah has 15 shares of APPLE Stock with current price $10 in the account
#    And Sarah sets 2% as target percent for APPLE Stock
#    Then a portfolio manager Mary buy