package com.github.kanon.common.base.model.entity;

import com.github.kanon.common.constants.LogTypeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>日志模板</p>
 *
 * @author PengCheng
 * @date 2018/11/6
 */
@Data
@Accessors(chain = true)
public class Logger {

    /**
     *
     * 日志类型
     */
    private LogTypeEnum logTypeEnum;

    /**
     * 日志内容
     */
    private String message;

    /**
     * 操作人
     */
    private String createBy;

    /**
     * 请求方地址
     */
    private String remoteAddr;

    /**
     * 请求的路径
     */
    private String requestUri;

    /**
     * 请求的方法
     */
    private String method;

    /**
     * 用户代理
     */
    private String userAgent;

    /**
     * 请求的参数
     */
    private String params;

    /**
     * 请求耗费的时间
     */
    private Long time;

    /**
     * 请求的服务id
     */
    private String serviceId;

    /**
     * 接口调用时间
     */
    private Date callDate;
}
