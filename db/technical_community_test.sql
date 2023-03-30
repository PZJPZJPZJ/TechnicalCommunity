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

 Date: 30/03/2023 23:29:58
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
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tc_chat
-- ----------------------------
INSERT INTO `tc_chat` VALUES (1, 100000, 100001, 0, '2023-03-30 23:15:13');
INSERT INTO `tc_chat` VALUES (2, 100000, 100002, 0, '2023-03-30 23:15:15');
INSERT INTO `tc_chat` VALUES (3, 100001, 100002, 0, '2023-03-09 13:05:34');
INSERT INTO `tc_chat` VALUES (4, 100003, 100000, 0, '2023-03-30 23:15:15');
INSERT INTO `tc_chat` VALUES (5, 100000, 100005, 0, '2023-03-23 18:30:51');
INSERT INTO `tc_chat` VALUES (11, 100012, 100001, 0, '2023-03-30 21:50:45');
INSERT INTO `tc_chat` VALUES (14, 100014, 100001, 0, '2023-03-30 22:31:36');
INSERT INTO `tc_chat` VALUES (15, 100000, 100013, 0, '2023-03-30 23:14:18');
INSERT INTO `tc_chat` VALUES (16, 100000, 100015, 0, '2023-03-30 23:14:21');
INSERT INTO `tc_chat` VALUES (17, 100000, 100010, 0, '2023-03-30 23:14:24');
INSERT INTO `tc_chat` VALUES (18, 100000, 100009, 0, '2023-03-30 23:14:29');
INSERT INTO `tc_chat` VALUES (19, 100000, 100008, 0, '2023-03-30 23:19:16');

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
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tc_comment
-- ----------------------------
INSERT INTO `tc_comment` VALUES (1, 2, 100001, '感谢支持新社区😊', '2023-03-22 16:42:03');
INSERT INTO `tc_comment` VALUES (2, 3, 100014, '哇，遇到站长了哈哈哈😂', '2023-03-22 17:11:30');
INSERT INTO `tc_comment` VALUES (3, 3, 100014, '站长帮我置顶嘛🥰', '2023-03-22 17:12:43');
INSERT INTO `tc_comment` VALUES (4, 2, 100000, '支持楼主，期待浅谈安卓（2）', '2023-03-22 17:52:42');
INSERT INTO `tc_comment` VALUES (8, 4, 100000, '可以小刀吗', '2023-03-22 17:56:33');
INSERT INTO `tc_comment` VALUES (9, 4, 100000, '感觉贵了些', '2023-03-22 17:56:45');
INSERT INTO `tc_comment` VALUES (10, 2, 100014, '帮顶', '2023-03-22 18:01:01');
INSERT INTO `tc_comment` VALUES (11, 2, 100014, '写得很好', '2023-03-22 18:01:07');
INSERT INTO `tc_comment` VALUES (12, 2, 100014, '可以继续出第二帖嘛', '2023-03-22 18:01:36');
INSERT INTO `tc_comment` VALUES (13, 2, 100014, '帮顶', '2023-03-22 18:01:55');
INSERT INTO `tc_comment` VALUES (14, 2, 100014, '帮顶', '2023-03-22 18:01:57');
INSERT INTO `tc_comment` VALUES (15, 2, 100014, '帮顶', '2023-03-22 18:02:00');
INSERT INTO `tc_comment` VALUES (16, 2, 100014, '帮顶', '2023-03-22 18:02:02');
INSERT INTO `tc_comment` VALUES (17, 2, 100014, '帮顶', '2023-03-22 18:02:04');
INSERT INTO `tc_comment` VALUES (18, 2, 100014, '帮顶', '2023-03-22 18:02:06');
INSERT INTO `tc_comment` VALUES (19, 2, 100014, '帮顶', '2023-03-22 18:02:08');
INSERT INTO `tc_comment` VALUES (20, 2, 100014, '继续帮顶', '2023-03-22 18:02:13');
INSERT INTO `tc_comment` VALUES (21, 1, 100000, '支持支持', '2023-03-27 17:52:58');
INSERT INTO `tc_comment` VALUES (22, 1, 100000, '有兴趣共同管理我们的社区吗', '2023-03-27 17:53:44');
INSERT INTO `tc_comment` VALUES (23, 6, 100003, '防沉', '2023-03-30 17:37:37');
INSERT INTO `tc_comment` VALUES (24, 6, 100003, '第一次使用动图功能，请多多支持', '2023-03-30 17:38:10');
INSERT INTO `tc_comment` VALUES (25, 4, 100003, '可以邮寄吗？', '2023-03-30 17:56:38');
INSERT INTO `tc_comment` VALUES (26, 4, 100003, '准备做一期旧手机测评', '2023-03-30 17:56:57');
INSERT INTO `tc_comment` VALUES (27, 4, 100003, '请大家多多支持', '2023-03-30 17:57:10');
INSERT INTO `tc_comment` VALUES (28, 5, 100012, '支持，喜欢这类的游戏体验帖', '2023-03-30 21:50:13');
INSERT INTO `tc_comment` VALUES (29, 9, 100000, '测试1', '2023-03-30 23:02:46');
INSERT INTO `tc_comment` VALUES (30, 9, 100000, '测试2', '2023-03-30 23:02:50');
INSERT INTO `tc_comment` VALUES (31, 9, 100000, '测试3', '2023-03-30 23:02:54');
INSERT INTO `tc_comment` VALUES (32, 9, 100000, '测试4', '2023-03-30 23:02:59');
INSERT INTO `tc_comment` VALUES (33, 9, 100000, '测试5', '2023-03-30 23:03:03');
INSERT INTO `tc_comment` VALUES (34, 9, 100000, '测试6', '2023-03-30 23:03:08');
INSERT INTO `tc_comment` VALUES (35, 9, 100000, '测试7', '2023-03-30 23:03:11');
INSERT INTO `tc_comment` VALUES (36, 9, 100000, '测试8', '2023-03-30 23:03:16');
INSERT INTO `tc_comment` VALUES (37, 9, 100000, '测试9', '2023-03-30 23:03:22');
INSERT INTO `tc_comment` VALUES (38, 9, 100000, '测试10', '2023-03-30 23:03:30');
INSERT INTO `tc_comment` VALUES (39, 9, 100000, '测试11', '2023-03-30 23:03:35');
INSERT INTO `tc_comment` VALUES (40, 9, 100000, '测试12', '2023-03-30 23:03:38');
INSERT INTO `tc_comment` VALUES (41, 9, 100000, '测试13', '2023-03-30 23:03:42');
INSERT INTO `tc_comment` VALUES (42, 9, 100000, '测试14', '2023-03-30 23:03:47');
INSERT INTO `tc_comment` VALUES (43, 9, 100000, '测试15', '2023-03-30 23:03:52');
INSERT INTO `tc_comment` VALUES (44, 9, 100000, '测试16', '2023-03-30 23:03:56');
INSERT INTO `tc_comment` VALUES (45, 9, 100000, '测试17', '2023-03-30 23:04:00');
INSERT INTO `tc_comment` VALUES (46, 9, 100000, '测试18', '2023-03-30 23:04:03');
INSERT INTO `tc_comment` VALUES (47, 9, 100000, '测试19', '2023-03-30 23:04:07');
INSERT INTO `tc_comment` VALUES (48, 9, 100000, '测试20', '2023-03-30 23:04:11');
INSERT INTO `tc_comment` VALUES (49, 9, 100000, '测试21', '2023-03-30 23:04:15');
INSERT INTO `tc_comment` VALUES (50, 9, 100000, '测试22', '2023-03-30 23:04:18');
INSERT INTO `tc_comment` VALUES (51, 9, 100000, '测试23', '2023-03-30 23:04:23');
INSERT INTO `tc_comment` VALUES (52, 9, 100000, '测试24', '2023-03-30 23:04:28');
INSERT INTO `tc_comment` VALUES (53, 9, 100000, '测试25', '2023-03-30 23:04:34');
INSERT INTO `tc_comment` VALUES (54, 9, 100000, '测试26', '2023-03-30 23:04:38');
INSERT INTO `tc_comment` VALUES (55, 9, 100000, '测试27', '2023-03-30 23:04:42');
INSERT INTO `tc_comment` VALUES (56, 9, 100000, '测试28', '2023-03-30 23:04:47');
INSERT INTO `tc_comment` VALUES (57, 9, 100014, '哈哈哈管理员在自己顶帖嘛', '2023-03-30 23:07:42');
INSERT INTO `tc_comment` VALUES (58, 9, 100014, '内容不错支持', '2023-03-30 23:08:02');
INSERT INTO `tc_comment` VALUES (59, 9, 100014, '希望有更详细的教程', '2023-03-30 23:08:14');

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
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tc_message
-- ----------------------------
INSERT INTO `tc_message` VALUES (1, 2, 100000, '你好', '2023-03-09 14:28:12');
INSERT INTO `tc_message` VALUES (2, 2, 100000, '你好呀', '2023-03-09 14:50:34');
INSERT INTO `tc_message` VALUES (3, 2, 100000, '哈哈哈', '2023-03-09 14:51:52');
INSERT INTO `tc_message` VALUES (4, 2, 100000, '感觉怎么样', '2023-03-09 14:52:00');
INSERT INTO `tc_message` VALUES (5, 2, 100000, '很棒', '2023-03-09 14:52:28');
INSERT INTO `tc_message` VALUES (6, 2, 100000, '好好啊', '2023-03-09 14:52:36');
INSERT INTO `tc_message` VALUES (7, 2, 100000, '不错', '2023-03-09 14:54:01');
INSERT INTO `tc_message` VALUES (8, 2, 100000, '哈哈哈', '2023-03-09 16:49:51');
INSERT INTO `tc_message` VALUES (9, 4, 100000, '哈哈哈', '2023-03-09 16:51:53');
INSERT INTO `tc_message` VALUES (10, 4, 100000, '哈哈哈', '2023-03-09 17:11:19');
INSERT INTO `tc_message` VALUES (11, 3, 100001, '哈哈哈', '2023-03-09 17:14:34');
INSERT INTO `tc_message` VALUES (12, 3, 100002, '哈哈哈', '2023-03-09 17:14:43');
INSERT INTO `tc_message` VALUES (13, 2, 100002, '哈哈哈', '2023-03-09 17:49:43');
INSERT INTO `tc_message` VALUES (14, 1, 100000, '哈哈哈', '2023-03-09 17:51:02');
INSERT INTO `tc_message` VALUES (15, 1, 100001, '哈哈哈', '2023-03-09 18:58:25');
INSERT INTO `tc_message` VALUES (16, 1, 100000, '感觉怎么样', '2023-03-09 18:58:31');
INSERT INTO `tc_message` VALUES (17, 1, 100000, '这个论坛还行吧', '2023-03-09 18:58:39');
INSERT INTO `tc_message` VALUES (18, 1, 100000, '如果可以的话请多多支持喔', '2023-03-09 18:58:54');
INSERT INTO `tc_message` VALUES (19, 1, 100000, '有什么可以改进的地方可以反馈', '2023-03-09 18:59:15');
INSERT INTO `tc_message` VALUES (20, 1, 100000, '可以看到我的消息吗', '2023-03-22 17:20:35');
INSERT INTO `tc_message` VALUES (21, 2, 100000, '哈哈哈', '2023-03-23 18:09:38');
INSERT INTO `tc_message` VALUES (22, 2, 100002, '哈哈哈', '2023-03-23 18:10:20');
INSERT INTO `tc_message` VALUES (23, 2, 100002, '好很好', '2023-03-23 18:10:30');
INSERT INTO `tc_message` VALUES (24, 2, 100000, '好', '2023-03-30 23:14:40');
INSERT INTO `tc_message` VALUES (25, 1, 100000, '回复我一下', '2023-03-30 23:14:55');
INSERT INTO `tc_message` VALUES (26, 4, 100000, '哈哈哈', '2023-03-30 23:15:01');
INSERT INTO `tc_message` VALUES (27, 4, 100000, '哈哈哈', '2023-03-30 23:15:06');
INSERT INTO `tc_message` VALUES (28, 4, 100000, '哈哈哈', '2023-03-30 23:15:11');

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
-- Records of tc_news
-- ----------------------------
INSERT INTO `tc_news` VALUES (1, 100000, '惠普新品', '惠普2023游戏家族新品发布会定档4月3日20：00，届时将发布新款暗影精灵与光影精灵游戏本', 'http://localhost:8080/img/f3d6afc23c3c493ea322040d1da66711.png', '2023-03-22 16:33:59');
INSERT INTO `tc_news` VALUES (2, 100000, '红米新品发布会', '红米Note12 Turbo官宣定档3月28日晚7点发布', 'http://localhost:8080/img/d212e74cd0164fcaa44634d49af0c50e.jpg', '2023-03-23 16:35:16');
INSERT INTO `tc_news` VALUES (3, 100000, '跨品牌也能一键换机！国产手机三巨头达成合作，成立“互传联盟”', '3月22日，国产手机三巨头：OPPO、vivo和小米达成合作，ColorOS、MIUI、OriginOS官方同时在官微上宣布成立“互传联盟”，支持跨品牌一键换机，给用户带来更好的换机体验。', 'http://localhost:8080/img/62496fd6b62a4cbab9c339e154e9d745.jpg', '2023-03-22 16:36:22');
INSERT INTO `tc_news` VALUES (4, 100000, '第二代骁龙7＋影像技术解析：核心影像技术追平旗舰', '首先在基本面上，第二代骁龙7+和第一代骁龙8+在CPU方面非常接近，采用了同样的1+3+4的架构，以及相同的4nm制程，区别只是在CPU上进行了适当的降频。这代表着，第二代骁龙7+拥有着能效更高，持久性能表现更加稳定的架构，能够胜任复杂运算、多任务切换、拍照、游戏等日常使用功能的长时间流畅体验，同时在成本上也能够得到进一步的降低。', 'http://localhost:8080/img/50caee9da40d4685bc7d50dc734246d5.png', '2023-03-22 16:37:03');

