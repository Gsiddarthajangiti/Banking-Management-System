package gui;

import main.BankSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TransactionForm extends JPanel {
    private JTextField accountIdField;
    private JTextField amountField;
    private BankSystem bankSystem;

    public TransactionForm(BankSystem bankSystem) {
        this.bankSystem = bankSystem;

        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.add(new JLabel("Account ID:"));
        accountIdField = new JTextField();
        inputPanel.add(accountIdField);

        inputPanel.add(new JLabel("Amount:"));
        amountField = new JTextField();
        inputPanel.add(amountField);

        JButton depositButton = new JButton("Deposit");
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performDeposit();
            }
        });

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performWithdraw();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(depositButton);
        buttonPanel.add(withdrawButton);

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void performDeposit() {
        int accountId = Integer.parseInt(accountIdField.getText().trim());
        double amount = Double.parseDouble(amountField.getText().trim());

        try {
            bankSystem.deposit(accountId, amount);
            JOptionPane.showMessageDialog(this, "Deposit successful.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to deposit: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void performWithdraw() {
        int accountId = Integer.parseInt(accountIdField.getText().trim());
        double amount = Double.parseDouble(amountField.getText().trim());

        try {
            bankSystem.withdraw(accountId, amount);
            JOptionPane.showMessageDialog(this, "Withdrawal successful.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to withdraw: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
