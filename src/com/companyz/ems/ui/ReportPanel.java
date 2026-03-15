package com.companyz.ems.ui;

import com.companyz.ems.dao.DivisionDAO;
import com.companyz.ems.dao.JobTitleDAO;
import com.companyz.ems.model.*;
import com.companyz.ems.service.EmployeeService;
import com.companyz.ems.service.PayrollService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ReportPanel extends JPanel {
    private String reportType;
    private int empID;
    private PayrollService payrollService = new PayrollService();
    private EmployeeService employeeService = new EmployeeService();
    private DivisionDAO divisionDAO = new DivisionDAO();
    private JobTitleDAO jobTitleDAO = new JobTitleDAO();

    public ReportPanel(String reportType, int empID) {
        this.reportType = reportType;
        this.empID = empID;
        setLayout(new BorderLayout());
        setBackground(Theme.CONTENT_BG);

        switch (reportType) {
            case "paystubs": showPayStubs(); break;
            case "payByJobTitle": showPayByJobTitle(); break;
            case "payByDivision": showPayByDivision(); break;
            case "newHires": showNewHires(); break;
        }
    }

    private void showPayStubs() {
        JPanel wrapper = Theme.createContentWrapper();

        JLabel header = new JLabel("Pay Statement History");
        header.setFont(Theme.FONT_TITLE);
        header.setForeground(Theme.PRIMARY);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));
        wrapper.add(header, BorderLayout.NORTH);

        String[] columns = {"Pay Date", "Gross Pay", "Federal Tax", "State Tax",
                "Social Security", "Medicare", "401(k)", "Health Ins.", "Net Pay"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };

        List<Payroll> payHistory = payrollService.getPayHistory(empID);
        for (Payroll p : payHistory) {
            model.addRow(new Object[]{
                p.getPayDate(),
                String.format("$%,.2f", p.getGrossPay()),
                String.format("$%,.2f", p.getFederalTax()),
                String.format("$%,.2f", p.getStateTax()),
                String.format("$%,.2f", p.getSocialSecurity()),
                String.format("$%,.2f", p.getMedicare()),
                String.format("$%,.2f", p.getRetirement401k()),
                String.format("$%,.2f", p.getHealthInsurance()),
                String.format("$%,.2f", p.getNetPay())
            });
        }

        JTable table = new JTable(model);
        Theme.styleTable(table);
        wrapper.add(Theme.createStyledScrollPane(table), BorderLayout.CENTER);

        // Summary
        double totalGross = payHistory.stream().mapToDouble(Payroll::getGrossPay).sum();
        double totalNet = payHistory.stream().mapToDouble(Payroll::getNetPay).sum();
        JPanel summary = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        summary.setOpaque(false);
        JLabel totalLabel = new JLabel(String.format("Total Gross: $%,.2f   |   Total Net: $%,.2f   |   Records: %d",
                totalGross, totalNet, payHistory.size()));
        totalLabel.setFont(Theme.FONT_BODY_BOLD);
        totalLabel.setForeground(Theme.PRIMARY);
        summary.add(totalLabel);
        wrapper.add(summary, BorderLayout.SOUTH);

        add(wrapper, BorderLayout.CENTER);
    }

    private void showPayByJobTitle() {
        JPanel wrapper = Theme.createContentWrapper();

        JLabel header = new JLabel("Total Pay by Job Title");
        header.setFont(Theme.FONT_TITLE);
        header.setForeground(Theme.PRIMARY);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));
        wrapper.add(header, BorderLayout.NORTH);

        JPanel card = Theme.createCard();
        card.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 12, 8, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        card.add(Theme.createFormLabel("Job Title"), gbc);
        gbc.gridx = 1;
        List<JobTitle> titles = jobTitleDAO.getAllJobTitles();
        JComboBox<JobTitle> titleCombo = new JComboBox<>(titles.toArray(new JobTitle[0]));
        titleCombo.setFont(Theme.FONT_BODY);
        card.add(titleCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        card.add(Theme.createFormLabel("Month (1-12)"), gbc);
        gbc.gridx = 1;
        JTextField monthField = Theme.createStyledTextField(8);
        card.add(monthField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        card.add(Theme.createFormLabel("Year"), gbc);
        gbc.gridx = 1;
        JTextField yearField = Theme.createStyledTextField(8);
        card.add(yearField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.insets = new Insets(16, 12, 8, 12);
        JButton genBtn = Theme.createPrimaryButton("Generate Report");
        card.add(genBtn, gbc);

        gbc.gridy = 4;
        gbc.insets = new Insets(20, 12, 8, 12);
        JLabel resultLabel = new JLabel(" ");
        resultLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        resultLabel.setForeground(Theme.ACCENT);
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(resultLabel, gbc);

        wrapper.add(card, BorderLayout.CENTER);
        add(wrapper, BorderLayout.CENTER);

        genBtn.addActionListener(e -> {
            try {
                int month = Integer.parseInt(monthField.getText().trim());
                int year = Integer.parseInt(yearField.getText().trim());
                String title = ((JobTitle) titleCombo.getSelectedItem()).getJobTitle();
                double total = payrollService.getTotalPayByJobTitle(title, month, year);
                resultLabel.setText(String.format("Total: $%,.2f", total));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid month and year.");
            }
        });
    }

    private void showPayByDivision() {
        JPanel wrapper = Theme.createContentWrapper();

        JLabel header = new JLabel("Total Pay by Division");
        header.setFont(Theme.FONT_TITLE);
        header.setForeground(Theme.PRIMARY);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));
        wrapper.add(header, BorderLayout.NORTH);

        JPanel card = Theme.createCard();
        card.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 12, 8, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        card.add(Theme.createFormLabel("Division"), gbc);
        gbc.gridx = 1;
        List<Division> divisions = divisionDAO.getAllDivisions();
        JComboBox<Division> divCombo = new JComboBox<>(divisions.toArray(new Division[0]));
        divCombo.setFont(Theme.FONT_BODY);
        card.add(divCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        card.add(Theme.createFormLabel("Month (1-12)"), gbc);
        gbc.gridx = 1;
        JTextField monthField = Theme.createStyledTextField(8);
        card.add(monthField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        card.add(Theme.createFormLabel("Year"), gbc);
        gbc.gridx = 1;
        JTextField yearField = Theme.createStyledTextField(8);
        card.add(yearField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.insets = new Insets(16, 12, 8, 12);
        JButton genBtn = Theme.createPrimaryButton("Generate Report");
        card.add(genBtn, gbc);

        gbc.gridy = 4;
        gbc.insets = new Insets(20, 12, 8, 12);
        JLabel resultLabel = new JLabel(" ");
        resultLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        resultLabel.setForeground(Theme.ACCENT);
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(resultLabel, gbc);

        wrapper.add(card, BorderLayout.CENTER);
        add(wrapper, BorderLayout.CENTER);

        genBtn.addActionListener(e -> {
            try {
                int month = Integer.parseInt(monthField.getText().trim());
                int year = Integer.parseInt(yearField.getText().trim());
                String div = ((Division) divCombo.getSelectedItem()).getDivName();
                double total = payrollService.getTotalPayByDivision(div, month, year);
                resultLabel.setText(String.format("Total: $%,.2f", total));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid month and year.");
            }
        });
    }

    private void showNewHires() {
        JPanel wrapper = Theme.createContentWrapper();

        JLabel header = new JLabel("New Employee Hires Report");
        header.setFont(Theme.FONT_TITLE);
        header.setForeground(Theme.PRIMARY);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));
        wrapper.add(header, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(0, 16));
        centerPanel.setOpaque(false);

        // Input card
        JPanel inputCard = Theme.createCard();
        inputCard.setLayout(new GridBagLayout());
        GridBagConstraints igbc = new GridBagConstraints();
        igbc.insets = new Insets(6, 8, 6, 8);
        igbc.fill = GridBagConstraints.HORIZONTAL;
        igbc.anchor = GridBagConstraints.WEST;

        igbc.gridx = 0; igbc.gridy = 0;
        inputCard.add(Theme.createFormLabel("Start Date (yyyy-MM-dd):"), igbc);
        igbc.gridx = 1;
        JTextField startField = Theme.createStyledTextField(12);
        inputCard.add(startField, igbc);

        igbc.gridx = 2;
        inputCard.add(Theme.createFormLabel("End Date (yyyy-MM-dd):"), igbc);
        igbc.gridx = 3;
        JTextField endField = Theme.createStyledTextField(12);
        inputCard.add(endField, igbc);

        igbc.gridx = 4;
        JButton genBtn = Theme.createPrimaryButton("Generate Report");
        inputCard.add(genBtn, igbc);

        centerPanel.add(inputCard, BorderLayout.NORTH);

        // Results table
        String[] columns = {"Emp ID", "First Name", "Last Name", "SSN", "DOB", "Email", "Phone", "Hire Date"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(model);
        Theme.styleTable(table);
        centerPanel.add(Theme.createStyledScrollPane(table), BorderLayout.CENTER);

        wrapper.add(centerPanel, BorderLayout.CENTER);
        add(wrapper, BorderLayout.CENTER);

        genBtn.addActionListener(e -> {
            try {
                model.setRowCount(0);
                java.sql.Date start = java.sql.Date.valueOf(startField.getText().trim());
                java.sql.Date end = java.sql.Date.valueOf(endField.getText().trim());
                List<Employee> hires = employeeService.getNewHires(start, end);
                for (Employee emp : hires) {
                    model.addRow(new Object[]{
                        emp.getEmpID(), emp.getFirstName(), emp.getLastName(),
                        emp.getSSN(), emp.getDOB(), emp.getEmail(),
                        emp.getPhone(), emp.getHireDate()
                    });
                }
                if (hires.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No new hires found in the given date range.");
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Invalid date format. Use yyyy-MM-dd.");
            }
        });
    }
}
