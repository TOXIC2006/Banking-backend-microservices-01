package com.banking.offlineupiservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UpiMeshApplication {
    public static void main(String[] args) {
        SpringApplication.run(UpiMeshApplication.class, args);
    }
}
