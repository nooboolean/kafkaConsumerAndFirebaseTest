package com.example.kafkaConsumerAndFirebaseTest.config.firebase;

import com.example.kafkaConsumerAndFirebaseTest.config.firebase.properties.FirebaseProperties;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
@EnableConfigurationProperties(FirebaseProperties.class)
public class CreateFirebaseCredentials {

    private final FirebaseProperties properties;

    private GoogleCredentials credentials;

    public CreateFirebaseCredentials(FirebaseProperties properties) {
        this.properties = properties;
    }

    @PostConstruct
    public synchronized void init() {
        if (this.credentials != null) {
                return;
        }

        try (InputStream uriagekunAccount = new FileInputStream(properties.getCredential())) {
            this.credentials = GoogleCredentials.fromStream(uriagekunAccount);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    @Bean
    public GoogleCredentials getCredentials() {
        return this.credentials;
    }

}
