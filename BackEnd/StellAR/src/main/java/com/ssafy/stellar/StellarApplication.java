package com.ssafy.stellar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
<<<<<<< Updated upstream
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
=======
import org.springframework.scheduling.annotation.EnableScheduling;
>>>>>>> Stashed changes

@EnableScheduling
@SpringBootApplication(exclude= {SecurityAutoConfiguration.class})
@EnableScheduling
public class StellarApplication {
    public static void main(String[] args) {
        SpringApplication.run(StellarApplication.class, args);
    }
}

