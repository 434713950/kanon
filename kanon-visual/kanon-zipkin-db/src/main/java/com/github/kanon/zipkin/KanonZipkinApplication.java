package com.github.kanon.zipkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import zipkin.server.EnableZipkinServer;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/10/10
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@EnableZipkinServer
public class KanonZipkinApplication {

    public static void main(String[] args) {
        SpringApplication.run(KanonZipkinApplication.class, args);
    }
}
