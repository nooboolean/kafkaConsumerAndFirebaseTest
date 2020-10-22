package com.example.kafkaConsumerAndFirebaseTest.config.firebase.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.firebase")
public class FirebaseProperties {

    @Setter
    @Getter
    private String credential;

    @Setter
    @Getter
    private String database;
}
