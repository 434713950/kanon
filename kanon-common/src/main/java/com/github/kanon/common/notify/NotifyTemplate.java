package com.github.kanon.common.notify;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>通知模板</p>
 *
 * @author PengCheng
 * @date 2018/11/1
 */
@Data
public abstract class NotifyTemplate implements Serializable {

    /**
     * 消息通道
     */
    private String channel;

    public NotifyTemplate(String channel) {
        this.channel = channel;
    }
}
