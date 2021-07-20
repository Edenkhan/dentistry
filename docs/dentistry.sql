/*
 Navicat Premium Data Transfer

 Source Server         : Easy
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : localhost:3306
 Source Schema         : dentistry

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 20/07/2021 23:17:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for appointmanage
-- ----------------------------
DROP TABLE IF EXISTS `appointmanage`;
CREATE TABLE `appointmanage`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdDate` datetime NULL DEFAULT NULL,
  `lastModifiedDate` datetime NULL DEFAULT NULL,
  `version` int(11) NULL DEFAULT NULL,
  `topLimit` int(11) NULL DEFAULT NULL COMMENT '预约上限',
  `appointNum` int(11) NULL DEFAULT NULL COMMENT '已预约次数',
  `appointDate` datetime NULL DEFAULT NULL COMMENT '预约日期',
  `timePeriod` tinyint(4) NULL DEFAULT NULL COMMENT '预约时间段 0-am 1-pm',
  `enabled` bit(1) NULL DEFAULT NULL COMMENT '是否开启预约',
  `shopId` bigint(20) NULL DEFAULT NULL COMMENT '门店id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 65 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of appointmanage
-- ----------------------------
INSERT INTO `appointmanage` VALUES (1, '2021-07-15 17:25:11', '2021-07-20 17:53:58', 5, 5, 0, '2021-07-15 00:00:00', 0, b'1', 1);
INSERT INTO `appointmanage` VALUES (2, '2021-07-15 17:25:11', '2021-07-20 17:53:58', 4, 5, 1, '2021-07-16 00:00:00', 0, b'1', 1);
INSERT INTO `appointmanage` VALUES (3, '2021-07-15 17:25:11', '2021-07-20 17:53:58', 5, 5, 2, '2021-07-17 00:00:00', 0, b'1', 1);
INSERT INTO `appointmanage` VALUES (4, '2021-07-15 17:25:11', '2021-07-20 17:53:58', 5, 5, 0, '2021-07-18 00:00:00', 0, b'1', 1);
INSERT INTO `appointmanage` VALUES (5, '2021-07-15 17:25:11', '2021-07-20 17:53:58', 13, 5, 8, '2021-07-19 00:00:00', 0, b'1', 1);
INSERT INTO `appointmanage` VALUES (6, '2021-07-15 17:25:11', '2021-07-20 17:53:58', 5, 5, 5, '2021-07-20 00:00:00', 0, b'1', 1);
INSERT INTO `appointmanage` VALUES (7, '2021-07-15 17:25:11', '2021-07-20 18:46:05', 6, 5, 1, '2021-07-21 00:00:00', 0, b'1', 1);
INSERT INTO `appointmanage` VALUES (8, '2021-07-15 17:25:11', '2021-07-20 17:53:58', 2, 6, 0, '2021-07-15 00:00:00', 1, b'1', 1);
INSERT INTO `appointmanage` VALUES (9, '2021-07-15 17:25:11', '2021-07-20 17:53:58', 2, 6, 0, '2021-07-16 00:00:00', 1, b'1', 1);
INSERT INTO `appointmanage` VALUES (10, '2021-07-15 17:25:11', '2021-07-20 17:53:58', 2, 6, 0, '2021-07-17 00:00:00', 1, b'1', 1);
INSERT INTO `appointmanage` VALUES (11, '2021-07-15 17:25:11', '2021-07-20 17:53:58', 2, 6, 0, '2021-07-18 00:00:00', 1, b'1', 1);
INSERT INTO `appointmanage` VALUES (12, '2021-07-15 17:25:11', '2021-07-20 17:53:58', 2, 6, 0, '2021-07-19 00:00:00', 1, b'1', 1);
INSERT INTO `appointmanage` VALUES (13, '2021-07-15 17:25:11', '2021-07-20 17:53:58', 3, 6, 0, '2021-07-20 00:00:00', 1, b'0', 1);
INSERT INTO `appointmanage` VALUES (14, '2021-07-15 17:25:11', '2021-07-20 17:53:58', 3, 6, 0, '2021-07-21 00:00:00', 1, b'0', 1);
INSERT INTO `appointmanage` VALUES (15, '2021-07-15 17:25:11', NULL, 0, 50, 0, '2021-07-15 00:00:00', 0, b'1', 2);
INSERT INTO `appointmanage` VALUES (16, '2021-07-15 17:25:11', NULL, 0, 50, 0, '2021-07-16 00:00:00', 0, b'1', 2);
INSERT INTO `appointmanage` VALUES (17, '2021-07-15 17:25:11', NULL, 0, 50, 0, '2021-07-17 00:00:00', 0, b'1', 2);
INSERT INTO `appointmanage` VALUES (18, '2021-07-15 17:25:11', NULL, 0, 50, 0, '2021-07-18 00:00:00', 0, b'1', 2);
INSERT INTO `appointmanage` VALUES (19, '2021-07-15 17:25:11', NULL, 0, 50, 0, '2021-07-19 00:00:00', 0, b'1', 2);
INSERT INTO `appointmanage` VALUES (20, '2021-07-15 17:25:11', '2021-07-20 23:14:15', 2, 50, 0, '2021-07-20 00:00:00', 0, b'1', 2);
INSERT INTO `appointmanage` VALUES (21, '2021-07-15 17:25:11', NULL, 0, 50, 0, '2021-07-21 00:00:00', 0, b'1', 2);
INSERT INTO `appointmanage` VALUES (22, '2021-07-15 17:25:11', NULL, 0, 50, 0, '2021-07-15 00:00:00', 1, b'1', 2);
INSERT INTO `appointmanage` VALUES (23, '2021-07-15 17:25:11', NULL, 0, 50, 0, '2021-07-16 00:00:00', 1, b'1', 2);
INSERT INTO `appointmanage` VALUES (24, '2021-07-15 17:25:11', NULL, 0, 50, 0, '2021-07-17 00:00:00', 1, b'1', 2);
INSERT INTO `appointmanage` VALUES (25, '2021-07-15 17:25:11', NULL, 0, 50, 0, '2021-07-18 00:00:00', 1, b'1', 2);
INSERT INTO `appointmanage` VALUES (26, '2021-07-15 17:25:11', NULL, 0, 50, 0, '2021-07-19 00:00:00', 1, b'1', 2);
INSERT INTO `appointmanage` VALUES (27, '2021-07-15 17:25:11', NULL, 0, 50, 0, '2021-07-20 00:00:00', 1, b'1', 2);
INSERT INTO `appointmanage` VALUES (28, '2021-07-15 17:25:11', NULL, 0, 50, 0, '2021-07-21 00:00:00', 1, b'1', 2);
INSERT INTO `appointmanage` VALUES (29, '2021-07-16 09:22:35', '2021-07-20 18:33:42', 6, 5, 1, '2021-07-22 00:00:00', 0, b'1', 1);
INSERT INTO `appointmanage` VALUES (30, '2021-07-16 09:22:35', '2021-07-20 17:53:58', 2, 6, 0, '2021-07-22 00:00:00', 1, b'1', 1);
INSERT INTO `appointmanage` VALUES (31, '2021-07-16 09:22:35', NULL, 0, 50, 0, '2021-07-22 00:00:00', 0, b'1', 2);
INSERT INTO `appointmanage` VALUES (32, '2021-07-16 09:22:35', NULL, 0, 50, 0, '2021-07-22 00:00:00', 1, b'1', 2);
INSERT INTO `appointmanage` VALUES (33, '2021-07-17 00:13:13', '2021-07-20 17:53:58', 4, 5, 5, '2021-07-23 00:00:00', 0, b'1', 1);
INSERT INTO `appointmanage` VALUES (34, '2021-07-17 00:13:13', '2021-07-20 17:53:58', 2, 6, 0, '2021-07-23 00:00:00', 1, b'1', 1);
INSERT INTO `appointmanage` VALUES (35, '2021-07-17 00:13:13', NULL, 0, 50, 0, '2021-07-23 00:00:00', 0, b'1', 2);
INSERT INTO `appointmanage` VALUES (36, '2021-07-17 00:13:13', NULL, 0, 50, 0, '2021-07-23 00:00:00', 1, b'1', 2);
INSERT INTO `appointmanage` VALUES (37, '2021-07-19 09:21:18', '2021-07-20 17:53:58', 4, 5, 0, '2021-07-24 00:00:00', 0, b'0', 1);
INSERT INTO `appointmanage` VALUES (38, '2021-07-19 09:21:18', '2021-07-20 23:11:17', 6, 5, 1, '2021-07-25 00:00:00', 0, b'1', 1);
INSERT INTO `appointmanage` VALUES (39, '2021-07-19 09:21:18', '2021-07-20 17:53:58', 2, 6, 0, '2021-07-24 00:00:00', 1, b'1', 1);
INSERT INTO `appointmanage` VALUES (40, '2021-07-19 09:21:18', '2021-07-20 17:53:58', 2, 6, 0, '2021-07-25 00:00:00', 1, b'0', 1);
INSERT INTO `appointmanage` VALUES (41, '2021-07-19 09:21:18', '2021-07-20 23:14:15', 1, 50, 1, '2021-07-24 00:00:00', 0, b'1', 2);
INSERT INTO `appointmanage` VALUES (42, '2021-07-19 09:21:18', NULL, 0, 50, 0, '2021-07-25 00:00:00', 0, b'1', 2);
INSERT INTO `appointmanage` VALUES (43, '2021-07-19 09:21:18', NULL, 0, 50, 0, '2021-07-24 00:00:00', 1, b'1', 2);
INSERT INTO `appointmanage` VALUES (44, '2021-07-19 09:21:18', NULL, 0, 50, 0, '2021-07-25 00:00:00', 1, b'1', 2);
INSERT INTO `appointmanage` VALUES (45, '2021-07-19 14:16:28', '2021-07-20 15:46:27', 9, 6, 0, '2021-07-19 00:00:00', 0, b'0', 3);
INSERT INTO `appointmanage` VALUES (46, '2021-07-19 14:16:28', '2021-07-20 15:46:27', 10, 6, 0, '2021-07-20 00:00:00', 0, b'1', 3);
INSERT INTO `appointmanage` VALUES (47, '2021-07-19 14:16:28', '2021-07-20 15:46:27', 9, 6, 0, '2021-07-21 00:00:00', 0, b'0', 3);
INSERT INTO `appointmanage` VALUES (48, '2021-07-19 14:16:28', '2021-07-20 15:46:27', 9, 6, 1, '2021-07-22 00:00:00', 0, b'1', 3);
INSERT INTO `appointmanage` VALUES (49, '2021-07-19 14:16:28', '2021-07-20 15:46:27', 8, 6, 0, '2021-07-23 00:00:00', 0, b'1', 3);
INSERT INTO `appointmanage` VALUES (50, '2021-07-19 14:16:28', '2021-07-20 15:46:27', 8, 6, 0, '2021-07-24 00:00:00', 0, b'1', 3);
INSERT INTO `appointmanage` VALUES (51, '2021-07-19 14:16:28', '2021-07-20 15:46:27', 8, 6, 0, '2021-07-25 00:00:00', 0, b'1', 3);
INSERT INTO `appointmanage` VALUES (52, '2021-07-19 14:16:28', '2021-07-20 15:46:27', 6, 7, 0, '2021-07-19 00:00:00', 1, b'1', 3);
INSERT INTO `appointmanage` VALUES (53, '2021-07-19 14:16:28', '2021-07-20 15:46:27', 6, 7, 0, '2021-07-20 00:00:00', 1, b'1', 3);
INSERT INTO `appointmanage` VALUES (54, '2021-07-19 14:16:28', '2021-07-20 15:46:27', 7, 7, 0, '2021-07-21 00:00:00', 1, b'0', 3);
INSERT INTO `appointmanage` VALUES (55, '2021-07-19 14:16:28', '2021-07-20 15:46:27', 6, 7, 0, '2021-07-22 00:00:00', 1, b'1', 3);
INSERT INTO `appointmanage` VALUES (56, '2021-07-19 14:16:28', '2021-07-20 15:46:27', 6, 7, 0, '2021-07-23 00:00:00', 1, b'1', 3);
INSERT INTO `appointmanage` VALUES (57, '2021-07-19 14:16:28', '2021-07-20 15:46:27', 6, 7, 0, '2021-07-24 00:00:00', 1, b'1', 3);
INSERT INTO `appointmanage` VALUES (58, '2021-07-19 14:16:28', '2021-07-20 15:46:27', 6, 7, 0, '2021-07-25 00:00:00', 1, b'1', 3);
INSERT INTO `appointmanage` VALUES (59, '2021-07-20 09:23:07', '2021-07-20 17:53:58', 4, 5, 0, '2021-07-26 00:00:00', 0, b'0', 1);
INSERT INTO `appointmanage` VALUES (60, '2021-07-20 09:23:07', '2021-07-20 17:53:58', 2, 6, 6, '2021-07-26 00:00:00', 1, b'1', 1);
INSERT INTO `appointmanage` VALUES (61, '2021-07-20 09:23:07', NULL, 0, 50, 0, '2021-07-26 00:00:00', 0, b'1', 2);
INSERT INTO `appointmanage` VALUES (62, '2021-07-20 09:23:07', NULL, 0, 50, 0, '2021-07-26 00:00:00', 1, b'1', 2);
INSERT INTO `appointmanage` VALUES (63, '2021-07-20 09:23:07', '2021-07-20 15:46:27', 6, 6, 0, '2021-07-26 00:00:00', 0, b'1', 3);
INSERT INTO `appointmanage` VALUES (64, '2021-07-20 09:23:07', '2021-07-20 15:46:27', 4, 7, 0, '2021-07-26 00:00:00', 1, b'1', 3);

-- ----------------------------
-- Table structure for appointment
-- ----------------------------
DROP TABLE IF EXISTS `appointment`;
CREATE TABLE `appointment`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdDate` datetime NULL DEFAULT NULL,
  `lastModifiedDate` datetime NULL DEFAULT NULL,
  `version` int(11) NULL DEFAULT NULL,
  `appointDate` datetime NULL DEFAULT NULL COMMENT '预约日期',
  `arrivedDate` datetime NULL DEFAULT NULL COMMENT '到店日期',
  `timePeriod` tinyint(4) NULL DEFAULT NULL COMMENT '0-AM 1-PM',
  `appointState` tinyint(4) NULL DEFAULT NULL COMMENT '0-预约中 1-预约完成',
  `reportStatus` tinyint(4) NULL DEFAULT NULL COMMENT '0-报告未上传 1-报告已上传',
  `orderId` bigint(20) NULL DEFAULT NULL COMMENT '订单id',
  `userId` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  `productId` bigint(20) NULL DEFAULT NULL COMMENT '产品id',
  `shopId` bigint(20) NULL DEFAULT NULL COMMENT '门店id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of appointment
-- ----------------------------
INSERT INTO `appointment` VALUES (1, '2021-07-19 12:04:41', '2021-07-19 12:05:48', 1, '2021-07-19 00:00:00', NULL, 0, 1, 1, 1, 1, 3, 1);
INSERT INTO `appointment` VALUES (2, '2021-07-19 12:06:59', '2021-07-19 12:08:48', 2, '2021-07-19 00:00:00', NULL, 0, 1, 1, 2, 1, 3, 1);
INSERT INTO `appointment` VALUES (3, '2021-07-19 12:10:58', '2021-07-19 12:13:15', 2, '2021-07-19 00:00:00', NULL, 0, 1, 1, 3, 1, 2, 1);
INSERT INTO `appointment` VALUES (4, '2021-07-19 12:22:17', '2021-07-19 14:00:05', 3, '2021-07-20 00:00:00', NULL, 0, 1, 1, 3, 1, 2, 1);
INSERT INTO `appointment` VALUES (5, '2021-07-19 14:00:22', '2021-07-19 14:00:37', 2, '2021-07-21 00:00:00', NULL, 0, 1, 1, 3, 1, 2, 1);
INSERT INTO `appointment` VALUES (6, '2021-07-19 14:00:45', '2021-07-19 14:01:04', 2, '2021-07-22 00:00:00', NULL, 0, 1, 1, 3, 1, 2, 1);
INSERT INTO `appointment` VALUES (7, '2021-07-19 14:01:13', '2021-07-19 14:01:31', 2, '2021-07-23 00:00:00', NULL, 0, 1, 1, 3, 1, 2, 1);
INSERT INTO `appointment` VALUES (8, '2021-07-19 14:24:55', '2021-07-19 14:31:12', 3, '2021-07-22 00:00:00', NULL, 0, 1, 1, 4, 1, 4, 3);
INSERT INTO `appointment` VALUES (9, '2021-07-20 18:21:46', '2021-07-20 23:13:02', 5, '2021-07-25 00:00:00', NULL, 0, 1, 1, 5, 1, 4, 1);
INSERT INTO `appointment` VALUES (10, '2021-07-20 23:14:00', '2021-07-20 23:14:15', 1, '2021-07-24 00:00:00', NULL, 0, 0, 0, 6, 1, 4, 2);

-- ----------------------------
-- Table structure for dictionary
-- ----------------------------
DROP TABLE IF EXISTS `dictionary`;
CREATE TABLE `dictionary`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdDate` datetime NULL DEFAULT NULL,
  `lastModifiedDate` datetime NULL DEFAULT NULL,
  `version` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典名称',
  `mark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典标识',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of dictionary
-- ----------------------------
INSERT INTO `dictionary` VALUES (1, '2021-06-26 13:05:07', '2021-06-28 12:27:39', 7, '医生', 'DOCTOR');
INSERT INTO `dictionary` VALUES (2, '2021-06-28 11:18:38', '2021-06-28 16:59:02', 7, '超人', 'SUPERMAN');

-- ----------------------------
-- Table structure for dictionaryitem
-- ----------------------------
DROP TABLE IF EXISTS `dictionaryitem`;
CREATE TABLE `dictionaryitem`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdDate` datetime NULL DEFAULT NULL,
  `lastModifiedDate` datetime NULL DEFAULT NULL,
  `version` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典详情名称',
  `enabled` bit(1) NULL DEFAULT NULL COMMENT '是否启用',
  `dictionaryId` bigint(20) NULL DEFAULT NULL COMMENT '父字典id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of dictionaryitem
-- ----------------------------
INSERT INTO `dictionaryitem` VALUES (1, '2021-06-28 11:41:39', '2021-06-28 17:59:47', 17, '老王', b'1', 1);
INSERT INTO `dictionaryitem` VALUES (2, '2021-06-28 11:52:30', '2021-07-20 12:35:28', 17, '老王', b'1', 2);
INSERT INTO `dictionaryitem` VALUES (3, '2021-06-28 18:01:55', '2021-07-20 12:36:06', 10, '老张', b'1', 1);
INSERT INTO `dictionaryitem` VALUES (4, '2021-07-19 14:32:27', '2021-07-20 12:35:20', 4, '23123', b'1', 1);

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdDate` datetime NULL DEFAULT NULL,
  `lastModifiedDate` datetime NULL DEFAULT NULL,
  `version` int(11) NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `realName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phoneNumber` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `locked` bit(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username_uindex`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES (1, '2021-06-25 16:10:45', '2021-06-25 18:28:27', 19, 'admin', '超级管理员', '1', NULL, b'0');
INSERT INTO `employee` VALUES (2, '2021-06-25 17:13:17', '2021-06-26 09:40:39', 12, 'Eden', '甘乐', NULL, '18723456755', b'1');

-- ----------------------------
-- Table structure for employeerole
-- ----------------------------
DROP TABLE IF EXISTS `employeerole`;
CREATE TABLE `employeerole`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdDate` datetime NULL DEFAULT NULL,
  `lastModifiedDate` datetime NULL DEFAULT NULL,
  `version` int(11) NULL DEFAULT NULL,
  `employeeId` bigint(20) NULL DEFAULT NULL,
  `roleId` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `employeeId_roleId_uindex`(`employeeId`, `roleId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of employeerole
-- ----------------------------
INSERT INTO `employeerole` VALUES (1, NULL, NULL, 0, 1, 1);

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdDate` datetime NULL DEFAULT NULL,
  `lastModifiedDate` datetime NULL DEFAULT NULL,
  `version` int(11) NULL DEFAULT NULL,
  `orderNo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单编号',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '订单金额',
  `totalNum` int(11) NULL DEFAULT NULL COMMENT '总次数',
  `appointNum` int(11) NULL DEFAULT NULL COMMENT '已预约次数',
  `boughtTime` datetime NULL DEFAULT NULL COMMENT '购买时间',
  `payStatus` tinyint(4) NULL DEFAULT NULL COMMENT '支付状态 0-未支付 1-已支付',
  `appointStatus` tinyint(4) NULL DEFAULT NULL COMMENT '预约状态 0-未预约 1-已预约',
  `userId` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  `productId` bigint(20) NULL DEFAULT NULL COMMENT '产品id',
  `shopId` bigint(20) NULL DEFAULT NULL COMMENT '门店id',
  `dicItemId` bigint(20) NULL DEFAULT NULL COMMENT '字典详情id',
  `isRedeemOrder` bit(1) NULL DEFAULT NULL COMMENT '是否为兑换码订单',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (1, '2021-07-19 12:03:48', '2021-07-19 12:05:48', 4, '866651536095182848', 12.00, 1, 1, '2021-07-19 12:03:53', 1, 0, 1, 3, 1, NULL, b'0');
INSERT INTO `orders` VALUES (2, '2021-07-19 12:06:40', '2021-07-19 12:08:48', 4, '866652260375986176', 12.00, 1, 1, '2021-07-19 12:06:46', 1, 0, 1, 3, 1, NULL, b'0');
INSERT INTO `orders` VALUES (3, '2021-07-19 12:10:26', '2021-07-19 14:01:31', 16, '866653204136329216', 12.00, 5, 5, '2021-07-19 12:10:31', 1, 0, 1, 2, 1, NULL, b'0');
INSERT INTO `orders` VALUES (4, '2021-07-19 14:23:04', '2021-07-19 14:31:12', 4, '866686584588599296', 15.00, 1, 1, '2021-07-19 14:23:18', 1, 0, 1, 4, 3, NULL, b'0');
INSERT INTO `orders` VALUES (5, '2021-07-19 14:34:58', '2021-07-20 23:13:02', 3, '866689577614573568', 15.00, 1, 1, '2021-07-19 14:34:58', 1, 0, 1, 4, 1, NULL, b'1');
INSERT INTO `orders` VALUES (6, '2021-07-20 22:32:51', '2021-07-20 23:14:00', 2, '867172231518617600', 15.00, 1, 1, '2021-07-20 22:32:51', 1, 1, 1, 4, 2, NULL, b'1');

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdDate` datetime NULL DEFAULT NULL,
  `lastModifiedDate` datetime NULL DEFAULT NULL,
  `version` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name_uindex`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (1, '2021-06-25 16:10:45', NULL, 0, '*', '全部权限');
INSERT INTO `permission` VALUES (2, '2021-06-25 16:10:45', NULL, 0, 'message.sms.list', '短信消息-列表');
INSERT INTO `permission` VALUES (3, '2021-06-25 16:10:45', NULL, 0, 'message.smsVerification.list', '验证短信-列表');
INSERT INTO `permission` VALUES (4, '2021-06-25 16:10:45', NULL, 0, 'platform.employee.add', '员工-添加');
INSERT INTO `permission` VALUES (5, '2021-06-25 16:10:45', NULL, 0, 'platform.employee.list', '员工-列表');
INSERT INTO `permission` VALUES (6, '2021-06-25 16:10:45', NULL, 0, 'platform.employee.get', '员工-获取');
INSERT INTO `permission` VALUES (7, '2021-06-25 16:10:45', NULL, 0, 'platform.employee.assignRolesOptions', '员工-获取分配角色选项');
INSERT INTO `permission` VALUES (8, '2021-06-25 16:10:45', NULL, 0, 'platform.employee.edit', '员工-编辑');
INSERT INTO `permission` VALUES (9, '2021-06-25 16:10:45', NULL, 0, 'platform.employee.assignRoles', '员工-分配角色');
INSERT INTO `permission` VALUES (10, '2021-06-25 16:10:45', NULL, 0, 'platform.employee.resetPassword', '员工-重置密码');
INSERT INTO `permission` VALUES (11, '2021-06-25 16:10:45', NULL, 0, 'platform.permission.add', '权限-添加');
INSERT INTO `permission` VALUES (12, '2021-06-25 16:10:45', NULL, 0, 'platform.permission.list', '权限-列表');
INSERT INTO `permission` VALUES (13, '2021-06-25 16:10:45', NULL, 0, 'platform.permission.get', '权限-获取');
INSERT INTO `permission` VALUES (14, '2021-06-25 16:10:45', NULL, 0, 'platform.permission.edit', '权限-修改');
INSERT INTO `permission` VALUES (15, '2021-06-25 16:10:45', NULL, 0, 'platform.role.add', '角色-添加');
INSERT INTO `permission` VALUES (16, '2021-06-25 16:10:45', NULL, 0, 'platform.role.list', '角色-列表');
INSERT INTO `permission` VALUES (17, '2021-06-25 16:10:45', NULL, 0, 'platform.role.get', '角色-获取');
INSERT INTO `permission` VALUES (18, '2021-06-25 16:10:45', NULL, 0, 'platform.role.assignPermissionsOptions', '角色-获取分配权限选项');
INSERT INTO `permission` VALUES (19, '2021-06-25 16:10:45', NULL, 0, 'platform.role.assignPermissions', '角色-分配权限');
INSERT INTO `permission` VALUES (20, '2021-06-25 16:10:45', NULL, 0, 'platform.role.edit', '角色-修改');
INSERT INTO `permission` VALUES (21, '2021-06-25 16:10:45', NULL, 0, 'user.user.add', '用户-添加');
INSERT INTO `permission` VALUES (22, '2021-06-25 16:10:45', NULL, 0, 'user.user.get', '用户-获取');
INSERT INTO `permission` VALUES (23, '2021-06-25 16:10:45', NULL, 0, 'user.user.list', '用户-列表');
INSERT INTO `permission` VALUES (24, '2021-06-25 16:10:45', NULL, 0, 'user.user.edit', '用户-修改');
INSERT INTO `permission` VALUES (25, '2021-06-28 10:14:11', '2021-06-28 10:15:48', 1, 'backstage.dictionary.list', '字典-列表');
INSERT INTO `permission` VALUES (26, '2021-06-28 10:14:53', '2021-06-28 10:15:36', 1, 'backstage.dictionaryitem.list', '字典详情-列表');

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdDate` datetime NULL DEFAULT NULL,
  `lastModifiedDate` datetime NULL DEFAULT NULL,
  `version` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品名称',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '产品价格',
  `intro` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品简介',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品描述',
  `type` tinyint(4) NULL DEFAULT NULL COMMENT '产品类型 0-线上 1-线下',
  `userType` tinyint(4) NULL DEFAULT NULL COMMENT '用户类型 0-个人 1-团体',
  `peopleNum` int(11) NULL DEFAULT NULL COMMENT '包含人数',
  `totalAppointNum` int(11) NULL DEFAULT NULL COMMENT '包含次数',
  `iconPath` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '列表图片',
  `state` tinyint(4) NULL DEFAULT NULL COMMENT '状态 0-下架 1-销售中 2-待发布',
  `sales` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '销量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES (1, '2021-06-29 17:49:49', '2021-07-17 15:51:47', 4, '产品1', 12.00, '产品1简介', '<p>产品1描述</p>', 0, 0, 1, 2, '/api/file/producticon/4b0e4e13-0c88-4c83-adb7-8ffffc794edd.png', 1, 2);
INSERT INTO `product` VALUES (2, '2021-06-29 18:21:24', '2021-07-20 11:19:28', 9, '产品2', 12.00, '产品2简介', '<p>产品2描述</p>', 1, 0, 1, 5, '/api/file/producticon/7735e66a-2527-4ba2-84b8-0e19557f27d7.png', 1, 3);
INSERT INTO `product` VALUES (3, '2021-06-29 18:26:39', '2021-07-20 11:11:07', 26, '产品3', 12.00, '产品3简介', '<p>产品3描述</p>', 1, 1, 3, 1, '/api/file/producticon/d7ce5a5b-1aa4-407b-bc5b-29a5f46874a1.png', 0, 0);
INSERT INTO `product` VALUES (4, '2021-07-19 14:19:54', '2021-07-20 11:19:21', 37, '西瓜', 15.00, '西瓜西瓜西瓜', '<p>而非感到十分士大夫的</p><p>而无法士大夫微软微软为地方士大夫</p>', 1, 0, 1, 1, '/api/file/producticon/4cabf577-53d9-45c8-8e78-bcb816aa9725.png', 1, 1);
INSERT INTO `product` VALUES (5, '2021-07-19 18:25:58', '2021-07-20 15:21:36', 7, '瓜子', 123.00, '瓜子瓜子瓜子', '<p>发射点发生</p>', 0, 0, 1, 123, '/api/file/producticon/96f1da08-99da-41d4-906a-d843f81a279c.png', 1, 0);

-- ----------------------------
-- Table structure for productdetailpath
-- ----------------------------
DROP TABLE IF EXISTS `productdetailpath`;
CREATE TABLE `productdetailpath`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdDate` datetime NULL DEFAULT NULL,
  `lastModifiedDate` datetime NULL DEFAULT NULL,
  `version` int(11) NULL DEFAULT NULL,
  `detailPath` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '详情主图路径',
  `productId` bigint(20) NULL DEFAULT NULL COMMENT '产品id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 51 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of productdetailpath
-- ----------------------------
INSERT INTO `productdetailpath` VALUES (32, '2021-07-15 12:41:19', NULL, 0, '/api/file/productdetail/c2919a99-d87d-442c-8f25-79a36852fd80.png', 1);
INSERT INTO `productdetailpath` VALUES (33, '2021-07-15 13:45:10', NULL, 0, '/api/file/productdetail/681a0fd1-a8fe-4c39-b951-4bb579ada735.png', 2);
INSERT INTO `productdetailpath` VALUES (34, '2021-07-15 13:45:10', NULL, 0, '/api/file/productdetail/1aff5ee9-84d8-45eb-96d2-9689ce726373.png', 2);
INSERT INTO `productdetailpath` VALUES (35, '2021-07-19 14:13:22', NULL, 0, '/api/file/productdetail/d1bfa60a-22a5-44c7-96b3-eb6945465521.png', 3);
INSERT INTO `productdetailpath` VALUES (37, '2021-07-19 14:21:02', NULL, 0, '/api/file/productdetail/7ecb3a0e-5162-4828-907e-37e0fdcddab2.png', 4);
INSERT INTO `productdetailpath` VALUES (48, '2021-07-20 09:55:36', NULL, 0, '/api/file/productdetail/762447b7-a1b9-4447-a133-310df5a5a845.png', 5);
INSERT INTO `productdetailpath` VALUES (49, '2021-07-20 09:55:36', NULL, 0, '/api/file/productdetail/a4846696-411b-4549-860d-0f4bf77a135b.png', 5);
INSERT INTO `productdetailpath` VALUES (50, '2021-07-20 09:55:36', NULL, 0, '/api/file/productdetail/232e6dac-c5a4-41bd-9345-722f0a23022a.png', 5);

-- ----------------------------
-- Table structure for redeemcode
-- ----------------------------
DROP TABLE IF EXISTS `redeemcode`;
CREATE TABLE `redeemcode`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdDate` datetime NULL DEFAULT NULL,
  `lastModifiedDate` datetime NULL DEFAULT NULL,
  `version` int(11) NULL DEFAULT NULL,
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '兑换码',
  `bound` bit(1) NULL DEFAULT NULL COMMENT '是否已绑定',
  `used` bit(1) NULL DEFAULT NULL COMMENT '是否已使用',
  `userId` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  `productId` bigint(20) NULL DEFAULT NULL COMMENT '产品id',
  `shopId` bigint(20) NULL DEFAULT NULL COMMENT '门店id',
  `orderId` bigint(20) NULL DEFAULT NULL COMMENT '订单id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of redeemcode
-- ----------------------------
INSERT INTO `redeemcode` VALUES (1, '2021-07-17 14:14:07', '2021-07-17 14:26:38', 2, 'M223BA', b'1', b'1', 1, 3, 1, 1);
INSERT INTO `redeemcode` VALUES (2, '2021-07-17 14:14:07', '2021-07-17 14:16:17', 2, '389C26', b'1', b'1', 1, 3, 1, 1);
INSERT INTO `redeemcode` VALUES (3, '2021-07-19 14:34:17', '2021-07-20 18:21:45', 2, 'E24925', b'1', b'1', 1, 4, 2, 5);
INSERT INTO `redeemcode` VALUES (4, '2021-07-19 14:34:17', '2021-07-20 23:14:00', 2, 'I9P436', b'1', b'1', 1, 4, 2, 6);
INSERT INTO `redeemcode` VALUES (5, '2021-07-20 22:38:00', NULL, 0, 'Cjj419', b'0', b'0', NULL, 2, 3, NULL);
INSERT INTO `redeemcode` VALUES (6, '2021-07-20 22:38:00', NULL, 0, 'coRy4i', b'0', b'0', NULL, 2, 3, NULL);

-- ----------------------------
-- Table structure for registereduser
-- ----------------------------
DROP TABLE IF EXISTS `registereduser`;
CREATE TABLE `registereduser`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `createdDate` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `lastModifiedDate` datetime NULL DEFAULT NULL COMMENT '最后一次修改时间',
  `version` int(11) NULL DEFAULT NULL COMMENT '版本',
  `realName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `age` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `gender` tinyint(4) NULL DEFAULT NULL COMMENT '性别',
  `phoneNumber` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `locked` bit(1) NULL DEFAULT NULL COMMENT '是否锁定 0-未锁定 1-已锁定',
  `openid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信用户唯一id',
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信用户昵称',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像地址',
  `state` tinyint(4) NULL DEFAULT NULL COMMENT '模板消息状态 0-拒绝 1-接收',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of registereduser
-- ----------------------------
INSERT INTO `registereduser` VALUES (1, '2021-07-15 17:21:14', '2021-07-19 14:36:00', 73, '甘乐', NULL, 1, '15228943505', NULL, 'o94oc5gI4p4hcPLDBNtflB_w1jKQ', '甘乐', 'https://thirdwx.qlogo.cn/mmopen/vi_32/TMAQqcP5mIFIniagwQ4gxECWiapxvEbibwEQiazIicXpCDiaSGib85NJORIX5vH1we8SufjPOTu9DGicphibrLVhBY8CnibQ/132', NULL);
INSERT INTO `registereduser` VALUES (2, '2021-07-15 17:22:21', '2021-07-15 17:23:24', 5, 'aaa', NULL, 1, '12345687455', NULL, 'o94oc5t6JomH250NBsoevYEpfsRk', 'oqs', 'https://thirdwx.qlogo.cn/mmopen/vi_32/dVicHeJ08Objibv6FWXUMfLjKXpoWteOpAlAz2jNkIp3xbcTIiaGBoNeu1LdhuORZbGb7t3peT4xjZcj1LsZsgN9Q/132', NULL);

-- ----------------------------
-- Table structure for report
-- ----------------------------
DROP TABLE IF EXISTS `report`;
CREATE TABLE `report`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdDate` datetime NULL DEFAULT NULL,
  `lastModifiedDate` datetime NULL DEFAULT NULL,
  `version` int(11) NULL DEFAULT NULL,
  `reportNo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报告编号',
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报告路径',
  `sync` bit(1) NULL DEFAULT NULL COMMENT '是否同步',
  `userId` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  `appointId` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '预约id',
  `productId` bigint(20) NULL DEFAULT NULL COMMENT '产品id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of report
-- ----------------------------
INSERT INTO `report` VALUES (1, '2021-07-19 12:05:20', '2021-07-19 12:05:43', 1, '866651924181549056', '/api/file/report/5d25ebbd-b8b7-410d-a897-7f289a628200.pdf', b'1', 1, 1, 3);
INSERT INTO `report` VALUES (2, '2021-07-19 12:07:23', '2021-07-19 12:07:30', 1, '866652438252224512', '/api/file/report/b0c70b43-7147-4843-a431-fc24b195020c.pdf', b'1', 1, 2, 3);
INSERT INTO `report` VALUES (3, '2021-07-19 12:07:38', '2021-07-19 12:07:42', 1, '866652502299246592', '/api/file/report/be04588b-26b4-4087-aade-8b3fe2ffeb4f.pdf', b'1', 1, 2, 3);
INSERT INTO `report` VALUES (4, '2021-07-19 12:08:08', '2021-07-19 12:08:48', 1, '866652627671187456', '/api/file/report/67a31269-14a9-44d2-9bba-f1df161ec26b.pdf', b'1', 1, 2, 3);
INSERT INTO `report` VALUES (5, '2021-07-19 12:13:01', '2021-07-19 12:13:15', 1, '866653855784370176', '/api/file/report/cdf8755f-f4bc-4bf6-b381-b74ac9c084b4.pdf', b'1', 1, 3, 2);
INSERT INTO `report` VALUES (6, '2021-07-19 13:59:49', '2021-07-19 14:00:05', 1, '866680732347531264', '/api/file/report/79d3c983-3d19-499c-89d6-7cef2a929d0d.pdf', b'1', 1, 4, 2);
INSERT INTO `report` VALUES (7, '2021-07-19 14:00:32', '2021-07-19 14:00:37', 1, '866680912392224768', '/api/file/report/26306957-30a3-459a-8988-6eff56a62c24.pdf', b'1', 1, 5, 2);
INSERT INTO `report` VALUES (8, '2021-07-19 14:01:01', '2021-07-19 14:01:04', 1, '866681037097271296', '/api/file/report/ec83a925-04d4-4a2e-9300-41f09cce39bd.pdf', b'1', 1, 6, 2);
INSERT INTO `report` VALUES (9, '2021-07-19 14:01:20', '2021-07-19 14:01:31', 1, '866681116730327040', '/api/file/report/65e99c8a-1c36-4b65-9b01-16151f6669a4.pdf', b'1', 1, 7, 2);
INSERT INTO `report` VALUES (10, '2021-07-19 14:28:59', '2021-07-19 14:31:12', 1, '866688071855243264', '/api/file/report/9bcc77ce-529b-4975-8816-b21fb630567b.pdf', b'1', 1, 8, 4);
INSERT INTO `report` VALUES (11, '2021-07-20 23:12:50', '2021-07-20 23:13:02', 1, '867182290428493824', '/api/file/report/4b7f27b4-4b31-44d4-8097-c957b9856873.pdf', b'1', 1, 9, 4);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdDate` datetime NULL DEFAULT NULL,
  `lastModifiedDate` datetime NULL DEFAULT NULL,
  `version` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '2021-06-25 16:10:45', NULL, 0, '超级管理员', '拥有全部权限');

-- ----------------------------
-- Table structure for rolepermission
-- ----------------------------
DROP TABLE IF EXISTS `rolepermission`;
CREATE TABLE `rolepermission`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdDate` datetime NULL DEFAULT NULL,
  `lastModifiedDate` datetime NULL DEFAULT NULL,
  `version` int(11) NULL DEFAULT NULL,
  `roleId` bigint(20) NULL DEFAULT NULL,
  `permissionId` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `roleId_permissionId_uindex`(`roleId`, `permissionId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of rolepermission
-- ----------------------------
INSERT INTO `rolepermission` VALUES (1, NULL, NULL, 0, 1, 1);

-- ----------------------------
-- Table structure for shop
-- ----------------------------
DROP TABLE IF EXISTS `shop`;
CREATE TABLE `shop`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdDate` datetime NULL DEFAULT NULL,
  `lastModifiedDate` datetime NULL DEFAULT NULL,
  `version` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '门店名称',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '门店地址',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '门店手机',
  `validNum` int(11) NULL DEFAULT NULL COMMENT '可预约次数',
  `appointNum` int(11) NULL DEFAULT NULL COMMENT '已预约次数',
  `enabled` bit(1) NULL DEFAULT NULL COMMENT '是否启用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of shop
-- ----------------------------
INSERT INTO `shop` VALUES (1, '2021-06-30 11:55:04', '2021-07-20 18:21:46', 36, '门店1', '地址1', '31564654654', 416, 19, b'1');
INSERT INTO `shop` VALUES (2, '2021-07-02 11:57:35', '2021-07-20 23:14:00', 8, '门店2', '地址2', '65456465', 500, 5, b'1');
INSERT INTO `shop` VALUES (3, '2021-07-19 14:14:40', '2021-07-20 11:43:25', 14, '成都', '打发打发', '15228943505', 11, 0, b'0');

-- ----------------------------
-- Table structure for smsmessage
-- ----------------------------
DROP TABLE IF EXISTS `smsmessage`;
CREATE TABLE `smsmessage`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdDate` datetime NULL DEFAULT NULL,
  `lastModifiedDate` datetime NULL DEFAULT NULL,
  `version` int(11) NULL DEFAULT NULL,
  `phoneNumber` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `templateId` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `closeDate` datetime NULL DEFAULT NULL,
  `note` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `state` tinyint(4) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `phoneNumber_index`(`phoneNumber`) USING BTREE,
  INDEX `state_index`(`state`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 117 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of smsmessage
-- ----------------------------
INSERT INTO `smsmessage` VALUES (1, '2021-07-05 17:23:28', NULL, 0, '15262626252', 'SMS_60680199', '{\"code\":\"3870\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (2, '2021-07-05 17:26:35', NULL, 0, '15282828281', 'SMS_60680199', '{\"code\":\"7890\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (3, '2021-07-05 17:35:40', NULL, 0, '15242342342', 'SMS_60680199', '{\"code\":\"2391\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (4, '2021-07-05 17:37:43', NULL, 0, '15258643373', 'SMS_60680199', '{\"code\":\"5366\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (5, '2021-07-05 17:50:08', NULL, 0, '13600000000', 'SMS_60680199', '{\"code\":\"2966\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (6, '2021-07-05 17:55:45', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"3164\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (7, '2021-07-05 17:58:38', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"0707\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (8, '2021-07-05 18:04:13', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"8625\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (9, '2021-07-05 18:09:36', NULL, 0, '15272722262', 'SMS_60680199', '{\"code\":\"6436\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (10, '2021-07-05 18:11:21', NULL, 0, '15272722262', 'SMS_60680199', '{\"code\":\"4115\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (11, '2021-07-05 18:15:05', NULL, 0, '15285959595', 'SMS_60680199', '{\"code\":\"9531\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (12, '2021-07-05 18:16:21', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"0602\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (13, '2021-07-05 18:17:32', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"0019\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (14, '2021-07-05 18:19:06', NULL, 0, '13600000000', 'SMS_60680199', '{\"code\":\"8197\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (15, '2021-07-05 18:21:34', NULL, 0, '13600000000', 'SMS_60680199', '{\"code\":\"007569\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (16, '2021-07-06 09:51:44', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"009282\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (17, '2021-07-06 09:54:05', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"005114\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (18, '2021-07-06 09:54:59', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"001870\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (19, '2021-07-06 11:40:19', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"004836\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (20, '2021-07-06 11:43:40', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"004735\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (21, '2021-07-06 12:08:21', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"006927\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (22, '2021-07-06 14:44:01', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"008690\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (23, '2021-07-06 15:31:46', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"002226\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (24, '2021-07-06 15:32:51', NULL, 0, '15228943805', 'SMS_60680199', '{\"code\":\"007486\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (25, '2021-07-06 15:56:53', NULL, 0, '15228943805', 'SMS_60680199', '{\"code\":\"007772\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (26, '2021-07-06 15:58:12', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"009816\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (27, '2021-07-06 16:22:47', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"009493\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (28, '2021-07-06 16:29:38', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"004766\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (29, '2021-07-06 16:31:04', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"005230\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (30, '2021-07-06 17:07:57', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"002216\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (31, '2021-07-06 17:51:48', NULL, 0, '15228943503', 'SMS_60680199', '{\"code\":\"088660\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (32, '2021-07-06 17:56:51', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"892071\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (33, '2021-07-06 18:06:38', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"669829\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (34, '2021-07-07 10:03:38', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"354662\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (35, '2021-07-07 10:14:39', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"413331\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (36, '2021-07-07 11:37:31', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"216400\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (37, '2021-07-07 11:38:59', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"745602\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (38, '2021-07-07 11:55:04', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"170695\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (39, '2021-07-07 11:59:19', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"336033\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (40, '2021-07-07 12:23:56', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"189785\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (41, '2021-07-07 12:25:46', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"859760\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (42, '2021-07-07 12:35:47', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"003208\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (43, '2021-07-07 12:51:57', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"512259\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (44, '2021-07-07 13:34:59', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"578804\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (45, '2021-07-07 15:55:24', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"750154\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (46, '2021-07-07 15:56:53', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"637146\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (47, '2021-07-07 16:38:25', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"555718\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (48, '2021-07-07 17:11:26', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"880850\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (49, '2021-07-08 09:47:03', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"272548\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (50, '2021-07-08 11:07:26', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"279549\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (51, '2021-07-08 11:22:29', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"251329\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (52, '2021-07-08 12:21:16', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"778711\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (53, '2021-07-08 13:31:41', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"147481\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (54, '2021-07-08 14:32:54', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"634592\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (55, '2021-07-08 15:30:54', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"262132\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (56, '2021-07-08 16:11:09', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"464315\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (57, '2021-07-08 16:51:12', NULL, 0, '13600000000', 'SMS_60680199', '{\"code\":\"619106\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (58, '2021-07-08 18:04:41', NULL, 0, '13600000000', 'SMS_60680199', '{\"code\":\"732618\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (59, '2021-07-08 18:07:35', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"010568\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (60, '2021-07-08 18:29:09', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"243818\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (61, '2021-07-09 09:33:50', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"871754\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (62, '2021-07-09 09:38:19', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"490539\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (63, '2021-07-09 09:40:38', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"666743\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (64, '2021-07-11 17:23:33', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"082082\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (65, '2021-07-12 13:48:46', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"531648\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (66, '2021-07-13 11:41:38', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"198479\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (67, '2021-07-13 11:48:23', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"826531\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (68, '2021-07-13 12:05:43', NULL, 0, '13600000000', 'SMS_60680199', '{\"code\":\"088305\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (69, '2021-07-13 16:05:20', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"309893\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (70, '2021-07-13 16:09:18', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"026688\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (71, '2021-07-13 16:12:15', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"806806\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (72, '2021-07-13 16:48:28', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"824419\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (73, '2021-07-13 17:12:26', NULL, 0, '13600000000', 'SMS_60680199', '{\"code\":\"501912\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (74, '2021-07-13 17:17:58', NULL, 0, '13600000000', 'SMS_60680199', '{\"code\":\"327181\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (75, '2021-07-13 17:21:31', NULL, 0, '13600000000', 'SMS_60680199', '{\"code\":\"016008\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (76, '2021-07-13 17:23:25', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"131543\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (77, '2021-07-13 17:24:09', NULL, 0, '13600000000', 'SMS_60680199', '{\"code\":\"763227\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (78, '2021-07-13 17:28:20', NULL, 0, '13600000000', 'SMS_60680199', '{\"code\":\"113242\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (79, '2021-07-13 17:31:27', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"159383\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (80, '2021-07-13 17:37:37', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"470422\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (81, '2021-07-13 17:46:46', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"949074\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (82, '2021-07-13 17:49:31', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"102534\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (83, '2021-07-13 17:50:48', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"543783\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (84, '2021-07-13 17:52:01', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"043782\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (85, '2021-07-13 17:52:54', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"216287\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (86, '2021-07-13 17:54:14', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"441329\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (87, '2021-07-13 17:59:07', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"567007\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (88, '2021-07-13 17:59:47', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"897223\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (89, '2021-07-13 18:00:20', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"155321\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (90, '2021-07-13 18:02:46', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"193721\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (91, '2021-07-13 18:03:30', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"221385\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (92, '2021-07-13 18:06:36', NULL, 0, '16582766666', 'SMS_60680199', '{\"code\":\"264954\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (93, '2021-07-13 18:07:10', NULL, 0, '17628374949', 'SMS_60680199', '{\"code\":\"306023\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (94, '2021-07-13 18:09:26', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"961124\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (95, '2021-07-13 18:09:57', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"996196\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (96, '2021-07-13 18:10:35', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"205247\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (97, '2021-07-13 18:16:27', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"218462\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (98, '2021-07-13 18:16:58', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"961640\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (99, '2021-07-13 18:32:35', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"916833\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (100, '2021-07-13 18:37:02', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"381313\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (101, '2021-07-13 18:44:54', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"978126\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (102, '2021-07-13 18:45:38', NULL, 0, '16666666666', 'SMS_60680199', '{\"code\":\"410724\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (103, '2021-07-13 18:46:27', NULL, 0, '16666666666', 'SMS_60680199', '{\"code\":\"538935\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (104, '2021-07-13 18:47:35', NULL, 0, '18888888888', 'SMS_60680199', '{\"code\":\"553146\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (105, '2021-07-13 18:48:16', NULL, 0, '12234542121', 'SMS_60680199', '{\"code\":\"550613\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (106, '2021-07-13 18:49:03', NULL, 0, '12234542121', 'SMS_60680199', '{\"code\":\"214396\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (107, '2021-07-14 17:24:59', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"792623\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (108, '2021-07-15 17:54:36', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"339968\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (109, '2021-07-15 18:11:09', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"070215\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (110, '2021-07-15 18:14:50', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"162172\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (111, '2021-07-15 18:16:38', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"799335\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (112, '2021-07-15 18:19:37', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"441589\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (113, '2021-07-15 22:09:21', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"187138\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (114, '2021-07-16 09:29:45', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"245380\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (115, '2021-07-17 14:54:10', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"392625\"}', NULL, NULL, 0);
INSERT INTO `smsmessage` VALUES (116, '2021-07-17 14:55:48', NULL, 0, '15228943505', 'SMS_60680199', '{\"code\":\"660829\"}', NULL, NULL, 0);

-- ----------------------------
-- Table structure for smsverification
-- ----------------------------
DROP TABLE IF EXISTS `smsverification`;
CREATE TABLE `smsverification`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdDate` datetime NULL DEFAULT NULL,
  `lastModifiedDate` datetime NULL DEFAULT NULL,
  `version` int(11) NULL DEFAULT NULL,
  `smsMessageId` bigint(20) NULL DEFAULT NULL,
  `phoneNumber` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `requestIp` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `retryCount` int(11) NULL DEFAULT NULL,
  `expirationDate` datetime NULL DEFAULT NULL,
  `state` tinyint(4) NULL DEFAULT NULL,
  `type` tinyint(4) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `smsMessageId_index`(`smsMessageId`) USING BTREE,
  INDEX `phoneNumber_index`(`phoneNumber`) USING BTREE,
  INDEX `requestIp_index`(`requestIp`) USING BTREE,
  INDEX `state_index`(`state`) USING BTREE,
  INDEX `type_index`(`type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 117 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of smsverification
-- ----------------------------
INSERT INTO `smsverification` VALUES (1, '2021-07-05 17:23:28', NULL, 0, 1, '15262626252', '3870', '127.0.0.1', 0, '2021-07-05 17:28:16', 0, 0);
INSERT INTO `smsverification` VALUES (2, '2021-07-05 17:26:35', NULL, 0, 2, '15282828281', '7890', '127.0.0.1', 0, '2021-07-05 17:31:35', 0, 0);
INSERT INTO `smsverification` VALUES (3, '2021-07-05 17:35:40', NULL, 0, 3, '15242342342', '2391', '127.0.0.1', 0, '2021-07-05 17:40:40', 0, 0);
INSERT INTO `smsverification` VALUES (4, '2021-07-05 17:37:43', NULL, 0, 4, '15258643373', '5366', '127.0.0.1', 0, '2021-07-05 17:42:43', 0, 0);
INSERT INTO `smsverification` VALUES (5, '2021-07-05 17:50:08', '2021-07-05 18:19:06', 1, 5, '13600000000', '2966', '127.0.0.1', 0, '2021-07-05 17:55:08', 2, 0);
INSERT INTO `smsverification` VALUES (6, '2021-07-05 17:55:45', '2021-07-05 17:58:37', 1, 6, '15228943505', '3164', '127.0.0.1', 0, '2021-07-05 18:00:45', 2, 0);
INSERT INTO `smsverification` VALUES (7, '2021-07-05 17:58:38', '2021-07-05 18:04:13', 1, 7, '15228943505', '0707', '127.0.0.1', 0, '2021-07-05 18:03:38', 2, 0);
INSERT INTO `smsverification` VALUES (8, '2021-07-05 18:04:13', '2021-07-05 18:16:21', 1, 8, '15228943505', '8625', '127.0.0.1', 0, '2021-07-05 18:09:13', 2, 0);
INSERT INTO `smsverification` VALUES (9, '2021-07-05 18:09:36', '2021-07-05 18:11:21', 1, 9, '15272722262', '6436', '127.0.0.1', 0, '2021-07-05 18:14:36', 2, 0);
INSERT INTO `smsverification` VALUES (10, '2021-07-05 18:11:21', NULL, 0, 10, '15272722262', '4115', '127.0.0.1', 0, '2021-07-05 18:16:21', 0, 0);
INSERT INTO `smsverification` VALUES (11, '2021-07-05 18:15:05', NULL, 0, 11, '15285959595', '9531', '127.0.0.1', 0, '2021-07-05 18:20:05', 0, 0);
INSERT INTO `smsverification` VALUES (12, '2021-07-05 18:16:21', '2021-07-05 18:17:31', 1, 12, '15228943505', '0602', '127.0.0.1', 0, '2021-07-05 18:21:21', 2, 0);
INSERT INTO `smsverification` VALUES (13, '2021-07-05 18:17:32', '2021-07-06 09:51:43', 1, 13, '15228943505', '0019', '127.0.0.1', 0, '2021-07-05 18:22:32', 2, 0);
INSERT INTO `smsverification` VALUES (14, '2021-07-05 18:19:06', '2021-07-05 18:21:33', 1, 14, '13600000000', '8197', '127.0.0.1', 0, '2021-07-05 18:24:06', 2, 0);
INSERT INTO `smsverification` VALUES (15, '2021-07-05 18:21:34', '2021-07-08 16:51:11', 1, 15, '13600000000', '007569', '127.0.0.1', 0, '2021-07-05 18:26:34', 2, 0);
INSERT INTO `smsverification` VALUES (16, '2021-07-06 09:51:44', '2021-07-06 09:54:04', 1, 16, '15228943505', '009282', '127.0.0.1', 0, '2021-07-06 09:56:44', 2, 0);
INSERT INTO `smsverification` VALUES (17, '2021-07-06 09:54:05', '2021-07-06 09:54:59', 1, 17, '15228943505', '005114', '127.0.0.1', 0, '2021-07-06 09:59:05', 2, 0);
INSERT INTO `smsverification` VALUES (18, '2021-07-06 09:54:59', '2021-07-06 11:40:18', 1, 18, '15228943505', '001870', '127.0.0.1', 0, '2021-07-06 09:59:59', 2, 0);
INSERT INTO `smsverification` VALUES (19, '2021-07-06 11:40:19', '2021-07-06 11:43:39', 1, 19, '15228943505', '004836', '127.0.0.1', 0, '2021-07-06 11:45:19', 2, 0);
INSERT INTO `smsverification` VALUES (20, '2021-07-06 11:43:40', '2021-07-06 12:08:20', 1, 20, '15228943505', '004735', '127.0.0.1', 0, '2021-07-06 11:48:40', 2, 0);
INSERT INTO `smsverification` VALUES (21, '2021-07-06 12:08:21', '2021-07-06 14:44:00', 1, 21, '15228943505', '006927', '127.0.0.1', 0, '2021-07-06 12:13:20', 2, 0);
INSERT INTO `smsverification` VALUES (22, '2021-07-06 14:44:01', '2021-07-06 15:31:45', 1, 22, '15228943505', '008690', '127.0.0.1', 0, '2021-07-06 14:49:01', 2, 0);
INSERT INTO `smsverification` VALUES (23, '2021-07-06 15:31:46', '2021-07-06 15:58:12', 1, 23, '15228943505', '002226', '127.0.0.1', 0, '2021-07-06 15:36:46', 2, 0);
INSERT INTO `smsverification` VALUES (24, '2021-07-06 15:32:51', '2021-07-06 15:56:53', 1, 24, '15228943805', '007486', '127.0.0.1', 0, '2021-07-06 15:37:51', 2, 0);
INSERT INTO `smsverification` VALUES (25, '2021-07-06 15:56:53', NULL, 0, 25, '15228943805', '007772', '127.0.0.1', 0, '2021-07-06 16:01:53', 0, 0);
INSERT INTO `smsverification` VALUES (26, '2021-07-06 15:58:12', '2021-07-06 16:22:46', 1, 26, '15228943505', '009816', '127.0.0.1', 0, '2021-07-06 16:03:12', 2, 0);
INSERT INTO `smsverification` VALUES (27, '2021-07-06 16:22:47', '2021-07-06 16:29:38', 1, 27, '15228943505', '009493', '127.0.0.1', 0, '2021-07-06 16:27:47', 2, 0);
INSERT INTO `smsverification` VALUES (28, '2021-07-06 16:29:38', '2021-07-06 16:31:04', 1, 28, '15228943505', '004766', '127.0.0.1', 0, '2021-07-06 16:34:38', 2, 0);
INSERT INTO `smsverification` VALUES (29, '2021-07-06 16:31:04', '2021-07-06 17:07:56', 1, 29, '15228943505', '005230', '127.0.0.1', 0, '2021-07-06 16:36:04', 2, 0);
INSERT INTO `smsverification` VALUES (30, '2021-07-06 17:07:57', '2021-07-06 17:56:50', 1, 30, '15228943505', '002216', '127.0.0.1', 0, '2021-07-06 17:12:57', 2, 0);
INSERT INTO `smsverification` VALUES (31, '2021-07-06 17:51:48', NULL, 0, 31, '15228943503', '088660', '127.0.0.1', 0, '2021-07-06 17:56:48', 0, 0);
INSERT INTO `smsverification` VALUES (32, '2021-07-06 17:56:51', '2021-07-06 18:06:37', 1, 32, '15228943505', '892071', '127.0.0.1', 0, '2021-07-06 18:01:50', 2, 0);
INSERT INTO `smsverification` VALUES (33, '2021-07-06 18:06:38', '2021-07-07 10:03:38', 1, 33, '15228943505', '669829', '127.0.0.1', 0, '2021-07-06 18:11:38', 2, 0);
INSERT INTO `smsverification` VALUES (34, '2021-07-07 10:03:38', '2021-07-07 10:14:38', 1, 34, '15228943505', '354662', '127.0.0.1', 0, '2021-07-07 10:08:38', 2, 0);
INSERT INTO `smsverification` VALUES (35, '2021-07-07 10:14:39', '2021-07-07 11:37:30', 1, 35, '15228943505', '413331', '127.0.0.1', 0, '2021-07-07 10:19:39', 2, 0);
INSERT INTO `smsverification` VALUES (36, '2021-07-07 11:37:31', '2021-07-07 11:38:59', 1, 36, '15228943505', '216400', '127.0.0.1', 0, '2021-07-07 11:42:30', 2, 0);
INSERT INTO `smsverification` VALUES (37, '2021-07-07 11:38:59', '2021-07-07 11:55:04', 1, 37, '15228943505', '745602', '127.0.0.1', 0, '2021-07-07 11:43:59', 2, 0);
INSERT INTO `smsverification` VALUES (38, '2021-07-07 11:55:04', '2021-07-07 11:59:19', 1, 38, '15228943505', '170695', '127.0.0.1', 0, '2021-07-07 12:00:04', 2, 0);
INSERT INTO `smsverification` VALUES (39, '2021-07-07 11:59:19', '2021-07-07 12:23:56', 1, 39, '15228943505', '336033', '127.0.0.1', 0, '2021-07-07 12:04:19', 2, 0);
INSERT INTO `smsverification` VALUES (40, '2021-07-07 12:23:56', '2021-07-07 12:25:46', 1, 40, '15228943505', '189785', '127.0.0.1', 0, '2021-07-07 12:28:56', 2, 0);
INSERT INTO `smsverification` VALUES (41, '2021-07-07 12:25:46', '2021-07-07 12:35:46', 1, 41, '15228943505', '859760', '127.0.0.1', 0, '2021-07-07 12:30:46', 2, 0);
INSERT INTO `smsverification` VALUES (42, '2021-07-07 12:35:47', '2021-07-07 12:51:56', 1, 42, '15228943505', '003208', '127.0.0.1', 0, '2021-07-07 12:40:47', 2, 0);
INSERT INTO `smsverification` VALUES (43, '2021-07-07 12:51:57', '2021-07-07 13:34:59', 1, 43, '15228943505', '512259', '127.0.0.1', 0, '2021-07-07 12:56:57', 2, 0);
INSERT INTO `smsverification` VALUES (44, '2021-07-07 13:34:59', '2021-07-07 15:55:23', 1, 44, '15228943505', '578804', '127.0.0.1', 0, '2021-07-07 13:39:59', 2, 0);
INSERT INTO `smsverification` VALUES (45, '2021-07-07 15:55:24', '2021-07-07 15:56:53', 1, 45, '15228943505', '750154', '127.0.0.1', 0, '2021-07-07 16:00:24', 2, 0);
INSERT INTO `smsverification` VALUES (46, '2021-07-07 15:56:53', '2021-07-07 16:38:24', 1, 46, '15228943505', '637146', '127.0.0.1', 0, '2021-07-07 16:01:53', 2, 0);
INSERT INTO `smsverification` VALUES (47, '2021-07-07 16:38:25', '2021-07-07 17:11:26', 1, 47, '15228943505', '555718', '127.0.0.1', 0, '2021-07-07 16:43:25', 2, 0);
INSERT INTO `smsverification` VALUES (48, '2021-07-07 17:11:26', '2021-07-08 09:47:02', 1, 48, '15228943505', '880850', '127.0.0.1', 0, '2021-07-07 17:16:26', 2, 0);
INSERT INTO `smsverification` VALUES (49, '2021-07-08 09:47:03', '2021-07-08 11:07:26', 1, 49, '15228943505', '272548', '127.0.0.1', 0, '2021-07-08 09:52:03', 2, 0);
INSERT INTO `smsverification` VALUES (50, '2021-07-08 11:07:26', '2021-07-08 11:22:29', 1, 50, '15228943505', '279549', '127.0.0.1', 0, '2021-07-08 11:12:26', 2, 0);
INSERT INTO `smsverification` VALUES (51, '2021-07-08 11:22:29', '2021-07-08 12:21:15', 1, 51, '15228943505', '251329', '127.0.0.1', 0, '2021-07-08 11:27:29', 2, 0);
INSERT INTO `smsverification` VALUES (52, '2021-07-08 12:21:16', '2021-07-08 13:31:41', 1, 52, '15228943505', '778711', '127.0.0.1', 0, '2021-07-08 12:26:16', 2, 0);
INSERT INTO `smsverification` VALUES (53, '2021-07-08 13:31:41', '2021-07-08 14:32:53', 1, 53, '15228943505', '147481', '127.0.0.1', 0, '2021-07-08 13:36:41', 2, 0);
INSERT INTO `smsverification` VALUES (54, '2021-07-08 14:32:54', '2021-07-08 15:30:53', 1, 54, '15228943505', '634592', '127.0.0.1', 0, '2021-07-08 14:37:53', 2, 0);
INSERT INTO `smsverification` VALUES (55, '2021-07-08 15:30:54', '2021-07-08 16:11:08', 1, 55, '15228943505', '262132', '127.0.0.1', 0, '2021-07-08 15:35:54', 2, 0);
INSERT INTO `smsverification` VALUES (56, '2021-07-08 16:11:09', '2021-07-08 18:07:35', 1, 56, '15228943505', '464315', '127.0.0.1', 0, '2021-07-08 16:16:09', 2, 0);
INSERT INTO `smsverification` VALUES (57, '2021-07-08 16:51:12', '2021-07-08 18:04:41', 1, 57, '13600000000', '619106', '127.0.0.1', 0, '2021-07-08 16:56:12', 2, 0);
INSERT INTO `smsverification` VALUES (58, '2021-07-08 18:04:41', '2021-07-13 12:05:43', 1, 58, '13600000000', '732618', '127.0.0.1', 0, '2021-07-08 18:09:41', 2, 0);
INSERT INTO `smsverification` VALUES (59, '2021-07-08 18:07:35', '2021-07-08 18:29:08', 1, 59, '15228943505', '010568', '127.0.0.1', 0, '2021-07-08 18:12:35', 2, 0);
INSERT INTO `smsverification` VALUES (60, '2021-07-08 18:29:09', '2021-07-09 09:33:49', 1, 60, '15228943505', '243818', '127.0.0.1', 0, '2021-07-08 18:34:09', 2, 0);
INSERT INTO `smsverification` VALUES (61, '2021-07-09 09:33:50', '2021-07-09 09:38:18', 1, 61, '15228943505', '871754', '127.0.0.1', 0, '2021-07-09 09:38:49', 2, 0);
INSERT INTO `smsverification` VALUES (62, '2021-07-09 09:38:19', '2021-07-09 09:40:37', 1, 62, '15228943505', '490539', '127.0.0.1', 0, '2021-07-09 09:43:19', 2, 0);
INSERT INTO `smsverification` VALUES (63, '2021-07-09 09:40:38', '2021-07-11 17:23:33', 1, 63, '15228943505', '666743', '127.0.0.1', 0, '2021-07-09 09:45:38', 2, 0);
INSERT INTO `smsverification` VALUES (64, '2021-07-11 17:23:33', '2021-07-12 13:48:45', 1, 64, '15228943505', '082082', '127.0.0.1', 0, '2021-07-11 17:28:33', 2, 0);
INSERT INTO `smsverification` VALUES (65, '2021-07-12 13:48:46', '2021-07-13 11:41:37', 1, 65, '15228943505', '531648', '127.0.0.1', 0, '2021-07-12 13:53:46', 2, 0);
INSERT INTO `smsverification` VALUES (66, '2021-07-13 11:41:38', '2021-07-13 11:48:22', 1, 66, '15228943505', '198479', '127.0.0.1', 0, '2021-07-13 11:46:37', 2, 0);
INSERT INTO `smsverification` VALUES (67, '2021-07-13 11:48:23', '2021-07-13 16:05:19', 1, 67, '15228943505', '826531', '127.0.0.1', 0, '2021-07-13 11:53:23', 2, 0);
INSERT INTO `smsverification` VALUES (68, '2021-07-13 12:05:43', '2021-07-13 17:12:26', 1, 68, '13600000000', '088305', '127.0.0.1', 0, '2021-07-13 12:10:43', 2, 0);
INSERT INTO `smsverification` VALUES (69, '2021-07-13 16:05:20', '2021-07-13 16:09:17', 1, 69, '15228943505', '309893', '127.0.0.1', 0, '2021-07-13 16:10:20', 2, 0);
INSERT INTO `smsverification` VALUES (70, '2021-07-13 16:09:18', '2021-07-13 16:12:15', 1, 70, '15228943505', '026688', '127.0.0.1', 0, '2021-07-13 16:14:18', 2, 0);
INSERT INTO `smsverification` VALUES (71, '2021-07-13 16:12:15', '2021-07-13 16:48:27', 1, 71, '15228943505', '806806', '127.0.0.1', 0, '2021-07-13 16:17:15', 2, 0);
INSERT INTO `smsverification` VALUES (72, '2021-07-13 16:48:28', '2021-07-13 17:23:25', 1, 72, '15228943505', '824419', '127.0.0.1', 0, '2021-07-13 16:53:28', 2, 0);
INSERT INTO `smsverification` VALUES (73, '2021-07-13 17:12:26', '2021-07-13 17:17:58', 1, 73, '13600000000', '501912', '127.0.0.1', 0, '2021-07-13 17:17:26', 2, 0);
INSERT INTO `smsverification` VALUES (74, '2021-07-13 17:17:58', '2021-07-13 17:21:31', 1, 74, '13600000000', '327181', '127.0.0.1', 0, '2021-07-13 17:22:58', 2, 0);
INSERT INTO `smsverification` VALUES (75, '2021-07-13 17:21:31', '2021-07-13 17:24:09', 1, 75, '13600000000', '016008', '127.0.0.1', 0, '2021-07-13 17:26:31', 2, 0);
INSERT INTO `smsverification` VALUES (76, '2021-07-13 17:23:25', '2021-07-13 17:31:27', 1, 76, '15228943505', '131543', '127.0.0.1', 0, '2021-07-13 17:28:25', 2, 0);
INSERT INTO `smsverification` VALUES (77, '2021-07-13 17:24:09', '2021-07-13 17:28:19', 1, 77, '13600000000', '763227', '127.0.0.1', 0, '2021-07-13 17:29:09', 2, 0);
INSERT INTO `smsverification` VALUES (78, '2021-07-13 17:28:20', NULL, 0, 78, '13600000000', '113242', '127.0.0.1', 0, '2021-07-13 17:33:20', 0, 0);
INSERT INTO `smsverification` VALUES (79, '2021-07-13 17:31:27', '2021-07-13 17:37:37', 1, 79, '15228943505', '159383', '127.0.0.1', 0, '2021-07-13 17:36:27', 2, 0);
INSERT INTO `smsverification` VALUES (80, '2021-07-13 17:37:37', '2021-07-13 17:46:45', 1, 80, '15228943505', '470422', '127.0.0.1', 0, '2021-07-13 17:42:37', 2, 0);
INSERT INTO `smsverification` VALUES (81, '2021-07-13 17:46:46', '2021-07-13 17:49:30', 1, 81, '15228943505', '949074', '127.0.0.1', 0, '2021-07-13 17:51:46', 2, 0);
INSERT INTO `smsverification` VALUES (82, '2021-07-13 17:49:31', '2021-07-13 17:50:47', 1, 82, '15228943505', '102534', '127.0.0.1', 0, '2021-07-13 17:54:31', 2, 0);
INSERT INTO `smsverification` VALUES (83, '2021-07-13 17:50:48', '2021-07-13 17:52:00', 1, 83, '15228943505', '543783', '127.0.0.1', 0, '2021-07-13 17:55:48', 2, 0);
INSERT INTO `smsverification` VALUES (84, '2021-07-13 17:52:01', '2021-07-13 17:52:54', 1, 84, '15228943505', '043782', '127.0.0.1', 0, '2021-07-13 17:57:01', 2, 0);
INSERT INTO `smsverification` VALUES (85, '2021-07-13 17:52:54', '2021-07-13 17:54:13', 1, 85, '15228943505', '216287', '127.0.0.1', 0, '2021-07-13 17:57:54', 2, 0);
INSERT INTO `smsverification` VALUES (86, '2021-07-13 17:54:14', '2021-07-13 17:59:07', 1, 86, '15228943505', '441329', '127.0.0.1', 0, '2021-07-13 17:59:14', 2, 0);
INSERT INTO `smsverification` VALUES (87, '2021-07-13 17:59:07', '2021-07-13 17:59:47', 1, 87, '15228943505', '567007', '127.0.0.1', 0, '2021-07-13 18:04:07', 2, 0);
INSERT INTO `smsverification` VALUES (88, '2021-07-13 17:59:47', '2021-07-13 18:00:19', 1, 88, '15228943505', '897223', '127.0.0.1', 0, '2021-07-13 18:04:47', 2, 0);
INSERT INTO `smsverification` VALUES (89, '2021-07-13 18:00:20', '2021-07-13 18:02:46', 1, 89, '15228943505', '155321', '127.0.0.1', 0, '2021-07-13 18:05:20', 2, 0);
INSERT INTO `smsverification` VALUES (90, '2021-07-13 18:02:46', '2021-07-13 18:03:29', 1, 90, '15228943505', '193721', '127.0.0.1', 0, '2021-07-13 18:07:46', 2, 0);
INSERT INTO `smsverification` VALUES (91, '2021-07-13 18:03:30', '2021-07-13 18:09:26', 1, 91, '15228943505', '221385', '127.0.0.1', 0, '2021-07-13 18:08:30', 2, 0);
INSERT INTO `smsverification` VALUES (92, '2021-07-13 18:06:36', NULL, 0, 92, '16582766666', '264954', '127.0.0.1', 0, '2021-07-13 18:11:36', 0, 0);
INSERT INTO `smsverification` VALUES (93, '2021-07-13 18:07:10', NULL, 0, 93, '17628374949', '306023', '127.0.0.1', 0, '2021-07-13 18:12:10', 0, 0);
INSERT INTO `smsverification` VALUES (94, '2021-07-13 18:09:26', '2021-07-13 18:09:56', 1, 94, '15228943505', '961124', '127.0.0.1', 0, '2021-07-13 18:14:26', 2, 0);
INSERT INTO `smsverification` VALUES (95, '2021-07-13 18:09:57', '2021-07-13 18:10:34', 1, 95, '15228943505', '996196', '127.0.0.1', 0, '2021-07-13 18:14:57', 2, 0);
INSERT INTO `smsverification` VALUES (96, '2021-07-13 18:10:35', '2021-07-13 18:16:26', 1, 96, '15228943505', '205247', '127.0.0.1', 0, '2021-07-13 18:15:35', 2, 0);
INSERT INTO `smsverification` VALUES (97, '2021-07-13 18:16:27', '2021-07-13 18:16:57', 1, 97, '15228943505', '218462', '127.0.0.1', 0, '2021-07-13 18:21:27', 2, 0);
INSERT INTO `smsverification` VALUES (98, '2021-07-13 18:16:58', '2021-07-13 18:32:35', 1, 98, '15228943505', '961640', '127.0.0.1', 0, '2021-07-13 18:21:58', 2, 0);
INSERT INTO `smsverification` VALUES (99, '2021-07-13 18:32:35', '2021-07-13 18:37:02', 1, 99, '15228943505', '916833', '127.0.0.1', 0, '2021-07-13 18:37:35', 2, 0);
INSERT INTO `smsverification` VALUES (100, '2021-07-13 18:37:02', '2021-07-13 18:44:54', 1, 100, '15228943505', '381313', '127.0.0.1', 0, '2021-07-13 18:42:02', 2, 0);
INSERT INTO `smsverification` VALUES (101, '2021-07-13 18:44:54', '2021-07-14 17:24:58', 1, 101, '15228943505', '978126', '127.0.0.1', 0, '2021-07-13 18:49:54', 2, 0);
INSERT INTO `smsverification` VALUES (102, '2021-07-13 18:45:38', '2021-07-13 18:46:27', 1, 102, '16666666666', '410724', '127.0.0.1', 0, '2021-07-13 18:50:38', 2, 0);
INSERT INTO `smsverification` VALUES (103, '2021-07-13 18:46:27', NULL, 0, 103, '16666666666', '538935', '127.0.0.1', 0, '2021-07-13 18:51:27', 0, 0);
INSERT INTO `smsverification` VALUES (104, '2021-07-13 18:47:35', NULL, 0, 104, '18888888888', '553146', '127.0.0.1', 0, '2021-07-13 18:52:35', 0, 0);
INSERT INTO `smsverification` VALUES (105, '2021-07-13 18:48:16', '2021-07-13 18:49:03', 1, 105, '12234542121', '550613', '127.0.0.1', 0, '2021-07-13 18:53:16', 2, 0);
INSERT INTO `smsverification` VALUES (106, '2021-07-13 18:49:03', NULL, 0, 106, '12234542121', '214396', '127.0.0.1', 0, '2021-07-13 18:54:03', 0, 0);
INSERT INTO `smsverification` VALUES (107, '2021-07-14 17:24:59', '2021-07-15 17:54:35', 1, 107, '15228943505', '792623', '127.0.0.1', 0, '2021-07-14 17:29:59', 2, 0);
INSERT INTO `smsverification` VALUES (108, '2021-07-15 17:54:36', '2021-07-15 18:11:09', 1, 108, '15228943505', '339968', '127.0.0.1', 0, '2021-07-15 17:59:36', 2, 0);
INSERT INTO `smsverification` VALUES (109, '2021-07-15 18:11:09', '2021-07-15 18:14:49', 1, 109, '15228943505', '070215', '127.0.0.1', 0, '2021-07-15 18:16:09', 2, 0);
INSERT INTO `smsverification` VALUES (110, '2021-07-15 18:14:50', '2021-07-15 18:16:37', 1, 110, '15228943505', '162172', '127.0.0.1', 0, '2021-07-15 18:19:50', 2, 0);
INSERT INTO `smsverification` VALUES (111, '2021-07-15 18:16:38', '2021-07-15 18:19:37', 1, 111, '15228943505', '799335', '127.0.0.1', 0, '2021-07-15 18:21:38', 2, 0);
INSERT INTO `smsverification` VALUES (112, '2021-07-15 18:19:37', '2021-07-15 22:09:21', 1, 112, '15228943505', '441589', '127.0.0.1', 0, '2021-07-15 18:24:37', 2, 0);
INSERT INTO `smsverification` VALUES (113, '2021-07-15 22:09:21', '2021-07-16 09:29:44', 1, 113, '15228943505', '187138', '127.0.0.1', 0, '2021-07-15 22:14:21', 2, 0);
INSERT INTO `smsverification` VALUES (114, '2021-07-16 09:29:45', '2021-07-17 14:54:09', 1, 114, '15228943505', '245380', '127.0.0.1', 0, '2021-07-16 09:34:45', 2, 0);
INSERT INTO `smsverification` VALUES (115, '2021-07-17 14:54:10', '2021-07-17 14:55:48', 1, 115, '15228943505', '392625', '127.0.0.1', 0, '2021-07-17 14:59:10', 2, 0);
INSERT INTO `smsverification` VALUES (116, '2021-07-17 14:55:48', NULL, 0, 116, '15228943505', '660829', '127.0.0.1', 0, '2021-07-17 15:00:48', 0, 0);

-- ----------------------------
-- Table structure for usersummary
-- ----------------------------
DROP TABLE IF EXISTS `usersummary`;
CREATE TABLE `usersummary`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdDate` datetime NULL DEFAULT NULL,
  `lastModifiedDate` datetime NULL DEFAULT NULL,
  `version` int(11) NULL DEFAULT NULL,
  `reportNum` int(11) NULL DEFAULT NULL COMMENT '检验报告数量',
  `buyNum` int(11) NULL DEFAULT NULL COMMENT '产品购买数量',
  `userId` bigint(20) NOT NULL COMMENT '用户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of usersummary
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
