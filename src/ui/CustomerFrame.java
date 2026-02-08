package ui;

import javax.swing.*;

public class CustomerFrame extends JFrame {

    private JFrame home;

    public CustomerFrame(JFrame home) {
        this.home = home;
        setTitle("Customer");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JButton btnViewMenu = new JButton("View Menu");
        add(btnViewMenu);

        btnViewMenu.addActionListener(e -> {
            DashboardFrame dashboard = new DashboardFrame(false, home); // read-only
            dashboard.setVisible(true);
            this.dispose();
        });
    }
}
