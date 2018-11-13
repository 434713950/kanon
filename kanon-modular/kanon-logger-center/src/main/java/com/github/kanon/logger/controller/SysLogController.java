package com.github.kanon.logger.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.github.kanon.common.base.controller.BaseController;
import com.github.kanon.common.base.model.vo.Pagination;
import com.github.kanon.common.base.model.vo.ResponseParam;
import com.github.kanon.logger.model.dto.SysLogQuery;
import com.github.kanon.logger.model.pojo.SysLog;
import com.github.kanon.logger.service.SysLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/6/28
 */
@Api(description = "系统日志", tags = "系统日志")
@RestController
@RequestMapping("/log")
public class SysLogController extends BaseController {

    @Autowired
    private SysLogService sysLogService;

    @ApiOperation(value = "分页查询",tags = "系统日志")
    @ApiImplicitParam(name = "loggerQuery",value = "系统日志分页查询实体", required = true, dataType = "LoggerQuery")
    @PostMapping(value = "/find")
    public ResponseParam<List<SysLog>> findByPage(@RequestBody SysLogQuery sysLogQuery) {
        Page<SysLog> logPage = sysLogService.queryByPage(sysLogQuery);
        Pagination pagination = new Pagination(logPage.getTotal(),logPage.getCurrent(),logPage.getSize());
        return ResponseParam.success(logPage.getRecords(),pagination);
    }

    @ApiOperation(value = "批量删除",tags = "系统日志")
    @PostMapping(value = "/delete")
    public ResponseParam delete(@RequestParam("ids") @RequestBody List<Long> ids){
        sysLogService.deleteSysLog(ids);
        return getSuccessDeleteResult();
    }
}
