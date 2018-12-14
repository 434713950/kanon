package com.github.kanon.gateway.component.config;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: PengCheng
 * @Description:    动态路由配置项
 * @Date: 2018/9/12
 */
@Configuration
public class DynamicRouteConfiguration {

    /**
     * 将自定义的动态路由注入
     * @return
     */
    @Bean
    public DynamicRouteLocator dynamicRouteLocator(Registration registration, DiscoveryClient discovery,
                                                   ZuulProperties zuulProperties, ServerProperties server, CacheManager cacheManager) {
        return new DynamicRouteLocator(server.getServletPrefix()
                , discovery
                , zuulProperties
                , registration
                , cacheManager);
    }
}
