package com.github.kanon.upms.model.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.github.pcutil.common.DateUtil;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>系统路由</p>
 *
 * @author PengCheng
 * @date 2018/11/8
 */
@Data
@TableName("sys_zuul_route")
public class SysZuulRoute extends Model<SysZuulRoute> {

    /**
     * router Id
     */
    @TableId
    private Long id;

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
     * 是否转发去掉前缀
     */
    private Boolean stripPrefix;

    /**
     * 是否重试
     */
    private Boolean retryable;

    /**
     * 敏感请求头
     */
    private String sensitiveheadersList;

    /**
     * 是否启用
     */
    private Boolean enabled;

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
     * 删除标签
     */
    private Boolean delFlag;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
