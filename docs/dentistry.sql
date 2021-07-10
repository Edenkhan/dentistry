/*
 Navicat Premium Data Transfer

 Source Server         : ghost
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : localhost:3306
 Source Schema         : dentistry

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 09/07/2021 20:02:33
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
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of appointmanage
-- ----------------------------
INSERT INTO `appointmanage` VALUES (1, '2021-07-09 12:41:24', '2021-07-09 15:47:55', 2, 50, 1, '2021-07-09 00:00:00', 0, b'1', 1);
INSERT INTO `appointmanage` VALUES (2, '2021-07-09 12:41:24', NULL, 0, 50, 0, '2021-07-10 00:00:00', 0, b'1', 1);
INSERT INTO `appointmanage` VALUES (3, '2021-07-09 12:41:24', NULL, 0, 50, 0, '2021-07-11 00:00:00', 0, b'1', 1);
INSERT INTO `appointmanage` VALUES (4, '2021-07-09 12:41:24', NULL, 0, 50, 0, '2021-07-12 00:00:00', 0, b'1', 1);
INSERT INTO `appointmanage` VALUES (5, '2021-07-09 12:41:24', NULL, 0, 50, 0, '2021-07-13 00:00:00', 0, b'1', 1);
INSERT INTO `appointmanage` VALUES (6, '2021-07-09 12:41:24', NULL, 0, 50, 0, '2021-07-14 00:00:00', 0, b'1', 1);
INSERT INTO `appointmanage` VALUES (7, '2021-07-09 12:41:24', NULL, 0, 50, 0, '2021-07-15 00:00:00', 0, b'1', 1);
INSERT INTO `appointmanage` VALUES (8, '2021-07-09 12:41:24', NULL, 0, 50, 0, '2021-07-09 00:00:00', 1, b'1', 1);
INSERT INTO `appointmanage` VALUES (9, '2021-07-09 12:41:24', NULL, 0, 50, 0, '2021-07-10 00:00:00', 1, b'1', 1);
INSERT INTO `appointmanage` VALUES (10, '2021-07-09 12:41:24', NULL, 0, 50, 0, '2021-07-11 00:00:00', 1, b'1', 1);
INSERT INTO `appointmanage` VALUES (11, '2021-07-09 12:41:24', NULL, 0, 50, 0, '2021-07-12 00:00:00', 1, b'1', 1);
INSERT INTO `appointmanage` VALUES (12, '2021-07-09 12:41:24', NULL, 0, 50, 0, '2021-07-13 00:00:00', 1, b'1', 1);
INSERT INTO `appointmanage` VALUES (13, '2021-07-09 12:41:24', NULL, 0, 50, 0, '2021-07-14 00:00:00', 1, b'1', 1);
INSERT INTO `appointmanage` VALUES (14, '2021-07-09 12:41:24', NULL, 0, 50, 0, '2021-07-15 00:00:00', 1, b'1', 1);
INSERT INTO `appointmanage` VALUES (15, '2021-07-09 12:41:26', NULL, 0, 50, 0, '2021-07-15 00:00:00', 0, b'1', 1);
INSERT INTO `appointmanage` VALUES (16, '2021-07-09 12:41:26', NULL, 0, 50, 0, '2021-07-15 00:00:00', 1, b'1', 1);
INSERT INTO `appointmanage` VALUES (17, '2021-07-09 15:36:52', NULL, 0, 50, 0, '2021-07-09 00:00:00', 0, b'1', 2);
INSERT INTO `appointmanage` VALUES (18, '2021-07-09 15:36:52', NULL, 0, 50, 0, '2021-07-10 00:00:00', 0, b'1', 2);
INSERT INTO `appointmanage` VALUES (19, '2021-07-09 15:36:52', NULL, 0, 50, 0, '2021-07-11 00:00:00', 0, b'1', 2);
INSERT INTO `appointmanage` VALUES (20, '2021-07-09 15:36:52', NULL, 0, 50, 0, '2021-07-12 00:00:00', 0, b'1', 2);
INSERT INTO `appointmanage` VALUES (21, '2021-07-09 15:36:52', NULL, 0, 50, 0, '2021-07-13 00:00:00', 0, b'1', 2);
INSERT INTO `appointmanage` VALUES (22, '2021-07-09 15:36:52', NULL, 0, 50, 0, '2021-07-14 00:00:00', 0, b'1', 2);
INSERT INTO `appointmanage` VALUES (23, '2021-07-09 15:36:52', NULL, 0, 50, 0, '2021-07-15 00:00:00', 0, b'1', 2);
INSERT INTO `appointmanage` VALUES (24, '2021-07-09 15:36:52', NULL, 0, 50, 0, '2021-07-09 00:00:00', 1, b'1', 2);
INSERT INTO `appointmanage` VALUES (25, '2021-07-09 15:36:52', NULL, 0, 50, 0, '2021-07-10 00:00:00', 1, b'1', 2);
INSERT INTO `appointmanage` VALUES (26, '2021-07-09 15:36:52', NULL, 0, 50, 0, '2021-07-11 00:00:00', 1, b'1', 2);
INSERT INTO `appointmanage` VALUES (27, '2021-07-09 15:36:52', NULL, 0, 50, 0, '2021-07-12 00:00:00', 1, b'1', 2);
INSERT INTO `appointmanage` VALUES (28, '2021-07-09 15:36:52', NULL, 0, 50, 0, '2021-07-13 00:00:00', 1, b'1', 2);
INSERT INTO `appointmanage` VALUES (29, '2021-07-09 15:36:52', NULL, 0, 50, 0, '2021-07-14 00:00:00', 1, b'1', 2);
INSERT INTO `appointmanage` VALUES (30, '2021-07-09 15:36:52', NULL, 0, 50, 0, '2021-07-15 00:00:00', 1, b'1', 2);
INSERT INTO `appointmanage` VALUES (31, '2021-07-09 15:36:55', NULL, 0, 50, 0, '2021-07-15 00:00:00', 0, b'1', 2);
INSERT INTO `appointmanage` VALUES (32, '2021-07-09 15:36:55', NULL, 0, 50, 0, '2021-07-15 00:00:00', 1, b'1', 2);

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
  `timePeriod` tinyint(4) NULL DEFAULT NULL COMMENT '0-AM 1-PM',
  `state` tinyint(4) NULL DEFAULT NULL COMMENT '0-初始状态 1-报告上传完成',
  `orderId` bigint(20) NULL DEFAULT NULL COMMENT '订单id',
  `userId` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  `productId` bigint(20) NULL DEFAULT NULL COMMENT '产品id',
  `shopId` bigint(20) NULL DEFAULT NULL COMMENT '门店id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of appointment
-- ----------------------------
INSERT INTO `appointment` VALUES (1, '2021-07-09 15:47:55', '2021-07-09 16:46:15', 2, '2021-07-09 00:00:00', 1, 0, 1, 1, 3, 1);

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
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of dictionaryitem
-- ----------------------------
INSERT INTO `dictionaryitem` VALUES (1, '2021-06-28 11:41:39', '2021-06-28 17:59:47', 17, '老王', b'1', 1);
INSERT INTO `dictionaryitem` VALUES (2, '2021-06-28 11:52:30', '2021-06-28 18:00:00', 15, '老王', b'1', 2);
INSERT INTO `dictionaryitem` VALUES (3, '2021-06-28 18:01:55', '2021-07-06 14:49:32', 2, '老张', b'1', 1);

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
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
  `payStatus` tinyint(4) NULL DEFAULT NULL COMMENT '支付状态 0-未支付 1-已支付',
  `appointStatus` tinyint(4) NULL DEFAULT NULL COMMENT '预约状态 0-待预约 1-已预约',
  `userId` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  `productId` bigint(20) NULL DEFAULT NULL COMMENT '产品id',
  `shopId` bigint(20) NULL DEFAULT NULL COMMENT '门店id',
  `dicItemId` bigint(20) NULL DEFAULT NULL COMMENT '字典详情id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (1, '2021-07-08 14:37:50', '2021-07-09 15:47:55', 2, '862704034404892672', 12.00, 1, 1, 1, 1, 1, 3, 1, NULL);
INSERT INTO `orders` VALUES (2, '2021-07-08 14:38:02', NULL, 0, '862704084321304576', 12.00, 2, 0, 1, 0, 1, 2, NULL, 1);
INSERT INTO `orders` VALUES (3, '2021-07-08 15:38:40', NULL, 0, '862719343966814208', 12.00, 2, 0, 1, 0, 1, 2, NULL, 3);

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
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES (1, '2021-06-29 17:49:49', '2021-07-06 14:40:11', 1, '测试', 12.00, '简介', '<p>描述</p>', 0, 0, 1, 2, '/api/img/producticon/a7edc9a0-e8c4-465d-b386-256432726f7c.jpg', 1, 2);
INSERT INTO `product` VALUES (2, '2021-06-29 18:21:24', '2021-07-06 14:41:48', 2, '测试1', 12.00, '简介1', '<p>描述1</p>', 0, 0, 1, 2, '/api/img/producticon/d7894037-d64a-425b-b0dc-e228f0b60dcc.jpg', 1, 3);
INSERT INTO `product` VALUES (3, '2021-06-29 18:26:39', '2021-07-09 18:13:34', 8, '产品2', 12.00, '简介2', '<p>a啊啊啊啊啊啊啊啊</p>', 1, 1, 10, 1, '/api/img/producticon/f6a431be-ad57-4275-b74f-9f773641449d.jpg', 1, 4);

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
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of productdetailpath
-- ----------------------------
INSERT INTO `productdetailpath` VALUES (13, '2021-07-06 14:40:11', NULL, 0, '/api/img/productdetail/59dd8f95-fab2-4265-897b-c4b87b20ccb6.jpg', 1);
INSERT INTO `productdetailpath` VALUES (15, '2021-07-06 14:41:48', NULL, 0, '/api/img/productdetail/9b706ba3-a2ff-4ae7-af75-ce3c2e67be80.jpg', 2);
INSERT INTO `productdetailpath` VALUES (20, '2021-07-09 18:13:35', NULL, 0, '/api/img/productdetail/53195810-ef78-4b3b-9966-14e7b4e0d493.jpg', 3);
INSERT INTO `productdetailpath` VALUES (21, '2021-07-09 18:13:35', NULL, 0, '/api/img/productdetail/033100d8-3775-4b5d-a191-d1214fa12b3f.jpg', 3);

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
  `used` bit(1) NULL DEFAULT NULL COMMENT '是否已使用',
  `userId` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  `productId` bigint(20) NULL DEFAULT NULL COMMENT '产品id',
  `shopId` bigint(20) NULL DEFAULT NULL COMMENT '门店id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of redeemcode
-- ----------------------------

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
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of registereduser
-- ----------------------------
INSERT INTO `registereduser` VALUES (1, '2021-07-05 10:11:47', '2021-07-09 09:40:49', 66, '汤汤', NULL, 1, '15228943505', b'0', 'o94oc5gI4p4hcPLDBNtflB_w1jKQ', '甘乐', '/api/img/avatar/bb4b1448-5a12-434b-be9e-f1231afdcacc.jpeg');

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
  `synced` bit(1) NULL DEFAULT NULL COMMENT '是否同步',
  `userId` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  `reserveId` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '预约id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of report
-- ----------------------------

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
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
  `appointedNum` int(11) NULL DEFAULT NULL COMMENT '已预约次数',
  `enabled` bit(1) NULL DEFAULT NULL COMMENT '是否启用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of shop
-- ----------------------------
INSERT INTO `shop` VALUES (1, '2021-06-30 11:55:04', '2021-07-02 11:50:25', 10, '门店1', '地址1', '31564654654', 16, NULL, b'1');
INSERT INTO `shop` VALUES (2, '2021-07-02 11:57:35', '2021-07-02 12:16:09', 2, '门店2', '地址2', '65456465', NULL, NULL, b'1');

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
) ENGINE = InnoDB AUTO_INCREMENT = 64 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 64 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
INSERT INTO `smsverification` VALUES (58, '2021-07-08 18:04:41', NULL, 0, 58, '13600000000', '732618', '127.0.0.1', 0, '2021-07-08 18:09:41', 0, 0);
INSERT INTO `smsverification` VALUES (59, '2021-07-08 18:07:35', '2021-07-08 18:29:08', 1, 59, '15228943505', '010568', '127.0.0.1', 0, '2021-07-08 18:12:35', 2, 0);
INSERT INTO `smsverification` VALUES (60, '2021-07-08 18:29:09', '2021-07-09 09:33:49', 1, 60, '15228943505', '243818', '127.0.0.1', 0, '2021-07-08 18:34:09', 2, 0);
INSERT INTO `smsverification` VALUES (61, '2021-07-09 09:33:50', '2021-07-09 09:38:18', 1, 61, '15228943505', '871754', '127.0.0.1', 0, '2021-07-09 09:38:49', 2, 0);
INSERT INTO `smsverification` VALUES (62, '2021-07-09 09:38:19', '2021-07-09 09:40:37', 1, 62, '15228943505', '490539', '127.0.0.1', 0, '2021-07-09 09:43:19', 2, 0);
INSERT INTO `smsverification` VALUES (63, '2021-07-09 09:40:38', NULL, 0, 63, '15228943505', '666743', '127.0.0.1', 0, '2021-07-09 09:45:38', 0, 0);

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
