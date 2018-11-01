package com.github.sms.handler;


import com.github.kanon.common.notify.NotifyTemplate;

/**
 * <p>通知消息处理器</p>
 *
 * @author PengCheng
 * @date 2018/11/1
 */
public interface NotifyMessageHandler<T extends NotifyTemplate>  {

    /**
     * 执行
     */
    void excute(T notifyTemplate);

    /**
     * 检测
     * @return
     */
    void check(T notifyTemplate);

    /**
     * 处理
     */
    Boolean process(T notifyTemplate);

    /**
     * 处理成功后需要执行
     * @param notifyTemplate
     */
    void success(T notifyTemplate);

    /**
     * 处理失败需要执行
     * @param notifyTemplate
     */
    void fail(T notifyTemplate);
}
