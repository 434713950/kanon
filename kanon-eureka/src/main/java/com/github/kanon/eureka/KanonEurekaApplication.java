package com.github.kanon.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/6/13
 */
@EnableEurekaServer
@SpringBootApplication
public class KanonEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(KanonEurekaApplication.class,args);
    }
}
