package com.form;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class CategoryProductQuery  extends JFrame implements ActionListener {
    private JTable table;
    private DefaultTableModel model;
    private JButton confirmTransaction = new JButton("Make Order");
    private JButton exist = new JButton("Exist");

    //declare a variable to hold the name of product
    private String categoryName;
    private int UserID;
    public CategoryProductQuery(String categoryName, int UserID) {

        this.categoryName = categoryName;
        this.UserID = UserID;
        setTitle("Products in "+categoryName +"E-Commerce Portal System | E-BUY SMART");
        setSize(1100,700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10,10));

        //Header
        JLabel header = new JLabel("Category Product: "+ categoryName, JLabel.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 25));
        header.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(header, BorderLayout.NORTH);

        //initializing table and modal once
        model= new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ProductID", "Name", "Description", "price", "StockQuantity"});

        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.BOLD, 20));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 18));
        table.getTableHeader().setBackground(new Color(5, 105, 205));
        table.getTableHeader().setForeground(Color.white);
        table.setGridColor(Color.LIGHT_GRAY);
        table.setSelectionBackground(new Color(5, 105, 75));
        table.setSelectionForeground(Color.white);
        table.setBounds(10, 10, 300, 300);
        add(new JScrollPane(table), BorderLayout.CENTER);

        //Footer panel
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20,10));
        confirmTransaction.setFont(new Font("Segoe UI", Font.BOLD, 20));
        confirmTransaction.setBackground(new Color(5, 105, 75));
        confirmTransaction.setForeground(Color.white);
        confirmTransaction.addActionListener(this);

        exist.setFont(new Font("Segoe UI", Font.BOLD, 20));
        exist.setBackground(Color.RED);
        exist.setForeground(Color.white);
        exist.addActionListener(this);

        footerPanel.add(confirmTransaction);
        footerPanel.add(exist);
        add(footerPanel, BorderLayout.SOUTH);

        //loading product from database
        loadProducts(categoryName);
        setVisible(true);

    }

    public void loadProducts(String categoryName) {
        try(Connection conn = com.utils.DB.getConnection()){

            String sql = "SELECT p.ProductID, p.Name, p.Description, p.Price, p.StockQuantity " +
                    "FROM products p " +
                    "JOIN categories c ON p.CategoryID = c.CategoryID " +
                    "WHERE c.Name = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, categoryName);
            ResultSet rs = ps.executeQuery();
            model.setRowCount(0);
            while (rs.next()) {
                Object[] row ={
                        rs.getInt("ProductID"),
                        rs.getString("Name"),
                        rs.getString("Description"),
                        rs.getString("Price"),
                        rs.getString("StockQuantity"),
                };
                model.addRow(row);

            }
            rs.close();
            ps.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Failed to load Categories" + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exist) {
            dispose();
            new UserDashboard().CreateProductsPanel();
        }
        if (e.getSource() == confirmTransaction) {
            int selectedRow =  table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Please select a product to order");
                return;
            }
            //getting the product details
            int productID = (int)model.getValueAt(selectedRow, 0);
            String productName = (String)model.getValueAt(selectedRow, 1);
            Object priceObject = model.getValueAt(selectedRow, 3);
            double price = priceObject instanceof Number ? ((Number)priceObject).doubleValue() : 0;

            //Asking user to select a payment method
            String[] methods = {"Mobile money", "Cash money", "Credit card"};
            String payment = (String) JOptionPane.showInputDialog(this, "Selecy a payment:", "Payment", JOptionPane.QUESTION_MESSAGE, null, methods, methods[0]);
            if (payment == null) return;

            //Generating order information
            String orderNumber = "ORD-" + new Random().nextInt(99999);
            String status = "Pending";
            double total = price; //single item extendable
            String notes = "Order for product: "+ productName;
            String currentDate = new SimpleDateFormat("yyy-MM-dd").format(new Date());

            try(Connection conn = com.utils.DB.getConnection()){
                String  sql = "INSERT INTO orders (UserID, OrderNumber, `Date`, Status, TotalAmount, PaymentMethod, Notes) VALUES(?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, UserID);
                ps.setString(2, orderNumber);
                ps.setString(3, currentDate);
                ps.setString(4, status);
                ps.setDouble(5, total);
                ps.setString(6, payment);
                ps.setString(7, notes);

                int row = ps.executeUpdate();
                if (row > 0) {

                    //progress bar before confirmation message
                    JProgressBar progressBar = new JProgressBar(0,100);
                    progressBar.setStringPainted(true);
                    JOptionPane pane = new JOptionPane(progressBar,JOptionPane.INFORMATION_MESSAGE,JOptionPane.DEFAULT_OPTION,null,new Object[]{},null);
                    JDialog dialog = pane.createDialog("Order Processing....");
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

                    //Confirmation message
                    JOptionPane.showMessageDialog(null, "✅ Order placed successfully!\nOrder No: "+orderNumber+"\n\n© 2025 E-BUY SMART | Powered by Adolphe Nayituriki Adolphe");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database error: "+ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        new CategoryProductQuery("Electronics", 1);
    }

}
