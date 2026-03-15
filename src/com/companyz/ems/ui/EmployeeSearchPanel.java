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
        setLayout(new BorderLayout(0, 0));
        setBackground(Theme.CONTENT_BG);
        initComponents();
    }

    private void initComponents() {
        JPanel wrapper = Theme.createContentWrapper();

        // Header
        JLabel header = new JLabel("Search Employees");
        header.setFont(Theme.FONT_TITLE);
        header.setForeground(Theme.PRIMARY);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        wrapper.add(header, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(0, 16));
        centerPanel.setOpaque(false);

        // Search card
        JPanel searchCard = Theme.createCard();
        searchCard.setLayout(new BorderLayout(12, 0));

        JPanel searchFields = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        searchFields.setOpaque(false);

        JLabel searchByLabel = Theme.createFormLabel("Search by:");
        searchFields.add(searchByLabel);

        searchTypeCombo = new JComboBox<>(new String[]{"Name", "DOB (yyyy-MM-dd)", "SSN", "Employee ID"});
        searchTypeCombo.setFont(Theme.FONT_BODY);
        searchTypeCombo.setPreferredSize(new Dimension(180, 36));
        searchFields.add(searchTypeCombo);

        searchField = Theme.createStyledTextField(22);
        searchFields.add(searchField);

        JButton searchBtn = Theme.createPrimaryButton("Search");
        searchFields.add(searchBtn);

        searchCard.add(searchFields, BorderLayout.CENTER);
        centerPanel.add(searchCard, BorderLayout.NORTH);

        // Results table
        String[] columns = {"Emp ID", "First Name", "Last Name", "SSN", "Date of Birth", "Email", "Phone", "Hire Date"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        resultTable = new JTable(tableModel);
        resultTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Theme.styleTable(resultTable);

        JScrollPane scrollPane = Theme.createStyledScrollPane(resultTable);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        wrapper.add(centerPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 12));
        buttonPanel.setOpaque(false);

        JButton viewBtn = Theme.createSecondaryButton("View Details");
        buttonPanel.add(viewBtn);

        if (AuthService.isAdmin()) {
            JButton editBtn = Theme.createPrimaryButton("Edit Employee");
            JButton deleteBtn = Theme.createDangerButton("Delete");
            buttonPanel.add(editBtn);
            buttonPanel.add(deleteBtn);
            editBtn.addActionListener(e -> openDetail(true));
            deleteBtn.addActionListener(e -> deleteSelected());
        }

        wrapper.add(buttonPanel, BorderLayout.SOUTH);

        add(wrapper, BorderLayout.CENTER);

        searchBtn.addActionListener(e -> doSearch());
        searchField.addActionListener(e -> doSearch());
        viewBtn.addActionListener(e -> openDetail(false));
    }

    private void doSearch() {
        tableModel.setRowCount(0);
        String searchValue = searchField.getText().trim();
        if (searchValue.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a search value.",
                    "Search", JOptionPane.INFORMATION_MESSAGE);
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
            JOptionPane.showMessageDialog(this, "Please select an employee from the table.",
                    "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Employee emp = currentResults.get(row);
        mainFrame.showPanel("detail", new EmployeeDetailPanel(emp, editable, mainFrame));
    }

    private void deleteSelected() {
        int row = resultTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select an employee to delete.",
                    "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Employee emp = currentResults.get(row);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete " + emp.getFirstName() + " " + emp.getLastName() + "?\nThis action cannot be undone.",
                "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
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
