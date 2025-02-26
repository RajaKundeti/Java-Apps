package main.java.org.example;
import main.java.org.example.dto.Account;
import main.java.org.example.filehandling.AccountsFileHandler;
import main.java.org.example.service.Bank;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {

        // showing menu
        showMenu();

    }

    private static void showMenu() {

        Bank bank = new Bank();
        Scanner scanner = new Scanner(System.in);

        // Menu Options
        System.out.println();
        System.out.println("Welcome to Bank");
        System.out.println("*******************");
        System.out.println("1. Create Account");
        System.out.println("2. Login Account");
        System.out.print("Enter your choice: ");

        // User input for menu options
        try{
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:

                    System.out.print("Enter you name: ");
                    String name;

                    try {
                        name = scanner.next();
                        System.out.println("Invalid name");
                        System.out.print("Enter initial balance: ");
                        double balance = scanner.nextDouble();
                        // Creating account
                        bank.createAccount(name, balance);

                    }catch (InputMismatchException e){

                        System.out.println("Invalid name");

                    }
                    break;

                case 2:

                    System.out.print("Enter account number: ");
                    long accountNumber = scanner.nextLong();

                    // Loading data from file
                    AccountsFileHandler.loadData();

                    // Logging in account
                    Account account = bank.loginAccount(accountNumber);
                    if(account != null) {

                        showAccountMenu(bank, account);

                    } else {

                        System.out.println("Invalid account number");

                    }
                    break;

                default:
                    System.out.println("Invalid Option");

            }

        }catch (InputMismatchException e){

            System.out.println("Please enter a valid input");

        }
    }

    private static void showAccountMenu(Bank bank, Account account) {

        while (true) {

            // Account Menu Options
            System.out.println();
            System.out.println("********************************");
            System.out.println("Welcome " + account.getAccountHolderName());
            System.out.println();
            System.out.println("1. View Account Details");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Transfer Money");
            System.out.println("5. View Transaction History");
            System.out.println("6. Exit");
            System.out.println("********************************");
            System.out.println();

            try{

                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                switch (choice) {

                    // Viewing account details
                    case 1:

                        bank.viewAccountDetails(account);
                        break;

                    // Depositing amount
                    case 2:

                        System.out.print("Enter amount to deposit: ");
                        double amount1 = scanner.nextDouble();

                        if(amount1 <=0 ){
                            throw new InputMismatchException();
                        }
                        if(bank.deposit(account, amount1)){
                            System.out.println("Amount deposited successfully");
                        } else {
                            System.out.println("Amount deposit failed");
                        }
                        break;

                    // Withdrawing amount
                    case 3:

                        System.out.print("Enter amount to withdraw: ");
                        double amount2 = scanner.nextDouble();

                        if(amount2 <=0 ){
                            throw new InputMismatchException();
                        }
                        if(bank.withdraw(account, amount2)){
                            System.out.println("Amount withdrawn successfully");
                        } else {
                            System.out.println("Amount withdrawal failed");
                        }
                        break;

                    // Transferring amount
                    case 4:

                        System.out.print("Enter account number to transfer: ");
                        long toAccountNumber = scanner.nextLong();
                        System.out.print("Enter amount to transfer: ");
                        double amount3 = scanner.nextDouble();

                        if(amount3 <=0 ){
                            throw new InputMismatchException();
                        }
                        if(bank.transfer(account, toAccountNumber, amount3)){
                            System.out.println("Amount transferred successfully");
                        } else {
                            System.out.println("Amount transfer failed");
                        }
                        break;

                    // Viewing transaction history
                    case 5:

                        AccountsFileHandler.viewTransactionHistory(account);
                        break;

                    // Exiting
                    case 6:

                        return;

                    // Invalid option
                    default:
                        System.out.println("Invalid Option");

                }

            }catch (InputMismatchException e) {

                System.out.println("Please enter a valid input");

            }
        }
    }

}