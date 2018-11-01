package com.github.kanon.common.base.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import com.github.pcutil.common.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: PengCheng
 * @Description:  日志表
 * @Date: 2018/6/21
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "sys_log",description = "日志")
public class SysLog extends Model<SysLog> {

    /**
     * 编号
     */
    @TableId(type = IdType.ID_WORKER)
    private Long id;
    /**
     * 日志类型
     */
    @ApiModelProperty(value = "日志类型")
    private Integer type;
    /**
     * 日志标题
     */
    @ApiModelProperty(value = "日志标题")
    private String title;
    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    private String createBy;

    /**
     * 操作IP地址
     */
    @ApiModelProperty("操作IP地址")
    private String remoteAddr;

    /**
     * 用户代理
     */
    @ApiModelProperty("用户代理")
    private String userAgent;

    /**
     * 请求URI
     */
    @ApiModelProperty("请求URI")
    private String requestUri;

    /**
     * 操作接口
     */
    @ApiModelProperty("操作接口")
    private String method;

    /**
     * 操作提交的数据
     */
    @ApiModelProperty("操作提交的数据")
    private String params;

    /**
     * 执行时间
     */
    @ApiModelProperty("执行时长")
    private Long time;

    /**
     * 删除标记
     */
    @ApiModelProperty("删除标记")
    private String delFlag;

    /**
     * 异常信息
     */
    @ApiModelProperty("异常信息")
    private String exception;

    /**
     * 服务ID
     */
    @ApiModelProperty("服务ID")
    private String serviceId;

    /**
     * 创建时间
     */
    @ApiModelProperty("建立时间")
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    @DateTimeFormat(pattern = DateUtil.DATE_TIME_FORMAT)
    @JSONField(format = DateUtil.DATE_TIME_FORMAT)
    private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    @TableField(value = "create_time",fill = FieldFill.INSERT_UPDATE)
    @DateTimeFormat(pattern = DateUtil.DATE_TIME_FORMAT)
    @JSONField(format = DateUtil.DATE_TIME_FORMAT)
    private Date updateTime;

    @Override
    public String toString() {
        return "SysLog{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", remoteAddr='" + remoteAddr + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", requestUri='" + requestUri + '\'' +
                ", method='" + method + '\'' +
                ", params='" + params + '\'' +
                ", time=" + time +
                ", delFlag='" + delFlag + '\'' +
                ", exception='" + exception + '\'' +
                '}';
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
