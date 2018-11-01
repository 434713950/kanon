package com.github.kanon.common.base.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import com.github.pcutil.common.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: PengCheng
 * @Description:  菜单
 * @Date: 2018/6/21
 */
@Data
@TableName("sys_menu")
@ApiModel(value = "Menu", description = "菜单")
public class Menu extends Model<Menu> {

  @ApiModelProperty(value = "菜单id")
  @TableId(value = "id",type = IdType.ID_WORKER)
  private Long id;

  @ApiModelProperty(value = "菜单code")
  private String menuCode;

  @ApiModelProperty(value = "父级菜单code")
  private String menuPcode;

  @ApiModelProperty(value = "菜单名")
  private String menuName;

  @ApiModelProperty(value = "菜单名（国际化）")
  private String menuNameI18n;

  @ApiModelProperty(value = "菜单图标")
  private String menuIcon;

  @ApiModelProperty(value = "菜单对应的操作url")
  private String menuUrl;

  @ApiModelProperty(value = "菜单对应的后台方法url")
  private String menuMethod;

  @ApiModelProperty(value = "后台方法的请求方式")
  private String methodType;

  @ApiModelProperty(value = "菜单排序")
  private Integer num;

  @ApiModelProperty(value = "类型（0：菜单项,1：操作项）")
  private Integer type;

  @ApiModelProperty(value = "创建时间")
  @TableField(value = "create_time",fill = FieldFill.INSERT)
  @DateTimeFormat(pattern = DateUtil.DATE_TIME_FORMAT)
  @JSONField(format = DateUtil.DATE_TIME_FORMAT)
  private Date createTime;

  @ApiModelProperty(value = "更新时间")
  @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
  @DateTimeFormat(pattern = DateUtil.DATE_TIME_FORMAT)
  @JSONField(format = DateUtil.DATE_TIME_FORMAT)
  private Date updateTime;

  @ApiModelProperty(value = "删除标记")
  private Boolean delFlag;

  @ApiModelProperty(value = "子级菜单")
  @TableField(exist = false)
  private List<Menu> children;

  @Override
  protected Serializable pkVal() {
    return this.id;
  }
}
