/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50729
Source Host           : localhost:3306
Source Database       : app-fast

Target Server Type    : MYSQL
Target Server Version : 50729
File Encoding         : 65001

Date: 2020-10-31 21:19:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(255) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `avatar` varchar(255) NOT NULL COMMENT '头像',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态(0启用，1禁用)',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除（0未删除，1已删除）',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('1', 'root', '63a9f0ea7bb98050796b649e85481845', 'root', 'https://iph.href.lu/100x100?fg=666666&bg=cccccc', '0', '0', '0', '2020-10-11 18:53:42', '2020-10-28 21:13:44');

-- ----------------------------
-- Table structure for admin_role
-- ----------------------------
DROP TABLE IF EXISTS `admin_role`;
CREATE TABLE `admin_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `admin_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin_role
-- ----------------------------

-- ----------------------------
-- Table structure for resource
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '父ID',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `url` varchar(255) DEFAULT NULL COMMENT '地址',
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '类型(0目录，1菜单，2按钮)',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态(0启用，1禁用)',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除（0未删除，1已删除）',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of resource
-- ----------------------------
INSERT INTO `resource` VALUES ('1', '0', '首页', '/index.html', '0', 'mdi-home', '0', '0', '0', '2020-10-31 20:50:25', '2020-10-31 20:50:25');
INSERT INTO `resource` VALUES ('2', '1', '欢迎页', '/main.html', '1', 'mdi-human-greeting', '0', '0', '0', '2020-10-31 20:53:42', '2020-10-31 20:53:42');
INSERT INTO `resource` VALUES ('3', '0', '权限管理', '', '0', 'mdi-lock', '0', '0', '0', '2020-10-31 20:08:20', '2020-10-31 21:15:44');
INSERT INTO `resource` VALUES ('4', '3', '管理员列表', '/admin/list.html', '1', 'mdi-account', '0', '0', '0', '2020-10-31 20:47:39', '2020-10-31 21:11:59');
INSERT INTO `resource` VALUES ('5', '3', '角色列表', '/role/list.html', '1', 'mdi-account-multiple', '0', '0', '0', '2020-10-31 20:50:25', '2020-10-31 20:50:25');
INSERT INTO `resource` VALUES ('6', '3', '资源列表', '/resource/list.html', '1', 'mdi-menu', '0', '0', '0', '2020-10-31 20:53:42', '2020-10-31 20:53:42');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态(0启用，1禁用)',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除（0未删除，1已删除）',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '管理员', '0', '0', '0', '2020-10-31 20:09:22', '2020-10-31 20:09:22');

-- ----------------------------
-- Table structure for role_resource
-- ----------------------------
DROP TABLE IF EXISTS `role_resource`;
CREATE TABLE `role_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` int(11) NOT NULL,
  `resource_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_resource
-- ----------------------------
