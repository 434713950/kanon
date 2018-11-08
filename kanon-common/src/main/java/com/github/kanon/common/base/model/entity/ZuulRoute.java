package com.github.kanon.common.base.model.entity;

import lombok.Data;

/**
 * <p>动态路由配置模型</p>
 *
 * @author lengleng
 * @since 2018-05-15
 */
@Data
public class ZuulRoute {

    /**
     * router Id
     */
    private Integer id;
    /**
     * 路由路径
     */
    private String path;
    /**
     * 服务名称
     */
    private String serviceId;
    /**
     * url代理
     */
    private String url;
    /**
     * 转发去掉前缀
     */
    private String stripPrefix;
    /**
     * 是否重试
     */
    private String retryable;
    /**
     * 敏感请求头
     */
    private String sensitiveheadersList;

}
