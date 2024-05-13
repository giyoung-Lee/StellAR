package com.ssafy.stellar.settings;

// ApiKeyProvider.java
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ApiKeyProvider {
    @Value("${FIREBASE_API_KEY}")
    private String firebaseApiKey;
}
