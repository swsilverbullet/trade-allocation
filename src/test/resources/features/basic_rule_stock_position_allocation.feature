Feature: Stock position & allocation basic rules

  Scenario Outline: What is "Target Max Value" & "Target Maximum Share"
    Given an investor <Investor> has an account with <Capital> capital
    And an investor <Investor> sets a <Stock Symbol> stock with target percent <Target Percent>
    When current price of <Stock Symbol> stock is <Current Price>
    Then an investor <Investor> has an account with <Target Market Value> for <Stock Symbol> stock
    And an investor <Investor> can maintain a <Stock Symbol> stock up to <Target Max Share> share

    Examples:
      | Investor | Capital  | Stock Symbol | Current Price | Target Percent | Target Market Value | Target Max Share |
      | John     | $50,000  | GOOGLE       | $20           | 4%             | $2,000              | 100              |
      | John     | $50,000  | APPLE        | $10           | 1.5%           | $750                | 75               |
      | Sarah    | $150,000 | GOOGLE       | $20           | 1%             | $1,500              | 75               |
      | Sarah    | $150,000 | APPLE        | $10           | 2%             | $3,000              | 300              |

  Scenario Outline: Calculating allowed share when the account already holds some
    Given an investor <Investor> has an account with <Capital> capital
    When an investor <Investor> has <Owned Quantity> shares of <Stock Symbol> stock with current price <Current Price> in the account
    And an investor <Investor> sets <Target Percent> target percent of <Stock Symbol> stock
    Then an investor <Investor> can own <Extra Quantity> more shares of <Stock Symbol> stock

    Examples:
      | Investor | Capital  | Stock Symbol | Current Price | Target Percent | Owned Quantity | Extra Quantity |
      | John     | $50,000  | GOOGLE       | $20           | 4%             | 50             | 50             |
      | John     | $50,000  | APPLE        | $10           | 1.5%           | 25             | 50             |
      | Sarah    | $150,000 | GOOGLE       | $20           | 1%             | 10             | 65             |
      | Sarah    | $150,000 | APPLE        | $10           | 2%             | 50             | 250            |