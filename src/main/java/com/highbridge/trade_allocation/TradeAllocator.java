package com.highbridge.trade_allocation;

import com.highbridge.trade_allocation.domain.AccountRepository;
import com.highbridge.trade_allocation.domain.Portfolio;
import com.highbridge.trade_allocation.domain.Trade;

public class TradeAllocator {
    public static void main(String[] args) {
        String tradesCsv = args[0];
        String capitalsCsv = args[1];
        String holdingsCsv = args[2];
        String targetsCsv = args[3];
        String allocationsCsv = args[4];

        AccountRepository repository = new AccountRepository();

        CsvAccountLoader accountLoader = new CsvAccountLoader(repository);
        accountLoader.loadFromCsv(capitalsCsv, targetsCsv, holdingsCsv);

        Portfolio portfolio = new Portfolio();
        repository.all().forEach(portfolio::add);

        for (Trade newTrade : accountLoader.loadNewTrades(tradesCsv)) {
            portfolio.reallocateHoldings(newTrade);
        }
        new CsvAccountPrinter(portfolio.accounts()).print(allocationsCsv);
    }
}