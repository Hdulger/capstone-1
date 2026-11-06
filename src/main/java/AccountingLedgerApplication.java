import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;
public class AccountingLedgerApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<Transaction> transactions = loadTransactions();

        mainMenu(scanner, transactions);

        System.out.print("Thank you for choosing our bank!");
    }
    public static ArrayList<Transaction> loadTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        String fileName = "src/main/resources/transactions.csv";

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();//throw away header
            line = bufferedReader.readLine();
            while (line != null) {
                if (!line.isBlank()) {
                    Transaction transaction = parseTransaction(line);
                    transactions.add(transaction);
                }
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException ex) {
            System.err.println("There is an in and out problem " + ex.getMessage());
        }
        return transactions;
    }
    private static void mainMenu(Scanner scanner, ArrayList<Transaction> transactions) {
        String menuPrompt = """
                Home Screen
                D)Add Deposit
                P)Make Payment(Debit)
                L)Ledger
                X)Exit
                What would you like to do today? Please enter your command here:
                """;

        boolean done = false;
        do {
            System.out.print(menuPrompt);
            String input = scanner.nextLine();

            switch (input) {
                case "D", "d" -> addDeposit(transactions, scanner);
                case "P", "p" -> makePayment(transactions, scanner);
                case "L", "l" -> ledgerMenu(scanner, transactions);
                case "X", "x" -> done = true;
                default -> System.out.println("Please enter a valid query");

            }
        } while (!done);
    }
    private static void ledgerMenu(Scanner scanner, ArrayList<Transaction> transactions) {
        System.out.print("This is your ledger menu");
        String ledgerMenu = """
                
                A)All
                D)Deposits
                P)Payments
                R)Reports
                H)Home
                How would you like to continue? Please enter an option?
                """;
        boolean done = false;
        do {
            System.out.print(ledgerMenu);
            String userInput = scanner.nextLine();
            switch (userInput) {
                case "A", "a" -> allEntries(transactions);
                case "D", "d" -> displayDeposits(transactions);
                case "P", "p" -> displayPayment(transactions);
                case "R", "r" -> reports(scanner, transactions);
                case "H", "h" -> mainMenu(scanner, transactions);
                default -> done = true;
            }

        } while (!done);
    }
    private static void reports(Scanner scanner, ArrayList<Transaction> transactions) {
        System.out.print("This is your Reports menu");
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
            System.out.print(ledgerMenu);
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
        int lastYear = LocalDate.now().getYear() - 1;
        for (Transaction t : transactions) {
            if (t.getDateTime().getYear() == lastYear) {
                t.display();
            }
        }
    }
    private static void yearToDate(ArrayList<Transaction> transactions) {
        int thisYear = LocalDate.now().getYear();//gets the current year
        int count = 0;
        for (Transaction t : transactions) {
            if (t.getDateTime().getYear()==thisYear) {//whether the input date equals to this year
                t.display();
                count++;
            }
        }
        System.out.print("There are " + count + " transactions from last year to date");
    }
    private static void previousMonth(ArrayList<Transaction> transactions) {
        YearMonth lastMonth = YearMonth.now().minusMonths(1);//finds last month 2026-11
        for (Transaction tr : transactions) {
            YearMonth month = YearMonth.from(tr.getDateTime());//returns the given month
            if (month.equals(lastMonth)) {
                tr.display();
            }
        }
    }
    private static void monthToDate(ArrayList<Transaction> transactions) {
        int thisMonth=LocalDate.now().getMonthValue();
        for (Transaction tr : transactions) {
            if (tr.getDateTime().getMonthValue()==thisMonth) {
                tr.display();
            }
        }
    }
    private static void addDeposit(ArrayList<Transaction> transactions, Scanner scanner) {
        System.out.print("How much would you like to deposit today? Please type like Invoice 1001 paid|Joe|1500.00");
        String depositInput = scanner.nextLine();
        appendTransactions(transactions, depositInput);
    }
    private static void appendTransactions(ArrayList < Transaction > transactions, String depositInput){
        LocalDateTime now = LocalDateTime.now();
        String depositInfoCsv = now.toLocalDate() + "|" + now.toLocalTime().withNano(0) + "|" + depositInput + "\n";
        Transaction newTransaction = parseTransaction(depositInfoCsv);
        transactions.add(newTransaction);

        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/transactions.csv", true);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(depositInfoCsv);
            bufferedWriter.close();

        } catch (IOException e) {
            System.err.println("There is a problem  here" + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException ae) {
            System.err.println("There is an array problem");
        }
    }
    private static Transaction parseTransaction(String csvString) {
        //Split up a string like 2023-04-15|10:13:25|ergonomic keyboard|Amazon|-89.50
        String[] parts = csvString.split(Pattern.quote("|"));
        //Convert the first split part into the date
        LocalDate date = LocalDate.parse(parts[0]);
        LocalTime time = LocalTime.parse(parts[1]);
        LocalDateTime dateTime = date.atTime(time);
        String description = parts[2];
        String vendor = parts[3];
        double price = Double.parseDouble(parts[4]);

        return new Transaction(dateTime, description, vendor, price);
    }
    private static void makePayment(ArrayList<Transaction> transactions, Scanner scanner) {
        System.out.print("Enter payment info like ergonomic keyboard|Amazon|-89.50");
        String paymentInfoInput = scanner.nextLine();
        appendTransactions(transactions, paymentInfoInput);
    }
    private static void allEntries(ArrayList<Transaction> transactions) {
        for (Transaction t : transactions) {
            t.display();
        }
    }
    private static void displayDeposits(ArrayList<Transaction> transactions) {
        for (Transaction tr : transactions) {
            if (tr.getAmount() > 0 || tr.getAmount() == 0) {
                tr.display();
            }
        }
    }
    private static void displayPayment(ArrayList<Transaction> transactions) {
        for (Transaction tr : transactions) {
            if (tr.getAmount() < 0) {
                tr.display();
            }
        }
    }
    private static void searchByVendor(ArrayList<Transaction> transactions, Scanner scanner) {
        System.out.print("Please enter the vendor: ");
        String input = scanner.nextLine();
        for (Transaction t : transactions) {
            if (input.equalsIgnoreCase(t.getVendor())) {
                t.display();
            }
        }
    }
    private static void customSearch(ArrayList<Transaction> transactions, Scanner scanner) {
        System.out.println("“Enter search criteria. Press Enter to skip any field.”");
        System.out.print("Start Date (yyyy//MM/dd): ");
        String startDate = scanner.nextLine().trim();

        System.out.print("End Date (yyyy//MM/dd): ");
        String endDate = scanner.nextLine().trim();

        System.out.print("Description: ");
        String description = scanner.nextLine().trim();

        System.out.print("Vendor: ");
        String vendor = scanner.nextLine().trim();

        System.out.print("Amount:");
        String amountText = scanner.nextLine();

        LocalDate startD=null;
        LocalDate endD=null;
        if(!startDate.isEmpty()) {
            startD = LocalDate.parse(startDate);
        }
        if(!endDate.isEmpty()) {
            endD = LocalDate.parse(endDate);
        }
        double amount = 0.0;
        if (!amountText.isEmpty()) {
            amount = Double.parseDouble(amountText);
        }

        boolean found=false;//This is to print if there is no transactions
        for (Transaction t:transactions){

            if(startD!=null && !t.getDateTime().toLocalDate().isAfter(startD)){
               continue;
            }
            if(endD!=null && !t.getDateTime().toLocalDate().isBefore(endD)){
                continue;
            }
            if (!description.isBlank() && !t.getDescription().equalsIgnoreCase(description)) {
                continue;
            }
            if (!vendor.isBlank() && !t.getVendor().equalsIgnoreCase(vendor)) {
                continue;
            }
            if (!amountText.isBlank()&&t.getAmount() != amount) {
                continue;
            }
            t.display();
            found=true;
        }
        if (!found){
            System.out.println("There is no matching transaction");
        }
    }
}
