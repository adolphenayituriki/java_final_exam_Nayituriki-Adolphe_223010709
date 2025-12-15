package com.form;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainDashboard extends JFrame implements ActionListener {

    JTabbedPane tabbedPane = new JTabbedPane();

    public MainDashboard() {
        setTitle("E-BUY SMART | Main Dashboard");
        setBounds(100, 100, 1000, 700);
        setLayout(new BorderLayout());

        // ✅ MENU BAR
        JMenuBar menuBar = new JMenuBar();

        JMenuItem orderItem = new JMenuItem("Order Product");
        JMenuItem payItem = new JMenuItem("Pay Product");

        JMenu profileMenu = new JMenu("Profile");
        JMenuItem editProfile = new JMenuItem("Edit Profile");
        JMenuItem logout = new JMenuItem("Logout");
        profileMenu.add(editProfile);
        profileMenu.add(logout);

        JMenu toolsMenu = new JMenu("Tools");
        JMenuItem historyItem = new JMenuItem("History");
        JMenuItem exportItem = new JMenuItem("Export");
        toolsMenu.add(historyItem);
        toolsMenu.add(exportItem);

        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        helpMenu.add(aboutItem);

        JMenu extraMenu = new JMenu("Options");
        JMenuItem settingsItem = new JMenuItem("Settings");
        JMenuItem themeItem = new JMenuItem("Themes");
        JMenuItem exitItem = new JMenuItem("Exit");
        extraMenu.add(settingsItem);
        extraMenu.add(themeItem);
        extraMenu.add(exitItem);

        // add all menus
        menuBar.add(profileMenu);
        menuBar.add(toolsMenu);
        menuBar.add(helpMenu);
        menuBar.add(extraMenu);
        setJMenuBar(menuBar);

        // ✅ Add a Dashboard tab (Full-Page Panel)
        tabbedPane.add("Dashboard", createDashboardPanel());
        tabbedPane.add("Products", new JLabel("Products Page Coming Soon..."));
        tabbedPane.add("Cart", new JLabel("Cart Page"));
        tabbedPane.add("Help", new JLabel("Help Page"));
        tabbedPane.add("Profile", new JLabel("Profile Page"));

        add(tabbedPane, BorderLayout.CENTER);

        // ✅ Exit action
        exitItem.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Exit Application?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) System.exit(0);
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // ✅ Build Dashboard UI Panel
    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        // 🟣 Title banner
        JLabel title = new JLabel("Welcome to E-BUY SMART", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0));

        // 🟡 Quick Actions Panel
        JPanel actions = new JPanel(new GridLayout(2, 3, 30, 30));
        actions.setBorder(BorderFactory.createEmptyBorder(20, 50, 50, 50));
        actions.setBackground(Color.WHITE);

        JButton electronicsProducts = new JButton("Electronics");
        JButton fashionsButton = new JButton("Fashions");
        JButton agricultureButton = new JButton("Agriculture");
        JButton constructionsButton = new JButton("Constructions");
        JButton healthAndBeautyButton = new JButton("Health & Beauty");
        JButton foodsAndBeveragesButton = new JButton("Foods and Beverages");
        JButton homeAndKitchen = new JButton("Home & Kitchen");
        JButton   booksAndStationery= new JButton("Shopping");

        // Make buttons look nicer
        JButton[] buttons = {electronicsProducts, agricultureButton, constructionsButton, healthAndBeautyButton, fashionsButton, foodsAndBeveragesButton, booksAndStationery, homeAndKitchen};
        for (JButton b : buttons) {
            b.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            b.setFocusPainted(false);
            b.setBackground(new Color(66, 133, 244));
            b.setForeground(Color.WHITE);
            b.setPreferredSize(new Dimension(200, 60));
            actions.add(b);
        }

        // 🟢 Footer
        JLabel footer = new JLabel("© 2025 E-BUY SMART - All Rights Reserved", JLabel.CENTER);
        footer.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        footer.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        footer.setForeground(new Color(100, 100, 100));

        panel.add(title, BorderLayout.NORTH);
        panel.add(actions, BorderLayout.CENTER);
        panel.add(footer, BorderLayout.SOUTH);

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Add menu actions here if needed
    }

    public static void main(String[] args) {
        new MainDashboard();
    }
}
