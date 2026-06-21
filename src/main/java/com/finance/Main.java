package com.finance;

import com.finance.repository.TransactionRepository;
import com.finance.service.FinanceService;
import com.finance.ui.ConsoleUI;

import java.io.IOException;

public class Main {
    /**
     * Starts the application by creating the repository, loading persisted transactions,
     * and launching the console UI.
     *
     * @param args command-line arguments; currently unused
     */
    public static void main(String[] args) {
        String fileName = "transactions.csv";
        TransactionRepository transactionRepository = new TransactionRepository(fileName);
        FinanceService financeService = new FinanceService(transactionRepository);
        ConsoleUI consoleUI = new ConsoleUI(financeService);
        try {
            financeService.loadFromCsv();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load transactions from CSV", e);
        }
        consoleUI.run();
    }
}
