package com.github.kanon.upms.cache;

import com.github.kanon.common.constants.CacheConstants;
import com.github.kanon.upms.manager.SysMenuManager;
import com.github.kanon.upms.model.Tree.SysMenuTree;
import com.github.kanon.upms.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/12/13
 */
@Component
public class SysMenuCacheManager {

    @Autowired
    private ISysMenuService sysMenuService;

    /**
     * 强制刷新菜单树缓存信息
     * @return
     */
    @CachePut(value = CacheConstants.KANON_CACHE_GROUP,key = "'"+ CacheConstants.SYS_MENU_TREE_CACHE +"'")
    public List<SysMenuTree> applyWholeTilingMenuTreeCache(){
        //将数据包装成tree返回
        return SysMenuManager.wrapSysMenuToTree(sysMenuService.getWholeMenu());

    }

    /**
     * 获取缓存树数据,如果有缓存则直接返回缓存,否则执行条件后将数据刷入缓存并返回
     * @return
     */
    @Cacheable(value = CacheConstants.KANON_CACHE_GROUP,key = "'"+ CacheConstants.SYS_MENU_TREE_CACHE +"'")
    public List<SysMenuTree> getWholeTilingMenuTreeCache(){
        return applyWholeTilingMenuTreeCache();
    }

    /**
     * 清除菜单树
     */
    @CacheEvict(value= CacheConstants.KANON_CACHE_GROUP,key="'"+  CacheConstants.SYS_MENU_TREE_CACHE+"'")
    public void clearSysMenuCache(){}



}
