Feature: Buy & Allocate a new Stock

  Scenario Outline: Maximum share of Stock
    Given an investor <Investor> has an account with <Capital> capital
    And an investor <Investor> sets a <Stock Symbol> Stock with target percent <Target Percent>
    When current price of <Stock Symbol> Stock is <Current Price>
    Then an investor <Investor> has an account with <Target Market Value> for <Stock Symbol> Stock
    And an investor <Investor> can maintain a <Stock Symbol> Stock up to <Max Share> share
    Examples:
      | Investor | Capital  | Stock Symbol | Current Price | Target Percent | Target Market Value | Max Share |
      | John     | $50,000  | GOOGLE       | $20           | 4%             | $2,000              | 100       |
      | John     | $50,000  | APPLE        | $10           | 1.5%           | $750                | 75        |
      | Sarah    | $150,000 | GOOGLE       | $20           | 1%             | $1,500              | 75        |
      | Sarah    | $150,000 | APPLE        | $10           | 2%             | $3,000              | 300       |