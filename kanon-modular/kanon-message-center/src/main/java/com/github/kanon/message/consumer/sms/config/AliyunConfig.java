package com.github.kanon.message.consumer.sms.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <p>阿里云服务必备的认证信息</p>
 *
 * @author PengCheng
 * @date 2018/11/1
 */
@Data
@Configuration
@ConditionalOnExpression("!'${aliyun}'.isEmpty()")
@ConfigurationProperties(prefix = "aliyun")
public class AliyunConfig {

    /**
     * 阿里云ak信息
     */
    private String accessKeyId;

    /**
     * 阿里云ak信息
     */
    private String accessKeySecret;
}
