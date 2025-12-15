package com.panel;

import com.form.AdminProductPanel;
import com.form.AdminUserPanelManagement;
import com.form.Users;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.io.FileWriter;

public class AdminPanel extends JFrame implements ActionListener {

    //AUTH SESSION
    private int loggedInUserId;
    private String loggedInRole;

    private JLabel welcomeLabel;
    private JTabbedPane tabbedPane = new JTabbedPane();

    //PANELS
    private JPanel usersPanel = new JPanel();
    private JPanel productPanel = new JPanel();
    private JPanel orderPanel = new JPanel();
    private JPanel settingPanel = new JPanel();

    private JTable usersTable = new JTable();
    private JTable productTable = new JTable();
    private JTable orderTable = new JTable();

    private JScrollPane usersScrollPane = new JScrollPane(usersTable);
    private JScrollPane productScrollPane = new JScrollPane(productTable);
    private JScrollPane orderScrollPane = new JScrollPane(orderTable);

    private JButton exportButton = new JButton("Export Data");
    private JButton refreshButton = new JButton("🔄 Refresh");
    private JButton logoutButton = new JButton("Logout");

    private JButton viewOrderButton = new JButton("View Order");
    private JButton approveOrderButton = new JButton("Approve Order");
    private JButton rejectOrderButton = new JButton("Reject Order");
    private JButton deleteOrderButton = new JButton("Delete Order");

    public AdminPanel(int userId, String role, String fullname) {

        this.loggedInUserId = userId;
        this.loggedInRole = role;

        if (!isAuthenticated()) {
            JOptionPane.showMessageDialog(null, "Unauthorized Access!");
            System.exit(0);
        }

        setTitle("E-BUY SMART | Admin Panel");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel header = new JPanel(new BorderLayout());
        header.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        header.setBackground(new Color(245, 245, 245));

        JLabel appName = new JLabel("E-BUY SMART | ADMIN");
        appName.setFont(new Font("Segoe UI", Font.BOLD, 20));

        welcomeLabel = new JLabel("Welcome, " + fullname);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        welcomeLabel.setForeground(new Color(255, 96, 115));

        JPanel headerRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        headerRight.setOpaque(false);

        exportButton.setBackground(new Color(255, 193, 7));
        refreshButton.setBackground(new Color(52, 152, 219));
        logoutButton.setBackground(new Color(220, 53, 69));

        refreshButton.setForeground(Color.WHITE);
        logoutButton.setForeground(Color.WHITE);

        exportButton.addActionListener(this);
        refreshButton.addActionListener(this);
        logoutButton.addActionListener(this);

        headerRight.add(exportButton);
        headerRight.add(refreshButton);
        headerRight.add(logoutButton);

        header.add(appName, BorderLayout.WEST);
        header.add(welcomeLabel, BorderLayout.CENTER);
        header.add(headerRight, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        usersPanel.setLayout(new BorderLayout(10, 10));
        usersPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        usersPanel.add(usersScrollPane, BorderLayout.CENTER);

        JButton manageUsersButton = new JButton("Manage Users");
        manageUsersButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        manageUsersButton.setBackground(new Color(15, 185, 115));
        manageUsersButton.setForeground(Color.WHITE);
        manageUsersButton.addActionListener(this);
        usersPanel.add(manageUsersButton, BorderLayout.SOUTH);

        productPanel.setLayout(new BorderLayout(10, 10));
        productPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        productPanel.add(productScrollPane, BorderLayout.CENTER);

        JButton manageProductsButton = new JButton("Manage Products");
        manageProductsButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        manageProductsButton.setBackground(new Color(15, 185, 115));
        manageProductsButton.setForeground(Color.WHITE);
        manageProductsButton.addActionListener(this);
        productPanel.add(manageProductsButton, BorderLayout.SOUTH);

        orderPanel.setLayout(new BorderLayout(10, 10));
        orderPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        orderPanel.add(orderScrollPane, BorderLayout.CENTER);

        JPanel orderButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        Font font = new Font("Segoe UI", Font.BOLD, 18);
        JButton[] orderButtons = {viewOrderButton, approveOrderButton, rejectOrderButton, deleteOrderButton};

        for (JButton btn : orderButtons) {
            btn.setFont(font);
            btn.addActionListener(this);
            orderButtonPanel.add(btn);
        }

        orderPanel.add(orderButtonPanel, BorderLayout.SOUTH);

        tabbedPane.addTab("Users", usersPanel);
        tabbedPane.addTab("Products", productPanel);
        tabbedPane.addTab("Orders", orderPanel);
        tabbedPane.addTab("Settings", settingPanel);
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 18));

