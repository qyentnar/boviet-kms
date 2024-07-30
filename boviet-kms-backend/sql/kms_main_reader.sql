/*
 Navicat Premium Data Transfer

 Source Server         : admin123
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : localhost:3306
 Source Schema         : ry-vue-kms

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 02/11/2022 11:51:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for kms_main_reader
-- ----------------------------
DROP TABLE IF EXISTS `kms_main_reader`;
CREATE TABLE `kms_main_reader` (
  `know_id` bigint(20) DEFAULT NULL COMMENT '知识ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
