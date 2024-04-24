package com.ssafy.stellar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude= {SecurityAutoConfiguration.class})
public class StellarApplication {

    public static void main(String[] args) {
        SpringApplication.run(StellarApplication.class, args);
    }

}

