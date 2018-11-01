package com.github.kanon.common.notify;

import lombok.Data;

/**
 * <p>短信通知用的模板bean</p>
 *
 * @author PengCheng
 * @date 2018/10/31
 */
@Data
public class SmsNotifyTemplate extends NotifyTemplate{

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 组装后的模板内容JSON字符串
     */
    private String context;

    /**
     * 短信通道
     */
    private String channel;

    /**
     * 短信类型(验证码或者通知短信)
     * 暂时不用，留着后面存数据库备用吧
     */
    private String type;

    /**
     * 短信签名
     */
    private String signName;

    /**
     * 短信模板
     */
    private String template;

    public SmsNotifyTemplate(String mobile, String context, String channel, String sign, String templateCode){
        this.mobile = mobile;
        this.context = context;
        this.channel = channel;
        this.signName = sign;
        this.template = templateCode;
    }
}
