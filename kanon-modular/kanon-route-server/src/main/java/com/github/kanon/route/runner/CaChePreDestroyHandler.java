package com.github.kanon.route.runner;

import com.github.kanon.route.service.SysZuulRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * <p>服务停止时的缓存清理处理器</p>
 *
 * @author PengCheng
 * @date 2018/12/12
 */
@Component
public class CaChePreDestroyHandler {

    @Autowired
    private SysZuulRouteService sysZuulRouteService;

    @PreDestroy
    public void destroy(){
        sysZuulRouteService.clearZuulRouteCache();
    }
}
