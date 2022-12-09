package com.seller.usertransactionservice.usertransaction.producers;

import com.seller.usertransactionservice.kafka.configuration.KafkaTopics;
import com.seller.usertransactionservice.usertransaction.messages.ReducePositionAmountMessage;
import com.seller.usertransactionservice.usertransaction.models.UserTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PositionProducer {

    private final KafkaTopics kafkaTopics;
    private final KafkaTemplate<UUID, ReducePositionAmountMessage> kafkaTemplate;

    public void sendReducePositionAmountMessage(UserTransaction userTransaction) {
        var message = ReducePositionAmountMessage.builder()
                .userTransactionId(userTransaction.getId())
                .userId(userTransaction.getCreatedById())
                .positionId(userTransaction.getPositionId())
                .amount(userTransaction.getAmount())
                .build();
        kafkaTemplate.send(
                kafkaTopics.getBoughtsTopic(),
                userTransaction.getCreatedById(),
                message
        );
    }
}
