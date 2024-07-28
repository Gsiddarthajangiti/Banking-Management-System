package gui;

import main.BankSystem;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class MainFrame extends JFrame {
    private JTabbedPane tabbedPane;
    private BankSystem bankSystem;

    public MainFrame() {
        try {
            bankSystem = new BankSystem();

            setTitle("Banking System");
            setSize(800, 600);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            tabbedPane = new JTabbedPane();
            tabbedPane.addTab("Accounts", new AccountForm(bankSystem));
            tabbedPane.addTab("Transactions", new TransactionForm(bankSystem));
            tabbedPane.addTab("Balance Inquiry", new BalanceInquiryForm(bankSystem));

            add(tabbedPane);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to initialize the banking system: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1); // Exit the application if initialization fails
        }
    }
}
