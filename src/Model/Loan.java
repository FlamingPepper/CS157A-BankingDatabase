package Model;
public class Loan {
    private int loan_id;
    private int customer_id;
    private double loan_amount;
    private double interest_rate;   // decimal
    private String start_date;
    private String status;      // approved, pending, rejected

    public Loan() {}

    public Loan(int customer_id, double loan_amount, double interest_rate,
                String start_date, String status) {
        this.customer_id = customer_id;
        this.loan_amount = loan_amount;
        this.interest_rate = interest_rate;
        this.start_date = start_date;
        this.status = status;
    }

    public int getLoan_id() {
        return loan_id;
    }
    public void setLoan_id(int loan_id) {
        this.loan_id = loan_id;
    }
    // getters
    public int getCustomer_id() {
        return customer_id;
    }

    public double getLoan_amount() {
        return loan_amount;
    }

    public double getInterest_rate() {
        return interest_rate;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getStatus() {
        return status;
    }

    // setters
    public void setLoan_amount(double loan_amount) {
        this.loan_amount = loan_amount;
    }

    public void setInterest_rate(double interest_rate) {
        this.interest_rate = interest_rate;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }
    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }
    
}
