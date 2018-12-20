package com.github.kanon.message.consumer.sms.handler;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.github.kanon.common.constants.MessageChannel;
import com.github.kanon.common.notify.SmsNotifyTemplate;
import com.github.kanon.message.exception.MessagePushFailException;
import com.github.kanon.message.handler.AbstractNotifyMessageHandler;
import com.github.kanon.message.consumer.sms.config.AliyunConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/11/1
 */
@Slf4j
@Component(MessageChannel.ALIYUN_SMS)
@ConditionalOnExpression("!'${aliyun}'.isEmpty() && '${aliyun.enabled:true}'")
public class SmsNotifyMessageHandler extends AbstractNotifyMessageHandler<SmsNotifyTemplate> {

    /**
     * 产品名称
     */
    static final String PORDUCT = "Dysmsapi";

    /**
     *  产品域名
     */
    static final String DOMAIN = "dysmsapi.aliyuncs.com";

    @Autowired
    private AliyunConfig aliyunConfig;

    @Override
    public void check(SmsNotifyTemplate notifyTemplate) {
        if (StringUtils.isEmpty(notifyTemplate.getMobile())){
            throw new MessagePushFailException("Lack of cell phone number!");
        }
        if (StringUtils.isEmpty(notifyTemplate.getContext())){
            throw new MessagePushFailException("Lack of message context!");
        }
    }

    @Override
    public Boolean process(SmsNotifyTemplate notifyTemplate) {
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", aliyunConfig.getAccessKeyId(), aliyunConfig.getAccessKeySecret());
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", PORDUCT, DOMAIN);
        } catch (ClientException e) {
            throw new MessagePushFailException("init message sdk error",e);
        }
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(notifyTemplate.getMobile());
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(notifyTemplate.getSign());
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(notifyTemplate.getTemplate());
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam("{\"notify\":\""+notifyTemplate.getContext()+"\"}");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId(notifyTemplate.getMobile());

        try {
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            log.info("message send finish! mobile:{},response status:{},response msg:{}",notifyTemplate.getMobile(),sendSmsResponse.getCode(),sendSmsResponse.getMessage());
        } catch (ClientException e) {
            throw new MessagePushFailException("message send error",e);
        }
        return true;
    }

    @Override
    public void fail(SmsNotifyTemplate notifyTemplate) {
        log.error("message send fail -> direct:{} -> mobile:{}",notifyTemplate.getChannel(),notifyTemplate.getMobile());
    }
}
