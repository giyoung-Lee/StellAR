package com.ssafy.stellar.settings;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class FCMConfig {

    private final ApiKeyProvider apiKeyProvider;

    public FCMConfig(ApiKeyProvider apiKeyProvider) {
        this.apiKeyProvider = apiKeyProvider;
    }

    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException {
        ClassPathResource resource = new ClassPathResource("stellar-e2012-firebase.json");
        InputStream inputStream = resource.getInputStream();

        FirebaseApp firebaseApp = null;
        List<FirebaseApp> firebaseAppList = FirebaseApp.getApps();

        if(firebaseAppList != null && !firebaseAppList.isEmpty()){
            for(FirebaseApp app : firebaseAppList){
                if(app.getName().equals(FirebaseApp.DEFAULT_APP_NAME)){
                    firebaseApp = app;
                }
            }
        } else {
            // JSON 객체로 변환하기
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(new String(inputStream.readAllBytes(), StandardCharsets.UTF_8), JsonObject.class);

            // FIREBASE_API_KEY 추가하기
            jsonObject.addProperty("private_key", apiKeyProvider.getFirebaseApiKey().replace("\\n", "\n"));

            // 변경된 JSON 문자열을 다시 InputStream으로 변환
            String modifiedJson = gson.toJson(jsonObject);
            InputStream modifiedInputStream = new ByteArrayInputStream(modifiedJson.getBytes(StandardCharsets.UTF_8));
            GoogleCredentials googleCredentials = GoogleCredentials.fromStream(modifiedInputStream);

            // FirebaseOptions 설정
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(googleCredentials)
                    .build();
            firebaseApp = FirebaseApp.initializeApp(options);
        }
        // GoogleCredentials 객체 생성
        return FirebaseMessaging.getInstance(firebaseApp);
    }
}
