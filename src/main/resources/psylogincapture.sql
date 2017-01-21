-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jan 21, 2017 at 02:19 PM
-- Server version: 10.1.16-MariaDB
-- PHP Version: 7.0.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `psylogincapture`
--

-- --------------------------------------------------------

--
-- Table structure for table `actions`
--

CREATE TABLE `actions` (
  `id` int(11) NOT NULL,
  `session_id` int(11) NOT NULL,
  `action_type_id` int(11) NOT NULL,
  `resource_id` int(11) DEFAULT NULL,
  `time_stamp` datetime(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- --------------------------------------------------------

--
-- Table structure for table `action_types`
--

CREATE TABLE `action_types` (
  `id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `action_types`
--

INSERT INTO `action_types` (`id`, `title`, `description`) VALUES
(31, 'navigation', 'index'),
(32, 'navigation', 'add'),
(33, 'keypress', 'A'),
(34, 'keypress', 'B'),
(35, 'keypress', 'C'),
(36, 'keypress', 'D'),
(37, 'add_article', 'add'),
(38, 'keypress', 'password_input'),
(39, 'keypress', ''),
(40, 'keypress', 'O'),
(41, 'keypress', 'L'),
(42, 'keypress', 'E'),
(43, 'keypress', ' '),
(44, 'keypress', 'G'),
(45, 'keypress', 'S'),
(46, 'keypress', 'H'),
(47, 'keypress', 'I'),
(48, 'keypress', 'T'),
(49, 'navigation', 'delete'),
(50, 'keypress', 'f'),
(51, 'keypress', '*'),
(52, 'keypress', '£'),
(53, 'navigation', 'view'),
(54, 'keypress', 'a'),
(55, 'keypress', 's'),
(56, 'keypress', 'd'),
(57, 'keypress', '$'),
(58, 'keypress', 't'),
(59, 'keypress', 'e'),
(60, 'keypress', 'j'),
(61, 'keypress', 'ö'),
(62, 'navigation', 'edit'),
(63, 'keypress', 'i'),
(64, 'keypress', 'm'),
(65, 'keypress', 'p'),
(66, 'keypress', 'r'),
(67, 'keypress', 'R'),
(68, 'keypress', 'o'),
(69, 'keypress', 'g'),
(70, 'keypress', 'W'),
(71, 'keypress', 'n'),
(72, 'keypress', '.'),
(73, 'keypress', 'h'),
(74, 'keypress', 'l'),
(75, 'keypress', 'b'),
(76, 'keypress', 'c'),
(77, 'keypress', 'v'),
(78, 'keypress', 'w'),
(79, 'keypress', 'u'),
(80, 'keypress', 'z'),
(81, 'keypress', 'M'),
(82, 'keypress', 'Z'),
(83, 'keypress', ','),
(84, 'keypress', 'k'),
(85, 'keypress', 'x'),
(86, 'keypress', '-'),
(87, 'keypress', '2'),
(88, 'keypress', 'J'),
(89, 'keypress', 'N'),
(90, 'keypress', 'F'),
(91, 'keypress', 'P');

-- --------------------------------------------------------

--
-- Table structure for table `alerts`
--

CREATE TABLE `alerts` (
  `producer` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `severity` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `blog_user_id` int(11) NOT NULL,
  `time_stamp` date NOT NULL,
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

----------------------------------------------

--
-- Table structure for table `blog_users`
--

CREATE TABLE `blog_users` (
  `id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- --------------------------------------------------------

--
-- Table structure for table `classifications`
--

CREATE TABLE `classifications` (
  `resource_id` int(11) NOT NULL,
  `tag_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `resources`
--

CREATE TABLE `resources` (
  `id` int(11) NOT NULL,
  `url` varchar(1000) NOT NULL,
  `content` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- --------------------------------------------------------

--
-- Table structure for table `sessions`
--

CREATE TABLE `sessions` (
  `id` int(11) NOT NULL,
  `blog_user_id` int(11) NOT NULL,
  `start` datetime NOT NULL,
  `stop` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- --------------------------------------------------------

--
-- Table structure for table `static_analyse_config`
--

CREATE TABLE `static_analyse_config` (
  `id` int(11) NOT NULL,
  `penalty_warning_level` int(11) NOT NULL,
  `penalty_error_level` int(11) NOT NULL,
  `minimum_number_logins` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `static_analyse_config`
--

INSERT INTO `static_analyse_config` (`id`, `penalty_warning_level`, `penalty_error_level`, `minimum_number_logins`) VALUES
(0, 30, 5, 5);

-- --------------------------------------------------------

--
-- Table structure for table `static_session_datas`
--

CREATE TABLE `static_session_datas` (
  `session_id` int(11) NOT NULL,
  `os` varchar(255) NOT NULL,
  `lang` varchar(255) NOT NULL,
  `browser` varchar(255) NOT NULL,
  `location` varchar(255) NOT NULL,
  `referrer` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- --------------------------------------------------------

--
-- Table structure for table `tags`
--

CREATE TABLE `tags` (
  `id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `actions`
--
ALTER TABLE `actions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_actions_action_types` (`action_type_id`),
  ADD KEY `FK_actions_resources` (`resource_id`),
  ADD KEY `FK_actions_sessions` (`session_id`);

--
-- Indexes for table `action_types`
--
ALTER TABLE `action_types`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `alerts`
--
ALTER TABLE `alerts`
  ADD PRIMARY KEY (`id`),
  ADD KEY `blog_user_id` (`blog_user_id`);

--
-- Indexes for table `blog_users`
--
ALTER TABLE `blog_users`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `classifications`
--
ALTER TABLE `classifications`
  ADD PRIMARY KEY (`resource_id`,`tag_id`),
  ADD KEY `FK_classifications_tags` (`tag_id`);

--
-- Indexes for table `resources`
--
ALTER TABLE `resources`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `sessions`
--
ALTER TABLE `sessions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `blog_user_key` (`blog_user_id`);

--
-- Indexes for table `static_session_datas`
--
ALTER TABLE `static_session_datas`
  ADD PRIMARY KEY (`session_id`);

--
-- Indexes for table `tags`
--
ALTER TABLE `tags`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `actions`
--
ALTER TABLE `actions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7954;
--
-- AUTO_INCREMENT for table `action_types`
--
ALTER TABLE `action_types`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=92;
--
-- AUTO_INCREMENT for table `alerts`
--
ALTER TABLE `alerts`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
--
-- AUTO_INCREMENT for table `blog_users`
--
ALTER TABLE `blog_users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;
--
-- AUTO_INCREMENT for table `resources`
--
ALTER TABLE `resources`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;
--
-- AUTO_INCREMENT for table `sessions`
--
ALTER TABLE `sessions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=593;
--
-- AUTO_INCREMENT for table `tags`
--
ALTER TABLE `tags`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `actions`
--
ALTER TABLE `actions`
  ADD CONSTRAINT `FK_actions_action_types` FOREIGN KEY (`action_type_id`) REFERENCES `action_types` (`id`),
  ADD CONSTRAINT `FK_actions_resources` FOREIGN KEY (`resource_id`) REFERENCES `resources` (`id`),
  ADD CONSTRAINT `FK_actions_sessions` FOREIGN KEY (`session_id`) REFERENCES `sessions` (`id`);

--
-- Constraints for table `classifications`
--
ALTER TABLE `classifications`
  ADD CONSTRAINT `FK_classifications_resources` FOREIGN KEY (`resource_id`) REFERENCES `resources` (`id`),
  ADD CONSTRAINT `FK_classifications_tags` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`);

--
-- Constraints for table `sessions`
--
ALTER TABLE `sessions`
  ADD CONSTRAINT `blog_user_key` FOREIGN KEY (`blog_user_id`) REFERENCES `blog_users` (`id`);

--
-- Constraints for table `static_session_datas`
--
ALTER TABLE `static_session_datas`
  ADD CONSTRAINT `session_key` FOREIGN KEY (`session_id`) REFERENCES `sessions` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
