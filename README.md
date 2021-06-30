# Instruction
## 1. Run Feature Test
```
$ mvn clean test
```

## 2. Feature Files
Following feature files cover most of problems.
```
src/test/resources/features/
- 1_basic_terms_rules.feature
- 2_buy_stock.feature
- 3_sell_stock.feature
- 4_error_conditions.feature
```

## 3. Run Main
All csv files are bundled with jar.
```
$ cd ./target
$ java -cp ./trade-allocation-1.0-SNAPSHOT-jar-with-dependencies.jar com.highbridge.trade_allocation.TradeAllocator trades.csv capitals.csv holdings.csv targets.csv allocations.csv
```