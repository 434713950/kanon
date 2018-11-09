package com.github.kanon.upms.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.github.kanon.common.base.controller.BaseController;
import com.github.kanon.common.base.model.vo.Pagination;
import com.github.kanon.common.base.model.vo.ResponseParam;
import com.github.kanon.upms.model.dto.SysZuulRouteDto;
import com.github.kanon.upms.model.dto.SysZuulRouteQuery;
import com.github.kanon.upms.model.pojo.SysZuulRoute;
import com.github.kanon.upms.service.SysZuulRouteService;
import com.github.pcutil.common.CollectionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>系统路由控制器</p>
 * 增、删、改的同时会去同步刷新配置(非一致性)
 *
 * @author PengCheng
 * @date 2018/11/8
 */
@RestController
@RequestMapping("zuulRoute")
@Api(description = "系统路由",tags = "系统路由")
public class SysZuulRouteController extends BaseController {

    @Autowired
    private SysZuulRouteService sysZuulRouteService;

    @ApiOperation(value="分页查询",tags="系统路由")
    @ApiImplicitParam(name = "sysZuulRouteQuery", required = true, dataType = "SysZuulRouteQuery")
    @PostMapping("query")
    public ResponseParam<List<SysZuulRoute>> query(@RequestBody SysZuulRouteQuery sysZuulRouteQuery){
        Page<SysZuulRoute> page = sysZuulRouteService.query(sysZuulRouteQuery);
        return ResponseParam.success(
                page.getRecords(),
                new Pagination(page.getTotal(),page.getCurrent(),page.getSize()));
    }

    @ApiOperation(value="获取单个详情",tags="系统路由")
    @ApiImplicitParam(name = "id", required = true, dataType = "Long")
    @GetMapping("view/{id}")
    public ResponseParam<SysZuulRoute> view(@PathVariable Long id){
        return ResponseParam.success(sysZuulRouteService.getOne(id));
    }

    @ApiOperation(value="新增",tags="系统路由")
    @ApiImplicitParam(name = "sysZuulRouteDto", required = true, dataType = "SysZuulRouteDto")
    @PostMapping("add")
    public ResponseParam<Boolean> add(@Validated @RequestBody SysZuulRouteDto sysZuulRouteDto){
        if (sysZuulRouteService.save(sysZuulRouteDto)){
            sysZuulRouteService.applyZuulRoute();
        }
        return getSuccessAddResult();
    }

    @ApiOperation(value="修改",tags="系统路由")
    @ApiImplicitParam(name = "sysZuulRouteDto", required = true, dataType = "SysZuulRouteDto")
    @PostMapping("modify")
    public ResponseParam<Boolean> modify(@Validated @RequestBody SysZuulRouteDto sysZuulRouteDto){
        if (sysZuulRouteService.modify(sysZuulRouteDto)){
            sysZuulRouteService.applyZuulRoute();
        }
        return getSuccessUpdateResult();
    }

    @ApiOperation(value="批量删除",tags="系统路由")
    @ApiImplicitParam(name = "ids", required = true, dataType = "List")
    @PostMapping("delete")
    public ResponseParam<Boolean> delete(@RequestBody List<Long> ids){
        if (CollectionUtil.isNotBlank(ids)) {
            if (sysZuulRouteService.deleteBatchIds(ids)){
                sysZuulRouteService.applyZuulRoute();
            }
        }
        return getSuccessDeleteResult();
    }

    @ApiOperation(value="应用路由配置(刷新配置)",tags="系统路由")
    @ApiImplicitParam(name = "ids", required = true, dataType = "List")
    @GetMapping("apply")
    public ResponseParam<Boolean> apply(){
        return ResponseParam.success(sysZuulRouteService.applyZuulRoute());
    }
}
