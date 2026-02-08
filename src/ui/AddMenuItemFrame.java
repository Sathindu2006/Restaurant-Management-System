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
        setSize(300, 200);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3,2,10,10));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        txtName = new JTextField();
        txtPrice = new JTextField();
        btnSave = new JButton("Save");

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
