/*
Navicat MySQL Data Transfer

Source Server         : localhosttest
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : db_security

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-06-22 12:25:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_employee
-- ----------------------------
DROP TABLE IF EXISTS `t_employee`;
CREATE TABLE `t_employee` (
  `employee_id` bigint(20) NOT NULL COMMENT '员工id',
  `scenic_area_id` bigint(20) NOT NULL COMMENT '景区id',
  `name` varchar(50) NOT NULL COMMENT '姓名',
  `id_card_number` char(18) NOT NULL COMMENT '身份证号码',
  `phone_number` char(11) NOT NULL COMMENT '电话号码',
  `address` varchar(200) DEFAULT NULL COMMENT '家庭住址',
  `job_number` varchar(32) DEFAULT NULL COMMENT '工号',
  `user_name` varchar(16) NOT NULL COMMENT '用户名',
  `password` char(32) NOT NULL COMMENT '密码',
  `salt` char(32) NOT NULL COMMENT '秘钥',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `is_disabled` tinyint(1) NOT NULL COMMENT '是否禁用',
  `is_del` tinyint(1) NOT NULL COMMENT '是否删除',
  PRIMARY KEY (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工';

-- ----------------------------
-- Records of t_employee
-- ----------------------------
INSERT INTO `t_employee` VALUES ('5501251945563136', '0', '小小张', '510302198907083718', '13408547784', '双流', 'string', 'Heaton', 'c73c28725767d5bde693161d04a1b11a', 'sDJCZsEIAcKuRuqoEhbQXvNLQHswnoIS', '2018-06-22 11:37:08', '0', '0');

-- ----------------------------
-- Table structure for t_employee_role
-- ----------------------------
DROP TABLE IF EXISTS `t_employee_role`;
CREATE TABLE `t_employee_role` (
  `employee_id` bigint(20) NOT NULL COMMENT '员工id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`employee_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工角色';

-- ----------------------------
-- Records of t_employee_role
-- ----------------------------
INSERT INTO `t_employee_role` VALUES ('5501251945563136', '1');

-- ----------------------------
-- Table structure for t_perm
-- ----------------------------
DROP TABLE IF EXISTS `t_perm`;
CREATE TABLE `t_perm` (
  `perm_id` bigint(20) NOT NULL COMMENT '权限id',
  `parent_id` bigint(20) NOT NULL COMMENT '上级id',
  `perm_name` varchar(50) NOT NULL COMMENT '权限名称',
  `perm_url` varchar(100) NOT NULL COMMENT '权限url',
  `method` varchar(10) NOT NULL COMMENT '访问方式',
  `is_disabled` tinyint(1) NOT NULL COMMENT '是否禁用',
  `is_del` tinyint(1) NOT NULL COMMENT '是否删除',
  PRIMARY KEY (`perm_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限';

-- ----------------------------
-- Records of t_perm
-- ----------------------------
INSERT INTO `t_perm` VALUES ('1', '0', '查询', '/employee/**', 'GET', '0', '0');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `role_name` varchar(50) NOT NULL COMMENT '角色名称',
  `is_disabled` tinyint(1) NOT NULL COMMENT '是否禁用',
  `is_del` tinyint(1) NOT NULL COMMENT '是否删除',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色';

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('1', '管理员', '0', '0');

-- ----------------------------
-- Table structure for t_role_perm
-- ----------------------------
DROP TABLE IF EXISTS `t_role_perm`;
CREATE TABLE `t_role_perm` (
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `perm_id` bigint(20) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`role_id`,`perm_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限';

-- ----------------------------
-- Records of t_role_perm
-- ----------------------------
INSERT INTO `t_role_perm` VALUES ('1', '1');
