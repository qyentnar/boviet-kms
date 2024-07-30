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

 Date: 09/10/2022 15:41:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_activiti_template
-- ----------------------------
DROP TABLE IF EXISTS `sys_activiti_template`;
CREATE TABLE `sys_activiti_template` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT '流程模板名称',
  `description` varchar(500) CHARACTER SET utf8 DEFAULT NULL COMMENT '流程说明',
  `process_def_id` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT '流程定义id',
  `process_key` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT '流程key',
  `process_deployment_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '流程部署id',
  `process_def_xml` longtext CHARACTER SET utf8 COLLATE utf8_bin COMMENT '流程定义xml',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新人',
  `create_user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `update_user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
