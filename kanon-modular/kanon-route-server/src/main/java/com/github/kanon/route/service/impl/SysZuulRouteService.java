package com.github.kanon.route.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.github.kanon.common.constants.CommonConstant;
import com.github.kanon.common.exceptions.ErrorMsgException;
import com.github.kanon.datasource.mybatis.service.AbstractMyBatisPlusService;
import com.github.kanon.route.cache.SysZuulRouteCacheManager;
import com.github.kanon.route.mapper.SysZuulRouteMapper;
import com.github.kanon.route.model.dto.SysZuulRouteDto;
import com.github.kanon.route.model.dto.SysZuulRouteQuery;
import com.github.kanon.route.model.pojo.SysZuulRoute;
import com.github.kanon.route.service.ISysZuulRouteService;
import com.github.kanon.route.vaildation.SysZuulRouteValidation;
import com.github.tool.common.CollectionUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/11/8
 */
@Service
public class SysZuulRouteService extends AbstractMyBatisPlusService<SysZuulRouteMapper, SysZuulRoute> implements ISysZuulRouteService {

    @Autowired
    private SysZuulRouteCacheManager sysZuulRouteCacheManager;

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public List<SysZuulRoute> findAllSysZuulRoute(){
        return selectList(new EntityWrapper<SysZuulRoute>()
                .eq(CommonConstant.DEL_FLAG,false)
        );
    }

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public List<SysZuulRoute> findAllEnableSysZuulRoute(){
        return selectList(new EntityWrapper<SysZuulRoute>()
                .eq(CommonConstant.DEL_FLAG,false)
                .eq("enabled",true)
        );
    }

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public Page<SysZuulRoute> queryByPage(SysZuulRouteQuery sysZuulRouteQuery) {
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq(CommonConstant.DEL_FLAG,false);
        if (sysZuulRouteQuery.getRequirePage()){
            Page<SysZuulRoute> page = new Page<>(sysZuulRouteQuery.getPage(),sysZuulRouteQuery.getPageSize());
            return selectPage(page,entityWrapper);
        }else {
            Page<SysZuulRoute> page = new Page<>();
            page.setRecords(selectList(new EntityWrapper<>()));
            return page;
        }
    }

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public SysZuulRoute getOne(Long id) {
        return selectOne(new EntityWrapper<SysZuulRoute>().eq(CommonConstant.DEL_FLAG,false).eq("id",id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysZuulRoute save(SysZuulRouteDto sysZuulRouteDto) {
        SysZuulRoute sysZuulRoute = new SysZuulRoute();
        BeanUtils.copyProperties(sysZuulRouteDto,sysZuulRoute,"id");
        if (CollectionUtil.isNotBlank(sysZuulRouteDto.getSensitiveheaderList())) {
            sysZuulRoute.setSensitiveheadersList(CollectionUtil.join(sysZuulRouteDto.getSensitiveheaderList(), ","));
        }
        if (insert(sysZuulRoute)){
            sysZuulRouteCacheManager.applyZuulRoute();
            return sysZuulRoute;
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysZuulRoute modify(SysZuulRouteDto sysZuulRouteDto) {
        if (sysZuulRouteDto.getId()==null){
            throw new ErrorMsgException(SysZuulRouteValidation.SYS_ZUUL_ROUTE_NOT_SELECT);
        }
        SysZuulRoute source = getOne(sysZuulRouteDto.getId());
        if (source==null){
            throw new ErrorMsgException(SysZuulRouteValidation.SYS_ZUUL_ROUTE_NOT_EXIST);
        }
        BeanUtils.copyProperties(sysZuulRouteDto,source);
        if (CollectionUtil.isNotBlank(sysZuulRouteDto.getSensitiveheaderList())){
            source.setSensitiveheadersList(CollectionUtil.join(sysZuulRouteDto.getSensitiveheaderList(),","));
        }else {
            source.setSensitiveheadersList(null);
        }
        if (updateById(source)){
            sysZuulRouteCacheManager.applyZuulRoute();
            return source;
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(Long id) {
        if (deleteById(id)){
            sysZuulRouteCacheManager.applyZuulRoute();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeBatch(List<Long> ids) {
        if (CollectionUtil.isNotBlank(ids)) {
            if (deleteBatchIds(ids)){
                sysZuulRouteCacheManager.applyZuulRoute();
            }
        }
    }


}
