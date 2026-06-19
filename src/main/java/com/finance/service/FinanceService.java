package com.finance.service;

import com.finance.model.Category;
import com.finance.model.Transaction;
import com.finance.model.TransactionType;
import com.finance.repository.TransactionRepository;
import com.finance.util.ValidationUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

/**
 * Provides business operations for managing and querying transactions.
 * <p>
 * This service keeps validation close to the public API and delegates
 * persistence concerns to {@link TransactionRepository}.
 */
public class FinanceService {
    private final TransactionRepository transactionRepository;

    /**
     * Creates a finance service backed by the given repository.
     *
     * @param transactionRepository the repository used for transaction storage
     * @throws IllegalArgumentException if {@code transactionRepository} is null
     */
    public FinanceService(TransactionRepository transactionRepository) {
        ValidationUtils.requireNonNull(transactionRepository, "transactionRepository");
        this.transactionRepository = transactionRepository;
    }

    /**
     * Checks whether the given transaction already exists in the repository.
     *
     * @param transaction the transaction to compare
     * @return {@code true} if an equal transaction is already stored, otherwise {@code false}
     */
    private boolean isDuplicate(Transaction transaction) {
        return transactionRepository.getTransactions().stream().anyMatch(t -> t.getType().equals(transaction.getType()) && t.getCategory().equals(transaction.getCategory()) && t.getAmount().compareTo(transaction.getAmount()) == 0 && t.getDateTime().equals(transaction.getDateTime()));
    }

    /**
     * Adds a transaction if it does not already exist in the repository.
     *
     * <p>Two transactions are treated as duplicates when they have the same
     * type, category, amount, and date/time.</p>
     *
     * @param transaction the transaction to add
     * @return {@code true} when the transaction was added, {@code false} when it was a duplicate
     * @throws IllegalArgumentException if {@code transaction} is null
     */
    public boolean addTransaction(Transaction transaction) {
        ValidationUtils.requireNonNull(transaction, "transaction");

        if (isDuplicate(transaction)) {
            return false;
        }
        transactionRepository.add(transaction);
        return true;
    }

    /**
     * Returns all transactions whose date/time falls within the inclusive range.
     *
     * @param start the inclusive lower bound
     * @param end the inclusive upper bound
     * @return the matching transactions ordered as stored in the repository
     * @throws IllegalArgumentException if either argument is null or if {@code start} is after {@code end}
     */
    public List<Transaction> filterByDateRange(LocalDateTime start, LocalDateTime end) {
        ValidationUtils.requireNonNull(start, "start");
        ValidationUtils.requireNonNull(end, "end");
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("start cannot be after end");
        }
        return transactionRepository.getTransactions().stream()
                .filter(t -> !t.getDateTime().isBefore(start) && !t.getDateTime().isAfter(end))
                .toList();
    }

    /**
     * Returns all transactions that match the given type.
     *
     * @param transactionType the type to match
     * @return the matching transactions
     * @throws IllegalArgumentException if {@code transactionType} is null
     */
    public List<Transaction> filterByType(TransactionType transactionType) {
        ValidationUtils.requireNonNull(transactionType, "transactionType");
        return transactionRepository.getTransactions().stream()
                .filter(t -> t.getType().equals(transactionType))
                .toList();
    }

    /**
     * Returns all transactions that match the given category.
     *
     * @param category the category to match
     * @return the matching transactions
     * @throws IllegalArgumentException if {@code category} is null
     */
    public List<Transaction> filterByCategory(Category category) {
        ValidationUtils.requireNonNull(category, "category");
        return transactionRepository.getTransactions().stream()
                .filter(t -> t.getCategory().equals(category))
                .toList();
    }

    /**
     * Returns all transactions whose description contains the given text.
     *
     * <p>The match is case-insensitive.</p>
     *
     * @param description the search text
     * @return the matching transactions
     * @throws IllegalArgumentException if {@code description} is null or blank
     */
    public List<Transaction> filterByDescription(String description) {
        ValidationUtils.requireNotBlank(description, "description");
        return transactionRepository.getTransactions().stream()
                .filter(t -> t.getDescription().toLowerCase(Locale.ROOT).contains(description.toLowerCase(Locale.ROOT)))
                .toList();
    }
}
