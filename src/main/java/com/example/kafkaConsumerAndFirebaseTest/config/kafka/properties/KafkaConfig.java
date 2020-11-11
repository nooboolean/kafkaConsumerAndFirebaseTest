package com.example.kafkaConsumerAndFirebaseTest.config.kafka.properties;

import com.example.kafkaConsumerAndFirebaseTest.infra.repository.kafka.deserializer.NoExceptionJsonDeserializer;
import com.example.kafkaConsumerAndFirebaseTest.infra.repository.kafka.entity.PaymentInfoMessage;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    private final KafkaProperties properties;

    @Autowired
    public KafkaConfig(KafkaProperties properties) {
        this.properties = properties;
    }

    public Map<String, Object> getConfigs() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
        configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, properties.getConsumer().getAutoOffsetReset());
        configs.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, properties.getConsumer().isEnableAutoCommit());
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, properties.getConsumer().getKeyDeserializer());
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializer());

        return configs;
    }

    private JsonDeserializer<PaymentInfoMessage> valueDeserializer() {
        return new NoExceptionJsonDeserializer<>(PaymentInfoMessage.class);
    }
}
