package gui;

import main.BankSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BalanceInquiryForm extends JPanel {
    private JTextField accountIdField;
    private JLabel balanceLabel;
    private BankSystem bankSystem;

    public BalanceInquiryForm(BankSystem bankSystem) {
        this.bankSystem = bankSystem;

        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.add(new JLabel("Account ID:"));
        accountIdField = new JTextField();
        inputPanel.add(accountIdField);

        JButton inquireButton = new JButton("Inquire Balance");
        inquireButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performInquiry();
            }
        });

        balanceLabel = new JLabel("Balance: ");
        JPanel balancePanel = new JPanel();
        balancePanel.add(balanceLabel);

        add(inputPanel, BorderLayout.NORTH);
        add(inquireButton, BorderLayout.CENTER);
        add(balancePanel, BorderLayout.SOUTH);
    }

    private void performInquiry() {
        int accountId = Integer.parseInt(accountIdField.getText().trim());

        try {
            double balance = bankSystem.getBalance(accountId);
            balanceLabel.setText("Balance: " + balance);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to inquire balance: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
