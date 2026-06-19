package com.finance.model;

import com.finance.util.ValidationUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a single financial transaction record.
 */
public class Transaction {
    private final UUID id;
    private final TransactionType type;
    private final Category category;
    private final BigDecimal amount;
    private final LocalDateTime dateTime;
    private final String description;

    /**
     * Creates a transaction with its core details.
     *
     * @param id          the unique transaction identifier
     * @param type        the transaction type
     * @param category    the transaction category
     * @param amount      the transaction amount
     * @param dateTime    the transaction date time
     * @param description a short description of the transaction
     * @throws IllegalArgumentException if any argument is null, if amount is not positive,
     *                                  if description is blank, or if description contains
     *                                  newline characters
     */
    public Transaction(UUID id, TransactionType type, Category category, BigDecimal amount, LocalDateTime dateTime, String description) {
        ValidationUtils.requireNonNull(id, "id");
        ValidationUtils.requireNonNull(type, "type");
        ValidationUtils.requireNonNull(category, "category");
        ValidationUtils.requirePositive(amount, "amount");
        ValidationUtils.requireNonNull(dateTime, "dateTime");
        ValidationUtils.requireNotBlank(description, "description");
        if (description.contains("\n") || description.contains("\r")) {
            throw new IllegalArgumentException("description cannot contain newline characters");
        }

        this.id = id;
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.dateTime = dateTime;
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
     * Returns the transaction date time.
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Returns the transaction description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns a readable multi-line representation of this transaction.
     *
     * @return a formatted string containing all transaction fields
     */
    @Override
    public String toString() {
        return "ID: " + id + "\nTransaction Type: " + type + "\nCategory: " + category + "\nAmount: " + amount + "\nDateTime: " + dateTime + "\nDescription: " + description;
    }

    /**
     * Serializes this transaction to a CSV row.
     *
     * @return a comma-separated representation of this transaction
     */
    public String toCSV() {
        return id + "," + type + "," + category + "," + amount + "," + dateTime + "," + description;
    }
}
