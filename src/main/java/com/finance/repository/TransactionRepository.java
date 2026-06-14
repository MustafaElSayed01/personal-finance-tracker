package com.finance.repository;

import com.finance.model.Category;
import com.finance.model.Transaction;
import com.finance.model.TransactionType;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Stores transactions in memory and provides CSV persistence hooks.
 */
public class TransactionRepository {
    private final List<Transaction> transactions;
    private final String fileName;

    /**
     * Creates a repository backed by the given CSV file name.
     *
     * @param fileName the CSV file name used for persistence
     * @throws IllegalArgumentException if fileName is null or blank
     */
    public TransactionRepository(String fileName) {
        if (fileName == null) {
            throw new IllegalArgumentException("fileName cannot be null");
        }
        if (fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("fileName cannot be blank");
        }
        this.transactions = new ArrayList<>();
        this.fileName = fileName;
    }

    /**
     * Returns an immutable snapshot of all stored transactions.
     *
     * @return an unmodifiable copy of the current transaction list
     */
    public List<Transaction> getTransactions() {
        return List.copyOf(transactions);
    }

    /**
     * Adds a transaction to the in-memory repository.
     *
     * @param transaction the transaction to store
     * @throws IllegalArgumentException if transaction is null
     */
    public void add(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("transaction cannot be null");
        }
        transactions.add(transaction);
    }

    /**
     * Parses a single CSV row into a {@link Transaction}.
     *
     * @param line the CSV line to parse
     * @return the parsed transaction
     * @throws IllegalArgumentException if the line is null, blank, or malformed
     */
    private Transaction parseTransaction(String line) {
        if (line == null || line.isBlank()) {
            throw new IllegalArgumentException("line cannot be null");
        }
        String[] lineArray = line.split(",");
        BigDecimal amount = new BigDecimal(lineArray[3]);
        return new Transaction(UUID.fromString(lineArray[0]), TransactionType.valueOf(lineArray[1]), Category.valueOf(lineArray[2]), amount, LocalDate.parse(lineArray[4]), lineArray[5]);
    }

    /**
     * Loads transactions from the configured CSV file into memory.
     *
     * @throws IOException if reading the CSV file fails
     */
    public void loadFromCsv() throws IOException {
        Path path = Paths.get(fileName);
        if (Files.notExists(path)) {
            return;
        }

        for (String line : Files.readAllLines(path)) {
            if (line.startsWith("id")) continue;
            transactions.add(parseTransaction(line));
        }
    }

    /**
     * Saves the current in-memory transactions to the configured CSV file.
     *
     * @throws IOException if writing the CSV file fails
     */
    public void saveToCsv() throws IOException {
        Path path = Paths.get(fileName);
        try (var writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            writer.write("id,type,category,amount,date,description\n");
            for (Transaction transaction : transactions) {
                writer.append(transaction.toCSV()).append("\n");
            }
        }
    }
}
