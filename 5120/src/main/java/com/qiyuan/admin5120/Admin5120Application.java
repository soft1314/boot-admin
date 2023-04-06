package com.qiyuan.admin5120;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@EnableAdminServer
@SpringBootApplication
public class Admin5120Application {

    public static void main(String[] args) {
        SpringApplication.run(Admin5120Application.class, args);
    }

}
