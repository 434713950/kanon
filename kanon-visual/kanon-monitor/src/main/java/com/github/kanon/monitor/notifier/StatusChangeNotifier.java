package com.github.kanon.monitor.notifier;

import com.alibaba.fastjson.JSONObject;
import com.github.kanon.common.constants.CommonConstant;
import com.github.kanon.common.constants.MqQueueConstant;
import com.github.kanon.common.utils.template.SmsChannelTemplateEnum;
import com.github.kanon.common.utils.template.SmsNotifyTemplate;
import com.github.kanon.monitor.config.MonitorPropertiesConfig;
import com.github.pcutil.common.CollectionUtil;
import com.github.pcutil.common.DateUtil;
import de.codecentric.boot.admin.event.ClientApplicationEvent;
import de.codecentric.boot.admin.event.ClientApplicationStatusChangedEvent;
import de.codecentric.boot.admin.notify.AbstractStatusChangeNotifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Date;

/**
 * <p>服务状态变动通知</p>
 *
 * @author PengCheng
 * @date 2018/10/31
 */
@Slf4j
public class StatusChangeNotifier extends AbstractStatusChangeNotifier {

    private RabbitTemplate rabbitTemplate;
    private MonitorPropertiesConfig monitorMobilePropertiesConfig;

    public StatusChangeNotifier(MonitorPropertiesConfig monitorMobilePropertiesConfig, RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.monitorMobilePropertiesConfig = monitorMobilePropertiesConfig;
    }


    @Override
    protected void doNotify(ClientApplicationEvent event) throws Exception {
        if (event instanceof ClientApplicationStatusChangedEvent) {
            log.info("Application {} ({}) is {}", event.getApplication().getName(),
                    event.getApplication().getId(), ((ClientApplicationStatusChangedEvent) event).getTo().getStatus());
            String text = String.format("应用:%s 服务ID:%s 状态改变为：%s，时间：%s"
                    , event.getApplication().getName()
                    , event.getApplication().getId()
                    , ((ClientApplicationStatusChangedEvent) event).getTo().getStatus()
                    , DateUtil.dateTimeFormat(new Date(event.getTimestamp())));

            JSONObject contextJson = new JSONObject();
            contextJson.put("name", event.getApplication().getName());
            contextJson.put("seid", event.getApplication().getId());
            contextJson.put("time", DateUtil.dateTimeFormat(new Date(event.getTimestamp())));

            //开启短信通知
            if (monitorMobilePropertiesConfig.getMobile().getEnabled()) {
                log.info("开始短信通知，内容：{}", text);
                rabbitTemplate.convertAndSend(MqQueueConstant.SMS_SERVICE_STATUS_CHANGE,
                        new SmsNotifyTemplate(
                                CollectionUtil.join(monitorMobilePropertiesConfig.getMobile().getMobiles(), ","),
                                contextJson.toJSONString(),
                                CommonConstant.ALIYUN_SMS,
                                SmsChannelTemplateEnum.SERVICE_STATUS_CHANGE
                        ));
            }

            //开启钉钉通知
            if (monitorMobilePropertiesConfig.getDingTalk().getEnabled()) {
                log.info("开始钉钉通知，内容：{}", text);
                rabbitTemplate.convertAndSend(MqQueueConstant.DINGTALK_SERVICE_STATUS_CHANGE, text);
            }
        } else {
            log.info("Application {} ({}) {}", event.getApplication().getName(),
                    event.getApplication().getId(), event.getType());
        }
    }
}
