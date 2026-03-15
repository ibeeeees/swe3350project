package com.companyz.ems.ui;

import com.companyz.ems.service.AuthService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame {
    private JPanel contentArea;
    private CardLayout cardLayout;
    private JPanel sidebarPanel;
    private JLabel activeNavItem = null;

    public MainFrame() {
        String role = AuthService.isAdmin() ? "HR Admin" : "Employee";
        setTitle("Company Z - EMS");
        setSize(1200, 780);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(1000, 650));
        initComponents();
    }

    private void initComponents() {
        JPanel mainContainer = new JPanel(new BorderLayout());

        // Sidebar
        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(Theme.SIDEBAR_BG);
        sidebarPanel.setPreferredSize(new Dimension(280, 0));
        sidebarPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // Brand header
        JPanel brandPanel = new JPanel(new BorderLayout());
        brandPanel.setBackground(Theme.PRIMARY);
        brandPanel.setPreferredSize(new Dimension(280, 70));
        brandPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        brandPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        JLabel brandLabel = new JLabel("COMPANY Z");
        brandLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        brandLabel.setForeground(Color.WHITE);
        brandPanel.add(brandLabel, BorderLayout.WEST);
        sidebarPanel.add(brandPanel);

        // User info
        JPanel userPanel = new JPanel(new BorderLayout());
        userPanel.setBackground(Theme.PRIMARY_LIGHT);
        userPanel.setPreferredSize(new Dimension(280, 60));
        userPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        userPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel userInfo = new JPanel(new GridLayout(2, 1));
        userInfo.setOpaque(false);
        JLabel userName = new JLabel(AuthService.getCurrentUser().getUsername());
        userName.setFont(Theme.FONT_BODY_BOLD);
        userName.setForeground(Color.WHITE);
        JLabel userRole = new JLabel(AuthService.isAdmin() ? "HR Administrator" : "Employee");
        userRole.setFont(Theme.FONT_SMALL);
        userRole.setForeground(Theme.TEXT_LIGHT);
        userInfo.add(userName);
        userInfo.add(userRole);
        userPanel.add(userInfo, BorderLayout.CENTER);
        sidebarPanel.add(userPanel);

        sidebarPanel.add(Box.createVerticalStrut(15));

        boolean isAdmin = AuthService.isAdmin();

        // Nav section: Employee
        addNavSection("EMPLOYEE");
        JLabel searchNav = addNavItem("Search Employees", "search");
        if (isAdmin) {
            addNavItem("Add New Employee", "add");
        }

        sidebarPanel.add(Box.createVerticalStrut(8));

        // Nav section: Payroll
        addNavSection("PAYROLL");
        if (!isAdmin) {
            addNavItem("My Pay Stubs", "paystubs");
        }
        if (isAdmin) {
            addNavItem("Salary Update", "salary");
        }

        sidebarPanel.add(Box.createVerticalStrut(8));

        // Nav section: Reports
        addNavSection("REPORTS");
        if (isAdmin) {
            addNavItem("Pay by Job Title", "payByJobTitle");
            addNavItem("Pay by Division", "payByDivision");
            addNavItem("New Hires", "newHires");
        }
        if (!isAdmin) {
            addNavItem("Pay Statement History", "paystubsReport");
        }

        sidebarPanel.add(Box.createVerticalGlue());

        // Logout button at bottom
        sidebarPanel.add(Box.createVerticalStrut(10));
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        logoutPanel.setOpaque(false);
        logoutPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        JButton logoutBtn = Theme.createDangerButton("Logout");
        logoutBtn.setPreferredSize(new Dimension(240, 38));
        logoutBtn.addActionListener(e -> {
            new AuthService().logout();
            dispose();
            SwingUtilities.invokeLater(() -> {
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
            });
        });
        logoutPanel.add(logoutBtn);
        sidebarPanel.add(logoutPanel);
        sidebarPanel.add(Box.createVerticalStrut(15));

        // Content area
        cardLayout = new CardLayout();
        contentArea = new JPanel(cardLayout);
        contentArea.setBackground(Theme.CONTENT_BG);

        // Welcome panel
        JPanel welcomePanel = createWelcomePanel();
        contentArea.add(welcomePanel, "welcome");

        mainContainer.add(sidebarPanel, BorderLayout.WEST);
        mainContainer.add(contentArea, BorderLayout.CENTER);

        setContentPane(mainContainer);

        // Default highlight
        if (searchNav != null) {
            highlightNav(searchNav);
        }
    }

    private JPanel createWelcomePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Theme.CONTENT_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(5, 0, 5, 0);

        gbc.gridy = 0;
        JLabel icon = new JLabel("Z");
        icon.setFont(new Font("SansSerif", Font.BOLD, 72));
        icon.setForeground(Theme.ACCENT);
        icon.setHorizontalAlignment(SwingConstants.CENTER);
        icon.setOpaque(true);
        icon.setBackground(Theme.CARD_BG);
        icon.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Theme.BORDER_COLOR),
            BorderFactory.createEmptyBorder(15, 35, 15, 35)
        ));
        panel.add(icon, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(25, 0, 5, 0);
        JLabel welcome = new JLabel("Welcome to Company Z");
        welcome.setFont(Theme.FONT_TITLE);
        welcome.setForeground(Theme.PRIMARY);
        panel.add(welcome, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(5, 0, 5, 0);
        JLabel subtitle = new JLabel("Employee Management System");
        subtitle.setFont(Theme.FONT_SUBTITLE);
        subtitle.setForeground(Theme.TEXT_SECONDARY);
        panel.add(subtitle, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(15, 0, 5, 0);
        String roleText = AuthService.isAdmin()
            ? "You are logged in as an HR Administrator with full access."
            : "You are logged in as an Employee with view-only access.";
        JLabel roleLabel = new JLabel(roleText);
        roleLabel.setFont(Theme.FONT_BODY);
        roleLabel.setForeground(Theme.TEXT_SECONDARY);
        panel.add(roleLabel, gbc);

        gbc.gridy = 4;
        gbc.insets = new Insets(5, 0, 0, 0);
        JLabel hint = new JLabel("Use the sidebar to navigate between features.");
        hint.setFont(Theme.FONT_BODY);
        hint.setForeground(Theme.TEXT_SECONDARY);
        panel.add(hint, gbc);

        return panel;
    }

    private void addNavSection(String title) {
        JPanel section = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 4));
        section.setOpaque(false);
        section.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        JLabel label = new JLabel(title);
        label.setFont(Theme.FONT_SIDEBAR_HEADER);
        label.setForeground(Theme.TEXT_LIGHT);
        section.add(label);
        sidebarPanel.add(section);
    }

    private JLabel addNavItem(String text, String panelName) {
        JLabel item = new JLabel(text);
        item.setFont(Theme.FONT_SIDEBAR);
        item.setForeground(new Color(180, 190, 210));
        item.setOpaque(true);
        item.setBackground(Theme.SIDEBAR_BG);
        item.setPreferredSize(new Dimension(280, 42));
        item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        item.setBorder(BorderFactory.createEmptyBorder(0, 24, 0, 10));
        item.setCursor(new Cursor(Cursor.HAND_CURSOR));

        item.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (item != activeNavItem) {
                    item.setBackground(Theme.SIDEBAR_HOVER);
                }
            }
            public void mouseExited(MouseEvent e) {
                if (item != activeNavItem) {
                    item.setBackground(Theme.SIDEBAR_BG);
                }
            }
            public void mouseClicked(MouseEvent e) {
                highlightNav(item);
                navigateTo(panelName);
            }
        });

        sidebarPanel.add(item);
        return item;
    }

    private void highlightNav(JLabel item) {
        if (activeNavItem != null) {
            activeNavItem.setBackground(Theme.SIDEBAR_BG);
            activeNavItem.setForeground(new Color(180, 190, 210));
            activeNavItem.setBorder(BorderFactory.createEmptyBorder(0, 24, 0, 10));
        }
        activeNavItem = item;
        item.setBackground(Theme.SIDEBAR_ACTIVE);
        item.setForeground(Color.WHITE);
        item.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 4, 0, 0, Color.WHITE),
            BorderFactory.createEmptyBorder(0, 20, 0, 10)
        ));
    }

    private void navigateTo(String panelName) {
        JPanel panel;
        int empID = AuthService.getCurrentUser().getEmpID();
        switch (panelName) {
            case "search":
                panel = new EmployeeSearchPanel(this);
                break;
            case "add":
                panel = new AddEmployeePanel(this);
                break;
            case "salary":
                panel = new SalaryUpdatePanel();
                break;
            case "paystubs":
            case "paystubsReport":
                panel = new ReportPanel("paystubs", empID);
                break;
            case "payByJobTitle":
                panel = new ReportPanel("payByJobTitle", 0);
                break;
            case "payByDivision":
                panel = new ReportPanel("payByDivision", 0);
                break;
            case "newHires":
                panel = new ReportPanel("newHires", 0);
                break;
            default:
                return;
        }
        showPanel(panelName, panel);
    }

    public void showPanel(String name, JPanel panel) {
        contentArea.add(panel, name);
        cardLayout.show(contentArea, name);
    }
}
