package com.github.kanon.upms.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.github.kanon.common.base.model.entity.ZuulRoute;
import com.github.kanon.common.constants.CommonConstant;
import com.github.kanon.common.constants.ZuulConstants;
import com.github.kanon.common.exceptions.ErrorMsgException;
import com.github.kanon.datasource.mybatis.service.MyBatisPlusServiceImpl;
import com.github.kanon.upms.mapper.SysZuulRouteMapper;
import com.github.kanon.upms.model.dto.SysZuulRouteDto;
import com.github.kanon.upms.model.dto.SysZuulRouteQuery;
import com.github.kanon.upms.model.pojo.SysZuulRoute;
import com.github.kanon.upms.service.SysZuulRouteService;
import com.github.kanon.upms.vaildation.SysZuulRouteValidation;
import com.github.tool.common.CollectionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/11/8
 */
@Service
public class SysZuulRouteServiceImpl extends MyBatisPlusServiceImpl<SysZuulRouteMapper,SysZuulRoute> implements SysZuulRouteService {

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    @Cacheable(value = "kanon",key = "'"+ZuulConstants.CACHE_ROUTE_KEY_SUFFIX+"'")
    public List<ZuulRoute> applyZuulRoute(){
        List<ZuulRoute> zuulRoutes = new ArrayList<>();
        List<SysZuulRoute> enableSysZuulRoutes = findAllEnableSysZuulRoute();
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

    @Override
    @CacheEvict(value="kanon",key="'ZuulConstants.CACHE_ROUTE_KEY_SUFFIX'")
    public void clearZuulRouteCache(){}


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
    public Boolean save(SysZuulRouteDto sysZuulRouteDto) {
        SysZuulRoute sysZuulRoute = new SysZuulRoute();
        BeanUtils.copyProperties(sysZuulRouteDto,sysZuulRoute,"id");
        if (CollectionUtil.isNotBlank(sysZuulRouteDto.getSensitiveheaderList())) {
            sysZuulRoute.setSensitiveheadersList(CollectionUtil.join(sysZuulRouteDto.getSensitiveheaderList(), ","));
        }
        return insert(sysZuulRoute);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean modify(SysZuulRouteDto sysZuulRouteDto) {
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
        return updateById(source);
    }


}
