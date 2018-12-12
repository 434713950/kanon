package com.github.kanon.upms.model.pojo;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/12/12
 */
@Data
@TableName("sys_p_operate")
@ApiModel(value = "SysOperate", description = "操作权限")
public class SysOperate {

    @ApiModelProperty(value = "菜单id")
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "操作名")
    private String name;

    @ApiModelProperty(value = "操作服务id")
    private String serviceId;

    @ApiModelProperty(value = "操作服务中的路径")
    private String path;

}
