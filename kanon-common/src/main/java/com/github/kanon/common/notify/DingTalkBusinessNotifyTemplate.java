package com.github.kanon.common.notify;

import lombok.Data;

/**
 * <p>钉钉企业消息通知模板</p>
 *
 * @author PengCheng
 * @date 2018/11/5
 */
@Data
public class DingTalkBusinessNotifyTemplate extends NotifyTemplate{

    /**
     * 信息标题
     */
    private String title;

    /**
     * 信息内容
     */
    private String content;

    /**
     * 需要传达的用户
     */
    private String users;

    /**
     * 使用的消息发送应用id
     */
    private Long agentId;

    public DingTalkBusinessNotifyTemplate(String channel, String title, String content, String users, Long agentId) {
        super(channel);
        this.title = title;
        this.content = content;
        this.users = users;
        this.agentId = agentId;
    }
}
