package ui;

import DBConnection.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class DashboardFrame extends JFrame {

    private boolean isEditable;
    private JFrame home;
    private JTable menuTable;
    private DefaultTableModel model;
    private JButton btnAdd, btnUpdate, btnDelete, btnBack;

    public DashboardFrame(boolean editable, JFrame home) {
        this.isEditable = editable;
        this.home = home;
        setTitle("Dashboard");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table
        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Price");
        menuTable = new JTable(model);
        add(new JScrollPane(menuTable), BorderLayout.CENTER);

        // Bottom buttons
        JPanel bottomPanel = new JPanel();
        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnBack = new JButton("Back");
        bottomPanel.add(btnAdd);
        bottomPanel.add(btnUpdate);
        bottomPanel.add(btnDelete);
        bottomPanel.add(btnBack);
        add(bottomPanel, BorderLayout.SOUTH);

        // Enable/Disable based on access
        btnAdd.setEnabled(isEditable);
        btnUpdate.setEnabled(isEditable);
        btnDelete.setEnabled(isEditable);

        // Load menu
        loadMenuFromDB();

        // Actions
        btnAdd.addActionListener(e -> new AddMenuItemFrame(this).setVisible(true));
        btnUpdate.addActionListener(e -> updateMenu());
        btnDelete.addActionListener(e -> deleteMenu());
        btnBack.addActionListener(e -> {
            home.setVisible(true);
            this.dispose();
        });
    }

    public void loadMenuFromDB() {
        model.setRowCount(0);
        try (Connection con = DBConnection.getConnection()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM menu");
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("menu_id"),
                        rs.getString("item_name"),
                        rs.getDouble("price")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "DB Error: " + e.getMessage());
        }
    }

    private void updateMenu() {
        int row = menuTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a row!");
            return;
        }
        int id = (int) menuTable.getValueAt(row, 0);
        new UpdateFrame(this, id).setVisible(true);
    }

    private void deleteMenu() {
        int row = menuTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a row!");
            return;
        }
        int id = (int) menuTable.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Delete this item?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection con = DBConnection.getConnection()) {
                PreparedStatement pst = con.prepareStatement("DELETE FROM menu WHERE menu_id=?");
                pst.setInt(1, id);
                pst.executeUpdate();
                loadMenuFromDB();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "DB Error: " + e.getMessage());
            }
        }
    }
}
