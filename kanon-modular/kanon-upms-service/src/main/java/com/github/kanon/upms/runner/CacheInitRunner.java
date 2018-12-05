package com.github.kanon.upms.runner;

import com.github.kanon.upms.service.SysZuulRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * <p>spring启动后缓存初始加载</p>
 *
 * @author PengCheng
 * @date 2018/11/9
 */
@Component
public class CacheInitRunner implements ApplicationRunner {

    @Autowired
    private SysZuulRouteService sysZuulRouteService;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        //路由信息刷入缓存
        sysZuulRouteService.applyZuulRoute();
    }
}
