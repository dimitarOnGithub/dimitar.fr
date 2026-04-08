CREATE TABLE `posts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `published_date` datetime NOT NULL,
  `content` mediumtext NOT NULL,
  `is_draft` BOOLEAN NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `guestbook` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` mediumtext NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `website` varchar(255) DEFAULT NULL,
  `approved` BOOLEAN NOT NULL,
  `ip_address` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ip_address` (`ip_address`)
);
