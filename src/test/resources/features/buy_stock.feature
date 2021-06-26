Feature: Buy & Allocate a new Stock

  Scenario Outline: Maximum share of Stock
    Given an investor <Investor> has an account with <Capital> capital
    Given an investor <Investor> sets a <Stock Symbol> Stock with target percent <Target Percent>
    When current price of <Stock Symbol> Stock is <Current Price>
    Then an investor <Investor> can maintain a <Stock Symbol> Stock up to <Max Share>
    Examples:
      | Investor | Capital  | Stock Symbol | Current Price | Target Percent | Max Share |
      | John     | $50,000  | GOOGLE       | $20           | 4%             | 100       |
      | John     | $50,000  | APPLE        | $10           | 1.5%           | 75        |
      | Sarah    | $150,000 | GOOGLE       | $20           | 1%             | 75        |
      | Sarah    | $150,000 | APPLE        | $10           | 2%             | 300       |