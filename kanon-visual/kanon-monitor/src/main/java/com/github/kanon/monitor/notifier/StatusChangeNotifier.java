package com.github.kanon.monitor.notifier;

import com.github.kanon.common.constants.MessageChannel;
import com.github.kanon.common.constants.MqQueueConstant;
import com.github.kanon.common.notify.DingTalkBusinessNotifyTemplate;
import com.github.kanon.common.notify.SmsNotifyTemplate;
import com.github.kanon.monitor.config.MonitorPropertiesConfig;
import com.github.pcutil.common.CollectionUtil;
import com.github.pcutil.common.DateUtil;
import de.codecentric.boot.admin.event.ClientApplicationEvent;
import de.codecentric.boot.admin.event.ClientApplicationStatusChangedEvent;
import de.codecentric.boot.admin.notify.AbstractStatusChangeNotifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Date;
import java.util.List;

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
    protected void doNotify(ClientApplicationEvent event) {
        if (event instanceof ClientApplicationStatusChangedEvent) {
            log.info("Application {} ({}) is {}", event.getApplication().getName(),
                    event.getApplication().getId(), ((ClientApplicationStatusChangedEvent) event).getTo().getStatus());
            String text = String.format("application:%s server_id:%s status_changed:%s,time:%s"
                    , event.getApplication().getName()
                    , event.getApplication().getId()
                    , ((ClientApplicationStatusChangedEvent) event).getTo().getStatus()
                    , DateUtil.dateTimeFormat(new Date(event.getTimestamp())));

            //开启短信通知
            if (monitorMobilePropertiesConfig.getMobile().getEnabled()) {
                smsNotify(
                        monitorMobilePropertiesConfig.getMobile().getSign(),
                        monitorMobilePropertiesConfig.getMobile().getTemplateCode(),
                        monitorMobilePropertiesConfig.getMobile().getMobiles(),
                        text);
            }

            //开启钉钉通知,由于当前服务为监控服务,必须配置对应的信息接受人,否则不发送钉钉通知
            if (monitorMobilePropertiesConfig.getDingtalk().getEnabled() && CollectionUtil.isNotBlank(monitorMobilePropertiesConfig.getDingtalk().getUserIds())) {
                dingTalkNotify(monitorMobilePropertiesConfig.getDingtalk().getAgentId(),monitorMobilePropertiesConfig.getDingtalk().getUserIds(),text);
            }
        } else {
            log.info("Application {} ({}) {}", event.getApplication().getName(),
                    event.getApplication().getId(), event.getType());
        }
    }


    /**
     * 短信通知
     * @param sign          短信的签名
     * @param templateCode  签名下的短信模板
     * @param mobiles       要通知到的手机号
     * @param content       通知的内容
     */
    private void smsNotify(String sign,String templateCode,List<String> mobiles,String content){
        log.info("message notify start,content:{}", content);
        rabbitTemplate.convertAndSend(MqQueueConstant.SMS_SERVICE_STATUS_CHANGE,
                new SmsNotifyTemplate(
                        CollectionUtil.join(mobiles, ","),
                        content,
                        MessageChannel.ALIYUN_SMS,
                        sign,
                        templateCode
                ));
    }


    /**
     * 发送钉钉通知
     * @param agentId   使用的应用id
     * @param userIds   接收人的钉钉userId
     * @param content   发送的信息
     */
    private void dingTalkNotify(Long agentId, List<String> userIds, String content){
        log.info("dingtalk notify start,content:{}", content);
        rabbitTemplate.convertAndSend(MqQueueConstant.DINGTALK_SERVICE_STATUS_CHANGE,
                new DingTalkBusinessNotifyTemplate(
                        MessageChannel.DINGTALK_WORKING_NOTIFY,
                        "service change notify",
                        content,
                        CollectionUtil.join(userIds,","),
                        agentId
                        )
        );
    }
}
