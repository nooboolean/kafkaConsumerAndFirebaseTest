package com.example.kafkaConsumerAndFirebaseTest.config.firebase.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "spring.firebase")
public class FirebaseProperties {

    private String credential;

    private String database;

}
