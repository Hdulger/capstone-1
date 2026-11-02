

import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class AccountingLedgerApplication {

    public static void main(String[] args) {
        Scanner scanner= new Scanner(System.in);

        ArrayList<Transaction> transactions=loadTransactions();

        mainMenu(scanner, transactions);

        System.out.println("Thank you for choosing our bank!");
    }

    public static ArrayList<Transaction> loadTransactions()  {
      //  FileReader fileReader=new FileReader("src/main/resources/transactions");
        ArrayList<Transaction> transactions= new ArrayList<>();
        LocalDateTime currentTime=LocalDateTime.now();

        Transaction transaction1= new Transaction(currentTime,"Foods", "Trader Joe`s", 29.99);
        return transactions;
    }

    private static void mainMenu(Scanner scanner, ArrayList<Transaction> transactions){
        String menuPrompt= """
                Home Screen
                D)Add Deposit
                P)Make Payment(Debit)
                L)Ledger
                X)Exit
                What would you like to do today? Please enter your command here:
                """;

        boolean done=false;
        do {
            System.out.println(menuPrompt);
            String input=scanner.nextLine();

            switch (input){
                case  "D","d" -> addDeposit(transactions, scanner);
                case  "P", "p"-> makePayment(transactions, scanner);
                case  "L","l" -> ledgerMenu(scanner, transactions);
                case  "X","x" -> done=true;
                default -> System.out.println("Please enter a valid query");

            }
        }while (!done);
    }

    private static void ledgerMenu(Scanner scanner, ArrayList<Transaction> transactions){
        System.out.println("This is your ledger menu");
        String ledgerMenu= """
                A)All
                D)Deposits
                P)Payments
                R)Reports
                H)Home
                How would you like to continue? Please enter an option?
                """;

        boolean done=false;

        do {
            System.out.println(ledgerMenu);
            String userInput=scanner.nextLine();
            switch (userInput){
                case "A", "a"-> allEntries(transactions);
                case "D", "d"-> deposits(transactions);
                case "P", "p"-> payments(transactions);
                case "R", "r"-> Reports(transactions, scanner);
                case "H", "h"-> mainMenu(scanner, transactions);
                default -> done=true;
            }

        }while (!done);



    }
    private static void addDeposit(ArrayList<Transaction> transactions, Scanner scanner){
        System.out.println("Enter deposit info");
        String depositInfo=scanner.nextLine();

//        Transaction newTransaction=parseTransaction(depositInfo);
//        transactions.add(newTransaction);
    }

    private static void makePayment(ArrayList<Transaction> transactions, Scanner scanner){
        System.out.println("make payment");

    }

    private static void allEntries(ArrayList<Transaction> transactions){

    }

    private static void deposits(ArrayList<Transaction> transactions){

    }
    private static void payments(ArrayList<Transaction> transactions){

    }
    private static void Reports(ArrayList<Transaction> transactions, Scanner scanner){

    }


















}
