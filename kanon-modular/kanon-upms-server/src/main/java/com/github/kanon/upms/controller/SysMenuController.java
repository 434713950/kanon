package com.github.kanon.upms.controller;

import com.github.kanon.common.base.controller.IKanonController;
import com.github.kanon.common.base.model.vo.Pagination;
import com.github.kanon.common.base.model.vo.ResponseParam;
import com.github.kanon.upms.model.Tree.SysMenuTree;
import com.github.kanon.upms.model.dto.SysMenuDto;
import com.github.kanon.upms.model.dto.SysMenuQuery;
import com.github.kanon.upms.model.pojo.SysMenu;
import com.github.kanon.upms.service.SysMenuService;
import com.github.tool.page.MockPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/12/3
 */
@RestController
@RequestMapping("menu")
@Api(description = "系统菜单",tags = "系统菜单")
public class SysMenuController implements IKanonController {

    @Autowired
    private SysMenuService sysMenuService;

    @ApiOperation(value="分页查询",tags="系统菜单")
    @ApiImplicitParam(name = "sysMenuQuery", required = true, dataType = "SysMenuQuery")
    @PostMapping("query")
    public ResponseParam<List<SysMenuTree>> query(@RequestBody SysMenuQuery sysMenuQuery){
        MockPage page = sysMenuService.queryByPage(sysMenuQuery);
        return ResponseParam.success(
                (List<SysMenuTree>)page.getResult(),
                new Pagination(page.getTotal(),page.getCurrentPage(),page.getPageSize()));
    }

    @ApiOperation(value="获取单个详情",tags="系统菜单")
    @ApiImplicitParam(name = "id", required = true, dataType = "Long")
    @GetMapping("view")
    public ResponseParam<SysMenu> view(@RequestParam("id") Long id){
        return ResponseParam.success(sysMenuService.getOne(id));
    }

    @ApiOperation(value="新增",tags="系统菜单")
    @ApiImplicitParam(name = "sysMenuDto", required = true, dataType = "SysMenuDto")
    @PostMapping("save")
    public ResponseParam save(@Validated @RequestBody SysMenuDto sysMenuDto){
        sysMenuService.save(sysMenuDto);
        return ResponseParam.success();
    }

    @ApiOperation(value="修改",tags="系统菜单")
    @ApiImplicitParam(name = "sysMenuDto", required = true, dataType = "SysMenuDto")
    @PostMapping("modify")
    public ResponseParam modify(@Validated @RequestBody SysMenuDto sysMenuDto){
        sysMenuService.modify(sysMenuDto);
        return ResponseParam.success();
    }

    @ApiOperation(value="批量删除",tags="系统菜单")
    @PostMapping("deleteBatch")
    public ResponseParam deleteBatch(@RequestParam("ids") @RequestBody List<Long> ids){
        sysMenuService.removeBatch(ids);
        return ResponseParam.success();
    }

    @ApiOperation(value="删除单个",tags="系统菜单")
    @PostMapping("delete")
    public ResponseParam deleteBatch(@RequestParam("id") @RequestBody Long id){
        sysMenuService.remove(id);
        return ResponseParam.success();
    }
}
