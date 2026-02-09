package ui;

import javax.swing.*;
import java.awt.*;

public class CustomerFrame extends JFrame {

    private JFrame home;

    public CustomerFrame(JFrame home) {
        this.home = home;

        setTitle("Customer");
        setSize(500, 350); // standard size
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Background panel
        JPanel backgroundPanel = new JPanel() {
            private Image backgroundImage = new ImageIcon("resources/Screenshot 2026-02-09 094715.jpg").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        backgroundPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 120)); // center button
        setContentPane(backgroundPanel);

        // View Menu Button
        JButton btnViewMenu = new JButton("View Menu");
        btnViewMenu.setFont(new Font("Segoe UI", Font.BOLD, 20));
        btnViewMenu.setBackground(new Color(100, 149, 237));
        btnViewMenu.setForeground(Color.WHITE);
        btnViewMenu.setFocusPainted(false);
        backgroundPanel.add(btnViewMenu);

        // Button action
        btnViewMenu.addActionListener(e -> {
            DashboardFrame dashboard = new DashboardFrame(false, home); // read-only
            dashboard.setVisible(true);
            this.dispose();
        });
    }
}