-- ----------------------------
-- Table structure for tc_picture
-- ----------------------------
DROP TABLE IF EXISTS `tc_picture`;
CREATE TABLE `tc_picture`  (
  `picture_id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `picture_post` int UNSIGNED NOT NULL,
  `picture_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  PRIMARY KEY (`picture_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tc_picture
-- ----------------------------
INSERT INTO `tc_picture` VALUES (1, 1, 'http://localhost:8080/img/260396aae0fd4d499cbc2fdcc0f1474b.jpg');
INSERT INTO `tc_picture` VALUES (2, 1, 'http://localhost:8080/img/373a718a1c7c4167b0412e655615f774.jpg');
INSERT INTO `tc_picture` VALUES (3, 1, 'http://localhost:8080/img/5114b0f876f548a78faddd0994335264.jpg');
INSERT INTO `tc_picture` VALUES (4, 1, 'http://localhost:8080/img/a6df47f01b154fa7808060cb7119cb8b.jpg');
INSERT INTO `tc_picture` VALUES (5, 1, 'http://localhost:8080/img/f138293a28cd4e8fa730758eae25bfe1.jpg');
INSERT INTO `tc_picture` VALUES (6, 2, 'http://localhost:8080/img/d5e5ea848ee5411ea807c714c967c4dc.jpeg');
INSERT INTO `tc_picture` VALUES (7, 2, 'http://localhost:8080/img/7fada992bde54e3c9fb0e6240bab028f.jpg');
INSERT INTO `tc_picture` VALUES (8, 2, 'http://localhost:8080/img/c173d32f4a344a0aa72d24ab32e397bc.jpg');
INSERT INTO `tc_picture` VALUES (9, 3, 'http://localhost:8080/img/1e58b0cbaf0647f998ec4282e813bbaf.jpg');
INSERT INTO `tc_picture` VALUES (10, 3, 'http://localhost:8080/img/fda4d9e9c5a547a393b69d453b4490ad.jpg');
INSERT INTO `tc_picture` VALUES (11, 3, 'http://localhost:8080/img/9d058036b15341f4888d20ceb9adf1b1.jpg');
INSERT INTO `tc_picture` VALUES (12, 3, 'http://localhost:8080/img/65e91a06bd3d42909fb37a339d2a4d76.jpg');
INSERT INTO `tc_picture` VALUES (13, 3, 'http://localhost:8080/img/5a99c0ef25db4637a95e37d9aa7be4ae.jpg');
INSERT INTO `tc_picture` VALUES (14, 3, 'http://localhost:8080/img/1aca2cf726454a3389695f3a1e4c606e.jpg');
INSERT INTO `tc_picture` VALUES (15, 3, 'http://localhost:8080/img/ae227e5e686b41e3a3f8c5b2a8d4ea5a.jpg');
INSERT INTO `tc_picture` VALUES (16, 4, 'http://localhost:8080/img/a05bdfadbc7b4bcfab0a9619d3246ec1.jpeg');
INSERT INTO `tc_picture` VALUES (17, 5, 'http://localhost:8080/img/c8947638a8c342918b098ad59d4921f5.jpg');
INSERT INTO `tc_picture` VALUES (18, 5, 'http://localhost:8080/img/154c1da24a9349608f4c412d24f549bd.jpg');
INSERT INTO `tc_picture` VALUES (19, 5, 'http://localhost:8080/img/7799227581564f1f8c58d3cffc62f008.jpeg');
INSERT INTO `tc_picture` VALUES (20, 6, 'http://localhost:8080/img/435c651f867e43ada017a936a029a25e.gif');
INSERT INTO `tc_picture` VALUES (21, 6, 'http://localhost:8080/img/64c2896a78b943c498703cc5f4afe0d4.gif');
INSERT INTO `tc_picture` VALUES (22, 6, 'http://localhost:8080/img/584f3e69f65e4f16bca304d11da88831.gif');
INSERT INTO `tc_picture` VALUES (23, 7, 'http://localhost:8080/img/e71d213057d349b18cdecbb6d9170d77.jpg');
INSERT INTO `tc_picture` VALUES (24, 8, 'http://localhost:8080/img/4b2c440cf3f64079817975c0ceba4b20.jpg');
INSERT INTO `tc_picture` VALUES (25, 8, 'http://localhost:8080/img/93e290d4c9ae4a74ac22e97c90b418fe.jpg');
INSERT INTO `tc_picture` VALUES (26, 8, 'http://localhost:8080/img/e9ad24431fec4399ac8cb67c700008ff.jpg');
INSERT INTO `tc_picture` VALUES (27, 9, 'http://localhost:8080/img/cd03e917fd4641eb95d72ce6be168c72.gif');
INSERT INTO `tc_picture` VALUES (28, 9, 'http://localhost:8080/img/6d54c972b64243a8a0eb4c6198b858f8.jpeg');

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
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tc_post
-- ----------------------------
INSERT INTO `tc_post` VALUES (1, 2, 100012, '堆硬件对比暴力算法，谁才是手机摄影正确的方向？', '在现今各款旗舰手机都主打摄影的时代\n手机厂商逐渐分成了硬件派和算法派\n硬件派的代表是一众国产厂商\n以超高规格的硬件来保证成像素质\n算法派的代表为三星、苹果、谷歌等外国厂商\n强调软硬结合 硬件参数一般没有那么激进\n但会大量使用定制硬件 以提升软硬结合表现\n那到底是谁会更胜一筹？\n\n抱着这个疑问 我找来了两台价格以及定位都十分接近的安卓手机 分别代表硬件派和算法派来比较看看\n他们分别是Samsung Galaxy S23+ 和 Xiaomi 13 Pro\nSamsung Galaxy S23+ 8+256 价格为 HK$7499\nXiaomi 13 Pro 12+256 价格为 HK$7699\n\n两台手机均为国际版媒体机\n软件系统也是零售版系统 与零售版并无差距\n\n📱硬件参数对比及分析：\n\n📱小米13 Pro\n\n📱主摄/广角：\n感光元件： SONY IMX989 50MP 1.0”\n镜头：23MM F1.9 OIS (由21MM裁切)\n对焦：Dual Pixel PDAF 双核相位, Laser AF 激光\n\n💡SONY IMX989 是目前最大尺寸的Mobile CIS\n4:3 TYPE-1.0 的尺寸以及1.6um的大像素\n像素合并后更可达到3.2um的尺寸\n令它拥有高动态范围以及低噪点的特性\n是目前Mobile CIS中最顶级的存在\nDPAF + Laser AF 也保证了在暗光环境的AF速度\n\n📷长焦：\n感光元件：ISOCELL S5KJN1 50MP 1/2.76\"\n镜头：75MM F2 OIS (由66MM裁切)\n对焦：PDAF 相位对焦 (10cm - ∞)\n\n💡ISOCELL JN1 是一颗高像素的中低阶Mobile CIS\n经常被用于千元级的主摄以及定位中高阶的副摄\n优点是可以较小的体积去实现高像素\n也符合小米13 Pro各方面都比较均衡的定位\n值得注意的是这颗3.2倍长焦镜头采用了F2的大光圈\n以及浮动对焦镜组 搭配上高像素 CIS 及 OIS 光学防抖\n在近摄及5倍变焦時能获得不错的表现\n\n📷超广角：\n感光元件：ISOCELL S5KJN1 50 MP 1/2.76\"\n镜头：14MM F2.2 115˚\n对焦：PDAF 相位对焦\n\n💡因为主摄使用了TYPE-1.0模组导致了厚度及体积增加\n所以超广角现在能够有足够空间加入VCM 以实', '2023-03-09 19:22:48', 1, NULL);
INSERT INTO `tc_post` VALUES (2, 2, 100013, '浅谈安卓（1）-----安卓简介', '我们先来聊聊这个很有意思的版本代号\n首先，如果让你来数数，你会咋样数？\n相信绝大多数人是“1 2 3 4 5 6 7 8 9 10......”\n现在教你谷歌的数数：“阿童木、发条机器人、纸杯蛋糕、甜甜圈、松饼、冻酸奶、姜饼、蜂巢、冰激凌三明治、果冻豆、奇巧、棒棒糖、棉花糖、牛轧糖、奥利奥、派，柑橘馅饼、红色天鹅绒蛋糕、雪花冰沙、提拉米苏......”\n（以上仅为段子，不必过于认真流汗滑稽流汗滑稽）\n不过细心的朋友会发现并觉得很好奇，安卓的版本代号为何绝大多数都是甜品（除前两个外）\n其实，在最初，安卓的代号原本是机器人系列（如阿童木，发条机器人）但后来谷歌为了避免商标问题，将代号由机器人系列转变为了现在的甜点系列\n而谷歌工程师Hiroshi Lockheimer在一篇博文中则透露，Android系统的名字涉及到谷歌软件工程师深夜加班时喜爱的各类美味（看来大家都是吃货.jpg）\n不过还有个细节\n我花了点时间浅浅地做成了表格（如下）', '2023-03-09 19:51:51', 1, NULL);
INSERT INTO `tc_post` VALUES (3, 3, 100001, '每天佩戴Applewatch超过22小时，我是怎么用好它的？', '第一部分：使用体验\n从2022年6月份购入Applewatch S6后，直至今日，我每一天都会佩戴这支手表，每天佩戴的时间则达到了22小时以上。\n“EDC”的一部分\n那么在使用体验这个部分，我主要想分享一下续航充电、功能性、佩戴感、外观颜值这四个方面的体验。\n续航：\n先来说说我的作息模式：\n- 00：30戴着100%电量的手表躺床，1点左右入睡\n- 8点醒来，戴着手表洗漱，吃早餐\n- 9点开始工作，期间手表全程开启常亮显示、保持各种消息推送和监测功能开启\n- 晚上21点左右运动45分钟～1小时。\n- 22：30左右摘下手表，此时剩余电量大概在27～35%之间，连接充电器进行充电\n- 00：30戴回手表\n至此，每天佩戴Applewatch时间超过了22小时，长此以往。', '2023-03-22 16:49:55', 1, NULL);
INSERT INTO `tc_post` VALUES (4, 47, 100001, '出售二手红米手机', '红米Note4X，9新功能完全正常，喜欢的可以点击购买私聊', '2023-03-22 16:57:07', 0, 299.00);
INSERT INTO `tc_post` VALUES (5, 48, 100014, '一份来自游戏的“爱”', '最近经常在抖音上看到一款Steam的游戏:《Love Choice》中文名字叫“拣爱”，这款游戏，无论是画风和内容上，都深受大家喜爱。我寻思着，我没有带电脑，这想玩也玩不了啊。我没有放弃，抱着试一试的心里，上了酷安一搜受虐滑稽，原来是有手机版的，既然有手机版那就好办了嘛，直接开整！受虐滑稽\n刚打开游戏的时候，就会让你先买断游戏，因为是腾讯代理的，所以比Steam上贵两块钱，我也没有过多的想法，直接支付了。进去游戏后，画面是一个简单的游戏主页。我寻思着，我会不会浪费了12块钱受虐滑稽，于是，我点击了开始游戏。\n刚开始游戏的时候，我把平时打游戏的思维投入到这个游戏当中，把他当成一款普普通通的游戏一样玩，并没有过多的想法，带着对游戏的些许怀疑，我很顺利的拿到了游戏第一个故事里的“钢铁直男”的称号，刚开头那几次，在于她的微信聊天界面当中，我并没有选择去看朋友圈，也就造成了接下来，在选择电影和美食的选项中，完完全全依照着自己的思路去选择，一遍一遍过后，结局都是在信中提的分手，在N次的尝试与参考了一点点攻略中，我终于迎来了他们走在一起的结局。', '2023-03-22 17:02:54', 1, NULL);
INSERT INTO `tc_post` VALUES (6, 3, 100003, '「非全面屏版」iOS动画细节｜一些肤浅的看法', '他们是在桌面的基础上而延伸的区域或者说是空间，表现形式为“浮在桌面之上”，但他们的最“底层”依旧是桌面（也可以说是“从桌面中来，到桌面中去”）\n最直接则是，iOS的锁屏，实际就是以通知栏作为遮掩，辅以Touch ID与传统密码作为加密屏幕的手段，这样的做法会让人感觉，锁屏与解锁后的桌面并不会产生割裂感。\n当然，以上这些不完是iOS才有的特点，事实上任何智能手机的操作系统基本都有这个逻辑。\n这样的效果虽然很符合物理直觉，但总感觉是“顿”了一下，虽说很多人不太在意，但注意到了这一点，在iOS16中作出了一些改动，也就是把“三步走”的第一步去掉，这样的好处更加跟手了。', '2023-03-30 17:32:39', 1, NULL);
INSERT INTO `tc_post` VALUES (7, 15, 100003, '雷蛇灵刃14 2022-我将在这厚砖本即将横行的年代守护14寸轻薄游戏本最后的荣耀', '这是我今年购买的第二台笔记本，比较凑巧的是上一台正好是年初购买的2021款MacBook pro 14，两款笔记本在外观方面非常接近，都使用了细腻的铝合金的阳极氧化工艺，在外观上少数几个不同可能就是颜色 A面logo B面颜值（雷蛇的下巴属实大），而且在MacBook使用上新的设计语言后，硬朗的机身线条和雷蛇更加相似了，称灵刃是暗黑MacBook也完全不为过，精致的做工一直都是我喜欢灵刃系列的原因。我上一次购买灵刃系列笔记本还是在2018年，当时购买了i7-8750h + GTX1070的配置，但是因为风扇巨大的噪音以及烫手的表面温度让我放弃了把它当作主力笔记本的想法，至今它仍然躺在国内地下室的储物间里吃灰，在那之后的一段时间里灵刃在我的心中一直是高价高颜值实用性差的笔记本代表，然而在四年后这一次灵刃靠着清仓价打动了我，原价6000刀的灵刃14 R9-6900HX+RTX 3080Ti的配置在Boxing Day打折到了3200刀，再加上公司今年送的eBay礼品卡，实付1700刀就到手了，四舍五入一下简直是白送。我本来也眼馋灵刃这个14寸的新模具很久了，本来一直没有想到要购买的理由，但是这次打折真是太香了，哪怕没理由也要买这个可能是“花瓶”的产品来体验一下。灵刃家族的传统设计，从14到17寸灵刃的外观都是类似的，黑色的阳极氧化铝合金是Windows PC中我认为质感最好的材质，雷蛇的外观从18年后的新模具开始几乎没有任何大的改动了，每一年的迭代更多是优化了内部的空间排布和散热系统，但是在今年的机型上有了两个重大的改变，一个是表面的铝合金做了工艺上的优化，使其不再像过去那样沾染指纹和油污了（实测只是好了一点，该沾还是会沾，各位硫酸手用户不贴膜的话可能用一天就变得很脏）', '2023-03-30 17:41:27', 1, NULL);
INSERT INTO `tc_post` VALUES (8, 1, 100016, '使你的Windows11更加现代化！附Windows11全局更换字体教程', '众所周知，微软对操作系统的UI界面和重要功能更新极慢，使得Windows11的充斥着大量上古遗留产物和半成品界面，并且在许多功能上有缺失。本文的目的在于以最少的学习成本和对系统性能最少的影响来改善Windows11的UI界面。\n1. Startallback\n作用：增大深色模式覆盖范围，调整文件资源管理器的外观，如有需求还可更改右键菜单。\n可以发现Startallback不仅将文件资源管理器顶部的mica效果范围延展到了搜索框，还为一些微软打死都不适配深色模式的界面添加了深色模式t耐克嘴t耐克嘴t耐克嘴。\n另外StartAllback还可以还原右键菜单，并添加亚克力效果。我本人对Windows11的右键菜单并不反感，如有需求可以自行更换。\nStartAllback官方下载地址：查看链接\n使用方法：将这些选项勾选即可。\nStartAllback还可以更换任务栏和开始菜单，但会牺牲性能和稳定性，我并不推荐，建议将这些功能都关闭。况且它给的样式个人觉得并不比默认的好看。\n2. MicaForEveryone\n作用：将mica效果更改为亚克力效果（对UWP和windowsApp SDK应用不生效）\n在Windows11 22H2中，微软为应用标题栏引入了Mica效果，MicaForEveryone可以将这些Mica效果更换为亚克力效果。目前对文件资源管理器、记事本、画图和大部分使用Windows默认标题栏的应用生效，甚至安卓子系统窗口也行。\nMicaForEveryone还可以强制某些应用的标题栏跟随系统的深色模式\nMicaForEveryone下载地址：查看链接\n使用方法：\n将所有配置中的标题栏颜色改为跟随系统，模糊类型改为亚克力即可，并将应用设置为开机自启，否则可能不会生效。\n你也可以自定义配置，单独控制某个应用窗口的效果。\n不推荐更改圆角类型和高级选项中的内容，可能会导致应用显示异常。\n3. Auto Dark Mode\n作用：自动开启/关闭深色模式\n（我也不知道微软为什么这么久了还不加入深色模式自动切换，莫非是要献祭阿三才行吗t耐克嘴？？？）\n软件目前已经上架微软应用商店，并支持中文，直接搜索即可', '2023-03-30 22:52:31', 1, NULL);
INSERT INTO `tc_post` VALUES (9, 2, 100000, '关于安卓制作音乐相片的解决方案', '最近看到大家都在做音乐相片这个东西，猛然想到以前我好像做过类似玩意。\n关于iOS用快捷指令达成音乐墙的解决方案，而下方评论有好多网友在寻求安卓这边该怎么操作。然后。。。就有了我。', '2023-03-30 22:58:27', 1, NULL);

-- ----------------------------
-- Table structure for tc_tag
-- ----------------------------
DROP TABLE IF EXISTS `tc_tag`;
CREATE TABLE `tc_tag`  (
  `tag_id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `tag_cover` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  PRIMARY KEY (`tag_id`, `tag_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 49 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

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
INSERT INTO `tc_tag` VALUES (47, '二手物品', 'http://localhost:8080/img/a0899a7bf0fd4c17acf51a7bd53682d9.jpg');
INSERT INTO `tc_tag` VALUES (48, '2D游戏', 'http://localhost:8080/img/c9769dc4f90c47699f0494768adb9ed1.png');

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
) ENGINE = InnoDB AUTO_INCREMENT = 100016 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tc_user
-- ----------------------------
INSERT INTO `tc_user` VALUES (100000, 1, '$2a$10$ULux4VKe8JxZ77trA1fwi.WQ.243Prhk6cx0GsFH5o8orQXgYRMc.', '社区管理员', 'http://localhost:8080/img/eab11c3dd2d84353be4b6c4b2adf5204.jpg', '维护科技社区氛围是我的职责', '男', '2022-12-31 16:00:00');
INSERT INTO `tc_user` VALUES (100001, 1, '$2a$10$/pLgHwjVM3GIT09ZggzNOefsZ1crxC74vbK3rfIscoZVFbdG54kbK', '站长', 'http://localhost:8080/img/e85a3fe9c7574faf990e3e6c835647c3.jpg', '有什么问题可以向我反馈喔', '男', '2023-02-08 18:05:31');
INSERT INTO `tc_user` VALUES (100002, 0, '$2a$10$lQqKjIO3GkIqRR1Y1ya54umTdLBuupv6O7m91TwMZ6Lz.y6i.uXtm', '科技媒体', 'http://localhost:8080/img/5ba4fa43635841eea7f3c2ea0a48dc6f.jpg', '第一手新鲜资讯会呈现给大家', '女', '2023-02-08 16:00:00');
INSERT INTO `tc_user` VALUES (100003, 0, '$2a$10$r7ecGX/XKksKgCJqz8OyvOTAsnY7l6aM8KlCenHK5Ha1zvNYxopq2', '科技美学', 'http://localhost:8080/img/31a6ca2b62dd426d88804913bf51a447.jpg', '大家好这里是科技美学', '男', NULL);
INSERT INTO `tc_user` VALUES (100004, 0, '$2a$10$Vu7xyO1DTPKGyJ09jv7KiuO29bDhG8UzT72R3dNmkaJ627sJaV36K', '刷机玩家', 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png', NULL, NULL, NULL);
INSERT INTO `tc_user` VALUES (100005, 0, '$2a$10$nn3KbzdUjTsrdrzJbLwlnOcHQrYg3d.9arwjPADrCEUfNk/IJJgcW', 'VR资讯', 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png', NULL, NULL, NULL);
INSERT INTO `tc_user` VALUES (100006, 0, '$2a$10$ndQw1GY5B2Pb6o3XAH4rVeSSclhpEVI/NhRXNgHkBLrWpR.103G42', '任务管理器', 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png', NULL, NULL, NULL);
INSERT INTO `tc_user` VALUES (100007, 0, '$2a$10$HhOo1sycQTnu8WlbF2Va4uFkrJXRhat20zIOCNTWYCtGXe97MGA8C', 'Vue小哥', 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png', NULL, NULL, NULL);
INSERT INTO `tc_user` VALUES (100008, 0, '$2a$10$sM24.Na3NztI61evh0A4Fu9JQtjQKZDaGxtoM0Q0L5gf2go97Y2J.', 'IOS越狱支持', 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png', NULL, NULL, NULL);
INSERT INTO `tc_user` VALUES (100009, 0, '$2a$10$o5ANzRhfcAKi0Mo7moHHJ.fqA0ggdTqdDhPrG3D1VObU/zhsJsCHC', '社区小编', 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png', NULL, NULL, NULL);
INSERT INTO `tc_user` VALUES (100010, 0, '$2a$10$gPsqLS/GpYjD5rdukil9DubZ.RBTEQqqLQwg5xkXA4GL5GjvSV3Ty', '营销号', 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png', NULL, NULL, NULL);
INSERT INTO `tc_user` VALUES (100011, 0, '$2a$10$jOgDuK9.nokOo/D7.XLEae.MvY1ckM.tucrbgK2hVwPpui9Vd8MNO', '邻站小编', 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png', NULL, NULL, NULL);
INSERT INTO `tc_user` VALUES (100012, 0, '$2a$10$HhQfnpRSwTn3qMLU5iWO9eBEukUNbITQuK0D6O.0NYCl6b/idW7Vy', '拍照盲评', 'http://localhost:8080/img/77a88b25fd004ab099efe2cb3ef73ef8.jpg', '欢迎参加拍照盲评', '男', '2023-01-02 16:00:00');
INSERT INTO `tc_user` VALUES (100013, 0, '$2a$10$0aMXrvH.hZwj9H1APgg89Oy78/r4e7SdX12kZPZpX2e/vLdP1Jska', '软件科普', 'http://localhost:8080/img/ff0c13606e9d4c7aa99921afbfef2087.jpg', NULL, NULL, NULL);
INSERT INTO `tc_user` VALUES (100014, 0, '$2a$10$ELKRsKqrkOzArbSWGLTW2eBA28mdBDDAPLFbUa3zLzA9emhnjzVxG', 'Starry_LV', 'http://localhost:8080/img/256c8cdb153748c39038b2cfa3df1c86.jpg', NULL, '女', '2000-12-31 16:00:00');
INSERT INTO `tc_user` VALUES (100015, 0, '$2a$10$ZmtqNu5zgB0bAADdt//8IuIblUsiJ66I2gPm0XRg9MzsSPib02sSW', 'Stillshinin', 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png', NULL, NULL, NULL);
INSERT INTO `tc_user` VALUES (100016, 0, '$2a$10$xFB/QyihQuxxi3W50gR9Vu8NYnXyN8DMVVxvxo1Z7kyESV0LR83tW', 'Windows资讯', 'http://localhost:8080/img/58594c8d78f74f88b1412171e4adabd7.jpg', '给大家呈现第一手更新资讯', '男', '2023-01-31 16:00:00');

SET FOREIGN_KEY_CHECKS = 1;
