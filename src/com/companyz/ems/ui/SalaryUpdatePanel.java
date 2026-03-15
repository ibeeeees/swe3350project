package com.companyz.ems.ui;

import com.companyz.ems.service.SalaryService;
import javax.swing.*;
import java.awt.*;

public class SalaryUpdatePanel extends JPanel {
    private JTextField percentageField, minField, maxField;
    private JTextArea resultArea;
    private SalaryService salaryService;

    public SalaryUpdatePanel() {
        salaryService = new SalaryService();
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        initComponents();
    }

    private void initComponents() {
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel header = new JLabel("Salary Update - Increase by Percentage");
        header.setFont(new Font("SansSerif", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        inputPanel.add(header, gbc);
        gbc.gridwidth = 1;

        gbc.gridy = 1; gbc.gridx = 0;
        inputPanel.add(new JLabel("Increase Percentage (%):"), gbc);
        gbc.gridx = 1;
        percentageField = new JTextField(10);
        inputPanel.add(percentageField, gbc);

        gbc.gridy = 2; gbc.gridx = 0;
        inputPanel.add(new JLabel("Minimum Annual Salary ($):"), gbc);
        gbc.gridx = 1;
        minField = new JTextField(10);
        inputPanel.add(minField, gbc);

        gbc.gridy = 3; gbc.gridx = 0;
        inputPanel.add(new JLabel("Maximum Annual Salary ($):"), gbc);
        gbc.gridx = 1;
        maxField = new JTextField(10);
        inputPanel.add(maxField, gbc);

        gbc.gridy = 4; gbc.gridx = 0; gbc.gridwidth = 2;
        JButton updateBtn = new JButton("Update Salaries");
        inputPanel.add(updateBtn, gbc);

        add(inputPanel, BorderLayout.NORTH);

        resultArea = new JTextArea(8, 40);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        updateBtn.addActionListener(e -> doUpdate());
    }

    private void doUpdate() {
        try {
            double percentage = Double.parseDouble(percentageField.getText().trim());
            double min = Double.parseDouble(minField.getText().trim());
            double max = Double.parseDouble(maxField.getText().trim());

            if (percentage <= 0 || min < 0 || max <= min) {
                JOptionPane.showMessageDialog(this,
                        "Please enter valid values. Percentage > 0, Max > Min >= 0.",
                        "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    String.format("Apply %.1f%% increase to employees with annual salary between $%.2f and $%.2f?",
                            percentage, min, max),
                    "Confirm Salary Update", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                int count = salaryService.updateSalariesByPercentage(percentage, min, max);
                resultArea.setText(String.format(
                        "Salary Update Complete\n" +
                        "========================\n" +
                        "Percentage Increase: %.1f%%\n" +
                        "Salary Range: $%.2f - $%.2f\n" +
                        "Employees Updated: %d\n",
                        percentage, min, max, count));
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
