package com.github.kanon.common.utils.template;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>短信通道模板</p>
 * 对应在阿里大于中配置的相应模板
 *
 * @author PengCheng
 * @date 2018/10/31
 */
public enum SmsChannelTemplateEnum {

    /**
     * 登录验证
     */
    LOGIN_NAME_LOGIN("loginCodeChannel", "登录验证"),

    /**
     * 服务异常提醒
     */
    SERVICE_STATUS_CHANGE("serviceStatusChange", "Kanon");

    /**
     * 模板名称
     */
    @Getter
    @Setter
    private String template;
    /**
     * 模板签名
     */
    @Getter
    @Setter
    private String signName;

    SmsChannelTemplateEnum(String template, String signName) {
        this.template = template;
        this.signName = signName;
    }
}
