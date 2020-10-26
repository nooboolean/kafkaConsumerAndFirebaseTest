package com.example.kafkaConsumerAndFirebaseTest.service;

import com.example.kafkaConsumerAndFirebaseTest.infra.repository.FirebaseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FirebaseMessagingService {
    private final FirebaseRepository repository;

    @Autowired
    public FirebaseMessagingService(FirebaseRepository repository) {
        this.repository = repository;
    }

    public void run(String message) {
        this.repository.insertMessage(message);
    }
}
