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
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        switch (reportType) {
            case "paystubs": showPayStubs(); break;
            case "payByJobTitle": showPayByJobTitle(); break;
            case "payByDivision": showPayByDivision(); break;
            case "newHires": showNewHires(); break;
        }
    }

    private void showPayStubs() {
        JLabel header = new JLabel("Pay Statement History");
        header.setFont(new Font("SansSerif", Font.BOLD, 16));
        add(header, BorderLayout.NORTH);

        String[] columns = {"Pay Date", "Gross Pay", "Federal Tax", "State Tax", "Social Security",
                "Medicare", "401(k)", "Health Ins.", "Net Pay"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };

        List<Payroll> payHistory = payrollService.getPayHistory(empID);
        for (Payroll p : payHistory) {
            model.addRow(new Object[]{
                p.getPayDate(),
                String.format("$%.2f", p.getGrossPay()),
                String.format("$%.2f", p.getFederalTax()),
                String.format("$%.2f", p.getStateTax()),
                String.format("$%.2f", p.getSocialSecurity()),
                String.format("$%.2f", p.getMedicare()),
                String.format("$%.2f", p.getRetirement401k()),
                String.format("$%.2f", p.getHealthInsurance()),
                String.format("$%.2f", p.getNetPay())
            });
        }

        JTable table = new JTable(model);
        table.setRowHeight(25);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void showPayByJobTitle() {
        JLabel header = new JLabel("Total Pay by Job Title");
        header.setFont(new Font("SansSerif", Font.BOLD, 16));

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        inputPanel.add(header);

        List<JobTitle> titles = jobTitleDAO.getAllJobTitles();
        JComboBox<JobTitle> titleCombo = new JComboBox<>(titles.toArray(new JobTitle[0]));
        inputPanel.add(new JLabel("Job Title:"));
        inputPanel.add(titleCombo);

        JTextField monthField = new JTextField(4);
        inputPanel.add(new JLabel("Month (1-12):"));
        inputPanel.add(monthField);

        JTextField yearField = new JTextField(6);
        inputPanel.add(new JLabel("Year:"));
        inputPanel.add(yearField);

        JButton genBtn = new JButton("Generate");
        inputPanel.add(genBtn);
        add(inputPanel, BorderLayout.NORTH);

        JLabel resultLabel = new JLabel(" ");
        resultLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(resultLabel, BorderLayout.CENTER);

        genBtn.addActionListener(e -> {
            try {
                int month = Integer.parseInt(monthField.getText().trim());
                int year = Integer.parseInt(yearField.getText().trim());
                String title = ((JobTitle) titleCombo.getSelectedItem()).getJobTitle();
                double total = payrollService.getTotalPayByJobTitle(title, month, year);
                resultLabel.setText(String.format("Total Pay for '%s' in %d/%d: $%.2f", title, month, year, total));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid month and year.");
            }
        });
    }

    private void showPayByDivision() {
        JLabel header = new JLabel("Total Pay by Division");
        header.setFont(new Font("SansSerif", Font.BOLD, 16));

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        inputPanel.add(header);

        List<Division> divisions = divisionDAO.getAllDivisions();
        JComboBox<Division> divCombo = new JComboBox<>(divisions.toArray(new Division[0]));
        inputPanel.add(new JLabel("Division:"));
        inputPanel.add(divCombo);

        JTextField monthField = new JTextField(4);
        inputPanel.add(new JLabel("Month (1-12):"));
        inputPanel.add(monthField);

        JTextField yearField = new JTextField(6);
        inputPanel.add(new JLabel("Year:"));
        inputPanel.add(yearField);

        JButton genBtn = new JButton("Generate");
        inputPanel.add(genBtn);
        add(inputPanel, BorderLayout.NORTH);

        JLabel resultLabel = new JLabel(" ");
        resultLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(resultLabel, BorderLayout.CENTER);

        genBtn.addActionListener(e -> {
            try {
                int month = Integer.parseInt(monthField.getText().trim());
                int year = Integer.parseInt(yearField.getText().trim());
                String div = ((Division) divCombo.getSelectedItem()).getDivName();
                double total = payrollService.getTotalPayByDivision(div, month, year);
                resultLabel.setText(String.format("Total Pay for '%s' in %d/%d: $%.2f", div, month, year, total));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid month and year.");
            }
        });
    }

    private void showNewHires() {
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        JLabel header = new JLabel("New Employee Hires Report");
        header.setFont(new Font("SansSerif", Font.BOLD, 16));
        inputPanel.add(header);

        JTextField startField = new JTextField(10);
        inputPanel.add(new JLabel("Start Date (yyyy-MM-dd):"));
        inputPanel.add(startField);

        JTextField endField = new JTextField(10);
        inputPanel.add(new JLabel("End Date (yyyy-MM-dd):"));
        inputPanel.add(endField);

        JButton genBtn = new JButton("Generate");
        inputPanel.add(genBtn);
        add(inputPanel, BorderLayout.NORTH);

        String[] columns = {"EmpID", "First Name", "Last Name", "SSN", "DOB", "Email", "Phone", "Hire Date"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(model);
        table.setRowHeight(25);
        add(new JScrollPane(table), BorderLayout.CENTER);

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
