package com.github.kanon.upms.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.kanon.common.constants.CacheConstants;
import com.github.kanon.common.constants.CommonConstant;
import com.github.kanon.common.exceptions.ErrorMsgException;
import com.github.kanon.datasource.mybatis.service.MyBatisPlusServiceImpl;
import com.github.kanon.upms.mapper.SysMenuMapper;
import com.github.kanon.upms.model.Tree.SysMenuTree;
import com.github.kanon.upms.model.dto.SysMenuDto;
import com.github.kanon.upms.model.dto.SysMenuQuery;
import com.github.kanon.upms.model.pojo.SysMenu;
import com.github.kanon.upms.service.SysMenuService;
import com.github.kanon.upms.manager.SysMenuManager;
import com.github.kanon.upms.vaildation.SysMenuValidation;
import com.github.tool.common.CollectionUtil;
import com.github.tool.page.MockPage;
import com.github.tool.page.MockPageUtil;
import com.github.tool.tree.template.PromiscuityTreeTemplate;
import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/12/5
 */
@Service
public class SysMenuServiceImpl extends MyBatisPlusServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    @Cacheable(value = CacheConstants.KANON_CACHE_GROUP,key = "'"+ CacheConstants.SYS_MENU_TREE_CACHE +"'")
    public List<SysMenuTree> getWholeFoldedMenuTree(){
        List<SysMenuTree> sysMenuTreeList = getWholeTilingMenuTree();

        //提取出所有的根节点
        Set<SysMenuTree> rootMenuTreeList = SysMenuManager.obtainRootMenuTrees(sysMenuTreeList);

        if (CollectionUtil.isNotBlank(rootMenuTreeList)) {
            rootMenuTreeList.forEach(rootMenuTree->{
                PromiscuityTreeTemplate<SysMenuTree> template = new PromiscuityTreeTemplate<>();
                template.mountChildrenNode(rootMenuTree,sysMenuTreeList);
            });
        }
        return sysMenuTreeList;
    }

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public List<SysMenuTree> getWholeTilingMenuTree(){
        //查询出所有的菜单数据
        List<SysMenu> menuList = getWholeMenu();

        //将数据包装成tree返回
        return SysMenuManager.wrapSysMenuToTree(menuList);
    }

    @Transactional(readOnly = true,rollbackFor = Exception.class)
    @Cacheable(value = CacheConstants.KANON_CACHE_GROUP,key = "'"+ CacheConstants.SYS_MENU_DATA_CACHE +"'")
    public List<SysMenu> getWholeMenu(){
        return selectList(new EntityWrapper<SysMenu>().eq(CommonConstant.DEL_FLAG,false).orderBy("num"));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value= CacheConstants.KANON_CACHE_GROUP,key="'"+  CacheConstants.SYS_MENU_TREE_CACHE+"'"),
            @CacheEvict(value= CacheConstants.KANON_CACHE_GROUP,key="'"+  CacheConstants.SYS_MENU_DATA_CACHE+"'")
    })
    public void clearSysMenuCache(){}

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysMenu save(SysMenuDto sysMenuDto) {
        //验证编号是否重复
        validParentMenuExist(sysMenuDto.getMenuCode());
        validParentMenuExist(sysMenuDto.getMenuCode());

        SysMenu sysMenu = SysMenuManager.covertDtoToEntity(sysMenuDto);
        sysMenu.setId(null);

        if (insert(sysMenu)){
            clearSysMenuCache();
        }
        return sysMenu;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysMenu modify(SysMenuDto sysMenuDto) {
        //校验对象是否存在
        SysMenu sysMenu = selectById(sysMenuDto.getId());
        if (sysMenu == null) {
            throw new ErrorMsgException(SysMenuValidation.SYS_MENU_NOT_EXIST);
        }

        //验证编号是否重复
        if (!sysMenuDto.getMenuCode().equals(sysMenu.getMenuCode())){
            validCodeDuplicate(sysMenuDto.getMenuCode());
        }

        validParentMenuExist(sysMenuDto.getMenuCode());

        sysMenu = SysMenuManager.covertDtoToEntity(sysMenuDto);
        if (updateById(sysMenu)){
            clearSysMenuCache();
        }

        return sysMenu;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(Long id){
        SysMenu sysMenu = selectById(id);
        if (sysMenu == null) {
           return;
        }

        List<SysMenu> sysMenuChildren = selectList(new EntityWrapper<SysMenu>().eq("menu_p_code",sysMenu.getMenuCode()));
        if (CollectionUtil.isNotBlank(sysMenuChildren)){
            throw new ErrorMsgException(SysMenuValidation.SYS_MENU_CHILDREN_EXIST_NOT_REMOVE,sysMenu.getMenuName());
        }

        //删除成功清除缓存
        if (deleteById(sysMenu)){
            clearSysMenuCache();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeBatch(List<Long> ids){
        List<SysMenu> sysMenus = selectList(new EntityWrapper<SysMenu>().in("id",ids).eq(CommonConstant.DEL_FLAG,false));

        List<String> cannotRemoveMenuName = new ArrayList<>();
        if (CollectionUtil.isNotBlank(sysMenus)){
            sysMenus.stream()
                    .filter(p -> selectCount(new EntityWrapper<SysMenu>().eq("menu_p_code",p.getMenuCode())) > 0)
                    .forEach(p-> cannotRemoveMenuName.add(p.getMenuName()));
        }
        if (CollectionUtil.isNotBlank(cannotRemoveMenuName)){
            throw new ErrorMsgException(SysMenuValidation.SYS_MENU_CHILDREN_EXIST_NOT_REMOVE,CollectionUtil.join(cannotRemoveMenuName,","));
        }

        if(deleteBatchIds(ids)){
            clearSysMenuCache();
        }
    }


    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public MockPage queryByPage(SysMenuQuery sysMenuQuery) {
        return  MockPageUtil.page(getWholeFoldedMenuTree(),sysMenuQuery.getPage(),sysMenuQuery.getPageSize(),false);
    }

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public SysMenu queryByCode(String code){
        return selectOne(new EntityWrapper<SysMenu>().eq("menu_code",code).eq(CommonConstant.DEL_FLAG,false));
    }

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public SysMenu getOne(Long id) {
        return selectOne(new EntityWrapper<SysMenu>().eq("id",id).eq(CommonConstant.DEL_FLAG,false));
    }

    /**
     * 校验父级节点是否存在
     * @param pMenuCode 父菜单code
     */
    private void validParentMenuExist(String pMenuCode){
        if(StringUtils.isNotBlank(pMenuCode)){
            SysMenu pSysMenu = queryByCode(pMenuCode);
            if (pSysMenu == null) {
                throw new ErrorMsgException(SysMenuValidation.SYS_MENU_PARENT_NOT_EXIST);
            }
        }
    }

    /**
     * 校验编号是否重复
     * @param code 菜单code
     */
    private void validCodeDuplicate(String code){
        SysMenu sysMenu = queryByCode(code);
        if (sysMenu != null) {
            throw new ErrorMsgException(SysMenuValidation.SYS_MENU_CODE_DUPLICATE);
        }
    }
}
