package com.example.kafkaConsumerAndFirebaseTest.infra.repository.kafka.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentInfoMessage {

    @JsonProperty("billing_number")
    private String billingNumber;


    private long amount;
}
