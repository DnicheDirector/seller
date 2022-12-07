package com.seller.usertransactionservice.usertransaction.kafka;

import com.seller.usertransactionservice.kafka.configuration.KafkaTopics;
import com.seller.usertransactionservice.usertransaction.messages.UserTransactionStatusMessage;
import com.seller.usertransactionservice.usertransaction.models.UserTransactionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class KafkaTestProducer {

    private final KafkaTopics kafkaTopics;
    private final KafkaTemplate<UUID, UserTransactionStatusMessage> kafkaTemplate;

    public void sendUserTransactionStatusMessage(Long userTransactionId, UUID userId, UserTransactionStatus status) {
        var message = UserTransactionStatusMessage.builder()
                .userTransactionId(userTransactionId)
                .status(status)
                .build();
        kafkaTemplate.send(kafkaTopics.getTransactionStatusTopic(), userId, message);
    }
}
