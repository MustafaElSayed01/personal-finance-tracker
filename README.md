# Personal Finance Tracker

A console-based personal finance application built in Java.
Records income and expense transactions, categorises them, persists data to a CSV file,
and generates summary reports — all from the terminal.

## Features

- Add income and expense transactions with category, amount, date, and description
- View all recorded transactions
- Filter transactions by date range or category
- Generate a summary report grouped by category with net balance
- Automatic CSV persistence — data survives between sessions

## Tech Stack

- Java 25 LTS
- Maven
- `java.nio.file` for file I/O
- `BigDecimal` for exact monetary arithmetic
- `LocalDate` for transaction dating
- `UUID` for transaction identity

## Project Structure

```
src/main/java/com/finance/
├── Main.java
├── model/
│   ├── Transaction.java
│   ├── TransactionType.java
│   └── Category.java
├── repository/
│   └── TransactionRepository.java
├── service/
│   └── FinanceService.java
├── report/
│   ├── ReportGenerator.java
│   └── SummaryReport.java
└── ui/
    └── ConsoleUI.java
```

## Getting Started

```bash
git clone https://github.com/MustafaElSayed01/personal-finance-tracker.git
cd personal-finance-tracker
mvn compile
mvn exec:java -Dexec.mainClass="com.finance.Main"
```

## Author

Mustafa ElSayed — [github.com/MustafaElSayed01](https://github.com/MustafaElSayed01)