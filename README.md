**Accounting Ledger Application**
-
Account Ledger application for recording deposits and payments, viewing
transactions, and running reports for a coffee shop. Transactions are stored in transactions.csv file.

**Home Screen**
- D)Add Deposit -> Saves a new deposit as a positive amount in `transactions.csv`.
- P)Make Payment(Debit) -> Saves a new payment as a negative amount in `transactions.csv`.
- L)Ledger
- X)Exit


**L)Ledger Menu**
- A)All  -> Displays all transactions.
- D)Deposits -> Displays only deposits (positive amounts).
- P)Payments -> Displays only payments (negative amounts).
- R)Reports 
- H)Home -> Returns to the Home Screen.

**R)Reports**


1) Month To Date -> Displays transactions from the start of this month through today.
2) Previous Month -> Displays transactions from the previous calendar month.
3) Year To Date -> Displays transactions from the start of this year through today.
4) Previous Year -> Displays transactions from the previous calendar year.
5) Search by Vendor  -> Displays transactions that match the vendor name you enter.
6) Custom Search **(Bonus)** -> Filters transactions by date, description, vendor, or amount. Press Enter to skip a filter.
Enter "0" to go back to the Ledger menu.



Each line in `transactions.csv` contains the date, time, description, vendor,
and amount, separated by `|`. Positive amounts are deposits and negative
amounts are payments. Press Enter to skip any filter you do not want to use.

For Add Deposit, only enter the description, vendor, and amount, as shown in the example.
```text
Morning sales|Coffee Shop|125.50
output in transactions csv field:
2026-11-05|09:30:00|Morning sales|Coffee Shop|125.50


```
For make payment, only enter the description, vendor, and amount, as shown in the example.

```text
2026-11-05|11:15:00|Coffee beans|Bean Supplier|-45.00
```