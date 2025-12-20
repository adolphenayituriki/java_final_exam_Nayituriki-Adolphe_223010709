-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Dec 20, 2025 at 12:44 PM
-- Server version: 8.3.0
-- PHP Version: 8.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `e_commerce_portal_system`
--

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
CREATE TABLE IF NOT EXISTS `categories` (
  `CategoryID` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(100) NOT NULL,
  `Description` text,
  `CreatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`CategoryID`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`CategoryID`, `Name`, `Description`, `CreatedAt`) VALUES
(1, 'Electronics', 'Smartphones, laptops, electronics, and accessories', '2025-12-20 12:05:26'),
(2, 'Fashion', 'Clothing, accessories, and fashion items', '2025-12-20 12:05:26'),
(3, 'Food & Beverages', 'Food items, drinks, and beverages', '2025-12-20 12:05:26'),
(4, 'Home & Kitchen', 'Home appliances and kitchenware', '2025-12-20 12:05:26'),
(5, 'Health & Beauty', 'Health products and beauty items', '2025-12-20 12:05:26'),
(6, 'Agriculture', 'Farm supplies, seeds, and agricultural products', '2025-12-20 12:05:26'),
(7, 'Stationery', 'Books, notebooks, and office supplies', '2025-12-20 12:05:26'),
(8, 'Construction', 'Building materials and construction supplies', '2025-12-20 12:05:26');

-- --------------------------------------------------------

--
-- Stand-in structure for view `orderdetails`
-- (See below for the actual view)
--
DROP VIEW IF EXISTS `orderdetails`;
CREATE TABLE IF NOT EXISTS `orderdetails` (
`Customer` varchar(100)
,`Date` date
,`ItemTotal` decimal(20,2)
,`OrderID` int
,`OrderNumber` varchar(50)
,`OrderStatus` enum('Pending','Processing','Delivered','Cancelled')
,`PaymentMethod` varchar(50)
,`ProductID` int
,`ProductName` varchar(200)
,`Quantity` int
,`TotalAmount` decimal(10,2)
,`UnitPrice` decimal(10,2)
);

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
CREATE TABLE IF NOT EXISTS `orders` (
  `OrderID` int NOT NULL AUTO_INCREMENT,
  `UserID` int NOT NULL,
  `OrderNumber` varchar(50) NOT NULL,
  `Date` date NOT NULL,
  `Status` enum('Pending','Processing','Delivered','Cancelled') DEFAULT 'Pending',
  `TotalAmount` decimal(10,2) NOT NULL,
  `PaymentMethod` varchar(50) DEFAULT NULL,
  `Notes` text,
  `CreatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`OrderID`),
  UNIQUE KEY `OrderNumber` (`OrderNumber`),
  KEY `UserID` (`UserID`)
) ENGINE=MyISAM AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`OrderID`, `UserID`, `OrderNumber`, `Date`, `Status`, `TotalAmount`, `PaymentMethod`, `Notes`, `CreatedAt`) VALUES
(1, 2, 'ORD-202501', '2025-01-18', 'Delivered', 120000.00, 'Mobile Money', 'Order for Infinix Smart 8', '2025-12-20 12:12:18'),
(2, 2, 'ORD-202502', '2025-01-19', 'Processing', 45000.00, 'Credit Card', 'Order for Samsung Galaxy A15', '2025-12-20 12:12:18'),
(3, 2, 'ORD-202503', '2025-01-20', 'Pending', 6500.00, 'Cash', 'Order for Classic T-shirt', '2025-12-20 12:12:18'),
(4, 3, 'ORD-202504', '2025-01-21', 'Delivered', 35000.00, 'Mobile Money', 'Order for Cooking Pot', '2025-12-20 12:12:18'),
(5, 3, 'ORD-202505', '2025-01-22', 'Delivered', 8000.00, 'Mobile Money', 'Order for Body Lotion', '2025-12-20 12:12:18'),
(6, 3, 'ORD-202506', '2025-01-23', 'Cancelled', 12000.00, 'Credit Card', 'Order for Maize Seeds', '2025-12-20 12:12:18'),
(7, 4, 'ORD-202507', '2025-01-24', 'Delivered', 35000.00, 'Cash', 'Order for Exercise Books', '2025-12-20 12:12:18'),
(8, 4, 'ORD-202508', '2025-01-25', 'Processing', 15000.00, 'Mobile Money', 'Order for Cement', '2025-12-20 12:12:18'),
(9, 4, 'ORD-202509', '2025-01-26', 'Pending', 220000.00, 'Credit Card', 'Order for Samsung Galaxy', '2025-12-20 12:12:18'),
(10, 5, 'ORD-202510', '2025-01-27', 'Delivered', 6000.00, 'Mobile Money', 'Order for HP Laptop', '2025-12-20 12:12:18'),
(11, 5, 'ORD-202511', '2025-01-28', 'Delivered', 13000.00, 'Cash', 'Order for Bluetooth Earbuds', '2025-12-20 12:12:18'),
(12, 5, 'ORD-202512', '2025-01-29', 'Processing', 15000.00, 'Mobile Money', 'Order for Men T-Shirt', '2025-12-20 12:12:18'),
(13, 6, 'ORD-202513', '2025-01-30', 'Pending', 220000.00, 'Credit Card', 'Order for Women Handbag', '2025-12-20 12:12:18'),
(14, 6, 'ORD-202514', '2025-01-31', 'Delivered', 880000.00, 'Mobile Money', 'Order for Rwandan Coffee', '2025-12-20 12:12:18'),
(15, 6, 'ORD-202515', '2025-02-01', 'Cancelled', 35000.00, 'Cash', 'Order for Cooking Pot', '2025-12-20 12:12:18'),
(16, 2, 'ORD-20251001', '2025-10-01', 'Delivered', 120000.00, 'Mobile Money', 'Order for Infinix Smart 8', '2025-12-20 12:19:45'),
(17, 2, 'ORD-20251008', '2025-10-08', 'Processing', 250000.00, 'Credit Card', 'Order for Samsung Galaxy A15', '2025-12-20 12:19:45'),
(18, 2, 'ORD-20251015', '2025-10-15', 'Pending', 8000.00, 'Cash', 'Order for Classic T-shirt', '2025-12-20 12:19:45'),
(19, 6, 'ORD-20251002', '2025-10-02', 'Delivered', 12000.00, 'Mobile Money', 'Order for Rwandan Coffee', '2025-12-20 12:19:45'),
(20, 6, 'ORD-20251009', '2025-10-09', 'Delivered', 35000.00, 'Cash', 'Order for Cooking Pot', '2025-12-20 12:19:45'),
(21, 6, 'ORD-20251016', '2025-10-16', 'Cancelled', 7500.00, 'Credit Card', 'Order for Body Lotion', '2025-12-20 12:19:45'),
(22, 8, 'ORD-20251003', '2025-10-03', 'Delivered', 15000.00, 'Mobile Money', 'Order for Maize Seeds', '2025-12-20 12:19:45'),
(23, 8, 'ORD-20251010', '2025-10-10', 'Processing', 6000.00, 'Cash', 'Order for Exercise Books', '2025-12-20 12:19:45'),
(24, 8, 'ORD-20251017', '2025-10-17', 'Pending', 13000.00, 'Mobile Money', 'Order for Cement', '2025-12-20 12:19:45'),
(25, 9, 'ORD-20251004', '2025-10-04', 'Delivered', 220000.00, 'Credit Card', 'Order for Samsung Galaxy', '2025-12-20 12:19:45'),
(26, 9, 'ORD-20251011', '2025-10-11', 'Delivered', 880000.00, 'Mobile Money', 'Order for HP Laptop', '2025-12-20 12:19:45'),
(27, 9, 'ORD-20251018', '2025-10-18', 'Processing', 45000.00, 'Cash', 'Order for Bluetooth Earbuds', '2025-12-20 12:19:45'),
(28, 10, 'ORD-20251005', '2025-10-05', 'Delivered', 15000.00, 'Mobile Money', 'Order for Men T-Shirt', '2025-12-20 12:19:45'),
(29, 10, 'ORD-20251012', '2025-10-12', 'Pending', 55000.00, 'Credit Card', 'Order for Women Handbag', '2025-12-20 12:19:45'),
(30, 10, 'ORD-20251019', '2025-10-19', 'Cancelled', 12000.00, 'Cash', 'Order for Rwandan Coffee', '2025-12-20 12:19:45'),
(31, 14, 'ORD-20251006', '2025-10-06', 'Processing', 35000.00, 'Mobile Money', 'Order for Cooking Pot', '2025-12-20 12:19:45'),
(32, 14, 'ORD-20251013', '2025-10-13', 'Delivered', 7500.00, 'Cash', 'Order for Body Lotion', '2025-12-20 12:19:45'),
(33, 14, 'ORD-20251020', '2025-10-20', 'Pending', 15000.00, 'Credit Card', 'Order for Maize Seeds', '2025-12-20 12:19:45'),
(34, 15, 'ORD-20251007', '2025-10-07', 'Delivered', 6000.00, 'Mobile Money', 'Order for Exercise Books', '2025-12-20 12:19:45'),
(35, 15, 'ORD-20251014', '2025-10-14', 'Processing', 13000.00, 'Cash', 'Order for Cement', '2025-12-20 12:19:45'),
(36, 15, 'ORD-20251021', '2025-10-21', 'Delivered', 220000.00, 'Credit Card', 'Order for Samsung Galaxy', '2025-12-20 12:19:45'),
(37, 0, 'ORD-78789', '2025-12-20', 'Pending', 0.00, 'Mobile money', 'Order for product: Paint 5L', '2025-12-20 12:23:32');

