package com.github.kanon.common.base.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: PengCheng
 * @Description: 分页查询基础query
 * @Date: 15:23 2018/4/10/010
 */
public class PageQuery extends BaseDto {

    @Setter
    @ApiModelProperty(value = "当前页（默认1）")
    private Integer page = 1;


    @Setter@Getter
    @ApiModelProperty(value = "每页大小（默认10）")
    private Integer pageSize = 10;

    public Integer getPage() {
        return page-1;
    }
}
