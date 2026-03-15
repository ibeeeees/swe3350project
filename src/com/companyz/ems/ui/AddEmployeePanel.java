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
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        initComponents();
    }

    private void initComponents() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 8, 4, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;

        JLabel header = new JLabel("Add New Employee");
        header.setFont(new Font("SansSerif", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        formPanel.add(header, gbc);
        gbc.gridwidth = 1;
        row++;

        addLabel(formPanel, gbc, "First Name*:", 0, row);
        firstNameField = new JTextField(15);
        addField(formPanel, gbc, firstNameField, 1, row++);

        addLabel(formPanel, gbc, "Last Name*:", 0, row);
        lastNameField = new JTextField(15);
        addField(formPanel, gbc, lastNameField, 1, row++);

        addLabel(formPanel, gbc, "SSN* (XXX-XX-XXXX):", 0, row);
        ssnField = new JTextField(15);
        addField(formPanel, gbc, ssnField, 1, row++);

        addLabel(formPanel, gbc, "DOB* (yyyy-MM-dd):", 0, row);
        dobField = new JTextField(15);
        addField(formPanel, gbc, dobField, 1, row++);

        addLabel(formPanel, gbc, "Email:", 0, row);
        emailField = new JTextField(15);
        addField(formPanel, gbc, emailField, 1, row++);

        addLabel(formPanel, gbc, "Phone:", 0, row);
        phoneField = new JTextField(15);
        addField(formPanel, gbc, phoneField, 1, row++);

        addLabel(formPanel, gbc, "Emergency Contact:", 0, row);
        emergContactField = new JTextField(15);
        addField(formPanel, gbc, emergContactField, 1, row++);

        addLabel(formPanel, gbc, "Emergency Phone:", 0, row);
        emergPhoneField = new JTextField(15);
        addField(formPanel, gbc, emergPhoneField, 1, row++);

        addLabel(formPanel, gbc, "Hire Date* (yyyy-MM-dd):", 0, row);
        hireDateField = new JTextField(15);
        addField(formPanel, gbc, hireDateField, 1, row++);

        addLabel(formPanel, gbc, "--- Address ---", 0, row);
        row++;

        addLabel(formPanel, gbc, "Street*:", 0, row);
        streetField = new JTextField(15);
        addField(formPanel, gbc, streetField, 1, row++);

        addLabel(formPanel, gbc, "City*:", 0, row);
        List<City> cities = addressDAO.getAllCities();
        cityCombo = new JComboBox<>(cities.toArray(new City[0]));
        addField(formPanel, gbc, cityCombo, 1, row++);

        addLabel(formPanel, gbc, "State*:", 0, row);
        List<State> states = addressDAO.getAllStates();
        stateCombo = new JComboBox<>(states.toArray(new State[0]));
        addField(formPanel, gbc, stateCombo, 1, row++);

        addLabel(formPanel, gbc, "Zip*:", 0, row);
        zipField = new JTextField(15);
        addField(formPanel, gbc, zipField, 1, row++);

        addLabel(formPanel, gbc, "Division*:", 0, row);
        List<Division> divisions = divisionDAO.getAllDivisions();
        divisionCombo = new JComboBox<>(divisions.toArray(new Division[0]));
        addField(formPanel, gbc, divisionCombo, 1, row++);

        addLabel(formPanel, gbc, "Job Title*:", 0, row);
        List<JobTitle> jobTitles = jobTitleDAO.getAllJobTitles();
        jobTitleCombo = new JComboBox<>(jobTitles.toArray(new JobTitle[0]));
        addField(formPanel, gbc, jobTitleCombo, 1, row++);

        addLabel(formPanel, gbc, "--- Login Account ---", 0, row);
        row++;

        addLabel(formPanel, gbc, "Username:", 0, row);
        usernameField = new JTextField(15);
        addField(formPanel, gbc, usernameField, 1, row++);

        addLabel(formPanel, gbc, "Password:", 0, row);
        passwordField = new JPasswordField(15);
        addField(formPanel, gbc, passwordField, 1, row++);

        JScrollPane scrollPane = new JScrollPane(formPanel);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton addBtn = new JButton("Add Employee");
        addBtn.addActionListener(e -> addEmployee());
        buttonPanel.add(addBtn);

        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(e -> mainFrame.showPanel("search", new EmployeeSearchPanel(mainFrame)));
        buttonPanel.add(backBtn);
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

    private void addEmployee() {
        // Validate required fields
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
                        "Employee added successfully! (ID: " + emp.getEmpID() + ")",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add employee.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Use yyyy-MM-dd.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        ssnField.setText("");
        dobField.setText("");
        emailField.setText("");
        phoneField.setText("");
        emergContactField.setText("");
        emergPhoneField.setText("");
        hireDateField.setText("");
        streetField.setText("");
        zipField.setText("");
        usernameField.setText("");
        passwordField.setText("");
    }
}
