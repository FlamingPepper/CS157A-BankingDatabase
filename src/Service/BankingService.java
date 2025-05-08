package Service;

import DAO.AccountDAO;
import DAO.CustomerDAO;
import DAO.TransactionDAO;
import Model.Account;
import Model.Customer;
import Model.Transaction;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BankingService {
    private AccountDAO accountDAO;
    private CustomerDAO customerDAO;
    private TransactionDAO transactionDAO;
    
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    public BankingService() {
        this.accountDAO = new AccountDAO();
        this.customerDAO = new CustomerDAO();
        this.transactionDAO = new TransactionDAO();
    }
    
    public Customer createCustomer(String name, String email, String phone, String address) {
        Customer customer = new Customer(name, email, phone, address);
        customerDAO.addCustomer(customer);
        return customer;
    }
    
    public Account createAccount(int customerId, String accountType, double initialDeposit) {
        // Validate customer exists
        Customer customer = customerDAO.getCustomerById(customerId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer does not exist");
        }
        
        // Create account
        String currentDate = LocalDate.now().format(DATE_FORMAT);
        Account account = new Account(customerId, accountType, initialDeposit, currentDate);
        accountDAO.addAccount(account);
        
        // Create initial deposit transaction if amount > 0
        if (initialDeposit > 0) {
            Transaction transaction = new Transaction(account.getAccount_id(), initialDeposit, "deposit", currentDate);
            transactionDAO.addTransaction(transaction);
        }
        
        return account;
    }
    
    public boolean deposit(int accountId, double amount) {
        if (amount <= 0) {
            return false;
        }
        
        Account account = accountDAO.getAccountById(accountId);
        if (account == null) {
            return false;
        }
        
        double newBalance = account.getBalance() + amount;
        accountDAO.updateBalance(accountId, newBalance);
        
        // Record transaction
        String currentDate = LocalDate.now().format(DATE_FORMAT);
        Transaction transaction = new Transaction(accountId, amount, "deposit", currentDate);
        transactionDAO.addTransaction(transaction);
        
        return true;
    }
    
    public boolean withdraw(int accountId, double amount) {
        if (amount <= 0) {
            return false;
        }
        
        Account account = accountDAO.getAccountById(accountId);
        if (account == null || account.getBalance() < amount) {
            return false;
        }
        
        double newBalance = account.getBalance() - amount;
        accountDAO.updateBalance(accountId, newBalance);
        
        // Record transaction
        String currentDate = LocalDate.now().format(DATE_FORMAT);
        Transaction transaction = new Transaction(accountId, -amount, "withdrawal", currentDate);
        transactionDAO.addTransaction(transaction);
        
        return true;
    }
    
    public boolean transfer(int fromAccountId, int toAccountId, double amount) {
        if (amount <= 0) {
            return false;
        }
        
        boolean success = accountDAO.transfer(fromAccountId, toAccountId, amount);
        
        if (success) {
            // Record transactions
            String currentDate = LocalDate.now().format(DATE_FORMAT);
            
            // From account transaction
            Transaction fromTrans = new Transaction(fromAccountId, -amount, "transfer_out", currentDate);
            transactionDAO.addTransaction(fromTrans);
            
            // To account transaction
            Transaction toTrans = new Transaction(toAccountId, amount, "transfer_in", currentDate);
            transactionDAO.addTransaction(toTrans);
        }
        
        return success;
    }
    
    public List<Account> getCustomerAccounts(int customerId) {
        return accountDAO.getAccountsByCustomerId(customerId);
    }
    
    public List<Transaction> getAccountTransactions(int accountId) {
        return transactionDAO.getTransactionsByAccountId(accountId);
    }
    
    public List<Transaction> getAccountTransactionsByDateRange(int accountId, String startDate, String endDate) {
        return transactionDAO.getTransactionsByDateRange(accountId, startDate, endDate);
    }
    
    public Account getAccountDetails(int accountId) {
        return accountDAO.getAccountById(accountId);
    }
    
    public Customer getCustomerDetails(int customerId) {
        return customerDAO.getCustomerById(customerId);
    }
}
