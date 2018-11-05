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
     * 短信签名
     */
    private String sign;

    /**
     * 短信模板
     */
    private String template;

    public SmsNotifyTemplate(String mobile, String context, String channel, String sign, String templateCode){
        super(channel);
        this.mobile = mobile;
        this.context = context;
        this.sign = sign;
        this.template = templateCode;
    }
}
