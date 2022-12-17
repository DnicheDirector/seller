package com.seller.sellersystem.position.controllers.kafka;

import com.seller.sellersystem.kafka.configuration.KafkaTopics;
import com.seller.sellersystem.position.messages.ReducePositionAmountMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class KafkaTestProducer {

    private final KafkaTopics kafkaTopics;
    private final KafkaTemplate<UUID, ReducePositionAmountMessage> kafkaTemplate;

    public void sendReducePositionAmountMessage(String userTransactionId, UUID userId, Long positionId, BigDecimal amount) {
        var message = ReducePositionAmountMessage.builder()
                .userId(userId)
                .userTransactionId(userTransactionId)
                .positionId(positionId)
                .amount(amount)
                .build();
        kafkaTemplate.send(kafkaTopics.getBoughtsTopic(), userId, message);
    }
}
