# Personal Finance Tracker

A Java console application for recording, filtering, and exporting transactions.

## What It Does

- Stores income and expense transactions with type, category, amount, date/time, and description
- Lists all stored transactions from the in-memory repository
- Filters transactions by date range, transaction type, category, or description
- Persists transactions to CSV through the repository layer

## Current Architecture

```
src/main/java/com/finance/
├── Main.java
├── model/
│   ├── Category.java
│   ├── Transaction.java
│   └── TransactionType.java
├── repository/
│   └── TransactionRepository.java
├── service/
│   └── FinanceService.java
└── ui/
    └── ConsoleUI.java
```

## Tech Stack

- Java 25
- Maven
- `BigDecimal` for monetary values
- `LocalDateTime` for transaction timestamps
- `UUID` for transaction identity
- `java.nio.file` for CSV persistence

## Working Notes

- The console workflow is documented in [`docs/console-ui.md`](docs/console-ui.md).
- Transaction descriptions reject blank values and newline characters.
- CSV persistence is exposed through the repository and service layers, and the application loads saved transactions at startup before opening the console UI.

## Author

Mustafa ElSayed - [github.com/MustafaElSayed01](https://github.com/MustafaElSayed01)
