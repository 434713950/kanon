package com.github.kanon.common.base.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: PengCheng
 * @Description: 分页查询基础query
 * @Date: 15:23 2018/4/10/010
 */
@Data
public class PageQuery extends BaseDto {

    @ApiModelProperty(value = "当前页(默认1)")
    private Integer page = 1;

    @ApiModelProperty(value = "每页大小(默认10)")
    private Integer pageSize = 10;

    @ApiModelProperty(value = "是否需要分页(默认需要分页)")
    private Boolean requirePage = true;

    /**
     * 该方式用于获取sql中额limit参数
     * @return
     */
    public Integer getLimitPage(){
        return  (this.page-1)*pageSize;
    }

}
