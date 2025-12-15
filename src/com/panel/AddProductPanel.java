package com.panel;
import com.form.Users;
import com.form.*;

import javax.swing.*;
import java.awt.*;
import java.security.MessageDigest;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class AddProductPanel extends JFrame implements ActionListener {
    //User interface components
    JLabel header = new JLabel("Add Product");
    JLabel productName = new JLabel("Product Name");
    JTextField productTxt = new JTextField();

    JLabel descriptionLabel = new JLabel("Description:");
    JTextArea descriptionArea = new JTextArea();
    JScrollPane descriptionScroller = new JScrollPane(descriptionArea);

    JLabel categoryLabel = new JLabel("Category ID: ");
    JTextField categoryTxt = new JTextField();

    JLabel priceLabel = new JLabel("Price:");
    JTextField priceTxt = new JTextField();


    JLabel stockLabel = new JLabel("Stock Quantity:");
    JTextField stockTxt = new JTextField();

    JButton addProductBtn = new JButton("Add Product");
    JButton clear = new JButton("Clear");
    JLabel footer = new JLabel("© 2025 E-Commerce Portal System | Add product !");

    //This is a constructor of the "User" class
    public AddProductPanel() {
        setTitle("BUY SMART E-BUY");
        setBounds(100, 100, 450, 440);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(false);
        setLayout(null);
        setResizable(false);
        //positioning the components
        header.setBounds(0, 10, 450, 50);
        header.setFont(new Font("Self", Font.BOLD, 30));
        header.setForeground(new Color(0, 25, 25));
        header.setOpaque(true);
        header.setBackground(new Color(25, 195, 255));
        header.setHorizontalAlignment(SwingConstants.CENTER);

        productName.setBounds(50, 70, 100, 30);
        productTxt.setBounds(170, 70, 150, 30);

        descriptionLabel.setBounds(50, 120, 100, 30);
        descriptionArea.setBounds(170, 120, 150, 30);

        addProductBtn.setBounds(50, 320, 100, 30);
        clear.setBounds(170, 320, 100, 30);

        categoryLabel.setBounds(50, 190, 100, 30);
        categoryTxt.setBounds(170, 190, 150, 30);

        priceLabel.setBounds(50, 230, 100, 30);
        priceTxt.setBounds(170, 230, 150, 30);

        stockLabel.setBounds(50, 270, 100, 30);
        stockTxt.setBounds(170, 270, 150, 30);

        footer.setBounds(100, 350, 450, 50);
        footer.setForeground(new Color(190, 117, 2));

        // adding components to the window
        add(header);
        add(productName);
        add(productTxt);
        add(descriptionLabel);
        add(descriptionArea);
        add(addProductBtn);
        add(clear);
        add(footer);
        add(categoryLabel);
        add(categoryTxt);
        add(priceLabel);
        add(priceTxt);
        add(stockLabel);
        add(stockTxt);
        add(descriptionScroller);

        //Adding action listeners
        addProductBtn.addActionListener(this);
        clear.addActionListener(this);

    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == clear) {
            priceTxt.setText("");
            productTxt.setText("");
            descriptionArea.setText("");
            categoryTxt.setText("");
            productTxt.setText("");
            stockTxt.setText("");

        }else if (e.getSource() == addProductBtn) {
            String proname = productTxt.getText();
            String desctxt = descriptionArea.getText();
            String categId = categoryTxt.getText();
            String price = priceTxt.getText();
            String stockQuantiry = stockTxt.getText();

            //validation
            if (proname.isEmpty() || desctxt.isEmpty() || price.isEmpty() || stockQuantiry.isEmpty()) {
                JOptionPane.showMessageDialog(null, "⚠\uFE0F Please fill all the fields");
                return;
            }
            try(Connection conn = com.utils.DB.getConnection()){
                String sql = "INSERT INTO products (Name, Description, CategoryID, Price, StockQuantity, CreatedAt) VALUES (?, ?, ?, ?, ?, NOW())";
                var ps = conn.prepareStatement(sql);
                ps.setString(1, proname);
                ps.setString(2, desctxt);
                ps.setString(3, categId);
                ps.setString(4, price);
                ps.setString(5, stockQuantiry);

                    int rows = ps.executeUpdate();
                    if (rows > 0) {
                        JOptionPane.showMessageDialog(null, "✅ Product added successfully!");
                        dispose();

                    } else{
                        JOptionPane.showMessageDialog(null," Adding product failed.");
                    }
            } catch (Exception ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "⚠\uFE0F Database error:" + ex.getMessage());
            }
        }
        setLocationRelativeTo(null);

    }
    public static void main(String[] args) {
        new AddProductPanel().setVisible(true);
    }
}

