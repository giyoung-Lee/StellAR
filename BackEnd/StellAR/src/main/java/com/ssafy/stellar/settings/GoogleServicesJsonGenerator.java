package com.ssafy.stellar.settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
public class GoogleServicesJsonGenerator {

    private final ApiKeyProvider apiKeyProvider;

    @Autowired
    public GoogleServicesJsonGenerator(ApiKeyProvider apiKeyProvider) {
        this.apiKeyProvider = apiKeyProvider;
    }

    @PostConstruct
    public void generateGoogleServicesJson() throws IOException {
        // 템플릿 파일 경로와 대상 파일 경로 지정
        String templatePath = "src/main/resources/google-services-template.json";
        String targetPath = "src/main/resources/google-services.json";
        System.out.println("됐냐?");

        // 템플릿 파일 읽기
        String content = new String(Files.readAllBytes(Paths.get(templatePath)));

        // 프로퍼티 값으로 치환
        content = content.replace("${googleApiKey}", apiKeyProvider.getGoogleApiKey());

        // `google-services.json` 파일 생성
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(targetPath))) {
            writer.write(content);
            System.out.println("됐냐?");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}