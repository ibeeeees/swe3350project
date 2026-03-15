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
        setLayout(new BorderLayout());
        setBackground(Theme.CONTENT_BG);
        initComponents();
    }

    private void initComponents() {
        JPanel wrapper = Theme.createContentWrapper();

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));
        JLabel header = new JLabel(editable ? "Edit Employee" : "Employee Details");
        header.setFont(Theme.FONT_TITLE);
        header.setForeground(Theme.PRIMARY);
        headerPanel.add(header, BorderLayout.WEST);

        JLabel badge = new JLabel("  ID: " + employee.getEmpID() + "  ");
        badge.setFont(Theme.FONT_BODY_BOLD);
        badge.setForeground(Color.WHITE);
        badge.setOpaque(true);
        badge.setBackground(Theme.ACCENT);
        badge.setBorder(BorderFactory.createEmptyBorder(4, 12, 4, 12));
        headerPanel.add(badge, BorderLayout.EAST);
        wrapper.add(headerPanel, BorderLayout.NORTH);

        // Form in a card
        JPanel card = Theme.createCard();
        card.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 12, 6, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;

        // Personal info section
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 4;
        card.add(Theme.createSectionHeader("Personal Information"), gbc);
        gbc.gridwidth = 1; row++;

        firstNameField = addFormRow(card, gbc, "First Name", employee.getFirstName(), 0, row);
        lastNameField = addFormRow(card, gbc, "Last Name", employee.getLastName(), 2, row);
        row++;

        ssnField = addFormRow(card, gbc, "SSN", employee.getSSN(), 0, row);
        dobField = addFormRow(card, gbc, "Date of Birth", employee.getDOB() != null ? employee.getDOB().toString() : "", 2, row);
        row++;

        emailField = addFormRow(card, gbc, "Email", employee.getEmail(), 0, row);
        phoneField = addFormRow(card, gbc, "Phone", employee.getPhone(), 2, row);
        row++;

        emergContactField = addFormRow(card, gbc, "Emergency Contact", employee.getEmergencyContactName(), 0, row);
        emergPhoneField = addFormRow(card, gbc, "Emergency Phone", employee.getEmergencyContactPhone(), 2, row);
        row++;

        hireDateField = addFormRow(card, gbc, "Hire Date", employee.getHireDate() != null ? employee.getHireDate().toString() : "", 0, row);
        row++;

        // Address section
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 4;
        card.add(Theme.createSectionHeader("Address"), gbc);
        gbc.gridwidth = 1; row++;

        Address addr = addressDAO.getByID(employee.getAddressID());
        streetField = addFormRow(card, gbc, "Street", addr != null ? addr.getStreet() : "", 0, row);
        zipField = addFormRow(card, gbc, "Zip Code", addr != null ? addr.getZip() : "", 2, row);
        row++;

        // City combo
        gbc.gridx = 0; gbc.gridy = row;
        card.add(Theme.createFormLabel("City"), gbc);
        gbc.gridx = 1;
        List<City> cities = addressDAO.getAllCities();
        cityCombo = new JComboBox<>(cities.toArray(new City[0]));
        cityCombo.setFont(Theme.FONT_BODY);
        cityCombo.setEnabled(editable);
        if (addr != null) {
            for (int i = 0; i < cities.size(); i++) {
                if (cities.get(i).getCityID() == addr.getCityID()) { cityCombo.setSelectedIndex(i); break; }
            }
        }
        card.add(cityCombo, gbc);

        // State combo
        gbc.gridx = 2;
        card.add(Theme.createFormLabel("State"), gbc);
        gbc.gridx = 3;
        List<State> states = addressDAO.getAllStates();
        stateCombo = new JComboBox<>(states.toArray(new State[0]));
        stateCombo.setFont(Theme.FONT_BODY);
        stateCombo.setEnabled(editable);
        if (addr != null) {
            for (int i = 0; i < states.size(); i++) {
                if (states.get(i).getStateID() == addr.getStateID()) { stateCombo.setSelectedIndex(i); break; }
            }
        }
        card.add(stateCombo, gbc);
        row++;

        // Employment section
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 4;
        card.add(Theme.createSectionHeader("Employment"), gbc);
        gbc.gridwidth = 1; row++;

        gbc.gridx = 0; gbc.gridy = row;
        card.add(Theme.createFormLabel("Division"), gbc);
        gbc.gridx = 1;
        List<Division> divisions = divisionDAO.getAllDivisions();
        divisionCombo = new JComboBox<>(divisions.toArray(new Division[0]));
        divisionCombo.setFont(Theme.FONT_BODY);
        divisionCombo.setEnabled(editable);
        int empDivID = divisionDAO.getDivisionIDForEmployee(employee.getEmpID());
        for (int i = 0; i < divisions.size(); i++) {
            if (divisions.get(i).getDivID() == empDivID) { divisionCombo.setSelectedIndex(i); break; }
        }
        card.add(divisionCombo, gbc);

        gbc.gridx = 2;
        card.add(Theme.createFormLabel("Job Title"), gbc);
        gbc.gridx = 3;
        List<JobTitle> jobTitles = jobTitleDAO.getAllJobTitles();
        jobTitleCombo = new JComboBox<>(jobTitles.toArray(new JobTitle[0]));
        jobTitleCombo.setFont(Theme.FONT_BODY);
        jobTitleCombo.setEnabled(editable);
        int empJobID = jobTitleDAO.getJobTitleIDForEmployee(employee.getEmpID());
        for (int i = 0; i < jobTitles.size(); i++) {
            if (jobTitles.get(i).getJobTitleID() == empJobID) { jobTitleCombo.setSelectedIndex(i); break; }
        }
        card.add(jobTitleCombo, gbc);

        JScrollPane scrollPane = new JScrollPane(card);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(Theme.CARD_BG);
        wrapper.add(scrollPane, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 12));
        buttonPanel.setOpaque(false);
        JButton backBtn = Theme.createSecondaryButton("Back to Search");
        backBtn.addActionListener(e -> mainFrame.showPanel("search", new EmployeeSearchPanel(mainFrame)));
        buttonPanel.add(backBtn);

        if (editable) {
            JButton saveBtn = Theme.createPrimaryButton("Save Changes");
            saveBtn.addActionListener(e -> saveEmployee());
            buttonPanel.add(saveBtn);
        }
        wrapper.add(buttonPanel, BorderLayout.SOUTH);
        add(wrapper, BorderLayout.CENTER);
    }

    private JTextField addFormRow(JPanel panel, GridBagConstraints gbc, String label, String value, int col, int row) {
        gbc.gridx = col; gbc.gridy = row;
        panel.add(Theme.createFormLabel(label), gbc);
        gbc.gridx = col + 1;
        JTextField field = Theme.createStyledTextField(18);
        field.setText(value != null ? value : "");
        field.setEditable(editable);
        if (!editable) {
            field.setBackground(new Color(245, 245, 245));
        }
        panel.add(field, gbc);
        return field;
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

            Address addr = addressDAO.getByID(employee.getAddressID());
            if (addr != null) {
                addr.setStreet(streetField.getText().trim());
                addr.setCityID(((City) cityCombo.getSelectedItem()).getCityID());
                addr.setStateID(((State) stateCombo.getSelectedItem()).getStateID());
                addr.setZip(zipField.getText().trim());
                addressDAO.updateAddress(addr);
            }

            Division selDiv = (Division) divisionCombo.getSelectedItem();
            JobTitle selJob = (JobTitle) jobTitleCombo.getSelectedItem();
            divisionDAO.setEmployeeDivision(employee.getEmpID(), selDiv.getDivID());
            jobTitleDAO.setEmployeeJobTitle(employee.getEmpID(), selJob.getJobTitleID());

            if (employeeService.updateEmployee(employee)) {
                JOptionPane.showMessageDialog(this, "Employee updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update employee.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
