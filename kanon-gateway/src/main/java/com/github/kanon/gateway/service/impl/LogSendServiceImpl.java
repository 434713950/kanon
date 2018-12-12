package com.github.kanon.gateway.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.kanon.common.base.model.entity.Logger;
import com.github.kanon.common.constants.CommonConstant;
import com.github.kanon.common.constants.LogTypeEnum;
import com.github.kanon.common.constants.MqQueueConstant;
import com.github.kanon.common.utils.net.HttpUtil;
import com.github.kanon.common.utils.net.URLUtil;
import com.github.kanon.common.utils.system.AuthUtils;
import com.github.kanon.gateway.service.LogSendService;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * @Author: PengCheng
 * @Description:  日志发送服务
 * @Date: 2018/9/12
 */
@Slf4j
@Component
public class LogSendServiceImpl implements LogSendService {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    /**
     * 1. 获取 requestContext 中的请求信息
     * 2. 如果返回状态不是OK，则获取返回信息中的错误信息
     * 3. 发送到MQ
     * @param requestContext 上下文对象
     */
    @Override
    @RefreshScope
    public void send(RequestContext requestContext) {
        Logger logger = generateBasicLog(requestContext);

        //响应异常处理
        if (requestContext.getResponseStatusCode() == HttpStatus.SC_INTERNAL_SERVER_ERROR
                && requestContext.getResponseDataStream() != null) {
            InputStream inputStream = requestContext.getResponseDataStream();

            try {
                //从流中获取信息
                String resp = IOUtils.toString(inputStream,CommonConstant.CONTENT_ENCODE);
                //设置日志数据
                logger.setLogTypeEnum(LogTypeEnum.ABNORMAL);
                logger.setMessage(resp);
            } catch (IOException e) {
                log.error("response stream analysis error: ", e);
                throw new RuntimeException(e);
            }
        }

        //网关内部异常
        Throwable throwable = requestContext.getThrowable();
        if (throwable != null) {
            log.error("gateway anomaly: ", throwable);
            logger.setMessage(throwable.getMessage());
        }

        //保存发往MQ
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && StringUtils.isNotEmpty(authentication.getName())) {
            logger.setCreateBy(authentication.getName());
            rabbitTemplate.convertAndSend(MqQueueConstant.LOG_QUEUE, logger);
        }
    }

    /**
     * 生成基本的日志信息
     * @param requestContext
     * @return
     */
    private Logger generateBasicLog(RequestContext requestContext){
        HttpServletRequest request = requestContext.getRequest();
        String requestUri = request.getRequestURI();
        String method = request.getMethod();
        Long startTime = (Long) requestContext.get("startTime");

        Logger logger = new Logger().setLogTypeEnum(LogTypeEnum.COMMON)
                .setRemoteAddr(HttpUtil.getRequestIPAddress(request))
                .setRequestUri(URLUtil.getPath(requestUri))
                .setMethod(method)
                .setUserAgent(request.getHeader("user-agent"))
                .setParams(JSON.toJSONString(request.getParameterMap()))
                .setTime(System.currentTimeMillis() - startTime)
                .setCallDate(new Date(startTime));

        Object serverId = requestContext.get(AuthUtils.SERVER_ID);
        if ( serverId!= null) {
            logger.setServiceId(serverId.toString());
        }
        return logger;
    }

}
