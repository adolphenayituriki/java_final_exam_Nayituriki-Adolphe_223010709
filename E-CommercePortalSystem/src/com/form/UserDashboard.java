package com.form;
import com.utils.Session;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class UserDashboard extends JFrame implements ActionListener {

    private final JButton homeButton = new JButton("Home");
    private final JButton profileButton = new JButton("Profile");
    private final JButton productsButton = new JButton("Products");
    private final JButton orderButton = new JButton("My orders");
    private final JButton logoutButton = new JButton("Logout");

    //main content area (where pages will change)
    private final JPanel contentPanel = new JPanel(new CardLayout());
    private JLabel welcomeLabel;

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
        //ADDING EVENT LISTENER
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

        // 🟣 Title banner
        JLabel title = new JLabel("Enjoy Our Products // Select your category ", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 40));
        title.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setOpaque(true);
        title.setBackground(new Color(200, 115, 10));
        title.setForeground(Color.WHITE);

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
            b.setBackground(new Color(0, 113, 144));
            b.setForeground(Color.WHITE);
            b.addActionListener(e -> new CategoryProductQuery(b.getText(), Session.CurrentUserId));
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
        dispose();

        return panel;
    }

    private JPanel CreateOrderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel title = new JLabel("Your oders visible here!", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 40));
        title.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setOpaque(true);
        title.setBackground(new Color(200, 115, 10));
        title.setForeground(Color.BLUE);

        panel.add(title);

        return panel;
    }

    //action to be implemented

    @Override
    public void actionPerformed(ActionEvent e) {

        Object src = e.getSource();
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        if (src == homeButton) {
            cl.show(contentPanel, "Home");
        }else if (src == profileButton) {
            cl.show(contentPanel, "Profile");
        }else if (src == productsButton) {
            cl.show(contentPanel, "Products");

        }   else if (src == orderButton) {
            cl.show(contentPanel, "My Orders");
        }else if (src == logoutButton) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Confirm Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new Users().setVisible(true);
            }
        }

    }
    public static void  main(String[] args) {
        UserDashboard usersDashboard = new UserDashboard();
    }

}
