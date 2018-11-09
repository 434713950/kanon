-- ----------------------------
-- 系统zuul动态路由表
-- ----------------------------
CREATE TABLE IF NOT EXISTS  `sys_zuul_route` (
  `id` bigint(20) NOT NULL,
  `path` varchar(150) DEFAULT NULL COMMENT '路由路径',
  `service_id` varchar(50) DEFAULT NULL COMMENT '服务名称',
  `url` varchar(255) DEFAULT NULL COMMENT '代理地址',
  `strip_prefix` bit(1) DEFAULT b'0' COMMENT '转发时是否去掉前缀',
  `retryable` bit(1) DEFAULT b'0' COMMENT '是否重试',
  `sensitiveheaders_list` varchar(255) DEFAULT NULL COMMENT '敏感的请求头',
  `enabled` bit(1) DEFAULT b'1' COMMENT '是否启用',
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` bit(1) DEFAULT b'0' COMMENT '删除标志',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic COMMENT='系统zuul动态路由表';
