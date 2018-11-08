package com.github.kanon.logger.model.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.github.pcutil.common.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/6/27
 */
@Data
@TableName("sys_log")
@ApiModel(value = "SysLog",description = "系统日志")
public class SysLog extends Model<SysLog> {

    @TableId(value = "id",type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty("日志类型")
    private String logType;

    @ApiModelProperty("操作账号")
    private String createBy;

    @ApiModelProperty("信息")
    private String message;

    @ApiModelProperty("请求方地址")
    private String remoteAddr;

    @ApiModelProperty("请求的路径")
    private String requestUri;

    @ApiModelProperty("请求的方法")
    private String method;

    @ApiModelProperty("请求代理")
    private String userAgent;

    @ApiModelProperty("请求的参数")
    private String params;

    @ApiModelProperty("请求耗费的时间")
    private Long time;

    @ApiModelProperty("请求的服务id")
    private String serviceId;

    @ApiModelProperty("假删标志")
    private Boolean delFlag;

    @ApiModelProperty("接口调用时间")
    @DateTimeFormat(pattern = DateUtil.DATE_TIME_FORMAT)
    @JSONField(format = DateUtil.DATE_TIME_FORMAT)
    private Date callDate;



    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
