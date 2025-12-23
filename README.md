# PROJECT: E-Commerce Portal System
# SYSTEM NAME: E-BUY SMART

[![Java](https://img.shields.io/badge/Java-17-blue)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-orange)](https://dev.mysql.com/downloads/mysql/)
[![License](https://img.shields.io/badge/License-Educational-green)](#license)

E-BUY SMART is a desktop-based e-commerce management system developed using Java Swing. It provides a multi-tabbed interface for administrators to efficiently manage users, products, orders, and payments. Customers can register, log in, browse products by category, place orders, and make payments securely. The system connects to a MySQL database through JDBC, ensuring reliable and secure data storage and retrieval. Administrators can perform CRUD operations on all entities and generate reports in CSV/Excel format. The application incorporates role-based access control and password hashing to enhance security. Its intuitive interface simplifies navigation and reduces manual errors in business operations. Overall, E-BUY SMART automates core e-commerce processes, improving efficiency, accuracy, and the overall user experience.

**WIREFRAME SYSTEM DESIGN LINK: https://balsamiq.cloud/s2st4s4/pi50kiy/rBD8A**

---

## Table of Contents
- [Features](#features)
- [System Requirements](#system-requirements)
- [Installation](#installation)
- [Usage](#usage)
- [Package Structure](#package-structure)
- [Database Design](#database-design)
- [Future Enhancements](#future-enhancements)

---

## Features
- **User Management:** Register, login, view, update, and delete users. Admin-only access for user management.
- **Product Management:** Add, edit, delete, search products, and categorize inventory.
- **Order & Payment Management:** Customers place orders; admin can approve/reject, confirm payment, and manage shipments.
- **Reporting:** Export data (users, products, orders, payments) to CSV/Excel for analysis.
- **GUI:** Multi-tabbed interface for admin dashboard; menu-driven for easy navigation.
- **Security:** Password hashing, role-based access, prepared statements to prevent SQL injection.
- **Session Management:** Tracks logged-in users and restricts features based on roles.

---

## System Requirements
- Java JDK 17+
- IntelliJ IDEA (or any Java IDE)
- MySQL 8.0
- WAMP/XAMPP Server
- MySQL JDBC Connector
- Windows 10/11 (tested)
- Minimum 8GB RAM recommended

---

## Installation

1. **Clone the repository**
```bash
git clone https://github.com/adolphenayituriki/E-CommercePortalSystem.git
Setup Database

Start WAMP/XAMPP server.

Create a database using the provided SQL script (ECommerceDB.sql) in the database/ folder.

Update the DB.java file in com.utils with your database username and password.

Open Project

Open in IntelliJ IDEA.

Add MySQL JDBC Connector to the project libraries.

Run Application

Launch the main class: com.panel.AdminPanel.java.

Login as Admin or Customer using test credentials.

Usage
Admin Login
Username: Adolphe

Password: 123

Customer Login
Username: Evode

Password: 112

Customer Registration
Click Register on login screen.

Fill in required fields and submit.

Admin Operations
Manage Users, Products, Orders, Payments.

Approve/reject orders, export reports to CSV.

Update product inventory and categories.

Customer Operations
Browse products by category.

Place and cancel orders.

View payment details.

Package Structure
graphql
Copy code
E-CommercePortalSystem/
├─ src/
│  ├─ com.form/         # Main GUI forms and user interface components
│  │  ├─ AdminUserPanelManagement.java
│  │  ├─ AdminProductPanel.java
│  │  ├─ Users.java
│  │  └─ UsersRegister.java
│  │  ├─ BackgroundPanel.java
│  │  ├─ UserDashboard.java
│  │  ├─ CategoryProductQuery.java
│  │
│  ├─ com.panel/        # Multi-tab panels for Admin
│  │  ├─ AdminPanel.java
│  │  ├─ OrderManagementPanel.java
│  │  └─ AddProductPanel.java
│  │
│  ├─ com.utils/        # Utilities: DB connection & session management
│  │  ├─ DB.java
│  │  └─ Session.java
│  │
│  ├─ com.test/
│  │  ├─ TestUserRegister.java    # Testing classes
│  │  └─ TestUsers.java
│  │
│  └─ com.images/       # Image resources for GUI (icons, backgrounds)
├─ database/
│  └─ E-Commerce_portal_system.sql   # SQL scripts to create tables and initial data
├─ resources/
│  └─ screenshots/      # Screenshot placeholders for README
├─ README.md
└─ LICENSE
Database Design
Main Tables:

Users (UserID, FullName, Email, Password, Role, LastLogin)

Categories (CategoryID, CategoryName)

Products (ProductID, Name, CategoryID, Price, Quantity)

Orders (OrderID, UserID, Status, Date)

Order_Items (OrderItemID, OrderID, ProductID, Quantity, Price)

Payments (PaymentID, OrderID, Amount, Status, Date)

Shipment (ShipmentID, OrderID, ShipmentStatus, Date)

Relationships use foreign keys and enforce data integrity. Passwords are hashed using SHA-256.

Screenshots
(Add actual screenshots in your repository for better visualization)

Login Screen

Admin Dashboard

User Management

Product Management

Order Management

Customer Dashboard

Future Enhancements
Online payment gateway integration (PayPal, Stripe, Mobile Money)

Responsive web or mobile version

Advanced reporting dashboard with graphs and analytics

Real-time order tracking and notifications

Support for multiple languages

License
This project is open-source for educational purposes. See LICENSE file for details.

 
