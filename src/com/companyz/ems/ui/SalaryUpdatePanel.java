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
        setLayout(new BorderLayout());
        setBackground(Theme.CONTENT_BG);
        initComponents();
    }

    private void initComponents() {
        JPanel wrapper = Theme.createContentWrapper();

        // Header
        JLabel header = new JLabel("Salary Update");
        header.setFont(Theme.FONT_TITLE);
        header.setForeground(Theme.PRIMARY);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));
        wrapper.add(header, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(0, 16));
        centerPanel.setOpaque(false);

        // Input card
        JPanel card = Theme.createCard();
        card.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 12, 8, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JLabel subtitle = new JLabel("Increase salaries by percentage for employees within a salary range");
        subtitle.setFont(Theme.FONT_BODY);
        subtitle.setForeground(Theme.TEXT_SECONDARY);
        card.add(subtitle, gbc);
        gbc.gridwidth = 1;

        gbc.gridy = 1; gbc.gridx = 0;
        card.add(Theme.createFormLabel("Increase Percentage (%)"), gbc);
        gbc.gridx = 1;
        percentageField = Theme.createStyledTextField(15);
        card.add(percentageField, gbc);

        gbc.gridy = 2; gbc.gridx = 0;
        card.add(Theme.createFormLabel("Minimum Annual Salary ($)"), gbc);
        gbc.gridx = 1;
        minField = Theme.createStyledTextField(15);
        card.add(minField, gbc);

        gbc.gridy = 3; gbc.gridx = 0;
        card.add(Theme.createFormLabel("Maximum Annual Salary ($)"), gbc);
        gbc.gridx = 1;
        maxField = Theme.createStyledTextField(15);
        card.add(maxField, gbc);

        gbc.gridy = 4; gbc.gridx = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(16, 12, 8, 12);
        JButton updateBtn = Theme.createPrimaryButton("Apply Salary Update");
        updateBtn.setPreferredSize(new Dimension(0, 42));
        card.add(updateBtn, gbc);

        centerPanel.add(card, BorderLayout.NORTH);

        // Result area
        JPanel resultCard = Theme.createCard();
        resultCard.setLayout(new BorderLayout());
        JLabel resultHeader = new JLabel("Results");
        resultHeader.setFont(Theme.FONT_HEADER);
        resultHeader.setForeground(Theme.PRIMARY);
        resultHeader.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
        resultCard.add(resultHeader, BorderLayout.NORTH);

        resultArea = new JTextArea(8, 40);
        resultArea.setEditable(false);
        resultArea.setFont(Theme.FONT_MONO);
        resultArea.setBackground(new Color(248, 249, 250));
        resultArea.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        resultArea.setText("No salary updates performed yet.\nConfigure parameters above and click 'Apply Salary Update'.");
        resultCard.add(Theme.createStyledScrollPane(resultArea), BorderLayout.CENTER);

        centerPanel.add(resultCard, BorderLayout.CENTER);
        wrapper.add(centerPanel, BorderLayout.CENTER);

        add(wrapper, BorderLayout.CENTER);

        updateBtn.addActionListener(e -> doUpdate());
    }

    private void doUpdate() {
        try {
            double percentage = Double.parseDouble(percentageField.getText().trim());
            double min = Double.parseDouble(minField.getText().trim());
            double max = Double.parseDouble(maxField.getText().trim());

            if (percentage <= 0 || min < 0 || max <= min) {
                JOptionPane.showMessageDialog(this,
                        "Please enter valid values.\nPercentage must be > 0, Max must be > Min >= 0.",
                        "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    String.format("Apply %.1f%% increase to employees with annual salary\nbetween $%,.2f and $%,.2f?",
                            percentage, min, max),
                    "Confirm Salary Update", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                int count = salaryService.updateSalariesByPercentage(percentage, min, max);
                resultArea.setText(String.format(
                        "  SALARY UPDATE COMPLETE\n" +
                        "  ====================================\n\n" +
                        "  Percentage Increase:   %.1f%%\n" +
                        "  Salary Range:          $%,.2f - $%,.2f\n" +
                        "  Employees Updated:     %d\n\n" +
                        "  ====================================\n" +
                        "  New payroll records have been created\n" +
                        "  with the updated gross pay amounts.\n",
                        percentage, min, max, count));
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
