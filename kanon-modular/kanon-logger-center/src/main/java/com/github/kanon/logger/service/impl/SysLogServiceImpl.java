package com.github.kanon.logger.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.github.kanon.datasource.mybatis.service.MyBatisPlusServiceImpl;
import com.github.kanon.logger.mapper.SysLogMapper;
import com.github.kanon.logger.model.dto.SysLogQuery;
import com.github.kanon.logger.model.pojo.SysLog;
import com.github.kanon.logger.service.SysLogService;
import com.github.pcutil.common.CollectionUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/6/28
 */
@Service
public class SysLogServiceImpl extends MyBatisPlusServiceImpl<SysLogMapper,SysLog> implements SysLogService {


    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public Page<SysLog> queryByPage(SysLogQuery sysLogQuery) {
        EntityWrapper<SysLog> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("del_flag",false);
        entityWrapper.orderBy("create_time",false);
        if (sysLogQuery.getCreateStartTime() != null){
            entityWrapper.ge("create_time",sysLogQuery.getCreateStartTime());
        }
        if (sysLogQuery.getCreateEndTime() != null){
            entityWrapper.le("create_time",sysLogQuery.getCreateEndTime());
        }
        Page<SysLog> logPage = selectPage(
                new Page<>(sysLogQuery.getPage(),sysLogQuery.getPageSize()),
                entityWrapper
        );
        logPage.setTotal(selectCount(entityWrapper));
        return logPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSysLog(List<Long> ids){
        if (CollectionUtil.isNotBlank(ids)) {
            deleteBatchIds(ids);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void mockDeleteSysLog(List<Long> ids){
        if (CollectionUtil.isNotBlank(ids)) {
            List<SysLog> logs = selectBatchIds(ids);
            if (CollectionUtil.isNotBlank(logs)){
                logs.forEach(log -> {
                    log.setDelFlag(true);
                });
                updateBatchById(logs);
            }
        }
    }
}
