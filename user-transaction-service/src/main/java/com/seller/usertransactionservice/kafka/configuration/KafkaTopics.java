package com.seller.usertransactionservice.kafka.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "kafka.topics")
public class KafkaTopics {
    private String boughtsTopic;
    private String transactionStatusTopic;
}
