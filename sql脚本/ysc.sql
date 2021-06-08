/*
 Navicat Premium Data Transfer

 Source Server         : mysqltest
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3306
 Source Schema         : ysc

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 27/05/2021 18:21:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for folder
-- ----------------------------
DROP TABLE IF EXISTS `folder`;
CREATE TABLE `folder`  (
                           `id` char(32) NOT NULL,
                           `pid` char(32) DEFAULT NULL,
                           `name` varchar(255) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT '文件或文件夹名称',
                           `type` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '文件类型',
                           `file_id` int DEFAULT NULL COMMENT '文件id  空表示是文件夹',
                           `file_stor_id` char(32) DEFAULT NULL COMMENT '创建人',
                           `create_time` datetime DEFAULT NULL,
                           `update_time` datetime DEFAULT NULL,
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file`  (
                             `id` char(32) NOT NULL,
                             `name` varchar(255) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT '文件名称',
                             `path` varchar(1024) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT '文件相对路径',
                             `file_store_id` char(32) DEFAULT NULL COMMENT '创建人',
                             `create_time` datetime DEFAULT NULL,
                             `update_time` datetime DEFAULT NULL,
                             `pid` char(32) DEFAULT NULL,
                             `size` double DEFAULT NULL,
                             `type` varchar(100) DEFAULT NULL,
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4  ROW_FORMAT = Dynamic;



-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
                             `role_id` char(32) NOT NULL,
                             `role_name` varchar(100) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT '角色名称',
                             `user_id` char(32) DEFAULT NULL COMMENT '创建者ID',
                             `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                             PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB  CHARACTER SET = utf8mb4  COMMENT = '角色' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
                             `user_id` char(32) NOT NULL ,
                             `username` varchar(50) CHARACTER SET utf8mb4  NOT NULL COMMENT '用户名',
                             `password` varchar(100) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '密码',
                             `email` varchar(100) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '邮箱',
                             `mobile` varchar(100) CHARACTER SET utf8mb4  NULL DEFAULT NULL COMMENT '手机号',
                             `status` tinyint(0) NULL DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
                             `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                             `file_store_id` char(32) DEFAULT NULL,
                             PRIMARY KEY (`user_id`) USING BTREE,
                             UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB  CHARACTER SET = utf8mb4  COMMENT = '系统用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
                             `id` char(32) NOT NULL,
                             `user_id` char(32) DEFAULT NULL,
                             `role_id` char(32) DEFAULT NULL,
                             PRIMARY KEY (`id`)
) ENGINE = InnoDB  CHARACTER SET = utf8mb4  COMMENT = '用户角色关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for file_store
-- ----------------------------
DROP TABLE IF EXISTS `file_store`;
CREATE TABLE `user_role` (
                             `file_store_id` char(32) NOT NULL,
                             `user_id` char(32) DEFAULT NULL,
                             `current_size` double DEFAULT '0',
                             `max_size` double DEFAULT '15728640',
                             PRIMARY KEY (`file_store_id`)
) ENGINE = InnoDB  CHARACTER SET = utf8mb4  COMMENT = '用户文件仓库' ROW_FORMAT = Dynamic;
SET FOREIGN_KEY_CHECKS = 1;