package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/OnlineBanking";
    private static final String USER = "root"; // replace with your MySQL username
    private static final String PASSWORD = ""; // replace with your MySQL password
    private static final int MAX_POOL_SIZE = 10;
    
    private static BlockingQueue<Connection> connectionPool;
    private static boolean poolInitialized = false;
    
    static {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL JDBC Driver successfully loaded!");
            initializeConnectionPool();
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Failed to initialize connection pool!");
            e.printStackTrace();
        }
    }
    
    private static void initializeConnectionPool() throws SQLException {
        if (poolInitialized) return;
        
        connectionPool = new ArrayBlockingQueue<>(MAX_POOL_SIZE);
        
        // Create connection properties
        Properties props = new Properties();
        props.setProperty("user", USER);
        props.setProperty("password", PASSWORD);
        props.setProperty("autoReconnect", "true");
        props.setProperty("useSSL", "false");
        
        // Initialize the pool with connections
        for (int i = 0; i < MAX_POOL_SIZE; i++) {
            connectionPool.offer(DriverManager.getConnection(URL, props));
        }
        
        poolInitialized = true;
    }
    
    public static Connection getConnection() throws SQLException {
        try {
            if (!poolInitialized) {
                initializeConnectionPool();
            }
            
            Connection connection = connectionPool.take();
            if (connection.isClosed()) {
                // If connection is closed, create a new one
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
            return connection;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new SQLException("Interrupted while waiting for a database connection", e);
        }
    }
    
    public static void releaseConnection(Connection connection) {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connectionPool.offer(connection);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void closeAllConnections() {
        for (Connection conn : connectionPool) {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        connectionPool.clear();
    }
}
