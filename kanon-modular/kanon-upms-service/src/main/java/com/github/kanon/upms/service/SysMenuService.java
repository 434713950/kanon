package com.github.kanon.upms.service;

import com.github.kanon.datasource.mybatis.service.MyBatisPlusService;
import com.github.kanon.upms.model.Tree.SysMenuTree;
import com.github.kanon.upms.model.dto.SysMenuDto;
import com.github.kanon.upms.model.dto.SysMenuQuery;
import com.github.kanon.upms.model.pojo.SysMenu;
import com.github.tool.page.MockPage;

import java.util.List;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/12/5
 */
public interface SysMenuService extends MyBatisPlusService<SysMenu> {

    /**
     * 获取完整的折叠菜单树
     * @return List<SysMenuTree>
     */
    List<SysMenuTree> getWholeFoldedMenuTree();

    /**
     * 获取完整的平铺菜单树
     * @return List<SysMenuTree>
     */
    List<SysMenuTree> getWholeTilingMenuTree();


    /**
     * 清除菜单相关的所有缓存
     */
    void clearSysMenuCache();

    /**
     * 保存
     * @return SysMenu
     */
    SysMenu save(SysMenuDto sysMenuDto);

    /**
     * 修改
     * @return SysMenu
     */
    SysMenu modify(SysMenuDto sysMenuDto);

    /**
     * 单个移除
     * @param id    菜单id
     */
    void remove(Long id);

    /**
     * 批量移除
     * @param ids   菜单id
     */
    void removeBatch(List<Long> ids);

    /**
     * 分页查询
     * @param sysMenuQuery  查询条件
     * @return
     */
    MockPage queryByPage(SysMenuQuery sysMenuQuery);

    /**
     * 通过编号查询
     * @param code  菜单编号
     * @return
     */
    SysMenu queryByCode(String code);

    /**
     * 查询单个
     * @param id
     * @return
     */
    SysMenu getOne(Long id);
}