-- --------------------------------------------------------

--
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;
CREATE TABLE IF NOT EXISTS `order_items` (
  `Order_ItemsID` int NOT NULL AUTO_INCREMENT,
  `OrderID` int NOT NULL,
  `ProductID` int NOT NULL,
  `Quantity` int NOT NULL DEFAULT '1',
  `Price` decimal(10,2) NOT NULL,
  PRIMARY KEY (`Order_ItemsID`),
  KEY `OrderID` (`OrderID`),
  KEY `ProductID` (`ProductID`)
) ENGINE=MyISAM AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `order_items`
--

INSERT INTO `order_items` (`Order_ItemsID`, `OrderID`, `ProductID`, `Quantity`, `Price`) VALUES
(1, 14, 7, 1, 120000.00),
(2, 15, 8, 1, 250000.00),
(3, 16, 9, 1, 8000.00),
(4, 17, 11, 1, 35000.00),
(5, 18, 12, 1, 7500.00),
(6, 19, 13, 1, 15000.00),
(7, 20, 14, 1, 6000.00),
(8, 21, 15, 1, 13000.00),
(9, 22, 16, 1, 220000.00),
(10, 23, 17, 1, 880000.00),
(11, 24, 18, 1, 45000.00),
(12, 25, 19, 1, 15000.00),
(13, 26, 20, 1, 55000.00),
(14, 27, 10, 1, 12000.00),
(15, 28, 11, 1, 35000.00),
(16, 1, 9, 1, 120000.00),
(17, 2, 9, 1, 45000.00),
(18, 3, 15, 1, 6500.00),
(19, 5, 13, 1, 120000.00),
(20, 6, 16, 1, 250000.00),
(21, 7, 19, 1, 8000.00),
(22, 8, 16, 1, 12000.00),
(23, 10, 15, 1, 35000.00),
(24, 11, 10, 1, 7500.00),
(25, 13, 9, 1, 15000.00),
(26, 1, 7, 1, 120000.00),
(27, 2, 18, 1, 45000.00),
(28, 3, 12, 1, 6500.00),
(29, 5, 9, 1, 8000.00),
(30, 6, 10, 1, 12000.00),
(31, 7, 11, 1, 35000.00),
(32, 8, 13, 1, 15000.00),
(33, 10, 14, 1, 6000.00),
(34, 11, 15, 1, 13000.00),
(35, 13, 16, 1, 220000.00),
(36, 14, 17, 1, 880000.00);

