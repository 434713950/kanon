package com.github.kanon.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/6/19
 */
@EnableDiscoveryClient
@EnableConfigServer
@SpringBootApplication
public class kanonConfigApplication {
    public static void main(String[] args) {
        SpringApplication.run(kanonConfigApplication.class,args);
    }
}
