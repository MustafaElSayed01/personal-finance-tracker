package com.finance.ui;

import com.finance.model.Category;
import com.finance.model.Transaction;
import com.finance.model.TransactionType;
import com.finance.service.FinanceService;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ConsoleUI {
    private final Scanner scanner = new Scanner(System.in);
    private final FinanceService financeService;

    /**
     * Creates a console UI backed by the given finance service.
     *
     * @param financeService the service used for transaction operations
     * @throws IllegalArgumentException if {@code financeService} is null
     */
    public ConsoleUI(FinanceService financeService) {
        this.financeService = financeService;
    }

    private void displayMenu() {
        System.out.println("Pick a choice:");
        System.out.println("1. Add a new transaction");
        System.out.println("2. View all transactions");
        System.out.println("3. Filter transactions");
        System.out.println("4. Save to CSV");
        System.out.println("0. Quit");
    }

    private void displayFilterMenu() {
        System.out.println("Pick a filter option:");
        System.out.println("1. Filter By Date Range");
        System.out.println("2. Filter By Transaction Type");
        System.out.println("3. Filter By Transaction Category");
        System.out.println("4. Filter By Transaction Description");
        System.out.println("0. Step Back");
    }

    private TransactionType readTransactionType() {
        TransactionType[] transactionTypes = TransactionType.values();

        System.out.println("Pick transaction type");
        do {
            for (int i = 0; i < transactionTypes.length; i++) {
                System.out.println((i + 1) + ". " + transactionTypes[i]);
            }
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice < 1 || choice > transactionTypes.length) {
                    System.out.println("Please enter a valid choice");
                    continue;
                }
                return transactionTypes[choice - 1];
            } catch (NumberFormatException e) {
                System.out.println("Invalid Number.");
            }
        }
        while (true);
    }

    private Category readTransactionCategory() {
        Category[] categories = Category.values();

        System.out.println("Pick transaction category");
        do {
            for (int i = 0; i < categories.length; i++) {
                System.out.println((i + 1) + ". " + categories[i]);
            }
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice < 1 || choice > categories.length) {
                    System.out.println("Please enter a valid choice");
                    continue;
                }
                return categories[choice - 1];
            } catch (NumberFormatException e) {
                System.out.println("Invalid Number.");
            }
        }
        while (true);
    }

    private LocalDateTime readTransactionDate(boolean allowBlank) {
        System.out.println("Enter transaction date time following yyyy-MM-dd HH:mm pattern" + (allowBlank ? ", leave blank for current time stamp." : "."));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        do {
            String input = scanner.nextLine().trim();

            if (allowBlank) {
                if (input.isEmpty()) {
                    return LocalDateTime.now();
                }
            }

            try {
                return LocalDateTime.parse(input, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid input format: " + e.getMessage());
            }
        } while (true);
    }

    private BigDecimal readTransactionAmount() {
        System.out.println("Enter transaction amount:");
        do {
            try {
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount.");
            }
        } while (true);
    }

    private String readTransactionDescription() {
        System.out.println("Enter transaction description or a part of it:");
        do {
            String description = scanner.nextLine().trim().toLowerCase();
            if (description.isBlank()) {
                System.out.println("Can't be blank");
                continue;
            }
            return description;
        } while (true);

    }

    private void promptAddTransaction() {
        TransactionType transactionType = readTransactionType();
        Category category = readTransactionCategory();
        BigDecimal amount = readTransactionAmount();
        LocalDateTime dateTime = readTransactionDate(true);
        System.out.println("Enter transaction description");
        String description = scanner.nextLine();
        UUID id = UUID.randomUUID();

        System.out.println(financeService.addTransaction(new Transaction(id, transactionType, category, amount, dateTime, description))
                ? "Transaction added successfully."
                : "Duplicate transaction!, failed to add.");

    }

    private void displayTransactions(List<Transaction> transactions) {
        if (transactions.isEmpty()) {
            System.out.println("No transactions found. Please add some transactions first.");
        }
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    private void saveToCsv() {
        try {
            financeService.saveToCsv();
            System.out.println("Transactions saved successfully.");
        } catch (IOException e) {
            System.out.println("Error while saving csv file.");
        }
    }

    private boolean handleMainMenuInput(int choice){
        switch (choice) {
            case 1 -> promptAddTransaction();
            case 2 -> displayTransactions(financeService.getTransactions());
            case 3 -> runFilterMenu();
            case 4 -> saveToCsv();
            case 0 -> {
                return false;
            }
            default -> System.out.println("Invalid choice");
        }
        return true;
    }


    private boolean handleFilterMenuInput(int choice) {
        switch (choice) {
            case 1 ->
                    displayTransactions(financeService.filterByDateRange(readTransactionDate(false), readTransactionDate(true)));
            case 2 -> displayTransactions(financeService.filterByType(readTransactionType()));
            case 3 -> displayTransactions(financeService.filterByCategory(readTransactionCategory()));
            case 4 -> displayTransactions(financeService.filterByDescription(readTransactionDescription()));
            case 0 -> {
                return false;
            }
            default -> System.out.println("Invalid choice");
        }
        return true;
    }

    private void runFilterMenu() {
        while (true) {
            displayFilterMenu();
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                boolean isValid = handleFilterMenuInput(choice);
                if (!isValid) {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid choice");
            }
        }
    }

    /**
     * Starts the interactive console loop.
     */
    public void run() {
        System.out.println("Welcome to Personal Finance Tracker Console UI");
        while (true) {
            displayMenu();
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                boolean isValid = handleMainMenuInput(choice);
                if (!isValid) {
                    System.out.println("Thanks for using our application.");
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid choice");
            }
        }
    }
}
