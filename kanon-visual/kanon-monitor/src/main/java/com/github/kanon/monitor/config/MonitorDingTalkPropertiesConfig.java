package com.github.kanon.monitor.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;

/**
 * <p>钉钉相关配置</p>
 *
 * @author PengCheng
 * @date 2018/10/31
 */
@Data
@ConditionalOnExpression("!'${webhook}'.isEmpty()")
public class MonitorDingTalkPropertiesConfig {

    /**
     * 是否开启钉钉通知
     */
    private Boolean enabled;
}
