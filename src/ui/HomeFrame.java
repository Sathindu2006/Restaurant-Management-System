package ui;

import javax.swing.*;
import java.awt.*;

public class HomeFrame extends JFrame {

    public HomeFrame() {
        setTitle("Home");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel lbl = new JLabel("Welcome to the Cafe", SwingConstants.CENTER);
        lbl.setFont(new Font("Lucida Calligraphy", Font.BOLD, 24));
        add(lbl, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        JButton btnCustomer = new JButton("Customer");
        JButton btnEmployee = new JButton("Employee");
        buttonPanel.add(btnCustomer);
        buttonPanel.add(btnEmployee);
        add(buttonPanel, BorderLayout.CENTER);

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
