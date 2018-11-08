package com.github.kanon.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/11/1
 */
@EnableDiscoveryClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class KanonMessageCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(KanonMessageCenterApplication.class);
    }
}
