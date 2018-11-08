package com.github.kanon.logger.listener;

import com.github.kanon.common.base.model.entity.Logger;
import com.github.kanon.common.constants.MqQueueConstant;
import com.github.kanon.logger.model.pojo.SysLog;
import com.github.kanon.logger.service.SysLogService;
import org.slf4j.MDC;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>rabbitmq日志队列消费</p>
 *
 * @author pengcheng
 * @date 2018-11-07
 *
 */
@Component
@RabbitListener(queues = MqQueueConstant.LOG_QUEUE)
public class LogReceiveListener {
    private static final String KEY_USER = "user";

    @Autowired
    private SysLogService sysLogService;

    @RabbitHandler
    public void receive(Logger logger) {
        MDC.put(KEY_USER, logger.getCreateBy());
        SysLog sysLog = new SysLog();
        BeanUtils.copyProperties(logger,sysLog);
        sysLogService.insert(sysLog);
        MDC.remove(KEY_USER);
    }
}
