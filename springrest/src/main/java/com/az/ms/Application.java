package com.az.ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages = "com.az.ms")
@EnableEurekaClient
@EnableDiscoveryClient
public class Application  {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
