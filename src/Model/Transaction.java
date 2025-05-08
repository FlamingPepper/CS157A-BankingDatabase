package Model;

public class Transaction {
    private int transaction_id;
    private int account_id;
    private double amount;
    private String transaction_type;    // deposit, withdrawl, transfer
    private String transaction_date;

    public Transaction() {}

    public Transaction(
        int account_id, 
        double amount,
        String transaction_type, 
        String transaction_date) 
        {
        this.account_id = account_id;
        this.amount = amount;
        this.transaction_type = transaction_type;
        this.transaction_date = transaction_date;
    }

    public int getTransaction_id() {
        return transaction_id;
    }
    
    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }
    
    // getters
    public int getAccount_id() {
        return account_id;
    }

    public double getAmount() {
        return amount;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public String getTransaction_date() {
        return transaction_date;
    }

    // setters
    public void setAccount_id(int account_id){
        this.account_id = account_id;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    
    public void setTransaction_date(String transaction_date) {
        this.transaction_date = transaction_date;
    }
}
