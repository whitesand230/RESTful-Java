-- phpMyAdmin SQL Dump
-- version 4.4.12
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Oct 12, 2015 at 08:34 PM
-- Server version: 5.6.25
-- PHP Version: 5.6.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `test`
--

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE IF NOT EXISTS `orders` (
  `order_id` int(20) NOT NULL,
  `firstName` varchar(255) NOT NULL,
  `lastName` varchar(255) NOT NULL,
  `startDate` datetime NOT NULL,
  `finishDate` datetime NOT NULL,
  `phone` varchar(20) NOT NULL,
  `owner_id` int(11) NOT NULL,
  `order_desc` text NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`order_id`, `firstName`, `lastName`, `startDate`, `finishDate`, `phone`, `owner_id`, `order_desc`) VALUES
(1, 'Bob', 'Test', '2015-09-01 12:10:00', '2015-09-05 12:10:00', '555-555-1234', 1, 'Fix broken buttons'),
(2, 'Bob', 'Test', '2015-09-01 12:10:00', '2015-09-05 12:18:00', '555-555-1235', 3, 'Dual Mod'),
(3, 'Eric', 'Test', '2015-09-01 12:10:00', '2015-09-05 12:18:00', '555-555-1235', 3, 'Quad Mod'),
(6, 'Mike', 'Test', '2015-09-22 20:12:48', '2015-09-22 20:12:48', '555-555-5523', 2, 'Fix Circuit Board');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`order_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `order_id` int(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=7;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