-- --------------------------------------------------------

--
-- Table structure for table `payments`
--

DROP TABLE IF EXISTS `payments`;
CREATE TABLE IF NOT EXISTS `payments` (
  `PaymentID` int NOT NULL AUTO_INCREMENT,
  `OrderID` int NOT NULL,
  `Amount` decimal(10,2) NOT NULL,
  `Type` varchar(50) DEFAULT NULL,
  `Reference` varchar(100) DEFAULT NULL,
  `Status` varchar(50) DEFAULT 'Pending',
  `CreatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`PaymentID`),
  KEY `OrderID` (`OrderID`)
) ENGINE=MyISAM AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `payments`
--

INSERT INTO `payments` (`PaymentID`, `OrderID`, `Amount`, `Type`, `Reference`, `Status`, `CreatedAt`) VALUES
(1, 14, 120000.00, 'Mobile Money', 'MM-20250118-001', 'Completed', '2025-12-20 12:12:39'),
(2, 15, 250000.00, 'Credit Card', 'CC-20250119-001', 'Processing', '2025-12-20 12:12:39'),
(3, 16, 8000.00, 'Cash', 'CASH-20250120-001', 'Pending', '2025-12-20 12:12:39'),
(4, 17, 35000.00, 'Mobile Money', 'MM-20250121-001', 'Completed', '2025-12-20 12:12:39'),
(5, 18, 7500.00, 'Mobile Money', 'MM-20250122-001', 'Completed', '2025-12-20 12:12:39'),
(6, 19, 15000.00, 'Credit Card', 'CC-20250123-001', 'Cancelled', '2025-12-20 12:12:39'),
(7, 20, 6000.00, 'Cash', 'CASH-20250124-001', 'Completed', '2025-12-20 12:12:39'),
(8, 21, 13000.00, 'Mobile Money', 'MM-20250125-001', 'Processing', '2025-12-20 12:12:39'),
(9, 22, 220000.00, 'Credit Card', 'CC-20250126-001', 'Pending', '2025-12-20 12:12:39'),
(10, 23, 880000.00, 'Mobile Money', 'MM-20250127-001', 'Completed', '2025-12-20 12:12:39'),
(11, 24, 45000.00, 'Cash', 'CASH-20250128-001', 'Completed', '2025-12-20 12:12:39'),
(12, 25, 15000.00, 'Mobile Money', 'MM-20250129-001', 'Processing', '2025-12-20 12:12:39'),
(13, 26, 55000.00, 'Credit Card', 'CC-20250130-001', 'Pending', '2025-12-20 12:12:39'),
(14, 27, 12000.00, 'Mobile Money', 'MM-20250131-001', 'Completed', '2025-12-20 12:12:39'),
(15, 28, 35000.00, 'Cash', 'CASH-20250201-001', 'Cancelled', '2025-12-20 12:12:39'),
(16, 29, 120000.00, 'Mobile Money', 'MM-20251001-001', 'Completed', '2025-12-20 12:20:04'),
(17, 30, 250000.00, 'Credit Card', 'CC-20251008-001', 'Processing', '2025-12-20 12:20:04'),
(18, 31, 8000.00, 'Cash', 'CASH-20251015-001', 'Pending', '2025-12-20 12:20:04'),
(19, 32, 12000.00, 'Mobile Money', 'MM-20251002-001', 'Completed', '2025-12-20 12:20:04'),
(20, 33, 35000.00, 'Cash', 'CASH-20251009-001', 'Completed', '2025-12-20 12:20:04'),
(21, 34, 7500.00, 'Credit Card', 'CC-20251016-001', 'Cancelled', '2025-12-20 12:20:04'),
(22, 35, 15000.00, 'Mobile Money', 'MM-20251003-001', 'Completed', '2025-12-20 12:20:04'),
(23, 36, 6000.00, 'Cash', 'CASH-20251010-001', 'Processing', '2025-12-20 12:20:04'),
(24, 37, 13000.00, 'Mobile Money', 'MM-20251017-001', 'Pending', '2025-12-20 12:20:04'),
(25, 38, 220000.00, 'Credit Card', 'CC-20251004-001', 'Completed', '2025-12-20 12:20:04'),
(26, 39, 880000.00, 'Mobile Money', 'MM-20251011-001', 'Completed', '2025-12-20 12:20:04'),
(27, 40, 45000.00, 'Cash', 'CASH-20251018-001', 'Processing', '2025-12-20 12:20:04'),
(28, 41, 15000.00, 'Mobile Money', 'MM-20251005-001', 'Completed', '2025-12-20 12:20:04'),
(29, 42, 55000.00, 'Credit Card', 'CC-20251012-001', 'Pending', '2025-12-20 12:20:04'),
(30, 43, 12000.00, 'Cash', 'CASH-20251019-001', 'Cancelled', '2025-12-20 12:20:04'),
(31, 44, 35000.00, 'Mobile Money', 'MM-20251006-001', 'Processing', '2025-12-20 12:20:04'),
(32, 45, 7500.00, 'Cash', 'CASH-20251013-001', 'Completed', '2025-12-20 12:20:04'),
(33, 46, 15000.00, 'Credit Card', 'CC-20251020-001', 'Pending', '2025-12-20 12:20:04'),
(34, 47, 6000.00, 'Mobile Money', 'MM-20251007-001', 'Completed', '2025-12-20 12:20:04'),
(35, 48, 13000.00, 'Cash', 'CASH-20251014-001', 'Processing', '2025-12-20 12:20:04'),
(36, 49, 220000.00, 'Credit Card', 'CC-20251021-001', 'Completed', '2025-12-20 12:20:04');

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
CREATE TABLE IF NOT EXISTS `products` (
  `ProductID` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(200) NOT NULL,
  `Description` text,
  `CategoryID` int NOT NULL,
  `Price` decimal(10,2) NOT NULL,
  `StockQuantity` int DEFAULT '0',
  `CreatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ProductID`),
  KEY `CategoryID` (`CategoryID`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`ProductID`, `Name`, `Description`, `CategoryID`, `Price`, `StockQuantity`, `CreatedAt`) VALUES
(1, 'iPhone 14 Pro', 'Apple smartphone, 256GB, Face ID', 1, 1800000.00, 12, '2025-12-20 12:11:54'),
(2, 'Dell XPS 13', 'Ultrabook laptop, 512GB SSD', 1, 2200000.00, 8, '2025-12-20 12:11:54'),
(3, 'Sony Bravia 65\"', '4K Smart TV with Android TV', 1, 3200000.00, 5, '2025-12-20 12:11:54'),
(4, 'Nike Air Max', 'Running shoes for men', 2, 85000.00, 30, '2025-12-20 12:11:54'),
(5, 'Levi\'s Jeans', 'Denim jeans for men', 2, 45000.00, 40, '2025-12-20 12:11:54'),
(6, 'Kitchen Mixer', 'Electric mixer for kitchen', 4, 55000.00, 15, '2025-12-20 12:11:54'),
(7, 'Vitamin C Serum', 'Skin care serum for face', 5, 25000.00, 25, '2025-12-20 12:11:54'),
(8, 'Tomato Seeds', 'Hybrid tomato seeds pack', 6, 8000.00, 100, '2025-12-20 12:11:54'),
(9, 'Office Chair', 'Ergonomic office chair', 7, 120000.00, 10, '2025-12-20 12:11:54'),
(10, 'Paint 5L', 'Interior wall paint', 8, 35000.00, 20, '2025-12-20 12:11:54');

-- --------------------------------------------------------

--
-- Table structure for table `shipment`
--

DROP TABLE IF EXISTS `shipment`;
CREATE TABLE IF NOT EXISTS `shipment` (
  `ShipmentID` int NOT NULL AUTO_INCREMENT,
  `UserID` int NOT NULL,
  `ProductID` int NOT NULL,
  `CreatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ShipmentID`),
  KEY `UserID` (`UserID`),
  KEY `ProductID` (`ProductID`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `shipment`
--

INSERT INTO `shipment` (`ShipmentID`, `UserID`, `ProductID`, `CreatedAt`) VALUES
(1, 2, 7, '2025-01-18 22:00:00'),
(2, 3, 11, '2025-01-21 22:00:00'),
(3, 3, 12, '2025-01-22 22:00:00'),
(4, 4, 14, '2025-01-24 22:00:00'),
(5, 5, 17, '2025-01-27 22:00:00'),
(6, 5, 18, '2025-01-28 22:00:00'),
(7, 6, 10, '2025-01-31 22:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `UserID` int NOT NULL AUTO_INCREMENT,
  `Username` varchar(50) NOT NULL,
  `PasswordHash` varchar(64) NOT NULL,
  `FullName` varchar(100) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `Role` enum('Admin','Customer') NOT NULL DEFAULT 'Customer',
  `CreatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `LastLogin` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`UserID`),
  UNIQUE KEY `Username` (`Username`),
  UNIQUE KEY `Email` (`Email`)
) ENGINE=MyISAM AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`UserID`, `Username`, `PasswordHash`, `FullName`, `Email`, `Role`, `CreatedAt`, `LastLogin`) VALUES
(2, 'adolphe', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 'Adolphe Nayituriki', 'adolphe@gmail.com', 'Admin', '2025-12-20 07:51:09', '2025-12-20 12:23:54'),
(3, 'evode', 'b1556dea32e9d0cdbfed038fd7787275775ea40939c146a64e205bcb349ad02f', 'Evode User', 'evode@gmail.com', 'Customer', '2025-12-20 07:51:09', '2025-12-20 12:40:45'),
(4, 'jean', 'f6e0a1e2ac41945a9aa7ff8a8aaa0cebc12a3bcc981a929ad5cf810a090e11ae', 'Jean Pierre', 'jean@gmail.com', 'Customer', '2025-12-20 07:51:09', NULL),
(5, 'paul', 'f6e0a1e2ac41945a9aa7ff8a8aaa0cebc12a3bcc981a929ad5cf810a090e11ae', 'Paul Ndayishimiye', 'paul@gmail.com', 'Customer', '2025-12-20 07:51:09', NULL),
(6, 'alice', 'f6e0a1e2ac41945a9aa7ff8a8aaa0cebc12a3bcc981a929ad5cf810a090e11ae', 'Alice Mukamana', 'alice@gmail.com', 'Customer', '2025-12-20 07:51:09', NULL),
(7, 'kevin', 'f6e0a1e2ac41945a9aa7ff8a8aaa0cebc12a3bcc981a929ad5cf810a090e11ae', 'Kevin Uwimana', 'kevin@gmail.com', 'Customer', '2025-12-20 07:51:09', NULL),
(8, 'eric', 'f6e0a1e2ac41945a9aa7ff8a8aaa0cebc12a3bcc981a929ad5cf810a090e11ae', 'Eric Habimana', 'eric@gmail.com', 'Customer', '2025-12-20 07:51:09', NULL),
(9, 'david', 'f6e0a1e2ac41945a9aa7ff8a8aaa0cebc12a3bcc981a929ad5cf810a090e11ae', 'David Nsengimana', 'david@gmail.com', 'Customer', '2025-12-20 07:51:09', NULL),
(10, 'marie', 'f6e0a1e2ac41945a9aa7ff8a8aaa0cebc12a3bcc981a929ad5cf810a090e11ae', 'Marie Claire', 'marie@gmail.com', 'Customer', '2025-12-20 07:51:09', NULL),
(11, 'james', 'f6e0a1e2ac41945a9aa7ff8a8aaa0cebc12a3bcc981a929ad5cf810a090e11ae', 'James Mugisha', 'james@gmail.com', 'Customer', '2025-12-20 07:51:09', NULL),
(12, 'fgs', '043a718774c572bd8a25adbeb1bfcd5c0256ae11cecf9f9c3f925d0e52beaf89', 's', 's', 'Customer', '2025-12-20 08:00:26', NULL),
(13, 'cedric11', 'f6e0a1e2ac41945a9aa7ff8a8aaa0cebc12a3bcc981a929ad5cf810a090e11ae', 'CEDRIC', 'cedric@gmail.com', 'Customer', '2025-12-20 08:20:42', '2025-12-20 08:21:02'),
(14, 'annah', 'f6e0a1e2ac41945a9aa7ff8a8aaa0cebc12a3bcc981a929ad5cf810a090e11ae', 'ANNAH', 'annah@gmail.com', 'Customer', '2025-12-20 12:41:45', NULL);

-- --------------------------------------------------------

--
-- Structure for view `orderdetails`
--
DROP TABLE IF EXISTS `orderdetails`;

DROP VIEW IF EXISTS `orderdetails`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `orderdetails`  AS SELECT `o`.`OrderID` AS `OrderID`, `o`.`OrderNumber` AS `OrderNumber`, `o`.`Date` AS `Date`, `o`.`Status` AS `OrderStatus`, `u`.`FullName` AS `Customer`, `o`.`TotalAmount` AS `TotalAmount`, `o`.`PaymentMethod` AS `PaymentMethod`, `p`.`ProductID` AS `ProductID`, `pr`.`Name` AS `ProductName`, `p`.`Quantity` AS `Quantity`, `p`.`Price` AS `UnitPrice`, (`p`.`Quantity` * `p`.`Price`) AS `ItemTotal` FROM (((`orders` `o` join `users` `u` on((`o`.`UserID` = `u`.`UserID`))) join `order_items` `p` on((`o`.`OrderID` = `p`.`OrderID`))) join `products` `pr` on((`p`.`ProductID` = `pr`.`ProductID`))) ORDER BY `o`.`OrderID` DESC ;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
