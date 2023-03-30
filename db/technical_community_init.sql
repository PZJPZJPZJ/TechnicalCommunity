/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80031
 Source Host           : localhost:3306
 Source Schema         : technical_community

 Target Server Type    : MySQL
 Target Server Version : 80031
 File Encoding         : 65001

 Date: 22/03/2023 17:54:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP DATABASE IF EXISTS technical_community;
CREATE DATABASE technical_community;
USE technical_community;

-- ----------------------------
-- Table structure for tc_chat
-- ----------------------------
DROP TABLE IF EXISTS `tc_chat`;
CREATE TABLE `tc_chat`  (
  `chat_id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `chat_send` int UNSIGNED NOT NULL,
  `chat_receive` int UNSIGNED NOT NULL,
  `chat_unread` int UNSIGNED NOT NULL DEFAULT 0,
  `chat_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`chat_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for tc_comment
-- ----------------------------
DROP TABLE IF EXISTS `tc_comment`;
CREATE TABLE `tc_comment`  (
  `comment_id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `comment_post` int UNSIGNED NOT NULL,
  `comment_user` int UNSIGNED NOT NULL,
  `comment_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `comment_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for tc_message
-- ----------------------------
DROP TABLE IF EXISTS `tc_message`;
CREATE TABLE `tc_message`  (
  `message_id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `message_chat` int UNSIGNED NOT NULL,
  `message_user` int UNSIGNED NOT NULL,
  `message_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `message_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`message_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for tc_news
-- ----------------------------
DROP TABLE IF EXISTS `tc_news`;
CREATE TABLE `tc_news`  (
  `news_id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `news_admin` int UNSIGNED NOT NULL,
  `news_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `news_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `news_cover` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `news_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`news_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for tc_picture
-- ----------------------------
DROP TABLE IF EXISTS `tc_picture`;
CREATE TABLE `tc_picture`  (
  `picture_id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `picture_post` int UNSIGNED NOT NULL,
  `picture_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  PRIMARY KEY (`picture_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for tc_post
-- ----------------------------
DROP TABLE IF EXISTS `tc_post`;
CREATE TABLE `tc_post`  (
  `post_id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `post_tag` int UNSIGNED NOT NULL,
  `post_user` int UNSIGNED NOT NULL,
  `post_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `post_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `post_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `post_top` tinyint(1) NOT NULL DEFAULT 0,
  `post_price` decimal(10, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`post_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for tc_tag
-- ----------------------------
DROP TABLE IF EXISTS `tc_tag`;
CREATE TABLE `tc_tag`  (
  `tag_id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `tag_cover` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  PRIMARY KEY (`tag_id`, `tag_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for tc_user
-- ----------------------------
DROP TABLE IF EXISTS `tc_user`;
CREATE TABLE `tc_user`  (
  `user_id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_admin` tinyint(1) NOT NULL,
  `user_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png',
  `user_sign` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `user_sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `user_birth` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100014 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;

ALTER TABLE tc_chat AUTO_INCREMENT=1;
ALTER TABLE tc_comment AUTO_INCREMENT=1;
ALTER TABLE tc_message AUTO_INCREMENT=1;
ALTER TABLE tc_news AUTO_INCREMENT=1;
ALTER TABLE tc_picture AUTO_INCREMENT=1;
ALTER TABLE tc_post AUTO_INCREMENT=1;
ALTER TABLE tc_tag AUTO_INCREMENT=1;
ALTER TABLE tc_user AUTO_INCREMENT=100000;
INSERT INTO `technical_community`.`tc_user` (`user_id`, `user_admin`, `user_password`, `user_name`, `user_cover`, `user_sign`, `user_sex`, `user_birth`) VALUES (100000, 1, '$2a$10$ULux4VKe8JxZ77trA1fwi.WQ.243Prhk6cx0GsFH5o8orQXgYRMc.', '初始管理员', 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png', '维护科技社区氛围是我的职责', '男', '2022-12-31 16:00:00');
INSERT INTO `technical_community`.`tc_user` (`user_id`, `user_admin`, `user_password`, `user_name`, `user_cover`, `user_sign`, `user_sex`, `user_birth`) VALUES (100001, 1, '$2a$10$/pLgHwjVM3GIT09ZggzNOefsZ1crxC74vbK3rfIscoZVFbdG54kbK', '站长', 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png', '有什么问题可以向我反馈喔', '男', '2023-02-08 18:05:31');