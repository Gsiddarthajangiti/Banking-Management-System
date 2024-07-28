package gui;

import main.BankSystem;
import main.Customer;
import main.Account;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class AccountForm extends JPanel {
    private JTextField nameField;
    private JTextField addressField;
    private JTextField phoneField;
    private JComboBox<Customer> customerComboBox;
    private BankSystem bankSystem;

    public AccountForm(BankSystem bankSystem) {
        this.bankSystem = bankSystem;

        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Address:"));
        addressField = new JTextField();
        inputPanel.add(addressField);

        inputPanel.add(new JLabel("Phone:"));
        phoneField = new JTextField();
        inputPanel.add(phoneField);

        inputPanel.add(new JLabel("Select Customer:"));
        customerComboBox = new JComboBox<>();
        inputPanel.add(customerComboBox);

        JButton createCustomerButton = new JButton("Create Customer");
        createCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createCustomer();
            }
        });

        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAccount();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createCustomerButton);
        buttonPanel.add(createAccountButton);

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        loadCustomers();
    }

    private void createCustomer() {
        String name = nameField.getText().trim();
        String address = addressField.getText().trim();
        String phone = phoneField.getText().trim();

        if (name.isEmpty() || address.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            bankSystem.createCustomer(name, address, phone);
            JOptionPane.showMessageDialog(this, "Customer created successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadCustomers();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to create customer: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createAccount() {
        Customer selectedCustomer = (Customer) customerComboBox.getSelectedItem();
        if (selectedCustomer == null) {
            JOptionPane.showMessageDialog(this, "No customer selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            bankSystem.createAccount(selectedCustomer.getId());
            JOptionPane.showMessageDialog(this, "Account created successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to create account: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadCustomers() {
        customerComboBox.removeAllItems();
        try {
            List<Customer> customers = bankSystem.getAllCustomers();
            for (Customer customer : customers) {
                customerComboBox.addItem(customer);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to load customers: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
