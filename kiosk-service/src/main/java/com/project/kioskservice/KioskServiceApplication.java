package com.project.kioskservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("com.project")
@EnableJpaAuditing
@EnableJpaRepositories("com.project")
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.project")
public class KioskServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(KioskServiceApplication.class, args);
    }

}
