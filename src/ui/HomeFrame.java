package ui;

import javax.swing.*;
import java.awt.*;

public class HomeFrame extends JFrame {

    public HomeFrame() {
        setTitle("Home");
        setSize(800, 600); // updated size
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Background panel with image
        JPanel backgroundPanel = new JPanel() {
            private Image backgroundImage = new ImageIcon("resources/young-woman-eating-croissants-cafe.jpg").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        backgroundPanel.setLayout(new BorderLayout());
        setContentPane(backgroundPanel);

        // Top label
        JLabel lbl = new JLabel("Welcome to the Cafe", SwingConstants.CENTER);
        lbl.setFont(new Font("Lucida Calligraphy", Font.BOLD, 36));
        lbl.setForeground(Color.WHITE); // visible over background
        backgroundPanel.add(lbl, BorderLayout.NORTH);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // transparent so background shows
        JButton btnCustomer = new JButton("Customer");
        JButton btnEmployee = new JButton("Employee");
        buttonPanel.add(btnCustomer);
        buttonPanel.add(btnEmployee);
        backgroundPanel.add(buttonPanel, BorderLayout.CENTER);

        // Button actions
        btnCustomer.addActionListener(e -> {
            CustomerFrame cf = new CustomerFrame(this);
            cf.setVisible(true);
            this.setVisible(false);
        });

        btnEmployee.addActionListener(e -> {
            EmployeeFrame ef = new EmployeeFrame(this);
            ef.setVisible(true);
            this.setVisible(false);
        });
    }
}
