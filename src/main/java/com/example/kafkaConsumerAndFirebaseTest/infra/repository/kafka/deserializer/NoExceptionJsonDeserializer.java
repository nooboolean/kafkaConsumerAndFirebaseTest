package com.example.kafkaConsumerAndFirebaseTest.infra.repository.kafka.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Slf4j
public class NoExceptionJsonDeserializer<T> extends JsonDeserializer<T> {

    public NoExceptionJsonDeserializer() {
        super();
    }

    public NoExceptionJsonDeserializer(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    public NoExceptionJsonDeserializer(Class<T> targetType) {
        super(targetType);
    }

    @Override
    public T deserialize(String topic, byte[] data) {
        try {
            if (data == null || data.length == 0) {
                throw new SerializationException("Can't deserialize data [ null ] from topic [ " + topic + " ]");
            }
            return super.deserialize(topic, data);
        } catch (SerializationException e) {
            log.error("Deserialize error [ {} ]", e.getMessage());
            return null;
        }
    }

    @Override
    public T deserialize(String topic, Headers headers, byte[] data) {
        try {
            if (data == null || data.length == 0) {
                throw new SerializationException("Can't deserialize data [ null ] from topic [ " + topic + " ]");
            }
            return super.deserialize(topic, headers, data);
        } catch (SerializationException e ) {
            log.error("Deserialize error [ {} ]", e.getMessage());
            return null;
        }
    }

}
