package com.example.kafkaConsumerAndFirebaseTest.infra.repository.kafka.runner;

import com.example.kafkaConsumerAndFirebaseTest.config.kafka.properties.KafkaProperties;
import com.example.kafkaConsumerAndFirebaseTest.infra.repository.kafka.entity.PaymentInfoMessage;
import com.example.kafkaConsumerAndFirebaseTest.service.FirebaseMessagingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.time.Instant;


@Service
@Slf4j
public class KafkaConsumer {

    private final FirebaseMessagingService service;
    private final KafkaProperties properties;

    @Autowired
    public KafkaConsumer(FirebaseMessagingService service, KafkaProperties properties) {
        this.service = service;
        this.properties = properties;
    }

    @KafkaListener(
            topics = "${spring.kafka.template.default-topic}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumer(
            PaymentInfoMessage message,
            @Header(
                    name = KafkaHeaders.RECEIVED_MESSAGE_KEY,
                    required = false,
                    defaultValue = "undefined"
            ) String key,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
            @Header(KafkaHeaders.OFFSET) String offset,
            @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long timestamp,
            @Header(KafkaHeaders.TIMESTAMP_TYPE) String timestampType
            )
    {
        log.info(
                "key = {}, topic = {}, partition = {}, offset = {}, timestamp = {}, timestampType = {}, Received " +
                        "message = {}",
                key, topic, partition, offset, Instant.ofEpochMilli(timestamp), timestampType, message
        );

        this.service.run(message);
    }

}
