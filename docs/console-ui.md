# Console UI Guide

This document describes the current console workflow exposed by `ConsoleUI`.

## Main Menu

1. Add a new transaction
2. View all transactions
3. Filter transactions
4. Save to CSV
0. Quit

## Adding A Transaction

When you choose the add flow, the console asks for:

1. Transaction type
2. Category
3. Amount
4. Date/time in `yyyy-MM-dd HH:mm` format
5. Description

Validation notes:

- Amounts must be positive decimal values.
- Descriptions cannot be blank.
- Descriptions cannot contain newline characters.
- If date/time is left blank, the console uses the current timestamp.

## Filtering

The filter submenu supports:

1. Date range
2. Transaction type
3. Transaction category
4. Description search
0. Return to the main menu

## CSV Persistence

The console can save the current in-memory transactions to CSV through the repository layer.
The file name is provided by the repository when it is constructed.

## Implementation Notes

- `ConsoleUI` owns all terminal interaction and delegates business rules to `FinanceService`.
- `FinanceService` exposes transaction queries and forwards persistence calls to `TransactionRepository`.
- `TransactionRepository` is responsible for the in-memory list and CSV read/write operations.
