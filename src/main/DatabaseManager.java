package main;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/bank_db";
    private static final String USER = "root";
    private static final String PASSWORD = "your_mysql_password";

    private Connection connection;

    public DatabaseManager() throws SQLException {
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void addCustomer(Customer customer) throws SQLException {
        String query = "INSERT INTO customers (name, address, phone) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getAddress());
            stmt.setString(3, customer.getPhone());
            stmt.executeUpdate();
        }
    }

    public void addAccount(Account account) throws SQLException {
        String query = "INSERT INTO accounts (customer_id, balance) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, account.getCustomerId());
            stmt.setDouble(2, account.getBalance());
            stmt.executeUpdate();
        }
    }

    public void updateAccount(Account account) throws SQLException {
        String query = "UPDATE accounts SET balance = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDouble(1, account.getBalance());
            stmt.setInt(2, account.getId());
            stmt.executeUpdate();
        }
    }

    public Account getAccount(int id) throws SQLException {
        String query = "SELECT * FROM accounts WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Account(rs.getInt("id"), rs.getInt("customer_id"), rs.getDouble("balance"));
            } else {
                throw new SQLException("Account not found");
            }
        }
    }

    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM customers";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                customers.add(new Customer(rs.getInt("id"), rs.getString("name"), rs.getString("address"), rs.getString("phone")));
            }
        }
        return customers;
    }

    public List<Account> getAccountsByCustomer(int customerId) throws SQLException {
        List<Account> accounts = new ArrayList<>();
        String query = "SELECT * FROM accounts WHERE customer_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                accounts.add(new Account(rs.getInt("id"), rs.getInt("customer_id"), rs.getDouble("balance")));
            }
        }
        return accounts;
    }
}
