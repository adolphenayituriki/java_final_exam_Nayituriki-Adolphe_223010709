package com.form;
import com.utils.Session;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDashboard extends JFrame implements ActionListener {

    private final JButton homeButton = new JButton("Home");
    private final JButton profileButton = new JButton("Profile");
    private final JButton productsButton = new JButton("Products");
    private final JButton orderButton = new JButton("My orders");
    private final JButton logoutButton = new JButton("Logout");

    //main content area (where pages will change)
    private final JPanel contentPanel = new JPanel(new CardLayout());
    private JLabel welcomeLabel;
    private JTable orderTable;
    private DefaultTableModel orderTableModel;

    public UserDashboard() {

        setTitle("Users Dashboard | E-BUY SMART");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(true);

        //top navigation
        JPanel navBar =  new JPanel(new BorderLayout());
        navBar.setBackground(new Color(245, 245, 245));
        navBar.setBorder(BorderFactory.createMatteBorder(0,0,0,0, Color.LIGHT_GRAY));

        JLabel logoLabel = new JLabel("E-BUY SMART");
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        logoLabel.setForeground(new Color(0, 102, 204));
        logoLabel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 5));

        JLabel footerLabel = new JLabel("© 2025 e-buy smart");
        footerLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        footerLabel.setForeground(new Color(255, 245, 255));
        footerLabel.setBounds(10, 470, 200, 50);
        add(footerLabel);

        //welcome text dynamic
        welcomeLabel = new JLabel("Welcome, " + Session.CurrentUserFullName + "!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        welcomeLabel.setForeground(new Color(60, 62, 64));

        //navigation buttons
        JPanel navButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        JButton[] navButtons = new JButton[]{homeButton, productsButton, orderButton, logoutButton, profileButton};
        for (JButton b : navButtons) {
            b.setFocusable(false);
            b.setBackground(Color.WHITE);
            b.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            b.addActionListener(this);
            navButtonPanel.add(b);
        }

        navBar.add(logoLabel, BorderLayout.WEST);
        navBar.add(navButtonPanel, BorderLayout.EAST);
        navBar.add(welcomeLabel, BorderLayout.CENTER);

        add(navBar, BorderLayout.NORTH);

        // contents panels
        contentPanel.add(CreateHomePanel(), "Home");
        contentPanel.add(CreateProfilePanel(), "Profile");
        contentPanel.add(CreateProductsPanel(), "Products");
        contentPanel.add(CreateOrderPanel(), "My Orders");

        add(contentPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    //panels
    private JPanel CreateHomePanel() {
        JPanel p = new BackgroundPanel("/com/images/top_right.png");
        JLabel lbl= new JLabel("Welcome to Kigali E-BUY SMART",  SwingConstants.CENTER);
        p.add(lbl, BorderLayout.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 40));
        lbl.setForeground(new Color(240, 222, 204));

        JButton getStartedBtn= new JButton("Get Started !");
        p.add(getStartedBtn, BorderLayout.SOUTH);
        getStartedBtn.setFont(new Font("Arial", Font.BOLD, 40));
        getStartedBtn.setForeground(new Color(250, 242, 250));
        getStartedBtn.setBackground(new Color(50, 122, 210));
        return p;
    }

    private JPanel CreateProfilePanel() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(BorderFactory.createEmptyBorder(300, 220, 100, 220));

        JLabel title = new JLabel("Your Profile");
        title.setFont(new Font("Segoe UI", Font.BOLD, 30));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField nameField = new JTextField("Adolphe Nayituriki", 20);
        JTextField emailField = new JTextField("www.nayituriki.com@gmail.com", 20);
        JButton updateButton = new JButton("Save changes");

        p.add(title);
        p.add(Box.createVerticalStrut(20));
        p.add(new JLabel("Full Name:")); p.add(nameField);
        p.add(Box.createVerticalStrut(10));
        p.add(new JLabel("Email Address:")); p.add(emailField);
        p.add(Box.createVerticalStrut(15));
        p.add(updateButton);

        return p;
    }

    public JPanel CreateProductsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        // Title banner
        JLabel title = new JLabel("Enjoy Our Products // Select your category ", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 40));
        title.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setOpaque(true);
        title.setBackground(new Color(200, 115, 10));
        title.setForeground(Color.WHITE);

        // Quick Actions Panel
        JPanel actions = new JPanel(new GridLayout(2, 3, 30, 30));
        actions.setBorder(BorderFactory.createEmptyBorder(20, 50, 50, 50));
        actions.setBackground(Color.WHITE);

        JButton electronicsProducts = new JButton("Electronics");
        JButton fashionsButton = new JButton("Fashion");
        JButton agricultureButton = new JButton("Agriculture");
        JButton constructionsButton = new JButton("Construction");
        JButton healthAndBeautyButton = new JButton("Health & Beauty");
        JButton foodsAndBeveragesButton = new JButton("Food & Beverages");
        JButton homeAndKitchen = new JButton("Home & Kitchen");
        JButton booksAndStationery = new JButton("Stationery");

        JButton[] buttons = {electronicsProducts, agricultureButton, constructionsButton, healthAndBeautyButton, fashionsButton, foodsAndBeveragesButton, booksAndStationery, homeAndKitchen};
        for (JButton b : buttons) {
            b.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            b.setFocusPainted(false);
            b.setBackground(new Color(0, 113, 144));
            b.setForeground(Color.WHITE);
            b.addActionListener(e -> new CategoryProductQuery(b.getText(), Session.CurrentUserId));
            actions.add(b);
        }

        // Footer
        JLabel footer = new JLabel("© 2025 E-BUY SMART - All Rights Reserved", JLabel.CENTER);
        footer.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        footer.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        footer.setForeground(new Color(100, 100, 100));

        panel.add(title, BorderLayout.NORTH);
        panel.add(actions, BorderLayout.CENTER);
        panel.add(footer, BorderLayout.SOUTH);
        dispose();

        return panel;
    }

    private JPanel CreateOrderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        // Title
        JLabel title = new JLabel("Your Orders", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 30));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setOpaque(true);
        title.setBackground(new Color(200, 115, 10));
        title.setForeground(Color.WHITE);

        // Create table
        orderTableModel = new DefaultTableModel();
        orderTableModel.setColumnIdentifiers(new String[]{
                "Order ID", "Order Number", "Date", "Status",
                "Total Amount", "Payment Method", "Items", "Notes"
        });

        orderTable = new JTable(orderTableModel);
        orderTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        orderTable.setRowHeight(30);
        orderTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        orderTable.getTableHeader().setBackground(new Color(5, 105, 205));
        orderTable.getTableHeader().setForeground(Color.WHITE);
        orderTable.setGridColor(Color.LIGHT_GRAY);

        // Add status color coding
        DefaultTableCellRenderer statusRenderer = new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (column == 3) { // Status column
                    String status = value != null ? value.toString() : "";
                    switch (status.toLowerCase()) {
                        case "delivered":
                            c.setForeground(new Color(0, 140, 80)); // Green
                            break;
                        case "pending":
                            c.setForeground(new Color(255, 150, 50)); // Orange
                            break;
                        case "processing":
                            c.setForeground(new Color(0, 113, 188)); // Blue
                            break;
                        case "cancelled":
                            c.setForeground(Color.RED);
                            break;
                        default:
                            c.setForeground(Color.GRAY);
                    }
                }
                return c;
            }
        };
        orderTable.getColumnModel().getColumn(3).setCellRenderer(statusRenderer);

        // Add scroll pane
        JScrollPane scrollPane = new JScrollPane(orderTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton refreshButton = new JButton("🔄 Refresh Orders");
        refreshButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        refreshButton.setBackground(new Color(5, 105, 205));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.addActionListener(e -> loadUserOrders());

        JButton viewDetailsButton = new JButton("📋 View Details");
        viewDetailsButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        viewDetailsButton.setBackground(new Color(60, 179, 113));
        viewDetailsButton.setForeground(Color.WHITE);
        viewDetailsButton.addActionListener(e -> viewOrderDetails());

        JButton trackButton = new JButton("🚚 Track Shipment");
        trackButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        trackButton.setBackground(new Color(255, 165, 0));
        trackButton.setForeground(Color.WHITE);
        trackButton.addActionListener(e -> trackShipment());

        buttonPanel.add(refreshButton);
        buttonPanel.add(viewDetailsButton);
        buttonPanel.add(trackButton);

        // Stats panel
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        statsPanel.setBackground(new Color(240, 240, 240));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel statsLabel = new JLabel("Loading statistics...");
        statsLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        statsLabel.setForeground(new Color(60, 60, 60));
        statsPanel.add(statsLabel);

        // Add components to panel
        panel.add(title, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        panel.add(statsPanel, BorderLayout.PAGE_START);

        // Load orders immediately
        loadUserOrders();
        loadOrderStats(statsLabel);

        return panel;
    }

    private void loadUserOrders() {
        orderTableModel.setRowCount(0); // Clear existing data

        try (Connection conn = com.utils.DB.getConnection()) {
            String sql = """
                SELECT 
                    o.OrderID,
                    o.OrderNumber,
                    o.Date,
                    o.Status,
                    o.TotalAmount,
                    o.PaymentMethod,
                    o.Notes,
                    COUNT(oi.ProductID) as ItemCount
                FROM Orders o
                LEFT JOIN Order_Items oi ON o.OrderID = oi.OrderID
                WHERE o.UserID = ?
                GROUP BY o.OrderID, o.OrderNumber, o.Date, o.Status, o.TotalAmount, o.PaymentMethod, o.Notes
                ORDER BY o.OrderID DESC
                """;

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, Session.CurrentUserId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                orderTableModel.addRow(new Object[]{
                        rs.getInt("OrderID"),
                        rs.getString("OrderNumber"),
                        rs.getDate("Date"),
                        rs.getString("Status"),
                        String.format("%,.0f", rs.getDouble("TotalAmount")) + " RWF",
                        rs.getString("PaymentMethod"),
                        rs.getInt("ItemCount") + " items",
                        rs.getString("Notes")
                });
            }

            if (orderTableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this,
                        "You haven't placed any orders yet.\nVisit our products section to start shopping!",
                        "No Orders Found",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Failed to load your orders: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void loadOrderStats(JLabel statsLabel) {
        try (Connection conn = com.utils.DB.getConnection()) {
            String sql = """
                SELECT 
                    COUNT(*) as TotalOrders,
                    SUM(CASE WHEN Status = 'Delivered' THEN 1 ELSE 0 END) as Delivered,
                    SUM(CASE WHEN Status = 'Pending' THEN 1 ELSE 0 END) as Pending,
                    SUM(CASE WHEN Status = 'Processing' THEN 1 ELSE 0 END) as Processing,
                    SUM(CASE WHEN Status = 'Cancelled' THEN 1 ELSE 0 END) as Cancelled,
                    SUM(TotalAmount) as TotalSpent
                FROM Orders 
                WHERE UserID = ?
                """;

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, Session.CurrentUserId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                int totalOrders = rs.getInt("TotalOrders");
                int delivered = rs.getInt("Delivered");
                int pending = rs.getInt("Pending");
                int processing = rs.getInt("Processing");
                int cancelled = rs.getInt("Cancelled");
                double totalSpent = rs.getDouble("TotalSpent");

                String statsText = String.format(
                        "📊 Order Statistics: %d Total Orders | ✅ %d Delivered | ⏳ %d Pending | 🔄 %d Processing | ❌ %d Cancelled | 💰 Total Spent: %,.0f RWF",
                        totalOrders, delivered, pending, processing, cancelled, totalSpent
                );
                statsLabel.setText(statsText);
            }

        } catch (SQLException e) {
            statsLabel.setText("Unable to load statistics");
        }
    }

    private void viewOrderDetails() {
        int selectedRow = orderTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select an order to view details.",
                    "No Order Selected",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int orderId = (int) orderTableModel.getValueAt(selectedRow, 0);

        try (Connection conn = com.utils.DB.getConnection()) {
            // Get order details
            String orderSql = """
                SELECT 
                    o.*,
                    u.FullName,
                    u.Email
                FROM Orders o
                JOIN Users u ON o.UserID = u.UserID
                WHERE o.OrderID = ?
                """;

            PreparedStatement pst = conn.prepareStatement(orderSql);
            pst.setInt(1, orderId);
            ResultSet orderRs = pst.executeQuery();

            if (orderRs.next()) {
                // Get order items
                String itemsSql = """
                    SELECT 
                        oi.*,
                        p.Name as ProductName,
                        p.Description
                    FROM Order_Items oi
                    JOIN Products p ON oi.ProductID = p.ProductID
                    WHERE oi.OrderID = ?
                    """;

                PreparedStatement itemsPst = conn.prepareStatement(itemsSql);
                itemsPst.setInt(1, orderId);
                ResultSet itemsRs = itemsPst.executeQuery();

                StringBuilder details = new StringBuilder();
                details.append("📋 ORDER DETAILS\n");
                details.append("================\n");
                details.append("Order Number: ").append(orderRs.getString("OrderNumber")).append("\n");
                details.append("Order Date: ").append(orderRs.getDate("Date")).append("\n");
                details.append("Status: ").append(orderRs.getString("Status")).append("\n");
                details.append("Payment Method: ").append(orderRs.getString("PaymentMethod")).append("\n");
                details.append("Total Amount: ").append(String.format("%,.0f", orderRs.getDouble("TotalAmount"))).append(" RWF\n");
                details.append("Notes: ").append(orderRs.getString("Notes")).append("\n\n");

                details.append("🛒 ORDER ITEMS\n");
                details.append("================\n");

                double total = 0;
                int itemCount = 0;
                while (itemsRs.next()) {
                    itemCount++;
                    String productName = itemsRs.getString("ProductName");
                    int quantity = itemsRs.getInt("Quantity");
                    double price = itemsRs.getDouble("Price");
                    double itemTotal = quantity * price;
                    total += itemTotal;

                    details.append(itemCount).append(". ").append(productName).append("\n");
                    details.append("   Quantity: ").append(quantity).append(" | ");
                    details.append("Price: ").append(String.format("%,.0f", price)).append(" RWF | ");
                    details.append("Total: ").append(String.format("%,.0f", itemTotal)).append(" RWF\n");
                }

                details.append("\n💰 ORDER SUMMARY\n");
                details.append("================\n");
                details.append("Items: ").append(itemCount).append("\n");
                details.append("Subtotal: ").append(String.format("%,.0f", total)).append(" RWF\n");
                details.append("Order Total: ").append(String.format("%,.0f", orderRs.getDouble("TotalAmount"))).append(" RWF\n");

                // Check shipment status
                String shipmentSql = """
                    SELECT COUNT(*) as ShipmentCount 
                    FROM Shipment 
                    WHERE OrderID = ?
                    """;

                PreparedStatement shipPst = conn.prepareStatement(shipmentSql);
                shipPst.setInt(1, orderId);
                ResultSet shipRs = shipPst.executeQuery();

                if (shipRs.next() && shipRs.getInt("ShipmentCount") > 0) {
                    details.append("\n🚚 SHIPMENT STATUS\n");
                    details.append("================\n");
                    details.append("✅ Items have been shipped\n");
                    details.append("Track your shipment using Order Number: ").append(orderRs.getString("OrderNumber"));
                }

                JOptionPane.showMessageDialog(this,
                        details.toString(),
                        "Order Details - " + orderRs.getString("OrderNumber"),
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Failed to load order details: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void trackShipment() {
        int selectedRow = orderTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select an order to track shipment.",
                    "No Order Selected",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int orderId = (int) orderTableModel.getValueAt(selectedRow, 0);
        String orderNumber = (String) orderTableModel.getValueAt(selectedRow, 1);

        try (Connection conn = com.utils.DB.getConnection()) {
            String sql = """
                SELECT 
                    s.ShipmentID,
                    s.CreatedAt as ShipmentDate,
                    p.Name as ProductName,
                    u.FullName as CustomerName
                FROM Shipment s
                JOIN Products p ON s.ProductID = p.ProductID
                JOIN Users u ON s.UserID = u.UserID
                WHERE s.OrderID = ?
                ORDER BY s.ShipmentID
                """;

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, orderId);
            ResultSet rs = pst.executeQuery();

            StringBuilder shipmentInfo = new StringBuilder();
            shipmentInfo.append("🚚 SHIPMENT TRACKING\n");
            shipmentInfo.append("====================\n");
            shipmentInfo.append("Order Number: ").append(orderNumber).append("\n");
            shipmentInfo.append("Order ID: ").append(orderId).append("\n\n");

            int shipmentCount = 0;
            while (rs.next()) {
                shipmentCount++;
                shipmentInfo.append("Shipment ").append(shipmentCount).append(":\n");
                shipmentInfo.append("  Product: ").append(rs.getString("ProductName")).append("\n");
                shipmentInfo.append("  Shipped Date: ").append(rs.getDate("ShipmentDate")).append("\n");
                shipmentInfo.append("  Status: Shipped ✅\n");
                shipmentInfo.append("  Estimated Delivery: Within 3-5 business days\n\n");
            }

            if (shipmentCount == 0) {
                shipmentInfo.append("No shipment records found for this order.\n");
                shipmentInfo.append("Your order is being processed.\n");
                shipmentInfo.append("Check back later for shipment updates.");
            } else {
                shipmentInfo.append("📦 Total Items Shipped: ").append(shipmentCount).append("\n");
                shipmentInfo.append("📞 For any issues, contact: 0780505948");
            }

            JOptionPane.showMessageDialog(this,
                    shipmentInfo.toString(),
                    "Shipment Tracking - " + orderNumber,
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Failed to track shipment: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        if (src == homeButton) {
            cl.show(contentPanel, "Home");
        } else if (src == profileButton) {
            cl.show(contentPanel, "Profile");
        } else if (src == productsButton) {
            cl.show(contentPanel, "Products");
        } else if (src == orderButton) {
            cl.show(contentPanel, "My Orders");
            // Refresh orders when switching to orders tab
            loadUserOrders();
        } else if (src == logoutButton) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to logout?",
                    "Confirm Logout",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new Users().setVisible(true);
            }
        }
    }

    public static void main(String[] args) {
        UserDashboard usersDashboard = new UserDashboard();
    }
}