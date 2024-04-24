package com.ssafy.stellar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class StellarApplication {

    public static void main(String[] args) {
        SpringApplication.run(StellarApplication.class, args);
    }

}

