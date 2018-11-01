package com.github.kanon.monitor.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <p>监控服务相关的配置</p>
 *
 * @author PengCheng
 * @date 2018/10/31
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "notifier")
public class MonitorPropertiesConfig {

    private MonitorMobileProperties mobile;

    private MonitorDingTalkPropertiesConfig dingTalk;
}
