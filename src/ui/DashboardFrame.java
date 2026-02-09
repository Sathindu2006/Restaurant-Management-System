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

        // Frame settings
        setTitle("Dashboard");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 245, 245));

        // Table setup
        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Price");
        menuTable = new JTable(model);
        menuTable.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        menuTable.setRowHeight(25);
        menuTable.setSelectionBackground(new Color(100, 149, 237));
        menuTable.setSelectionForeground(Color.WHITE);
        menuTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        menuTable.getTableHeader().setBackground(new Color(230, 230, 230));
        add(new JScrollPane(menuTable), BorderLayout.CENTER);

        // Bottom buttons
        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnBack = new JButton("Back");

        for (JButton btn : new JButton[]{btnAdd, btnUpdate, btnDelete, btnBack}) {
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            btn.setBackground(new Color(100, 149, 237));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        bottomPanel.setOpaque(false);
        bottomPanel.add(btnAdd);
        bottomPanel.add(btnUpdate);
        bottomPanel.add(btnDelete);
        bottomPanel.add(btnBack);
        add(bottomPanel, BorderLayout.SOUTH);

        // Enable buttons based on user role
        btnAdd.setEnabled(isEditable);
        btnUpdate.setEnabled(isEditable);
        btnDelete.setEnabled(isEditable);

        // Load menu from DB
        loadMenuFromDB();

        // --- BUTTON ACTIONS ---

        // Add menu item
        btnAdd.addActionListener(e -> new AddMenuItemFrame(this).setVisible(true));

        // Update menu item
        btnUpdate.addActionListener(e -> {
            int row = menuTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select a row!");
                return;
            }
            int id = (int) menuTable.getValueAt(row, 0);
            new UpdateFrame(this, id).setVisible(true);
        });

        // Delete menu item
        btnDelete.addActionListener(e -> deleteMenu());

        // Back to home
        btnBack.addActionListener(e -> {
            home.setVisible(true);
            this.dispose();
        });
    }

    // Load menu data
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

    // Delete menu method
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
