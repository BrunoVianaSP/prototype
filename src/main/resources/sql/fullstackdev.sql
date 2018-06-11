CREATE SCHEMA IF NOT EXISTS  `fullstackdev` ;
USE fullstackdev;


DROP TABLE IF EXISTS `user_role`;
DROP TABLE IF EXISTS `role`;
DROP TABLE IF EXISTS password_reset_token;
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS plan;


--
-- Table structure for table `plan`
--
CREATE TABLE `plan` (
  `id` tinyint(4) NOT NULL,
  `name` char(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

commit;

--
-- Table structure for table `publisher`
--
CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(60) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `email` varchar(256) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `phone_number` varchar(50) DEFAULT NULL,
  `country` varchar(2) DEFAULT NULL,
  `description` TEXT(500),
  `profile_image_url` TEXT(500),
  `plan_id` tinyint(4) NOT NULL DEFAULT 1,
  `stripe_customer_id` VARCHAR(100),
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `users_ix_email` (`email`),
  UNIQUE KEY `users_ix_username` (`username`),
  CONSTRAINT `fk_plan_users` FOREIGN KEY (`plan_id`) REFERENCES `plan` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

COMMIT;
--
-- Table structure for table `role`
--
CREATE TABLE `role` (
  `id` int(10) unsigned NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

COMMIT;


--
-- Table structure for table `user_role`
--
CREATE TABLE `user_role` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL,
  `role_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_userrole_role_idx` (`role_id`),
  CONSTRAINT `fk_userrole_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_userrole_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

COMMIT;

--
-- Table structure for table `password_reset_token`
--
CREATE TABLE `password_reset_token` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `token` varchar(255) NOT NULL,
  `user_id` int(10) UNSIGNED NOT NULL,
  `expiry_date` TIMESTAMP NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `token_ix_token` (`token`),
  CONSTRAINT `fk_token_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

COMMIT;


INSERT INTO plan VALUES ('1', 'Basic');
INSERT INTO plan VALUES ('2', 'Pro');

INSERT INTO role VALUES (1, 'ROLE_BASIC');
INSERT INTO role VALUES (2, 'ROLE_PRO');
INSERT INTO role VALUES (3, 'ROLE_ADMIN');

COMMIT;
