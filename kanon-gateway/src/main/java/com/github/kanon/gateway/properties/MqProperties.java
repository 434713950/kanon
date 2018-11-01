package com.github.kanon.gateway.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/9/13
 */
@Data
@Component
@RefreshScope
public class MqProperties {

    /**
     * mq的日志队列
     */
    @Value("${mq.queue.log:kanon_log}")
    private String mqQueueLog;

}
