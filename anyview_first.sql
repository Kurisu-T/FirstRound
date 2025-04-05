/*
 Navicat Premium Dump SQL

 Source Server         : Kurisu
 Source Server Type    : MySQL
 Source Server Version : 80041 (8.0.41-0ubuntu0.24.04.1)
 Source Host           : 172.25.84.131:3306
 Source Schema         : anyview_first

 Target Server Type    : MySQL
 Target Server Version : 80041 (8.0.41-0ubuntu0.24.04.1)
 File Encoding         : 65001

 Date: 05/04/2025 14:46:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名称',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '电话号码',
  `password` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `phone`(`phone` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '管理员表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'root', '1', '1');

-- ----------------------------
-- Table structure for movie
-- ----------------------------
DROP TABLE IF EXISTS `movie`;
CREATE TABLE `movie`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '电影id',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '电影名',
  `show_time` datetime NULL DEFAULT NULL COMMENT '放映时间',
  `hall` int NULL DEFAULT NULL COMMENT '影厅编号',
  `create_time` datetime NOT NULL COMMENT '添加时间',
  `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
  `price` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '价格',
  `amount` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '数量',
  `end_time` datetime NULL DEFAULT NULL COMMENT '结束时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '电影表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of movie
-- ----------------------------
INSERT INTO `movie` VALUES (1, '正常购买', '2025-04-18 00:00:00', 0, '2025-04-03 14:22:12', '支付后用户余额正常扣减', 10, 100, '2025-05-18 09:00:00');
INSERT INTO `movie` VALUES (2, '余额不足', '2025-04-18 00:00:00', 1, '2025-04-03 14:23:12', '余额不足，无法支付', 200, 100, '2025-05-18 17:00:00');
INSERT INTO `movie` VALUES (3, '尚未上映', '2025-04-01 00:00:00', 2, '2025-04-03 14:25:38', '未上映，用户获取的电影列表没有', 50, 0, '2025-04-01 00:00:00');
INSERT INTO `movie` VALUES (4, '库存不足', '2025-04-18 00:25:00', 3, '2025-04-03 14:26:18', '库存不足，无法购买', 100, 0, '2025-05-18 19:25:00');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `user_id` int NOT NULL COMMENT '订单用户id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `movie` int UNSIGNED NOT NULL COMMENT '电影id',
  `hall` int UNSIGNED NOT NULL COMMENT '影厅编号',
  `seat` int UNSIGNED NOT NULL COMMENT '座位号',
  `show_time` datetime NOT NULL COMMENT '放映时间',
  `end_time` datetime NOT NULL,
  `status` int NOT NULL COMMENT '订单状态',
  `price` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '单价',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of orders
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `phone` char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '注册电话',
  `status` int NOT NULL DEFAULT 1 COMMENT '状态',
  `money` double NOT NULL DEFAULT 0 COMMENT '余额',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `phone`(`phone` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'Kurisu', '1', '1', 1, 100);
INSERT INTO `user` VALUES (2, 'Ryo', '2', '2', 1, 100);
INSERT INTO `user` VALUES (3, 'c207', '3', '3', 1, 100);
INSERT INTO `user` VALUES (4, 'Mie', '4', '4', 0, 0);

SET FOREIGN_KEY_CHECKS = 1;