        add(tabbedPane, BorderLayout.CENTER);

        loadUsersTable();
        loadProductsTable();
        loadOrdersTable();

        setVisible(true);
    }

    public AdminPanel(String fullName) {
    }

    private boolean isAuthenticated() {
        return loggedInUserId > 0 && "ADMIN".equalsIgnoreCase(loggedInRole);
    }

    private boolean isRowSelected(JTable table) {
        if (table.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Please select a record first!");
            return false;
        }
        return true;
    }

    private void loadUsersTable() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"UserID", "Username", "Email", "Full Name", "Role", "Created At"}, 0);
        usersTable.setModel(model);

        try (Connection conn = com.utils.DB.getConnection();
             PreparedStatement pst = conn.prepareStatement("SELECT * FROM Users");
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("UserID"),
                        rs.getString("Username"),
                        rs.getString("Email"),
                        rs.getString("FullName"),
                        rs.getString("Role"),
                        rs.getDate("CreatedAt")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void loadProductsTable() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"ProductID", "Name", "CategoryID", "Price", "Stock"}, 0);
        productTable.setModel(model);

        try (Connection conn = com.utils.DB.getConnection();
             PreparedStatement pst = conn.prepareStatement("SELECT * FROM Products");
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("ProductID"),
                        rs.getString("Name"),
                        rs.getInt("CategoryID"),
                        rs.getInt("Price"),
                        rs.getInt("StockQuantity")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void loadOrdersTable() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"OrderID", "UserID", "OrderNumber", "Status", "Total"}, 0);
        orderTable.setModel(model);

        try (Connection conn = com.utils.DB.getConnection();
             PreparedStatement pst = conn.prepareStatement("SELECT * FROM Orders");
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("OrderID"),
                        rs.getInt("UserID"),
                        rs.getString("OrderNumber"),
                        rs.getString("Status"),
                        rs.getInt("TotalAmount")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void exportUsers() {
        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new java.io.File("users.csv"));

        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (FileWriter fw = new FileWriter(chooser.getSelectedFile());
                 Connection conn = com.utils.DB.getConnection();
                 PreparedStatement pst = conn.prepareStatement("SELECT * FROM Users");
                 ResultSet rs = pst.executeQuery()) {

                fw.write("UserID,Username,Email,Role\n");
                while (rs.next()) {
                    fw.write(rs.getInt("UserID") + "," +
                            rs.getString("Username") + "," +
                            rs.getString("Email") + "," +
                            rs.getString("Role") + "\n");
                }
                JOptionPane.showMessageDialog(this, "Export Successful!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (!isAuthenticated()) {
            JOptionPane.showMessageDialog(this, "Session expired!");
            dispose();
            new Users().setVisible(true);
            return;
        }

        Object src = e.getSource();

        if (src == logoutButton) {
            dispose();
            new Users().setVisible(true);
        } else if (src == refreshButton) {
            loadUsersTable();
            loadProductsTable();
            loadOrdersTable();
        } else if (src == exportButton) {
            exportUsers();
        } else if (src == viewOrderButton && isRowSelected(orderTable)) {
            JOptionPane.showMessageDialog(this, "Viewing order details...");
        } else if (src == approveOrderButton && isRowSelected(orderTable)) {
            JOptionPane.showMessageDialog(this, "Order Approved!");
        } else if (src == rejectOrderButton && isRowSelected(orderTable)) {
            JOptionPane.showMessageDialog(this, "Order Rejected!");
        } else if (src == deleteOrderButton && isRowSelected(orderTable)) {
            JOptionPane.showMessageDialog(this, "Order Deleted!");
        } else if (e.getActionCommand().equals("Manage Users")) {
            dispose();
            new AdminUserPanelManagement().setVisible(true);
        } else if (e.getActionCommand().equals("Manage Products")) {
            dispose();
            new AdminProductPanel().setVisible(true);
        }
    }

    public static void main(String[] args) {
        new AdminPanel(1, "ADMIN", "Admin");
    }
}
