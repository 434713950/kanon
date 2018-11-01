package com.github.kanon.zipkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import zipkin.server.EnableZipkinServer;

/**
 * <p>链路追踪</p>
 *
 * @author PengCheng
 * @date 2018/10/10
 */
@EnableDiscoveryClient
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
@EnableZipkinServer
public class KanonZipkinElkApplication {

    public static void main(String[] args) {
        SpringApplication.run(KanonZipkinElkApplication.class, args);
    }
}
