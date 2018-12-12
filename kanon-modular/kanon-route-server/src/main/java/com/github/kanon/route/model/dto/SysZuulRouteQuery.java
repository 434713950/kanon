package com.github.kanon.route.model.dto;

import com.github.kanon.common.base.model.dto.PageQuery;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/11/8
 */
@Data
@ApiModel(value = "SysZuulRouteQuery",description = "系统路由分页查询条件")
public class SysZuulRouteQuery extends PageQuery {
}
