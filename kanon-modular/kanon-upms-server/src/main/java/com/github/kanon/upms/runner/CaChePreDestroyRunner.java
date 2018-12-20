package com.github.kanon.upms.runner;

import com.github.kanon.upms.cache.SysMenuCacheManager;
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
public class CaChePreDestroyRunner {

    @Autowired
    private SysMenuCacheManager sysMenuCacheManager;

    @PreDestroy
    public void destroy(){
        sysMenuCacheManager.clearSysMenuCache();
    }
}
