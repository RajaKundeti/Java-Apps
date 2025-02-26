package main.java.org.example.filehandling;

import main.java.org.example.dto.Account;
import main.java.org.example.service.Bank;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class AccountsFileHandler {

    private static final Logger logger = Logger.getLogger(AccountsFileHandler.class.getName());


    // Method to load account details from file
    public static void loadData() {

        BufferedReader reader;
        try {

            // Reading account details from file
            reader = new BufferedReader(new FileReader("C:\\Users\\RajaNarasimhanKundet\\OneDrive - Atyeti Inc\\Desktop\\Projects\\Java\\BankApp\\src\\main\\resources\\accounts.txt"));
            String line;

            // Storing account details in the list of accounts
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Account account = new Account();
                account.setAccountNumber(Long.parseLong(data[0]));
                account.setAccountHolderName(data[1]);
                account.setBalance(Double.parseDouble(data[2]));
                Bank.accounts.put(account.getAccountNumber(), account);

            }

            reader.close();

        } catch (IOException e) {

            logger.info("No accounts found");

        }
    }

    // Method to save account details to file
    public static boolean saveData(Account account) {

        BufferedWriter writer;
        try {

            // Writing account details to file
            writer = new BufferedWriter(new FileWriter("C:\\Users\\RajaNarasimhanKundet\\OneDrive - Atyeti Inc\\Desktop\\Projects\\Java\\BankApp\\src\\main\\resources\\accounts.txt",true));
            writer.write(account.getAccountNumber() + ",");
            writer.write(account.getAccountHolderName() + ",");
            writer.write(account.getBalance() + "\n");
            logger.info("Account created: " + account.getAccountNumber() + ", " + account.getAccountHolderName() + ", " + account.getBalance());
            writer.close();

        } catch (IOException e) {

            logger.info("Account creation failed");
            return false;

        }

        return true;
    }

    // Method to update account details in the file
    public static boolean updateData(Account account)  {

        // Reading account details from file
        boolean updated = false;
        Path path = Paths.get("C:\\Users\\RajaNarasimhanKundet\\OneDrive - Atyeti Inc\\Desktop\\Projects\\Java\\BankApp\\src\\main\\resources\\accounts.txt");
        List<String> lines;

        try {

            lines = Files.readAllLines(path);
            Map<String, String> accountMap = new HashMap<>();
            logger.info("Searching for account: " + account.getAccountNumber());

            for (String line : lines) {

                String[] parts = line.split(",");
                accountMap.put(parts[0], line);

            }

            // Updating account details
            if (accountMap.containsKey(String.valueOf(account.getAccountNumber()))) {

                logger.info("Account found: " + account.getAccountNumber());
                String updatedLine = account.getAccountNumber() + "," + account.getAccountHolderName() + "," + account.getBalance();
                accountMap.put(String.valueOf(account.getAccountNumber()), updatedLine);
                logger.info("Account updated: " + account.getAccountNumber());
                updated = true;

            }

            // Writing updated account details to file
            if (updated) {

                logger.info("Writing updated account data to file");
                List<String> updatedLines = new ArrayList<>(accountMap.values());

                try {

                    Files.write(path, updatedLines);

                } catch (IOException e) {

                    logger.info("Account data update failed");

                }

                logger.info("Account data updated successfully");

            }
        } catch (IOException e) {

            logger.info("Account data not found");

        }

        return updated;

    }

    // Method to update transaction details in the file
    public static void updateTransaction(Account account, String transactionType, double amount) {

        BufferedWriter writer;
        try {

            // Writing transaction details to file
            writer = new BufferedWriter(new FileWriter("C:\\Users\\RajaNarasimhanKundet\\OneDrive - Atyeti Inc\\Desktop\\Projects\\Java\\BankApp\\src\\main\\resources\\transactions.txt",true));
            writer.write(getTransactionID() + ",");
            writer.write(account.getAccountNumber() + ",");
            writer.write(transactionType + ",");
            writer.write(amount + "\n");
            logger.info("Transaction recorded: " + transactionType + " " + amount + " for account " + account.getAccountNumber());
            writer.close();

        } catch (IOException e) {

            logger.info("Transaction recording failed");

        }
    }

    // Method to view transaction history from the file
    public static void viewTransactionHistory(Account account) {

        BufferedReader reader;
        try {

            // Reading transaction details from file
            reader = new BufferedReader(new FileReader("C:\\Users\\RajaNarasimhanKundet\\OneDrive - Atyeti Inc\\Desktop\\Projects\\Java\\BankApp\\src\\main\\resources\\transactions.txt"));
            String line;

            // Displaying transaction history
            System.out.println();
            System.out.println("Transaction History");
            System.out.println("*******************");

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");

                if (Long.parseLong(data[1]) == account.getAccountNumber()) {

                    System.out.println("Transaction ID: " + data[0]);
                    System.out.println("Transaction Type: " + data[2]);
                    System.out.println("Amount: " + data[3]);
                    System.out.println();

                }

            }

            reader.close();

        } catch (IOException e) {

            logger.info("No transactions found");

        }
    }

    // Method to get transaction ID
    private static long getTransactionID() {

        // Reading & Assigning New transaction ID from file
        long transactionId = 0;

        try {

            BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\RajaNarasimhanKundet\\OneDrive - Atyeti Inc\\Desktop\\Projects\\Java\\BankApp\\src\\main\\resources\\transactionId.txt"));
            String line = reader.readLine();
            transactionId = Long.parseLong(line);
            transactionId++;
            reader.close();
            BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\RajaNarasimhanKundet\\OneDrive - Atyeti Inc\\Desktop\\Projects\\Java\\BankApp\\src\\main\\resources\\transactionId.txt"));
            writer.write(String.valueOf(transactionId));
            writer.close();

        } catch (FileNotFoundException e) {

            logger.info("File not found");

        } catch (IOException e) {

            logger.info("Transaction ID not found");

        }

        return transactionId;

    }

    // Method to get account ID
    public static long getAccountID()  {


        // Reading & Assigning New account ID from file
        long accountNumber = 0;
        BufferedReader reader;

        try {

            reader = new BufferedReader(new FileReader("C:\\Users\\RajaNarasimhanKundet\\OneDrive - Atyeti Inc\\Desktop\\Projects\\Java\\BankApp\\src\\main\\resources\\accountNumber.txt"));
            String line = reader.readLine();
            accountNumber = Long.parseLong(line);
            accountNumber++;
            reader.close();
            BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\RajaNarasimhanKundet\\OneDrive - Atyeti Inc\\Desktop\\Projects\\Java\\BankApp\\src\\main\\resources\\accountNumber.txt"));
            writer.write(String.valueOf(accountNumber));
            writer.close();
            return accountNumber;

        } catch (FileNotFoundException e) {

            logger.info("File not found");

        }
        catch (IOException e) {

            logger.info("Account ID not found");

        }

        return accountNumber;

    }

}
