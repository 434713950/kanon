package com.github.kanon.monitor.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;

import java.util.List;

/**
 * <p>钉钉相关配置</p>
 *
 * @author PengCheng
 * @date 2018/10/31
 */
@Data
@ConditionalOnExpression("!'${dingtalk}'.isEmpty()")
public class MonitorDingTalkPropertiesConfig {

    /**
     * 是否开启钉钉通知
     */
    private Boolean enabled;

    /**
     *  应用id
     */
    private Long agentId;

    /**
     * 需要通知的用户
     */
    private List<String> userIds;
}
