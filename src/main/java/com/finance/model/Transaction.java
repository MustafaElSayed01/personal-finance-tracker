package com.finance.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Represents a single financial transaction record.
 */
public class Transaction {
    private final UUID id;
    private final TransactionType type;
    private final Category category;
    private final BigDecimal amount;
    private final LocalDate date;
    private final String description;

    /**
     * Creates a transaction with its core details.
     *
     * @param id          the unique transaction identifier
     * @param type        the transaction type
     * @param category    the transaction category
     * @param amount      the transaction amount
     * @param date        the transaction date
     * @param description a short description of the transaction
     * @throws IllegalArgumentException if any argument is null, if amount is not positive,
     *                                  or if description is blank
     */
    public Transaction(UUID id, TransactionType type, Category category, BigDecimal amount, LocalDate date, String description) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        if (type == null) {
            throw new IllegalArgumentException("type cannot be null");
        }
        if (category == null) {
            throw new IllegalArgumentException("category cannot be null");
        }
        if (amount == null) {
            throw new IllegalArgumentException("amount cannot be null");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("amount must be positive");
        }
        if (date == null) {
            throw new IllegalArgumentException("date cannot be null");
        }
        if (description == null) {
            throw new IllegalArgumentException("description cannot be null");
        }
        if (description.isBlank()) {
            throw new IllegalArgumentException("description cannot be blank");
        }

        this.id = id;
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    /**
     * Returns the unique transaction identifier.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Returns the transaction type.
     */
    public TransactionType getType() {
        return type;
    }

    /**
     * Returns the transaction category.
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Returns the transaction amount.
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Returns the transaction date.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Returns the transaction description.
     */
    public String getDescription() {
        return description;
    }
}
