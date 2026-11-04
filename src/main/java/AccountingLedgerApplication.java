import java.io.*;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class AccountingLedgerApplication {
    public static void main(String[] args) {
        Scanner scanner= new Scanner(System.in);

        ArrayList<Transaction> transactions=loadTransactions();

        mainMenu(scanner, transactions);

        System.out.println("Thank you for choosing our bank!");
    }

    public static ArrayList<Transaction> loadTransactions() {
        //  FileReader fileReader=new FileReader("src/main/resources/transactions.csv");
       ArrayList<Transaction> transactions= new ArrayList<>();
        LocalDateTime currentTime=LocalDateTime.now();


        String fileName = "src/main/resources/transactions.csv";

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();//throw away header
            line = bufferedReader.readLine();
            while (line != null) {
                if(!line.isBlank()){
                Transaction transaction = parseTransaction(line);
                transactions.add(transaction);
                }
                    line = bufferedReader.readLine();


            }
            bufferedReader.close();
            fileReader.close();

        }catch (FileNotFoundException e){
            System.err.println(e.getMessage());
        }catch (IOException ex){
            System.err.println("There is an in and out problem "+ex.getMessage());
        }

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
                case "R", "r"-> reports(scanner, transactions);
                case "H", "h"-> mainMenu(scanner, transactions);
                default -> done=true;
            }

        }while (!done);

        }


    private static void reports(Scanner scanner, ArrayList<Transaction> transactions) {
        System.out.println("This is your Reports menu");
        String ledgerMenu = """
                    1) Month To Date
                    2) Previous Month
                    3) Year To Date
                    4) Previous Year
                    5) Search by Vendor
                    6)Custom Search
                    0)Back
                How would you like to continue? Please enter an option?
                """;

        boolean done = false;

        do {
            System.out.println(ledgerMenu);
            String userInput = scanner.nextLine();
            switch (userInput) {
                case "1" -> monthToDate(transactions);
                case "2" -> previousMonth(transactions);
                case "3" -> yearToDate(transactions);
                case "4" -> previousYear(transactions);
                case "5" -> searchByVendor(transactions, scanner);
                case "6" -> customSearch(transactions, scanner);
                case "0" -> done = true;
                default -> mainMenu(scanner, transactions);
            }

        } while (!done);
    }

    private static void previousYear(ArrayList<Transaction> transactions) {
        int lastYear=LocalDate.now().getYear()-1;
        for(Transaction t:transactions){
            if(t.getDateTime().getYear()==lastYear){
                t.display();
            }
        }
    }

    private static void yearToDate(ArrayList<Transaction> transactions) {
        //LocalDate month=yearToDate();
    }

    private static void previousMonth(ArrayList<Transaction> transactions) {
        YearMonth month=YearMonth.now().minusMonths(1);//finds last month 2026-11
        for (Transaction tr:transactions){
            YearMonth lastMonth=YearMonth.from(tr.getDateTime());//returns the given month
            if (lastMonth.equals(month)){
                tr.display();
            }
        }
    }

    private static void monthToDate(ArrayList<Transaction> transactions) {

//        for (Transaction tr:transactions){
//           int month= tr.getDateTime().getMonthValue()-1;
//           int now=LocalDate.now().getMonthValue();
//
//        }

    }


    private static void addDeposit(ArrayList<Transaction> transactions, Scanner scanner)  {

        System.out.println("How much would you like to deposit today? Please type like Invoice 1001 paid|Joe|1500.00");
        String depositInput=scanner.nextLine();
        LocalDateTime now=LocalDateTime.now();
        String depositInfoCsv=now.toLocalDate()+"|"+now.toLocalTime().withNano(0)+"|"+depositInput+"\n";
            try {
                FileWriter fileWriter = new FileWriter("src/main/resources/transactions.csv", true);

                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(depositInfoCsv);


                Transaction newTransaction=parseTransaction(depositInfoCsv);
                transactions.add(newTransaction);

                bufferedWriter.close();
                //handle the array exception occurred !!!!!
            }catch (IOException e){
                System.err.println("There is a problem  here"+e.getMessage());
            }

    }

    private static Transaction parseTransaction(String csvString) {
        //Split up a string like 2023-04-15|10:13:25|ergonomic keyboard|Amazon|-89.50
        String[] parts=csvString.split(Pattern.quote("|"));
        //Convert the first split part into the date
        LocalDate date=LocalDate.parse(parts[0]);
        LocalTime time=LocalTime.parse(parts[1]);
        LocalDateTime dateTime=date.atTime(time);

        String description =parts[2];
        String vendor=parts[3];
        double price=Double.parseDouble(parts[4]);

        return new Transaction(dateTime, description, vendor, price);
    }


    private static void makePayment(ArrayList<Transaction> transactions, Scanner scanner){
        System.out.println("Enter payment info like ergonomic keyboard|Amazon|-89.50");
        String paymentInfoInput =scanner.nextLine();
        LocalDateTime now=LocalDateTime.now();
       // String[] parts=paymentInfoInput.split(Pattern.quote("|"));
        String paymentInfoCsv=now.toLocalDate()+"|"+now.toLocalTime().withNano(0)+"|"+paymentInfoInput+"\n";

        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/transactions.csv", true);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(paymentInfoCsv);

            Transaction newTransaction=parseTransaction(paymentInfoCsv);
            transactions.add(newTransaction);

            bufferedWriter.close();
        }catch (IOException e){
            System.err.println("There is a problem  here"+e.getMessage());
        }
    }

    private static void allEntries(ArrayList<Transaction> transactions){
        for (Transaction t:transactions){
            t.display();
        }


    }

    private static void deposits(ArrayList<Transaction> transactions){
        for (Transaction tr:transactions){
            if(tr.getAmount()>0 || tr.getAmount()==0){
                tr.display();
            }

        }



    }
    private static void payments(ArrayList<Transaction> transactions){
        for(Transaction tr:transactions){
            if (tr.getAmount()<0){
                tr.display();

            }
        }

    }




    private static void searchByVendor(ArrayList<Transaction> transactions, Scanner scanner){
        System.out.println("Please enter the vendor: ");
        String input=scanner.nextLine();
        for (Transaction t:transactions) {
            if (input.equalsIgnoreCase(t.getVendor())){
                t.display();

            }
        }

    }

    private static void customSearch(ArrayList<Transaction> transactions, Scanner scanner){

    }

















}
