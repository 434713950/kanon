package com.github.kanon.upms.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.kanon.common.base.model.vo.BaseVo;
import com.github.tool.common.DateUtil;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/12/5
 */
public class SysZuulRouteVo extends BaseVo {

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
    @DateTimeFormat(pattern = DateUtil.DATE_TIME_FORMAT)
    @JSONField(format = DateUtil.DATE_TIME_FORMAT)
    private Date createTime;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = DateUtil.DATE_TIME_FORMAT)
    @JSONField(format = DateUtil.DATE_TIME_FORMAT)
    private Date updateTime;

    /**
     * 删除标签
     */
    private Boolean delFlag;
}
