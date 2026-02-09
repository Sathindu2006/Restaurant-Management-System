package ui;

import DBConnection.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddMenuItemFrame extends JFrame {

    private DashboardFrame dashboard;
    private JTextField txtName, txtPrice;
    private JButton btnSave;

    public AddMenuItemFrame(DashboardFrame dashboard) {
        this.dashboard = dashboard;
        setTitle("Add Menu Item");
        setSize(350, 220);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3,2,15,15));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(245, 245, 245));
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        txtName = new JTextField();
        txtName.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtPrice = new JTextField();
        txtPrice.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btnSave = new JButton("Save");
        btnSave.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btnSave.setBackground(new Color(100, 149, 237));
        btnSave.setForeground(Color.WHITE);
        btnSave.setFocusPainted(false);
        btnSave.setBorder(BorderFactory.createEmptyBorder(8,15,8,15));
        btnSave.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        add(new JLabel("Name:"));
        add(txtName);
        add(new JLabel("Price:"));
        add(txtPrice);
        add(new JLabel());
        add(btnSave);

        btnSave.addActionListener(e -> saveMenuItem());
    }

    private void saveMenuItem() {
        String name = txtName.getText();
        String priceText = txtPrice.getText();
        if (name.isEmpty() || priceText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter Name and Price!");
            return;
        }
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement pst = con.prepareStatement("INSERT INTO menu(item_name, price) VALUES(?,?)");
            pst.setString(1, name);
            pst.setDouble(2, Double.parseDouble(priceText));
            pst.executeUpdate();
            dashboard.loadMenuFromDB();
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "DB Error: " + e.getMessage());
        }
    }
}

