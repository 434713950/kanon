package com.github.sms.consumer;

import com.github.kanon.common.constants.MqQueueConstant;
import com.github.kanon.common.notify.SmsNotifyTemplate;
import com.github.sms.handler.NotifyMessageHandler;
import com.github.sms.handler.SmsNotifyMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * <p>服务状态变更短信消费发送</p>
 *
 * @author PengCheng
 * @date 2018/11/1
 */
@Slf4j
@Component
@RabbitListener(queues = MqQueueConstant.SMS_SERVICE_STATUS_CHANGE)
public class SmsServiceChangeConsumer {

    @Autowired
    private Map<String, NotifyMessageHandler> notifyMessageHandlerMap;

    @RabbitHandler
    public void receive(SmsNotifyTemplate notifyTemplate) {
        long startTime = System.currentTimeMillis();
        log.info("consumer receive message -> mobile：{} -> message：{} ", notifyTemplate.getMobile(), notifyTemplate.getContext());
        SmsNotifyMessageHandler notifyMessageHandler = (SmsNotifyMessageHandler) notifyMessageHandlerMap.get(notifyTemplate.getType());
        if (notifyMessageHandler == null) {
            log.error("having not corresponding notifyMessageHandler,can not send message");
            return;
        }
        notifyMessageHandler.excute(notifyTemplate);
        long useTime = System.currentTimeMillis() - startTime;
        log.info("transfer {} sms server finish，time consuming {}ms", notifyTemplate.getType(), useTime);
    }
}
