package com.github.kanon.common.base.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>白名单配置</p>
 *
 * @author PengCheng
 * @date 2018/8/3
 */
@Data
@Configuration
@RefreshScope
@ConditionalOnExpression("!'${auth.ignore}'.isEmpty()")
@ConfigurationProperties(prefix = "auth.ignore")
public class FilterIgnorePropertiesConfig {
    private List<String> urls = new ArrayList<>();

    private List<String> clients = new ArrayList<>();
}
