public class account {
    private int account_id;
    private int customer_id;
    private String account_type;    // checking, savings
    private double balance;
    private String open_date;

    // default
    public account() {}

    public account(String account_type, double balance,
                   String open_date) {
        this.account_type = account_type;
        this.balance = balance;
        this.open_date = open_date;
    }

    // getters
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
    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setOpen_date() {
        this.open_date = open_date;
    }
}
