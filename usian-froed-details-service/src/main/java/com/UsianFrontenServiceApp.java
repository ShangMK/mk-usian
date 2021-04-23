package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.usian.mapper")
public class UsianFrontenServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(UsianFrontenServiceApp.class, args);
    }
}
