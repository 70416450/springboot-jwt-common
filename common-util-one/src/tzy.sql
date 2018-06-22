/*
Navicat MySQL Data Transfer

Source Server         : localhostMysql
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : mytest

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-06-12 00:09:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for info_enclosure
-- ----------------------------
DROP TABLE IF EXISTS `info_enclosure`;
CREATE TABLE `info_enclosure` (
  `biz_enclosure_id` bigint(20) NOT NULL COMMENT '附件中间表id',
  `enclosure_id` bigint(20) NOT NULL COMMENT '附件id',
  `info_id` bigint(20) NOT NULL COMMENT '业务id',
  `is_del` tinyint(1) DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`biz_enclosure_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='附件中间表';

-- ----------------------------
-- Records of info_enclosure
-- ----------------------------

-- ----------------------------
-- Table structure for t_enclosure
-- ----------------------------
DROP TABLE IF EXISTS `t_enclosure`;
CREATE TABLE `t_enclosure` (
  `enclosure_id` bigint(20) NOT NULL COMMENT '附件id',
  `upload_time` datetime DEFAULT NULL COMMENT '上传时间',
  `file_number` varchar(50) DEFAULT NULL COMMENT '文件编号',
  `file_name` varchar(50) DEFAULT NULL COMMENT '文件名称',
  `file_storage_name` varchar(50) DEFAULT NULL COMMENT '文件存储名称',
  `file_size` double DEFAULT NULL COMMENT '文件大小',
  `enclosure_path` varchar(100) DEFAULT NULL COMMENT '附件存放路径',
  `file_source` varchar(50) DEFAULT NULL COMMENT '文件来源',
  `upload_person` varchar(50) DEFAULT NULL COMMENT '上传人',
  `is_del` tinyint(1) DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`enclosure_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='附件表';

-- ----------------------------
-- Records of t_enclosure
-- ----------------------------

-- ----------------------------
-- Table structure for t_student
-- ----------------------------
DROP TABLE IF EXISTS `t_student`;
CREATE TABLE `t_student` (
  `student_id` bigint(11) NOT NULL COMMENT '学生ID',
  `id` bigint(11) DEFAULT NULL COMMENT '班级ID',
  `user_name` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '名字',
  `gender` int(11) DEFAULT NULL COMMENT '性别',
  `id_card` char(18) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '身份证号',
  `eyes_degree` double DEFAULT NULL COMMENT '眼睛度数',
  `is_del` tinyint(1) DEFAULT NULL COMMENT '是否删除',
  `bd` datetime DEFAULT NULL COMMENT '生日',
  PRIMARY KEY (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='学生表';

-- ----------------------------
-- Records of t_student
-- ----------------------------
INSERT INTO `t_student` VALUES ('5471374410122240', '5468888365663232', '小一', '1', '510250198907083828', '2', '0', '2018-06-11 00:00:00');
INSERT INTO `t_student` VALUES ('5471376268166144', '5468888365663232', '喔二', '1', '510250198907083828', '2', '0', '2018-06-11 00:00:00');
INSERT INTO `t_student` VALUES ('5471384038933504', '5468895679251456', '张三', '1', '510250198907083828', '2', '0', '2018-06-11 00:00:00');
INSERT INTO `t_student` VALUES ('5471384038933505', '5468895679251456', '李四', '1', '510250198907083828', '2', '0', '2018-06-11 00:00:00');
INSERT INTO `t_student` VALUES ('5471386855932928', '5468895679251457', '王五', '1', '510250198907083828', '2', '0', '2018-06-11 00:00:00');
INSERT INTO `t_student` VALUES ('5471386855932929', '5468895679251457', '赵六', '1', '510250198907083828', '2', '0', '2018-06-11 00:00:00');
INSERT INTO `t_student` VALUES ('5471386855932930', '5468895679251457', '秦七', '1', '510250198907083828', '2', '1', '2018-06-11 00:00:00');

-- ----------------------------
-- Table structure for t_tclass
-- ----------------------------
DROP TABLE IF EXISTS `t_tclass`;
CREATE TABLE `t_tclass` (
  `id` bigint(11) NOT NULL COMMENT '班级ID',
  `user_name` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '名字',
  `is_del` tinyint(1) DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='班级表';

-- ----------------------------
-- Records of t_tclass
-- ----------------------------
INSERT INTO `t_tclass` VALUES ('5468888365663232', '一班', '0');
INSERT INTO `t_tclass` VALUES ('5468895679251456', '二班', '0');
INSERT INTO `t_tclass` VALUES ('5468895679251457', '三班', '0');
