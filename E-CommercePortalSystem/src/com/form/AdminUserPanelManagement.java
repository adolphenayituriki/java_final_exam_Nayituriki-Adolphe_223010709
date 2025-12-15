package com.form;

import com.panel.AddProductPanel;
import com.panel.AdminPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.nio.file.*;
import java.sql.*;
import java.util.Optional;

public class AdminUserPanelManagement extends JFrame implements ActionListener {
    private JTabbedPane tabbedPane = new JTabbedPane();

    private JPanel usersPanel = new JPanel();
    private JTable userTable = new JTable();
    private JScrollPane usersScrollPane = new JScrollPane(userTable);
    private JTextField searchField = new JTextField(20);
    private JButton searchBtn = new JButton("🔍 Search User");
    private JButton refreshBtn = new JButton("🔄 Refresh");
    private JButton editBtn = new JButton("✏️ Edit");
    private JButton addUserBtn = new JButton("\uD83C\uDF4A Add User");
    private JButton deleteBtn = new JButton("🗑️ Delete User");
    private JLabel totalUsers = new JLabel("Total Users: 0");
    private JButton returnBackBtn = new JButton("⬅\uFE0F BACK TO HOME");


    public AdminUserPanelManagement() {
        setTitle("Admin Dashboard | E-BUY SMART");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initUsersPanel();

        add(tabbedPane, BorderLayout.CENTER);

        // Event listeners
        refreshBtn.addActionListener(this);
        searchBtn.addActionListener(this);
        editBtn.addActionListener(this);
        deleteBtn.addActionListener(this);
        returnBackBtn.addActionListener(this);
        addUserBtn.addActionListener(this);

        // load initial data
        loadUsers();
        setVisible(true);
    }

    private void initUsersPanel() {
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
        searchPanel.add(new JLabel("Search  User:"));
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);
        searchPanel.add(refreshBtn);
        header.add(searchPanel, BorderLayout.SOUTH);

        usersPanel.add(header, BorderLayout.NORTH);

        // Actions panel buttons at the bottoms (edit, delete, total)
        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actionsPanel.add(editBtn);
        actionsPanel.add(deleteBtn);
        actionsPanel.add(addUserBtn);
        actionsPanel.add(Box.createHorizontalStrut(20));
        actionsPanel.add(totalUsers);
        usersPanel.add(actionsPanel, BorderLayout.SOUTH);

