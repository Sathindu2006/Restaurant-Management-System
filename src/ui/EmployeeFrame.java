package ui;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import DBConnection.DBConnection;

public class EmployeeFrame extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JFrame home;

    public EmployeeFrame(JFrame home) {
        this.home = home;

        setTitle("Employee Login");
        setSize(500, 350); // frame size
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Background panel
        JPanel backgroundPanel = new JPanel() {
            private Image backgroundImage = new ImageIcon("resources/vitaly-gariev-9CR19q291ac-unsplash.jpg").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        backgroundPanel.setLayout(new GridLayout(3, 2, 10, 10));
        setContentPane(backgroundPanel);

        // --- Labels ---
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblUsername.setForeground(Color.WHITE);
        backgroundPanel.add(lblUsername);

        txtUsername = new JTextField();
        backgroundPanel.add(txtUsername);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblPassword.setForeground(Color.WHITE);
        backgroundPanel.add(lblPassword);

        txtPassword = new JPasswordField();
        backgroundPanel.add(txtPassword);

        // Empty label and login button
        backgroundPanel.add(new JLabel());
        btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnLogin.setBackground(new Color(100, 149, 237));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        backgroundPanel.add(btnLogin);

        // Button action
        btnLogin.addActionListener(e -> login());
    }

    private void login() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement pst = con.prepareStatement(
                    "SELECT * FROM employee WHERE username=? AND password=?"
            );
            pst.setString(1, username);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                DashboardFrame dashboard = new DashboardFrame(true, home);
                dashboard.setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "DB
