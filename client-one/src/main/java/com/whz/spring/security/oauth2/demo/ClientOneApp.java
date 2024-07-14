package com.whz.spring.security.oauth2.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.whz"})
public class ClientOneApp {

    public static void main(String[] args) {
        SpringApplication.run(ClientOneApp.class,args);
    }

}
