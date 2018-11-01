package com.github.kanon.gateway.component.config;

import com.github.kanon.common.constants.ServerConstantsConfiguration;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;


/**
 * @Author: PengCheng
 * @Description:    swagger服务资源配置
 * @Date: 2018/9/12
 */
@Component
@Primary
public class RegistrySwaggerResourcesProvider implements SwaggerResourcesProvider {

    @Autowired
    private ServerConstantsConfiguration serverConstantsConfiguration;

    /**
     * 当前系统加载的路由
     */
    private final RouteLocator routeLocator;

    public RegistrySwaggerResourcesProvider(RouteLocator routeLocator) {
        this.routeLocator = routeLocator;
    }

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();

        List<Route> routes = routeLocator.getRoutes();
        routes.forEach(route -> {
            //认证服务不维护到swagger
            if (!StringUtils.contains(route.getId(), serverConstantsConfiguration.getAuthServerName())){
                resources.add(swaggerResource(route.getId(), route.getFullPath().replace("**", "v2/api-docs")));
            }
        });
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }
}