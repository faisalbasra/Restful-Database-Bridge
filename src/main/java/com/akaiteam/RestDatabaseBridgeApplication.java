package com.akaiteam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class RestDatabaseBridgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestDatabaseBridgeApplication.class, args);
    }
}
