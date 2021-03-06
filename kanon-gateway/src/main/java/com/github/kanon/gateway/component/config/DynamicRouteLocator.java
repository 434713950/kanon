package com.github.kanon.gateway.component.config;

import com.github.kanon.common.base.model.entity.ZuulRoute;
import com.github.kanon.common.constants.CacheConstants;
import com.github.tool.common.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.discovery.DiscoveryClientRouteLocator;

import java.util.*;

/**
 * @Author: PengCheng
 * @Description:    动态路由定位器。DiscoveryClientRouteLocator实现了动态刷新，这里直接改写DiscoveryClientRouteLocator来实现数据库的动态路由
 * @Date: 2018/9/12
 */
@Slf4j
public class DynamicRouteLocator extends DiscoveryClientRouteLocator {

    private ZuulProperties properties;

    private CacheManager cacheManager;

    public DynamicRouteLocator(String servletPath, DiscoveryClient discovery, ZuulProperties properties,
                               ServiceInstance localServiceInstance,  CacheManager cacheManager) {
        super(servletPath, discovery, properties, localServiceInstance);
        this.properties = properties;
        this.cacheManager = cacheManager;
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
        routesMap.putAll(locateRoutesFromCache());

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
    private Map<String, ZuulProperties.ZuulRoute> locateRoutesFromCache() {
        Map<String, ZuulProperties.ZuulRoute> routes = new LinkedHashMap<>();

        List<ZuulRoute> results = loadRouteFromCache();
        if (CollectionUtil.isNotBlank(results)) {
            for (ZuulRoute zuulRoute : results) {
                if (StringUtils.isEmpty(zuulRoute.getPath()) && StringUtils.isEmpty(zuulRoute.getUrl())) {
                    continue;
                }
                ZuulProperties.ZuulRoute res = new ZuulProperties.ZuulRoute();
                res.setId(zuulRoute.getServiceId());
                res.setUrl(zuulRoute.getUrl());
                res.setStripPrefix(zuulRoute.getStripPrefix());
                res.setServiceId(zuulRoute.getServiceId());
                res.setRetryable(zuulRoute.getRetryable());
                res.setPath(zuulRoute.getPath());
                if (CollectionUtil.isNotBlank(zuulRoute.getSensitiveHeaders())) {
                    res.setSensitiveHeaders(zuulRoute.getSensitiveHeaders());
                    res.setCustomSensitiveHeaders(true);
                }
                routes.put(zuulRoute.getPath(), res);
            }
        }
        return routes;
    }

    private List<ZuulRoute> loadRouteFromCache(){
       Cache cache = cacheManager.getCache(CacheConstants.KANON_CACHE_GROUP);
       if (cache == null){
           return null;
       }
       Cache.ValueWrapper  valueWrapper= cache.get(CacheConstants.CACHE_ROUTE_KEY_SUFFIX);
       if (valueWrapper==null) {
           return null;
       }
       return (List<ZuulRoute>) valueWrapper.get();
    }

}
