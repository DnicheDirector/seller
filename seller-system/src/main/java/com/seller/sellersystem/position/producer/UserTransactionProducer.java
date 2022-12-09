package com.seller.sellersystem.position.producer;

import com.seller.sellersystem.kafka.configuration.KafkaTopics;
import com.seller.sellersystem.usertransaction.messages.UserTransactionStatus;
import com.seller.sellersystem.usertransaction.messages.UserTransactionStatusMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserTransactionProducer {

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
