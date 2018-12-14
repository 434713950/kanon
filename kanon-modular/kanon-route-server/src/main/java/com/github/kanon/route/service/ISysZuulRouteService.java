package com.github.kanon.route.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.github.kanon.common.base.model.entity.ZuulRoute;
import com.github.kanon.common.base.service.IKanonService;
import com.github.kanon.datasource.mybatis.service.MyBatisPlusService;
import com.github.kanon.route.model.dto.SysZuulRouteDto;
import com.github.kanon.route.model.dto.SysZuulRouteQuery;
import com.github.kanon.route.model.pojo.SysZuulRoute;

import java.util.List;


/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/11/8
 */
public interface ISysZuulRouteService extends MyBatisPlusService<SysZuulRoute>, IKanonService {

    /**
     * 查询所有的系统路由信息
     * @return
     */
    List<SysZuulRoute> findAllSysZuulRoute();

    /**
     * 查询所有启用的系统路由信息
     * @return
     */
    List<SysZuulRoute> findAllEnableSysZuulRoute();

    /**
     * 分页查询
     * @param sysZuulRouteQuery 查询条件
     * @return
     */
    Page<SysZuulRoute> queryByPage(SysZuulRouteQuery sysZuulRouteQuery);

    /**
     * 获取单个信息
     * @param id 系统路由id
     * @return
     */
    SysZuulRoute getOne(Long id);

    /**
     * 保存
     * @param sysZuulRouteDto   保存数据
     * @return
     */
    SysZuulRoute save(SysZuulRouteDto sysZuulRouteDto);

    /**
     * 修改
     * @param sysZuulRouteDto   保存数据
     * @return
     */
    SysZuulRoute modify(SysZuulRouteDto sysZuulRouteDto);

    /**
     * 批量移除
     * @param id  路由id
     */
    void remove(Long id);

    /**
     * 批量移除
     * @param ids   路由id
     */
    void removeBatch(List<Long> ids);
}
