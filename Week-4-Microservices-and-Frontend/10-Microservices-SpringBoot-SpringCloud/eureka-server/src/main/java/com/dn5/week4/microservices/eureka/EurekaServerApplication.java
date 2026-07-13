package com.dn5.week4.microservices.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Standalone Eureka service registry.
 *
 * All other services (product-service, order-service, api-gateway) register
 * themselves with this server on startup and discover each other through it.
 * Start this application FIRST, before any other module in this project.
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
