package com.github.kanon.route.cache;

import com.github.kanon.common.base.model.entity.ZuulRoute;
import com.github.kanon.common.constants.CacheConstants;
import com.github.kanon.route.model.pojo.SysZuulRoute;
import com.github.kanon.route.service.ISysZuulRouteService;
import com.github.tool.common.CollectionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/12/13
 */
@Component
public class SysZuulRouteCacheManager {

    @Autowired
    private ISysZuulRouteService sysZuulRouteService;

    @CachePut(value = CacheConstants.KANON_CACHE_GROUP,key = "'"+ CacheConstants.CACHE_ROUTE_KEY_SUFFIX+"'")
    public List<ZuulRoute> applyZuulRoute(){
        List<ZuulRoute> zuulRoutes = new ArrayList<>();
        List<SysZuulRoute> enableSysZuulRoutes = sysZuulRouteService.findAllEnableSysZuulRoute();
        if (CollectionUtil.isNotBlank(enableSysZuulRoutes)){
            enableSysZuulRoutes.forEach(enableSysZuulRoute -> {
                ZuulRoute zuulRoute = new ZuulRoute();
                BeanUtils.copyProperties(enableSysZuulRoute,zuulRoute);

                Set<String> sensitiveheaders = new LinkedHashSet<>();
                String[] sensitiveheaderList = StringUtils.split(enableSysZuulRoute.getSensitiveheadersList(),",");
                if (sensitiveheaderList!=null && sensitiveheaderList.length>0){
                    for (int i=0;i<sensitiveheaderList.length;i++){
                        sensitiveheaders.add(sensitiveheaderList[i]);
                    }
                    zuulRoute.setSensitiveHeaders(sensitiveheaders);
                    zuulRoute.setCustomSensitiveHeaders(true);
                }
                zuulRoutes.add(zuulRoute);
            });
        }
        return zuulRoutes;
    }


    @CacheEvict(value=CacheConstants.KANON_CACHE_GROUP,key="'"+ CacheConstants.CACHE_ROUTE_KEY_SUFFIX+"'")
    public void clearZuulRouteCache(){}

}
