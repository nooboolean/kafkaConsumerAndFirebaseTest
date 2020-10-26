package com.example.kafkaConsumerAndFirebaseTest;

import com.example.kafkaConsumerAndFirebaseTest.service.FirebaseMessagingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class Consumer {

    private final FirebaseMessagingService service;

    @Autowired
    public Consumer(FirebaseMessagingService service) {
        this.service = service;
    }

    @KafkaListener(topics = "kafkaTest", groupId = "test")
    public void consumer(String message){
        log.info("receivedMessage: #{}", message);
        this.service.run(message);
    }

}
