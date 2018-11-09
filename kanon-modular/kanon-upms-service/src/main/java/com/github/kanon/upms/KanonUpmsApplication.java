package com.github.kanon.upms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/11/9
 */
@EnableAsync
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.github.kanon"})
public class KanonUpmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(KanonUpmsApplication.class);
    }
}
