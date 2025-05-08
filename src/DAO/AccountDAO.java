package DAO;

import Model.Account;
import Util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    public void addAccount(Account acc) {
        String sql = "INSERT INTO Accounts (customer_id, account_type, balance, open_date) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet keys = null;

        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, acc.getCustomer_id());
            stmt.setString(2, acc.getAccount_type());
            stmt.setDouble(3, acc.getBalance());
            stmt.setString(4, acc.getOpen_date());

            stmt.executeUpdate();

            keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                acc.setAccount_id(keys.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, keys);
        }
    }

    public Account getAccountById(int id) {
        String sql = "SELECT * FROM Accounts WHERE account_id = ?";
        Account acc = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                acc = new Account();
                acc.setAccount_id(rs.getInt("account_id"));
                acc.setCustomer_id(rs.getInt("customer_id"));
                acc.setAccount_type(rs.getString("account_type"));
                acc.setBalance(rs.getDouble("balance"));
                acc.setOpen_date(rs.getString("open_date"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, rs);
        }

        return acc;
    }

    public List<Account> getAccountsByCustomerId(int customerId) {
        String sql = "SELECT * FROM Accounts WHERE customer_id = ?";
        List<Account> accounts = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, customerId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Account acc = new Account();
                acc.setAccount_id(rs.getInt("account_id"));
                acc.setCustomer_id(rs.getInt("customer_id"));
                acc.setAccount_type(rs.getString("account_type"));
                acc.setBalance(rs.getDouble("balance"));
                acc.setOpen_date(rs.getString("open_date"));
                accounts.add(acc);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, rs);
        }

        return accounts;
    }

    public void updateBalance(int accountId, double newBalance) {
        String sql = "UPDATE Accounts SET balance = ? WHERE account_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setDouble(1, newBalance);
            stmt.setInt(2, accountId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, null);
        }
    }
    
    public boolean transfer(int fromAccountId, int toAccountId, double amount) {
        Connection conn = null;
        PreparedStatement updateFrom = null;
        PreparedStatement updateTo = null;
        
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);  // Start transaction
            
            // Check if from account has sufficient funds
            Account fromAccount = getAccountById(fromAccountId);
            if (fromAccount == null || fromAccount.getBalance() < amount) {
                return false;
            }
            
            // Check if to account exists
            Account toAccount = getAccountById(toAccountId);
            if (toAccount == null) {
                return false;
            }
            
            // Update from account
            String sqlFrom = "UPDATE Accounts SET balance = balance - ? WHERE account_id = ?";
            updateFrom = conn.prepareStatement(sqlFrom);
            updateFrom.setDouble(1, amount);
            updateFrom.setInt(2, fromAccountId);
            updateFrom.executeUpdate();
            
            // Update to account
            String sqlTo = "UPDATE Accounts SET balance = balance + ? WHERE account_id = ?";
            updateTo = conn.prepareStatement(sqlTo);
            updateTo.setDouble(1, amount);
            updateTo.setInt(2, toAccountId);
            updateTo.executeUpdate();
            
            // Commit transaction
            conn.commit();
            return true;
            
        } catch (SQLException e) {
            // Rollback transaction on error
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            // Reset auto-commit
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            
            // Close resources
            if (updateFrom != null) {
                try {
                    updateFrom.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            
            if (updateTo != null) {
                try {
                    updateTo.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            
            DBConnection.releaseConnection(conn);
        }
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

