package com.github.kanon.common.constants;

/**
 * <p>通用的mq队列名</p>
 *
 * @author PengCheng
 * @date 2018/10/31
 */
public interface MqQueueConstant {

    /**
     * 短信服务状态队列
     */
    String SMS_SERVICE_STATUS_CHANGE = "notify_sms_service_status_change";

    /**
     * 钉钉服务状态队列
     */
    String DINGTALK_SERVICE_STATUS_CHANGE = "notify_dingtalk_server_status_change";

    /**
     * 日志队列
     */
    String LOG_QUEUE = "kanon_log";
}
