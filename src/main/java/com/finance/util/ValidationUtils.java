package com.finance.util;

import java.math.BigDecimal;

/**
 * Shared input validation helpers for finance components.
 */
public final class ValidationUtils {
    private ValidationUtils() {
    }

    /**
     * Ensures a value is not null.
     *
     * @param value the value to validate
     * @param name the parameter name used in the exception message
     * @return the same value, if it is not null
     * @param <T> the value type
     * @throws IllegalArgumentException if {@code value} is null
     */
    public static <T> T requireNonNull(T value, String name) {
        if (value == null) {
            throw new IllegalArgumentException(name + " cannot be null");
        }
        return value;
    }

    /**
     * Ensures a string is neither null nor blank.
     *
     * @param value the string to validate
     * @param name the parameter name used in the exception message
     * @return the same string, if it is valid
     * @throws IllegalArgumentException if {@code value} is null or blank
     */
    public static String requireNotBlank(String value, String name) {
        requireNonNull(value, name);
        if (value.isBlank()) {
            throw new IllegalArgumentException(name + " cannot be blank");
        }
        return value;
    }

    /**
     * Ensures a monetary value is strictly positive.
     *
     * @param value the amount to validate
     * @param name the parameter name used in the exception message
     * @return the same value, if it is positive
     * @throws IllegalArgumentException if {@code value} is null or not positive
     */
    public static BigDecimal requirePositive(BigDecimal value, String name) {
        requireNonNull(value, name);
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(name + " must be positive");
        }
        return value;
    }
}
