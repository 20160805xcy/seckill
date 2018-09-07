/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50527
 Source Host           : localhost:3306
 Source Schema         : seckill

 Target Server Type    : MySQL
 Target Server Version : 50527
 File Encoding         : 65001

 Date: 28/07/2018 18:57:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for seckill
-- ----------------------------
DROP TABLE IF EXISTS `seckill`;
CREATE TABLE `seckill`  (
  `seckill_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
  `name` varchar(120) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品名称',
  `number` int(11) NOT NULL COMMENT '库存数量',
  `start_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '秒杀开启时间',
  `end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '秒杀结束时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`seckill_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Records of seckill
-- ----------------------------
INSERT INTO `seckill` VALUES (5, '1000元秒杀iPhone6', 100, '2015-11-01 00:00:00', '2015-11-02 00:00:00', '2018-07-28 18:56:08');
INSERT INTO `seckill` VALUES (6, '500元秒杀iPad2', 200, '2015-11-01 00:00:00', '2015-11-02 00:00:00', '2018-07-28 18:56:08');
INSERT INTO `seckill` VALUES (7, '300元秒杀小米4', 300, '2015-11-01 00:00:00', '2015-11-02 00:00:00', '2018-07-28 18:56:08');
INSERT INTO `seckill` VALUES (8, '200元秒杀红米note', 400, '2015-11-01 00:00:00', '2015-11-02 00:00:00', '2018-07-28 18:56:08');

SET FOREIGN_KEY_CHECKS = 1;
