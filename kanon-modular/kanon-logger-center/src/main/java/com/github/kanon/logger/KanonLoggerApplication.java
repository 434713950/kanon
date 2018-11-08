package com.github.kanon.logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/11/6
 */
@EnableAsync
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.github.kanon"})
public class KanonLoggerApplication {

    public static void main(String[] args) {
        SpringApplication.run(KanonLoggerApplication.class);
    }
}

