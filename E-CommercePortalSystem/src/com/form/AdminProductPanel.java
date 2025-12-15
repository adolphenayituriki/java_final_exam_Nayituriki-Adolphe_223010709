package com.form;

import com.panel.AddProductPanel;
import com.panel.AdminPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.nio.file.*;
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

    // === Logout ===
    private JButton returnBackBtn = new JButton("BACK TO HOME");

    // Uploads folder
    private static final String UPLOAD_DIR = "uploads";

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

        // double click to view
        productTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && productTable.getSelectedRow() != -1) {
                    int row = productTable.convertRowIndexToModel(productTable.getSelectedRow());

                }
            }
        });

        // load initial data
        loadProducts();
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

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Search  Product:"));
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);
        searchPanel.add(refreshBtn);

        header.add(searchPanel, BorderLayout.SOUTH);

        usersPanel.add(header, BorderLayout.NORTH);

        // Actions panel (edit, delete, total)
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
        try(Connection conn = com.utils.DB.getConnection()){
            String query = "DELETE FROM products WHERE ProductID = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, productId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to delete product");
        }
    }
    //Editing product information
    private void editProduct(int productId, String productName, String description, String category, double price, int stockQuantity) {
        try(Connection conn = com.utils.DB.getConnection()){
            String query = "UPDATE products SET Name = ?, Description = ?, Category = ?, Price = ?, StockQuantity = ? WHERE ProductID = ?";
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
            }else  {
                JOptionPane.showMessageDialog(this, "No Product was Updated");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to Update product");
        }
    }


    //loading products
    public void loadProducts() {
        // Table model & table
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ProductID", "Name", "Description", "CategoryID", "Price per product", "StockQuantity", "CreatedAt"});
        productTable.setModel(model);
        productTable.setAutoCreateRowSorter(true);
        productTable.setRowHeight(26);
        productTable.setFillsViewportHeight(true);
        productTable.setGridColor(Color.LIGHT_GRAY);

        try (Connection conn = com.utils.DB.getConnection()) {
            String sql = "SELECT ProductID, Name, Description, CategoryID, Price, StockQuantity,CreatedAt  FROM products ORDER BY ProductID ASC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("ProductID"),
                        rs.getString("Name"),
                        rs.getString("Description"),
                        rs.getString("CategoryID"),
                        rs.getString("Price"),
                        rs.getInt("StockQuantity"),
                        rs.getTimestamp("CreatedAt")
                });
            }
            // center align ProductID
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
            productTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

            usersPanel.add(usersScrollPane, BorderLayout.CENTER);

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "⚠️ Failed to load products: " + ex.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       Object src = e.getSource();

       //adding product to the list
        if (src == addProductBtn) {
            new AddProductPanel().setVisible(true);
        }
        // this if where i refreshed my products
        else if (src == refreshBtn) {
            loadProducts();

            //progress bar before refreshing confirmation message message
            JProgressBar progressBar = new JProgressBar(0,100);
            progressBar.setStringPainted(true);
            JOptionPane pane = new JOptionPane(progressBar,JOptionPane.INFORMATION_MESSAGE,JOptionPane.DEFAULT_OPTION,null,new Object[]{},null);
            JDialog dialog = pane.createDialog("Refreshing Processing....");
            new Thread(() -> {
                for(int i = 0; i < 100; i++){
                    progressBar.setValue(i);
                    try{Thread.sleep(30);}
                    catch (InterruptedException ex){
                        ex.printStackTrace();
                    }
                }
                dialog.dispose();
            }).start();
            dialog.setVisible(true);
            JOptionPane.showMessageDialog(this, "Now your products are refreshed well!");
        }
        // condition to search a product
        else if (src == searchBtn) {
            String search = searchField.getText().trim();
            if (search.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a product name!");
                return;
            }
            searchField.setText(search);
        }
        // condition to edit any product within the list
        else if (src == editBtn) {
            int row = productTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select a product to be edited!");
                return;
            }
            int productID = (int) productTable.getValueAt(row, 0);
            new AddProductPanel().setVisible(true);
        }
        //delete
        else if (src == deleteBtn) {
            int row = productTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select a product to be deleted!");
                return;
            }
            int productID = (int) productTable.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Delete this product?");
            if (confirm == JOptionPane.YES_OPTION){
                deleteProduct(productID);
            loadProducts();
            JOptionPane.showMessageDialog(this, "product deleted successfully !");
        }}
        else if (src == returnBackBtn) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?");
            if (confirm == JOptionPane.YES_OPTION){
                dispose();

            }
        }

    }

    private void deleteBtn(int productID) {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AdminProductPanel::new);
    }
}

