package com.companyz.ems.ui;

import com.companyz.ems.service.AuthService;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JPanel contentArea;
    private CardLayout cardLayout;

    public MainFrame() {
        String role = AuthService.isAdmin() ? "HR Admin" : "Employee";
        setTitle("Company Z - EMS - " + role + " (" + AuthService.getCurrentUser().getUsername() + ")");
        setSize(1050, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        cardLayout = new CardLayout();
        contentArea = new JPanel(cardLayout);

        JPanel welcomePanel = new JPanel(new GridBagLayout());
        JLabel welcomeLabel = new JLabel("Welcome to Company Z Employee Management System");
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        welcomePanel.add(welcomeLabel);
        contentArea.add(welcomePanel, "welcome");

        setJMenuBar(createMenuBar());
        add(contentArea, BorderLayout.CENTER);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        boolean isAdmin = AuthService.isAdmin();

        // Employee menu
        JMenu employeeMenu = new JMenu("Employee");
        JMenuItem searchItem = new JMenuItem("Search");
        searchItem.addActionListener(e -> showPanel("search", new EmployeeSearchPanel(this)));
        employeeMenu.add(searchItem);

        if (isAdmin) {
            JMenuItem addItem = new JMenuItem("Add New Employee");
            addItem.addActionListener(e -> showPanel("add", new AddEmployeePanel(this)));
            employeeMenu.add(addItem);
        }
        menuBar.add(employeeMenu);

        // Payroll menu
        JMenu payrollMenu = new JMenu("Payroll");
        if (!isAdmin) {
            JMenuItem myPayStubs = new JMenuItem("My Pay Stubs");
            myPayStubs.addActionListener(e -> {
                int empID = AuthService.getCurrentUser().getEmpID();
                showPanel("paystubs", new ReportPanel("paystubs", empID));
            });
            payrollMenu.add(myPayStubs);
        }
        if (isAdmin) {
            JMenuItem salaryUpdate = new JMenuItem("Salary Update");
            salaryUpdate.addActionListener(e -> showPanel("salary", new SalaryUpdatePanel()));
            payrollMenu.add(salaryUpdate);
        }
        menuBar.add(payrollMenu);

        // Reports menu
        JMenu reportsMenu = new JMenu("Reports");
        if (isAdmin) {
            JMenuItem payByTitle = new JMenuItem("Total Pay by Job Title");
            payByTitle.addActionListener(e -> showPanel("payByTitle", new ReportPanel("payByJobTitle", 0)));
            reportsMenu.add(payByTitle);

            JMenuItem payByDiv = new JMenuItem("Total Pay by Division");
            payByDiv.addActionListener(e -> showPanel("payByDiv", new ReportPanel("payByDivision", 0)));
            reportsMenu.add(payByDiv);

            JMenuItem newHires = new JMenuItem("New Hires Report");
            newHires.addActionListener(e -> showPanel("newHires", new ReportPanel("newHires", 0)));
            reportsMenu.add(newHires);
        }
        if (!isAdmin) {
            JMenuItem myPayStubs = new JMenuItem("My Pay Statement History");
            myPayStubs.addActionListener(e -> {
                int empID = AuthService.getCurrentUser().getEmpID();
                showPanel("paystubs", new ReportPanel("paystubs", empID));
            });
            reportsMenu.add(myPayStubs);
        }
        menuBar.add(reportsMenu);

        // Account menu
        JMenu accountMenu = new JMenu("Account");
        JMenuItem logoutItem = new JMenuItem("Logout");
        logoutItem.addActionListener(e -> {
            new AuthService().logout();
            dispose();
            SwingUtilities.invokeLater(() -> {
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
            });
        });
        accountMenu.add(logoutItem);

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        accountMenu.add(exitItem);
        menuBar.add(accountMenu);

        return menuBar;
    }

    public void showPanel(String name, JPanel panel) {
        contentArea.add(panel, name);
        cardLayout.show(contentArea, name);
    }
}
