package com.github.message.consumer.dingtalk.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/11/5
 */
@Data
@Configuration
@ConditionalOnExpression("!'${dingtalk}'.isEmpty()")
@ConfigurationProperties(prefix = "dingtalk")
public class DingTalkConfig {

    private String cropId;

    private String cropSecret;
}
