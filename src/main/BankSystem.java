package main;

import java.sql.SQLException;
import java.util.List;

public class BankSystem {
    private DatabaseManager dbManager;

    public BankSystem() throws SQLException {
        dbManager = new DatabaseManager();
    }

    public void createCustomer(String name, String address, String phone) throws SQLException {
        dbManager.addCustomer(new Customer(0, name, address, phone));
    }

    public void createAccount(int customerId) throws SQLException {
        dbManager.addAccount(new Account(0, customerId, 0.0));
    }

    public void deposit(int accountId, double amount) throws SQLException {
        Account account = dbManager.getAccount(accountId);
        account.setBalance(account.getBalance() + amount);
        dbManager.updateAccount(account);
    }

    public void withdraw(int accountId, double amount) throws SQLException {
        Account account = dbManager.getAccount(accountId);
        if (account.getBalance() >= amount) {
            account.setBalance(account.getBalance() - amount);
            dbManager.updateAccount(account);
        } else {
            throw new SQLException("Insufficient funds");
        }
    }

    public double getBalance(int accountId) throws SQLException {
        return dbManager.getAccount(accountId).getBalance();
    }

    public List<Customer> getAllCustomers() throws SQLException {
        return dbManager.getAllCustomers();
    }

    public List<Account> getAccountsByCustomer(int customerId) throws SQLException {
        return dbManager.getAccountsByCustomer(customerId);
    }
}
