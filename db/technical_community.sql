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

 Date: 08/03/2023 23:42:31
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
  `chat_unread` int UNSIGNED NOT NULL DEFAULT 0,
  `chat_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`chat_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tc_chat
-- ----------------------------

-- ----------------------------
-- Table structure for tc_comment
-- ----------------------------
DROP TABLE IF EXISTS `tc_comment`;
CREATE TABLE `tc_comment`  (
  `comment_id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `comment_post` int UNSIGNED NOT NULL,
  `comment_user` int UNSIGNED NOT NULL,
  `comment_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `comment_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

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
  `message_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `message_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`message_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

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
  `news_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `news_cover` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `news_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`news_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

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
  `picture_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  PRIMARY KEY (`picture_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tc_picture
-- ----------------------------

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tc_post
-- ----------------------------

-- ----------------------------
-- Table structure for tc_tag
-- ----------------------------
DROP TABLE IF EXISTS `tc_tag`;
CREATE TABLE `tc_tag`  (
  `tag_id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `tag_cover` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  PRIMARY KEY (`tag_id`, `tag_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tc_tag
-- ----------------------------
INSERT INTO `tc_tag` VALUES (1, 'Windows', 'http://localhost:8080/img/c173f62129034b289c627104090e701a.jpg');
INSERT INTO `tc_tag` VALUES (2, '安卓', 'http://localhost:8080/img/ccc98110f3674693a6bed7b5ab4c8dd5.png');
INSERT INTO `tc_tag` VALUES (3, 'IOS', 'http://localhost:8080/img/be9f699e516045f6918fddce5bcd4f45.png');
INSERT INTO `tc_tag` VALUES (4, '小米', 'http://localhost:8080/img/113a3a45612a493c90212dc3a4f3fe26.jpg');
INSERT INTO `tc_tag` VALUES (5, '华为', 'http://localhost:8080/img/8ba345abf11344a8881d394c900f0b50.png');
INSERT INTO `tc_tag` VALUES (6, '三星', 'http://localhost:8080/img/7273dad311494c70bd3df74a741568aa.png');
INSERT INTO `tc_tag` VALUES (7, 'MIUI', 'http://localhost:8080/img/9de804cbbede40c78a2a5c051f575a70.jpg');
INSERT INTO `tc_tag` VALUES (8, 'ChatGPT', 'http://localhost:8080/img/5aa88bcbcea44f0aa3be2278e5308d13.png');
INSERT INTO `tc_tag` VALUES (9, '群晖', 'http://localhost:8080/img/a22051506d9640679d59e7fb67ccee4b.jpg');
INSERT INTO `tc_tag` VALUES (10, 'SpringBoot', 'http://localhost:8080/img/53aef8c149194f43bd74418a1d3cc49a.png');
INSERT INTO `tc_tag` VALUES (11, 'Vue', 'http://localhost:8080/img/b2c7f53907de4667910f142e2ec11811.png');
INSERT INTO `tc_tag` VALUES (12, 'iPhone', 'http://localhost:8080/img/9eb6cf9bc82b4a0e83a5f7448e69b2eb.jpg');
INSERT INTO `tc_tag` VALUES (13, 'iPad', 'http://localhost:8080/img/fba9ada4e79b4cceb3086d9ca1148bdd.png');
INSERT INTO `tc_tag` VALUES (14, '小米12S', 'http://localhost:8080/img/90ab512e099b4650a446a02a591c25b1.png');
INSERT INTO `tc_tag` VALUES (15, '雷蛇灵刃', 'http://localhost:8080/img/9804277d2ef54c88aeaf31fdf8dadf68.png');
INSERT INTO `tc_tag` VALUES (16, 'AirPods', 'http://localhost:8080/img/6042f1d557f94470938050c3ed292a33.png');
INSERT INTO `tc_tag` VALUES (17, '联想拯救者', 'http://localhost:8080/img/f14d4312a1ea405f8cdd06f82a8f91b0.jpg');
INSERT INTO `tc_tag` VALUES (18, '罗技G304', 'http://localhost:8080/img/fde6967f456d4bd99cd790e2708f10e3.jpg');
INSERT INTO `tc_tag` VALUES (19, 'GT-AX11000', 'http://localhost:8080/img/4b05d99c3598479ab116008e77fdfcc5.png');
INSERT INTO `tc_tag` VALUES (20, '小米万兆路由器', 'http://localhost:8080/img/77d4c8230e244cf7a99f46048b09e1fb.png');
INSERT INTO `tc_tag` VALUES (21, 'Quest2', 'http://localhost:8080/img/d44802441bf14855b5fccbc31feb7d68.png');
INSERT INTO `tc_tag` VALUES (22, 'HomePod', 'http://localhost:8080/img/aa6e084aec4f4b8ab7972e5b8397fa90.jpg');
INSERT INTO `tc_tag` VALUES (23, '小爱音箱Pro', 'http://localhost:8080/img/430921779b0844a5b4b620e646480423.jpg');
INSERT INTO `tc_tag` VALUES (24, 'Pico4', 'http://localhost:8080/img/7975152fb09f4f8fa8e8517b8800b7d2.jpg');
INSERT INTO `tc_tag` VALUES (25, '索尼PS5', 'http://localhost:8080/img/8316fa4a62d243228bd1aad6c95bb471.jpg');
INSERT INTO `tc_tag` VALUES (26, 'MacBookAir', 'http://localhost:8080/img/4e7b1d4a868a4ec6b67a9d451be5bbe2.jpg');
INSERT INTO `tc_tag` VALUES (27, '小米手环7', 'http://localhost:8080/img/e177a1890a774b8489060b2d9bcd7865.png');
INSERT INTO `tc_tag` VALUES (28, '雷蛇黑寡妇V3', 'http://localhost:8080/img/1803df04eac944ac8b95427aa03e0de9.jpg');
INSERT INTO `tc_tag` VALUES (29, 'Docker', 'http://localhost:8080/img/3314400201984c5d88fa2410e15fceb0.png');
INSERT INTO `tc_tag` VALUES (30, 'Golang', 'http://localhost:8080/img/a8c73ae3cd6e499e989cf480eda28837.png');
INSERT INTO `tc_tag` VALUES (31, 'PhotoShop', 'http://localhost:8080/img/1c4e75ce9f48465997bb3e3c8a3c7264.png');
INSERT INTO `tc_tag` VALUES (32, 'DaVinci', 'http://localhost:8080/img/22e5f7b1930e4af68286b0d41653a1dd.png');
INSERT INTO `tc_tag` VALUES (33, 'PremierePro', 'http://localhost:8080/img/c5ee54aa4c5c48a384d14d3fa8b118a0.png');
INSERT INTO `tc_tag` VALUES (34, 'Unity', 'http://localhost:8080/img/2e4359e7402745029d383d27d66cd8cd.jpg');
INSERT INTO `tc_tag` VALUES (35, '虚幻', 'http://localhost:8080/img/13bdf5e8caad4a59bd973b927dd3990c.png');
INSERT INTO `tc_tag` VALUES (36, 'RTX3070', 'http://localhost:8080/img/90bde37124ab46d3b3ce1c973b1878bc.jpg');
INSERT INTO `tc_tag` VALUES (37, 'i9 13900K', 'http://localhost:8080/img/40475bed965549178d85427e5409a5da.png');
INSERT INTO `tc_tag` VALUES (38, 'OBS Studio', 'http://localhost:8080/img/192a3728d2ec407f98caac3c7c54776c.jpg');
INSERT INTO `tc_tag` VALUES (39, '电脑组装', 'http://localhost:8080/img/d1f766c9dbe946018a29383fc7520f98.jpg');
INSERT INTO `tc_tag` VALUES (40, '桌搭', 'http://localhost:8080/img/fe28e5eb26f746d9ac06257cf2694d3a.jpg');
INSERT INTO `tc_tag` VALUES (41, 'JetBrains', 'http://localhost:8080/img/1acc23a30fb54730bd52363e53e225b4.png');
INSERT INTO `tc_tag` VALUES (42, 'Office', 'http://localhost:8080/img/609a14d2820b4a19bbea5b72378a286a.png');
INSERT INTO `tc_tag` VALUES (43, 'MySQL', 'http://localhost:8080/img/d9cd6e2084a748a6b54b02e149fb9c59.png');
INSERT INTO `tc_tag` VALUES (44, 'MyBatis', 'http://localhost:8080/img/0dee144f301642f181cce358fb4f5a75.png');
INSERT INTO `tc_tag` VALUES (45, 'Moonlight', 'http://localhost:8080/img/fcac2760d4604e5cb025822a4f03878b.png');
INSERT INTO `tc_tag` VALUES (46, 'Arc A380', 'http://localhost:8080/img/ad3897bb177540ae93c5a2e4f7e0187e.jpg');

-- ----------------------------
-- Table structure for tc_user
-- ----------------------------
DROP TABLE IF EXISTS `tc_user`;
CREATE TABLE `tc_user`  (
  `user_id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_admin` tinyint(1) NOT NULL,
  `user_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_sign` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `user_sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `user_birth` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100001 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tc_user
-- ----------------------------
INSERT INTO `tc_user` VALUES (100000, 1, '$2a$10$ULux4VKe8JxZ77trA1fwi.WQ.243Prhk6cx0GsFH5o8orQXgYRMc.', '管理员', NULL, NULL, NULL);
INSERT INTO `tc_user` VALUES (100001, 0, '$2a$10$/pLgHwjVM3GIT09ZggzNOefsZ1crxC74vbK3rfIscoZVFbdG54kbK', '测试用户', NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
