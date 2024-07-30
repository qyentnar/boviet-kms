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

 Date: 27/07/2022 10:53:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for kms_catalog
-- ----------------------------
DROP TABLE IF EXISTS `kms_catalog`;
CREATE TABLE `kms_catalog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(255) DEFAULT NULL COMMENT '目录名称',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父目录',
  `sort` int(11) DEFAULT NULL COMMENT '排序号',
  `description` varchar(500) DEFAULT NULL COMMENT '目录描述',
  `cover` varchar(255) DEFAULT NULL COMMENT '目录封面',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_user_id` bigint(22) DEFAULT NULL COMMENT '创建人ID',
  `update_user_id` bigint(22) DEFAULT NULL COMMENT '更新人ID',
  `template_id` bigint(22) DEFAULT NULL COMMENT '流程模板ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for kms_main
-- ----------------------------
DROP TABLE IF EXISTS `kms_main`;
CREATE TABLE `kms_main` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(255) DEFAULT NULL COMMENT '知识标题',
  `catalog_id` bigint(20) DEFAULT NULL COMMENT '所属目录',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '所属部门',
  `main_content` longtext COMMENT '知识正文',
  `summary` varchar(1000) DEFAULT NULL COMMENT '知识摘要',
  `keyword` varchar(500) DEFAULT NULL COMMENT '关键字',
  `cover_img` varchar(255) DEFAULT NULL COMMENT '知识封面',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `origin_id` bigint(20) NULL COMMENT '原始ID',
  `is_new_version` int(11) DEFAULT NULL COMMENT '是否最新版本',
  `state` varchar(2) DEFAULT NULL COMMENT '状态',
  `publish_date` datetime DEFAULT NULL COMMENT '发布时间',
  `read_count` int(11) DEFAULT NULL COMMENT '阅读量',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `file_path` varchar(2000) DEFAULT NULL COMMENT '附件路径',
  `file_name` varchar(2000) DEFAULT NULL COMMENT '附件名称',
  `create_user_id` bigint(22) DEFAULT NULL COMMENT '创建人ID',
  `update_user_id` bigint(22) DEFAULT NULL COMMENT '更新人ID',
  `process_instance_id` varchar(50) DEFAULT NULL COMMENT '流程实例id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for kms_main_to_author
-- ----------------------------
DROP TABLE IF EXISTS `kms_main_to_author`;
CREATE TABLE `kms_main_to_author` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `k_id` bigint(20) DEFAULT NULL COMMENT '知识id',
  `author_id` bigint(20) DEFAULT NULL COMMENT '作者id',
  `author_name` varchar(255) DEFAULT NULL COMMENT '作者名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for sys_att_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_att_file`;
CREATE TABLE `sys_att_file` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT '文件表主键ID',
  `file_path` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '文件路径',
  `file_name` varchar(90) COLLATE utf8_bin DEFAULT NULL COMMENT '文件名称',
  `md5` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '文件MD5值',
  `content_type` varchar(90) COLLATE utf8_bin DEFAULT '' COMMENT '文件内容类型，如：application/msword',
  `file_type` varchar(90) COLLATE utf8_bin DEFAULT NULL COMMENT '文件类型，如：doc/xls',
  `file_size` bigint(90) DEFAULT NULL COMMENT '文件大小',
  `state` tinyint(2) DEFAULT NULL COMMENT '文件状态: 0-转换中;1-转换失败;2-转换成功;3-无需转换',
  `file_content` longtext COLLATE utf8_bin COMMENT '文件内容',
  `converted` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '已转换后的地址',
  `tenant_id` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '租户号',
  `create_by` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='文件表';

-- ----------------------------
-- Table structure for sys_att_main
-- ----------------------------
DROP TABLE IF EXISTS `sys_att_main`;
CREATE TABLE `sys_att_main` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `model_id` bigint(22) DEFAULT NULL COMMENT '业务数据ID',
  `model_name` varchar(255) DEFAULT NULL COMMENT '业务实体完整类路径',
  `att_key` varchar(255) DEFAULT NULL COMMENT '附件KEY',
  `file_id` bigint(22) DEFAULT NULL COMMENT '物理文件ID',
  `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
