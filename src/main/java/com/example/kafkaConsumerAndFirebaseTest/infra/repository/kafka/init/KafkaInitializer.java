package com.example.kafkaConsumerAndFirebaseTest.infra.repository.kafka.init;

import com.example.kafkaConsumerAndFirebaseTest.config.kafka.properties.KafkaConfig;
import com.example.kafkaConsumerAndFirebaseTest.infra.repository.kafka.entity.PaymentInfoMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;

@Slf4j
@EnableKafka
@Configuration
public class KafkaInitializer {

    private final KafkaConfig config;

    @Autowired
    public KafkaInitializer(KafkaConfig config) {
        this.config = config;
    }

    @Bean
    public ConsumerFactory<String, PaymentInfoMessage> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(config.getConfigs());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentInfoMessage> kafkaListenerContainerFactory () {
        ConcurrentKafkaListenerContainerFactory<String, PaymentInfoMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public ConsumerAwareListenerErrorHandler listenerErrorHandler() {
        return (message, exception, consumer) -> {
            MessageHeaders headers = message.getHeaders();
            log.error("Kafka Listener error occurred : {}", exception.getMessage());

            consumer.seek(
                    new TopicPartition(
                            headers.get(KafkaHeaders.RECEIVED_TOPIC, String.class),
                            headers.get(KafkaHeaders.RECEIVED_PARTITION_ID, Integer.class)
                    ),
                    headers.get(KafkaHeaders.OFFSET, Long.class) + 1 // OffSetを進めることで、エラーが起きても後続のメッセージを受け取れる様にする
            );
            return null;
        };


    }
}
