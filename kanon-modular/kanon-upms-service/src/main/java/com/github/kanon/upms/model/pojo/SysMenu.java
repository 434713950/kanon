package com.github.kanon.upms.model.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import com.github.tool.common.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/12/3
 */
@Data
@TableName("sys_menu")
@ApiModel(value = "SysMenu", description = "菜单")
public class SysMenu extends Model<SysMenu> {

    @ApiModelProperty(value = "菜单id")
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "菜单code")
    private String menuCode;

    @ApiModelProperty(value = "父级菜单code")
    private String menuPCode;

    @ApiModelProperty(value = "菜单名")
    private String menuName;

    @ApiModelProperty(value = "菜单名（国际化）")
    private String menuNameI18n;

    @ApiModelProperty(value = "菜单图标")
    private String menuIcon;

    @ApiModelProperty(value = "菜单对应的操作url")
    private String menuUrl;

    @ApiModelProperty(value = "菜单排序")
    private Integer num;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "update_time",fill = FieldFill.INSERT)
    @DateTimeFormat(pattern = DateUtil.DATE_TIME_FORMAT)
    @JSONField(format = DateUtil.DATE_TIME_FORMAT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    @DateTimeFormat(pattern = DateUtil.DATE_TIME_FORMAT)
    @JSONField(format = DateUtil.DATE_TIME_FORMAT)
    private Date updateTime;

    @ApiModelProperty(value = "删除标识")
    private Boolean delFlag;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
