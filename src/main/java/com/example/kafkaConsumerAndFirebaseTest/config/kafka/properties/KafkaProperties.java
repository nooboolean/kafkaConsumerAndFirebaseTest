package com.example.kafkaConsumerAndFirebaseTest.config.kafka.properties;

import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("spring.kafka")
public class KafkaProperties {

    private String bootstrapServers;
    private Template template = new Template();
    private Consumer consumer = new Consumer();

    @Getter
    @Setter
    public class Template {
        private String defaultTopic;
    }

    @Getter
    @Setter
    public class Consumer {
        private String groupId;
        private String autoOffsetReset;
        private boolean enableAutoCommit;
        private Deserializer keyDeserializer;
        private Deserializer valueDeserializer;
    }

}
