/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50718
Source Host           : localhost:3306
Source Database       : kanon

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2018-11-08 18:05:44
*/
-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
CREATE TABLE IF NOT EXISTS `sys_log` (
  `id` bigint(20) NOT NULL,
  `log_type` int(3) DEFAULT NULL COMMENT '日志类型(0:异常，1:正常）',
  `create_by` varchar(25) DEFAULT NULL COMMENT '操作人信息',
  `message` varchar(255) DEFAULT NULL COMMENT '日志内容',
  `remote_addr` varchar(50) DEFAULT NULL COMMENT '请求接口的地址',
  `request_uri` varchar(150) DEFAULT NULL COMMENT '请求接口的路径',
  `method` varchar(50) DEFAULT NULL COMMENT '请求的方法',
  `user_agent` varchar(100) DEFAULT NULL COMMENT '请求代理',
  `params` varchar(255) DEFAULT NULL COMMENT '请求的参数',
  `time` bigint(20) DEFAULT NULL COMMENT '请求时长',
  `service_id` int(11) DEFAULT NULL COMMENT '请求的服务编号',
  `del_flag` bit(1) DEFAULT b'0' COMMENT '删除标识',
  `call_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '调用接口的时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic COMMENT='系统日志表';
