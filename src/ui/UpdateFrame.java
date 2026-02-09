package ui;

import DBConnection.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UpdateFrame extends JFrame {

    private DashboardFrame dashboard;
    private int menuId;
    private JTextField txtName, txtPrice;
    private JButton btnUpdate;

    public UpdateFrame(DashboardFrame dashboard, int menuId) {
        this.dashboard = dashboard;
        this.menuId = menuId;
        setTitle("Update Menu Item");
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
        btnUpdate = new JButton("Update");
        btnUpdate.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btnUpdate.setBackground(new Color(100, 149, 237));
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.setFocusPainted(false);
        btnUpdate.setBorder(BorderFactory.createEmptyBorder(8,15,8,15));
        btnUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        add(new JLabel("Name:"));
        add(txtName);
        add(new JLabel("Price:"));
        add(txtPrice);
        add(new JLabel());
        add(btnUpdate);

        loadMenuData();
        btnUpdate.addActionListener(e -> updateMenu());
    }

    private void loadMenuData() {
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM menu WHERE menu_id=?");
            pst.setInt(1, menuId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                txtName.setText(rs.getString("item_name"));
                txtPrice.setText(String.valueOf(rs.getDouble("price")));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "DB Error: " + e.getMessage());
        }
    }

    private void updateMenu() {
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement pst = con.prepareStatement("UPDATE menu SET item_name=?, price=? WHERE menu_id=?");
            pst.setString(1, txtName.getText());
            pst.setDouble(2, Double.parseDouble(txtPrice.getText()));
            pst.setInt(3, menuId);
            pst.executeUpdate();
            dashboard.loadMenuFromDB();
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "DB Error: " + e.getMessage());
        }
    }
}
