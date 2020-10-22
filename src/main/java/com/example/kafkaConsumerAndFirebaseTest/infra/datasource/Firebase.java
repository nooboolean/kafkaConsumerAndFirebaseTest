package com.example.kafkaConsumerAndFirebaseTest.infra.datasource;

import com.example.kafkaConsumerAndFirebaseTest.config.firebase.properties.FirebaseProperties;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Firebase {

    private final FirebaseProperties properties;

    private final GoogleCredentials credentials;

    private FirebaseApp app;

    @Autowired
    public Firebase(FirebaseProperties properties, GoogleCredentials credentials) {
        this.properties = properties;
        this.credentials = credentials;
    }

    @PostConstruct
    public synchronized void init () {
        if (app != null) {
            return;
        }

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(this.credentials)
                .setDatabaseUrl(this.properties.getDatabase())
                .build();
        this.app = FirebaseApp.initializeApp(options);
    }

    @Bean
    @Scope("prototype")
    public FirebaseDatabase getDatabaseClient() {
        return FirebaseDatabase.getInstance(this.app);
    }

}
