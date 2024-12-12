-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 13, 2024 at 02:33 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `librarydb`
--

-- --------------------------------------------------------

--
-- Table structure for table `issuebook`
--

CREATE TABLE `issuebook` (
  `id` int(11) NOT NULL,
  `bookID` int(11) NOT NULL,
  `bookName` varchar(255) DEFAULT 'Unknown',
  `memberID` int(11) NOT NULL,
  `memberName` varchar(255) DEFAULT 'Unknown',
  `issueDate` date NOT NULL,
  `dueDate` date NOT NULL,
  `returnBook` enum('Yes','No') DEFAULT 'No'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `issuebook`
--

INSERT INTO `issuebook` (`id`, `bookID`, `bookName`, `memberID`, `memberName`, `issueDate`, `dueDate`, `returnBook`) VALUES
(1, 1, 'Unknown', 1, 'Unknown', '2024-12-01', '2024-12-15', 'Yes'),
(2, 2, 'Unknown', 2, 'Unknown', '2024-11-01', '2024-11-12', 'No');

-- --------------------------------------------------------

--
-- Table structure for table `newbooks`
--

CREATE TABLE `newbooks` (
  `bookID` int(11) NOT NULL,
  `title` varchar(100) DEFAULT NULL,
  `author` varchar(100) DEFAULT NULL,
  `yearPublished` int(11) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `newbooks`
--

INSERT INTO `newbooks` (`bookID`, `title`, `author`, `yearPublished`, `price`, `status`) VALUES
(1, 'Java', 'John', 2016, 800, 'Available'),
(2, 'PHP', 'Donald', 2022, 9900, 'Unavailable');

-- --------------------------------------------------------

--
-- Table structure for table `newmembers`
--

CREATE TABLE `newmembers` (
  `memberID` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `contactInfo` varchar(100) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `cardNumber` varchar(50) DEFAULT NULL,
  `expirationDate` date DEFAULT NULL,
  `membershipStatus` enum('Active','Inactive','Expired') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `newmembers`
--

INSERT INTO `newmembers` (`memberID`, `name`, `contactInfo`, `address`, `cardNumber`, `expirationDate`, `membershipStatus`) VALUES
(1, 'Ayaaz', '0778912879', '132/A, Kandy', '1', '2024-12-31', 'Active'),
(2, 'Aaliyah', '0771259832', '12/B, Colombo', '2', '2024-12-17', 'Inactive');

-- --------------------------------------------------------

--
-- Table structure for table `returnedbooks`
--

CREATE TABLE `returnedbooks` (
  `recordID` int(11) NOT NULL,
  `bookID` int(11) NOT NULL,
  `bookName` varchar(255) DEFAULT NULL,
  `memberID` int(11) NOT NULL,
  `memberName` varchar(255) DEFAULT NULL,
  `issuedDate` date DEFAULT NULL,
  `returnedDate` date DEFAULT NULL,
  `returnedBook` enum('Yes','No') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `returnedbooks`
--

INSERT INTO `returnedbooks` (`recordID`, `bookID`, `bookName`, `memberID`, `memberName`, `issuedDate`, `returnedDate`, `returnedBook`) VALUES
(1, 1, NULL, 1, NULL, '2024-12-01', '2024-12-09', 'Yes');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `issuebook`
--
ALTER TABLE `issuebook`
  ADD PRIMARY KEY (`id`),
  ADD KEY `bookID` (`bookID`),
  ADD KEY `memberID` (`memberID`);

--
-- Indexes for table `newbooks`
--
ALTER TABLE `newbooks`
  ADD PRIMARY KEY (`bookID`);

--
-- Indexes for table `newmembers`
--
ALTER TABLE `newmembers`
  ADD PRIMARY KEY (`memberID`),
  ADD UNIQUE KEY `cardNumber` (`cardNumber`);

--
-- Indexes for table `returnedbooks`
--
ALTER TABLE `returnedbooks`
  ADD PRIMARY KEY (`recordID`),
  ADD KEY `bookID` (`bookID`),
  ADD KEY `memberID` (`memberID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `issuebook`
--
ALTER TABLE `issuebook`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `newbooks`
--
ALTER TABLE `newbooks`
  MODIFY `bookID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `newmembers`
--
ALTER TABLE `newmembers`
  MODIFY `memberID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `returnedbooks`
--
ALTER TABLE `returnedbooks`
  MODIFY `recordID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `issuebook`
--
ALTER TABLE `issuebook`
  ADD CONSTRAINT `issuebook_ibfk_1` FOREIGN KEY (`bookID`) REFERENCES `newbooks` (`bookID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `issuebook_ibfk_2` FOREIGN KEY (`memberID`) REFERENCES `newmembers` (`memberID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `returnedbooks`
--
ALTER TABLE `returnedbooks`
  ADD CONSTRAINT `returnedbooks_ibfk_1` FOREIGN KEY (`bookID`) REFERENCES `newbooks` (`bookID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `returnedbooks_ibfk_2` FOREIGN KEY (`memberID`) REFERENCES `newmembers` (`memberID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
