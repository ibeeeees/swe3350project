package com.companyz.ems.ui;

import com.companyz.ems.dao.AddressDAO;
import com.companyz.ems.dao.DivisionDAO;
import com.companyz.ems.dao.JobTitleDAO;
import com.companyz.ems.model.*;
import com.companyz.ems.service.EmployeeService;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AddEmployeePanel extends JPanel {
    private MainFrame mainFrame;
    private JTextField firstNameField, lastNameField, ssnField, dobField;
    private JTextField emailField, phoneField, emergContactField, emergPhoneField;
    private JTextField streetField, zipField, hireDateField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<City> cityCombo;
    private JComboBox<State> stateCombo;
    private JComboBox<Division> divisionCombo;
    private JComboBox<JobTitle> jobTitleCombo;

    private AddressDAO addressDAO = new AddressDAO();
    private DivisionDAO divisionDAO = new DivisionDAO();
    private JobTitleDAO jobTitleDAO = new JobTitleDAO();
    private EmployeeService employeeService = new EmployeeService();

    public AddEmployeePanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setBackground(Theme.CONTENT_BG);
        initComponents();
    }

    private void initComponents() {
        JPanel wrapper = Theme.createContentWrapper();

        // Header
        JLabel header = new JLabel("Add New Employee");
        header.setFont(Theme.FONT_TITLE);
        header.setForeground(Theme.PRIMARY);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));
        wrapper.add(header, BorderLayout.NORTH);

        // Form card
        JPanel card = Theme.createCard();
        card.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 12, 6, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;

        // Personal
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 4;
        card.add(Theme.createSectionHeader("Personal Information"), gbc);
        gbc.gridwidth = 1; row++;

        firstNameField = addFormRow(card, gbc, "First Name *", 0, row);
        lastNameField = addFormRow(card, gbc, "Last Name *", 2, row);
        row++;

        ssnField = addFormRow(card, gbc, "SSN * (XXX-XX-XXXX)", 0, row);
        dobField = addFormRow(card, gbc, "DOB * (yyyy-MM-dd)", 2, row);
        row++;

        emailField = addFormRow(card, gbc, "Email", 0, row);
        phoneField = addFormRow(card, gbc, "Phone", 2, row);
        row++;

        emergContactField = addFormRow(card, gbc, "Emergency Contact", 0, row);
        emergPhoneField = addFormRow(card, gbc, "Emergency Phone", 2, row);
        row++;

        hireDateField = addFormRow(card, gbc, "Hire Date * (yyyy-MM-dd)", 0, row);
        row++;

        // Address
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 4;
        card.add(Theme.createSectionHeader("Address"), gbc);
        gbc.gridwidth = 1; row++;

        streetField = addFormRow(card, gbc, "Street *", 0, row);
        zipField = addFormRow(card, gbc, "Zip Code *", 2, row);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        card.add(Theme.createFormLabel("City *"), gbc);
        gbc.gridx = 1;
        List<City> cities = addressDAO.getAllCities();
        cityCombo = new JComboBox<>(cities.toArray(new City[0]));
        cityCombo.setFont(Theme.FONT_BODY);
        card.add(cityCombo, gbc);

        gbc.gridx = 2;
        card.add(Theme.createFormLabel("State *"), gbc);
        gbc.gridx = 3;
        List<State> states = addressDAO.getAllStates();
        stateCombo = new JComboBox<>(states.toArray(new State[0]));
        stateCombo.setFont(Theme.FONT_BODY);
        card.add(stateCombo, gbc);
        row++;

        // Employment
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 4;
        card.add(Theme.createSectionHeader("Employment"), gbc);
        gbc.gridwidth = 1; row++;

        gbc.gridx = 0; gbc.gridy = row;
        card.add(Theme.createFormLabel("Division *"), gbc);
        gbc.gridx = 1;
        List<Division> divisions = divisionDAO.getAllDivisions();
        divisionCombo = new JComboBox<>(divisions.toArray(new Division[0]));
        divisionCombo.setFont(Theme.FONT_BODY);
        card.add(divisionCombo, gbc);

        gbc.gridx = 2;
        card.add(Theme.createFormLabel("Job Title *"), gbc);
        gbc.gridx = 3;
        List<JobTitle> jobTitles = jobTitleDAO.getAllJobTitles();
        jobTitleCombo = new JComboBox<>(jobTitles.toArray(new JobTitle[0]));
        jobTitleCombo.setFont(Theme.FONT_BODY);
        card.add(jobTitleCombo, gbc);
        row++;

        // Account
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 4;
        card.add(Theme.createSectionHeader("Login Account"), gbc);
        gbc.gridwidth = 1; row++;

        usernameField = addFormRow(card, gbc, "Username", 0, row);
        gbc.gridx = 2; gbc.gridy = row;
        card.add(Theme.createFormLabel("Password"), gbc);
        gbc.gridx = 3;
        passwordField = Theme.createStyledPasswordField(18);
        card.add(passwordField, gbc);

        JScrollPane scrollPane = new JScrollPane(card);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(Theme.CARD_BG);
        wrapper.add(scrollPane, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 12));
        buttonPanel.setOpaque(false);
        JButton clearBtn = Theme.createSecondaryButton("Clear Form");
        clearBtn.addActionListener(e -> clearFields());
        buttonPanel.add(clearBtn);
        JButton addBtn = Theme.createPrimaryButton("Add Employee");
        addBtn.addActionListener(e -> addEmployee());
        buttonPanel.add(addBtn);
        wrapper.add(buttonPanel, BorderLayout.SOUTH);

        add(wrapper, BorderLayout.CENTER);
    }

    private JTextField addFormRow(JPanel panel, GridBagConstraints gbc, String label, int col, int row) {
        gbc.gridx = col; gbc.gridy = row;
        panel.add(Theme.createFormLabel(label), gbc);
        gbc.gridx = col + 1;
        JTextField field = Theme.createStyledTextField(18);
        panel.add(field, gbc);
        return field;
    }

    private void addEmployee() {
        if (firstNameField.getText().trim().isEmpty() || lastNameField.getText().trim().isEmpty()
                || ssnField.getText().trim().isEmpty() || dobField.getText().trim().isEmpty()
                || hireDateField.getText().trim().isEmpty() || streetField.getText().trim().isEmpty()
                || zipField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields (marked with *).",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Employee emp = new Employee();
            emp.setFirstName(firstNameField.getText().trim());
            emp.setLastName(lastNameField.getText().trim());
            emp.setSSN(ssnField.getText().trim());
            emp.setDOB(java.sql.Date.valueOf(dobField.getText().trim()));
            emp.setEmail(emailField.getText().trim());
            emp.setPhone(phoneField.getText().trim());
            emp.setEmergencyContactName(emergContactField.getText().trim());
            emp.setEmergencyContactPhone(emergPhoneField.getText().trim());
            emp.setHireDate(java.sql.Date.valueOf(hireDateField.getText().trim()));

            Address addr = new Address();
            addr.setStreet(streetField.getText().trim());
            addr.setCityID(((City) cityCombo.getSelectedItem()).getCityID());
            addr.setStateID(((State) stateCombo.getSelectedItem()).getStateID());
            addr.setZip(zipField.getText().trim());

            Division selDiv = (Division) divisionCombo.getSelectedItem();
            JobTitle selJob = (JobTitle) jobTitleCombo.getSelectedItem();
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            boolean success = employeeService.addNewEmployee(emp, addr, selDiv.getDivID(),
                    selJob.getJobTitleID(), username, password);

            if (success) {
                JOptionPane.showMessageDialog(this,
                        "Employee added successfully!\nEmployee ID: " + emp.getEmpID(),
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add employee.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Use yyyy-MM-dd.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        firstNameField.setText(""); lastNameField.setText(""); ssnField.setText(""); dobField.setText("");
        emailField.setText(""); phoneField.setText(""); emergContactField.setText(""); emergPhoneField.setText("");
        hireDateField.setText(""); streetField.setText(""); zipField.setText("");
        usernameField.setText(""); passwordField.setText("");
    }
}
