package Model;
public class Account {
    private int account_id;
    private int customer_id;
    private String account_type;    // checking, savings
    private double balance;
    private String open_date;

    // default constructor
    public Account() {}

    // constructor for new accounts
    public Account(int customer_id, String account_type, double balance, String open_date) {
        this.customer_id = customer_id;
        this.account_type = account_type;
        this.balance = balance;
        this.open_date = open_date;
    }

    // getters
    public int getAccount_id() {
        return account_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public String getAccount_type() {
        return account_type;
    }

    public double getBalance() {
        return balance;
    }

    public String getOpen_date() {
        return open_date;
    }

    // setters
    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setOpen_date(String open_date) {
        this.open_date = open_date;
    }

    @Override
    public String toString() {
        return "Account{" +
                "account_id=" + account_id +
                ", customer_id=" + customer_id +
                ", account_type='" + account_type + '\'' +
                ", balance=" + balance +
                ", open_date='" + open_date + '\'' +
                '}';
    }
}