package com.github.kanon.gateway.service;

import com.netflix.zuul.context.RequestContext;

/**
 * @Author: PengCheng
 * @Description:  日志发送服务
 * @Date: 2018/9/12
 */
public interface LogSendService {
    /**
     * 往消息通道发消息
     *
     * @param requestContext requestContext
     */
    void send(RequestContext requestContext);
}
