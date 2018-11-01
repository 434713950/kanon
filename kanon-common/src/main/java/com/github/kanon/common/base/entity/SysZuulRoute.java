package com.github.kanon.common.base.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import com.github.pcutil.common.DateUtil;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: PengCheng
 * @Description:    路由配置表（参考ZuulProperties.ZuulRoute进行设计）
 * @Date: 2018/9/12
 */
@Data
@TableName("sys_zuul_route")
public class SysZuulRoute extends Model<SysZuulRoute> {

    /**
     * 路由Id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Integer id;

    /**
     * 路由路径
     */
    private String path;
    /**
     * 服务名称
     */
    private String serviceId;

    /**
     * url代理
     */
    private String url;
    /**
     * 转发去掉前缀
     */
    private String stripPrefix;

    /**
     * 是否重试
     */
    private String retryable;
    /**
     * 是否启用
     */
    private String enabled;
    /**
     * 敏感请求头
     */
    private String sensitiveHeaderList;

    /**
     * 创建时间
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    @DateTimeFormat(pattern = DateUtil.DATE_TIME_FORMAT)
    @JSONField(format = DateUtil.DATE_TIME_FORMAT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    @DateTimeFormat(pattern = DateUtil.DATE_TIME_FORMAT)
    @JSONField(format = DateUtil.DATE_TIME_FORMAT)
    private Date updateTime;

    /**
     * 删除标识（0-正常,1-删除）
     */
    private String delFlag;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }


}
