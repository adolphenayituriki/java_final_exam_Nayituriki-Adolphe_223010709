package com.form;

import com.panel.AddProductPanel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AdminProductPanel extends JFrame implements ActionListener {

    private JTabbedPane tabbedPane = new JTabbedPane();
    private JPanel usersPanel = new JPanel();
    private JTable productTable = new JTable();
    private JScrollPane usersScrollPane = new JScrollPane(productTable);

    private JTextField searchField = new JTextField(20);
    private JButton searchBtn = new JButton("🔍 Search Product");
    private JButton refreshBtn = new JButton("🔄 Refresh");
    private JButton editBtn = new JButton("✏️ Edit");
    private JButton addProductBtn = new JButton("\uD83C\uDF4A Add product");
    private JButton deleteBtn = new JButton("🗑️ Delete");
    private JLabel totalProducts = new JLabel("Total Products: 0");

    private JButton returnBackBtn = new JButton("BACK TO HOME");

    public AdminProductPanel() {
        setTitle("Admin Dashboard | E-BUY SMART");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initProductsPanel();

        add(tabbedPane, BorderLayout.CENTER);

        // Event listeners
        refreshBtn.addActionListener(this);
        searchBtn.addActionListener(this);
        editBtn.addActionListener(this);
        deleteBtn.addActionListener(this);
        returnBackBtn.addActionListener(this);
        addProductBtn.addActionListener(this);

        // Double click placeholder
        productTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && productTable.getSelectedRow() != -1) {
                    int row = productTable.convertRowIndexToModel(productTable.getSelectedRow());
                    // Optional: open view/edit window
                }
            }
        });

        // Load initial data
        loadProducts();

        // Dynamic search: live filter while typing
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) productTable.getModel());
        productTable.setRowSorter(sorter);

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { filter(); }
            @Override
            public void removeUpdate(DocumentEvent e) { filter(); }
            @Override
            public void changedUpdate(DocumentEvent e) { filter(); }

            private void filter() {
                String text = searchField.getText().trim();
                if (text.isEmpty()) {
                    sorter.setRowFilter(null); // show all if empty
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 1)); // filter by Name
                }
            }
        });

        setVisible(true);
    }

    private void initProductsPanel() {
        usersPanel.setLayout(new BorderLayout(10, 10));
        usersPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Header: title + logout + search
        JPanel header = new JPanel(new BorderLayout());
        returnBackBtn.setBackground(new Color(220, 53, 69));
        returnBackBtn.setForeground(Color.WHITE);
        returnBackBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.add(returnBackBtn, BorderLayout.EAST);

        searchBtn.setBackground(new Color(10, 132, 132));
        searchBtn.setForeground(Color.WHITE);
        addProductBtn.setBackground(new Color(132, 57, 10));
        addProductBtn.setForeground(Color.WHITE);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Search Product:"));
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);
        searchPanel.add(refreshBtn);
        header.add(searchPanel, BorderLayout.SOUTH);

        usersPanel.add(header, BorderLayout.NORTH);

        // Actions panel
        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actionsPanel.add(editBtn);
        actionsPanel.add(deleteBtn);
        actionsPanel.add(addProductBtn);
        actionsPanel.add(Box.createHorizontalStrut(20));
        actionsPanel.add(totalProducts);
        usersPanel.add(actionsPanel, BorderLayout.SOUTH);

        tabbedPane.addTab("Products", usersPanel);
    }

    private void deleteProduct(int productId) {
        try (Connection conn = com.utils.DB.getConnection()) {
            String query = "DELETE FROM products WHERE ProductID = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, productId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to delete product");
        }
    }

    private void editProduct(int productId, String productName, String description, String category, double price, int stockQuantity) {
        try (Connection conn = com.utils.DB.getConnection()) {
            String query = "UPDATE products SET Name = ?, Description = ?, CategoryID = ?, Price = ?, StockQuantity = ? WHERE ProductID = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, productName);
            ps.setString(2, description);
            ps.setString(3, category);
            ps.setDouble(4, price);
            ps.setInt(5, stockQuantity);
            ps.setInt(6, productId);

            int row = ps.executeUpdate();
            if (row > 0) {
                JOptionPane.showMessageDialog(this, "✅ Product Updated Successfully");
                loadProducts();
            } else {
                JOptionPane.showMessageDialog(this, "No Product was Updated");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to Update product");
        }
    }

    public void loadProducts() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ProductID", "Name", "Description", "CategoryID", "Price", "StockQuantity", "CreatedAt"});
        productTable.setModel(model);
        productTable.setAutoCreateRowSorter(true);
        productTable.setRowHeight(26);
        productTable.setFillsViewportHeight(true);
        productTable.setGridColor(Color.LIGHT_GRAY);

        try (Connection conn = com.utils.DB.getConnection()) {
            String sql = "SELECT ProductID, Name, Description, CategoryID, Price, StockQuantity, CreatedAt FROM products ORDER BY ProductID ASC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            int totalAvailable = 0;
            while (rs.next()) {
                int stock = rs.getInt("StockQuantity");
                model.addRow(new Object[]{
                        rs.getInt("ProductID"),
                        rs.getString("Name"),
                        rs.getString("Description"),
                        rs.getInt("CategoryID"),
                        rs.getDouble("Price"),
                        stock,
                        rs.getTimestamp("CreatedAt")
                });
                if (stock > 0) totalAvailable++;
            }

            totalProducts.setText("Total Products Available: " + totalAvailable);

            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
            productTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

            usersPanel.add(usersScrollPane, BorderLayout.CENTER);

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load products: " + ex.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == addProductBtn) {
            new AddProductPanel().setVisible(true);
        } else if (src == refreshBtn) {
            loadProducts();
            JOptionPane.showMessageDialog(this, "Products refreshed!");
        } else if (src == editBtn) {
            int row = productTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select a product to edit!");
                return;
            }
            int productID = (int) productTable.getValueAt(row, 0);
            new AddProductPanel().setVisible(true); // You can modify AddProductPanel to accept productID for editing
        } else if (src == deleteBtn) {
            int row = productTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select a product to delete!");
                return;
            }
            int productID = (int) productTable.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Delete this product?");
            if (confirm == JOptionPane.YES_OPTION) {
                deleteProduct(productID);
                loadProducts();
                JOptionPane.showMessageDialog(this, "Product deleted successfully!");
            }
        } else if (src == returnBackBtn) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?");
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
            }
        } else if (src == searchBtn) {
            searchField.requestFocus(); // optional
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AdminProductPanel::new);
    }
}
