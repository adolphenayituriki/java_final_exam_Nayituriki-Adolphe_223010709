package com.form;

import javax.swing.*;
import java.awt.*;
import java.security.MessageDigest;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class UsersRegister extends JFrame implements ActionListener {
    //User interface components
    JLabel header = new JLabel("E-BUY SMART|SignUp");
    JLabel username = new JLabel("Username");
    JTextField usernameTxt = new JTextField();

    JLabel password = new JLabel("Password:");
    JPasswordField passwordTxt = new JPasswordField();

    JLabel email = new JLabel("Email");
    JTextField emailTxt = new JTextField();

    JLabel fullname = new JLabel("Full name:");
    JTextField fullnameTxt = new JTextField();


    JLabel role = new JLabel("Select Role:");
    String[] roles = {"Admin", "Customer"};
    JComboBox<String> roleCombo =  new JComboBox<>(roles);

    JButton login = new JButton("Login");
    JButton register = new JButton("Register");
    JLabel footer = new JLabel("© 2025 E-Commerce Portal System | Register Now!");

    //This is a constructor of the "User" class
    public UsersRegister() {
        setTitle("BUY SMART E-BUY");
        setBounds(100, 100, 450, 440);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(false);
        setLayout(null);
        setResizable(false);
        //positioning the components
        header.setBounds(100, 10, 450, 50);
        header.setFont(new Font("Self", Font.BOLD, 30));
        header.setForeground(new Color(0, 25, 25));

        username.setBounds(50, 70, 100, 30);
        usernameTxt.setBounds(170, 70, 150, 30);

        password.setBounds(50, 120, 100, 30);
        passwordTxt.setBounds(170, 120, 150, 30);

        login.setBounds(50, 320, 100, 30);
        login.setCursor(new Cursor(Cursor.HAND_CURSOR));
        register.setBounds(170, 320, 100, 30);
        register.setCursor(new Cursor(Cursor.HAND_CURSOR));

        email.setBounds(50, 190, 100, 30);
        emailTxt.setBounds(170, 190, 150, 30);

        fullname.setBounds(50, 230, 100, 30);
        fullnameTxt.setBounds(170, 230, 150, 30);

        role.setBounds(50, 270, 100, 30);
        roleCombo.setBounds(170, 270, 150, 30);

        footer.setBounds(100, 350, 450, 50);
        footer.setForeground(new Color(190, 117, 2));

        // adding components to the window
        add(header);
        add(username);
        add(usernameTxt);
        add(password);
        add(passwordTxt);
        add(login);
        add(register);
        add(footer);
        add(email);
        add(emailTxt);
        add(fullname);
        add(fullnameTxt);
        add(role);
        add(roleCombo);

        //Adding action listeners
        login.addActionListener(this);
        register.addActionListener(this);

    }

    //password hashing method
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == login) {
            dispose();
            new Users().setVisible(true);
        }else if (e.getSource() == register) {
            String uname = usernameTxt.getText();
            String pass = hashPassword(new String(passwordTxt.getPassword()));
            String mail = emailTxt.getText();
            String fullname = fullnameTxt.getText();
            String role = (String) roleCombo.getSelectedItem();

            //validation
            if (uname.isEmpty() || pass.isEmpty() || fullname.isEmpty() || role.isEmpty()) {
                JOptionPane.showMessageDialog(null, "⚠\uFE0F Please fill all the fields");
                return;
            }
            try(Connection conn = com.utils.DB.getConnection()){
                String sql = "INSERT INTO users (Username, PasswordHash, Email, FullName, Role) VALUES (?, ?, ?, ?, ?)";
                        var ps = conn.prepareStatement(sql);
                ps.setString(1, uname);
                ps.setString(2, pass);
                ps.setString(3, mail);
                ps.setString(4, fullname);
                ps.setString(5, role.isEmpty() ? "Customer" : role);
                if(role.equals("Admin")){
                    int confirm = JOptionPane.showConfirmDialog(this, "Please ❌ you are not allowed to register as admin \n "+
                     "Contact this number for help 0780505948! \n\nWould you like to repeat?");
                    if (confirm == JOptionPane.YES_OPTION) {
                        dispose();
                        UsersRegister usersRegister = new UsersRegister();
                        usersRegister.setVisible(true);

                    }else  {
                        dispose();
                        new Users().setVisible(true);
                    }


                }else {

                int rows = ps.executeUpdate();
                if (rows > 0) {

                    //progress bar before confirmation message
                    JProgressBar progressBar = new JProgressBar(0,100);
                    progressBar.setStringPainted(true);
                    JOptionPane pane = new JOptionPane(progressBar,JOptionPane.INFORMATION_MESSAGE,JOptionPane.DEFAULT_OPTION,null,new Object[]{},null);
                    JDialog dialog = pane.createDialog("Registration Processing....");
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
                    JOptionPane.showMessageDialog(null, "✅ Registration successful!");
                    dispose();
                    new Users().setVisible(true);
                } else{
                    JOptionPane.showMessageDialog(null," Registration failed.");
                }}
            } catch (Exception ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "⚠\uFE0F Database error:" + ex.getMessage());
            }
        }
        setLocationRelativeTo(null);

    }
    public static void main(String[] args){
        UsersRegister users = new UsersRegister();
    }
}

