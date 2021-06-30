package com.highbridge.trade_allocation;

import com.highbridge.trade_allocation.domain.Account;
import com.opencsv.CSVWriter;

import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvAccountPrinter {

    private final List<Account> accounts;

    public CsvAccountPrinter(List<Account> accounts) {
        this.accounts = accounts;
    }

    public void print(String outputCsv) {
        try {
            Writer writer = Files.newBufferedWriter(Paths.get(outputCsv));
            CSVWriter csvWriter = new CSVWriter(writer);
            csvWriter.writeAll(toStringArray());
            csvWriter.flush();
            csvWriter.close();
        }
        catch (Exception ex) {
            System.out.println("something wrong to write the csv file: " + ex);
        }
    }

    private List<String[]> toStringArray() {
        List<String[]> records = new ArrayList<>();
        records.add(new String[] { "account", "stock", "quantity" });
        this.accounts.forEach(account ->
            account.holdStocks().forEach(stock ->
                records.add(new String[] {account.investor(), stock, String.valueOf(account.currentQuantity(stock))})
            )
        );
        return records;
    }
}