package com.github.kanon.common.base.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>分页数据包装体</p>
 *
 * @author PengCheng
 * @date 2018/4/13
 */
@Data
@ApiModel(value = "Pagination",description = "返回用的分页实体（不带数据）")
public class Pagination {

    @ApiModelProperty(value = "总数")
    private Integer total;

    @ApiModelProperty(value = "当前页")
    private Integer current;

    @ApiModelProperty(value = "每页展示数")
    private Integer pageSize;

    /**
     *
     * @param total         数据总数
     * @param current       当前页码
     * @param pageSize      每页尺寸
     * @param isIncrement   是否对当前页码进行+1返回
     */
    public Pagination(Integer total, Integer current, Integer pageSize,Boolean isIncrement) {
        this.total = total;
        if (isIncrement) {
            this.current = current + 1;
        }else {
            this.current = current;
        }
        this.pageSize = pageSize;
    }

    public Pagination(Integer total, int current, int size) {
        this(total,current,size,false);
    }
}
