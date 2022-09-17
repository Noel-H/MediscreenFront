package com.noelh.mediscreenfront;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * MediscreenFront Application
 */
@EnableFeignClients
@SpringBootApplication
public class MediscreenFrontApplication {

    public static void main(String[] args) {
        SpringApplication.run(MediscreenFrontApplication.class, args);
    }

}
