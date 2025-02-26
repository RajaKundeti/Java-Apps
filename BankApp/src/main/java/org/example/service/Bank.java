package main.java.org.example.service;
import main.java.org.example.dto.Account;
import main.java.org.example.filehandling.AccountsFileHandler;
import java.util.*;
import java.util.logging.Logger;
import static main.java.org.example.filehandling.AccountsFileHandler.getAccountID;


public class Bank {

    // Map to store account details
    public static Map<Long, Account> accounts = new HashMap<>();

    private static final Logger logger = Logger.getLogger(Bank.class.getName());


    // Method to create account
    public void createAccount(String accountHolderName, double balance)  {

        // Creating account
        Account account = new Account();
        account.setAccountNumber(getAccountID());
        account.setAccountHolderName(accountHolderName);
        account.setBalance(balance);
        accounts.put(account.getAccountNumber(), account);

        // Saving account details to file
        if(AccountsFileHandler.saveData(account)){

            logger.info("Account created: " + account.getAccountNumber() + ", " + account.getAccountHolderName() + ", " + account.getBalance());
            System.out.println("Account created successfully with account number: " + account.getAccountNumber());

        } else {

            logger.info("Account creation failed");
            System.out.println("Account creation failed");

        }
    }

    // Method to login account
    public Account loginAccount(long accountNumber) {

        // Loading data from file
        if(accounts.containsKey(accountNumber)){

            // Returning account details
            logger.info("Account logged in: " + accountNumber);
            return accounts.get(accountNumber);

        }

        return null;

    }

    // Method to view account details
    public void viewAccountDetails(Account account) {

        System.out.println();
        System.out.println("Account Details");
        System.out.println("****************");
        System.out.println("Account Number: " + account.getAccountNumber());
        System.out.println("Account Holder Name: " + account.getAccountHolderName());
        System.out.println("Balance: " + account.getBalance());

    }

    // Method to deposit amount to account
    public boolean deposit(Account account, double amount) {

        // Updating account balance
        account.setBalance(account.getBalance() + amount);

        // Updating account details in the list of accounts
        accounts.put(account.getAccountNumber(), account);

        // Updating account details in the file
        if(AccountsFileHandler.updateData(account)){

            // Updating transaction details in the file
            AccountsFileHandler.updateTransaction(account, "CREDIT", amount);
            logger.info("Deposited " + amount + " to account " + account.getAccountNumber() + ". New balance: " + account.getBalance());
            return true;

        }

        return false;

    }

    // Method to withdraw amount from account
    public boolean withdraw(Account account, double amount) {

        // Checking if account has sufficient balance
        if(account.getBalance() >= amount){

            // Updating account balance
            account.setBalance(account.getBalance() - amount);

            // Updating account details in the list of accounts
            accounts.put(account.getAccountNumber(), account);

            // Updating account details in the file
            if(AccountsFileHandler.updateData(account)){

                // Updating transaction details in the file
                AccountsFileHandler.updateTransaction(account, "DEBIT", amount);
                logger.info("Withdrawn " + amount + " from account " + account.getAccountNumber() + ". New balance: " + account.getBalance());
                return true;

            }

        }

        return false;

    }

    // Method to transfer amount from one account to another
    public boolean transfer(Account currentAccount, long toAccountNumber, double amount){

        // Getting account details of the account to which amount is to be transferred
        Account toAccount = accounts.get(toAccountNumber);

        // Checking if account exists
        if(toAccount != null){

            // Checking if current account has sufficient balance
            if(currentAccount.getBalance() >= amount){

                // Updating account balances
                currentAccount.setBalance(currentAccount.getBalance() - amount);
                toAccount.setBalance(toAccount.getBalance() + amount);

                // Updating account details in the list of accounts
                accounts.put(currentAccount.getAccountNumber(), currentAccount);
                accounts.put(toAccount.getAccountNumber(), toAccount);

                // Updating account details in the file
                AccountsFileHandler.updateData(currentAccount);
                AccountsFileHandler.updateTransaction(currentAccount, "DEBIT", amount);
                AccountsFileHandler.updateData(toAccount);
                AccountsFileHandler.updateTransaction(toAccount, "CREDIT", amount);

                logger.info("Transferred " + amount + " from account " + currentAccount.getAccountNumber() + " to account " + toAccount.getAccountNumber());
                return true;

            }
        }

        return false;

    }

}
