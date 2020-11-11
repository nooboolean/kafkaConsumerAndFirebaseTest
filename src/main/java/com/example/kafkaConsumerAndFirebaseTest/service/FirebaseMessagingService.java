package com.example.kafkaConsumerAndFirebaseTest.service;

import com.example.kafkaConsumerAndFirebaseTest.infra.repository.firebase.FirebaseRepository;
import com.example.kafkaConsumerAndFirebaseTest.infra.repository.kafka.entity.PaymentInfoMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FirebaseMessagingService {

    private final FirebaseRepository repository;

    @Autowired
    public FirebaseMessagingService(FirebaseRepository repository) {
        this.repository = repository;
    }

    public void run(PaymentInfoMessage message) {
        this.repository.insertMessage(message);
    }
}
