package com.form;

import com.panel.AdminPanel;
import com.utils.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class Users extends JFrame implements ActionListener {
    //User interface components;
        JLabel header = new JLabel("E-BUY SMART");
        JLabel username = new JLabel("Username:");
        JTextField usernameTxt = new JTextField();

        JLabel password = new JLabel("Password");
        JPasswordField passwordTxt = new JPasswordField();

        JButton login = new JButton("Login");
        JButton register = new JButton("Register");
        JCheckBox check = new JCheckBox("Show me password");
        private char defaultEchoChar;
        JLabel footer = new JLabel("© 2025 E-Commerce Portal System");

        //This is a constructor of the "User" class
        public Users() {
           setTitle("BUY SMART E-BUY");
           setBounds(100, 100, 450, 310);
           setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           setVisible(true);
           setLayout(null);
           setResizable(false);
           //positioning the components
            header.setBounds(0, 10, 450, 50);
            header.setFont(new Font("Arial", Font.BOLD, 30));
            header.setForeground(new Color(255, 255, 255));
            header.setBackground(new Color(0, 75, 25));
            header.setOpaque(true);
            header.setHorizontalAlignment(JLabel.CENTER);

            username.setBounds(50, 70, 100, 30);
            usernameTxt.setBounds(170, 70, 150, 30);

            password.setBounds(50, 120, 100, 30);
            passwordTxt.setBounds(170, 120, 150, 30);

            login.setBounds(50, 190, 100, 30);
            login.setCursor(new Cursor(Cursor.HAND_CURSOR));
            register.setBounds(170, 190, 100, 30);
            register.setCursor(new Cursor(Cursor.HAND_CURSOR));
            check.setBounds(170, 160, 200, 30);
            check.setBackground(getBackground());
            defaultEchoChar = passwordTxt.getEchoChar();

            footer.setBounds(100, 230, 450, 50);
            footer.setForeground(new Color(190, 117, 2));

           // adding components to the window
            add(header); add(username); add(usernameTxt); add(password); add(passwordTxt);
            add(login); add(register); add(footer); add(check);

            //Adding action listeners
           login.addActionListener(this);
           register.addActionListener(this);
           check.addActionListener(this);
           this.add(login); this.add(username); this.add(password); this.add(register);

        }
        //password hashing
        private String hashPassword(String password) throws Exception {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        }

    @Override
    public void actionPerformed(ActionEvent e) {
        //checkbox actions
        if (e.getSource() == check) {
            if (check.isSelected()) {
                passwordTxt.setEchoChar((char) 0);
                check.setText("Hide me password");
                check.setForeground(new Color(250, 75, 25));
            }
            else {
                passwordTxt.setEchoChar(defaultEchoChar);
                check.setText("Show me password");
                check.setForeground(Color.BLACK);
            }
            return;
        }

        //Login and register buttons action

           if (e.getSource() == register) {
                dispose();
                new UsersRegister().setVisible(true);
            }

           else if (e.getSource() == login) {
               String uname = usernameTxt.getText();
               String pass = new String(passwordTxt.getPassword());

               try (Connection conn = com.utils.DB.getConnection()){
                   String sql = "SELECT * FROM users WHERE Username = ? AND PasswordHash = ?";
                   var ps = conn.prepareStatement(sql);

                   ps.setString(1, uname);
                   ps.setString(2, pass);
                   String hashedPassword = hashPassword(pass);
                   ps.setString(2, hashedPassword);

                   var rs = ps.executeQuery();
                   if (rs.next()){
                       JOptionPane.showMessageDialog(null, "✅ Login successful!\nWelcome ! "+ rs.getString("FullName"));

                       //save session info
                       com.utils.Session.CurrentUserId = rs.getInt("UserID");
                       com.utils.Session.CurrentUserFullName = rs.getString("FullName");
                       com.utils.Session.CurrentUserEmail = rs.getString("Email");
                       com.utils.Session.CurrentUserRole = rs.getString("Role");

                       //update last login time
                       String updateSql =   "UPDATE users SET LastLogin = NOW() WHERE UserID = ?";
                       var updatePs = conn.prepareStatement(updateSql);
                       updatePs.setInt(1, rs.getInt("UserID"));
                       updatePs.executeUpdate();

                       dispose();
                       //Chose dashboard based on role
                       if ("Admin".equalsIgnoreCase(rs.getString("Role"))) {
                           new com.panel.AdminPanel(rs.getString("FullName")).setVisible(true);
                       }else {
                           new UserDashboard().setVisible(true);
                       }


                       // TODO: Open your dashboard here
                   }

                   else{
                       JOptionPane.showMessageDialog(null, "❌ Invalid username or password!");
                   }
               }catch (Exception ex) {
                   ex.printStackTrace();
                   JOptionPane.showMessageDialog(null, "⚠️ Database error: " + ex.getMessage());
               }
           }
           setLocationRelativeTo(null);
    }
}

