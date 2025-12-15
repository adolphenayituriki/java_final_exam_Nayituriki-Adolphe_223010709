package com.form;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductPanel extends JFrame implements ActionListener {

    JTabbedPane tabbedPane = new JTabbedPane();

    public ProductPanel() {
        setTitle("E-BUY SMART | Main Dashboard");
        setBounds(100, 100, 800, 600);
        setLayout(new BorderLayout());


        //MENU BAR
        JMenuBar menuBar = new JMenuBar();

        JMenu products = new JMenu("Products");
        products.add(new JMenuItem("Vegetables"));
        products.add(new JMenuItem("Dressing"));
        products.add(new JMenuItem("Sandwich"));
        products.add(new JMenuItem("Fish"));
        products.add(new JMenuItem("Electronics"));


        JMenuItem orderItem = new JMenuItem("Order Product");
        JMenuItem payItem = new JMenuItem("Pay Product");
        products.add(orderItem);
        products.add(payItem);

        JMenu profileMenu = new JMenu("Profile");
        JMenuItem editProfile = new JMenuItem("Edit Profile");
        JMenuItem logout = new JMenuItem("Logout");
        profileMenu.add(editProfile);
        profileMenu.add(logout);

        JMenu paymentMenu = new JMenu("Payment methods");

        JMenu MoreAboutUs = new JMenu("Payment methods");


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
        menuBar.add(products);
        menuBar.add(profileMenu);
        menuBar.add(toolsMenu);
        menuBar.add(helpMenu);
        menuBar.add(extraMenu);
        setJMenuBar(menuBar);

        //TABS
        tabbedPane.add("Products", new JLabel("You are most welcome on this E-BUY! "));

        tabbedPane.add("Cart", new JLabel("Cart Page"));
        tabbedPane.add("Help", new JLabel("Help Page"));
        tabbedPane.add("Help", new JLabel("Settings"));
        tabbedPane.add("Profile", new JLabel("Profile Page"));

        add(tabbedPane);

        //EXIT ACTION
        exitItem.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Exit Application?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION)
                System.exit(0);
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // You can add your button or menu actions here later
    }

    public static void main(String[] args) {
    }
}
