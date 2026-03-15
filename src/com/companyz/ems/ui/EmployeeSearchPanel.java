package com.companyz.ems.ui;

import com.companyz.ems.model.Employee;
import com.companyz.ems.service.AuthService;
import com.companyz.ems.service.EmployeeService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EmployeeSearchPanel extends JPanel {
    private JComboBox<String> searchTypeCombo;
    private JTextField searchField;
    private JTable resultTable;
    private DefaultTableModel tableModel;
    private EmployeeService employeeService;
    private MainFrame mainFrame;
    private List<Employee> currentResults;

    public EmployeeSearchPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.employeeService = new EmployeeService();
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        initComponents();
    }

    private void initComponents() {
        // Search bar
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        searchPanel.add(new JLabel("Search by:"));
        searchTypeCombo = new JComboBox<>(new String[]{"Name", "DOB (yyyy-MM-dd)", "SSN", "Employee ID"});
        searchPanel.add(searchTypeCombo);
        searchField = new JTextField(20);
        searchPanel.add(searchField);
        JButton searchBtn = new JButton("Search");
        searchPanel.add(searchBtn);
        add(searchPanel, BorderLayout.NORTH);

        // Results table
        String[] columns = {"EmpID", "First Name", "Last Name", "SSN", "DOB", "Email", "Phone", "Hire Date"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        resultTable = new JTable(tableModel);
        resultTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        resultTable.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(resultTable);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JButton viewBtn = new JButton("View Details");
        buttonPanel.add(viewBtn);

        if (AuthService.isAdmin()) {
            JButton editBtn = new JButton("Edit");
            JButton deleteBtn = new JButton("Delete");
            buttonPanel.add(editBtn);
            buttonPanel.add(deleteBtn);

            editBtn.addActionListener(e -> openDetail(true));
            deleteBtn.addActionListener(e -> deleteSelected());
        }
        add(buttonPanel, BorderLayout.SOUTH);

        searchBtn.addActionListener(e -> doSearch());
        searchField.addActionListener(e -> doSearch());
        viewBtn.addActionListener(e -> openDetail(false));
    }

    private void doSearch() {
        tableModel.setRowCount(0);
        String searchValue = searchField.getText().trim();
        if (searchValue.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a search value.");
            return;
        }

        String type;
        int idx = searchTypeCombo.getSelectedIndex();
        switch (idx) {
            case 0: type = "name"; break;
            case 1: type = "dob"; break;
            case 2: type = "ssn"; break;
            case 3: type = "empid"; break;
            default: type = "name";
        }

        currentResults = employeeService.searchEmployees(type, searchValue);
        if (currentResults.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No employees found matching your search.",
                    "No Results", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (Employee emp : currentResults) {
            tableModel.addRow(new Object[]{
                emp.getEmpID(), emp.getFirstName(), emp.getLastName(),
                emp.getSSN(), emp.getDOB(), emp.getEmail(),
                emp.getPhone(), emp.getHireDate()
            });
        }
    }

    private void openDetail(boolean editable) {
        int row = resultTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select an employee.");
            return;
        }
        Employee emp = currentResults.get(row);
        mainFrame.showPanel("detail", new EmployeeDetailPanel(emp, editable, mainFrame));
    }

    private void deleteSelected() {
        int row = resultTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select an employee to delete.");
            return;
        }
        Employee emp = currentResults.get(row);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete " + emp.getFirstName() + " " + emp.getLastName() + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (employeeService.deleteEmployee(emp.getEmpID())) {
                JOptionPane.showMessageDialog(this, "Employee deleted successfully.");
                tableModel.removeRow(row);
                currentResults.remove(row);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete employee.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
