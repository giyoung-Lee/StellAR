package com.ssafy.stellar.settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

@Configuration
public class GoogleServicesJsonGenerator {

    private final ApiKeyProvider apiKeyProvider;

    @Autowired
    public GoogleServicesJsonGenerator(ApiKeyProvider apiKeyProvider) {
        this.apiKeyProvider = apiKeyProvider;
    }

    @PostConstruct
    public void generateGoogleServicesJson() {
        System.out.println("Step 1: Starting to generate google services json");
        Resource templateResource = new ClassPathResource("google-services-template.json");

        try {
            System.out.println("Step 2: Reading template file");
            File file = templateResource.getFile(); // 파일 경로 가져오기
            String content = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);

            System.out.println("Step 3: Replacing API key");
            content = content.replace("${googleApiKey}", apiKeyProvider.getGoogleApiKey());

            System.out.println("Step 4: Writing to the same file");
            Files.write(file.toPath(), content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.TRUNCATE_EXISTING);

            System.out.println("Google services json file has been successfully updated.");
        } catch (IOException e) {
            System.err.println("Failed to update google services json: " + e.getMessage());
            e.printStackTrace();
        }
    }
}