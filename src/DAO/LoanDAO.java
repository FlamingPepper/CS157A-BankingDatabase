package DAO;

import Model.Loan;
import Util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanDAO {

    public void addLoan(Loan l) {
        String sql = "INSERT INTO Loans (customer_id, loan_amount, interest_rate, start_date, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, l.getCustomer_id());
            stmt.setDouble(2, l.getLoan_amount());
            stmt.setDouble(3, l.getInterest_rate());
            stmt.setString(4, l.getStart_date());
            stmt.setString(5, l.getStatus());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Loan> getLoansByCustomerId(int customerId) {
        List<Loan> loans = new ArrayList<>();
        String sql = "SELECT * FROM Loans WHERE customer_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Loan l = new Loan();
                l.setLoan_id(rs.getInt("loan_id"));
                l.setCustomer_id(rs.getInt("customer_id"));
                l.setLoan_amount(rs.getDouble("loan_amount"));
                l.setInterest_rate(rs.getDouble("interest_rate"));
                l.setStart_date(rs.getString("start_date"));
                l.setStatus(rs.getString("status"));
                loans.add(l);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return loans;
    }
}
