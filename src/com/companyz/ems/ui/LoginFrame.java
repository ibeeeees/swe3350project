package com.companyz.ems.ui;

import com.companyz.ems.model.User;
import com.companyz.ems.service.AuthService;
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel errorLabel;
    private AuthService authService;

    public LoginFrame() {
        authService = new AuthService();
        setTitle("Company Z - Employee Management System");
        setSize(900, 560);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents();
    }

    private void initComponents() {
        JPanel container = new JPanel(new GridLayout(1, 2));

        // Left panel - branding
        JPanel leftPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, Theme.PRIMARY, getWidth(), getHeight(), new Color(0, 168, 150));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        leftPanel.setPreferredSize(new Dimension(450, 560));

        GridBagConstraints lgbc = new GridBagConstraints();
        lgbc.gridx = 0; lgbc.gridy = 0;
        lgbc.insets = new Insets(0, 40, 5, 40);
        lgbc.anchor = GridBagConstraints.WEST;

        JLabel brandLabel = new JLabel("COMPANY Z");
        brandLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        brandLabel.setForeground(Color.WHITE);
        leftPanel.add(brandLabel, lgbc);

        lgbc.gridy = 1;
        lgbc.insets = new Insets(5, 40, 10, 40);
        JLabel tagline = new JLabel("Employee Management System");
        tagline.setFont(new Font("SansSerif", Font.PLAIN, 16));
        tagline.setForeground(new Color(200, 230, 225));
        leftPanel.add(tagline, lgbc);

        lgbc.gridy = 2;
        lgbc.insets = new Insets(30, 40, 5, 40);
        JLabel desc = new JLabel("<html><body style='width:280px; color:#B0D4CF; font-size:11px; line-height:1.6;'>"
                + "Manage employee data, payroll, and reports with role-based security. "
                + "HR administrators have full CRUD access while employees can view their personal data and pay stubs."
                + "</body></html>");
        leftPanel.add(desc, lgbc);

        lgbc.gridy = 3;
        lgbc.insets = new Insets(40, 40, 5, 40);
        JLabel secureLabel = new JLabel("Secure   |   Fast   |   Reliable");
        secureLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        secureLabel.setForeground(new Color(150, 210, 200));
        leftPanel.add(secureLabel, lgbc);

        container.add(leftPanel);

        // Right panel - login form
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 50, 4, 50);

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 50, 2, 50);
        JLabel welcomeBack = new JLabel("Welcome Back");
        welcomeBack.setFont(Theme.FONT_TITLE);
        welcomeBack.setForeground(Theme.PRIMARY);
        rightPanel.add(welcomeBack, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(2, 50, 30, 50);
        JLabel subtext = new JLabel("Sign in to your account");
        subtext.setFont(Theme.FONT_BODY);
        subtext.setForeground(Theme.TEXT_SECONDARY);
        rightPanel.add(subtext, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(4, 50, 4, 50);
        JLabel userLabel = Theme.createFormLabel("Username");
        rightPanel.add(userLabel, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(2, 50, 12, 50);
        usernameField = Theme.createStyledTextField(20);
        rightPanel.add(usernameField, gbc);

        gbc.gridy = 4;
        gbc.insets = new Insets(4, 50, 4, 50);
        JLabel passLabel = Theme.createFormLabel("Password");
        rightPanel.add(passLabel, gbc);

        gbc.gridy = 5;
        gbc.insets = new Insets(2, 50, 20, 50);
        passwordField = Theme.createStyledPasswordField(20);
        rightPanel.add(passwordField, gbc);

        gbc.gridy = 6;
        gbc.insets = new Insets(8, 50, 8, 50);
        JButton loginButton = Theme.createPrimaryButton("Sign In");
        loginButton.setPreferredSize(new Dimension(0, 44));
        rightPanel.add(loginButton, gbc);

        gbc.gridy = 7;
        gbc.insets = new Insets(12, 50, 4, 50);
        errorLabel = new JLabel(" ", SwingConstants.CENTER);
        errorLabel.setForeground(Theme.DANGER);
        errorLabel.setFont(Theme.FONT_SMALL);
        rightPanel.add(errorLabel, gbc);

        container.add(rightPanel);

        loginButton.addActionListener(e -> doLogin());
        passwordField.addActionListener(e -> doLogin());
        usernameField.addActionListener(e -> passwordField.requestFocus());

        setContentPane(container);
    }

    private void doLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Please enter username and password.");
            return;
        }

        User user = authService.login(username, password);
        if (user != null) {
            dispose();
            SwingUtilities.invokeLater(() -> {
                MainFrame mainFrame = new MainFrame();
                mainFrame.setVisible(true);
            });
        } else {
            errorLabel.setText("Invalid username or password. Please try again.");
            passwordField.setText("");
        }
    }
}
