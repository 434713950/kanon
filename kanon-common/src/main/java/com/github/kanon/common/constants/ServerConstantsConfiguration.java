package com.github.kanon.common.constants;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author: PengCheng
 * @Description:    服务项通用常量
 * @Date: 2018/9/12
 */
@Component
public class ServerConstantsConfiguration {

    /**
     * 认证服务的服务名
     */
    @Value("${server.auth.name}")
    @Getter@Setter
    private String authServerName;

    /**
     * 通用登录服务的服务名
     */
    @Value("${server.upms.name}")
    @Getter@Setter
    private String upmsServerName;
}
