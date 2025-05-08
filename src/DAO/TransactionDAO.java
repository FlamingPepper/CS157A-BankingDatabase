package DAO;

import Model.Transaction;
import Util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    public void addTransaction(Transaction t) {
        String sql = "INSERT INTO Transactions (account_id, amount, transaction_type, transaction_date) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet keys = null;

        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, t.getAccount_id());
            stmt.setDouble(2, t.getAmount());
            stmt.setString(3, t.getTransaction_type());
            stmt.setString(4, t.getTransaction_date());

            stmt.executeUpdate();
            
            keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                t.setTransaction_id(keys.getInt(1));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, keys);
        }
    }

    public List<Transaction> getTransactionsByAccountId(int accountId) {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM Transactions WHERE account_id = ? ORDER BY transaction_date DESC";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, accountId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Transaction t = new Transaction();
                t.setTransaction_id(rs.getInt("transaction_id"));
                t.setAccount_id(rs.getInt("account_id"));
                t.setAmount(rs.getDouble("amount"));
                t.setTransaction_type(rs.getString("transaction_type"));
                t.setTransaction_date(rs.getString("transaction_date"));
                list.add(t);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, rs);
        }

        return list;
    }
    
    public List<Transaction> getTransactionsByDateRange(int accountId, String startDate, String endDate) {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM Transactions WHERE account_id = ? AND transaction_date BETWEEN ? AND ? ORDER BY transaction_date DESC";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, accountId);
            stmt.setString(2, startDate);
            stmt.setString(3, endDate);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Transaction t = new Transaction();
                t.setTransaction_id(rs.getInt("transaction_id"));
                t.setAccount_id(rs.getInt("account_id"));
                t.setAmount(rs.getDouble("amount"));
                t.setTransaction_type(rs.getString("transaction_type"));
                t.setTransaction_date(rs.getString("transaction_date"));
                list.add(t);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, rs);
        }

        return list;
    }

    private void closeResources(Connection conn, Statement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        DBConnection.releaseConnection(conn);
    }
}

