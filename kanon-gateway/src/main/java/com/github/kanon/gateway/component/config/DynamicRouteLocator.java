package com.github.kanon.gateway.component.config;

import com.github.kanon.common.base.model.entity.ZuulRoute;
import com.github.kanon.common.constants.ZuulConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.discovery.DiscoveryClientRouteLocator;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;

/**
 * @Author: PengCheng
 * @Description:    动态路由定位器。DiscoveryClientRouteLocator实现了动态刷新，这里直接改写DiscoveryClientRouteLocator来实现数据库的动态路由
 * @Date: 2018/9/12
 */
@Slf4j
public class DynamicRouteLocator extends DiscoveryClientRouteLocator {

    private ZuulProperties properties;

    private RedisTemplate redisTemplate;

    public DynamicRouteLocator(String servletPath, DiscoveryClient discovery, ZuulProperties properties,
                               ServiceInstance localServiceInstance, RedisTemplate redisTemplate) {
        super(servletPath, discovery, properties, localServiceInstance);
        this.properties = properties;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 重写路由配置
     * 1. 原有加载方式配置。
     * 2. DB数据库配置。
     * @return
     */
    @Override
    protected LinkedHashMap<String, ZuulProperties.ZuulRoute> locateRoutes() {
        LinkedHashMap<String, ZuulProperties.ZuulRoute> routesMap = new LinkedHashMap<>();
        //调用原有方法,读取properties配置、eureka默认配置
        routesMap.putAll(super.locateRoutes());
        routesMap.putAll(locateRoutesFromDb());

        //这里直接参考父类对路径进行处理
        LinkedHashMap<String, ZuulProperties.ZuulRoute> values = new LinkedHashMap<>();
        for (Map.Entry<String, ZuulProperties.ZuulRoute> entry : routesMap.entrySet()) {
            String path = entry.getKey();
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            if (StringUtils.isEmpty(this.properties.getPrefix())) {
                path = this.properties.getPrefix() + path;
                if (!path.startsWith("/")) {
                    path = "/" + path;
                }
            }
            values.put(path, entry.getValue());
        }
        return routesMap;
    }

    /**
     * 动态路由参数保存在redis中方便拉取,使用redis缓存数据要保证缓存与数据库同步
     * @return
     */
    private Map<String, ZuulProperties.ZuulRoute> locateRoutesFromDb() {
        Map<String, ZuulProperties.ZuulRoute> routes = new LinkedHashMap<>();

        Object obj = redisTemplate.opsForValue().get(ZuulConstants.CACHE_ROUTE_KEY_SUFFIX);
        if (obj == null) {
            return routes;
        }

        List<ZuulRoute> results = (List<ZuulRoute>) obj;
        for (ZuulRoute result : results) {
            if (StringUtils.isEmpty(result.getPath()) && StringUtils.isEmpty(result.getUrl())) {
                continue;
            }

            ZuulProperties.ZuulRoute zuulRoute = new ZuulProperties.ZuulRoute();
            try {
                zuulRoute.setId(result.getServiceId());
                zuulRoute.setPath(result.getPath());
                zuulRoute.setServiceId(result.getServiceId());
                zuulRoute.setRetryable(StringUtils.equals(result.getRetryable(), "0") ? Boolean.FALSE : Boolean.TRUE);
                zuulRoute.setStripPrefix(StringUtils.equals(result.getStripPrefix(), "0") ? Boolean.FALSE : Boolean.TRUE);
                zuulRoute.setUrl(result.getUrl());
                List<String> sensitiveHeadersList = Arrays.asList(StringUtils.split(result.getSensitiveheadersList(), ","));
                if (sensitiveHeadersList != null) {
                    Set<String> sensitiveHeaderSet = new LinkedHashSet<>();
                    sensitiveHeadersList.forEach(sensitiveHeader -> sensitiveHeaderSet.add(sensitiveHeader));
                    zuulRoute.setSensitiveHeaders(sensitiveHeaderSet);
                    zuulRoute.setCustomSensitiveHeaders(true);
                }
            } catch (Exception e) {
                log.error("Loading Route Configuration Error From Database", e);
            }
            routes.put(zuulRoute.getPath(), zuulRoute);
        }
        return routes;
    }
}
