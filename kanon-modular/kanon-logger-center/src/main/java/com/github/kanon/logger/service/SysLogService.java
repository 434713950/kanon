package com.github.kanon.logger.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.github.kanon.datasource.mybatis.service.MyBatisPlusService;
import com.github.kanon.logger.model.dto.SysLogQuery;
import com.github.kanon.logger.model.pojo.SysLog;

import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/6/28
 */
public interface SysLogService extends MyBatisPlusService<SysLog> {

    /**
     * 分页查询
     * @param sysLogQuery  条件实体
     * @return
     */
    Page<SysLog> queryByPage(SysLogQuery sysLogQuery);

    /**
     * 删除
     * @param ids   日志id
     */
    void deleteSysLog(List<Long> ids);

    /**
     * 假删
     * @param ids   日志ID
     */
    void mockDeleteSysLog(List<Long> ids);
}
