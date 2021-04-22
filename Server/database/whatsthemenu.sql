-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3307
-- Generation Time: Nov 04, 2020 at 04:52 PM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.4.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `whatsthemenu`
--

-- --------------------------------------------------------

--
-- Table structure for table `feast`
--

CREATE TABLE `feast` (
  `f_id` int(11) NOT NULL,
  `f_name` varchar(100) NOT NULL,
  `l_lodgename` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `feast`
--

INSERT INTO `feast` (`f_id`, `f_name`, `l_lodgename`) VALUES
(26, 'halvo', '1234567890'),
(27, 'rasgulla', '1234567890'),
(31, 'rasgulla', '1234567899'),
(32, 'ras', '1234567899');

-- --------------------------------------------------------

--
-- Table structure for table `lodgeowner`
--

CREATE TABLE `lodgeowner` (
  `l_id` int(11) NOT NULL,
  `l_lodgename` varchar(100) NOT NULL,
  `l_lodgeliscence` varchar(14) NOT NULL,
  `l_lodgearea` varchar(50) NOT NULL,
  `l_lunchtime` varchar(100) NOT NULL,
  `l_dinnertime` varchar(100) NOT NULL,
  `l_ownername` varchar(100) NOT NULL,
  `l_email` varchar(50) NOT NULL,
  `l_phone` varchar(10) NOT NULL,
  `l_pass` varchar(32) NOT NULL,
  `l_menu` varchar(1000) NOT NULL,
  `l_menuprice` varchar(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `lodgeowner`
--

INSERT INTO `lodgeowner` (`l_id`, `l_lodgename`, `l_lodgeliscence`, `l_lodgearea`, `l_lunchtime`, `l_dinnertime`, `l_ownername`, `l_email`, `l_phone`, `l_pass`, `l_menu`, `l_menuprice`) VALUES
(19, 'Gujarat Lodge', 'abc', 'nana bazar', '10:30 to 1:30', '7:30 to 9:00', 'abc', '', '1234567890', '0f64a1c1e3009057addac15f9c339b16', 'Matar Paneer,\nPunjabi Chana,\nSuki Bhaji,\nRoti,\nSalad,\nPapad,\nChhash', '70'),
(20, 'Mahadev Dinning Hall', 'abc', 'Mohini Corner', '10:00 to 1:30', '7:30 to 9:30', 'abc', '', '1223456789', '42cb5c5c53ec1e429e1051ac34a49480', 'Dal Fry, Jeera Rise, Roti, 6ash,papad,olo,rotlo', '45'),
(21, 'Narayan Lodge', 'xyz', 'mota bazar', '10:30 to 1:00', '7:00 to 9:00', 'abc', '', '1234567899', 'c0c669570f0dd6c896db86e0447802e8', 'narayan menu', '40');

-- --------------------------------------------------------

--
-- Table structure for table `lodgeownerrequest`
--

CREATE TABLE `lodgeownerrequest` (
  `l_id` int(11) NOT NULL,
  `l_lodgename` varchar(200) NOT NULL,
  `l_liscence` varchar(14) NOT NULL,
  `l_lodgearea` varchar(100) NOT NULL,
  `l_lunchtime` varchar(100) NOT NULL,
  `l_dinnertime` varchar(100) NOT NULL,
  `l_ownername` varchar(150) NOT NULL,
  `l_email` varchar(150) NOT NULL,
  `l_phone` varchar(10) NOT NULL,
  `l_pass` varchar(32) NOT NULL,
  `l_menu` varchar(1000) NOT NULL,
  `l_menuprice` varchar(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `o_id` int(11) NOT NULL,
  `l_lodgename` varchar(100) NOT NULL,
  `o_useraddress` varchar(500) NOT NULL,
  `u_phone` bigint(10) NOT NULL,
  `o_price` int(11) NOT NULL,
  `o_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `o_status` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`o_id`, `l_lodgename`, `o_useraddress`, `u_phone`, `o_price`, `o_date`, `o_status`) VALUES
(20, 'Gujarat lodge', 'a-5,madhav gurukul', 8866469115, 30, '2018-02-17 06:43:28', 'delivered'),
(21, 'Gujarat lodge', 'test', 8866469115, 30, '2018-02-17 06:43:55', 'delivered'),
(22, 'Narayan Lodge', 'a-5,madhav', 8866469115, 30, '2018-02-17 07:23:45', 'delivered'),
(23, 'Gujarat Lodge', 'a-5,madhav gurukul', 8866469115, 40, '2018-02-20 09:30:09', 'delivered'),
(24, 'Gujarat Lodge', 'a-5,madhav gurukul', 8866469115, 50, '2018-02-20 09:50:53', 'delivered'),
(25, 'Mahadev Dinning Hall', 'b_104', 9824968168, 0, '2018-03-20 15:08:10', 'pending'),
(26, 'Gujarat Lodge', 'a-5,madhav gurukul', 8866469115, 45, '2018-04-01 08:58:09', 'delivered'),
(27, 'Gujarat Lodge', 'b_104', 9824968168, 45, '2018-04-01 08:58:40', 'delivered'),
(28, 'Narayan Lodge', 'b_104', 9824968168, 40, '2018-04-04 07:41:41', 'pending'),
(29, 'Gujarat Lodge', 'GCET SFL2', 9033224822, 45, '2018-04-05 04:31:55', 'delivered'),
(30, 'Gujarat Lodge', 'a-5,madhav gurukul', 8866469115, 45, '2018-04-05 04:48:04', 'delivered'),
(31, 'Mahadev Dinning Hall', 'Gcet', 9033224822, 0, '2018-04-05 04:52:53', 'pending'),
(32, 'Gujarat Lodge', 'a-5,madhav gurukul', 8866469115, 45, '2018-04-05 05:21:34', 'delivered'),
(33, 'Gujarat Lodge', 'a-5,madhav gurukul', 8866469115, 45, '2018-04-05 05:31:54', 'delivered'),
(34, 'Gujarat Lodge', 'a-5,madhav gurukul', 8866469115, 10, '2018-04-05 05:49:27', 'pending'),
(35, 'Gujarat Lodge', 'a-5,madhav gurukul', 8866469115, 10, '2018-06-06 02:48:45', 'pending'),
(36, 'Gujarat Lodge', 'a-5,madhav gurukul', 8866469115, 70, '2018-06-06 06:07:14', 'pending');

-- --------------------------------------------------------

--
-- Table structure for table `suggestions`
--

CREATE TABLE `suggestions` (
  `s_id` int(11) NOT NULL,
  `s_name` varchar(100) NOT NULL,
  `l_phone` varchar(100) NOT NULL,
  `u_phone` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `u_id` int(11) NOT NULL,
  `u_name` varchar(50) NOT NULL,
  `u_email` varchar(100) NOT NULL,
  `u_phone` bigint(10) NOT NULL,
  `u_pass` varchar(32) NOT NULL,
  `u_birthdate` varchar(10) NOT NULL,
  `u_address` varchar(1000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`u_id`, `u_name`, `u_email`, `u_phone`, `u_pass`, `u_birthdate`, `u_address`) VALUES
(0, 'Darshit Dhameliya', 'd@g.com', 8866469115, '31e37e94f9d840d2b86ce098172f8b94', '25/04/1998', 'a-5,madhav gurukul'),
(0, 'Pranav', 'pranavmody98@gmail.com', 9033224822, '6c0a4ce2ce842f02da7c80a0a7b122dd', '', ''),
(0, 'Dip Limbani', 'd@g.com', 9824968168, 'ffad3509caa0d87832f811f0f8d211f8', '21/09/1998', 'b_104');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `feast`
--
ALTER TABLE `feast`
  ADD PRIMARY KEY (`f_id`);

--
-- Indexes for table `lodgeowner`
--
ALTER TABLE `lodgeowner`
  ADD PRIMARY KEY (`l_lodgename`),
  ADD UNIQUE KEY `l_phone` (`l_phone`),
  ADD KEY `l_id` (`l_id`);

--
-- Indexes for table `lodgeownerrequest`
--
ALTER TABLE `lodgeownerrequest`
  ADD PRIMARY KEY (`l_lodgename`),
  ADD UNIQUE KEY `l_phone` (`l_phone`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`o_id`);

--
-- Indexes for table `suggestions`
--
ALTER TABLE `suggestions`
  ADD PRIMARY KEY (`s_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`u_phone`),
  ADD UNIQUE KEY `u_phone` (`u_phone`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `feast`
--
ALTER TABLE `feast`
  MODIFY `f_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT for table `lodgeowner`
--
ALTER TABLE `lodgeowner`
  MODIFY `l_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `o_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;

--
-- AUTO_INCREMENT for table `suggestions`
--
ALTER TABLE `suggestions`
  MODIFY `s_id` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
