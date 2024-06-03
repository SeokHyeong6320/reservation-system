package com.project.kioskservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class KioskServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(KioskServiceApplication.class, args);
    }

}
