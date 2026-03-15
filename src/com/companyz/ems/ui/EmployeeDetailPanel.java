package com.companyz.ems.ui;

import com.companyz.ems.dao.AddressDAO;
import com.companyz.ems.dao.DivisionDAO;
import com.companyz.ems.dao.JobTitleDAO;
import com.companyz.ems.model.*;
import com.companyz.ems.service.EmployeeService;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EmployeeDetailPanel extends JPanel {
    private Employee employee;
    private boolean editable;
    private MainFrame mainFrame;

    private JTextField firstNameField, lastNameField, ssnField, dobField;
    private JTextField emailField, phoneField, emergContactField, emergPhoneField;
    private JTextField streetField, zipField, hireDateField;
    private JComboBox<City> cityCombo;
    private JComboBox<State> stateCombo;
    private JComboBox<Division> divisionCombo;
    private JComboBox<JobTitle> jobTitleCombo;

    private AddressDAO addressDAO = new AddressDAO();
    private DivisionDAO divisionDAO = new DivisionDAO();
    private JobTitleDAO jobTitleDAO = new JobTitleDAO();
    private EmployeeService employeeService = new EmployeeService();

    public EmployeeDetailPanel(Employee employee, boolean editable, MainFrame mainFrame) {
        this.employee = employee;
        this.editable = editable;
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        initComponents();
        loadData();
    }

    private void initComponents() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 8, 5, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;

        // Employee ID (always read-only)
        addLabel(formPanel, gbc, "Employee ID:", 0, row);
        JTextField empIDField = new JTextField(String.valueOf(employee.getEmpID()), 15);
        empIDField.setEditable(false);
        addField(formPanel, gbc, empIDField, 1, row++);

        addLabel(formPanel, gbc, "First Name:", 0, row);
        firstNameField = new JTextField(employee.getFirstName(), 15);
        firstNameField.setEditable(editable);
        addField(formPanel, gbc, firstNameField, 1, row++);

        addLabel(formPanel, gbc, "Last Name:", 0, row);
        lastNameField = new JTextField(employee.getLastName(), 15);
        lastNameField.setEditable(editable);
        addField(formPanel, gbc, lastNameField, 1, row++);

        addLabel(formPanel, gbc, "SSN:", 0, row);
        ssnField = new JTextField(employee.getSSN(), 15);
        ssnField.setEditable(editable);
        addField(formPanel, gbc, ssnField, 1, row++);

        addLabel(formPanel, gbc, "DOB (yyyy-MM-dd):", 0, row);
        dobField = new JTextField(employee.getDOB() != null ? employee.getDOB().toString() : "", 15);
        dobField.setEditable(editable);
        addField(formPanel, gbc, dobField, 1, row++);

        addLabel(formPanel, gbc, "Email:", 0, row);
        emailField = new JTextField(employee.getEmail(), 15);
        emailField.setEditable(editable);
        addField(formPanel, gbc, emailField, 1, row++);

        addLabel(formPanel, gbc, "Phone:", 0, row);
        phoneField = new JTextField(employee.getPhone(), 15);
        phoneField.setEditable(editable);
        addField(formPanel, gbc, phoneField, 1, row++);

        addLabel(formPanel, gbc, "Emergency Contact:", 0, row);
        emergContactField = new JTextField(employee.getEmergencyContactName(), 15);
        emergContactField.setEditable(editable);
        addField(formPanel, gbc, emergContactField, 1, row++);

        addLabel(formPanel, gbc, "Emergency Phone:", 0, row);
        emergPhoneField = new JTextField(employee.getEmergencyContactPhone(), 15);
        emergPhoneField.setEditable(editable);
        addField(formPanel, gbc, emergPhoneField, 1, row++);

        addLabel(formPanel, gbc, "Hire Date (yyyy-MM-dd):", 0, row);
        hireDateField = new JTextField(employee.getHireDate() != null ? employee.getHireDate().toString() : "", 15);
        hireDateField.setEditable(editable);
        addField(formPanel, gbc, hireDateField, 1, row++);

        // Address section
        addLabel(formPanel, gbc, "--- Address ---", 0, row);
        row++;

        Address addr = addressDAO.getByID(employee.getAddressID());
        addLabel(formPanel, gbc, "Street:", 0, row);
        streetField = new JTextField(addr != null ? addr.getStreet() : "", 15);
        streetField.setEditable(editable);
        addField(formPanel, gbc, streetField, 1, row++);

        addLabel(formPanel, gbc, "City:", 0, row);
        List<City> cities = addressDAO.getAllCities();
        cityCombo = new JComboBox<>(cities.toArray(new City[0]));
        cityCombo.setEnabled(editable);
        if (addr != null) {
            for (int i = 0; i < cities.size(); i++) {
                if (cities.get(i).getCityID() == addr.getCityID()) {
                    cityCombo.setSelectedIndex(i);
                    break;
                }
            }
        }
        addField(formPanel, gbc, cityCombo, 1, row++);

        addLabel(formPanel, gbc, "State:", 0, row);
        List<State> states = addressDAO.getAllStates();
        stateCombo = new JComboBox<>(states.toArray(new State[0]));
        stateCombo.setEnabled(editable);
        if (addr != null) {
            for (int i = 0; i < states.size(); i++) {
                if (states.get(i).getStateID() == addr.getStateID()) {
                    stateCombo.setSelectedIndex(i);
                    break;
                }
            }
        }
        addField(formPanel, gbc, stateCombo, 1, row++);

        addLabel(formPanel, gbc, "Zip:", 0, row);
        zipField = new JTextField(addr != null ? addr.getZip() : "", 15);
        zipField.setEditable(editable);
        addField(formPanel, gbc, zipField, 1, row++);

        // Division and Job Title
        addLabel(formPanel, gbc, "Division:", 0, row);
        List<Division> divisions = divisionDAO.getAllDivisions();
        divisionCombo = new JComboBox<>(divisions.toArray(new Division[0]));
        divisionCombo.setEnabled(editable);
        int empDivID = divisionDAO.getDivisionIDForEmployee(employee.getEmpID());
        for (int i = 0; i < divisions.size(); i++) {
            if (divisions.get(i).getDivID() == empDivID) {
                divisionCombo.setSelectedIndex(i);
                break;
            }
        }
        addField(formPanel, gbc, divisionCombo, 1, row++);

        addLabel(formPanel, gbc, "Job Title:", 0, row);
        List<JobTitle> jobTitles = jobTitleDAO.getAllJobTitles();
        jobTitleCombo = new JComboBox<>(jobTitles.toArray(new JobTitle[0]));
        jobTitleCombo.setEnabled(editable);
        int empJobID = jobTitleDAO.getJobTitleIDForEmployee(employee.getEmpID());
        for (int i = 0; i < jobTitles.size(); i++) {
            if (jobTitles.get(i).getJobTitleID() == empJobID) {
                jobTitleCombo.setSelectedIndex(i);
                break;
            }
        }
        addField(formPanel, gbc, jobTitleCombo, 1, row++);

        JScrollPane scrollPane = new JScrollPane(formPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton backBtn = new JButton("Back to Search");
        backBtn.addActionListener(e -> mainFrame.showPanel("search", new EmployeeSearchPanel(mainFrame)));
        buttonPanel.add(backBtn);

        if (editable) {
            JButton saveBtn = new JButton("Save Changes");
            saveBtn.addActionListener(e -> saveEmployee());
            buttonPanel.add(saveBtn);
        }
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addLabel(JPanel panel, GridBagConstraints gbc, String text, int x, int y) {
        gbc.gridx = x; gbc.gridy = y;
        panel.add(new JLabel(text), gbc);
    }

    private void addField(JPanel panel, GridBagConstraints gbc, JComponent comp, int x, int y) {
        gbc.gridx = x; gbc.gridy = y;
        panel.add(comp, gbc);
    }

    private void loadData() {
        // Data already loaded in initComponents
    }

    private void saveEmployee() {
        try {
            employee.setFirstName(firstNameField.getText().trim());
            employee.setLastName(lastNameField.getText().trim());
            employee.setSSN(ssnField.getText().trim());
            employee.setDOB(java.sql.Date.valueOf(dobField.getText().trim()));
            employee.setEmail(emailField.getText().trim());
            employee.setPhone(phoneField.getText().trim());
            employee.setEmergencyContactName(emergContactField.getText().trim());
            employee.setEmergencyContactPhone(emergPhoneField.getText().trim());
            employee.setHireDate(java.sql.Date.valueOf(hireDateField.getText().trim()));

            // Update address
            Address addr = addressDAO.getByID(employee.getAddressID());
            if (addr != null) {
                addr.setStreet(streetField.getText().trim());
                addr.setCityID(((City) cityCombo.getSelectedItem()).getCityID());
                addr.setStateID(((State) stateCombo.getSelectedItem()).getStateID());
                addr.setZip(zipField.getText().trim());
                addressDAO.updateAddress(addr);
            }

            // Update division and job title
            Division selDiv = (Division) divisionCombo.getSelectedItem();
            JobTitle selJob = (JobTitle) jobTitleCombo.getSelectedItem();
            divisionDAO.setEmployeeDivision(employee.getEmpID(), selDiv.getDivID());
            jobTitleDAO.setEmployeeJobTitle(employee.getEmpID(), selJob.getJobTitleID());

            if (employeeService.updateEmployee(employee)) {
                JOptionPane.showMessageDialog(this, "Employee updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update employee.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