        tabbedPane.addTab("Users", usersPanel);
    }


    // method to hash entered password
    private String hashPassword(String password) throws Exception {
        java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = md.digest(password.getBytes());
        StringBuffer sb = new StringBuffer();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    private void showAddUserDialog() {
        JTextField userNameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField fullNameField = new JTextField();
        JTextField passwordField = new JTextField();
        String[] roles = new String[]{"Admin", "User"};
        JComboBox<String> roleComboBox = new JComboBox<>(roles);

        Object[] fields = {
                "Username: ", userNameField,
                "Email: ", emailField,
                "Full Name: ", fullNameField,
                "Password: ", passwordField,
                "Role: ", roleComboBox
        };
        int option = JOptionPane.showConfirmDialog(
                this,
                fields,
                "Add new user",
                JOptionPane.OK_CANCEL_OPTION
        );
        if (option == JOptionPane.OK_OPTION) {
            String userName = userNameField.getText().trim();
            String email = emailField.getText().trim();
            String fullName = fullNameField.getText().trim();
            String password =  passwordField.getText().trim();
            String role = roleComboBox.getSelectedItem().toString();

            if (userName.isEmpty() || email.isEmpty() || fullName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all the fields");
                return;
            }
            try(Connection conn = com.utils.DB.getConnection()){
                String hashedPassword = hashPassword(password);
                String query = "INSERT INTO users(Username, PasswordHash, Email, FullName, Role, CreatedAt) VALUES (?, ?, ?, ?, ?, Now())";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, userName);
                ps.setString(2, hashedPassword);
                ps.setString(3, email);
                ps.setString(4, fullName);
                ps.setString(5, role);

                int rows = ps.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(this, "User has been added successfully");
                }
                else  {
                    JOptionPane.showMessageDialog(this, "Failed to add user");
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database error");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }



    private void deleteUser(int productId) {
        try(Connection conn = com.utils.DB.getConnection()){
            String query = "DELETE FROM users WHERE UserID = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, productId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to delete User");
        }
    }

    //Editing User information

    private void showEditUserDialog(int userId, String userName, String email, String fullName, String role) {
        JTextField userNameField = new JTextField(userName);
        JTextField emailField = new JTextField(email);
        JTextField fullNameField = new JTextField(fullName);
        String[] roles = new String[]{"Admin", "Customer"};
        JComboBox<String> roleComboBox = new JComboBox<>(roles);
        roleComboBox.setSelectedItem(role);
        Object[] fields = {
                "Username: ", userNameField,
                "Email: ", emailField,
                "Full Name: ", fullNameField,
                "Role: ", roleComboBox
        };
        int option = JOptionPane.showConfirmDialog(this, fields,"Edit User",JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String newUsername = userNameField.getText().trim();
            String newEmail = emailField.getText().trim();
            String newFullName = fullNameField.getText().trim();
            String newRole = roleComboBox.getSelectedItem().toString();

            if (newUsername.isEmpty() || newEmail.isEmpty() || newFullName.isEmpty()  || newRole.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all the fields");
                return;
            }
            try(Connection conn = com.utils.DB.getConnection()){
                String query = "UPDATE users SET Username = ?, Email = ?, FullName = ?, Role = ? WHERE UserID = ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, newUsername);
                ps.setString(2, newEmail);
                ps.setString(3, newFullName);
                ps.setString(4, newRole);
                ps.setInt(5, userId);

                int updated = ps.executeUpdate();
                if (updated > 0) {
                    JOptionPane.showMessageDialog(this, "User has been updated successfully");
                }
                else  {
                    JOptionPane.showMessageDialog(this, "Failed to update User");
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database error");
            
            }

        }
    }
    private void editUser(int UserID,  String Username, String PasswordHash, String Email, String FullName, String Role) {
        try(Connection conn = com.utils.DB.getConnection()){
            String query = "UPDATE users SET Username = ?, PasswordHash = ?, Email = ?, FullName = ?, Role = ? WHERE UserID = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, Username);
            ps.setString(2, PasswordHash);
            ps.setString(3, Email);
            ps.setString(4, FullName);
            ps.setString(5, Role);
            ps.setInt(6, UserID);

            int row = ps.executeUpdate();
            if (row > 0) {
                JOptionPane.showMessageDialog(this, "✅ User Updated Successfully");
                loadUsers();
            }else  {
                JOptionPane.showMessageDialog(this, "No User was Updated");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to Update User");
        }
    }


    //loading products
    public void loadUsers() {
        // Table model & table
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"UserId", "Username", "Password", "Email", "Full Name", "Role", "Created At", "Last Login"});
        userTable.setModel(model);
        userTable.setAutoCreateRowSorter(true);
        userTable.setRowHeight(26);
        userTable.setFillsViewportHeight(true);
        userTable.setGridColor(Color.LIGHT_GRAY);

        try (Connection conn = com.utils.DB.getConnection()) {
            String sql = "SELECT UserId, Username, PasswordHash, Email, FullName, Role,CreatedAt, LastLogin  FROM users ORDER BY UserID ASC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("UserId"),
                        rs.getString("Username"),
                        rs.getString("PasswordHash"),
                        rs.getString("Email"),
                        rs.getString("FullName"),
                        rs.getString("Role"),
                        rs.getTimestamp("CreatedAt"),
                        rs.getTimestamp("LastLogin"),
                });
            }
            // center align ProductID
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
            userTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

            usersPanel.add(usersScrollPane, BorderLayout.CENTER);

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "⚠️ Failed to load User: " + ex.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        //adding product to the list
        if (src == addUserBtn) {
            showAddUserDialog();
        }
        // this if where i refreshed my products
        else if (src == refreshBtn) {
            loadUsers();

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
            JOptionPane.showMessageDialog(this, "Now your Users are refreshed well!");
        }
        // condition to search a product
        else if (src == searchBtn) {
            String search = searchField.getText().trim();
            if (search.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a user name!");
                return;
            }
            searchField.setText(search);
        } else if (src == editBtn) {
            int row = userTable.getSelectedRow();
            if (row ==-1) {
                JOptionPane.showMessageDialog(this, "Please select a user to edit!");
                return;
            }
            int userId = (int)userTable.getValueAt(row, 0);
            String username = (String)userTable.getValueAt(row, 1);
            String passwordHash = (String)userTable.getValueAt(row, 2);
            String email = (String)userTable.getValueAt(row, 3);
            String fullName = (String)userTable.getValueAt(row, 4);
            String role = (String)userTable.getValueAt(row, 5);
            showEditUserDialog(userId, username, email, fullName, role);
        }
        else if (src == deleteBtn) {
            int row = userTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select a User to be deleted!");
                return;
            }
            int productID = (int) userTable.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Delete this User?");
            if (confirm == JOptionPane.YES_OPTION){
                deleteUser(productID);
                loadUsers();
                JOptionPane.showMessageDialog(this, "User deleted successfully !");
            }}
        else if (src == returnBackBtn) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?");
            if (confirm == JOptionPane.YES_OPTION){
                dispose();
                new AdminPanel("Admin").setVisible(true);

            }
        }

    }

    private void deleteBtn(int productID) {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AdminUserPanelManagement::new);
    }
}

