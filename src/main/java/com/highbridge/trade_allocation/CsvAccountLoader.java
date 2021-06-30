package com.highbridge.trade_allocation;

import com.highbridge.trade_allocation.domain.Account;
import com.highbridge.trade_allocation.domain.AccountRepository;
import com.highbridge.trade_allocation.domain.Holding;
import com.highbridge.trade_allocation.domain.Trade;
import com.highbridge.trade_allocation.domain.Trade.BuyOrSell;
import com.highbridge.trade_allocation.domain.generic.Money;
import com.highbridge.trade_allocation.domain.generic.Percent;
import com.opencsv.CSVReader;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Triplet;

import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

class CsvAccountLoader {
    private AccountRepository repository;
    private CsvFileLoader loader;

    public CsvAccountLoader(AccountRepository repository) {
        this.repository = repository;
        this.loader = new CsvFileLoader();
    }

    public void loadFromCsv(String capitalCsv, String targetCsv, String holdingCsv) {
        loadCapitals(capitalCsv);
        loadTargetPercents(targetCsv);
        loadHoldings(holdingCsv);
    }

    private void loadCapitals(String csv) {
        loader.loadCapitals(csv).forEach(e ->
                repository.addAccount(new Account(e.getValue0(), e.getValue1()))
        );
    }

    private void loadTargetPercents(String csv) {
        loader.loadTargets(csv).forEach(e ->
                repository.findByInvestor(e.getValue1()).addStockTargetPercent(e.getValue0(), e.getValue2())
        );
    }

    private void loadHoldings(String csv) {
        loader.loadHoldings(csv).forEach(e ->
                repository.findByInvestor(e.getValue0()).addHolding(new Holding(e.getValue1(), e.getValue3(), e.getValue2()))
        );
    }

    List<Trade> loadNewTrades(String csv) {
        return loader.loadTrades(csv).stream()
                .map(e -> new Trade(e.getValue0(), e.getValue2(), e.getValue3(), e.getValue1()))
                .collect(Collectors.toList());
    }
}

class CsvFileLoader {

    // [0]=stock, [1]=type, [2]=quantity, [3]=price
    List<Quartet<String, BuyOrSell, Long, Money>> loadTrades(String csvFileName) {
        return loadFromCsv(csvFileName).stream()
                .map(e -> Quartet.with(e[0], BuyOrSell.valueOf(e[1]), Long.valueOf(e[2]), toMoney(e[3]))).collect(Collectors.toList());
    }

    // [0]=account, [1]=capital
    List<Pair<String, Money>> loadCapitals(String csvFileName) {
        return loadFromCsv(csvFileName).stream().map(e -> Pair.with(e[0], toMoney(e[1]))).collect(Collectors.toList());
    }

    // [0]=account, [1]=stock, [2]=quantity, [3]=price
    List<Quartet<String, String, Long, Money>> loadHoldings(String csvFileName) {
        return loadFromCsv(csvFileName).stream()
                .map(e -> Quartet.with(e[0], e[1], Long.valueOf(e[2]), toMoney(e[3]))).collect(Collectors.toList());
    }

    // [0]=stock, [1]=account, [2]=targetPercent
    List<Triplet<String, String, Percent>> loadTargets(String csvFileName) {
        return loadFromCsv(csvFileName).stream().map(e -> Triplet.with(e[0], e[1], toPercent(e[2]))).collect(Collectors.toList());
    }

    private List<String[]> loadFromCsv(String fileName) {
        CSVReader reader = new CSVReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(fileName)));
        try {
            reader.skip(1);
            return reader.readAll();
        }
        catch(Exception ex) {
            System.out.println(ex); // TODO BL - we don't handle exception for now
        }
        finally {
            try {
                reader.close();
            }
            catch (Exception ex) {
                System.out.println("error to close the csv reader: " + ex);
            }
        }
        throw new IllegalStateException("cannot load the csv " + fileName);
    }


    private Money toMoney(String money) {
        return Money.dollars(Double.valueOf(money));
    }

    private Percent toPercent(String percent) {
        return Percent.of(Double.valueOf(percent));
    }
}