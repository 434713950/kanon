package com.github.kanon.common.base.config;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.URL;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/12/12
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "swagger2.api")
public class Swagger2Properties {

    private String title = "Kanon Swagger API";

    private String description = "Kanon后台微服务快速开发框架";

    @URL
    private String serviceUrl = "https://github.com/434713950/kanon";

    private String version = "1.0";

    private String contactName = "PC";

    @URL
    private String contactUrl = "https://github.com/434713950/kanon";

    @Email
    private String contactEmail = "434713950@163.com";

}
