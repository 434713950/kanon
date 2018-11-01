package com.github.kanon.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: PengCheng
 * @Description: 获取用户信息也是通过这个应用实现,这里既是认证服务器，也是资源服务器
 * @Date: 2018/6/20
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(basePackages = {"com.pc.kanon.auth", "com.pc.kanon.common"})
public class KanonAuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(KanonAuthServerApplication.class, args);
    }

}
