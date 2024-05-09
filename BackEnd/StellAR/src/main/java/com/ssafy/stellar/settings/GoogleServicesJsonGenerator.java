package com.ssafy.stellar.settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        // 리소스 파일 경로 지정
        System.out.println("step 1");
        Resource templateResource = new ClassPathResource("google-services-template.json");
        String targetPath = "google-services.json"; // 일반적으로 src/main/resources에 쓰기는 권장하지 않음
        System.out.println("step 2");

        try {
            // 템플릿 파일 읽기
            InputStream inputStream = templateResource.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append(System.lineSeparator());
            }
            String content = stringBuilder.toString();
            System.out.println("step 3");

            // 프로퍼티 값으로 치환
            content = content.replace("${googleApiKey}", apiKeyProvider.getGoogleApiKey());

            // `google-services.json` 파일 생성
            // src/main/resources 디렉토리는 빌드 후 변경할 수 없으므로 다른 위치에 파일을 생성합니다.
            System.out.println("step 4");
            File targetFile = new File(targetPath);
            Files.write(targetFile.toPath(), content.getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("Current working directory: " + new File(".").getAbsolutePath());

            System.out.println("Google services json file has been successfully generated.");
        } catch (IOException e) {
            System.err.println("Failed to generate google services json: " + e.getMessage());
            e.printStackTrace();
        }
    }
}