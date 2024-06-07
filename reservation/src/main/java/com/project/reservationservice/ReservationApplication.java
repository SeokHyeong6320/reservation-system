package com.project.reservationservice;

import org.springframework.boot.SpringApplication;

//@EnableJpaAuditing
//@EnableJpaRepositories("com.project")
//@EntityScan("com.project")
//@SpringBootApplication(scanBasePackages = "com.project")
public class ReservationApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReservationApplication.class, args);
    }
}
