package com.github.kanon.message.consumer.dingtalk;

import com.github.kanon.common.constants.MqQueueConstant;
import com.github.kanon.common.notify.DingTalkBusinessNotifyTemplate;
import com.github.kanon.message.consumer.dingtalk.handler.DingtalkBusinessNotifyMessageHandler;
import com.github.kanon.message.exception.MessagePushFailException;
import com.github.kanon.message.handler.NotifyMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * <p>服务状态变更钉钉消息队列消费监听器</p>
 *
 * @author PengCheng
 * @date 2018/11/5
 */
@Slf4j
@Component
@RabbitListener(queues = MqQueueConstant.DINGTALK_SERVICE_STATUS_CHANGE)
public class DingTalkServiceChangeListener {

    @Autowired
    private Map<String, NotifyMessageHandler> notifyMessageHandlerMap;

    @RabbitHandler
    public void receive(DingTalkBusinessNotifyTemplate notifyTemplate) {
        long startTime = System.currentTimeMillis();
        DingtalkBusinessNotifyMessageHandler notifyMessageHandler = (DingtalkBusinessNotifyMessageHandler) notifyMessageHandlerMap.get(notifyTemplate.getChannel());
        if (notifyMessageHandler == null) {
            throw new MessagePushFailException("bean get null, having not corresponding notifyMessageHandler,can not send message");
        }
        notifyMessageHandler.excute(notifyTemplate);
        long useTime = System.currentTimeMillis() - startTime;
        log.info("transfer {} message server finish，time consuming {}ms", notifyTemplate.getChannel(), useTime);
    }
}
