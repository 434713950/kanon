package com.github.kanon.upms.model.dto;

import com.github.kanon.common.base.model.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/11/8
 */
@Data
@ApiModel(value = "SysZuulRouteDto",description = "新增更新用的系统路由参数")
public class SysZuulRouteDto extends BaseDto {

    @ApiModelProperty(value = "系统路由id")
    private Long id;

    @ApiModelProperty(value = "路由路径")
    private String path;

    @ApiModelProperty(value = "服务名称")
    private String serviceId;

    @ApiModelProperty(value = "url代理")
    private String url;

    @ApiModelProperty(value = "是否转发时去掉前缀")
    private Boolean stripPrefix = false;

    @ApiModelProperty(value = "是否重试")
    private Boolean retryable = false;

    @ApiModelProperty(value = "敏感请求头")
    private List<String> sensitiveheaderList;

    @ApiModelProperty("是否启用")
    private Boolean enabled = true;
}
