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

 Date: 12/02/2023 17:58:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tc_chat
-- ----------------------------
DROP TABLE IF EXISTS `tc_chat`;
CREATE TABLE `tc_chat`  (
  `chat_id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `chat_send` int UNSIGNED NOT NULL,
  `chat_receive` int UNSIGNED NOT NULL,
  PRIMARY KEY (`chat_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tc_chat
-- ----------------------------

-- ----------------------------
-- Table structure for tc_comment
-- ----------------------------
DROP TABLE IF EXISTS `tc_comment`;
CREATE TABLE `tc_comment`  (
  `comment_id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `comment_user` int UNSIGNED NOT NULL,
  `comment_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `comment_like` int NOT NULL,
  `comment_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tc_comment
-- ----------------------------

-- ----------------------------
-- Table structure for tc_message
-- ----------------------------
DROP TABLE IF EXISTS `tc_message`;
CREATE TABLE `tc_message`  (
  `message_id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `message_chat` int UNSIGNED NOT NULL,
  `message_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `message_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `message_unread` tinyint(1) NOT NULL,
  PRIMARY KEY (`message_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tc_message
-- ----------------------------

-- ----------------------------
-- Table structure for tc_news
-- ----------------------------
DROP TABLE IF EXISTS `tc_news`;
CREATE TABLE `tc_news`  (
  `news_id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `news_admin` int UNSIGNED NOT NULL,
  `news_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `news_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `news_cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `news_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`news_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tc_news
-- ----------------------------

-- ----------------------------
-- Table structure for tc_picture
-- ----------------------------
DROP TABLE IF EXISTS `tc_picture`;
CREATE TABLE `tc_picture`  (
  `picture_id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `picture_post` int UNSIGNED NOT NULL,
  `picture_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`picture_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tc_picture
-- ----------------------------
INSERT INTO `tc_picture` VALUES (1, 1, 'url1');
INSERT INTO `tc_picture` VALUES (2, 1, 'url2');
INSERT INTO `tc_picture` VALUES (3, 2, 'url3');
INSERT INTO `tc_picture` VALUES (4, 1, 'url4');

-- ----------------------------
-- Table structure for tc_post
-- ----------------------------
DROP TABLE IF EXISTS `tc_post`;
CREATE TABLE `tc_post`  (
  `post_id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `post_tag` int UNSIGNED NOT NULL,
  `post_user` int UNSIGNED NOT NULL,
  `post_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `post_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `post_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `post_top` tinyint(1) NOT NULL,
  `post_like` int NOT NULL,
  `post_price` decimal(10, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`post_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tc_post
-- ----------------------------
INSERT INTO `tc_post` VALUES (1, 1, 1, 'ChatGPT注册教程', '教程如下', '2023-02-12 15:21:39', 1, 666, NULL);
INSERT INTO `tc_post` VALUES (2, 1, 2, '如何装一台属于自己的台式机', '教程如下', '2023-02-12 17:02:59', 1, 555, NULL);
INSERT INTO `tc_post` VALUES (3, 2, 1, '加装内存条', '教程如下', '2023-02-12 15:23:34', 0, 222, NULL);

-- ----------------------------
-- Table structure for tc_tag
-- ----------------------------
DROP TABLE IF EXISTS `tc_tag`;
CREATE TABLE `tc_tag`  (
  `tag_id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `tag_cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`tag_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tc_tag
-- ----------------------------

-- ----------------------------
-- Table structure for tc_user
-- ----------------------------
DROP TABLE IF EXISTS `tc_user`;
CREATE TABLE `tc_user`  (
  `user_id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_admin` tinyint(1) NOT NULL,
  `user_account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_sign` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `user_sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `user_birth` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tc_user
-- ----------------------------
INSERT INTO `tc_user` VALUES (1, 0, '1001', '1111', '测试用户', NULL, NULL, NULL);
INSERT INTO `tc_user` VALUES (2, 0, '12345', '67890', '测试', NULL, NULL, NULL);
INSERT INTO `tc_user` VALUES (3, 0, '123', '12345', '测试', NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
