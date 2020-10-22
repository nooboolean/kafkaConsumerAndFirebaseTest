package com.example.kafkaConsumerAndFirebaseTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;

@Service
public class Consumer {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @KafkaListener(topics = "kafkaTest", groupId = "test")
    public void consumer(String message){
        log.info("receivedMessage: #{}", message);
    }

}
