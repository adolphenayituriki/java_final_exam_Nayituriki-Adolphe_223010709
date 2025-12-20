package com.panel;

import com.form.AdminProductPanel;
import com.form.AdminUserPanelManagement;
import com.form.Users;
import com.utils.Session;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AdminPanel extends JFrame implements ActionListener {
    private JLabel welcomeLabel;
    JTabbedPane tabbedPane = new JTabbedPane();

    //my panels
    JPanel usersPanel = new JPanel();
    JPanel productPanel = new JPanel();
    JPanel orderPanel = new JPanel();
    JPanel shippedPanel = new JPanel();
    JPanel settingPanel = new JPanel();

    JTable usersTable = new JTable();
    JTable productTable = new JTable();
    JTable orderTable = new JTable();
    JTable shippedTable = new JTable();

    JScrollPane usersScrollPane = new JScrollPane(usersTable);
    JScrollPane productScrollPane = new JScrollPane(productTable);
    JScrollPane orderScrollPane = new JScrollPane(orderTable);
    JScrollPane shippedScrollPane = new JScrollPane(shippedTable);

    //buttons
    JButton logoutButton = new JButton("Logout");
    JButton exportButton = new JButton(" Export Data ");



    //Order management buttons
    JButton viewOrderButton = new JButton("View Order");
    JButton approveOrderButton = new JButton("Approve Order");
    JButton rejectOrderButton = new JButton("Reject Order");
    JButton deleteOrderButton = new JButton("Delete Order");


    public AdminPanel(String fullname) {
        setTitle("E-BUY SMART | Admin Panel");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setVisible(true);
        //My headers
        JPanel header =  new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        header.setBackground(new Color(245, 245, 245));
        header.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.GRAY));

        JLabel appName =  new JLabel(" © E-BUY SMART | ADMIN");
        appName.setFont(new Font("Segoe UI", Font.BOLD, 20));

        welcomeLabel = new JLabel("Welcome, " + fullname);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        welcomeLabel.setForeground(new Color(25, 96, 225));

        logoutButton.setBackground(new Color(245, 245, 245));
        logoutButton.setForeground(Color.RED);
        logoutButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
        logoutButton.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.GRAY));
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        exportButton.setBackground(new Color(245, 245, 245));
        exportButton.setForeground(new Color(225, 160, 5));
        exportButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
        exportButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exportButton.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(23, 132, 39)));
        header.add(exportButton);

        header.add(appName);
        header.add(welcomeLabel);
        header.add(logoutButton);
        add(header, BorderLayout.NORTH);

        //this is a user tab
        usersPanel.setLayout(new BorderLayout(10,10));
        usersPanel.setBorder(BorderFactory.createEmptyBorder(10, 10,10,10));
        usersPanel.add(usersScrollPane, BorderLayout.CENTER);

        JButton manageUsersButton = new JButton("Manage Users");
        manageUsersButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
        manageUsersButton.setBackground(new Color(15, 185, 115));
        manageUsersButton.setForeground(Color.WHITE);
        manageUsersButton.addActionListener(this);
        usersPanel.add(manageUsersButton, BorderLayout.SOUTH);

        //this is a product tab
        productPanel.setLayout(new BorderLayout(10,10));
        productPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        productPanel.add(productScrollPane, BorderLayout.CENTER);

        JButton manageProductsButton = new JButton("Manage Products");
        manageProductsButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
        manageProductsButton.setBackground(new Color(15, 185, 115));
        manageProductsButton.setForeground(Color.WHITE);
        manageProductsButton.addActionListener(this);
        productPanel.add(manageProductsButton, BorderLayout.SOUTH);

        //This is an order tab
        orderPanel.setLayout(new BorderLayout(10,10));
        orderPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        orderPanel.add(orderScrollPane, BorderLayout.CENTER);

        //this is shipped tab
        shippedPanel.setLayout(new BorderLayout(10,10));
        shippedPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        shippedPanel.add(shippedScrollPane, BorderLayout.SOUTH);

        //Bottom order management buttons
        JPanel orderButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        orderButtonPanel.setBackground(new Color(244, 244, 240));

        viewOrderButton.setBackground(new Color(70, 180, 190));
        viewOrderButton.setForeground(Color.WHITE);
        approveOrderButton.setBackground(new Color(60, 129, 113));
        approveOrderButton.setForeground(Color.WHITE);
        rejectOrderButton.setBackground(new Color(255, 30, 113));
        rejectOrderButton.setForeground(Color.WHITE);
        deleteOrderButton.setBackground(new Color(220, 20, 60));
        deleteOrderButton.setForeground(Color.WHITE);

        Font font = new Font("Segoe UI", Font.BOLD, 20);
        viewOrderButton.setFont(font);
        approveOrderButton.setFont(font);
        rejectOrderButton.setFont(font);
        deleteOrderButton.setFont(font);

        orderButtonPanel.add(viewOrderButton);
        orderButtonPanel.add(approveOrderButton);
        orderButtonPanel.add(rejectOrderButton);
        orderButtonPanel.add(deleteOrderButton);

        orderPanel.add(orderButtonPanel, BorderLayout.SOUTH);

        //setting tab
        tabbedPane.addTab("Users", usersPanel);
        tabbedPane.addTab("Products", productPanel);
        tabbedPane.addTab("Orders", orderPanel);
        tabbedPane.addTab("Shipped", shippedPanel);

        tabbedPane.addTab("Settings", settingPanel);

        tabbedPane.setBackground(new Color(244, 244, 240));
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 20));

        add(tabbedPane, BorderLayout.CENTER);

        //Adding event listeners to the buttons
        logoutButton.addActionListener(this);
        exportButton.addActionListener(this);
        viewOrderButton.addActionListener(this);
        approveOrderButton.addActionListener(this);
        rejectOrderButton.addActionListener(this);
        deleteOrderButton.addActionListener(this);

        //setting all tables header format
        Color tableColor = new Color(20, 125, 230);
        Color tableForeground = new Color(255, 255, 255);
        usersTable.getTableHeader().setBackground(tableColor);
        productTable.getTableHeader().setBackground(tableColor);
        orderTable.getTableHeader().setBackground(tableColor);
        usersTable.setRowHeight(30);
        usersTable.getTableHeader().setResizingAllowed(true);
        productTable.setRowHeight(30);
        productTable.getTableHeader().setResizingAllowed(true);
        orderTable.setRowHeight(30);
        orderTable.getTableHeader().setResizingAllowed(true);
        shippedTable.setRowHeight(30);
        shippedTable.getTableHeader().setResizingAllowed(true);
        // header foreground color
        usersTable.getTableHeader().setForeground(tableForeground);
        productTable.getTableHeader().setForeground(tableForeground);
        orderTable.getTableHeader().setForeground(tableForeground);
        shippedTable.getTableHeader().setForeground(tableForeground);
        shippedPanel.setBackground(new Color(24, 144, 240));
        shippedTable.getTableHeader().setBackground(Color.BLACK);


        //loading data from database
        loadUsersTable();
        loadProductsTable();
        loadOrdersTable();
        loadShippedTable();
    }



    public void loadUsersTable(){
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"UserID", "Username","Email", "Full Name", "Role", "Created At", "Last Login"});
        usersTable.setModel(model);
        usersTable.setRowHeight(30);

        try(Connection conn = com.utils.DB.getConnection()){
            String sql = "SELECT UserID, Username, Email, FullName, Role, CreatedAt, LastLogin FROM Users ORDER BY UserID ASC";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                model.addRow(new Object[]{
                        rs.getInt("UserID"),
                        rs.getString("Username"),
                        rs.getString("Email"),
                        rs.getString("FullName"),
                        rs.getString("Role"),
                        rs.getDate("CreatedAt"),
                        rs.getDate("LastLogin")
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to load Users: "+e.getMessage());
        }
    }

    public void loadProductsTable(){
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"ProductID", "Product Name", "Description", "Category ID", "Price", "Stock Quantity", "Created At"});
        productTable.setModel(model);
        productTable.setRowHeight(30);

        try(Connection conn = com.utils.DB.getConnection()){
            String sql = "SELECT ProductID, Name, Description, CategoryID, Price, StockQuantity, CreatedAt FROM Products ORDER BY ProductID ASC";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                model.addRow(new Object[]{
                        rs.getInt("ProductID"),
                        rs.getString("Name"),
                        rs.getString("Description"),
                        rs.getString("CategoryID"),
                        rs.getInt("Price"),
                        rs.getInt("StockQuantity"),
                        rs.getDate("CreatedAt")
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to load Products: "+e.getMessage());
        }
    }

    public void loadOrdersTable(){
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Order Id", "User ID","Order Number", "Date", "Status", "TotalAmount", "PaymentMethod", "Notes"});
        orderTable.setModel(model);
        orderTable.setRowHeight(30);

        try(Connection conn = com.utils.DB.getConnection()){
            String sql = "SELECT OrderId, UserID, OrderNumber, `Date`, Status, TotalAmount, PaymentMethod, Notes FROM Orders ORDER BY OrderId ASC";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                model.addRow(new Object[]{
                        rs.getInt("OrderId"),
                        rs.getString("UserID"),
                        rs.getString("OrderNumber"),
                        rs.getDate("Date"),
                        rs.getString("Status"),
                        rs.getInt("TotalAmount"),
                        rs.getString("PaymentMethod"),
                        rs.getString("Notes")
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to load Orders: "+e.getMessage());
        }
        DefaultTableCellRenderer statusRenderer = new DefaultTableCellRenderer(){
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if(value != null && value.toString().equalsIgnoreCase("Cancelled")){
                    c.setForeground(Color.RED);
                }
                else if(value != null && value.toString().equalsIgnoreCase("Pending")){
                    c.setForeground(new Color(255, 150, 50));
                }
                else if(value != null && value.toString().equalsIgnoreCase("Delivered")){
                    c.setForeground(new Color(0, 140, 80));
                }
                else{
                    c.setForeground(Color.GRAY);
                }
                return c;
            }
        };
        orderTable.getColumnModel().getColumn(4).setCellRenderer(statusRenderer);
    }

    //Loading shipped table
    public void loadShippedTable(){
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"Shipment ID", "User ID", "Product ID","Created At"});
        shippedTable.setModel(model);
        shippedTable.setRowHeight(30);

        try(Connection conn = com.utils.DB.getConnection()){
            String sql = """
                    SELECT s.ShipmentID, u.UserID AS UserId, p.ProductID AS ProductId, s.CreatedAt FROM Shipment s JOIN
                    users u ON s.UserID = u.userID
                    JOIN products p ON s.ProductID = p.ProductID
                    ORDER BY s.ShipmentID ASC;
                    """;
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                model.addRow(new Object[]{
                        rs.getInt("ShipmentID"),
                        rs.getString("UserId"),
                        rs.getString("ProductId"),
                        rs.getDate("CreatedAt")
                });
            }
            loadOrdersTable();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to load shipments: "+e.getMessage());
        }
    }


    //Order panel buttons management
    public void deleteOrder(int orderId){
        try(Connection conn = com.utils.DB.getConnection()){
            String sql = "DELETE FROM Orders WHERE OrderID = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, orderId);
            pst.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to delete Order: "+e.getMessage());
        }
    }

    public void rejectOrder(int orderId){
        try(Connection conn = com.utils.DB.getConnection()){
            String sql = "UPDATE orders SET Status='Cancelled' WHERE OrderID = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, orderId);
            pst.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to reject Order: "+e.getMessage());
        }
    }

    public void approveOrder(int orderId){
        try(Connection conn = com.utils.DB.getConnection()){

            String fetchOrderQQuery = "SELECT UserID FROM orders WHERE OrderID = ?";
            PreparedStatement pst1 = conn.prepareStatement(fetchOrderQQuery);
            pst1.setInt(1, orderId);
            ResultSet rs = pst1.executeQuery();

            if(!rs.next()){
                JOptionPane.showMessageDialog(this, "Please, Order not found: ");
                return;
            }
            int userID = rs.getInt("UserID");

            //picking any existing ProductID
            String getProductQQuery = "SELECT ProductID FROM products ORDER BY ProductID ASC LIMIT 1";
            PreparedStatement pst2 = conn.prepareStatement(getProductQQuery);
            ResultSet rs2 = pst2.executeQuery();

            if (!rs2.next()) {
                JOptionPane.showMessageDialog(this, "Please, Product not found: ");
                return;
            }
            int productID = rs2.getInt("ProductID");

            //Updating order status
            String sql = "UPDATE orders SET Status='Delivered' WHERE OrderID = ?";
            PreparedStatement pst3 = conn.prepareStatement(sql);
            pst3.setInt(1, orderId);
            pst3.executeUpdate();

            //inserting into shipment entity
            String insertIntoShipmentQuery = """
                    INSERT INTO shipment (UserID, ProductID) SELECT ?, ? WHERE NOT EXISTS (SELECT 1 FROM shipment WHERE UserID = ? AND ProductID = ?)
                    """;
            PreparedStatement pst4 = conn.prepareStatement(insertIntoShipmentQuery);
            pst4.setInt(1, userID);
            pst4.setInt(2, productID);
            pst4.setInt(3, productID);
            pst4.setInt(4, orderId);
            pst4.executeUpdate();

            JOptionPane.showMessageDialog(this, "Order approved successfully!\nSipment created:\nUserID: "+userID+"\nProductID: "+productID);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to approve Order: "+e.getMessage());
        }
    }

    public void viewOrderDetails(int orderId){
        try(Connection conn = com.utils.DB.getConnection()){
            String orderDetailsQuery = "SELECT * FROM orders WHERE OrderID = ?";
            PreparedStatement pst = conn.prepareStatement(orderDetailsQuery);
            pst.setInt(1, orderId);
            ResultSet rs = pst.executeQuery();


            if (rs.next()){
                String details =
                        "OrderID: "+rs.getInt("OrderID") + "\n"+"User ID: "+rs.getInt("UserID")+
                                "\n" + "Order Number: "+rs.getString("OrderNumber") + "\n"+"Date: "+rs.getDate("Date")+ "\n" +
                                "Status: "+rs.getString("Status") + "\n"+ "Total Amount: "+rs.getDouble("TotalAmount")+
                                "\n" + "Payment Method: "+rs.getString("PaymentMethod") + "\n"+"Notes: "+rs.getString("Notes");
                JOptionPane.showMessageDialog(this, details, "OrderDetails", JOptionPane.INFORMATION_MESSAGE);
            }else {
                JOptionPane.showMessageDialog(this, "Order Detail not found");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to view Order Details: "+e.getMessage());
        }
    }

    public void exportTableToCSV(JTable table){
        try{
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Exported Data");
            fileChooser.setSelectedFile(new java.io.File("E-BUY_SMART_Export.xls"));

            int userSelection = fileChooser.showSaveDialog(this);
            if(userSelection != JFileChooser.APPROVE_OPTION){
                return;
            }
            java.io.File fileToSave = fileChooser.getSelectedFile();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            try(java.io.PrintWriter csvWriter = new java.io.PrintWriter(fileToSave)){
                //write header row
                for(int i = 0; i < model.getColumnCount(); i++){
                    csvWriter.print(model.getColumnName(i));
                    if (i<model.getColumnCount()-1) csvWriter.print("\t");
                }
                csvWriter.println("\n");

                //write rows for data
                for(int row = 0; row < model.getRowCount(); row++){
                    for(int col = 0; col < model.getColumnCount(); col++){
                        Object value = model.getValueAt(row, col);
                        csvWriter.print(value == null ? "" : value.toString().replace("\t", " "));
                        if (col<model.getColumnCount()-1) csvWriter.print("\t");
                    }
                    csvWriter.println();
                }
                csvWriter.flush();
            }
            JOptionPane.showMessageDialog(this, "Exported Data saved successfully!"+fileToSave.getAbsolutePath()+"\n\n____________________________________________________________" +
                    "\n"+"E-BUY SMART | E-COMMERCE PORTAL SYSTEM\nServed by" +
                    " www.nayituriki.com@gmail.com 2025\nADMIN: "+Session.CurrentUserFullName);

        }catch (Exception e){
            JOptionPane.showMessageDialog(this, "Failed to export data"+ e.getMessage());
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == logoutButton) {
            int confirm = JOptionPane.showConfirmDialog(null, "Are You Sure To Log Out?");
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new Users().setVisible(true);
            }
        }
        else if (src == exportButton) {
            int selectedIndex = tabbedPane.getSelectedIndex();
            JTable selectedTable;

            //choosing table based on selected tab
            if (selectedIndex == 0) selectedTable = usersTable;
            else if (selectedIndex == 1) selectedTable = productTable;
            else if (selectedIndex == 2) selectedTable = orderTable;
            else {
                JOptionPane.showMessageDialog(null, "No table to be exported in this tab!");
                return;
            }
            exportTableToCSV(selectedTable);

        }
        else if (src == viewOrderButton) {
            int row = orderTable.getSelectedRow();
            if(row == -1){
                JOptionPane.showMessageDialog(null, "Please Select Order.");
                return;
            }
            int orderId = (int)orderTable.getValueAt(row, 0);
            viewOrderDetails(orderId);
            String adminName = Session.CurrentUserFullName;
            JOptionPane.showMessageDialog(this, "Viewing Orders Details was Successful!" +"\n" +"Signature: "+adminName );
        }
        else if (src == approveOrderButton) {
            int row = orderTable.getSelectedRow();
            if (row == -1){
                JOptionPane.showMessageDialog(this, "Please Select Order To Approve!");
                return;
            }
            int orderId = (int)orderTable.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(null, "Are You Sure To Approve this Order?");
            if (confirm == JOptionPane.YES_OPTION) {
                approveOrder(orderId);
                loadOrdersTable();
                JOptionPane.showMessageDialog(this, "✅ Order approved!");

            }
        }
        else if (src == rejectOrderButton) {
            int row = orderTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please Select Order ID to be rejected!");
                return;
            }
            int orderId = (int)orderTable.getValueAt(row, 0);
            int confirm =  JOptionPane.showConfirmDialog(null, "Are You Sure To Reject Order?");
            if (confirm == JOptionPane.YES_OPTION) {
                rejectOrder(orderId);
                loadOrdersTable();
                JOptionPane.showMessageDialog(this, "Order Rejected successfully!");
            }
        }
        else if (src == deleteOrderButton) {
            int row = orderTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please Select an Order ID to be deleted!");
                return;
            }
            int orderId = (int) orderTable.getValueAt(row, 0);
            int confirm =  JOptionPane.showConfirmDialog(null, "Are You Sure To Delete this Order?");
            if (confirm == JOptionPane.YES_OPTION) {
                deleteOrder(orderId);
                loadOrdersTable();
                JOptionPane.showMessageDialog(this, "Order Deleted successfully!");
            }
        }else if (e.getActionCommand().equals("Manage Products")) {
            dispose();
            new AdminProductPanel().setVisible(true);
        }else if (e.getActionCommand().equals("Manage Users")) {
            dispose();
            new AdminUserPanelManagement().setVisible(true);
        }

    }
    public static void main(String[] args) {
        new AdminPanel("Admin");
    }


}