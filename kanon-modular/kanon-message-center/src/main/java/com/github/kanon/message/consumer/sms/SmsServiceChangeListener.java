package com.github.kanon.message.consumer.sms;

import com.github.kanon.common.constants.MqQueueConstant;
import com.github.kanon.common.notify.SmsNotifyTemplate;
import com.github.kanon.message.consumer.sms.handler.SmsNotifyMessageHandler;
import com.github.kanon.message.exception.MessagePushFailException;
import com.github.kanon.message.handler.NotifyMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * <p>服务状态变更短信消息队列消费监听器</p>
 *
 * @author PengCheng
 * @date 2018/11/1
 */
@Slf4j
@Component
@RabbitListener(queues = MqQueueConstant.SMS_SERVICE_STATUS_CHANGE)
@ConditionalOnExpression("!'${aliyun}'.isEmpty() && '${aliyun.enabled:true}'")
public class SmsServiceChangeListener {

    @Autowired
    private Map<String, NotifyMessageHandler> notifyMessageHandlerMap;

    @RabbitHandler
    public void receive(SmsNotifyTemplate notifyTemplate) {
        long startTime = System.currentTimeMillis();
        log.info("listener receive message -> mobile：{} -> message：{} ", notifyTemplate.getMobile(), notifyTemplate.getContext());
        SmsNotifyMessageHandler notifyMessageHandler = (SmsNotifyMessageHandler) notifyMessageHandlerMap.get(notifyTemplate.getChannel());
        if (notifyMessageHandler == null) {
            throw new MessagePushFailException("bean get null, having not corresponding notifyMessageHandler,can not send message");
        }
        notifyMessageHandler.excute(notifyTemplate);
        long useTime = System.currentTimeMillis() - startTime;
        log.info("transfer {} message server finish，time consuming {}ms", notifyTemplate.getChannel(), useTime);
    }
}
