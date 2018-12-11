package com.github.kanon.upms.manager;

import com.github.kanon.common.utils.spring.I18nUtil;
import com.github.kanon.upms.model.Tree.SysMenuTree;
import com.github.kanon.upms.model.dto.SysMenuDto;
import com.github.kanon.upms.model.pojo.SysMenu;
import com.github.tool.common.CollectionUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>系统菜单数据处理器</p>
 *
 * @author PengCheng
 * @date 2018/12/5
 */
public class SysMenuManager {

    /**
     * 从一堆菜单数据中提取根级菜单数据
     * @param menuList
     * @return
     */
    public static Set<SysMenu> obtainRootMenus(List<SysMenu> menuList){
        Set<SysMenu> rootMenus = new LinkedHashSet<>();

        if (CollectionUtil.isNotBlank(menuList)){
            menuList.forEach(sysMenu -> {
                if (StringUtils.isBlank(sysMenu.getMenuPCode())){
                    rootMenus.add(sysMenu);
                }
            });
        }
        return rootMenus;
    }

    /**
     * 从一堆menuTree数据中提取根级菜单数据
     * @param menuTreeList
     * @return
     */
    public static Set<SysMenuTree> obtainRootMenuTrees(List<SysMenuTree> menuTreeList){
        Set<SysMenuTree> rootMenuTrees = new LinkedHashSet<>();

        if (CollectionUtil.isNotBlank(menuTreeList)){
            menuTreeList.forEach(sysMenuTree -> {
                if (StringUtils.isBlank(sysMenuTree.getPcode())){
                    rootMenuTrees.add(sysMenuTree);
                }
            });
        }
        return rootMenuTrees;
    }

    /**
     * 数据包装
     * @param sysMenu
     * @return
     */
    public static SysMenuTree wrapSysMenuToTree(SysMenu sysMenu){
        SysMenuTree sysMenuTree =SysMenuTree.builder()
                .id(sysMenu.getId())
                .menuIcon(sysMenu.getMenuIcon())
                .menuUrl(sysMenu.getMenuUrl())
                .createTime(sysMenu.getCreateTime())
                .updateTime(sysMenu.getUpdateTime()).build();
        //处理国际化名称
        if (StringUtils.isNotEmpty(sysMenu.getMenuNameI18n())){
            sysMenuTree.setMenuName(I18nUtil.i18nParser(sysMenu.getMenuNameI18n()));
        }
        return sysMenuTree;
    }

    /**
     * 数据包装
     * @param sysMenuList
     * @return
     */
    public static List<SysMenuTree> wrapSysMenuToTree(List<SysMenu> sysMenuList){
        List<SysMenuTree> sysMenuTreeList = new ArrayList<>();
        if (CollectionUtil.isNotBlank(sysMenuList)){
            sysMenuList.forEach(sysMenu -> {
                sysMenuTreeList.add(wrapSysMenuToTree(sysMenu));
            });
        }
        return sysMenuTreeList;
    }

    public static SysMenu covertDtoToEntity(SysMenuDto sysMenuDto){
        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(sysMenuDto,sysMenu);
        return sysMenu;
    }
}
