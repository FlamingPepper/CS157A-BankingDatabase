import DAO.*;
import Model.*;
import Service.BankingService;
import Util.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static BankingService bankingService;
    private static Scanner scanner;
    private static DateTimeFormatter dateFormatter;
    
    public static void main(String[] args) {
        // Test database connection
        try {
            Connection conn = DBConnection.getConnection();
            if (conn != null) {
                System.out.println("Database connection successful!");
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);  // Exit if database connection fails
        }
        
        // Initialize services
        bankingService = new BankingService();
        scanner = new Scanner(System.in);
        dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        try {
            boolean running = true;
            while (running) {
                displayMainMenu();
                int choice = getIntInput("Enter your choice: ");
                
                switch (choice) {
                    case 1:
                        customerMenu();
                        break;
                    case 2:
                        accountMenu();
                        break;
                    case 3:
                        transactionMenu();
                        break;
                    case 4:
                        loanMenu();
                        break;
                    case 5:
                        runDemoOperations();
                        break;
                    case 0:
                        running = false;
                        System.out.println("Exiting the application...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
            // Clean up resources
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Closing database connections...");
                Util.DBConnection.closeAllConnections();
            }));
        }
    }
    
    private static void displayMainMenu() {
        System.out.println("\n===== BANKING APPLICATION MENU =====");
        System.out.println("1. Customer Operations");
        System.out.println("2. Account Operations");
        System.out.println("3. Transaction Operations");
        System.out.println("4. Loan Operations");
        System.out.println("5. Run Demo Operations");
        System.out.println("0. Exit");
        System.out.println("===================================");
    }
    
    private static void customerMenu() {
        boolean customerMenuRunning = true;
        
        while (customerMenuRunning) {
            System.out.println("\n===== CUSTOMER OPERATIONS =====");
            System.out.println("1. Create New Customer");
            System.out.println("2. View Customer Details");
            System.out.println("3. View All Customers");
            System.out.println("0. Back to Main Menu");
            System.out.println("==============================");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    createCustomer();
                    break;
                case 2:
                    viewCustomerDetails();
                    break;
                case 3:
                    viewAllCustomers();
                    break;
                case 0:
                    customerMenuRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void accountMenu() {
        boolean accountMenuRunning = true;
        
        while (accountMenuRunning) {
            System.out.println("\n===== ACCOUNT OPERATIONS =====");
            System.out.println("1. Create New Account");
            System.out.println("2. View Account Details");
            System.out.println("3. View Customer Accounts");
            System.out.println("0. Back to Main Menu");
            System.out.println("============================");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    viewAccountDetails();
                    break;
                case 3:
                    viewCustomerAccounts();
                    break;
                case 0:
                    accountMenuRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void transactionMenu() {
        boolean transactionMenuRunning = true;
        
        while (transactionMenuRunning) {
            System.out.println("\n===== TRANSACTION OPERATIONS =====");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Transfer");
            System.out.println("4. View Transaction History");
            System.out.println("0. Back to Main Menu");
            System.out.println("=================================");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    makeDeposit();
                    break;
                case 2:
                    makeWithdrawal();
                    break;
                case 3:
                    makeTransfer();
                    break;
                case 4:
                    viewTransactionHistory();
                    break;
                case 0:
                    transactionMenuRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void loanMenu() {
        boolean loanMenuRunning = true;
        
        while (loanMenuRunning) {
            System.out.println("\n===== LOAN OPERATIONS =====");
            System.out.println("1. Apply for Loan");
            System.out.println("2. View Customer Loans");
            System.out.println("0. Back to Main Menu");
            System.out.println("==========================");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    applyForLoan();
                    break;
                case 2:
                    viewCustomerLoans();
                    break;
                case 0:
                    loanMenuRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    // Customer Operations
    private static void createCustomer() {
        System.out.println("\n----- Create New Customer -----");
        String name = getStringInput("Enter customer name: ");
        String email = getStringInput("Enter customer email: ");
        String phone = getStringInput("Enter customer phone: ");
        String address = getStringInput("Enter customer address: ");
        
        try {
            Customer customer = bankingService.createCustomer(name, email, phone, address);
            System.out.println("Customer created successfully with ID: " + customer.getCustomer_id());
        } catch (Exception e) {
            System.out.println("Error creating customer: " + e.getMessage());
        }
    }
    
    private static void viewCustomerDetails() {
        System.out.println("\n----- View Customer Details -----");
        int customerId = getIntInput("Enter customer ID: ");
        
        try {
            Customer customer = bankingService.getCustomerDetails(customerId);
            if (customer != null) {
                System.out.println("Customer ID: " + customer.getCustomer_id());
                System.out.println("Name: " + customer.getName());
                System.out.println("Email: " + customer.getEmail());
                System.out.println("Phone: " + customer.getPhone());
                System.out.println("Address: " + customer.getAddress());
            } else {
                System.out.println("Customer not found!");
            }
        } catch (Exception e) {
            System.out.println("Error retrieving customer details: " + e.getMessage());
        }
    }
    
    private static void viewAllCustomers() {
        System.out.println("\n----- All Customers -----");
        try {
            CustomerDAO customerDAO = new CustomerDAO();
            List<Customer> customers = customerDAO.getAllCustomers();
            
            if (customers.isEmpty()) {
                System.out.println("No customers found.");
            } else {
                System.out.println("ID\tName\t\tEmail\t\tPhone");
                System.out.println("--------------------------------------------------");
                for (Customer customer : customers) {
                    System.out.printf("%d\t%s\t%s\t%s\n", 
                        customer.getCustomer_id(), 
                        customer.getName(), 
                        customer.getEmail(), 
                        customer.getPhone());
                }
            }
        } catch (Exception e) {
            System.out.println("Error retrieving customers: " + e.getMessage());
        }
    }
    
    // Account Operations
    private static void createAccount() {
        System.out.println("\n----- Create New Account -----");
        int customerId = getIntInput("Enter customer ID: ");
        
        // Verify customer exists
        Customer customer = bankingService.getCustomerDetails(customerId);
        if (customer == null) {
            System.out.println("Customer not found!");
            return;
        }
        
        System.out.println("Account types: checking, savings");
        String accountType = getStringInput("Enter account type: ");
        double initialDeposit = getDoubleInput("Enter initial deposit amount: ");
        
        try {
            Account account = bankingService.createAccount(customerId, accountType, initialDeposit);
            System.out.println("Account created successfully with ID: " + account.getAccount_id());
            System.out.println("Initial balance: $" + account.getBalance());
        } catch (Exception e) {
            System.out.println("Error creating account: " + e.getMessage());
        }
    }
    
    private static void viewAccountDetails() {
        System.out.println("\n----- View Account Details -----");
        int accountId = getIntInput("Enter account ID: ");
        
        try {
            Account account = bankingService.getAccountDetails(accountId);
            if (account != null) {
                System.out.println("Account ID: " + account.getAccount_id());
                System.out.println("Customer ID: " + account.getCustomer_id());
                System.out.println("Account Type: " + account.getAccount_type());
                System.out.println("Balance: $" + account.getBalance());
                System.out.println("Open Date: " + account.getOpen_date());
            } else {
                System.out.println("Account not found!");
            }
        } catch (Exception e) {
            System.out.println("Error retrieving account details: " + e.getMessage());
        }
    }
    
    private static void viewCustomerAccounts() {
        System.out.println("\n----- View Customer Accounts -----");
        int customerId = getIntInput("Enter customer ID: ");
        
        try {
            Customer customer = bankingService.getCustomerDetails(customerId);
            if (customer == null) {
                System.out.println("Customer not found!");
                return;
            }
            
            List<Account> accounts = bankingService.getCustomerAccounts(customerId);
            
            if (accounts.isEmpty()) {
                System.out.println("No accounts found for customer: " + customer.getName());
            } else {
                System.out.println("Accounts for customer: " + customer.getName());
                System.out.println("ID\tType\t\tBalance\t\tOpen Date");
                System.out.println("--------------------------------------------------");
                for (Account account : accounts) {
                    System.out.printf("%d\t%s\t\t$%.2f\t\t%s\n", 
                        account.getAccount_id(), 
                        account.getAccount_type(), 
                        account.getBalance(), 
                        account.getOpen_date());
                }
            }
        } catch (Exception e) {
            System.out.println("Error retrieving customer accounts: " + e.getMessage());
        }
    }
    
    // Transaction Operations
    private static void makeDeposit() {
        System.out.println("\n----- Make Deposit -----");
        int accountId = getIntInput("Enter account ID: ");
        double amount = getDoubleInput("Enter deposit amount: ");
        
        try {
            boolean result = bankingService.deposit(accountId, amount);
            if (result) {
                System.out.println("Deposit successful!");
                Account account = bankingService.getAccountDetails(accountId);
                System.out.println("New balance: $" + account.getBalance());
            } else {
                System.out.println("Deposit failed. Please check the account ID and amount.");
            }
        } catch (Exception e) {
            System.out.println("Error making deposit: " + e.getMessage());
        }
    }
    
    private static void makeWithdrawal() {
        System.out.println("\n----- Make Withdrawal -----");
        int accountId = getIntInput("Enter account ID: ");
        double amount = getDoubleInput("Enter withdrawal amount: ");
        
        try {
            boolean result = bankingService.withdraw(accountId, amount);
            if (result) {
                System.out.println("Withdrawal successful!");
                Account account = bankingService.getAccountDetails(accountId);
                System.out.println("New balance: $" + account.getBalance());
            } else {
                System.out.println("Withdrawal failed. Please check the account ID, amount, and ensure sufficient balance.");
            }
        } catch (Exception e) {
            System.out.println("Error making withdrawal: " + e.getMessage());
        }
    }
    
    private static void makeTransfer() {
        System.out.println("\n----- Make Transfer -----");
        int fromAccountId = getIntInput("Enter source account ID: ");
        int toAccountId = getIntInput("Enter destination account ID: ");
        double amount = getDoubleInput("Enter transfer amount: ");
        
        try {
            boolean result = bankingService.transfer(fromAccountId, toAccountId, amount);
            if (result) {
                System.out.println("Transfer successful!");
                Account fromAccount = bankingService.getAccountDetails(fromAccountId);
                Account toAccount = bankingService.getAccountDetails(toAccountId);
                System.out.println("Source account new balance: $" + fromAccount.getBalance());
                System.out.println("Destination account new balance: $" + toAccount.getBalance());
            } else {
                System.out.println("Transfer failed. Please check account IDs, amount, and ensure sufficient balance.");
            }
        } catch (Exception e) {
            System.out.println("Error making transfer: " + e.getMessage());
        }
    }
    
    private static void viewTransactionHistory() {
        System.out.println("\n----- View Transaction History -----");
        int accountId = getIntInput("Enter account ID: ");
        
        try {
            Account account = bankingService.getAccountDetails(accountId);
            if (account == null) {
                System.out.println("Account not found!");
                return;
            }
            
            List<Transaction> transactions = bankingService.getAccountTransactions(accountId);
            
            if (transactions.isEmpty()) {
                System.out.println("No transactions found for account ID: " + accountId);
            } else {
                System.out.println("Transaction history for account ID: " + accountId);
                System.out.println("ID\tType\t\tAmount\t\tDate");
                System.out.println("--------------------------------------------------");
                for (Transaction transaction : transactions) {
                    System.out.printf("%d\t%s\t\t$%.2f\t\t%s\n", 
                        transaction.getTransaction_id(), 
                        transaction.getTransaction_type(), 
                        transaction.getAmount(), 
                        transaction.getTransaction_date());
                }
            }
        } catch (Exception e) {
            System.out.println("Error retrieving transaction history: " + e.getMessage());
        }
    }
    
    // Loan Operations
    private static void applyForLoan() {
        System.out.println("\n----- Apply for Loan -----");
        int customerId = getIntInput("Enter customer ID: ");
        
        // Verify customer exists
        Customer customer = bankingService.getCustomerDetails(customerId);
        if (customer == null) {
            System.out.println("Customer not found!");
            return;
        }
        
        double loanAmount = getDoubleInput("Enter loan amount: ");
        double interestRate = getDoubleInput("Enter interest rate (e.g., 0.05 for 5%): ");
        String status = "pending"; // All new loans start with pending status
        
        try {
            LoanDAO loanDAO = new LoanDAO();
            String currentDate = LocalDate.now().format(dateFormatter);
            
            Loan loan = new Loan(customerId, loanAmount, interestRate, currentDate, status);
            loanDAO.addLoan(loan);
            
            System.out.println("Loan application submitted successfully!");
            System.out.println("Loan amount: $" + loanAmount);
            System.out.println("Interest rate: " + (interestRate * 100) + "%");
            System.out.println("Status: " + status);
        } catch (Exception e) {
            System.out.println("Error applying for loan: " + e.getMessage());
        }
    }
    
    private static void viewCustomerLoans() {
        System.out.println("\n----- View Customer Loans -----");
        int customerId = getIntInput("Enter customer ID: ");
        
        try {
            Customer customer = bankingService.getCustomerDetails(customerId);
            if (customer == null) {
                System.out.println("Customer not found!");
                return;
            }
            
            LoanDAO loanDAO = new LoanDAO();
            List<Loan> loans = loanDAO.getLoansByCustomerId(customerId);
            
            if (loans.isEmpty()) {
                System.out.println("No loans found for customer: " + customer.getName());
            } else {
                System.out.println("Loans for customer: " + customer.getName());
                System.out.println("ID\tAmount\t\tRate\t\tStart Date\tStatus");
                System.out.println("--------------------------------------------------------------");
                for (Loan loan : loans) {
                    System.out.printf("%d\t$%.2f\t%.2f%%\t\t%s\t%s\n", 
                        loan.getLoan_id(), 
                        loan.getLoan_amount(), 
                        loan.getInterest_rate() * 100, 
                        loan.getStart_date(), 
                        loan.getStatus());
                }
            }
        } catch (Exception e) {
            System.out.println("Error retrieving customer loans: " + e.getMessage());
        }
    }
    
    // Run the demo operations
    private static void runDemoOperations() {
        System.out.println("\n===== RUNNING DEMO OPERATIONS =====");
        String today = LocalDate.now().format(dateFormatter);
        
        try {
            // Create a customer
            System.out.println("Creating customer...");
            Customer customer = bankingService.createCustomer("Darren San", "emample@gmail.com", "1234567890", "100 Sesame Street");
            System.out.println("Customer created with ID: " + customer.getCustomer_id());
            
            // Create checking account
            System.out.println("\nCreating checking account...");
            Account checkingAccount = bankingService.createAccount(customer.getCustomer_id(), "checking", 1000.0);
            System.out.println("Checking account created with ID: " + checkingAccount.getAccount_id());
            
            // Create savings account
            System.out.println("\nCreating savings account...");
            Account savingsAccount = bankingService.createAccount(customer.getCustomer_id(), "savings", 500.0);
            System.out.println("Savings account created with ID: " + savingsAccount.getAccount_id());
            
            // Make a deposit
            System.out.println("\nMaking deposit to checking account...");
            boolean depositResult = bankingService.deposit(checkingAccount.getAccount_id(), 250.0);
            System.out.println("Deposit result: " + depositResult);
            
            // Make a withdrawal
            System.out.println("\nMaking withdrawal from checking account...");
            boolean withdrawResult = bankingService.withdraw(checkingAccount.getAccount_id(), 100.0);
            System.out.println("Withdrawal result: " + withdrawResult);
            
            // Make a transfer between accounts
            System.out.println("\nTransferring between accounts...");
            boolean transferResult = bankingService.transfer(
                checkingAccount.getAccount_id(), 
                savingsAccount.getAccount_id(), 
                200.0
            );
            System.out.println("Transfer result: " + transferResult);
            
            // Get account details
            System.out.println("\nChecking account details:");
            Account updatedChecking = bankingService.getAccountDetails(checkingAccount.getAccount_id());
            System.out.println("Current balance: " + updatedChecking.getBalance());
            
            System.out.println("\nSavings account details:");
            Account updatedSavings = bankingService.getAccountDetails(savingsAccount.getAccount_id());
            System.out.println("Current balance: " + updatedSavings.getBalance());
            
            // Get transaction history
            System.out.println("\nTransaction history for checking account:");
            List<Transaction> transactions = bankingService.getAccountTransactions(checkingAccount.getAccount_id());
            for (Transaction t : transactions) {
                System.out.printf("ID: %d, Type: %s, Amount: %.2f, Date: %s\n", 
                    t.getTransaction_id(), t.getTransaction_type(), t.getAmount(), t.getTransaction_date());
            }
            
            // Create a loan
            LoanDAO loanDAO = new LoanDAO();
            Loan loan = new Loan(customer.getCustomer_id(), 5000.0, 0.05, today, "pending");
            loanDAO.addLoan(loan);
            System.out.println("\nLoan created for customer: " + customer.getCustomer_id());
            
            // Display customer accounts
            System.out.println("\nAll accounts for customer " + customer.getName() + ":");
            List<Account> customerAccounts = bankingService.getCustomerAccounts(customer.getCustomer_id());
            for (Account acc : customerAccounts) {
                System.out.printf("Account ID: %d, Type: %s, Balance: %.2f\n", 
                    acc.getAccount_id(), acc.getAccount_type(), acc.getBalance());
            }
            
            System.out.println("\n===== DEMO COMPLETED SUCCESSFULLY =====");
            
        } catch (Exception e) {
            System.err.println("An error occurred during demo: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Helper methods for input
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
    
    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer!");
            }
        }
    }
    
    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }
    }
}